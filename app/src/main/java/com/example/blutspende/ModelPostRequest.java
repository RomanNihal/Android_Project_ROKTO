package com.example.blutspende;

public class ModelPostRequest {
    private String userName,phoneNumber,bloodGroup,location,date,inputDistrict, inputDiv, inputBG, key;

    public ModelPostRequest() {
    }

    public ModelPostRequest(String userName, String phoneNumber, String bloodGroup, String location, String date, String inputDistrict, String inputDiv, String key) {
        this.userName = userName;
        this.phoneNumber = phoneNumber;
        this.bloodGroup = bloodGroup;
        this.location = location;
        this.date = date;
        this.inputDistrict = inputDistrict;
        this.inputDiv = inputDiv;
        this.inputBG = inputBG;
        this.key = key;
    }

    public String getInputDistrict() {
        return inputDistrict;
    }

    public void setInputDistrict(String inputDistrict) {
        this.inputDistrict = inputDistrict;
    }

    public String getInputDiv() {
        return inputDiv;
    }

    public void setInputDiv(String inputDiv) {
        this.inputDiv = inputDiv;
    }


    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getUserName() {
        return userName;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public String getBloodGroup() {
        return bloodGroup;
    }

    public String getLocation() {
        return location;
    }

    public String getDate() {
        return date;
    }
}
