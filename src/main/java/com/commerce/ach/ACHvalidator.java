package com.commerce.ach;

import com.commerce.ach.ACH3;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Pattern;

public class ACHvalidator{

	protected List<ValidatorResult> validatorResults;
	
// Note: ACHvalidator reads in a file/files performs the runValidator function on each.
//		 Results are written back to _validatorResults.
	
	public ACHvalidator(File[] files) {
		
		this.validatorResults = new ArrayList<ValidatorResult>();
		
		for (int i=0; i<files.length; i++) {
			try	{
				runValidation(files[i]);
			}
			catch(Exception e)	{
				e.printStackTrace();
			}
			
		}

	}
//	more Notes: orgin company, destination company, company id, effective dates/times, entry count, entry hash, debit entry amount, credit entry amount
	
// Note: runValidation is the driver of the ACHvalidator functions. This produces a ValidatorResult
// 		 that is added to the ValidatorResult list for each file ran against the validator. 
//		 The validatorResult contains list of the invalid types, and the errors within those.
//	
// TODO:
// 	 Validate Entries match batches.
//	 
//	BatchControl:
//		Validate that Transaction codes in batch Header and Entries match.
//		Validate sum of the debit entry amount fields in batch(right justify, left zero-fill, assume two decimals)
//	 	Validate sum of the credit entry amount fields in batch (right justify, left zero-fill, assume two decimals)
//	
//	Definitions:
//		ODFI: Originating Depository Financial Institution
//		RDFI: Receiving Depository Financial Institution
//		(M): Mandatory field, required by ODFI
//		(R): Required field, Omission may cause file, batch, or entry to be rejected by the RDFI

	
	private void runValidation(File file) throws IOException {		
		try {
			ACH3 achMcGreg = new ACH3(file);
			String catString = "";
			
			for (String string : achMcGreg.fileCopy) {
				catString += (string+"\n");
			}
			
			Reader inputString = new StringReader(catString);
			BufferedReader bufferedReader = new BufferedReader(inputString);
			ValidatorResult validatorResult = new ValidatorResult(file);
			String currentLine;
			int fileDebitTot = 0;
			int fileCreditTot = 0;
			
			int currentBatch = 0;
			int currentBatchIndex = 0;
			long currentBatchDebitTot = 0;
			long currentBatchCreditTot = 0;
			String currentBatchServiceIndex = "";
			long currentBatchHash = 0;
			
			
			int lineCount = 0;
				while ((currentLine = bufferedReader.readLine()) != null) {					  
					lineCount++;
// 		Note: Checking line length
					FailedLine validLength = validateLineLength(currentLine, lineCount);
					if( validLength != null) 
					{ 
						validatorResult._failedLines.add(validLength);
						validatorResult.incrementBlockCount();
					}
					else if(validatorResult.currentFileControl == null)
					{
//		Note: Remaining validation for valid line lengths
						switch(currentLine.charAt(0))
						{
							case '1':
//		Note: checking if fileHeader is valid format
								FileHeader fileHeader = validateFileHeader(currentLine, lineCount);		  
								if (fileHeader != null)
								{
									validatorResult._failedFileHeaders.add(fileHeader);
									validatorResult.errorInFile[4] = true;
									validatorResult.incrementBlockCount();
								}
//		Note: Checks to make sure only one FileHeader exist, else reports as failed line   
								if (validatorResult.currentFileHeader == null) 
								{
									validatorResult.currentFileHeader = new FileHeader(currentLine, lineCount);
									validatorResult.incrementBlockCount();
								}
								else
								{
									FailedLine failedLine = new FailedLine(currentLine, lineCount);
									failedLine.setErrorMessage(
											"(File Header) Is not a valid File Header, to many exsist ");
									validatorResult._failedLines.add(failedLine);
									validatorResult.incrementBlockCount();
									validatorResult.errorInFile[1] = true;
									validatorResult.errorInFile[4] = true;
								}
								break;
							   
							case '5':
								BatchHeader batchHeader = validateBatchHeader(currentLine, lineCount);
								if (batchHeader != null) 
								{
									validatorResult._failedBatchHeaders.add(batchHeader);
									validatorResult.errorInFile[0] = true;
									validatorResult.incrementBlockCount();
								}
//		Note: adds BatchHeader to report for later validation, get batch number and validates it incremented. Else reports line  
								else
								{
									BatchHeader currentBatchHeader = new BatchHeader(currentLine, lineCount);
									int lineBatch = Integer.parseInt(currentBatchHeader.batchNumber);
									currentBatchServiceIndex = currentBatchHeader.batchNumber;
									validatorResult.batchHeaders.add(currentBatchHeader);
									
									if (currentBatch+1 == lineBatch) 
									{
										currentBatch = lineBatch;
									}
									else 
									{
										FailedLine failedLine = new FailedLine(currentLine, lineCount);
										failedLine.setErrorMessage(
												"(Batch Control) Batch Error, unexpected increment. ");
										validatorResult._failedLines.add(failedLine);
										validatorResult.errorInFile[0] = true;
									}
//		Note: Service index for validation against entry credit/debit	   
									currentBatchServiceIndex = validatorResult.batchHeaders.get(currentBatchIndex).serviceClassCode;
									validatorResult.incrementBlockCount();
								}
								break;
							   
							case '6':
								Entry entry = validateEntry(currentLine, lineCount);
								if (entry != null)
								{
									validatorResult._failedEntries.add(entry);
								}
								else
								{
									Object entryCodeAndAmount[] = entryCodeAmountAndRoute(currentLine);
									int code = (int) entryCodeAndAmount[0];
									long amount = (long) entryCodeAndAmount[1];
									currentBatchHash += (long)entryCodeAndAmount[2];
									if(Arrays.asList(22, 23, 32, 33).contains(code)) 
									{
										if(currentBatchServiceIndex.contentEquals("200") ||
										   currentBatchServiceIndex.contentEquals("220") )
									   	{
											currentBatchCreditTot += amount;
									   	}
									   	else {
									   		validatorResult.errorInFile[9] = true;
									   		FailedLine failedLine = new FailedLine(currentLine, lineCount);
									   		failedLine.setErrorMessage(
									   				"(Entry) Entry is of invalid type given the Batch Service Index. ");
									   		validatorResult._failedLines.add(failedLine);
									   	}
									}
									else if (Arrays.asList(27, 28, 37, 38).contains(code))
									{
										if(currentBatchServiceIndex.contentEquals("200") ||
										   currentBatchServiceIndex.contentEquals("225") ) 
										{
											currentBatchDebitTot += amount;
									   }
									   else
									   {
										   	validatorResult.errorInFile[9] = true;
										   	FailedLine failedLine = new FailedLine(currentLine, lineCount);
										   	failedLine.setErrorMessage(
										   			"(Entry) Entry is of invalid type given the Batch Service Index. ");
										   	validatorResult._failedLines.add(failedLine);
									   }
									}	
									else
									{
										validatorResult.errorInFile[9] = true;
										FailedLine failedLine = new FailedLine(currentLine, lineCount);
										failedLine.setErrorMessage(
												"(Entry) Entry is of invalid type. ");
										validatorResult._failedLines.add(failedLine);
									}
							
								}
								validatorResult.incrementBlockCount();
								break;
							   
							case '7': // Adenda Entry
								break;
							   
							case '8':
								BatchControl batchControl = validateBatchControl(currentLine, lineCount);
								if (batchControl != null) 
								{
									validatorResult._failedBatchControls.add(batchControl);
									validatorResult.errorInFile[0] = true;
									validatorResult.incrementBlockCount();
								}
								else {
									batchControl = new BatchControl(currentLine, lineCount);
									validatorResult.batchControls.add(batchControl);
									if(Integer.parseInt(batchControl.batchNumber) != currentBatch)
									{
										FailedLine failedLine = new FailedLine(currentLine, lineCount);
										failedLine.setErrorMessage(
												"(Batch Control) Batch Number is incorrect. "
												);
										validatorResult._failedLines.add(failedLine);
										validatorResult.errorInFile[0] = true;
									}
									if (batchControl.getTotalDebitAmount() != currentBatchDebitTot )
									{
										FailedLine failedLine = new FailedLine(currentLine, lineCount);
										failedLine.setErrorMessage(
												"(Batch Control) Batch total: " + batchControl.getTotalDebitAmount()+
												", Total based off entries: " + String.format("%06d", currentBatchDebitTot)
												);
										validatorResult._failedLines.add(failedLine);
										validatorResult.errorInFile[9] = true;
									}
									else
									{
										fileDebitTot += currentBatchDebitTot;
										currentBatchDebitTot = 0;
									}
									if (batchControl.getTotalCreditAmount() != currentBatchCreditTot )
									{
										FailedLine failedLine = new FailedLine(currentLine, lineCount);
										failedLine.setErrorMessage(
												"(Batch Control) Batch total: " + batchControl.getTotalCreditAmount()+
												", Total based off entries: " + currentBatchCreditTot
												);
										validatorResult._failedLines.add(failedLine);
										validatorResult.errorInFile[9] = true;
									}
									else 
									{ 
										fileCreditTot += currentBatchCreditTot;
										currentBatchCreditTot = 0;
									}
// Debuging Statements	
// for batch Hash									
//									System.out.println(file.getName()+":\n\t("+
//											currentBatchIndex+") BatchHatch = "+currentBatchHash%10000000+
//											"\n\t\tEntryHash = "+Long.valueOf(validatorResult.batchControls.get(currentBatchIndex).entryHash));
									
//									if (!(currentBatchHash%100000000 == Long.valueOf(validatorResult.batchControls.get(currentBatchIndex).entryHash)))
//									{
//										FailedLine failedLine = new FailedLine(currentLine, lineCount);
//										failedLine.setErrorMessage(
//												"(Batch Control) Batch Hash does not compute");
//										validatorResult._failedLines.add(failedLine);
//										validatorResult.errorInFile[0] = true;
//									}
//									else currentBatchHash = 0;
									validatorResult.incrementBlockCount();
									currentBatchIndex++;
								}
								break;
							   	
							case '9':
								FileControl fileControl = validateFileControl(currentLine, lineCount);
								if (fileControl != null)
								{
									validatorResult._failedFileControls.add(fileControl);
									validatorResult.errorInFile[5] = true;
								}
								if (validatorResult.currentFileControl == null) 
								{
									validatorResult.currentFileControl = new FileControl(currentLine, lineCount);
								}
								else {
									FailedLine failedLine = new FailedLine(currentLine, lineCount);
									failedLine.setErrorMessage(
											"(File Control)Is not a valid File Control, to many exsist ");
									validatorResult._failedLines.add(failedLine);
									validatorResult.errorInFile[2] = true;
									validatorResult.errorInFile[5] = true;
								}
								validatorResult.incrementBlockCount();
								break;
							   
							default: 
								FailedLine failedLine = new FailedLine(currentLine, lineCount);
								failedLine.setErrorMessage(
										"Unknown Line format. ");
								validatorResult._failedLines.add(failedLine);
								validatorResult.incrementBlockCount();
								validatorResult.errorInFile[6] = true;
								break;
						} // end switch
					}
					else if((Pattern.matches("[9]{94}", currentLine)))
					{
						validatorResult.incrementLineFills();
					}
					else
					{
						FailedLine failedLine = new FailedLine(currentLine, lineCount);
						failedLine.setErrorMessage(
								"Unexpected Line after File Control. ");
						validatorResult._failedLines.add(failedLine);
						validatorResult.incrementBlockCount();
						validatorResult.errorInFile[6] = true;
					}
					   
				}// end While
				bufferedReader.close();
				validatorResult.lineCount = lineCount;
	// TODO: batchErrors exist
				int totalCount = validatorResult.blockCount + validatorResult.lineFills;
				if((totalCount) % 10 != 0 )
				{
					validatorResult.errorInFile[7] = true;
				}
				else if (!(validatorResult.currentFileControl.blockCount.contentEquals(String.format("%06d", totalCount/10))))
				{
					validatorResult.errorInFile[8] = true;
				}
				if(validatorResult.currentFileControl.getDebitEntryAmt() != fileDebitTot) 
				{
					validatorResult.errorInFile[9] = true;
					FailedLine failedLine = new FailedLine(currentLine, lineCount);
					failedLine.setErrorMessage(
							"(File Header) File  Debit Total: " + validatorResult.currentFileControl.getDebitEntryAmt() +
							", Total from Batch: " + fileDebitTot);
					validatorResult._failedLines.add(failedLine);
				}
				if(validatorResult.currentFileControl.getCreditEntryAmt() != fileCreditTot) 
				{
					validatorResult.errorInFile[9] = true;
					FailedLine failedLine = new FailedLine(currentLine, lineCount);
					failedLine.setErrorMessage(
							"(File Header) File  Credit Total: " + validatorResult.currentFileControl.getCreditEntryAmt() +
							", Total from Batch: " + fileCreditTot);
					validatorResult._failedLines.add(failedLine);
				}
						
				
			this.validatorResults.add(validatorResult);
	 
		}
			catch (FileNotFoundException e) {e.printStackTrace();} 
	}
		
	
// validateFileHeader: Regex valid string tested.
	private FileHeader validateFileHeader(String currentLine, int lineCount) {
		
		boolean[] errorBools = new boolean[12];
//	priorityCode
		if(!(Pattern.matches("[0][1]", currentLine.substring(1,3))))
		{ 
			errorBools[0] = true; 
		}
		
//	immediateDest
		if(!(Pattern.matches("(\\s)+101000019", currentLine.substring(3,13))))
		{
			errorBools[1] = true;
		}
		
//	immediateOrig
		if(!(Pattern.matches("(\\d){10}", currentLine.substring(13,23))))
		{
			errorBools[2] = true;
		}
		
//	fileCreationDate
		if(!(Pattern.matches("(\\d){2}[01](\\d)[0123](\\d)", currentLine.substring(23,29)))) 
		{
			errorBools[3] = true;
		}
		
//	fileCreationTime
		if(!(Pattern.matches("[012](\\d)[012345](\\d)", currentLine.substring(29,33)))) 
		{
			errorBools[4] = true;
		}
		
//	fileIdModifier
		if(!(Pattern.matches("[A-Z0-9]", (String.valueOf(currentLine.charAt(33)))))) 
		{
			errorBools[5] = true;
		}
		
//	recordSize
		if(!(Pattern.matches("[0][9][4]", currentLine.substring(34,37))))
		{
			errorBools[6] = true;
		}
		
//	blockingFactor
		if(!(Pattern.matches("[1][0]", currentLine.substring(37,39))))
		{
			errorBools[7] = true;
		}
		
//	formatCode
		if(!(Pattern.matches("[1]", (String.valueOf(currentLine.charAt(39))))))
		{
			errorBools[8] = true;
		}
		
//	desintationName
		if(!(Pattern.matches("[(\\s)(\\w)]{23}", currentLine.substring(40,63))))
		{
			errorBools[9] = true;
		}
		
//	originName
		if(!(Pattern.matches("[(\\s)(\\w)]{23}", currentLine.substring(63,86))))
		{
			errorBools[10] = true;
		}
		
//	referenceCode
		if(!(Pattern.matches("[(\\s)(\\w)]{8}", currentLine.substring(86,94)))) 
		{
			errorBools[11] = true;
		}
		
// fileHeader checks if errors exist. If none returns null.
		FileHeader fileHeader = null;
		boolean isReportNeeded = false;
		for (boolean error : errorBools) 
		{
			if(error) isReportNeeded = true;
		}
		if (isReportNeeded)
		{ 
			fileHeader = new FileHeader(currentLine, lineCount);
			fileHeader.setErrors(errorBools);
		}
		return fileHeader;
		
		
	}
// validateFileControl: Regex valid string tested.
	private FileControl validateFileControl(String currentLine, int lineCount) {
		
		boolean[] errorBools = new boolean[7];
		
//	batchCount
//	TODO: validate against File Header
		if(!(Pattern.matches("(\\d){6}", currentLine.substring(1,7))))
		{
			errorBools[0] = true;
		}
		
//	blockCount
//	TODO: Validate the integer value of all records written to output file  including all header, trailer, detail, 
//		  and block filler records divided by the blocking factor of 10 [i.e. (54 records + 6 block filler lines) /10 = 6]
//		  Probably will have to do validation outside the line validation.
		if(!(Pattern.matches("(\\d){6}", currentLine.substring(7,13)))) 
		{
			errorBools[1] = true;
		}
		
//	entryCount
//	TODO: total number of entry detail and addenda records in file
		if(!(Pattern.matches("(\\d){8}", currentLine.substring(13,21))))
		{
			errorBools[2] = true;
		}
		
//	hashEntry
//	TODO Validate against File Header
		if(!(Pattern.matches("(\\d){10}", currentLine.substring(21,31))))
		{
			errorBools[3] = true;
		}
		
//	debitEntryAmt
		if(!(Pattern.matches("(\\d){12}", currentLine.substring(31,43))))
		{
			errorBools[4] = true;
		}
		
//	creditEntryAmt
		if(!(Pattern.matches("(\\d){12}", currentLine.substring(43,55))))
		{
			errorBools[5] = true;
		}
		
//	bankReserved
		if(!(Pattern.matches("(\\s){39}", currentLine.substring(55,94)))) 
		{
			errorBools[6] = true;
		}
		
// fileHeader checks if errors exist. If none returns null.
		FileControl fileControl = null;
		boolean isReportNeeded = false;
		for (boolean error : errorBools)
		{
			if(error) isReportNeeded = true;
		}
		if (isReportNeeded)
		{
			fileControl = new FileControl(currentLine, lineCount);
			fileControl.setErrors(errorBools);
		}
		return fileControl;
		
	
	}
// validateBatchHeader: Regex valid string tested.
	private BatchHeader validateBatchHeader(String currentLine, int lineCount) {
		
		boolean[] errorBools = new boolean[12];		//booleans to aid UI: false by default
//	serviceClassCode
//	TODO: Can only be 200, 220, 225, must match BatchControl
		if(!(Pattern.matches("[2][02][05]", currentLine.substring(1,4))))
		{ 
			errorBools[0] = true; 
		}
		
//	companyName
		if(!(Pattern.matches("(\\w){16}", currentLine.substring(4,20))))
		{
			errorBools[1] = true;
		}
		
//	companyDiscresionaryData
		if(!(Pattern.matches("((\\s)){20}", currentLine.substring(20,40)))) 
		{
			errorBools[2] = true;
		}
		
//	companyID
		if(!(Pattern.matches("[a-zA-Z0-9](\\d){9}", currentLine.substring(40,50))))
		{
			errorBools[3] = true;
		}
		
//	standardEntryCode
//	TODO: Standard Entry Class (SEC) Code - Refer to the NACHA Operating Rules & Guidelines for the appropriate Code.
		if(!(Pattern.matches("(\\w){3}", currentLine.substring(50,53)))) 
		{
			errorBools[4] = true;
		}
		
//	companyEntryDescription	
		if(!(Pattern.matches("[(\\w)(\\s)]{10}", (String.valueOf(currentLine.substring(53,63)))))) 
		{
			errorBools[5] = true;
		}
		
//	companyDescriptionDate	
		if(!(Pattern.matches("(\\w){6}", currentLine.substring(63,69)))) 
		{
			errorBools[6] = true;
		}
		
//	effectiveEntryDate
//	TODO: Effective Entry Date (EXTREMELY IMPORTANT) - date items are to post, must be a valid banking day. 
//		  Same-Day files received before the Same-Day cutoff will be processed as such. 3:00pm
		if(!(Pattern.matches("(\\d){2}[01](\\d)[0123](\\d)", currentLine.substring(69,75)))) 
		{
			errorBools[7] = true;
		}
		
//	settlementDate
		if(!(Pattern.matches("[(\\s)]{3}", currentLine.substring(75,78))))
		{
			errorBools[8] = true;
		}
		
//	originatorStatusCode
		if(!(Pattern.matches("[1]", (String.valueOf(currentLine.charAt(78))))))
		{
			errorBools[9] = true;
		}
		
//	originatingDFIid
		if(!(Pattern.matches("[(\\d)]{8}", currentLine.substring(79,87))))
		{
			errorBools[10] = true;
		}
		
//	batchNumber
		if(!(Pattern.matches("(\\d){7}", currentLine.substring(87,94))))
		{
			errorBools[11] = true;
		}
		
// fileHeader checks if errors exist. If none returns null.
		BatchHeader batchHeader = null;
		boolean isReportNeeded = false;
		for (boolean error : errorBools)
		{
			if(error) isReportNeeded = true;
		}
		if (isReportNeeded) 
		{
			batchHeader = new BatchHeader(currentLine, lineCount);
			batchHeader.setErrors(errorBools);
		}
		return batchHeader;
	}
//	BatchControl: still need to test
	private BatchControl validateBatchControl(String currentLine, int lineCount) {
		
		boolean[] errorBools = new boolean[10];
		
//	serviceClassCode
//	TODO: Can only be 200, 220, 225, and must match BatchHeader	
		if(!(Pattern.matches("[2][02][05]", currentLine.substring(1,4))))
		{
			errorBools[0] = true;
		}
		
//	entryCount
//	TODO: validate the number of entries equals entry count.
		if(!(Pattern.matches("(\\d){6}", currentLine.substring(4,10))))
		{
			errorBools[1] = true;
		}
		
//	entryHash
//	TODO: sum of the first 8 digits of the routing numbers in batch; ignore any overflow beyond the 10-character
//		  size with left digits being dropped.
		if(!(Pattern.matches("(\\d){10}", currentLine.substring(10,20)))) 
		{
			errorBools[2] = true;
		}
		
//	totalDebitAmount
// 	TODO: write sum back to validate against total in File Control
		if(!(Pattern.matches("(\\d){12}", currentLine.substring(20,32)))) 
		{
			errorBools[3] = true;
		}
		
//	totalCreditAmount
//	TODO: write sum back to validate against total in File Control

		if(!(Pattern.matches("(\\d){12}", currentLine.substring(32,44))))
		{
			errorBools[4] = true;
		}
		
//	companyID
		if(!(Pattern.matches("(\\w)(\\d){9}", currentLine.substring(44,54)))) 
		{
			errorBools[5] = true;
		}
		
//	messageAuthenticationCode
		if(!(Pattern.matches("[(\\s)(\\w)]{19}", currentLine.substring(54,73))))
		{
			errorBools[6] = true;
		}
		
//	bankUse
		if(!(Pattern.matches("[(\\s)(\\w)]{6}", currentLine.substring(73,79)))) 
		{
			errorBools[7] = true;
		}
		
//	originatingDFIid
		if(!(Pattern.matches("(\\w){8}", currentLine.substring(79,87)))) 
		{
			errorBools[8] = true;
		}
//	batchNumber
//	TODO: validate the batchNumber is the same as the batchHeader 
		if(!(Pattern.matches("(\\d){7}", currentLine.substring(87,94))))
		{
			errorBools[9] = true;
		}
		
// fileHeader checks if errors exist. If none returns null.
		BatchControl batchControl = null;
		boolean isReportNeeded = false;
		for (boolean error : errorBools) 
		{
			if(error) isReportNeeded = true;
		}
		if (isReportNeeded) 
		{
			batchControl = new BatchControl(currentLine, lineCount);
			batchControl.setErrors(errorBools);
		}
		return batchControl;
		
		
	}
//	validateEntry: still need to test
	private Entry validateEntry(String currentLine, int lineCount) {
		
		boolean[] errorBools = new boolean[10];
		
//	transactionCode
//	TODO: Can only be the values 22, 23, 27, 28, 32, 33, 37,38
		if(!(Pattern.matches("[23][2378]", currentLine.substring(1,3))))
		{
			errorBools[0] = true;
		}
		
//	receivingRoutingNumber
		if(!(Pattern.matches("(\\d){8}", currentLine.substring(3,11)))) 
		{
			errorBools[1] = true;
		}
		
//	routingCheckDigit
		if(!(Pattern.matches("(\\d)", String.valueOf(currentLine.charAt(11))))) 
		{
			errorBools[2] = true;
		}
		
//	receivingAccountNumber
//	TODO: validate that is left justified, and space-filled.
		if(!(Pattern.matches("[(\\s)(\\w)]{17}", currentLine.substring(12,29)))) 
		{
			errorBools[3] = true;
		}
		
//	dollarAmount
		if(!(Pattern.matches("(\\d){10}", currentLine.substring(29,39)))) 
		{
			errorBools[4] = true;
		}
		
//	receiverIDnumber
		if(!(Pattern.matches("[(\\s)(\\w)]{15}", currentLine.substring(39,54)))) 
		{
			errorBools[5] = true;
		}
		
//	receiverName
		if(!(Pattern.matches("[(\\s)(\\w)]{22}", currentLine.substring(54,76)))) 
		{
			errorBools[6] = true;
		}
		
//	discretionaryData
		if(!(Pattern.matches("[(\\s)]{2}", currentLine.substring(76,78)))) 
		{
			errorBools[7] = true;
		}
		
//	addendaRecordIndicator
		if(!(Pattern.matches("[01]", String.valueOf(currentLine.charAt(78))))) 
		{
			errorBools[8] = true;
		}
//	traceNumber
//	TODO: Trace number - ascending, sequential order unique to each transaction. Bank will insert new number upon collection.
		if(!(Pattern.matches("(\\d){15}", currentLine.substring(79,94)))) 
		{
			errorBools[9] = true;
		}
		
// fileHeader checks if errors exist. If none returns null.
		Entry entry = null;
		boolean isReportNeeded = false;
		for (boolean error : errorBools) 
		{
			if(error) isReportNeeded = true;
		}
		if (isReportNeeded) 
		{
			entry = new Entry(currentLine, lineCount);
			entry.setErrors(errorBools);
		}
		return entry;
		
	
	}
	 
	private FailedLine validateLineLength(String currentLine, int lineCount) {
		
		FailedLine failedLine = null;
		int lineLength = currentLine.length();
		
		if(lineLength != 94) 
		{
			if(lineLength > 94) 
			{
				failedLine = new FailedLine(currentLine, lineCount);
				failedLine.setErrorMessage("Is over 94 characters long. ");
			}
			else{
				failedLine = new FailedLine(currentLine, lineCount);
				failedLine.setErrorMessage("Is under 94 characters long. ");
			}
			
		}
		return failedLine;
		
		
	}
	
	private Object[] entryCodeAmountAndRoute(String currentLine) {
		Object[] codeAmountRoute = new Object[3];
//		Note: grabbing Transaction Code and Dollar Amount.
		codeAmountRoute[0] = Integer.valueOf(currentLine.substring(1,3));
		codeAmountRoute[1] = Long.valueOf(currentLine.substring(29,39));
		codeAmountRoute[2] = Long.valueOf(currentLine.substring(12,19).trim());
//		System.out.println(codeAmountRoute[2].toString());
		return codeAmountRoute;
		
		
	}
	
	public void returnResults(){
		for (ValidatorResult result : validatorResults)
		{
			result.errorResults();
		}
		
		
	}
	

}
