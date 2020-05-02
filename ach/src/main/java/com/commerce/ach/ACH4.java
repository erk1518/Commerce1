package com.commerce.ach;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class ACH4 {
	
	protected Date _currentTimeStamp;
	protected String _fileName;
	protected String _filePath;
	protected ArrayList<String> fileCopy;  //original file, line by line,
	
	protected boolean[] achErrors;
	protected int lineCount;

	//HEADER RECORD - HR
	protected char fh1RecordTypeCode;         // 01-01    1   '1'
	protected String fh2PriorityCode;           // 02-03    2   '01'
	protected String fh3ImmediateDest;          // 04-13    10  '_101000019'
	protected String fh4ImmediateOrigin;        // 14-23    10  _nnnnnnnnn
	protected String fh5FileCreationDate;       // 24-29    6   YYMMDD
	protected String fh6FileCreationTime;       // 30-33    4   HHMM
	protected char fh7FileIDModifier;         // 34-34    1   alphameric
	protected String fh8RecordSize;             // 35-37    3   '094'
	protected String fh9BlockingFactor;         // 38-39    2   '10'
	protected char fh10FormatCode;            // 40-40    1   '1'
	protected String fh11ImmediateDestName;     // 41-63    23  'Commerce Bank'
	protected String fh12ImmediateOriginName;   // 64-86    23  alphameric
	protected String fh13ReferenceCode;         // 87-94    8   alphameric
	protected int fhLineNumber;
	protected boolean[] fhErrors;
	
	protected ArrayList<Batch> batchRecords;
	protected ArrayList<File999> file999;
	protected ArrayList<FailedLine> failedLines;
	
	//FILE CONTROL - FC
	protected char fc1RecordTypeCode;           // 01-01    1   '9'
	protected String fc2BatchCount;               // 02-07    6   numeric
	protected String fc3BlockCount;               // 08-13    6   numeric
	protected String fc4EntryCount;               // 14-21    8   numeric
	protected String fc5EntryHash;                // 22-31    10  numeric
	protected String fc6TotalDebitEntry;          // 32-43    12  $$$$$$$$$cc
	protected String fc7TotalCreditEntry;         // 44-55    12  $$$$$$$$$cc
	protected String fc8Reserved;                 // 56-94    39  blanks
	protected int fcLineNumber;
	protected boolean[] fcErrors;
	
	int batchIndex;
	
	

	public ACH4(File file){

        try { 
        	this._currentTimeStamp = new Date();
        	this._fileName = file.getName();
        	this._filePath = file.getPath();
        	this.fileCopy = new ArrayList<String>();
        	
        	this.batchRecords = new ArrayList<Batch>();
        	this.file999 = new ArrayList<File999>();
        	this.failedLines = new ArrayList<FailedLine>();
        	
        	this.achErrors = new boolean[10];
        	this.fhErrors = new boolean[12];
        	this.fcErrors = new boolean[7];
        	
        	
        	this.lineCount = 0;
        	this.batchIndex = 0;
        	this.fh1RecordTypeCode = '8';
        	
        	buildACH4(file);
        	
        	
        }
        catch (Exception ex) { System.out.println("ACH FileNotFoundException thrown: " + ex); }
        
	}
	
	public void buildACH4 (File file) throws FileNotFoundException {
		
		try {
			BufferedReader bufferedReader = new BufferedReader(new FileReader(file));
			String currentLine = "";

			while ((currentLine = bufferedReader.readLine()) != null) {					  
				lineCount++;
				fileCopy.add(currentLine);
				int lineLength = currentLine.length();
				if(lineLength == 94) {
					switch(currentLine.charAt(0))
					{
// TODO: validate that no line are before file Header	
						case '1':
							this.setFileHeader(currentLine, lineCount);
							break;
						
						case '5':
							this.addBatchHeader(currentLine, lineCount);
							break;
							
						case '6':
							this.addEntryToBatch(currentLine, lineCount);
							break;
							
						case '7':
							this.addAddendasToBatch(currentLine, lineCount);
							break;
						
						case '8':
							this.addBatchControl(currentLine, lineCount);
							break;
							
						case '9':
							if(currentLine.charAt(1) != '9')
								 this.setFileControl(currentLine, lineCount);
							else this.file999.add(new File999(currentLine, lineCount, false));
					}
				}
				else if(lineLength < 94) {
//TODO:
					failedLines.add(new FailedLine(currentLine,
							"Line length less than 94", lineCount));
					achErrors[0] = true;
				}
				else {
//TODO:
					failedLines.add(new FailedLine(currentLine,
							"Line length greater than 94", lineCount));
					achErrors[0] = true;
				}
			}
			bufferedReader.close();	
		}
		catch (FileNotFoundException e) {
			System.out.print("File Not Found: ");
			e.printStackTrace();
			System.out.println();
		}
		catch (IOException e) {
			System.out.print("IO exception: ");
			e.printStackTrace();
			System.out.println();
		} 
			
	}
        
	protected void setFileHeader (String currentLine, int lineNum) {
        
		this.fh1RecordTypeCode = currentLine.charAt(0);				 // f:01 p:01-01 r:M
		this.fh2PriorityCode = currentLine.substring(1,3);		   	 // f:02 p:02-03 r:R
		this.fh3ImmediateDest = currentLine.substring(3,13);	 	 // f:03 p:04-13 r:M
		this.fh4ImmediateOrigin = currentLine.substring(13,23);   	 // f:04 p:14-23 r:M, Inconsistency in documentation(non-whitespace) 
		this.fh5FileCreationDate = currentLine.substring(23,29);	 // f:05 p:24-29 r:M
		this.fh6FileCreationTime = currentLine.substring(29,33);	 // f:06 p:30-33 r:O
		this.fh7FileIDModifier = currentLine.charAt(33);		 	 // f:07 p:34-34 r:M
		this.fh8RecordSize = currentLine.substring(34,37);		 	 // f:08 p:35-37 r:M
		this.fh9BlockingFactor = currentLine.substring(37,39);  	 // f:09 p:38-39 r:M
		this.fh10FormatCode = currentLine.charAt(39);			 	 // f:10 p:40-40 r:M
		this.fh11ImmediateDestName = currentLine.substring(40,63); 	 // f:11 p:41-63 r:O
		this.fh12ImmediateOriginName = currentLine.substring(63,86); // f:12 p:64-86 r:O
		this.fh13ReferenceCode = currentLine.substring(86,94); 	 	 // f:13 p:87-94 r:o
		
		this.fhLineNumber = lineNum;
        
	}
	
	protected void setFileControl (String currentLine, int lineNum) {
		
		this.fc1RecordTypeCode = currentLine.charAt(0);
		this.fc2BatchCount = currentLine.substring(1,7);
		this.fc3BlockCount = currentLine.substring(7,13);
		this.fc4EntryCount = currentLine.substring(13,21); 
		this.fc5EntryHash = currentLine.substring(21,31);
		this.fc6TotalDebitEntry = currentLine.substring(31,43);
		this.fc7TotalCreditEntry= currentLine.substring(43,55);
		this.fc8Reserved = currentLine.substring(55,94);	
	
		this.fcLineNumber = lineNum;
		
	}
	
	protected void addBatchHeader(String currentLine, int lineNum) {
		
		if(batchRecords.size() == batchIndex) {
			Batch batch = new Batch();
			batch.setBatchHeader(currentLine, lineNum);
			batchRecords.add(batchIndex, batch);
			
		}
		else {
//TODO: flag correct booleans
			failedLines.add(new FailedLine(currentLine,
					"Batch Control not found before addition of Batch Header", lineNum));
			achErrors[0] = true;
		}
		
	}
	
	protected void addBatchControl(String currentLine, int lineNum) {
		
		if(batchRecords.get(batchIndex) != null) {
			batchRecords.get(batchIndex++).setBatchControl(currentLine, lineNum);
		}
		else {
//TODO: Flag correct booleans
			failedLines.add(new FailedLine(currentLine,
					"Batch Header not found before addition of Batch Control", lineNum));
			achErrors[0] = true;
		}
		
	}
	
	protected void addEntryToBatch(String currentLine, int lineNum) {
		
		if(batchRecords.get(batchIndex) != null)
			batchRecords.get(batchIndex).addEntry(currentLine, lineNum);
//TODO: Flag correct booleans
		else {
			failedLines.add(new FailedLine(currentLine,
					"Entry Found before creation of Batch", lineNum));
			achErrors[0] = true;
		}
		
	}

	protected void addAddendasToBatch(String currentLine, int lineNum) {
		
		if(batchRecords.get(batchIndex) != null)
			batchRecords.get(batchIndex).addAddenda(currentLine, lineNum);
//TODO: Flag correct booleans
		else {
			failedLines.add(new FailedLine(currentLine,
					"Addenda Found before creation of Batch", lineNum));
			achErrors[0] = true;
		}
	}

	@Override
	public String toString() {
		String theReturn = 
				"ACH4 [ fileName= " + _fileName + ",\n\tfilePath= " + _filePath + "\n\t achErrors=" + Arrays.toString(achErrors)
				+"\n\n\tFile Header:"
				+"\n\t fhLineNumber: "+ fhLineNumber+ ", fhErrors=" + Arrays.toString(fhErrors) + "\n\t fh1RecordTypeCode="
				+ fh1RecordTypeCode + ", fh2PriorityCode=" + fh2PriorityCode
				+ ", fh3ImmediateDest=" + fh3ImmediateDest + ", fh4ImmediateOrigin=" + fh4ImmediateOrigin
				+ ", fh5FileCreationDate=" + fh5FileCreationDate + ", fh6FileCreationTime=" + fh6FileCreationTime
				+ ", fh7FileIDModifier=" + fh7FileIDModifier + ", fh8RecordSize=" + fh8RecordSize
				+ ", fh9BlockingFactor=" + fh9BlockingFactor + ", fh10FormatCode=" + fh10FormatCode
				+ ", fh11ImmediateDestName=" + fh11ImmediateDestName + ", fh12ImmediateOriginName="
				+ fh12ImmediateOriginName + ", fh13ReferenceCode=" + fh13ReferenceCode;
				for(Batch b : batchRecords) {
					theReturn +=
							"\n\n\t\tBatch Header:" 
							+"\n\t\t bhLineNumber= " + b.bhLineNumber + ", bhErrors= " + Arrays.toString(b.bhErrors) +"\n\t\t bh1RecordTypeCode = " 
							+b.bh1RecordTypeCode +", bh2ServiceClassCode" + b.bh2ServiceClassCode + ", bh3" + b.bh3CompanyName + "Batch [bh4CompanyDiscrData="
							+ b.bh4CompanyDiscrData + ", bh5CompanyIdentification="+ b.bh5CompanyIdentification + ", bh6StandardEntryClassCode="
							+ b.bh6StandardEntryClassCode + ", bh7CompanyEntryDescr=" + b.bh7CompanyEntryDescr + ", bh8CompanyDescrDate="
							+ b.bh8CompanyDescrDate + ", bh9EffectiveEntryDate=" + b.bh9EffectiveEntryDate + ", bh10SettlementDate=" + b.bh10SettlementDate
							+ ", bh11OriginatorStatusCode=" + b.bh11OriginatorStatusCode + ", bh12OriginatingDFIID="
							+ b.bh12OriginatingDFIID + ", bh13BatchNum=" + b.bh13BatchNum;
					
							theReturn += b.entriesToString();

					theReturn +=
							"\n\n\t\tBatchControl:"
							+"\n\t\t bcLineNumber=" + b.bcLineNumber + ", bcErrors=" + Arrays.toString(b.bcErrors) +"\n\t\t bc1RecordTypeCode=" + b.bc1RecordTypeCode
							+ ", bc2ServiceClassCode=" + b.bc2ServiceClassCode + ", bc3EntryCount=" + b.bc3EntryCount
							+ ", bc4EntryHash=" + b.bc4EntryHash + ", bc5TotalDebitEntry=" + b.bc5TotalDebitEntry
							+ ", bc6TotalCreditEntry=" + b.bc6TotalCreditEntry + ", bc7CompanyID=" + b.bc7CompanyID
							+ ", bc8MessageAuthCode=" + b.bc8MessageAuthCode + ", bc9Reserved=" + b.bc9Reserved
							+ ", bc10OriginatingDFIID=" + b.bc10OriginatingDFIID + ", bc11BatchNumber=" + b.bc11BatchNumber;
									
				}
				theReturn += "\n\n\tFile Control:"
				+"\n\tfcLineNumber=" + fcLineNumber +", fcErrors=" + Arrays.toString(fcErrors)+"\n\t fc1RecordTypeCode=" + fc1RecordTypeCode
				+ ", fc2BatchCount=" + fc2BatchCount + ", fc3BlockCount=" + fc3BlockCount + ", fc4EntryCount="
				+ fc4EntryCount + ", fc5EntryHash=" + fc5EntryHash + ", fc6TotalDebitEntry=" + fc6TotalDebitEntry
				+ ", fc7TotalCreditEntry=" + fc7TotalCreditEntry + ", fc8Reserved=" + fc8Reserved + "\n";
				
				for(File999 n : file999) {
					theReturn += 
							"\n\t lineNumber= " + n.lineNumber + ", " + n.contents;
				}
				for(FailedLine f : failedLines)
					theReturn += 
						"\n\t\t\t lineNumber= " + f.flLineNumber + ", " + f.flErrorDescription
						+ "\n\t\t\t" + f.flLine;
				return theReturn;
	}

	public String get_fileName() {
		return _fileName;
	}

	public boolean[] getAchErrors() {
		return achErrors;
	}

	public char getFh1RecordTypeCode() {
		return fh1RecordTypeCode;
	}

	public String getFh2PriorityCode() {
		return fh2PriorityCode;
	}

	public String getFh3ImmediateDest() {
		return fh3ImmediateDest;
	}

	public String getFh4ImmediateOrigin() {
		return fh4ImmediateOrigin;
	}

	public String getFh5FileCreationDate() {
		return fh5FileCreationDate;
	}

	public String getFh6FileCreationTime() {
		return fh6FileCreationTime;
	}

	public char getFh7FileIDModifier() {
		return fh7FileIDModifier;
	}

	public String getFh8RecordSize() {
		return fh8RecordSize;
	}

	public String getFh9BlockingFactor() {
		return fh9BlockingFactor;
	}

	public char getFh10FormatCode() {
		return fh10FormatCode;
	}

	public String getFh11ImmediateDestName() {
		return fh11ImmediateDestName;
	}

	public String getFh12ImmediateOriginName() {
		return fh12ImmediateOriginName;
	}

	public String getFh13ReferenceCode() {
		return fh13ReferenceCode;
	}

	public int getFhLineNumber() {
		return fhLineNumber;
	}

	public boolean[] getFhErrors() {
		return fhErrors;
	}

//TODO: array access	
public ArrayList<Batch> getBatchRecords() {
		return batchRecords;
	}

	public ArrayList<FailedLine> getFailedLines() {
		return failedLines;
	}

	public char getFc1RecordTypeCode() {
		return fc1RecordTypeCode;
	}

	public String getFc2BatchCount() {
		return fc2BatchCount;
	}

	public String getFc3BlockCount() {
		return fc3BlockCount;
	}

	public String getFc4EntryCount() {
		return fc4EntryCount;
	}

	public String getFc5EntryHash() {
		return fc5EntryHash;
	}

	public String getFc6TotalDebitEntry() {
		return fc6TotalDebitEntry;
	}

	public String getFc7TotalCreditEntry() {
		return fc7TotalCreditEntry;
	}

	public String getFc8Reserved() {
		return fc8Reserved;
	}

	public int getFcLineNumber() {
		return fcLineNumber;
	}

	public boolean[] getFcErrors() {
		return fcErrors;
	}

	public int getBatchIndex() {
		return batchIndex;
	}

	
//	public void set_fileName(String _fileName) {
//		this._fileName = _fileName;
//	}
//
//	public void setAchErrors(boolean[] achErrors) {
//		this.achErrors = achErrors;
//	}
//
//	public void setFh1RecordTypeCode(char fh1RecordTypeCode) {
//		this.fh1RecordTypeCode = fh1RecordTypeCode;
//	}
//
//	public void setFh2PriorityCode(String fh2PriorityCode) {
//		this.fh2PriorityCode = fh2PriorityCode;
//	}
//
//	public void setFh3ImmediateDest(String fh3ImmediateDest) {
//		this.fh3ImmediateDest = fh3ImmediateDest;
//	}
//
//	public void setFh4ImmediateOrigin(String fh4ImmediateOrigin) {
//		this.fh4ImmediateOrigin = fh4ImmediateOrigin;
//	}
//
//	public void setFh5FileCreationDate(String fh5FileCreationDate) {
//		this.fh5FileCreationDate = fh5FileCreationDate;
//	}
//
//	public void setFh6FileCreationTime(String fh6FileCreationTime) {
//		this.fh6FileCreationTime = fh6FileCreationTime;
//	}
//
//	public void setFh7FileIDModifier(char fh7FileIDModifier) {
//		this.fh7FileIDModifier = fh7FileIDModifier;
//	}
//
//	public void setFh8RecordSize(String fh8RecordSize) {
//		this.fh8RecordSize = fh8RecordSize;
//	}
//
//	public void setFh9BlockingFactor(String fh9BlockingFactor) {
//		this.fh9BlockingFactor = fh9BlockingFactor;
//	}
//
//	public void setFh10FormatCode(char fh10FormatCode) {
//		this.fh10FormatCode = fh10FormatCode;
//	}
//
//	public void setFh11ImmediateDestName(String fh11ImmediateDestName) {
//		this.fh11ImmediateDestName = fh11ImmediateDestName;
//	}
//
//	public void setFh12ImmediateOriginName(String fh12ImmediateOriginName) {
//		this.fh12ImmediateOriginName = fh12ImmediateOriginName;
//	}
//
//	public void setFh13ReferenceCode(String fh13ReferenceCode) {
//		this.fh13ReferenceCode = fh13ReferenceCode;
//	}
//
//	public void setFhLineNumber(int fhLineNumber) {
//		this.fhLineNumber = fhLineNumber;
//	}
//
//	public void setFhErrors(boolean[] fhErrors) {
//		this.fhErrors = fhErrors;
//	}
//
//	public void setBatchRecords(ArrayList<Batch> batchRecords) {
//		this.batchRecords = batchRecords;
//	}
//
//	public void setFailedLines(ArrayList<FailedLine> failedLines) {
//		this.failedLines = failedLines;
//	}
//
//	public void setFc1RecordTypeCode(char fc1RecordTypeCode) {
//		this.fc1RecordTypeCode = fc1RecordTypeCode;
//	}
//
//	public void setFc2BatchCount(String fc2BatchCount) {
//		this.fc2BatchCount = fc2BatchCount;
//	}
//
//	public void setFc3BlockCount(String fc3BlockCount) {
//		this.fc3BlockCount = fc3BlockCount;
//	}
//
//	public void setFc4EntryCount(String fc4EntryCount) {
//		this.fc4EntryCount = fc4EntryCount;
//	}
//
//	public void setFc5EntryHash(String fc5EntryHash) {
//		this.fc5EntryHash = fc5EntryHash;
//	}
//
//	public void setFc6TotalDebitEntry(String fc6TotalDebitEntry) {
//		this.fc6TotalDebitEntry = fc6TotalDebitEntry;
//	}
//
//	public void setFc7TotalCreditEntry(String fc7TotalCreditEntry) {
//		this.fc7TotalCreditEntry = fc7TotalCreditEntry;
//	}
//
//	public void setFc8Reserved(String fc8Reserved) {
//		this.fc8Reserved = fc8Reserved;
//	}
//
//	public void setFcLineNumber(int fcLineNumber) {
//		this.fcLineNumber = fcLineNumber;
//	}
//
//	public void setFcErrors(boolean[] fcErrors) {
//		this.fcErrors = fcErrors;
//	}
//
//	public void setBatchIndex(int batchIndex) {
//		this.batchIndex = batchIndex;
//	}
//
//	
}
