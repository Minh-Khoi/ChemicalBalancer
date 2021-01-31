/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import action.Balancer;
import action.CompoundInfoGetter;
import action.EquationInfoGetter;
import api.FrontEndAPI;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.dto.Material;

/**
 *
 * @author USER
 */
@WebServlet(name = "balance", urlPatterns = {"/balance","/checkcompound"})
@MultipartConfig
public class Controller extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods. This process method will check the "urlPattern" (returned by request.getServletPath() )
     * and invoke the corresponding "private" method below.
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Set property "web.dir" for System, which is the absolute path to the folder "datas"
        System.setProperty("web.dir", request.getServletContext().getRealPath(""));
        response.setContentType("text/html;charset=UTF-8");
        response.setHeader("Access-Control-Allow-Origin", FrontEndAPI.frontendURL);
        response.setCharacterEncoding("UTF-8");
        
        PrintWriter printWriter = response.getWriter();
        if(request.getServletPath().equals("/balance") ){
            printWriter.println(this.doBalance(request, response));
        } else if (request.getServletPath().equals("/checkcompound")){
            String compoundDetails = (this.renderCompoundDetails(request, response));
            printWriter.println(compoundDetails);
        }
    }
    
    /** 
     * If the urlPattern = "/balance". this method will be invoke to make the equation (in request object)
     * balance and return its result as String
     * @param request
     * @param response 
     */
    private String doBalance(HttpServletRequest request, HttpServletResponse response){
        String reactants = request.getParameter("reactants");
        String products = request.getParameter("products");
        String result = Balancer.getBalancedString(reactants, products);
        String equationInfo = new EquationInfoGetter(reactants, products).getEquationDetailDatas();
        System.out.println(result + "\n" + equationInfo);
        return "<h1>" + result + "</h1> \n" + equationInfo; 
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

    /** 
     * If the urlPattern = "/checkcompound". this method will be invoked to check validation of a formula
     * @param request
     * @param response 
     */
    private String renderCompoundDetails(HttpServletRequest request, HttpServletResponse response) {
        String materialInfo = null ;
        String compoundFormula = request.getParameter("compoundFormula");
        Material material = new Material(compoundFormula);
        boolean materialIsValid = material.getIsValid();
//        System.out.println(request.getParameter("compoundFormula"));
        if(!materialIsValid){
//            System.out.println(material.toJSONString());
            return ("The formula " + compoundFormula + " does not stand for any compound or subtance");
        } else {
            materialInfo = new CompoundInfoGetter(material).getMaterialDetail();
            System.out.println(materialInfo);
        }
       
        return materialInfo;
    }
    
//    public static void main(String[] args) {
//        String compoundFormula = "C2H5OH";
//        Material material = new Material(compoundFormula);
//        if(!material.getIsValid()){
//            System.out.println("The formula " + compoundFormula + " does not stand for any compound or subtance");
//        } else {
//            String materialInfo = new CompoundInfoGetter(material).getMaterialDetail();
//            System.out.println(materialInfo);
//        }
//    }

}
