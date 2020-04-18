package com.commerce.ach;

public class FailedLine {

	protected String line;
	protected int lineNum;
	protected String errorMessage;
	protected boolean errorBools[];
	
	public FailedLine(String currentLine, int lineNum) {
	
		this.line = currentLine;
		this.lineNum = lineNum;
				
	}
	
	protected void setErrors(boolean[] errorBools) {
		this.errorBools = errorBools;
	}
	
	protected void setErrorMessage(String errorMessage) {
		this.errorMessage = "Line "+String.format("% 4d", lineNum)+": "+errorMessage;
	}
	

}
