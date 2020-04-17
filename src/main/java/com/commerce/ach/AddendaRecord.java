package com.commerce.ach;

public class AddendaRecord extends Batch{

    //ENTRY DETAIL ADDENDA RECORDS - AR
    protected String ar1RecordTypeCode;           // 01-01    1   '7'
    protected String ar2AddendaTypeCode;          // 02-03    2   '05'
    protected String ar3PaymentRelatedInfo;       // 04-83    80  alphameric
    protected String ar4AddendaSequenceNum;       // 84-87    4   numeric
    protected String ar5EntryDetailSeqNum;        // 88-94    7   numeric

    protected AddendaRecord(String lines) {

        this.ar1RecordTypeCode = lines.substring(0,1);
        this.ar2AddendaTypeCode = lines.substring(1,3);
        this.ar3PaymentRelatedInfo = lines.substring(3,83);
        this.ar4AddendaSequenceNum = lines.substring(83,87);
        this.ar5EntryDetailSeqNum = lines.substring(87);
    }

    public String getRecordTypeCode() { return ar1RecordTypeCode; }
    public String getAddendaTypeCode() { return ar2AddendaTypeCode; }
    public String getPaymentRelatedInfo() { return ar3PaymentRelatedInfo; }
    public String getAddendaSequenceNum() { return ar4AddendaSequenceNum; }
    public String getEntryDetailSeqNum() { return ar5EntryDetailSeqNum; }

    /*
    public void setAddendaTypeCode(String line) { ar2AddendaTypeCode = line; }
    public void setRecordTypeCode(String line) { ar1RecordTypeCode = line; }
    public void setPaymentRelatedInfo(String line) { ar3PaymentRelatedInfo = line; }
    public void setAddendaSequenceNum(String line) { ar4AddendaSequenceNum = line; }
    public void setEntryDetailSeqNum(String line) { ar5EntryDetailSeqNum = line; }
    */


    @Override
    public String toString() {
        return getRecordTypeCode() +
                getAddendaTypeCode() +
                getPaymentRelatedInfo() +
                getAddendaSequenceNum() +
                getEntryDetailSeqNum() + "\n";
    }
}
