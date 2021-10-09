package edu.escuelaing.arep.model;

/**
 * User entity
 * @author Angie Medina
 * @version 1.0
 */
public class User {

    private String username;
    private String password;

    /**
     * Creates a new user
     */
    public User(){}

    /**
     * Creates a new user
     * @param username unique name of the user
     * @param password password of the user
     */
    public User(String username, String password){
        this.username = username;
        this.password = password;
    }

    /**
     * Gets the username
     * @return the username
     */
    public String getUsername(){
        return username;
    }

    /**
     * Sets the username
     * @param username the new username
     */
    public void setUsername(String username){
        this.username = username;
    }

    /**
     * Gets the password
     * @return the password
     */
    public String getPassword(){
        return password;
    }

    /**
     * Sets the password
     * @param password, the new password
     */
    public void setPassword(String password){
        this.password = password;
    }
    
}
