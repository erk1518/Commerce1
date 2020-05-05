package com.commerce.ach.validation;

public class FailedLine {
	
	String flLine;
	String flErrorDescription;
	int flLineNumber;
	
	protected FailedLine (String currentLine, String errorDescription, int lineNum) {
		
		this.flLine = currentLine;
		this.flErrorDescription = errorDescription;
		this.flLineNumber = lineNum;
		
	}
	
}
