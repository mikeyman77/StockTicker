package com.stockticker;

/**
 * Defines the additional user information.
 * 
 * @author Michael Grissom
 */
public class UserInfo {

    /**
     * The user's first name
     */
    private String firstName;

    /**
     * The user's last name
     */
    private String lastName;
    
    /**
     * Constructs a UserInfo object
     */
    public UserInfo() { }

    /**
     * Constructs a UserInfo object
     * 
     * @param firstName the first name of the user
     * @param lastName the last name of the user
     */
    public UserInfo(String firstName, String lastName) {
        this.firstName = firstName;
        this.lastName = lastName;
    }
    
    /**
     * Retrieves the user's first name.
     *
     * @return the user's first name
     */
    
    public String getFirstName() {
        return firstName;
    }

    /**
     * Retrieves the user's last name.
     *
     * @return the user's last name
     */
    public String getLastName() {
        return lastName;
    }

    /**
     * Sets the user's first name.
     *
     * @param firstName the user's first name
     */
    
    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    /**
     * Sets the user's last name
     * 
     * @param lastName the user's last
     */
    public void setLastName(String lastName) {
        this.lastName = lastName;
    }
}
