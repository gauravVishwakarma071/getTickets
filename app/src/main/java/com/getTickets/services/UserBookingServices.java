package com.getTickets.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getTickets.entities.Train;
import com.getTickets.entities.User;
import com.getTickets.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;

public class UserBookingServices {

    private User user;

    private List<User> userList;

    private static final String USERS_PATH = "app/src/main/java/com/getTickets/localDb/Users.json";

    private ObjectMapper objectMapper = new ObjectMapper();

    public UserBookingServices(User user1) throws IOException {
        this.user = user1;
        loadUsers();
    }
    public UserBookingServices()throws IOException{loadUsers();}


    public List<User> loadUsers() throws IOException{
        File users = new File(USERS_PATH);
        return userList = objectMapper.readValue(users, new TypeReference<List<User>>() {});
    }

    public Boolean loginUser(){
        Optional<User> foundUser = userList.stream().filter(user1 -> {
            return user1.getName().equalsIgnoreCase(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        return foundUser.isPresent();
    }

    public Boolean signUp(User user1){
        try{
            userList.add(user1);
            saveUserListToFile();
            return Boolean.TRUE;
        }catch (IOException ex){
            return Boolean.FALSE;
        }
    }

    private void saveUserListToFile()throws IOException{
        File userFile = new File(USERS_PATH);
        objectMapper.writeValue(userFile, userList);
    }

    public void fetchBooking(){
        Optional<User> userFetched = userList.stream().filter(user1 -> {
            return user1.getName().equals(user.getName()) && UserServiceUtil.checkPassword(user.getPassword(), user1.getHashedPassword());
        }).findFirst();
        if(userFetched.isPresent()){
            userFetched.get().printTickets();
        }
    }

    public Boolean cancelBooking(String ticketID){
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter the ticket id to cancel.");
        ticketID = sc.next();

        if(ticketID==null || ticketID.isEmpty()){
            System.out.println("Ticket Id can't be empty.");
            return Boolean.FALSE;
        }


        String finalTicketId1 = ticketID;  //Because strings are immutable
        boolean removed = user.getTicketBooked().removeIf(ticket -> ticket.getTicketId().equals(finalTicketId1));

        String finalTicketId = ticketID;
        user.getTicketBooked().removeIf(Ticket -> Ticket.getTicketId().equals(finalTicketId));
        if (removed) {
            System.out.println("Ticket with ID " + ticketID + " has been canceled.");
            return Boolean.TRUE;
        }else{
            System.out.println("No ticket found with ID " + ticketID);
            return Boolean.FALSE;
        }


    }
    public List<Train> getTrains(String source, String destination){
        try{
            TrainService trainService = new TrainService();
            return trainService.searchTrains(source, destination);
        }catch(IOException ex){
            return new ArrayList<>();
        }
    }
}
