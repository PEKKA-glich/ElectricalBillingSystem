package com.project.cs.electricalbillngsystem;

import com.project.cs.electricalbillngsystem.model.ElectricityRates;
import com.project.cs.electricalbillngsystem.model.HouseBill;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.*;
import java.util.Scanner;

/**
 * operations
 * 1. Insert
 * 2. GetData
 * 3. Delete
 * 4. Update
 * 5. DeleteAll
 * 6. GetAllData
 */


class Main {
    public static final String PATH = "C:\\Users\\pgarg";
    private static ElectricityRates rates;

    public static void main(String[] args) throws Exception {
        rates = new ElectricityRates("Rajasthan", 10);

        System.out.print("99: To show all commands" + "\n" +
                "1: To register for our Billing service (Creating an Account)" + "\n" +
                "2: To get Account details" + "\n" +
                "3: To de-register for Billing service (Delete Account)" + "\n" +
                "4: To Update Account details" + "\n" +
                "5: To delete all accounts (Admin only)" + "\n" +
                "6: Change Electricity rates (Admins only) - Default is Rajasthan 10(INR/kWh)" + "\n");
        Scanner input = new Scanner(System.in);
        int enterInput = input.nextInt();
        switch (enterInput) {
            case 99 -> {
                System.out.print("99: To show all commands" + "\n" +
                        "1: To register for our Billing service (Creating an Account)" + "\n" +
                        "2: To get Account details" + "\n" +
                        "3: To de-register for Billing service (Delete Account)" + "\n" +
                        "4: To Update Account details" + "\n" +
                        "5: To delete all accounts (Admin only)" + "\n" +
                        "6: Change Electricity rates (Admins only) - Default is Rajasthan 10(INR/kWh)" + "\n");
                main(new String[]{});
            }
            case 1 -> {
                Scanner insertScanner = new Scanner(System.in);
                System.out.print("Enter Name of Account (First name): ");
                String accName = insertScanner.next();
                System.out.print("Enter Address: ");
                String address = insertScanner.next();
                System.out.print("Enter City: ");
                String city = insertScanner.next();
                System.out.print("Enter State: ");
                String state = insertScanner.next();
                System.out.print("Enter Postal code: ");
                int postalCode = insertScanner.nextInt();

                HouseBill houseBill = new HouseBill(accName, address, city, state, postalCode, rates);
                insertData(houseBill);
            }
            case 2 -> {
                Scanner getDataScanner = new Scanner(System.in);
                System.out.print("Enter Account ID: ");
                int accId = getDataScanner.nextInt();

                getData(accId);
            }
            case 3 -> {
                Scanner deleteDataScanner = new Scanner(System.in);
                System.out.print("Enter Account ID: ");
                int accId = deleteDataScanner.nextInt();

                deleteData(accId);
            }
            case 4 -> {
                Scanner updateScanner = new Scanner(System.in);
                System.out.print("Enter Account ID: ");
                int accId = updateScanner.nextInt();
                System.out.print("Enter Name of Account: ");
                String accName = updateScanner.next();
                System.out.print("Enter Address: ");
                String address = updateScanner.next();
                System.out.print("Enter City: ");
                String city = updateScanner.next();
                System.out.print("Enter State: ");
                String state = updateScanner.next();
                System.out.print("Enter Postal code: ");
                int postalCode = updateScanner.nextInt();

                HouseBill houseBill = new HouseBill(accName, address, accId, city, state, postalCode, rates);
                updateData(houseBill);
            }
            case 5 -> {
                Scanner admin = new Scanner(System.in);

                String password = admin.next();
                if (adminCheck(password)) {
                    deleteAll();
                } else {
                    System.out.print("You are unAuthorised to access this method......");
                }
            }
            default -> {
                System.out.print("This does not exist...");
                main(new String[]{});
            }
        }
        System.out.print("\nRun programme again(Y/N): ");
        String termInput = input.next();
        if (termInput.equals("y") || termInput.equals("Y")) {
            main(new String[]{});
        } else {
            input.close();
            return;
        }

    }

