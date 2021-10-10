package edu.escuelaing.arep;

import edu.escuelaing.arep.model.User;

import java.security.NoSuchAlgorithmException;  
import java.security.MessageDigest;  
import spark.Request;
import spark.Response;
import java.util.HashMap;
import static spark.Spark.*;
import com.google.gson.Gson;

public class LogInService {
    private static HashMap<String,String> users = new HashMap<String,String>();

    public static void main(String[] args){
        users.put("AngieMeG", encrypt("AMG1234"));
        port(getPort());
        staticFileLocation("/public");
        get("/", (req, res) -> {
            res.redirect("/login.html");
            res.status(200);
            return null;
        });
        post("/login", (req, res) -> logInPage(req, res));
    }

    private static String logInPage(Request req, Response res) {
        String status = "error in login";
        int numberStatus = 404;
        req.session(true);
        User user = (new Gson()).fromJson(req.body(), User.class);
        if(users.containsKey(user.getUsername()) && users.get(user.getUsername()).equals(encrypt(user.getPassword()))){
            System.out.println("Yep...");
            req.session().attribute("user", user.getUsername());
            req.session().attribute("isLogged", true);
            numberStatus = 200;
            status = "succesful login";
        }
        System.out.println("Dunno");
        res.status(numberStatus);
        return status;
    }
    private static String encrypt(String password){
        String encryptedpassword = password;
        try{
            MessageDigest m = MessageDigest.getInstance("MD5");  
            m.update(password.getBytes());  
            byte[] bytes = m.digest();  
            StringBuilder s = new StringBuilder();  
            for(int i=0; i< bytes.length ;i++){  
                s.append(Integer.toString((bytes[i] & 0xff) + 0x100, 16).substring(1));  
            }
            encryptedpassword = s.toString();
        }
        catch (NoSuchAlgorithmException e)   {  
            e.printStackTrace();  
        }
        return encryptedpassword;
    }


    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 5000;
    }

}
