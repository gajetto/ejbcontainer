/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package webclient;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dataTransferObjects.UserDTO;
import ejb.TradingRemote;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;
import javax.ejb.EJB;
import org.json.simple.JSONObject;

/**
 *
 * @author Jerome
 */
@WebServlet(name = "ServletGetUser", urlPatterns = {"/ServletGetUser"})
public class ServletGetUser extends HttpServlet {
    
    @EJB
    private TradingRemote tradingBean;
    
    private DateFormat df = new SimpleDateFormat("dd.MM.yyyy");

    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
            
        JSONObject obj = new JSONObject();
        
        if(request.getParameter("username")!= null){
            String username = request.getParameter("username");
            UserDTO user = tradingBean.getUser(username);
            obj.put(0, (user != null ? user.getUserName(): null));

        }
        else if(request.getParameter("userSearch")!= null){
            String username = request.getParameter("userSearch");
            List<UserDTO> users = tradingBean.searchUsers(username);
            
            int i = 0;
            if(users != null){
                for (UserDTO u : users) {
                    JSONObject obj2 = new JSONObject();
                    obj2.put("userName", u.getUserName());
                    obj2.put("firstName", u.getFirstName());
                    obj2.put("lastName", u.getLastName());
                    obj2.put("dateOfBirth", df.format(u.getDateOfBirth()));
                    obj2.put("isAdmin", u.isIsAdmin());
                    obj.put(i, obj2);
                    i++;
                }
            }
        }
        
        response.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(obj.toJSONString());
        
        
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

}
