package com.commerce.ach;

import java.util.regex.Pattern;

public class ACH4validator {

	protected ACH4 valres;
	
	public ACH4validator(ACH4 ach4) {
		
		this.valres = ach4;
		runValidation();	
	}
	public ACH4 getResults () {
		return this.valres;
	}
	
	private void runValidation() {
		
		int batchCount = 1;
		long fileDebitTot = 0;
		long fileCreditTot = 0;
		long fileEntryCount = 0;
		long fileBlockCount = 0;
		long fileBatchHash = 0;
		
		validateFileHeader();
		validateFileControl();
		for(Batch batch : valres.batchRecords) {
			validateBatch(batch);
			if(batchCount++ != Integer.valueOf(batch.bc11BatchNumber))
				batch.bcErrors[9] = true;
			
			if (!batch.bcErrors[1])
				fileEntryCount += Long.valueOf(batch.bc3EntryCount);
			if(!batch.bcErrors[2])
				fileBatchHash += Long.valueOf(batch.bc4EntryHash);
			if (!batch.bcErrors[3])
				fileDebitTot += Long.valueOf(batch.bc5TotalDebitEntry);
			if (!batch.bcErrors[4])
				fileCreditTot += Long.valueOf(batch.bc6TotalCreditEntry);
			fileBlockCount += fileEntryCount + 2;
		}
		for(File999 file999 : valres.file999) {
			validateFile999(file999);
			if (!file999.error)
				fileBlockCount++;
		}
		
		if(batchCount-1 != Integer.valueOf(valres.fc2BatchCount))
			valres.fcErrors[0] = true;
		if((fileBlockCount+2)/10 != Long.valueOf(valres.fc3BlockCount))
			valres.fcErrors[1] = true;
		if(fileEntryCount != Long.valueOf(valres.fc4EntryCount))
			valres.fcErrors[2] = true;
		if(fileBatchHash != Long.valueOf(valres.fc5EntryHash))
			valres.fcErrors[3] = true;
		if(fileDebitTot != Long.valueOf(valres.fc6TotalDebitEntry))
			valres.fcErrors[4] = true;
		if(fileCreditTot != Long.valueOf(valres.fc7TotalCreditEntry))
			valres.fcErrors[5] = true;
		
		
	}
	
