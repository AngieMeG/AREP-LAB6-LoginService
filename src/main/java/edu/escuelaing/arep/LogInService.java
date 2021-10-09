package edu.escuelaing.arep;

import edu.escuelaing.arep.model.User;

import java.security.NoSuchAlgorithmException;  
import java.security.MessageDigest;  
import spark.Request;
import spark.Response;
import java.util.HashMap;
import static spark.Spark.*;
import com.google.gson.Gson;

/**
 * Class that provides the service to get logged in the application
 * @author Angie Medina
 * @version  1.0
 * */
public class LogInService {
    private static HashMap<String,String> users = new HashMap<String,String>();

    /**
     * Main method of the login service
     * @param args, args needed by the service
     */
    public static void main(String[] args){
        users.put("AngieMeG", encrypt("123456"));
        URLReader.allowConnection();
        port(getPort());
        staticFileLocation("/public");
        secure("keystores/ecikeystore.p12", "123456", null, null);

        get("/", (req, res) -> {
            res.redirect("/login.html");
            res.status(200);
            return null;
        });
        post("/login", (req, res) -> logInPage(req, res));

        before("/security/*", (req, res) -> checkIsLogged(req));

        get("/security/helloService", (req, res) -> helloServiceHandler(res));
    }

    /**
     * Get the info needed to log in
     * @param req, request of the POST method
     * @param res, esponse of the POST method
     * @return If there was an error in he process to log in
     */
    private static String logInPage(Request req, Response res) {
        String status = "error in login";
        int numberStatus = 404;
        req.session(true);
        User user = (new Gson()).fromJson(req.body(), User.class);
        if(users.containsKey(user.getUsername()) && users.get(user.getUsername()).equals(encrypt(user.getPassword()))){
            req.session().attribute("user", user.getUsername());
            req.session().attribute("isLogged", true);
            numberStatus = 200;
            status = "succesful login";
        }
        res.status(numberStatus);
        return status;
    }

    /**
     * Check if the user is logged before can access the resources
     * @param req the request made by the client
     */
    private static void checkIsLogged(Request req){
        req.session(true);
            if(req.session().isNew())
                req.session().attribute("isLogged",false);
            if((boolean)req.session().attribute("isLogged"))
                return;
            halt(401, "<h1>401 Unauthorized</h1>");
    }

    /**
     * Displays the hello Service
     * @param res, the response to the recuest
     * @return if the connection is ok displays the response of the server, if its not displays a message saying that
     * the server was not found
     */
    private static String helloServiceHandler(Response res){
        try{
            //return "<h1>" + URLReader.readURL("https://localhost:4568/helloService") + "</h1>";
            return "<h1>" + URLReader.readURL("https://ec2-54-87-109-167.compute-1.amazonaws.com:4568/helloService") + "</h1>";
        } catch (Exception e){
            e.printStackTrace();
        }
        res.status(500);
        return "<h1> Server not found </h1>";
    }

    /**
     * Method to encrypt the user's password through MD5
     * @param password, the user's password  not encrypted
     * @return the password encrypted through MD5
     */
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


    /**
     * 
     * @return
     */
    static int getPort() {
        if (System.getenv("PORT") != null) {
            return Integer.parseInt(System.getenv("PORT"));
        }
        return 4567;
    }

}
