package com.commerce.ach.validation;

import java.util.ArrayList;
import java.util.Arrays;

public class Batch {
	
    //BATCH HEADER - BH
    protected char bh1RecordTypeCode;           // 01-01    1   '5'
    protected String bh2ServiceClassCode;         // 02-04    3   numeric
    protected String bh3CompanyName;              // 05-20    16  String
    protected String bh4CompanyDiscrData;         // 21-40    20  alphameric
    protected String bh5CompanyIdentification;    // 41-50    10  Xnnnnnnnnn
    protected String bh6StandardEntryClassCode;   // 51-53    3   alphameric
    protected String bh7CompanyEntryDescr;        // 54-63    10  alphameric
    protected String bh8CompanyDescrDate;         // 64-69    6   alphameric
    protected String bh9EffectiveEntryDate;       // 70-75    6   YYMMDD
    protected String bh10SettlementDate;          // 76-78    3   blanks
    protected char bh11OriginatorStatusCode;    // 79-79    1   '1'
    protected String bh12OriginatingDFIID;        // 80-87    8   blanks
    protected String bh13BatchNum;                // 88-94    7   numeric
    protected int bhLineNumber;
    protected boolean[] bhErrors;

    //ENTRY RECORDS - ER
    protected ArrayList<EntryRecord> entries;

    //ADDENDA RECORDS - AR
    protected ArrayList<AddendaRecord> addendas;

    //BATCH CONTROL - BC
    protected char bc1RecordTypeCode;           // 01-01    1   '8'
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
    protected int bcLineNumber;
    protected boolean[] bcErrors;
    
    protected Batch() {
//TODO: Errors booleans
    	entries = new ArrayList<EntryRecord>();
    	addendas = new ArrayList<AddendaRecord>();
    	bhErrors = new boolean[12];
    	bcErrors = new boolean[10];
    }
    
    protected void setBatchHeader(String currentLine, int lineNum) {
    	
		this.bh1RecordTypeCode = currentLine.charAt(0);
		this.bh2ServiceClassCode = currentLine.substring(1,4);	
		this.bh3CompanyName = currentLine.substring(4,20);
		this.bh4CompanyDiscrData = currentLine.substring(20,40); 
		this.bh5CompanyIdentification = currentLine.substring(40,50);
		this.bh6StandardEntryClassCode = currentLine.substring(50,53);
		this.bh7CompanyEntryDescr = currentLine.substring(53,63);
		this.bh8CompanyDescrDate = currentLine.substring(63,69);
		this.bh9EffectiveEntryDate = currentLine.substring(69,75);
		this.bh10SettlementDate = currentLine.substring(75,78);
		this.bh11OriginatorStatusCode = currentLine.charAt(78);
		this.bh12OriginatingDFIID = currentLine.substring(79,87);
		this.bh13BatchNum = currentLine.substring(87,94);
		this.bhLineNumber = lineNum;
		
    }
    
    protected void setBatchControl(String currentLine, int lineNum) {
    	
		this.bc1RecordTypeCode = currentLine.charAt(0);
		this.bc2ServiceClassCode = currentLine.substring(1,4);
		this.bc3EntryCount = currentLine.substring(4,10);
		this.bc4EntryHash = currentLine.substring(10,20);
		this.bc5TotalDebitEntry = currentLine.substring(20,32);
		this.bc6TotalCreditEntry = currentLine.substring(32,44);
		this.bc7CompanyID = currentLine.substring(44,54);
		this.bc8MessageAuthCode = currentLine.substring(54,73);
		this.bc9Reserved = currentLine.substring(73,79);
		this.bc10OriginatingDFIID = currentLine.substring(79,87);
		this.bc11BatchNumber = currentLine.substring(87,94);
		this.bcLineNumber = lineNum;
		
    }
    
    protected void addEntry(String currentLine, int lineNum) {
    	entries.add(new EntryRecord(currentLine, lineNum));
    }
    
