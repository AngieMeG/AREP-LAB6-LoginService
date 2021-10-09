package edu.escuelaing.arep;

import java.io.*;
import java.net.*;
import java.security.KeyManagementException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.CertificateException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.TrustManagerFactory;

/**
 * Conexion segura 
 * @author Angie Medina
 * @version 1.0
 */
public class URLReader {

    /**
     * Enable the secure connection with other servers
     */
    public static void allowConnection() {
        try {
            File trustStoreFile = new File("keystores/myTrustStore");
            char[] trustStorePassword = "123456".toCharArray();
            KeyStore trustStore = KeyStore.getInstance(KeyStore.getDefaultType());
            trustStore.load(new FileInputStream(trustStoreFile), trustStorePassword);

            // Get the singleton instance of the TrustManagerFactory
            TrustManagerFactory tmf = TrustManagerFactory
                    .getInstance(TrustManagerFactory.getDefaultAlgorithm());
            
            // Itit the TrustManagerFactory using the truststore object
            tmf.init(trustStore);

            //Set the default global SSLContext so all the connections will use it 
            SSLContext sslContext = SSLContext.getInstance("TLS"); 
            sslContext.init(null, tmf.getTrustManagers(), null); 
            SSLContext.setDefault(sslContext);

        } catch (KeyStoreException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (CertificateException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        } catch (KeyManagementException ex) {
            Logger.getLogger(URLReader.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Reed one https site
     * @param sitetoread the response of the site
     */
    public static String readURL(String sitetoread) throws IOException{
            URL siteURL = new URL(sitetoread);
            StringBuilder response = new StringBuilder();
            HttpURLConnection connection = (HttpURLConnection) siteURL.openConnection();
            connection.setRequestMethod("GET");
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            String line;
            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();
            return response.toString();
    }
}