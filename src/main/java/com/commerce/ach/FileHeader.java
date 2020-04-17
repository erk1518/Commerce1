package com.commerce.ach;

import java.util.Arrays;

public class FileHeader {
	
	private char recordTypeCode;
	private String priorityCode;		
	private String immediateDest;	
	private String immediateOrig; 
	protected String fileCreationDate;
	protected String fileCreationTime;	
	private char fileIdModifier;
	private String recordSize;
	private String blockingFactor;
	private char formatCode;
	private String desintationName;
	private String originName;
	private String referenceCode;
	
	protected int lineNum;
	protected boolean errorBools[];
	

	public FileHeader(String currentLine, int lineNum) {
	
		this.recordTypeCode = currentLine.charAt(0);		 // f:01 p:01-01 r:M
		this.priorityCode = currentLine.substring(1,3);		 // f:02 p:02-03 r:R
		this.immediateDest = currentLine.substring(3,13);	 // f:03 p:04-13 r:M
		this.immediateOrig = currentLine.substring(13,23);   // f:04 p:14-23 r:M, Inconsistency in documentation(non-whitespace) 
		this.fileCreationDate = currentLine.substring(23,29);// f:05 p:24-29 r:M
		this.fileCreationTime = currentLine.substring(29,33);// f:06 p:30-33 r:O
		this.fileIdModifier = currentLine.charAt(33);		 // f:07 p:34-34 r:M
		this.recordSize = currentLine.substring(34,37);		 // f:08 p:35-37 r:M
		this.blockingFactor = currentLine.substring(37,39);  // f:09 p:38-39 r:M
		this.formatCode = currentLine.charAt(39);			 // f:10 p:40-40 r:M
		this.desintationName = currentLine.substring(40,63); // f:11 p:41-63 r:O
		this.originName = currentLine.substring(63,86);		 // f:12 p:64-86 r:O
		this.referenceCode =currentLine.substring(86,94); 	 // f:13 p:87-94 r:o
		
		this.lineNum = lineNum;
		
	}

	protected void setErrors(boolean[] errorBools) {
		this.errorBools = errorBools;
	}
	
	@Override
	public String toString() {
		return "FileHeader [recordTypeCode=" + recordTypeCode + ", priorityCode=" + priorityCode + ", immediateDest("+immediateDest.length()+")="
				+ immediateDest + ", immediateOrig("+immediateOrig.length()+")=" + immediateOrig + ", fileCreationDate=" + fileCreationDate
				+ ", fileCreationTime=" + fileCreationTime + ", fileIdModifier=" + fileIdModifier + ", recordSize="
				+ recordSize + ", blockingFactor=" + blockingFactor + ", formatCode=" + formatCode
				+ ", desintationName("+desintationName.length()+")=" + desintationName + ", originName("+originName.length()+")=" + originName +
				", referenceCode("+referenceCode.length()+")="+ referenceCode + ", errorBools=" + Arrays.toString(errorBools)
				+ "]";
	}
	
	public String errorToString() {
	    
		String errorMessage = "Line "+String.format("% 4d", lineNum)+": (File Header)";
		String tempMessage = errorMessage;
		
		if(errorBools[0]) { errorMessage += " [0]Error in Priority Code.";
			}
		if(errorBools[1]) { errorMessage += " [1]Error in Immediate Destination.";
			}
		if(errorBools[2]) { errorMessage += " [2]Error in Immediate Origin.";
			}
		if(errorBools[3]) { errorMessage += " [3]Error in Date.";
			}
		if(errorBools[4]) { errorMessage += " [4]Error in Time.";
			}
		if(errorBools[5]) { errorMessage += " [5]Error in ID modifier.";
			}
		if(errorBools[6]) { errorMessage += " [6]Error in Record size.";
			}
		if(errorBools[7]) { errorMessage += " [7]Error in Blocking factor.";
			}
		if(errorBools[8]) { errorMessage += " [8]Error in Format code..";
			}
		if(errorBools[9]) { errorMessage += " [9]Error in immediate destination.";
			}
		if(errorBools[10]) { errorMessage += " [10]Error in iimmediate origin..";
			}
		if(errorBools[11]) { errorMessage += " [11]Error in Reference code.";
			}

		if(errorMessage != tempMessage) {
		return errorMessage;
			}
		else {
			return errorMessage += " No Error";
		}
		
	}

}
