package com.example.nepalilandconverter;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MainApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(MainApplication.class.getResource("hello-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("Hello!");
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        AreaCalculator calculator = new AreaCalculator();
        System.out.println("Enter conversion");
        System.out.println("1.  From Square Feet");
        System.out.println("2.  From Square Meter");
        System.out.println("3.  From Ropani");
        System.out.println("4.  From Anna");
        System.out.println("5.  From Paisa");
        System.out.println("6.  From Daam");
        System.out.println("7.  From Ropani-Anna-Paisa-Daam");
        System.out.println("8.  From Bigha");
        System.out.println("9.  From Kattha");
        System.out.println("10. From Dhur");
        System.out.println("11. From Bigha-Kattha-Dhur");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        double inputValue = 0.0;

        switch (choice) {
            case 1:
                System.out.println("Enter area to convert from Square Feet");
                inputValue = getValidPositiveDouble(scanner);
                calculator.convertFromSquareFeet(inputValue);

            case 2:
                System.out.println("Enter area to convert from Square Meter");
                inputValue = getValidPositiveDouble(scanner);
                double areaFromSqMeter = calculator.convertFromSquareMeter(inputValue);
                calculator.convertFromSquareFeet(areaFromSqMeter);

            case 3:
                System.out.println("Enter area to convert from Ropani");
                inputValue = getValidPositiveDouble(scanner);
                double areaFromRopani = calculator.convertFromRopani(inputValue);
                calculator.convertFromSquareFeet(areaFromRopani);

            case 4:
                System.out.println("Enter area to convert from Anna");
                inputValue = getValidPositiveDouble(scanner);
                double areaFromAnna = calculator.convertFromAnna(inputValue);
                calculator.convertFromSquareFeet(areaFromAnna);

            case 5:
                System.out.println("Enter area to convert from Paisa");
                inputValue = getValidPositiveDouble(scanner);
                double areaFromPaisa = calculator.convertFromPaisa(inputValue);
                calculator.convertFromSquareFeet(areaFromPaisa);

            case 6:
                System.out.println("Enter area to convert from Daam");
                inputValue = getValidPositiveDouble(scanner);
                double areaFromDaam = calculator.convertFromDaam(inputValue);
                calculator.convertFromSquareFeet(areaFromDaam);

            case 7:
                int ropani, paisa, anna;
                double daam, areaFromRAPD;
                System.out.println("Enter the input in Ropani-Anna-Paisa-Daam");
                System.out.println("Enter Ropani");
                ropani = getValidPositiveInt(scanner);
                System.out.println("Enter Anna");
                anna = getValidPositiveInt(scanner);
                System.out.println("Enter Paisa");
                paisa = getValidPositiveInt(scanner);
                System.out.println("Enter Daam");
                daam = getValidPositiveDouble(scanner);
                areaFromRAPD = calculator.convertFromRAPD(ropani, anna, paisa, daam);
                calculator.convertFromSquareFeet(areaFromRAPD);

            case 8:
                System.out.println("Enter area to convert from Bigha");
                inputValue = getValidPositiveDouble(scanner);
                double areaFromBigha = calculator.convertFromBigha(inputValue);
                calculator.convertFromSquareFeet(areaFromBigha);

            case 9:
                System.out.println("Enter area to convert from Kattha");
                inputValue = getValidPositiveDouble(scanner);
                double areaFromKattha = calculator.convertFromKattha(inputValue);
                calculator.convertFromSquareFeet(areaFromKattha);

            case 10:
                System.out.println("Enter area to convert from Dhur");
                inputValue = getValidPositiveDouble(scanner);
                double areaFromDhur = calculator.convertFromDhur(inputValue);
                calculator.convertFromSquareFeet(areaFromDhur);

            case 11:
                int bigha, kattha;
                double dhur, areaFromBKDh;
                System.out.println("Enter the input in Bigha-Kattha-Dhur");
                System.out.println("Enter Bigha");
                bigha = getValidPositiveInt(scanner);
                System.out.println("Enter Kattha");
                kattha = getValidPositiveInt(scanner);
                System.out.println("Enter Dhur");
                dhur = getValidPositiveDouble(scanner);
                areaFromBKDh = calculator.converFromBKDH(bigha, kattha, dhur);
                calculator.convertFromSquareFeet(areaFromBKDh);

        }
}

    //Get Valid Double Input
    private static double getValidPositiveDouble(Scanner scanner) {
        double inputValue = 0.0;
        boolean isValidInput = false;

        while(!isValidInput) {
            try {
                inputValue = scanner.nextDouble();
                if (inputValue > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("Area must be greater than 0 ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid area. Please enter valid Area");
                scanner.nextLine();
            }
        }
        return inputValue;
    }

    private static int getValidPositiveInt(Scanner scanner) {
        int inputValueInt = 0;
        boolean isValidInput = false;

        while(!isValidInput) {
            try {
                inputValueInt = scanner.nextInt();
                if (inputValueInt > 0) {
                    isValidInput = true;
                } else {
                    System.out.println("Area must be greater than 0 ");
                }
            } catch (InputMismatchException e) {
                System.out.println("Invalid area. Please enter valid Area");
                scanner.nextLine();
            }
        }
        return inputValueInt;
    }
}