    protected void addAddenda(String currentLine, int lineNum) {
    	addendas.add(new AddendaRecord(currentLine, lineNum));
    }
    
	public EntryRecord getEntry(int index) {
		return entries.get(index);
	}

	public AddendaRecord getAddenda(int index) {
		return addendas.get(index);
	}

	protected String entriesToString() {
		String theReturn = "";
		for(EntryRecord e : entries) {
			theReturn +=
				"\n\n\t\t\t\terLineNumber= " + e.erLineNumber + ", erErrors=" + Arrays.toString(e.erErrors)
				+ "\n\t\t\t\ter1RecordTypeCode=" + e.er1RecordTypeCode + ", er2TransactionCode=" + e.er2TransactionCode
				+ ", er3RoutingNumReceivingInst=" + e.er3RoutingNumReceivingInst + ", er4RoutingNumCheckDigit="
				+ e.er4RoutingNumCheckDigit + ", er5ReceivingInstAcctNum=" + e.er5ReceivingInstAcctNum
				+ ", er6DollarAmount=" + e.er6DollarAmount + ", er7ReceiverIDNum=" + e.er7ReceiverIDNum
				+ ", er8ReceiverName=" + e.er8ReceiverName + ", er9DiscretionaryData=" + e.er9DiscretionaryData
				+ ", er10AddendaRecordInd=" + e.er10AddendaRecordInd + ", er11TraceNum=" + e.er11TraceNum;
		}
		return theReturn;
	}
	
 	public char getBh1RecordTypeCode() {
		return bh1RecordTypeCode;
	}
	public String getBh2ServiceClassCode() {
		return bh2ServiceClassCode;
	}
	public String getBh3CompanyName() {
		return bh3CompanyName;
	}
	public String getBh4CompanyDiscrData() {
		return bh4CompanyDiscrData;
	}
	public String getBh5CompanyIdentification() {
		return bh5CompanyIdentification;
	}
	public String getBh6StandardEntryClassCode() {
		return bh6StandardEntryClassCode;
	}
	public String getBh7CompanyEntryDescr() {
		return bh7CompanyEntryDescr;
	}
	public String getBh8CompanyDescrDate() {
		return bh8CompanyDescrDate;
	}
	public String getBh9EffectiveEntryDate() {
		return bh9EffectiveEntryDate;
	}
	public String getBh10SettlementDate() {
		return bh10SettlementDate;
	}
	public char getBh11OriginatorStatusCode() {
		return bh11OriginatorStatusCode;
	}
	public String getBh12OriginatingDFIID() {
		return bh12OriginatingDFIID;
	}
	public String getBh13BatchNum() {
		return bh13BatchNum;
	}


	public int getBhLineNumber() {
		return bhLineNumber;
	}
	public boolean[] getBhErrors() {
		return bhErrors;
	}
	public char getBc1RecordTypeCode() {
		return bc1RecordTypeCode;
	}
	public String getBc2ServiceClassCode() {
		return bc2ServiceClassCode;
	}
	public String getBc3EntryCount() {
		return bc3EntryCount;
	}
	public String getBc4EntryHash() {
		return bc4EntryHash;
	}
	public String getBc5TotalDebitEntry() {
		return bc5TotalDebitEntry;
	}
	public String getBc6TotalCreditEntry() {
		return bc6TotalCreditEntry;
	}
	public String getBc7CompanyID() {
		return bc7CompanyID;
	}
	public String getBc8MessageAuthCode() {
		return bc8MessageAuthCode;
	}
	public String getBc9Reserved() {
		return bc9Reserved;
	}
	public String getBc10OriginatingDFIID() {
		return bc10OriginatingDFIID;
	}
	public String getBc11BatchNumber() {
		return bc11BatchNumber;
	}
	public int getBcLineNumber() {
		return bcLineNumber;
	}
	public boolean[] getBcErrors() {
		return bcErrors;
	}
    
}
