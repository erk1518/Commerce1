package com.commerce.ach;

import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

public class Batch {

    protected String batchHeader;
    protected String batchControl;

    //BATCH HEADER - BH
    protected String bh1RecordTypeCode;           // 01-01    1   '5'
    protected String bh2ServiceClassCode;         // 02-04    3   numeric
    protected String bh3CompanyName;              // 05-20    16  String
    protected String bh4CompanyDiscrData;         // 21-40    20  alphameric
    protected String bh5CompanyIdentification;    // 41-50    10  Xnnnnnnnnn
    protected String bh6StandardEntryClassCode;   // 51-53    3   alphameric
    protected String bh7CompanyEntryDescr;        // 54-63    10  alphameric
    protected String bh8CompanyDescrDate;         // 64-69    6   alphameric
    protected String bh9EffectiveEntryDate;       // 70-75    6   YYMMDD
    protected String bh10SettlementDate;          // 76-78    3   blanks
    protected String bh11OriginatorStatusCode;    // 79-79    1   '1'
    protected String bh12OriginatingDFIID;        // 80-87    8   blanks
    protected String bh13BatchNum;                // 88-94    7   numeric

    //ENTRY RECORDS - ER
    protected ArrayList<EntryRecord> entries;

    //ADDENDA RECORDS - AR
    protected ArrayList<AddendaRecord> addenda;

    //BATCH CONTROL - BC
    protected String bc1RecordTypeCode;           // 01-01    1   '8'
    protected String bc2ServiceClassCode;         // 02-04    3   numeric
    protected String bc3EntryCount;               // 05-10    6   numeric
    protected String bc4EntryHash;                // 11-20    10  numeric
    protected String bc5TotalDebitEntry;          // 21-32    12  $$$$$$$$$cc
    protected String bc6TotalCreditEntry;         // 33-44    12  $$$$$$$$$cc
    protected String bc7CompanyID;                // 45-54    10  Xnnnnnnnnn
    protected String bc8MessageAuthCode;          // 55-73    19  alphameric
    protected String bc9Reserved;                 // 74-79    6   blanks
    protected String bc10OriginatingDFIID;        // 80-87    8   blanks
    protected String bc11BatchNumber;             // 88-94    7   numeric

    public Batch() {}

    public Batch(File file) {

        buildBatch(file);

        this.bh1RecordTypeCode = this.batchHeader.substring(0,1);            // 01-01    1   '5'
        this.bh2ServiceClassCode = this.batchHeader.substring(1,4);          // 02-04    3   numeric
        this.bh3CompanyName = this.batchHeader.substring(4,20);              // 05-20    16  String
        this.bh4CompanyDiscrData = this.batchHeader.substring(20,40);        // 21-40    20  alphameric
        this.bh5CompanyIdentification = this.batchHeader.substring(40,50);   // 41-50    10  Xnnnnnnnnn
        this.bh6StandardEntryClassCode = this.batchHeader.substring(50,53);  // 51-53    3   alphameric
        this.bh7CompanyEntryDescr = this.batchHeader.substring(53,63);       // 54-63    10  alphameric
        this.bh8CompanyDescrDate = this.batchHeader.substring(63,69);        // 64-69    6   alphameric
        this.bh9EffectiveEntryDate = this.batchHeader.substring(69,75);      // 70-75    6   YYMMDD
        this.bh10SettlementDate = this.batchHeader.substring(75,78);         // 76-78    3   blanks
        this.bh11OriginatorStatusCode = this.batchHeader.substring(78,79);   // 79-79    1   '1'
        this.bh12OriginatingDFIID = this.batchHeader.substring(79,87);       // 80-87    8   blanks
        this.bh13BatchNum = this.batchHeader.substring(87);                  // 88-94    7   numeric

        this.bc1RecordTypeCode = this.batchControl.substring(0,1);          // 01-01    1   '08'
        this.bc2ServiceClassCode = this.batchControl.substring(1,4);        // 02-04    3   numeric
        this.bc3EntryCount = this.batchControl.substring(4,10);             // 05-10    6   numeric
        this.bc4EntryHash = this.batchControl.substring(10,20);             // 11-20    10  numeric
        this.bc5TotalDebitEntry = this.batchControl.substring(20,32);       // 21-32    12  $$$$$$$$$cc
        this.bc6TotalCreditEntry = this.batchControl.substring(32,44);      // 33-44    12  $$$$$$$$$cc
        this.bc7CompanyID = this.batchControl.substring(44,54);             // 45-54    10  Xnnnnnnnnn
        this.bc8MessageAuthCode = this.batchControl.substring(54,73);       // 55-73    19  alphameric
        this.bc9Reserved = this.batchControl.substring(73,79);              // 74-79    6   blanks
        this.bc10OriginatingDFIID = this.batchControl.substring(79,87);     // 80-87    8   blanks
        this.bc11BatchNumber = this.batchControl.substring(87);             // 88-94    7   numeric
    }

