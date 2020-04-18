package com.commerce.ach;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class ValidatorResult{
		
	protected Date _achTimeStamp;
	protected String _fileName;
	protected String _filePath;
	
//	blockCount: The integer value of all records written to output file  including all header, trailer, detail, 
//	and block filler records divided by the blocking factor of 10
	protected int fileDebitTotal;
	protected int fileCreditTotal;
	protected int blockCount;
	protected int lineFills; // 9999's
	protected int batchCount;
	protected int lineCount;
	
	
	protected FileHeader currentFileHeader ;
	protected List<BatchHeader> batchHeaders;
	protected	List<Double> batchTotaldebit;
	protected	List<Double> batchTotalCredit;
	protected List<BatchControl> batchControls;
	FileControl currentFileControl;
	
	protected boolean[] errorInFile;
	
	protected List<FileHeader> _failedFileHeaders;
	protected List<FileControl> _failedFileControls;
	protected List<BatchHeader> _failedBatchHeaders;
	protected List<BatchControl> _failedBatchControls;
	protected List<Entry> _failedEntries;
	protected List<FailedLine> _failedLines;


	public ValidatorResult(File file) {
	
		this._achTimeStamp = new Date();
		this._fileName = file.getName();
		this._filePath = file.getPath();
		
		this.fileDebitTotal = 0;
		this.fileCreditTotal = 0;
		this.blockCount = 0;
		this.lineFills = 0;
		this.batchCount = 0;
		this.lineCount =0;
		
		this.currentFileHeader = null;
		this.batchHeaders = new ArrayList<BatchHeader>();
		this.batchTotaldebit = new ArrayList<Double>();
		this.batchTotalCredit = new ArrayList<Double>();
		this.batchControls = new ArrayList<BatchControl>();
		this.currentFileControl = null;
		
		errorInFile = new boolean[10]; 
		
		this._failedFileHeaders = new ArrayList<FileHeader>();
		this._failedFileControls = new ArrayList<FileControl>();
		this._failedBatchHeaders = new ArrayList<BatchHeader>();
		this._failedBatchControls = new ArrayList<BatchControl>();
		this._failedEntries = new ArrayList<Entry>();
		this._failedLines = new ArrayList<FailedLine>();
		
	}
	
	protected void incrementBlockCount() {
		this.blockCount++;
	}
	protected Integer getBlockCount() {
		return (Integer)(this.blockCount/10);
	}
	protected void incrementLineFills() {
		this.lineFills++;
	}
	
	protected String errorInFileResults() {
		
		String message = "";
		
		if(errorInFile[0]) {
			message = message + "\t[0] Error in Batch number.\n";
		}
		if(errorInFile[1]) {
			message = message + "\t[1] More File Headers than expected.\n";
		}
		if(errorInFile[2]) {
			message = message + "\t[2] More File Controls than expected.\n";
		}
		if(errorInFile[3]) {
			message = message + "\t[3] Line/lines have an invalid length.\n";
		}
		if(errorInFile[4]) {
			message = message + "\t[4] Errors exist in File Header.\n";
		}
		if(errorInFile[5]) {
			message = message + "\t[5] Errors exist in File Control.\n";
		}
		if(errorInFile[6]) {
			message = message + "\t[6] Unknown line found.\n";
		}
		if(errorInFile[7]) {
			message = message + "\t[7] Total lines do not result in a base 10 result.\n";
		}
		if(errorInFile[8]) {
			message = message + "\t[8] Batch Count does not equate to File Control Record.\n";
		}
		if(errorInFile[9]) {
			message = message + "\t[9] Error processing total amounts.\n";
		}
		if((message.isEmpty())) {
			message = "\tNo general File Errors\n";
		}
		return message+"\n";
	}
	
	public void errorResults() {
		
		int fileHeaderIndex = 0, fileHeaderLastIndex = _failedFileHeaders.size(), fileHeaderNL = -1, 
			fileControlIndex = 0, fileControlLastIndex = _failedFileControls.size(), fileControNL = -1,
			batchHeaderIndex = 0, batchHeaderLastIndex = _failedBatchHeaders.size(), batchHeaderNL = -1,
			batchControlIndex = 0, batchControlLastIndex = _failedBatchControls.size(), batchControlNL = -1,
			entryIndex = 0, entryLastIndex = _failedEntries.size(), entryNL = -1,
			failedLineIndex = 0, faileLineLastIndex = _failedLines.size(), failedLineNL = -1;
		
		System.out.println("Validator Results(" + _fileName + "): ");
		
		System.out.print(errorInFileResults());
		for(int line=0; line <= this.lineCount; line++) {
			
			if(fileHeaderIndex < fileHeaderLastIndex) {
				fileHeaderNL = _failedFileHeaders.get(fileHeaderIndex).lineNum;
			}
			if (line == fileHeaderNL) {
				System.out.println("\t"+_failedFileHeaders.get(fileHeaderIndex++).errorToString());
			}
			
			if(fileControlIndex < fileControlLastIndex) {
				fileControNL = _failedFileControls.get(fileControlIndex).lineNum;
			}
			if (line == fileControNL) {
				System.out.println("\t"+_failedFileControls.get(fileControlIndex++).errorToString());
			}
			
			if(batchHeaderIndex < batchHeaderLastIndex) {
				batchHeaderNL = _failedBatchHeaders.get(batchHeaderIndex).lineNum;
			}
			if (line == batchHeaderNL) {
				System.out.println("\t"+_failedBatchHeaders.get(batchHeaderIndex++).errorToString());
			}
			
			if(batchControlIndex < batchControlLastIndex) {
				batchControlNL = _failedBatchControls.get(batchControlIndex).lineNum;
			}
			if (line== batchControlNL) {
				System.out.println("\t"+_failedBatchControls.get(batchControlIndex++).errorToString());
			}
			
			if(entryIndex < entryLastIndex) {
				entryNL = _failedEntries.get(entryIndex).lineNum;
			}
			if(line == entryNL) {
				System.out.println("\t"+_failedEntries.get(entryIndex++).errorToString());
			}
			
			if(failedLineIndex < faileLineLastIndex) {
				failedLineNL = _failedLines.get(failedLineIndex).lineNum;
			}
			if(line == failedLineNL) {
				System.out.println("\t"+_failedLines.get(failedLineIndex++).errorMessage);
			}
		
		}
		System.out.println();
	}

	
	
}