    public static void insertData(HouseBill houseBill) throws IOException {
        //Creating Account json obj
        JSONObject accObj = new JSONObject();
        //Putting data in json as KEY-VALUE pairs
        accObj.put("AccId", houseBill.getAccId());
        accObj.put("AccName", houseBill.getAccName());
        accObj.put("Address", houseBill.getAddress());
        accObj.put("City", houseBill.getCity());
        accObj.put("State", houseBill.getState());
        accObj.put("Postal Code", houseBill.getPostalCode());
        accObj.put("Rates", houseBill.getElectricityRates().getRates());
        //Creating file object which is creating an JSON file in machine
        File file = new File(
                PATH + "\\IdeaProjects\\ElectricalBillingSystem\\data\\" +
                        houseBill.getAccId() + ".json");
        //Creating fileWriter obj containing above .json file
        FileWriter writer = new FileWriter(file);

        //Feeding data to .json file
        writer.write(accObj.toJSONString());

        //Printing Account Id to console
        System.out.print("Your AccountID is " + houseBill.getAccId());

        //Closing writer
        writer.close();
    }

    public static void getData(int accountId) throws IOException, ParseException {
        //Creating file object containing an Account aka HouseBill
        File file = new File(
                PATH + "\\IdeaProjects\\ElectricalBillingSystem\\data\\" +
                        accountId + ".json");
        //If file present in machine than retrieve data
        if (file.exists()) {
            //Retrieving data from already saved JSON data files
            JSONObject jsonObject = (JSONObject) new JSONParser().parse(new FileReader(file));
            String accName = (String) jsonObject.get("AccName");
            String address = (String) jsonObject.get("Address");
            String city = (String) jsonObject.get("City");
            String state = (String) jsonObject.get("State");
            Long postalCode = (Long) jsonObject.get("Postal Code");
            Long rates = (Long) jsonObject.get("Rates");
            //Printing to console
            System.out.print("\n" + "Name: " + accName + "\n" +
                    "Address: " + address + "\n" +
                    "Account ID: " + accountId + "\n" +
                    "City: " + city + "\n" +
                    "State: " + state + "\n" +
                    "Postal Code: " + postalCode + "\n" +
                    "Electricity Rates: " + rates + "INR/kWh" + "\n");
        }
        //Else show file does not exist in machine message in console
        else {
            System.out.print("Account ID does not exists.....");
        }
    }

    public static void deleteData(int userId) {
        //Creating file obj to userId.json
        File file = new File(PATH + "\\IdeaProjects\\ElectricalBillingSystem\\data\\" + userId + ".json");
        if (file.exists()) {
            try {
                file.delete();
                System.out.print("Account deleted successfully.....");
            } catch (SecurityException e) {
                System.out.print("Security reasons Account cannot be deleted......");
            }

        } else {
            System.out.print("Account ID does not exists.....");
        }
    }

    public static void updateData(HouseBill houseBill) throws IOException {
        //Creating Account json obj
        JSONObject accObj = new JSONObject();
        //Putting data in json as KEY-VALUE pairs
        accObj.put("AccId", houseBill.getAccId());
        accObj.put("AccName", houseBill.getAccName());
        accObj.put("Address", houseBill.getAddress());
        accObj.put("City", houseBill.getCity());
        accObj.put("State", houseBill.getState());
        accObj.put("Postal Code", houseBill.getPostalCode());
        accObj.put("Rates", houseBill.getElectricityRates().getRates());
        //Creating file object which is creating an JSON file in machine
        File file = new File(
                PATH + "\\IdeaProjects\\ElectricalBillingSystem\\data\\" +
                        houseBill.getAccId() + ".json");
        //Creating fileWriter obj containing above .json file
        FileWriter writer = new FileWriter(file);

        //Feeding data to .json file
        writer.write(accObj.toJSONString());

        //Printing success
        System.out.print("Your Account is updated");

        //Closing writer
        writer.close();
    }

    public static void deleteAll() throws IOException {
        File file = new File(PATH + "\\IdeaProjects\\ElectricalBillingSystem\\data");
        try {
            file.delete();
        } catch (SecurityException e) {
            System.out.print("Security reasons action cannot be done......");
        }
        file = new File(PATH + "\\IdeaProjects\\ElectricalBillingSystem\\data");
        try {
            file.mkdir();
        } catch (SecurityException e) {
            System.out.print("Security reasons action cannot be done......");
        }
        file = new File(PATH + "\\IdeaProjects\\ElectricalBillingSystem\\data\\CrucialData.json");
        JSONObject jsonObject = new JSONObject();
        jsonObject.put("idCounter", "1");
        FileWriter writer = new FileWriter(file);
        writer.write(jsonObject.toJSONString());
        writer.close();
    }

    public static void getAllData() {
    }

    public static boolean adminCheck(String password) {
        String realPassword = "institute";
        if (realPassword.length() != password.length()) {
            return false;
        }
        for (int i = 0; i < password.length(); i++) {
            if (realPassword.charAt(i) != password.charAt(i)) {
                return false;
            }
        }
        return true;
    }
}