    public String getBatchHeader() { return batchHeader; }
    public String getBatchControl() { return batchControl; }

    //begin Batch Record (BR) getters
    public String getBHRecordTypeCode() { return bh1RecordTypeCode; }
    public String getServiceClassCode() { return bh2ServiceClassCode; }
    public String getCompanyName() { return bh3CompanyName; }
    public String getCompanyDiscrData() { return bh4CompanyDiscrData; }
    public String getCompanyIdentification() { return bh5CompanyIdentification; }
    public String getStandardEntryClassCode() { return bh6StandardEntryClassCode; }
    public String getCompanyEntryDescr() { return bh7CompanyEntryDescr; }
    public String getCompanyDescrDate() { return bh8CompanyDescrDate; }
    public String getEffectiveEntryDate() { return bh9EffectiveEntryDate; }
    public String getSettlementDate() { return bh10SettlementDate; }
    public String getOriginatorStatusCode() { return bh11OriginatorStatusCode; }
    public String getOriginatingDFIID() { return bh12OriginatingDFIID; }
    public String getBatchNum() { return bh13BatchNum; }

    //begin Batch Control (BC) getters
    public String getRecordTypeCode() { return bc1RecordTypeCode; }
    public String getBCServiceClassCode() { return bc2ServiceClassCode; }
    public String getEntryCount() { return bc3EntryCount; }
    public String getEntryHash() { return bc4EntryHash; }
    public String getTotalDebitEntry() { return bc5TotalDebitEntry; }
    public String getTotalCreditEntry() { return bc6TotalCreditEntry; }
    public String getCompanyID() { return bc7CompanyID; }
    public String getMessageAuthCode() { return bc8MessageAuthCode; }
    public String getReserved() { return bc9Reserved; }
    public String getBCOriginatingDFIID() { return bc10OriginatingDFIID; }
    public String getBatchNumber() { return bc11BatchNumber; }


    public String toString() {
        return  getBHRecordTypeCode() + getServiceClassCode() + getCompanyName() +
                getCompanyDiscrData() + getCompanyIdentification() + getStandardEntryClassCode() +
                getCompanyEntryDescr() + getCompanyDescrDate() + getEffectiveEntryDate() +
                getSettlementDate() + getOriginatorStatusCode() + getOriginatingDFIID() +
                getBatchNum() + "\n" +

                printEntryRecords(entries) +

                printAddendaRecords(addenda) +

                getRecordTypeCode() + getBCServiceClassCode() + getEntryCount() +
                getEntryHash() + getTotalDebitEntry() + getTotalCreditEntry() +
                getCompanyID() + getMessageAuthCode() + getReserved() +
                getBCOriginatingDFIID() + getBatchNumber();
    }


    public String printEntryRecords(ArrayList<EntryRecord> records) {
//        System.out.println("start printEntryRecords from Batch2");
        StringBuilder temp = new StringBuilder();
        int count1 = records.size();
        for(int i = 0; i < count1; i++)
            temp.append(records.get(i).toString());
//        System.out.println("finish printEntryRecords from Batch2");
        return temp.toString();
    }


    public String printAddendaRecords(ArrayList<AddendaRecord> records) {
//        System.out.println("start printAddendaRecords from Batch2");
        StringBuilder temp = new StringBuilder();
        int count2 = records.size();
        for(int i = 0; i < count2; i++)
            temp.append(records.get(i).toString());
//        System.out.println("finish printAddendaRecords from Batch2");
        return temp.toString();
    }


    public void buildBatch(File newFile) {
        try {
            Scanner input = new Scanner(newFile);
            String temp = "";
            int counter = 0;
            ArrayList<EntryRecord> newEntries = new ArrayList<EntryRecord>();
            ArrayList<AddendaRecord> newAddendas = new ArrayList<AddendaRecord>();

            while (input.hasNext()) {
                temp = input.nextLine();
                String firstChar = temp.substring(0, 1);
                counter++;

//                System.out.println("temp line value: " + temp);
//                System.out.println("temp line: " + counter + "\tsubstring(0, 1): " +
//                        firstChar + "\tchar count: " + temp.length());

                if (firstChar.equals("6")) {
                    EntryRecord newEntry = new EntryRecord(temp);
                    newEntries.add(newEntry);
                } else if (firstChar.equals("7")) {
                    AddendaRecord adden = new AddendaRecord(temp);
                    newAddendas.add(adden);
                } else if (firstChar.equals("5")) {
                    this.batchHeader = temp;
                } else if (firstChar.equals("8")) {
                    this.batchControl = temp;
                } else if(firstChar.equals("9") || firstChar.equals("1")) {
                    // no action
                } else
                    System.out.println("buildBatch error handling input: " + temp);
            }
            this.entries = newEntries;
            this.addenda = newAddendas;
        }
        catch(Exception ex) { System.out.println("Exception in buildBatch: " + ex); }
    }

}

