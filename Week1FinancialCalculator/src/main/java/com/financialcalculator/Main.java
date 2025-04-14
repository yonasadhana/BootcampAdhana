package com.financialcalculator;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
         Scanner input = new Scanner(System.in);
        boolean keepRunning = true;

        while (keepRunning) {
            // Display menu
            System.out.println("***************************************************");
            System.out.println("*   *                                      *      *");
            System.out.println("*       *   * FINANCIAL CALCULATOR *   *          *");
            System.out.println("*   *                                      *      *");
            System.out.println("***************************************************");
            System.out.println("\nWelcome to yoninaldo's Financial Calculator Application");
            System.out.println("Below is the application's menu");
            System.out.println("1. Mortgage Calculator");
            System.out.println("2. Future Value Calculator");
            System.out.println("3. Present Value of Annuity Calculator");
            System.out.println("4. Exit Program");
            System.out.print("Choose a calculator (1-4): ");

            int choice = input.nextInt();

            switch(choice) {
                case 1:
                    System.out.println("\n-- Mortgage Calculator --");

                    // user input
                    System.out.print("Enter loan amount ($): ");
                    double principal = input.nextDouble();

                    System.out.print("Enter yearly interest rate (%): ");
                    double yearlyRate = input.nextDouble();

                    System.out.print("Enter loan length (years): ");
                    int years = input.nextInt();

                    // Creating values for the given formula
                    double monthlyRate = yearlyRate / 100 / 12;
                    int months = years * 12;

                    // to calculate monthly payment
                    // M = P × (i*(1+i)^n / ((1+i)^n)-1)
                    double monthlyPayment = principal *
                        (monthlyRate * Math.pow(1 + monthlyRate, months)) /
                        (Math.pow(1 + monthlyRate, months) - 1);

                    // to calculate total interest
                    double totalInterest = (monthlyPayment * months) - principal;

                    // to display result
                    System.out.println("\nResults:");
                    System.out.println("Monthly payment: $" + monthlyPayment);
                    System.out.println("Total interest: $" + totalInterest);
                    break;

                case 2:
                    System.out.println("\n-- Future Value Calculator --");

                    // Get user input
                    System.out.print("Enter deposit amount ($): ");
                    double deposit = input.nextDouble();

                    System.out.print("Enter yearly interest rate (%): ");
                    double interestRate = input.nextDouble();

                    System.out.print("Enter number of years: ");
                    int depositYears = input.nextInt();

                    // interest rate to decimal
                    double rateDecimal = interestRate / 100;

                    // future value
                    // FV = P × (1 + (r / 365))^(365 × t)
                    double futureValue = deposit *
                        Math.pow(1 + (rateDecimal / 365), 365 * depositYears);

                    // to calculate interest earned
                    double interestEarned = futureValue - deposit;

                    // to display a results
                    System.out.println("\nResults:");
                    System.out.println("Future value: $" + futureValue);
                    System.out.println("Interest earned: $" + interestEarned);
                    break;

                case 3:
                    System.out.println("\n-- Present Value of Annuity Calculator --");

                    // Get users input
                    System.out.print("Enter monthly payout amount ($): ");
                    double monthlyPayout = input.nextDouble();

                    System.out.print("Enter expected yearly interest rate (%): ");
                    double annuityRate = input.nextDouble();

                    System.out.print("Enter years to pay out: ");
                    int payoutYears = input.nextInt();

                    // Convert values for formula
                    double monthlyInterestRate = annuityRate / 100 / 12;
                    int totalPayments = payoutYears * 12;

                    // to calculate present value of annuity
                    // Formula: PV = PMT × [(1 - (1 + r)^(-n)) / r]
                    double presentValue = monthlyPayout *
                        (1 - Math.pow(1 + monthlyInterestRate, -totalPayments)) /
                        monthlyInterestRate;

                    System.out.println("\nResults:");
                    System.out.println("Present value of annuity: $" + presentValue);
                    break;

                case 4:
                    System.out.println("Thank you for using the Financial Calculator! More advanced application to come. until then ciao");
                    keepRunning = false;
                    break;

                default:
                    System.out.println("Invalid choice! Please enter 1, 2, 3, or 4.");
                    break;
            }

            if (keepRunning) {
                System.out.println("\nPress Enter to continue...");
                input.nextLine();
                input.nextLine();
            }
        }

        input.close();
    }
}
