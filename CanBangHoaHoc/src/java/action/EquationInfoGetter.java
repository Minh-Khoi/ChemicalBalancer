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
            this.mainDocumentHTML = Jsoup.connect(this.getRightURLOfEquation()).get();
            this.equationDetailDatas = this.getInfoOfEquation(this.mainDocumentHTML);
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
    private String getInfoOfEquation(Document doc){
        Element div = doc.selectFirst("div[role=tabpanel]");
        String innerHTML = div.html();
        int endIndex = innerHTML.indexOf("Câu hỏi minh họa");
        // we should delete all the DOM from "Câu hỏi minh họa" and later
        if(endIndex != -1) {
            innerHTML = innerHTML.substring(0,endIndex);
        } 
        div = div.html(innerHTML);
        return div.outerHtml();
    }
       
    /**
     * The only way to get detail datas of a particular equation
     * @return prop equationDetailDatas of the EquationInfoGetter
     */
    public String getEquationDetailDatas() {
        return equationDetailDatas;
    }
    
    
}