package com.commerce.ach;

public class File999 {
	
	String contents;
	int lineNumber;
	boolean error;
	
	public File999(String currentLine, int lineNum, boolean error) {
		this.contents = currentLine;
		this.lineNumber = lineNum;
		this.error = error;
	}

	public String getContents() {
		return contents;
	}
	public int getLineNumber() {
		return lineNumber;
	}
	public boolean getError() {
		return error;
	}
	
	
}
