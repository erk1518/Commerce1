package com.commerce.ach.validation;

import java.util.Arrays;

public class EntryRecord {
	
    protected String er1RecordTypeCode;              // 01-01    1   '6'
    protected String er2TransactionCode;             // 02-03    2   numeric
    protected String er3RoutingNumReceivingInst;     // 04-11    8   numeric
    protected String er4RoutingNumCheckDigit;        // 12-12    1   numeric
    protected String er5ReceivingInstAcctNum;        // 13-29    17  alphameric
    protected String er6DollarAmount;                // 30-39    10  $$$$$$$$cc
    protected String er7ReceiverIDNum;               // 40-54    15  alphameric
    protected String er8ReceiverName;                // 55-76    22  alphameric
    protected String er9DiscretionaryData;           // 77-78    2   blanks
    protected String er10AddendaRecordInd;           // 79-79    1   numeric
    protected String er11TraceNum;                   // 80-94    15  numeric
    protected int erLineNumber;
    protected boolean[] erErrors;

    public EntryRecord(String currentLine, int lineNum) {
    	
        this.er1RecordTypeCode = currentLine.substring(0,1);           // 01-01    1   '6'
        this.er2TransactionCode = currentLine.substring(1,3);          // 02-03    2   numeric
        this.er3RoutingNumReceivingInst = currentLine.substring(3,11); // 04-11    8   numeric
        this.er4RoutingNumCheckDigit = currentLine.substring(11,12);   // 12-12    1   numeric
        this.er5ReceivingInstAcctNum = currentLine.substring(12,29);   // 13-29    17  alphameric
        this.er6DollarAmount = currentLine.substring(29,39);           // 30-39    10  $$$$$$$$cc
        this.er7ReceiverIDNum = currentLine.substring(39,54);          // 40-54    15  alphameric
        this.er8ReceiverName = currentLine.substring(54,76);           // 55-76    22  alphameric
        this.er9DiscretionaryData = currentLine.substring(76,78);      // 77-78    2   blanks
        this.er10AddendaRecordInd = currentLine.substring(78,79);      // 79-79    1   numeric
        this.er11TraceNum = currentLine.substring(79,94);                 // 80-94    15  numeric
        this.erLineNumber = lineNum;
        this.erErrors = new boolean[11];
        
    }

    @Override
	public String toString() {
		return "EntryRecord [er1RecordTypeCode=" + er1RecordTypeCode + ", er2TransactionCode=" + er2TransactionCode
				+ ", er3RoutingNumReceivingInst=" + er3RoutingNumReceivingInst + ", er4RoutingNumCheckDigit="
				+ er4RoutingNumCheckDigit + ", er5ReceivingInstAcctNum=" + er5ReceivingInstAcctNum
				+ ", er6DollarAmount=" + er6DollarAmount + ", er7ReceiverIDNum=" + er7ReceiverIDNum
				+ ", er8ReceiverName=" + er8ReceiverName + ", er9DiscretionaryData=" + er9DiscretionaryData
				+ ", er10AddendaRecordInd=" + er10AddendaRecordInd + ", er11TraceNum=" + er11TraceNum
				+ ", erLineNumber=" + erLineNumber + ", erErrors=" + Arrays.toString(erErrors) + "]";
	}

	public String getRecordTypeCode() { return er1RecordTypeCode; }
    public String getTransactionCode() { return er2TransactionCode; }
    public String getRoutingNumReceivingInst() { return er3RoutingNumReceivingInst; }
    public String getRoutingNumCheckDigit() { return er4RoutingNumCheckDigit; }
    public String getReceivingInstAcctNum() { return er5ReceivingInstAcctNum; }
    public String getDollarAmount() { return er6DollarAmount; }
    public String getReceiverIDNum() { return er7ReceiverIDNum; }
    public String getReceiverName() { return er8ReceiverName; }
    public String getDiscretionaryData() { return er9DiscretionaryData; }
    public String getAddendaRecordInd() { return er10AddendaRecordInd; }
    public String getTraceNum() { return er11TraceNum; }


}
