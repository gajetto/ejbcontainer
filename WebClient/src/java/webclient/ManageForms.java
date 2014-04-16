package webclient;

import com.sun.messaging.jmq.util.MD5;

import dataTransferObjects.StockProductDTO;
import dataTransferObjects.TransactionDTO;
import dataTransferObjects.UserDTO;
import ejb.TradingRemote;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import trading.SessionState;
import trading.SessionStatusRequest;
import trading.TradingTransaction;
import trading.TradingTransactionType;

/**
 * Permits to handle the POST informations posted from HTML forms when it is called
 * @author Jerome
 */
@WebServlet(name = "ManageForms", urlPatterns = {"/manageforms"})
@WebService()
@Stateless()
public class ManageForms extends HttpServlet {
    
    @EJB
    private TradingRemote tradingBean;

    private DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
    
    

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * Handles the information posted via the html form.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException, ParseException {
        
        if(request.getParameter("userNameRegister")!=null){
            String userName = request.getParameter("userNameRegister");
            String password = request.getParameter("password");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            String dateOfBirth = request.getParameter("dateOfBirth");

            Date date = df.parse(dateOfBirth);    
            String hashedPassword = MD5.getHashString(password);
            StockProductDTO product0 = new StockProductDTO(0, 0.0, 0, 0.0);
            StockProductDTO product1 = new StockProductDTO(1, 0.0, 0, 0.0);
            StockProductDTO product2 = new StockProductDTO(2, 0.0, 0, 0.0);
            List<StockProductDTO> products = new ArrayList<>();
            products.add(product2);
            products.add(product1);
            products.add(product0);
            List<TransactionDTO> transactions = new ArrayList<>();
            UserDTO user = new UserDTO(userName, firstName, lastName, date, hashedPassword, false, transactions, products);
            if(tradingBean.registerUser(user)){
                WebAppData.setUser(user);
                response.sendRedirect("trade.jsp");
            }else{
                response.sendRedirect("index.jsp?error=signup");
            }
        }
        else if(request.getParameter("userName")!=null && request.getParameter("password")!=null){
            String hashedPassword = MD5.getHashString(request.getParameter("password"));
            if(tradingBean.authenticateUser(request.getParameter("userName"), hashedPassword)){
                UserDTO user = tradingBean.getUser(request.getParameter("userName"));
                if(user != null){
                    WebAppData.setUser(user);
                    WebAppData.newStockService();
                    response.sendRedirect("trade.jsp"); 
                }
                else{
                    response.sendRedirect("index.jsp?error=user");
                }
            }else{
                response.sendRedirect("index.jsp?error=login");
            }
        }
        
        else if(request.getParameter("passwordChange")!=null){
            String hashedPassword = MD5.getHashString(request.getParameter("passwordChange"));
            UserDTO user;
            if(WebAppData.editOther){
                user = WebAppData.getUserToModifyByAdmin();
            }else{
                user = WebAppData.getUser();
            }
            user.setPassword(hashedPassword);
            if(tradingBean.updateUser(user)){
                response.sendRedirect("trade.jsp");
            }else{
                response.sendRedirect("trade.jsp?error=pwd");
            }
        }
        
        else if(request.getParameter("edit")!=null){
            WebAppData.setEditOther(true);
            String nickname = request.getParameter("edit");
            UserDTO user = tradingBean.getUser(nickname);
            WebAppData.setUserToModifyByAmin(user);
            response.sendRedirect("user.jsp");
        }
        
        else if(request.getParameter("editSelf")!=null){
            WebAppData.setEditOther(false);
            response.sendRedirect("user.jsp");
        }
        
        else if(request.getParameter("delete")!=null){
            UserDTO user = WebAppData.getUserToModifyByAdmin();
            if(tradingBean.deleteUser(user)){
                response.sendRedirect("trade.jsp");
            }else{
                response.sendRedirect("trade.jsp?error=delete");
            }
        }
        
        else if(request.getParameter("userUpdate")!=null){
            UserDTO user;
            if(WebAppData.editOther){
                user = WebAppData.getUserToModifyByAdmin();
            }else{
                user = WebAppData.getUser();
            }
            user.setFirstName(request.getParameter("firstName"));
            user.setLastName(request.getParameter("lastName"));
            Date date = df.parse(request.getParameter("dateOfBirth"));
            user.setDateOfBirth(date);
            if(tradingBean.updateUser(user)){
                response.sendRedirect("trade.jsp");
            }else{
                response.sendRedirect("trade.jsp?error=update");
            }
        }

        else if(request.getParameter("hitButton")!=null){
            int stockNumber = 0;
            String username = WebAppData.getUser().getUserName();
            TradingTransactionType tty;
            StockProductDTO product;
            TradingTransaction tt;
            UserDTO user = WebAppData.getUser();
            boolean trading = false;
            switch (request.getParameter("hitButton")){
                case "buySun":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberSun"));
                    tty = TradingTransactionType.Buy;
                    product = new StockProductDTO("Sun");
//                    tt = new TradingTransaction(tty, product, stockNumber, username);
//                    PTPConnection.sendOrder(tt);
                    user.update(stockNumber, 0, "buy", WebAppData.getStockService().getDaList());
                    WebAppData.setUser(user);
                    StockProductDTO p = (StockProductDTO) WebAppData.getStockService().getDaList().get(0);
                    trading = true;
                    break;
                case "sellSun":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberSun"));
                    tty = TradingTransactionType.Sell;
                    product = new StockProductDTO("Sun");
//                    tt = new TradingTransaction(tty, product, stockNumber, username);
//                    PTPConnection.sendOrder(tt);
                    user.update(stockNumber, 0, "sell", WebAppData.getStockService().getDaList());
                    WebAppData.setUser(user);
                    trading = true;
                    break;
                case "buyIBM":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberIBM"));
                    tty = TradingTransactionType.Buy;
                    product = new StockProductDTO("IBM");
//                    tt = new TradingTransaction(tty, product, stockNumber, username);
//                    PTPConnection.sendOrder(tt);
                    user.update(stockNumber, 2, "buy", WebAppData.getStockService().getDaList());
                    WebAppData.setUser(user);
                    trading = true;
                    break;
                case "sellIBM":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberIBM"));
                    tty = TradingTransactionType.Sell;
                    product = new StockProductDTO("IBM");
//                    tt = new TradingTransaction(tty, product, stockNumber, username);
//                    PTPConnection.sendOrder(tt);
                    user.update(stockNumber, 2, "sell", WebAppData.getStockService().getDaList());
                    WebAppData.setUser(user);
                    trading = true;
                    break;
                case "buyApple":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberApple"));
                    tty = TradingTransactionType.Buy;
                    product = new StockProductDTO("apple");
//                    tt = new TradingTransaction(tty, product, stockNumber, username);
//                    PTPConnection.sendOrder(tt);
                    user.update(stockNumber, 1, "buy", WebAppData.getStockService().getDaList());
                    WebAppData.setUser(user);
                    trading = true;
                    break;
                case "sellApple":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberApple"));
                    tty = TradingTransactionType.Sell;
                    product = new StockProductDTO("apple");
//                    tt = new TradingTransaction(tty, product, stockNumber, username);
//                    PTPConnection.sendOrder(tt);
                    user.update(stockNumber, 1, "sell", WebAppData.getStockService().getDaList());
                    WebAppData.setUser(user);
                    trading = true;
                    break;
            }
            if(trading){
                response.sendRedirect("trade.jsp");
            }else if(request.getParameter("hitLogout")!=null){
//                SessionStatusRequest ssr = new SessionStatusRequest(WebAppData.getTrader().getUserName(), SessionState.Disconnected);
//                PTPConnection.sendStatusRequest(ssr);
                WebAppData.setUser(null);
                response.sendRedirect("index.jsp");
            }
        }   

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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ManageForms.class.getName()).log(Level.SEVERE, null, ex);
        }
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
        try {
            processRequest(request, response);
        } catch (ParseException ex) {
            Logger.getLogger(ManageForms.class.getName()).log(Level.SEVERE, null, ex);
        }
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
