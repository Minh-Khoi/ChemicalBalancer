/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dto;

import action.SaveCompoundsJSON;
import java.io.File;
import java.io.FileNotFoundException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.JSONArray;
import org.json.JSONObject;

/**
 * This is the Object class stand for instance of compounds or subtances
 * @author USER
 */
public class Material {
    private String name, formula;
    private int id;
    private List<String> moreInfo;
    private boolean isValid=true;
    
    private List<AtomInMaterial> atomsOfMaterial ;

    public Material(String formula) {
        this.atomsOfMaterial = new ArrayList<AtomInMaterial>();
        this.formula = formula;
        if(this.isValid()){
            Map<String, Integer> mapOfAtoms = this.assignMapOfAtoms(formula);
            for(Map.Entry<String, Integer> set : mapOfAtoms.entrySet()){
                AtomInMaterial atom = new AtomInMaterial(set.getKey(), set.getValue());
                this.atomsOfMaterial.add(atom);
            } 
        } else {
            this.isValid = false;
        }
    }

    public boolean getIsValid(){
        return isValid;
    }
    public List<AtomInMaterial> getAtomsOfMaterial() {
        return atomsOfMaterial;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFormula() {
        return formula;
    }

    public void setFormula(String formula) {
        this.formula = formula;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public List<String> getMoreInfo() {
        return moreInfo;
    }

    public void setMoreInfo(List<String> moreInfo) {
        this.moreInfo = moreInfo;
    }

    
    //<editor-fold desc="Check validation of Compound (Material)">
    /**
     * Check validation of Compound or subtance (Material)
     * @return 
     */
    private boolean isValid(){
        try {
            String data = new SaveCompoundsJSON().getDataCompounds();
            JSONArray jSONArrayOfCompounds = new JSONArray(data);
            for(int j=0; j<jSONArrayOfCompounds.length();j++){
                JSONObject compound = jSONArrayOfCompounds.getJSONObject(j);
                if(compound.get("formula").equals(this.formula)){
                    this.setName(compound.getString("vietnamese_name"));
                    this.setId(compound.getInt("id"));
                    return true;
                }
            }
        } catch (ParseException ex) {
            Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    //</editor-fold>
    
    // <editor-fold desc="some line of code that assigning map of Atoms"> 
    // 
    /**
     * The property i was set for assist the method getMapOfAtoms only
     */
    private int i;
    private Map<String, Integer> assignMapOfAtoms(String formula) {
        int N = formula.length();
        Map<String, Integer> count = new HashMap();
        while (i < N && formula.charAt(i) != ')') {
            if (formula.charAt(i) == '(') {
                i++;
                for (Map.Entry<String, Integer> entry: assignMapOfAtoms(formula).entrySet()) {
                    count.put(entry.getKey(), count.getOrDefault(entry.getKey(), 0) + entry.getValue());
                }
            } else {
                int iStart = i++;
                while (i < N && Character.isLowerCase(formula.charAt(i))) i++;
                String name = formula.substring(iStart, i);
                iStart = i;
                while (i < N && Character.isDigit(formula.charAt(i))) i++;
                int multiplicity = iStart < i ? Integer.parseInt(formula.substring(iStart, i)) : 1;
                count.put(name, count.getOrDefault(name, 0) + multiplicity);
            }
        }
        int iStart = ++i;
        while (i < N && Character.isDigit(formula.charAt(i))) i++;
        if (iStart < i) {
            int multiplicity = Integer.parseInt(formula.substring(iStart, i));
            for (String key: count.keySet()) {
                count.put(key, count.get(key) * multiplicity);
            }
        }
        return count;
    }
    // </editor-fold>
    
    /**
     * Get the JSON string of the Material instance
     * @return String which is available to converted to JSON type
     */
    public String toJSONString(){
        JSONObject jSONObject = new JSONObject();
        jSONObject.put("vietnamese_name", this.name);
        jSONObject.put("formula", this.formula);
        jSONObject.put("id", this.id);
        JSONArray moreInfoJSON = new JSONArray(this.moreInfo);
        jSONObject.put("moreInfo", moreInfoJSON);
        return jSONObject.toString(4);
    }
}