	private void validateFileHeader() {
//		priorityCode
		if(!(Pattern.matches("[0][1]", valres.fh2PriorityCode)))
		{ 
			valres.fhErrors[0] = true; 
		}
		
//		immediateDest
		if(!(Pattern.matches("(\\s)+101000019", valres.fh3ImmediateDest)))
		{
			valres.fhErrors[1] = true;
		}
		
//		immediateOrig
		if(!(Pattern.matches("(\\d){10}", valres.fh4ImmediateOrigin)))
		{
			valres.fhErrors[2] = true;
		}
		
//		fileCreationDate
		if(!(Pattern.matches("(\\d){2}[01](\\d)[0123](\\d)", valres.fh5FileCreationDate))) 
		{
			valres.fhErrors[3] = true;
		}
		
//		fileCreationTime
		if(!(Pattern.matches("[012](\\d)[012345](\\d)", valres.fh6FileCreationTime))) 
		{
			valres.fhErrors[4] = true;
		}
		
//		fileIdModifier
		if(!(Pattern.matches("[A-Z0-9]", (String.valueOf(valres.fh7FileIDModifier))))) 
		{
			valres.fhErrors[5] = true;
		}
		
//		recordSize
		if(!(Pattern.matches("[0][9][4]", valres.fh8RecordSize)))
		{
			valres.fhErrors[6] = true;
		}
		
//		blockingFactor
		if(!(Pattern.matches("[1][0]", valres.fh9BlockingFactor)))
		{
			valres.fhErrors[7] = true;
		}
		
//		formatCode
		if(!(Pattern.matches("[1]", (String.valueOf(valres.fh10FormatCode)))))
		{
			valres.fhErrors[8] = true;
		}
		
//		desintationName
		if(!(Pattern.matches("[(\\s)(\\w)]{23}", valres.fh11ImmediateDestName)))
		{
			valres.fhErrors[9] = true;
		}
		
//		originName
		if(!(Pattern.matches("[(\\s)(\\w)]{23}", valres.fh12ImmediateOriginName)))
		{
			valres.fhErrors[10] = true;
		}
		
//		referenceCode
		if(!(Pattern.matches("[(\\s)(\\w)]{8}", valres.fh13ReferenceCode))) 
		{
			valres.fhErrors[11] = true;
		}
	}
	private void validateFileControl() {
//		batchCount
		if(!(Pattern.matches("(\\d){6}", valres.fc2BatchCount)))
		{
			valres.fcErrors[0] = true;
		}
		
//		blockCount
		if(!(Pattern.matches("(\\d){6}", valres.fc3BlockCount))) 
		{
			valres.fcErrors[1] = true;
		}
		
//		entryCount
		if(!(Pattern.matches("(\\d){8}", valres.fc4EntryCount)))
		{
			valres.fcErrors[2] = true;
		}
		
//		hashEntry
		if(!(Pattern.matches("(\\d){10}", valres.fc5EntryHash)))
		{
			valres.fcErrors[3] = true;
		}
		
//		debitEntryAmt
		if(!(Pattern.matches("(\\d){12}", valres.fc6TotalDebitEntry)))
		{
			valres.fcErrors[4] = true;
		}
		
//		creditEntryAmt
		if(!(Pattern.matches("(\\d){12}", valres.fc7TotalCreditEntry)))
		{
			valres.fcErrors[5] = true;
		}
		
//		bankReserved
		if(!(Pattern.matches("(\\s){39}", valres.fc8Reserved))) 
		{
			valres.fcErrors[6] = true;
		}
			
			
	}
	private void validateBatch(Batch batch) {
		
		Long entryTrace = new Long(batch.entries.get(0).er11TraceNum);
		Long currentBatchDebitTot = new Long(0);
		Long currentBatchCreditTot = new Long(0);
		
		validateBatchHeader(batch);
		validateBatchControl(batch);
// validating batch's service class code.
		if (!(batch.bh2ServiceClassCode.contentEquals(batch.bc2ServiceClassCode))) {
			batch.bhErrors[1] = true;
			batch.bcErrors[1] = true;
		}
		long entryCount = 0;
		long entryHash = 0;
		for(EntryRecord entry : batch.entries) {
				validatEntry(entry);
				if(!(Long.valueOf(entry.er11TraceNum).equals(entryTrace++)))
					entry.erErrors[10] = true;
// Adds Entry Dollar amounts to batch totals
				switch(batch.bc2ServiceClassCode) {
				case "200":
					if (entry.er2TransactionCode.charAt(1) == '2' ||
						entry.er2TransactionCode.charAt(1) == '3')
							currentBatchCreditTot += Long.valueOf(entry.er6DollarAmount);
					if (entry.er2TransactionCode.charAt(1) == '7' ||
						entry.er2TransactionCode.charAt(1) == '8')
							currentBatchDebitTot += Long.valueOf(entry.er6DollarAmount);
					break;
				case "220":
					if (entry.er2TransactionCode.charAt(1) == '2' ||
						entry.er2TransactionCode.charAt(1) == '3')
							currentBatchCreditTot += Long.valueOf(entry.er6DollarAmount);
					else entry.erErrors[1] = true;
					break;
				case "225":
					if (entry.er2TransactionCode.charAt(1) == '7' ||
						entry.er2TransactionCode.charAt(1) == '8')
							currentBatchDebitTot += Long.valueOf(entry.er6DollarAmount);
					else entry.erErrors[1] = true;
					break;
				}
				entryCount++;
				entryHash += Long.valueOf(String.valueOf(entry.er3RoutingNumReceivingInst.subSequence(0, 8)));
			}
		if (Long.valueOf(batch.bc3EntryCount) != entryCount)
			batch.bcErrors[1] = true;
		if(Long.valueOf(batch.bc4EntryHash) != entryHash)
			batch.bcErrors[2] = true;
		
		long bc5 = Long.valueOf(batch.bc5TotalDebitEntry);
		if(bc5 != currentBatchDebitTot)
			batch.bcErrors[3] = true;
		
		long bc6 = Long.valueOf(batch.bc6TotalCreditEntry);
		if(bc6 != currentBatchCreditTot)
			batch.bcErrors[4] = true;
		
	}
	private void validateBatchHeader(Batch batch) {

		if(!(Pattern.matches("[2][02][05]", batch.bh2ServiceClassCode)))
		{ 
			batch.bhErrors[0] = true; 
		}
		
//		companyName
		if(!(Pattern.matches("(\\w){16}", batch.bh3CompanyName)))
		{
			batch.bhErrors[1] = true;
		}
		
//		companyDiscresionaryData
		if(!(Pattern.matches("((\\s)){20}", batch.bh4CompanyDiscrData))) 
		{
			batch.bhErrors[2] = true;
		}
		
//		companyID
		if(!(Pattern.matches("[a-zA-Z0-9](\\d){9}", batch.bh5CompanyIdentification)))
		{
			batch.bhErrors[3] = true;
		}
		
//		standardEntryCode
//		TODO: Standard Entry Class (SEC) Code - Refer to the NACHA Operating Rules & Guidelines for the appropriate Code.
		if(!(Pattern.matches("(\\w){3}", batch.bh6StandardEntryClassCode))) 
		{
			batch.bhErrors[4] = true;
		}
		
//		companyEntryDescription	
		if(!(Pattern.matches("[(\\w)(\\s)]{10}", (String.valueOf(batch.bh7CompanyEntryDescr))))) 
		{
			batch.bhErrors[5] = true;
		}
		
//		companyDescriptionDate	
		if(!(Pattern.matches("(\\w){6}", batch.bh8CompanyDescrDate))) 
		{
			batch.bhErrors[6] = true;
		}
		
//		effectiveEntryDate
//		TODO: Effective Entry Date (EXTREMELY IMPORTANT) - date items are to post, must be a valid banking day. 
//			  Same-Day files received before the Same-Day cutoff will be processed as such. 3:00pm
		if(!(Pattern.matches("(\\d){2}[01](\\d)[0123](\\d)", batch.bh9EffectiveEntryDate))) 
		{
			batch.bhErrors[7] = true;
		}
		
//		settlementDate
		if(!(Pattern.matches("[(\\s)]{3}", batch.bh10SettlementDate)))
		{
			batch.bhErrors[8] = true;
		}
		
//		originatorStatusCode
		if(!(Pattern.matches("[1]", (String.valueOf(batch.bh11OriginatorStatusCode)))))
		{
			batch.bhErrors[9] = true;
		}
		
//		originatingDFIid
		if(!(Pattern.matches("[(\\d)]{8}", batch.bh12OriginatingDFIID)))
		{
			batch.bhErrors[10] = true;
		}
		
//		batchNumber
		if(!(Pattern.matches("(\\d){7}", batch.bh13BatchNum)))
		{
			batch.bhErrors[11] = true;
		}
	}
	private void validateBatchControl(Batch batch) {
//		serviceClassCode
		if(!(Pattern.matches("[2][02][05]", batch.bc2ServiceClassCode)))
		{
			batch.bcErrors[0] = true;
		}
		
//		entryCount
		if(!(Pattern.matches("(\\d){6}", batch.bc3EntryCount)))
		{
			batch.bcErrors[1] = true;
		}
		
//		entryHash
		if(!(Pattern.matches("(\\d){10}", batch.bc4EntryHash))) 
		{
			batch.bcErrors[2] = true;
		}
		
//		totalDebitAmount
		if(!(Pattern.matches("(\\d){12}", batch.bc5TotalDebitEntry))) 
		{
			batch.bcErrors[3] = true;
		}
		
//		totalCreditAmount
		if(!(Pattern.matches("(\\d){12}", batch.bc6TotalCreditEntry)))
		{
			batch.bcErrors[4] = true;
		}
		
//		companyID
		if(!(Pattern.matches("(\\w)(\\d){9}", batch.bc7CompanyID))) 
		{
			batch.bcErrors[5] = true;
		}
		
//		messageAuthenticationCode
		if(!(Pattern.matches("[(\\s)(\\w)]{19}", batch.bc8MessageAuthCode)))
		{
			batch.bcErrors[6] = true;
		}
		
//		bankUse
		if(!(Pattern.matches("[(\\s)(\\w)]{6}", batch.bc9Reserved))) 
		{
			batch.bcErrors[7] = true;
		}
		
//		originatingDFIid
		if(!(Pattern.matches("(\\w){8}", batch.bc10OriginatingDFIID))) 
		{
			batch.bcErrors[8] = true;
		}
//		batchNumber
//		TODO: validate the batchNumber is the same as the batchHeader 
		if(!(Pattern.matches("(\\d){7}", batch.bc11BatchNumber)))
		{
			batch.bcErrors[9] = true;
		}
	}
	private void validatEntry(EntryRecord entry) {
//		transactionCode
//		TODO: Can only be the values 22, 23, 27, 28, 32, 33, 37,38
		if(!(Pattern.matches("[23][2378]", entry.er2TransactionCode)))
		{
			entry.erErrors[0] = true;
		}
		
//		receivingRoutingNumber
		if(!(Pattern.matches("(\\d){8}", entry.er3RoutingNumReceivingInst))) 
		{
			entry.erErrors[1] = true;
		}
		
//		routingCheckDigit
		if(!(Pattern.matches("(\\d)", String.valueOf(entry.er4RoutingNumCheckDigit)))) 
		{
			entry.erErrors[2] = true;
		}
		
//		receivingAccountNumber

		String er5sub1 = entry.er5ReceivingInstAcctNum;
		String er5sub2 = "";
		int index = entry.er5ReceivingInstAcctNum.indexOf(" ",0);
		if (index != -1) {
			er5sub1 = entry.er5ReceivingInstAcctNum.substring(0,index);
			er5sub2 = entry.er5ReceivingInstAcctNum.substring(index,17);
		}
		if(!(Pattern.matches("[(\\d)]{6,17}", er5sub1))) 
			entry.erErrors[3] = true;
		if(!(Pattern.matches("[(\\ )]{0,11}", er5sub2))) 
			entry.erErrors[3] = true;
		
//		dollarAmount
		if(!(Pattern.matches("(\\d){10}", entry.er6DollarAmount))) 
		{
			entry.erErrors[4] = true;
		}
		
//		receiverIDnumber
		if(!(Pattern.matches("[(\\s)(\\w)]{15}", entry.er7ReceiverIDNum))) 
		{
			entry.erErrors[5] = true;
		}
		
//		receiverName
		if(!(Pattern.matches("[(\\s)(\\w)]{22}", entry.er8ReceiverName))) 
		{
			entry.erErrors[6] = true;
		}
		
//		discretionaryData
		if(!(Pattern.matches("[(\\s)]{2}", entry.er9DiscretionaryData))) 
		{
			entry.erErrors[7] = true;
		}
		
//		addendaRecordIndicator
		if(!(Pattern.matches("[01]", String.valueOf(entry.er10AddendaRecordInd)))) 
		{
			entry.erErrors[8] = true;
		}
//		traceNumber
		if(!(Pattern.matches("(\\d){15}", entry.er11TraceNum))) 
		{
			entry.erErrors[9] = true;
		}
	}
	private void validateFile999(File999 file999) {
		if(!(Pattern.matches("[9]{94}", file999.contents)))
			file999.error = true; 
	}
	
}
