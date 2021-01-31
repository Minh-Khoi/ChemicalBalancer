/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dto;

/**
 * This object class stand for instance of each atom 's formula (such as H, O, Cl, Fe, Si, Mg)....
 * exist in a particular compound of subtance
 * @author USER
 */
public class AtomInMaterial {
    private String name, formula ;
    private int numberOfAtomsInMaterial;

    public AtomInMaterial(String formula, int numberOfAtomsInMaterial) {
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumberOfAtomsInMaterial() {
        return numberOfAtomsInMaterial;
    }

    public void setNumberOfAtomsInMaterial(int numberOfAtomsInMaterial) {
        this.numberOfAtomsInMaterial = numberOfAtomsInMaterial;
    }

    public String getFormula() {
        return formula;
    }
    
    
}
