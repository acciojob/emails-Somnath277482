package com.driver;

import java.util.*;

public class Gmail extends Email {
    private ArrayList<details> inbox=new ArrayList<>();
    private HashMap<Date, String>trash=new HashMap<>();

    private int inboxCapacity; //maximum number of mails inbox can store
    //Inbox: Stores mails. Each mail has date (Date), sender (String), message (String). It is guaranteed that message is distinct for all mails.
    //Trash: Stores mails. Each mail has date (Date), sender (String), message (String)

    public ArrayList<details> getInbox() {
        return inbox;
    }

    public HashMap<Date, String> getTrash() {
        return trash;
    }

    public Gmail(String emailId, int inboxCapacity) {
        super(emailId);
        this.inboxCapacity=inboxCapacity;
    }

    public void receiveMail(Date date, String sender, String message){
        // If the inbox is full, move the oldest mail in the inbox to trash and add the new mail to inbox.
        // It is guaranteed that:
        // 1. Each mail in the inbox is distinct.
        // 2. The mails are received in non-decreasing order. This means that the date of a new mail is greater than equal to the dates of mails received already.
        if(inbox.size()==inboxCapacity){
            deleteMail(findOldestMessage());
            inbox.add(new details(date, message));
        }else{
            inbox.add(new details(date, message));
        }
    }

    public void deleteMail(String message){
        // Each message is distinct
        // If the given message is found in any mail in the inbox, move the mail to trash, else do nothing
        for(details info:inbox){
            if(info.getMessage().equals(message)){
                trash.put(info.getDate(),message);
                inbox.remove(info);
                break;
            }
        }

    }

    public String findLatestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the latest mail present in the inbox
        if(inbox.size()==0)return null;
        else {
            Collections.sort(inbox,(a,b)->{
                return a.getDate().compareTo(b.getDate());
            });
            return inbox.get(inbox.size()-1).getMessage();
        }
    }

    public String findOldestMessage(){
        // If the inbox is empty, return null
        // Else, return the message of the oldest mail present in the inbox
        if(inbox.size()==0)return null;
        else {
            Collections.sort(inbox,(a,b)->{
                return a.getDate().compareTo(b.getDate());
            });
            return inbox.get(0).getMessage();
        }
    }

    public int findMailsBetweenDates(Date start, Date end){
        //find number of mails in the inbox which are received between given dates
        //It is guaranteed that start date <= end date
        int cnt=0;
        for(details info:inbox){
            if(info.getDate().after(start) && info.getDate().before(end)){
                cnt++;
            }else if(info.getDate().equals(start)||info.getDate().equals(end)){
                cnt++;
            }
        }
        return cnt;
    }

    public int getInboxSize(){
        return inbox.size();
    }

    public int getTrashSize(){
        return trash.size();
    }

    public void emptyTrash(){
        // clear all mails in the trash
        getTrash().clear();
    }

    public int getInboxCapacity() {
        // Return the maximum number of mails that can be stored in the inbox
        return inboxCapacity;
    }
}