package com.getTickets.services;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.getTickets.entities.User;
import com.getTickets.util.UserServiceUtil;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

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
        user.printTickets();
    }

    public Boolean cancelBooking(String ticketID){
        //
        return Boolean.FALSE;
    }
}
