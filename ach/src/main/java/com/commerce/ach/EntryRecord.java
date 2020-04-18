package com.commerce.ach;

public class EntryRecord extends Batch{

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

    public EntryRecord(String line) {
        this.er1RecordTypeCode = line.substring(0,1);           // 01-01    1   '6'
        this.er2TransactionCode = line.substring(1,3);          // 02-03    2   numeric
        this.er3RoutingNumReceivingInst = line.substring(3,11); // 04-11    8   numeric
        this.er4RoutingNumCheckDigit = line.substring(11,12);   // 12-12    1   numeric
        this.er5ReceivingInstAcctNum = line.substring(12,29);   // 13-29    17  alphameric
        this.er6DollarAmount = line.substring(29,39);           // 30-39    10  $$$$$$$$cc
        this.er7ReceiverIDNum = line.substring(39,54);          // 40-54    15  alphameric
        this.er8ReceiverName = line.substring(54,76);           // 55-76    22  alphameric
        this.er9DiscretionaryData = line.substring(76,78);      // 77-78    2   blanks
        this.er10AddendaRecordInd = line.substring(78,79);      // 79-79    1   numeric
        this.er11TraceNum = line.substring(79);                 // 80-94    15  numeric
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

    /*
    public void setTransactionCode(String line) { er2TransactionCode = line; }
    public void setRecordTypeCode(String line) { er1RecordTypeCode = line; }
    public void setRoutingNumReceivingInst(String line) { er3RoutingNumReceivingInst = line; }
    public void setRoutingNumCheckDigit(String line) { er4RoutingNumCheckDigit = line; }
    public void setReceivingInstAcctNum(String line) { er5ReceivingInstAcctNum = line; }
    public void setDollarAmount(String line) { er6DollarAmount = line; }
    public void setReceiverIDNum(String line) { er7ReceiverIDNum = line; }
    public void setReceiverName(String line) { er8ReceiverName = line; }
    public void setDiscretionaryData(String line) { er9DiscretionaryData = line; }
    public void setAddendaRecordInd(String line) { er10AddendaRecordInd = line; }
    public void setTraceNum(String line) { er11TraceNum = line; }
    */


    public String toString() {
        return getRecordTypeCode() +
                getTransactionCode() +
                getRoutingNumReceivingInst() +
                getRoutingNumCheckDigit() +
                getReceivingInstAcctNum() +
                getDollarAmount() +
                getReceiverIDNum() +
                getReceiverName() +
                getDiscretionaryData() +
                getAddendaRecordInd() +
                getTraceNum() + "\n";
    }
}
