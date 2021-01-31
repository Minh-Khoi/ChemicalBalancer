/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;
import javax.tools.FileObject;
import org.json.JSONObject;

/**
 *
 * @author USER
 */
public class SaveCompoundsJSON {
    private String dataCompounds;

    public SaveCompoundsJSON() {
        try {
            getStringDataFromURL();
        } catch (FileNotFoundException ex) {
            Logger.getLogger(SaveCompoundsJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /**
     * This method is the only way to return the list of all compounds and subtance in JSON string
     * @return all compounds and subtance in JSON string
     */
    public String getDataCompounds(){
//        System.out.println(this.dataCompounds);
        return dataCompounds;
    }
    
    /**
     * This method create an URL to website phuongtrinhhoahoc.com and get response in JSON string
     * and save the JSON string in file json concurrently. 
     * This method is also the only way to set value to property "dataCompounds" of this class
     * @return JSON string
     * @throws FileNotFoundException 
     */
    private void getStringDataFromURL() throws FileNotFoundException{
        String line;
        HttpURLConnection connection = null;
        
        try {            
            //<editor-fold desc="Fixing the SSL Exception. Line: 71 - 92">
            /* Start of Fix */
            TrustManager[] trustAllCerts = new TrustManager[] { new X509TrustManager() {
                public java.security.cert.X509Certificate[] getAcceptedIssuers() { return null; }
                public void checkClientTrusted(X509Certificate[] certs, String authType) { }
                public void checkServerTrusted(X509Certificate[] certs, String authType) { }

            } };

            SSLContext sc = SSLContext.getInstance("SSL");
            sc.init(null, trustAllCerts, new java.security.SecureRandom());
            HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());

            // Create all-trusting host name verifier
            HostnameVerifier allHostsValid = new HostnameVerifier() {
                public boolean verify(String hostname, SSLSession session) { return true; }
            };
            // Install the all-trusting host verifier
            HttpsURLConnection.setDefaultHostnameVerifier(allHostsValid);
            /* End of the fix*/
            //</editor-fold>
            
            URL url = new URL("https://m.phuongtrinhhoahoc.com/substance/AjaxGetAllSubstance");
            connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            // We need this line to avoid the unexpected 403 (Forbbiden) code, and I don't know WHY?
            // Maybe the website was developped to prevent accessing of the robot tool 's actions or virus, trojans etc.
            // and this line of code make the crawler tool become undercovered as a browser
            connection.setRequestProperty("User-Agent",
                    "Mozilla/5.0 (Windows NT 5.1) AppleWebKit/535.11 (KHTML, like Gecko) Chrome/17.0.963.56 Safari/535.11");
            InputStream inputStream = connection.getInputStream();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
            StringBuffer response = new StringBuffer();

            while((line = bufferedReader.readLine()) != null){
                response.append(line);
                response.append('\n');
            }
            bufferedReader.close();
            // concurrent with returning the json string, we should save it in file json
            // This action will be handle by method saveToJSONFile()
            saveToJSONFile(response.toString());
            this.dataCompounds = response.toString();                    
        } catch (IOException ex) {
//             if the connecting to the URL get failed. We will load datas from file all_chemical.json
//             Code for rendering data from json file (on local server)
            File fileJSON = new File(System.getProperty("web.dir") + "/datas/all_chemical.json");
            Scanner scanner = new Scanner(fileJSON);
            StringBuffer resp = new StringBuffer();
            while (scanner.hasNextLine()){
                String holder = scanner.nextLine();
                resp.append(holder);
            }
            this.dataCompounds = resp.toString();
//            ex.printStackTrace();
        } catch (KeyManagementException ex) {
            Logger.getLogger(SaveCompoundsJSON.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(SaveCompoundsJSON.class.getName()).log(Level.SEVERE, null, ex);
        } 
    }
    
    /**
     * This method save the json string which is responsed from the URL (phuongtrinhhoahoc.com)
     * It will be invoked by method getStringDataFromURL() while this method (getStringDataFromURL) 
     * concurrently assign the dataCompound as the json array
     * @param jsonString 
     */
    private static void saveToJSONFile(String jsonString){
//        System.out.println(System.getProperty("web.dir"));
        try {
            File fileJSON = new File(System.getProperty("web.dir") + "/datas/all_chemical.json");
            if(fileJSON.createNewFile()){
                System.out.println("New file created: filename is " + fileJSON.getName());
            }
            FileWriter fwriter = new FileWriter(fileJSON);
            fwriter.write(jsonString);
            fwriter.close();
        } catch (IOException ex) {
            Logger.getLogger(SaveCompoundsJSON.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
}
