package com.commerce.ach;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class ACH2 {

    protected String[] fileCopy;

    public ACH2() {}

    public ACH2(File newFile) throws FileNotFoundException {

        try { buildACH2(newFile); }
        catch (Exception ex) { System.out.println("ACH FileNotFoundException thrown: " + ex); }

    }


    public String toString() {
        String tempLine = "";
        for(int i = 0; i < this.fileCopy.length; i++)
            if(this.fileCopy[i] != null)
                tempLine = this.fileCopy[i] + "\n";
        return tempLine;
    }


    public void buildACH2(File tempFile) throws FileNotFoundException {

        Scanner input = new Scanner(tempFile);
        String temp = "";
        int lineCounter = 0;
        String[] tempFileCopy = new String[10];
        while (input.hasNext()) {
            temp = input.nextLine();

            lineCounter++;
            if(lineCounter > tempFileCopy.length)
                tempFileCopy = expandArray(tempFileCopy);
            tempFileCopy[lineCounter - 1] = temp;
            this.fileCopy = tempFileCopy;

            //print markers for debugging
            System.out.println("temp line: " + lineCounter +
                    "\tchar count: " + temp.length() +
                    "\ntemp line value: " + temp);
        }
        input.close();

    }


    public String[] expandArray(String[] array){
        int currentSize = array.length;
        int addTen = currentSize + 10;
        String[] newArray = new String[addTen];
        for(int j = 0; j < currentSize; j++)
            newArray[j] = array[j];
        return newArray;
    }
}
