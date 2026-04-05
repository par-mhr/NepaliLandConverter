package com.nepalilandconverter;

import java.util.HashMap;
import java.util.Map;

public class AreaCalculator {

    /**
     * Convert from square feet to all area units
     * @param sqFeet Input area in square feet
     * @return Map containing all conversions
     */
    public Map<String, String> convertFromSquareFeet(double sqFeet) {
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

        Map<String, String> results = new HashMap<>();
        results.put("squareFeet", String.format("%.3f", sqFeet));
        results.put("squareMeter", String.format("%.3f", sqMeter));
        results.put("ropani", String.format("%.3f", ropani));
        results.put("anna", String.format("%.3f", anna));
        results.put("paisa", String.format("%.3f", paisa));
        results.put("daam", String.format("%.3f", daam));
        results.put("rapd", ropaniOutput + "-" + annaOutput + "-" + paisaOutput + "-" + String.format("%.3f", daamOutput));
        results.put("bigha", String.format("%.3f", bigha));
        results.put("kattha", String.format("%.3f", kattha));
        results.put("dhur", String.format("%.3f", dhur));
        results.put("bkdh", bighaOutput + "-" + katthaOutput + "-" + String.format("%.3f", dhurOutput));

        return results;
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

