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

    public String getUsername(){
        return username;
    }

    public void setUsername(String username){
        this.username = username;
    }

    public String getPassword(){
        return password;
    }

    public void setPassword(String password){
        this.password = password;
    }
    
}
