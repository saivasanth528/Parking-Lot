package com.vasanth;

public class OutputPrinter {
    public void welcome() {
        printWithNewLine("Welcome to Parking lot");
    }

    public void end() {
        printWithNewLine("Thanks for using this parking lot service");
    }

    public  void notFound() {
        printWithNewLine("Not Found");
    }

    public void statusHeader() {
        printWithNewLine("Slot No   Registration   Colour");
    }

    public void parkingLotFUll() {
        printWithNewLine("Sorry, Parking Lot is full");
    }

    public void parkingLotEmpty() {
        printWithNewLine("Parking Lot is empty");
    }

    public void invalidFile() {
        printWithNewLine("Invalid File Given");
    }

    public void printWithNewLine(String msg) {
        System.out.println(msg);
    }
}
