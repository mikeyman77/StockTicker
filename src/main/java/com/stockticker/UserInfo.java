package com.stockticker;

public class UserInfo {

    private String firstName;
    private String lastName;

    public UserInfo() { }

    public UserInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /*
     * Getter Methods
     */
    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    /*
     * Setter Methods
     */
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
