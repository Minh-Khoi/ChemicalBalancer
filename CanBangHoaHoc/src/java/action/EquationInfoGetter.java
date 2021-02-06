/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author USER
 */
public class EquationInfoGetter {
    private String equationDetailDatas;
    private Document documentHTMLDemo, mainDocumentHTML;

    public EquationInfoGetter(String left , String right) {
        String url = "https://phuongtrinhhoahoc.com/?chat_tham_gia="+ left + "&chat_san_pham=" + right;
        try {
            this.documentHTMLDemo = Jsoup.connect(url).get();
            String urlOfEquation = this.getRightURLOfEquation(); // Sai ở chỗ này chờ fix bug
            this.mainDocumentHTML = Jsoup.connect(urlOfEquation).get();
            Element mainDocumentLinkContainer = this.mainDocumentHTML.selectFirst(".bs-callout.bs-callout-primary b");
            boolean usingMainDocument = mainDocumentLinkContainer ==null 
                                            || mainDocumentLinkContainer.text().equals(urlOfEquation)
                                            || mainDocumentLinkContainer.text().indexOf("http:") == -1;
            Document datasDocument = (usingMainDocument) ? this.mainDocumentHTML : this.documentHTMLDemo;
            this.equationDetailDatas = this.getInfoOfEquation(datasDocument, usingMainDocument);
        } catch (IOException ex) {
            this.documentHTMLDemo = null;
        }
    }
    
    /**
     * get the link to the exactly page for a particular chemical equation (from phuongtrinhhoahoc.com)
     * @return url link (String instance)
     */
    private String getRightURLOfEquation(){
        Element linkTag = this.documentHTMLDemo.select("a.left.btn.btn-danger.btn-sm").first();
        return "https://phuongtrinhhoahoc.com" + linkTag.attr("href");
    }
    
    /**
     * return the String standing for DOM Element which contain information of a particular equation
     * which is rendered from method getRightURLOfEquation()
     * @param doc The Document (JSOUP) of the exactly page for a particular chemical equation (from phuongtrinhhoahoc.com)
     * @return he String standing for DOM Element which contain information of a particular equation
     */
    private String getInfoOfEquation(Document doc, boolean usingMainDocument){
        try{
            Element div = doc.selectFirst("div[role=tabpanel]");
            String innerHTML = div.html();
            int endIndex = (usingMainDocument) ? innerHTML.indexOf("<div") : innerHTML.indexOf("<a");
            System.out.println(usingMainDocument);
            // we should delete all the DOM from "<div" and later
            if(endIndex != -1) {
                innerHTML = innerHTML.substring(0,endIndex);
            } 
            div = div.html(innerHTML);
            return div.outerHtml();
        } catch (NullPointerException ex){
            return "<div class=\"alert alert-warning\" role=\"alert\">" +
                        " WARNING !!! This is a chemical equations which can never be done in reality!! Please Check it again" +
                        "</div>";
        }
        
    }
       
    /**
     * The only way to get detail datas of a particular equation
     * @return prop equationDetailDatas of the EquationInfoGetter
     */
    public String getEquationDetailDatas() {
        return equationDetailDatas;
    }
    
    
}