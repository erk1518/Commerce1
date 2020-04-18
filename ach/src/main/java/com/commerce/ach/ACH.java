package com.commerce.ach;
/*
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class ACH {

    protected ArrayList<String> fileCopy;
    protected String fileHeader;
    protected ArrayList<Batch> batches;
    protected String fileControl;
    protected ArrayList<String> block999;

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

    //FILE CONTROL - FC
    protected String fc1RecordTypeCode;           // 01-01    1   '9'
    protected String fc2BatchCount;               // 02-07    6   numeric
    protected String fc3BlockCount;               // 08-13    6   numeric
    protected String fc4EntryCount;               // 14-21    8   numeric
    protected String fc5EntryHash;                // 22-31    10  numeric
    protected String fc6TotalDebitEntry;          // 32-43    12  $$$$$$$$$cc
    protected String fc7TotalCreditEntry;         // 44-55    12  $$$$$$$$$cc
    protected String fc8Reserved;                 // 56-94    39  blanks


    public ACH() {
    }

    public ACH(File newFile) {

        try { buildACH(newFile); }
        catch (Exception ex) { System.out.println("ACH FileNotFoundException thrown: " + ex); }

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
    public String getBatchDetail() {
        return batchDetail;
    }
    public String getFileControl() {
        return fileControl;
    }
    public String getBlock999_1() {
        return block999_1;
    }
    public String getBlock999_2() {
        return block999_2;
    }
    public String getBlock999_3() {
        return block999_3;
    }
    public String getBlock999_4() {
        return block999_4;
    }
    public String getBlock999_5() {
        return block999_5;
    }
    public String getBlock999_6() {
        return block999_6;
    }
    public String getBlock999_7() {
        return block999_7;
    }
    public String getBlock999_8() {
        return block999_8;
    }
    public String getBlock999_9() {
        return block999_9;
    }

    //Header Record (HR) getters
    public String getRecordTypeCode() {
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

    //File Control (FC) getters
    public String getFCRecordTypeCode() { return fc1RecordTypeCode; }
    public String getBatchCount() { return fc2BatchCount; }
    public String getBlockCount() { return fc3BlockCount; }
    public String getFCEntryCount() { return fc4EntryCount; }
    public String getFCEntryHash() { return fc5EntryHash; }
    public String getFCTotalDebitEntry() { return fc6TotalDebitEntry; }
    public String getFCTotalCreditEntry() { return fc7TotalCreditEntry; }
    public String getFCReserved() { return fc8Reserved; }

    public String toString() {
        return getRecordTypeCode() + getPriorityCode() + getImmediateDest() + getImmediateOrigin()
                + getFileCreationDate() + getFileCreationTime() + getFileIDModifier()
                + getRecordSize() + getBlockingFactor() + getFormatCode() + getImmediateDestName()
                + getImmediateOriginName() + getReferenceCode() + "\n"

                + printBatches(batches)

                + getFCRecordTypeCode() + getFCRecordTypeCode() + getBatchCount() + getBlockCount()
                + getFCEntryCount() + getFCEntryHash() + getFCTotalDebitEntry() + getFCTotalCreditEntry()
                + getFCReserved() + "\n"

                + printBlock999();
    }


    public String printBatches(ArrayList<Batch> printBatch) {
//        System.out.println("start printBatches from ACH2");
        String temp = "";
        int maxBatches = printBatch.size();
        for (int i = 0; i < maxBatches; i++) {
            temp += printBatch.get(i).toString() + "\n";
            //System.out.println(printBatch.get(i));
        }
//        System.out.println("end printBatches from ACH2");
        return temp;
    }


    public String printBlock999() {
        String temp1 = "";
        if (!getBlock999_1().isEmpty())
            temp1 += getBlock999_1() + "\n";
        if (!getBlock999_2().isEmpty())
            temp1 += getBlock999_2() + "\n";
        if (!getBlock999_3().isEmpty())
            temp1 += getBlock999_3() + "\n";
        if (!getBlock999_4().isEmpty())
            temp1 += getBlock999_4() + "\n";
        if (!getBlock999_5().isEmpty())
            temp1 += getBlock999_5() + "\n";
        if (!getBlock999_6().isEmpty())
            temp1 += getBlock999_6() + "\n";
        if (!getBlock999_7().isEmpty())
            temp1 += getBlock999_7() + "\n";
        if (!getBlock999_8().isEmpty())
            temp1 += getBlock999_8() + "\n";
        if (!getBlock999_9().isEmpty())
            temp1 += getBlock999_9() + "\n";
        return temp1;
    }


    public String[] expandArray(String[] array){
        int currentSize = array.length;
        int addTen = currentSize + 10;
        String[] newArray = new String[addTen];
        for(int j = 0; j < currentSize; j++)
            newArray[j] = array[j];
        return newArray;
    }


    public void buildACH(File file) throws FileNotFoundException {

        this.block999_1 = "";
        this.block999_2 = "";
        this.block999_3 = "";
        this.block999_4 = "";
        this.block999_5 = "";
        this.block999_6 = "";
        this.block999_7 = "";
        this.block999_8 = "";
        this.block999_9 = "";

        try {
            Scanner input = new Scanner(file);
            String temp = "";
            int lineCounter = 0;
            int count999 = 0;
            int batchInProgress = 0;     // 0=No, 1=Yes
            ArrayList<Batch> newBatches = new ArrayList<Batch>();
            String[] tempFileCopy = new String[10];
            File multiBatch = new File("C:\\Users\\grega\\SrProj\\readFolder\\.camel\\.temp\\BatchHelper.txt");
            PrintWriter output = new PrintWriter(multiBatch);

            //iterate through lines in file
            while (input.hasNext()) {
                temp = input.nextLine();
                String firstChar = temp.substring(0, 1);

                //place file line-by-line into fileCopy[]
                lineCounter++;
                if(lineCounter > tempFileCopy.length)
                    tempFileCopy = expandArray(tempFileCopy);
                tempFileCopy[lineCounter - 1] = temp;
                this.fileCopy = tempFileCopy;

                //print markers for debugging
                System.out.println("temp line value: " + temp);
                System.out.println("temp line: " + lineCounter + "\tsubstring(0, 1): " + firstChar + "\tchar count: " + temp.length());

                if (firstChar.equals("9")) {
                    System.out.println("temp line " + lineCounter + ": temp.substring(0, 5)" + temp.substring(0, 5));
                    if (temp.substring(0, 5).equals("99999") || temp.substring(temp.length() - 5).equals("99999")) {
                        count999++;
                        switch (count999) {
                            case (1):
                                this.block999_1 = temp; break;
                            case (2):
                                this.block999_2 = temp; break;
                            case (3):
                                this.block999_3 = temp; break;
                            case (4):
                                this.block999_4 = temp; break;
                            case (5):
                                this.block999_5 = temp; break;
                            case (6):
                                this.block999_6 = temp; break;
                            case (7):
                                this.block999_7 = temp; break;
                            case (8):
                                this.block999_8 = temp; break;
                            case (9):
                                this.block999_9 = temp; break;
                        }
                    } else {
                        this.fileControl = temp;
                    }
                } else if (firstChar.equals("1")) {
                    this.fileHeader = temp;
                } else if (firstChar.equals("5")) {
                    if (multiBatch.exists()) {
                        System.out.println("batch file exists");
                        output.println(temp + "\n");
                    } else {
                        System.out.println("batch file not found[5]");
                    }
                    batchInProgress++;
                    this.batchDetail += temp;
                } else if (firstChar.equals("6")) {
                    if (multiBatch.exists()) {
                        output.println(temp + "\n");
                    } else {
                        System.out.println("batch file not found[6]");
                    }
                    this.batchDetail += temp;
                } else if (firstChar.equals("7")) {
                    if (multiBatch.exists()) {
                        output.println(temp + "\n");
                    } else {
                        System.out.println("batch file not found[7]");
                    }
                    this.batchDetail += temp;
                } else if (firstChar.equals("8")) {
                    if (multiBatch.exists()) {
                        output.println(temp + "\n");
                        newBatches.add(new Batch(multiBatch));
                    } else {
                        System.out.println("batch file not found[8]");
                    }
                    batchInProgress--;
                    this.batchDetail += temp;
                } else {
                    System.out.println("Error handling file line. First character not recognized.");
                }
            }
            this.batches = newBatches;
            // clear File contents, or delete file

            input.close();
            output.close();
        }
        catch(Exception ex){ System.out.println("Exception in buildACH: " + ex); }
    }

}
*/
