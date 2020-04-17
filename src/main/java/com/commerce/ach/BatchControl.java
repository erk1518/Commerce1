package com.commerce.ach;

import java.util.Arrays;

public class BatchControl {

	private char recordTypeCode;
	private String serviceClassCode;		
	private String entryCount;	
	private String entryHash; 
	private String totalDebitAmount;
	private String totalCreditAmount;	

	private String companyID;
	private String messageAuthenticationCode;
	private String bankUse;
	private String originatingDFIid;
	protected String batchNumber;
	
	protected int lineNum;
	protected boolean errorBools[];
	

	public BatchControl(String currentLine, int lineNum) {
	
		this.recordTypeCode = currentLine.charAt(0);
		this.serviceClassCode = currentLine.substring(1,4);
		this.entryCount = currentLine.substring(4,10);
		this.entryHash = currentLine.substring(10,20);
		this.totalDebitAmount = currentLine.substring(20,32);
		this.totalCreditAmount = currentLine.substring(32,44);
		this.companyID = currentLine.substring(44,54);
		this.messageAuthenticationCode = currentLine.substring(54,73);
		this.bankUse = currentLine.substring(73,79);
		this.originatingDFIid = currentLine.substring(79,87);
		this.batchNumber = currentLine.substring(87,94);
		
		this.lineNum = lineNum;
		
	}
	
	protected void setErrors(boolean[] errorBools) {
		this.errorBools = errorBools;
	}


	@Override
	public String toString() {
		return "BatchControl [recordTypeCode=" + recordTypeCode + ", serviceClassCode=" + serviceClassCode
				+ ", entryCount(" + entryCount.length() + ")=" + entryCount + ", entryHash(" + entryHash.length() 
				+ ")=" + entryHash + ", totalDebitAmount(" + totalDebitAmount.length() + ")=" + totalDebitAmount
				+ ", totalCreditAmount(" + totalCreditAmount.length() +")=" + totalCreditAmount + ", companyID(" 
				+ companyID.length() +")=" + companyID + ", messageAuthenticationCode(" + messageAuthenticationCode.length()
				+ ")=" + messageAuthenticationCode + ", bankUse(" + bankUse.length() + ")=" + bankUse + ", originatingDFIid(" 
				+ originatingDFIid.length() + ")=" + originatingDFIid + ", batchNumber(" + batchNumber.length() + ")=" 
				+ batchNumber + ", errorBools=" + Arrays.toString(errorBools) + "]";
	}
	
	public String errorToString() {
	    
		String errorMessage = "Line "+String.format("% 4d", lineNum)+": (Batch Control)";
		String tempMessage = errorMessage;
		
		if(errorBools[0]) { errorMessage += " [0]Error in Service Class Code.";
			}
		if(errorBools[1]) { errorMessage += " [1]Error in Entry Count.";
			}
		if(errorBools[2]) { errorMessage += " [2]Error in Entry Hash.";
			}
		if(errorBools[3]) { errorMessage += " [3]Error in Debit Amount.";
			}
		if(errorBools[4]) { errorMessage += " [4]Error in Credit Entry.";
			}
		if(errorBools[5]) { errorMessage += " [5]Error in Company ID.";
			}
		if(errorBools[6]) { errorMessage += " [6]Error in Message Authenication Code.";
			}
		if(errorBools[7]) { errorMessage += " [7]Error in Bank Reserved Field.";
			}
		if(errorBools[8]) { errorMessage += " [8]Error in Originating DFI ID.";
			}
		if(errorBools[9]) { errorMessage += " [9]Error in Batch Number.";
			}

		if(errorMessage != tempMessage) {
		return errorMessage;
			}
		else {
			return errorMessage += " No Error";
		}
		
	}
	public Integer getTotalDebitAmount() {
		return Integer.valueOf(totalDebitAmount);
	}
	public Integer getTotalCreditAmount() {
		return Integer.valueOf(totalCreditAmount);
	}

	
}
