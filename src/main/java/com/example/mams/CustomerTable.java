package com.example.mams;

public class CustomerTable {
    int CustomerID, CardID;
    String FirstName, LastName, Email, PhoneNo;

    public CustomerTable(int customerID, String firstName, String lastName,String email, String phoneNo, int cardID){
        this.CustomerID = customerID;
        this.CardID = cardID;
        this.FirstName = firstName;
        this.LastName = lastName;
        this.Email = email;
        this.PhoneNo = phoneNo;
    }

    public String getPhoneNo() {
        return PhoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        PhoneNo = phoneNo;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public int getCardID() {
        return CardID;
    }

    public void setCardID(int cardID) {
        CardID = cardID;
    }

    public int getCustomerID() {
        return CustomerID;
    }

    public void setCustomerID(int customerID) {
        CustomerID = customerID;
    }
}
