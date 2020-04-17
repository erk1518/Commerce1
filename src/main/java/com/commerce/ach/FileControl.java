package com.commerce.ach;

import java.util.Arrays;

public class FileControl {
		
		private char recordTypeCode;
		private String batchCount;		
		protected String blockCount;//The total number of records included in the ACH file, rounded up to the nearest 10, then divided by 10. (08-13) 6 numeric.	
		private String entryCount; 
		private String hashEntry;
		private String debitEntryAmt;	
		private String creditEntryAmt;
		private String bankReserved;
		
		protected int lineNum;
		protected boolean errorBools[];	//cat of bools to aid UI
		

		public FileControl(String currentLine, int lineNum) {
		
			this.recordTypeCode = currentLine.charAt(0);
			this.batchCount = currentLine.substring(1,7);
			this.blockCount = currentLine.substring(7,13);
			this.entryCount = currentLine.substring(13,21); 
			this.hashEntry = currentLine.substring(21,31);
			this.debitEntryAmt = currentLine.substring(31,43);
			this.creditEntryAmt = currentLine.substring(43,55);
			this.bankReserved = currentLine.substring(55,94);	
		
			this.lineNum = lineNum;
			
		}
		protected void setErrors(boolean[] errorBools) {
			this.errorBools = errorBools;
		}

		@Override
		public String toString() {
			return "FileControl [recordTypeCode=" + recordTypeCode + ", batchCount("+batchCount.length()+")=" + batchCount + ", blockCount("+blockCount.length()+")="
					+ blockCount + ", entryCount("+entryCount.length()+")=" + entryCount + ", hashEntry("+hashEntry.length()+")=" + hashEntry + ", debitEntryAmt("+debitEntryAmt.length()+")="
					+ debitEntryAmt + ", creditEntryAmt("+creditEntryAmt.length()+")=" + creditEntryAmt + ", bankReserved("+bankReserved.length()+")=" + bankReserved
					+ ", errorBools=" + Arrays.toString(errorBools) + "]";
		}
		
		public String errorToString() {
		    
			String errorMessage = "Line "+String.format("% 4d", lineNum)+": (File Control)";
			String tempMessage = errorMessage;
			
			if(errorBools[0]) { errorMessage += " [0]Error in Batch Count.";
				}
			if(errorBools[1]) { errorMessage += " [1]Error in Block Count.";
				}
			if(errorBools[2]) { errorMessage += " [2]Error in Entry Count.";
				}
			if(errorBools[3]) { errorMessage += " [3]Error in Entry Hash.";
				}
			if(errorBools[4]) { errorMessage += " [4]Error in Debit Entry.";
				}
			if(errorBools[5]) { errorMessage += " [5]Error in Credit Entry.";
				}
			if(errorBools[6]) { errorMessage += " [6]Error in Bank use section.";
				}

			if(errorMessage != tempMessage) {
			return errorMessage;
				}
			else {
				return errorMessage += " No Error";
			}
			
		}
		
		public Integer getDebitEntryAmt() {
			return Integer.valueOf(debitEntryAmt);
		}
		public Integer getCreditEntryAmt() {
			return Integer.valueOf(creditEntryAmt);
		}


}
