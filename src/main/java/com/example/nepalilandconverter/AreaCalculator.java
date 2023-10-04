package com.example.nepalilandconverter;

import java.util.Scanner;

public class AreaCalculator {
    //Conversion from Square Feet
    void convertFromSquareFeet(double sqFeet) {
        Scanner scanner = new Scanner(System.in);
        double sqMeter, ropani, anna, paisa, daam, tempRopani, daamOutput, bigha, kattha, dhur, dhurOutput, tempBigha;
        int ropaniOutput, annaOutput, paisaOutput, bighaOutput, katthaOutput;

        //For decimal conversion
        sqMeter = sqFeet / 10.76391042;
        anna = sqFeet / 342.25;
        ropani = sqFeet / 5476;
        paisa = sqFeet / 85.56;
        daam = sqFeet / 21.39;
        bigha = sqFeet / 72900;
        kattha = sqFeet / 3645;
        dhur = sqFeet / 182.25;

        //For Nepali Output
        ropaniOutput = (int) anna / 16;
        annaOutput = (int) (anna - (ropaniOutput * 16));
        tempRopani = 4 * (anna -(16 * ropaniOutput) - annaOutput);
        paisaOutput = (int) (tempRopani);
        daamOutput = (4 * (tempRopani - paisaOutput));
        bighaOutput = (int) kattha / 20;
        tempBigha = kattha - (bighaOutput * 20);
        katthaOutput = (int) tempBigha;
        dhurOutput = 20 * (tempBigha - katthaOutput);

        //Display output
        System.out.println("The converted area in Square Feet is "+sqFeet);
        System.out.println("The converted area in Bigha is "+String.format("%.3f", bigha));
        System.out.println("The converted area in Kattha is "+String.format("%.3f", kattha));
        System.out.println("The converted area in Dhur is "+String.format("%.3f", dhur));
        System.out.print("The converted area in Bigha-Kattha-Dhur is ");
        System.out.print(bighaOutput+"-");
        System.out.print(katthaOutput+"-");
        System.out.printf("%.3f \n", dhurOutput);
        System.out.println("The converted area in Ropani is "+String.format("%.3f",ropani));
        System.out.println("The converted area in Anna "+String.format("%.3f",anna));
        System.out.println("The converted area in Paisa "+String.format("%.3f",paisa));
        System.out.println("The converted area in Daam "+String.format("%.3f",daam));
        System.out.print("The converted area in Ropani-Anna-Paisa-Daam is ");
        System.out.print(ropaniOutput+"-");
        System.out.print(annaOutput+"-");
        System.out.print(paisaOutput+"-");
        System.out.printf("%.3f \n", daamOutput);
        System.out.println("The converted area in Square Meter is "+String.format("%.3f",sqMeter));
    }


    public double convertFromSquareMeter(double inputArea) {
        return inputArea * 10.76391042;
    }

    public double convertFromRopani(double inputArea) {
        return inputArea * 5476;
    }

    public double convertFromAnna(double inputArea) {
        return inputArea * 342.25;
    }

    public double convertFromPaisa(double inputArea) {
        return inputArea * 85.5625;
    }

    public double convertFromDaam(double inputArea) {
        return  inputArea * 21.390625;
    }

    public double convertFromRAPD (int r, int a, int p, double d) {
        return (r * 5476) + (a * 342.25) + (p * 85.5625) + (d * 21.390625);
    }

    public double convertFromBigha (double inputArea) {
        return inputArea * 72900;
    }

    public double convertFromKattha (double inputArea) {
        return inputArea * 3645;
    }

    public double convertFromDhur (double inputArea) {
        return inputArea * 182.25;
    }

    public double converFromBKDH (int b, int k, double dh) {
        return (b * 72900) + (k * 3645) + (dh * 182.25);
    }
}

