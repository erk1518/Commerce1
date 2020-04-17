package com.commerce.ach;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class ACH3 {

    protected ArrayList<String> fileCopy;
    protected String fileHeader;
    protected String batchHeader;
    protected String batchControl;
    protected String fileControl;

    //HEADER RECORD - HR
    protected String fh1RecordTypeCode;         // 01-01    1   '1'
    protected String fh2PriorityCode;           // 02-03    2   '01'
    protected String fh3ImmediateDest;          // 04-13    10  '_101000019'
    protected String fh4ImmediateOrigin;        // 14-23    10  _nnnnnnnnn
    protected String fh5FileCreationDate;       // 24-29    6   YYMMDD
    protected String fh6FileCreationTime;       // 30-33    4   HHMM
    protected String fh7FileIDModifier;         // 34-34    1   alphameric
    protected String fh8RecordSize;             // 35-37    3   '094'
    protected String fh9BlockingFactor;         // 38-39    2   '10'
    protected String fh10FormatCode;            // 40-40    1   '1'
    protected String fh11ImmediateDestName;     // 41-63    23  'Commerce Bank'
    protected String fh12ImmediateOriginName;   // 64-86    23  alphameric
    protected String fh13ReferenceCode;         // 87-94    8   alphameric

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

    //FILE CONTROL - FC
    protected String fc1RecordTypeCode;           // 01-01    1   '9'
    protected String fc2BatchCount;               // 02-07    6   numeric
    protected String fc3BlockCount;               // 08-13    6   numeric
    protected String fc4EntryCount;               // 14-21    8   numeric
    protected String fc5EntryHash;                // 22-31    10  numeric
    protected String fc6TotalDebitEntry;          // 32-43    12  $$$$$$$$$cc
    protected String fc7TotalCreditEntry;         // 44-55    12  $$$$$$$$$cc
    protected String fc8Reserved;                 // 56-94    39  blanks

    //999 Block Padding
    protected ArrayList<String> block999;

    public ACH3(){}

    public ACH3(File newFile){

        try { buildACH3(newFile); }
        catch (Exception ex) { System.out.println("ACH FileNotFoundException thrown: " + ex); }

        //File Header
        this.fh1RecordTypeCode = this.fileHeader.substring(0, 1);           // 01-01    1   '1'
        this.fh2PriorityCode = this.fileHeader.substring(1, 3);             // 02-03    2   '01'
        this.fh3ImmediateDest = this.fileHeader.substring(3, 13);           // 04-13    10  '_101000019'
        this.fh4ImmediateOrigin = this.fileHeader.substring(13, 23);        // 14-23    10  _nnnnnnnnn
        this.fh5FileCreationDate = this.fileHeader.substring(23, 29);       // 24-29    6   YYMMDD
        this.fh6FileCreationTime = this.fileHeader.substring(29, 33);       // 30-33    4   HHMM
        this.fh7FileIDModifier = this.fileHeader.substring(33, 34);         // 34-34    1   alphameric
        this.fh8RecordSize = this.fileHeader.substring(34, 37);             // 35-37    3   '094'
        this.fh9BlockingFactor = this.fileHeader.substring(37, 39);         // 38-39    2   '10
        this.fh10FormatCode = this.fileHeader.substring(39, 40);            // 40-40    1   '1'
        this.fh11ImmediateDestName = this.fileHeader.substring(40, 63);     // 41-63    23  'Commerce Bank'
        this.fh12ImmediateOriginName = this.fileHeader.substring(63, 86);   // 64-86    23  alphameric
        this.fh13ReferenceCode = this.fileHeader.substring(86);         // 87-94    8   alphameric

        //Batch Header
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

        //Batch Control
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

        //File Record
        this.fc1RecordTypeCode = this.fileControl.substring(0, 1);           // 01-01    1   '9'
        this.fc2BatchCount = this.fileControl.substring(1, 7);               // 02-07    6   numeric
        this.fc3BlockCount = this.fileControl.substring(7, 13);               // 08-13    6   numeric
        this.fc4EntryCount = this.fileControl.substring(13, 21);               // 14-21    8   numeric
        this.fc5EntryHash = this.fileControl.substring(21, 31);                // 22-31    10  numeric
        this.fc6TotalDebitEntry = this.fileControl.substring(31, 43);          // 32-43    12  $$$$$$$$$cc
        this.fc7TotalCreditEntry = this.fileControl.substring(43, 55);         // 44-55    12  $$$$$$$$$cc
        this.fc8Reserved = this.fileControl.substring(55);                 // 56-94    39  blanks
    }

    public String getFileHeader() {
        return fileHeader;
    }
    public String getFileControl() {
        return fileControl;
    }
    public String getBatchHeader() { return batchHeader; }
    public String getBatchControl() { return batchControl; }

    //File Header (FH) getters
    public String getFHRecordTypeCode() {
        return fh1RecordTypeCode;
    }
    public String getPriorityCode() {
        return fh2PriorityCode;
    }
    public String getImmediateDest() {
        return fh3ImmediateDest;
    }
    public String getImmediateOrigin() {
        return fh4ImmediateOrigin;
    }
    public String getFileCreationDate() {
        return fh5FileCreationDate;
    }
    public String getFileCreationTime() {
        return fh6FileCreationTime;
    }
    public String getFileIDModifier() {
        return fh7FileIDModifier;
    }
    public String getRecordSize() {
        return fh8RecordSize;
    }
    public String getBlockingFactor() {
        return fh9BlockingFactor;
    }
    public String getFormatCode() {
        return fh10FormatCode;
    }
    public String getImmediateDestName() {
        return fh11ImmediateDestName;
    }
    public String getImmediateOriginName() {
        return fh12ImmediateOriginName;
    }
    public String getReferenceCode() {
        return fh13ReferenceCode;
    }

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
    public String getBCRecordTypeCode() { return bc1RecordTypeCode; }
    public String getBCServiceClassCode() { return bc2ServiceClassCode; }
    public String getEntryCount() { return bc3EntryCount; }
    public String getEntryHash() { return bc4EntryHash; }
    public String getTotalDebitEntry() { return bc5TotalDebitEntry; }
    public String getTotalCreditEntry() { return bc6TotalCreditEntry; }
    public String getCompanyID() { return bc7CompanyID; }
    public String getMessageAuthCode() { return bc8MessageAuthCode; }
    public String getBCReserved() { return bc9Reserved; }
    public String getBCOriginatingDFIID() { return bc10OriginatingDFIID; }
    public String getBatchNumber() { return bc11BatchNumber; }

    //File Control (FC) getters
    public String getFCRecordTypeCode() { return fc1RecordTypeCode; }
    public String getBatchCount() { return fc2BatchCount; }
    public String getBlockCount() { return fc3BlockCount; }
    public String getFCEntryCount() { return fc4EntryCount; }
    public String getFCEntryHash() { return fc5EntryHash; }
    public String getFCTotalDebitEntry() { return fc6TotalDebitEntry; }
    public String getFCTotalCreditEntry() { return fc7TotalCreditEntry; }
    public String getFCReserved() { return fc8Reserved; }


    public void buildACH3(File newFile) throws FileNotFoundException {

        Scanner input = new Scanner(newFile);
        String temp = "";
        ArrayList<String> newCopyFile = new ArrayList<String>();
        ArrayList<EntryRecord> newEntries = new ArrayList<EntryRecord>();
        ArrayList<AddendaRecord> newAddendas = new ArrayList<AddendaRecord>();
        ArrayList<String> newBlock999 = new ArrayList<String>();

        while (input.hasNext()) {
            temp = input.nextLine();
            String firstChar = temp.substring(0, 1);

            newCopyFile.add(temp);

            //print flag for debugging
//            System.out.println("temp line: " + lineCounter +
//                    "\tchar count: " + temp.length() +
//                    "\ntemp line value: " + temp);

            if(firstChar.equals("9")) {
                if(temp.substring(0, 5).equals("99999"))
                    newBlock999.add(temp);
                else
                    this.fileControl = temp;
            } else if (firstChar.equals("1")) {
                this.fileHeader = temp;
            } else if (firstChar.equals("5")) {
                this.batchHeader = temp;
            } else if (firstChar.equals("6")) {
                newEntries.add(new EntryRecord(temp));
            } else if (firstChar.equals("7")) {
                newAddendas.add(new AddendaRecord(temp));
            } else if (firstChar.equals("8")) {
                this.batchControl = temp;
            } else { System.out.println("Error handling file line. First character not recognized.");
            }

        }//end while

        input.close();
        this.fileCopy = newCopyFile;
        this.entries = newEntries;
        this.addenda = newAddendas;
        this.block999 = newBlock999;

    }// end buildACH3


    public String toString() {
        return getFHRecordTypeCode() + getPriorityCode() + getImmediateDest() + getImmediateOrigin()
                + getFileCreationDate() + getFileCreationTime() + getFileIDModifier()
                + getRecordSize() + getBlockingFactor() + getFormatCode() + getImmediateDestName()
                + getImmediateOriginName() + getReferenceCode() + "\n"

                + getBHRecordTypeCode() + getServiceClassCode() + getCompanyName()
                + getCompanyDiscrData() + getCompanyIdentification() + getStandardEntryClassCode()
                + getCompanyEntryDescr() + getCompanyDescrDate() + getEffectiveEntryDate()
                + getSettlementDate() + getOriginatorStatusCode() + getOriginatingDFIID()
                + getBatchNum() + "\n"

                + printEntryRecords(entries)

                + printAddendaRecords(addenda)

                + getBCRecordTypeCode() + getBCServiceClassCode() + getEntryCount()
                + getEntryHash() + getTotalDebitEntry() + getTotalCreditEntry()
                + getCompanyID() + getMessageAuthCode() + getBCReserved()
                + getBCOriginatingDFIID() + getBatchNumber() + "\n"

                + getFCRecordTypeCode() + getFCRecordTypeCode() + getBatchCount() + getBlockCount()
                + getFCEntryCount() + getFCEntryHash() + getFCTotalDebitEntry() + getFCTotalCreditEntry()
                + getFCReserved() + "\n"

                + printBlock999(block999);
    }

    public String printEntryRecords(ArrayList<EntryRecord> records) {
        StringBuilder temp = new StringBuilder();
        for (EntryRecord record : records) temp.append(record.toString());
        return temp.toString();
    }

    public String printAddendaRecords(ArrayList<AddendaRecord> records) {
        StringBuilder temp = new StringBuilder();
        for (AddendaRecord record : records) temp.append(record.toString());
        return temp.toString();
    }

    public String printBlock999(ArrayList<String> lines) {
        StringBuilder temp1 = new StringBuilder();
        for (String line : lines) temp1.append(line).append("\n");
        return temp1.toString();
    }

}
