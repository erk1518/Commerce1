package com.commerce.ach;

import java.util.Arrays;

public class BatchHeader {
	
	private char recordTypeCode;
	protected String serviceClassCode;		
	private String companyName;	
	private String companyDiscresionaryData; 
	private String companyID;
	private String standardEntryCode;	
	private String companyEntryDescription;
	private String companyDescriptionDate;
	private String effectiveEntryDate;
	private String settlementDate;
	private char originatorStatusCode;
	private String originatingDFIid;
	protected String batchNumber;
	
	protected int lineNum;
	private String errorMessage;
	protected boolean errorBools[];
	

	public BatchHeader(String currentLine, int lineNum) {
	
		this.recordTypeCode = currentLine.charAt(0);
		this.serviceClassCode = currentLine.substring(1,4);	
		this.companyName = currentLine.substring(4,20);
		this.companyDiscresionaryData = currentLine.substring(20,40); 
		this.companyID = currentLine.substring(40,50);
		this.standardEntryCode = currentLine.substring(50,53);
		this.companyEntryDescription = currentLine.substring(53,63);
		this.companyDescriptionDate = currentLine.substring(63,69);
		this.effectiveEntryDate = currentLine.substring(69,75);
		this.settlementDate = currentLine.substring(75,78);
		this.originatorStatusCode = currentLine.charAt(78);
		this.originatingDFIid = currentLine.substring(79,87);
		this.batchNumber = currentLine.substring(87,94);
		
		this.lineNum = lineNum;
	}
	
	protected void setErrors(boolean[] errorBools) {
		this.errorBools = errorBools;
	}

	@Override
	public String toString() {
		return "BatchHeader [recordTypeCode=" + recordTypeCode + ", serviceClassCode=" + serviceClassCode
				+ ", companyName("+companyName.length() + ")=" + companyName + ", companyDiscresionaryData("
				+companyDiscresionaryData.length() + ")=" + companyDiscresionaryData + ", companyID("
				+ companyID.length()+")=" + companyID + ", standardEntryCode("+ standardEntryCode.length() 
				+ ")=" + standardEntryCode + ", commpanyEntryDescription(" + companyEntryDescription.length() 
				+")=" + companyEntryDescription + ", companyDescriptionDate(" + companyDescriptionDate.length() 
				+ ")=" + companyDescriptionDate + ", effectiveEntryDate(" + effectiveEntryDate.length() + ")=" 
				+ effectiveEntryDate + ", settlementDate(" +  settlementDate.length() + ")=" + settlementDate
				+ ", originatorStatusCode=" + originatorStatusCode + ", originatingDFIid(" +  originatingDFIid.length()
				+ ")=" + originatingDFIid + ", batchNumber(" +  batchNumber.length() + ")=" + batchNumber 
				+ ", errorMessage=" + errorMessage + ", errorBools=" + Arrays.toString(errorBools) + "]";
	}
	
	public String errorToString() {
		    
			String errorMessage = "Line "+String.format("% 4d", lineNum)+": (Batch Header)";
			String tempMessage = errorMessage;
			
			if(errorBools[0]) { errorMessage += " [0]Error in Service Class Code.";
				}
			if(errorBools[1]) { errorMessage += " [1]Error in Company Name.";
				}
			if(errorBools[2]) { errorMessage += " [2]Error in Company Discretionary Data.";
				}
			if(errorBools[3]) { errorMessage += " [3]Error in Company ID.";
				}
			if(errorBools[4]) { errorMessage += " [4]Error in Standard Entry Class(SEC) Code.";
				}
			if(errorBools[5]) { errorMessage += " [5]Error in Comapny Entry Description.";
				}
			if(errorBools[6]) { errorMessage += " [6]Error in Company Descriptive Date.";
				}
			if(errorBools[7]) { errorMessage += " [7]Error in Effective Entry Date.";
				}
			if(errorBools[8]) { errorMessage += " [8]Error in Settlement Date.";
				}
			if(errorBools[9]) { errorMessage += " [9]Error in Originator Status Code.";
				}
			if(errorBools[10]) { errorMessage += " [10]Error in Originating DFI ID.";
				}
			if(errorBools[11]) { errorMessage += " [11]Error in Batch Number.";
				}
			
			if(errorMessage != tempMessage) {
			return errorMessage;
				}
			else {
				return errorMessage += " No Error";
			}
			
		}
	
	
}
