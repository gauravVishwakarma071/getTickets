/*
 * This Java source file was generated by the Gradle 'init' task.
 */
package com.getTickets;

import com.getTickets.services.UserBookingServices;

import java.io.IOException;
import java.util.Scanner;

public class App {
    public String getGreeting() {

        return "Hello World!";
    }

    public static void main(String[] args) {

        System.out.println("Running Train Booking System");
        Scanner sc = new Scanner(System.in);

        int option = 0;

        UserBookingServices userBookingServices;
        try{
            userBookingServices = new UserBookingServices();
        } catch (IOException e) {
            System.out.println("There is something wrong...");
            return;
        }


    }
}
