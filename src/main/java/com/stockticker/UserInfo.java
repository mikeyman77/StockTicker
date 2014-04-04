package com.stockticker;

/**
 * This is a data class for user information.
 * 
 * @author Michael Grissom
 */
public class UserInfo {

    private String firstName;
    private String lastName;
    
    /**
     * Default constructor
     */
    public UserInfo() { }

    /**
     * Designated constructor
     * 
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     */
    public UserInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Returns the first name of the user.
     */
    
    public String getFirstName() {
        return firstName;
    }

    /**
     * Returns the last name of the user.
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's first name.
     * 
     * @param firstName the first name of the user
     */
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the user's last name
     * 
     * @param lastName the last name of the user
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
