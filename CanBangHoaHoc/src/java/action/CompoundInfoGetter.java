/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import model.dto.Material;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author USER
 */
public class CompoundInfoGetter {
    /**  */
    private Material material;
    private String materialDetail;
    private Document documentHTMLDemo, mainDocumentHTML;

    public CompoundInfoGetter(Material material) {
        this.material = material;
        String url = "https://chemicalequationbalance.com/substance?substance_search=" + material.getFormula();
        try {
            this.mainDocumentHTML = Jsoup.connect(url).get();
            this.getMoreInfoOfMaterialFromURL(this.mainDocumentHTML);
            this.materialDetail = this.material.toJSONString();
        } catch (IOException ex) {
            Logger.getLogger(CompoundInfoGetter.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Crawl Element from Document instance and get the required information of material
     * @param domHTML 
     */
    private void getMoreInfoOfMaterialFromURL(Document domHTML){
        Element el = domHTML.selectFirst(".bs-callout.bs-callout-pthh.substance-layout > .row > .col-md-8");
        Elements h4Tags = el.select("h4");
        List<String> moreInfo = new ArrayList<>();
        for(Element h4 : h4Tags){
            Elements spanTags = h4.select("span");
            if(!spanTags.isEmpty()){
                moreInfo.add(h4.text());
                this.material.setMoreInfo(moreInfo);
            }            
        }
        this.materialDetail = this.material.toJSONString();
    }
   
    /**
     * The only way to get detail information of a material through CompoundInfoGetter instance
     * @return detail information of a material (in JSON String)
     */
    public String getMaterialDetail() {
        return materialDetail;
    }
    
//    public static void main(String[] args) {
//        Material mat = new Material("H2S");
//        CompoundInfoGetter getter = new CompoundInfoGetter(mat);
//        System.out.println(getter.getMaterialDetail());
//    }
}
