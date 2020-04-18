package com.commerce.ach;

import java.util.Arrays;

public class Entry {

	private char recordTypeCode;
	private String transactionCode;		
	private String receivingRoutingNumber;	
	private char routingCheckDigit; 
	private String receivingAccountNumber;
	private String dollarAmount;	
	private String receiverIDnumber;
	private String receiverName;
	private String discretionaryData;
	private char addendaRecordIndicator;
	private String traceNumber;
	
	protected int lineNum;
	protected boolean errorBools[];
	

	public Entry(String curLine, int lineNum) {
	
		this.recordTypeCode = curLine.charAt(0);
		this.transactionCode = curLine.substring(1,3);
		this.receivingRoutingNumber = curLine.substring(3,11);
		this.routingCheckDigit = curLine.charAt(11);
		this.receivingAccountNumber = curLine.substring(12,29);
		this.dollarAmount = curLine.substring(29,39);
		this.receiverIDnumber = curLine.substring(39,54);
		this.receiverName = curLine.substring(54,76);
		this.discretionaryData = curLine.substring(76,78);
		this.addendaRecordIndicator = curLine.charAt(78);
		this.traceNumber = curLine.substring(79,94);
		
		this.lineNum = lineNum;
		
	}
	
	protected void setErrors(boolean[] errorBools) {
		this.errorBools = errorBools;
	}


	@Override
	public String toString() {
		return "Entry [recordTypeCode=" + recordTypeCode + ", transactionCode=" + transactionCode
				+ ", receivingRoutingNumber(" + receivingRoutingNumber.length() + ")=" + receivingRoutingNumber 
				+ ", routingCheckDigit=" + routingCheckDigit + ", receivingAccountNumber(" + receivingAccountNumber.length()
				+")=" + receivingAccountNumber + ", dollarAmount(" + dollarAmount.length() + ")=" + dollarAmount
				+ ", receiverIDnumber(" + receiverIDnumber.length() + ")=" + receiverIDnumber + ", receiverName(" + receiverName.length()
				+ ")=" + receiverName + ", discretionaryData(" + discretionaryData.length() + ")=" + discretionaryData 
				+ ", addendaRecordIndicator=" + addendaRecordIndicator + ", traceNumber(" + traceNumber.length() + ")="
				+ traceNumber + ", errorBools=" + Arrays.toString(errorBools) + "]";
	}
	
	public String errorToString() {
	    
		String errorMessage = "Line "+String.format("% 4d", lineNum)+": (Entry)";
		String tempMessage = errorMessage;
		
		if(errorBools[0]) { errorMessage += " [0]Error in Transaction Code.";
			}
		if(errorBools[1]) { errorMessage += " [1]Error in Receiving Routing Number.";
			}
		if(errorBools[2]) { errorMessage += " [2]Error in Routing Number Check Digit.";
			}
		if(errorBools[3]) { errorMessage += " [3]Error in Receiving Account Number.";
			}
		if(errorBools[4]) { errorMessage += " [4]Error in Dollar Amount.";
			}
		if(errorBools[5]) { errorMessage += " [5]Error in Receiver ID number.";
			}
		if(errorBools[6]) { errorMessage += " [6]Error in Receiver Name.";
			}
		if(errorBools[7]) { errorMessage += " [7]Error in Discretionary Data.";
		}
		if(errorBools[8]) { errorMessage += " [8]Error in Addenda Record Indicator.";
		}
		if(errorBools[9]) { errorMessage += " [9]Error in Trace Number.";
		}

		if(errorMessage != tempMessage) {
		return errorMessage;
			}
		else {
			return errorMessage += " No Error";
		}
		
	}

}
