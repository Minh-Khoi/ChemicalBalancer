
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Connection;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author USER
 */
public class Demo {
    public static void main(String[] args) {
        String url ="http://phuongtrinhhoahoc.com/?chat_tham_gia=HCl+Na2O&chat_san_pham=NaCl+H2O";
        try {
            Document data;
            Connection conn = Jsoup.connect(url)
                    .userAgent("Mozilla/5.0 "
                            + " Chrome/79.0.3945.132 Mobile Safari/537.36");
            data = conn.get();
//            Thread.sleep(5000);
            System.out.println(data.text());
        } catch (IOException ex) {
            Logger.getLogger(Demo.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
