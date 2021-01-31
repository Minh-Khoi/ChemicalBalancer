/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package action;

import com.aghajari.chemical.BalancerException;
import com.aghajari.chemical.ChemicalEducationBalancer;
import model.dto.Material;

/**
 *
 * @author USER
 */
public class Balancer {
//    public static void main(String[] args) {
//        System.out.println(getBalancedString("HNO3+FeO", "N2O+Fe(NO3)3+H2O"));
//    }
    
    /**
     * Make a chemical equation balanced and return it as a String
     * @param left a String of reactants expression
     * @param right a String of products expression
     * @return A String of Equation which is balanced. Or a String announce an error
     */
    public static String getBalancedString(String left, String right){
        String reactantNotValid = compoundNotValid(left);
        String productNotValid = compoundNotValid(right);
        if(reactantNotValid != null || productNotValid!=null){
            return ((reactantNotValid != null) ? reactantNotValid : productNotValid) + " is not a valid chemical compound or subtance";
        }
        // Using package com.aghajari.chemical which was supplied by library ChemicalEducationBalancer.jar
        try {
            return (ChemicalEducationBalancer.balance(left + "=" + right).getResult());
        } catch (BalancerException e) {
//            e.printStackTrace();
            return "Some thing when wrong. This equation is not available to be getting balance";
        }
    }
    
    /**
     * Check if there is a invalid compound or subtance in the expression
     * @param expression (reactants or products expression in String).
     * If there is one of them unvalid, return the invalid formula; else return null
     * @return null if all compound (and subtance) are valid. If there is one of them unvalid, return its formula
     */
    private static String compoundNotValid(String expression){
        String[] arrayOfCompoundsFormula = expression.split("\\+");
//        System.out.println(arrayOfCompoundsFormula.length);
        for(String formula : arrayOfCompoundsFormula){
            Material material = new Material(formula);
            if(!material.getIsValid()){
                return formula;
            }
        }
        return null;
    }
}
