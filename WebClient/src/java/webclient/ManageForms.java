package webclient;

import beans.TradingRemote;
import com.sun.messaging.jmq.util.MD5;
import dataTransferObjects.UserDTO;
import trading.Trader;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
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
import trading.StockProduct;
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

            DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
            Date date = df.parse(dateOfBirth);    
            String hashedPassword = MD5.getHashString(password);
            UserDTO user = new UserDTO(userName, firstName, lastName, date, hashedPassword, false);
            if(tradingBean.registerUser(user)){
                WebAppData.newTrader(user);
                response.sendRedirect("trade.jsp");
            }else{
                response.sendRedirect("index.jsp?error=signup");
            }
        }
        else if(request.getParameter("userName")!=null){
//            WebAppData.newTrader(request.getParameter("userName"));
            WebAppData.newStockService();
            response.sendRedirect("trade.jsp");
        }

        else if(request.getParameter("hitButton")!=null){
            int stockNumber = 0;
            String traderName = WebAppData.getTrader().getUserName();
            TradingTransactionType tty;
            StockProduct product;
            TradingTransaction tt;
            Trader trader = WebAppData.getTrader();
            boolean trading = false;
            switch (request.getParameter("hitButton")){
                case "buySun":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberSun"));
                    tty = TradingTransactionType.Buy;
                    product = new StockProduct("Sun");
                    tt = new TradingTransaction(tty, product, stockNumber, traderName);
                    PTPConnection.sendOrder(tt);
                    trader.update(stockNumber, 0, "buy", WebAppData.getStockService().getDaList());
                    WebAppData.updateTrader(trader);
                    StockProduct p = (StockProduct) WebAppData.getStockService().getDaList().get(0);
                    trading = true;
                    break;
                case "sellSun":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberSun"));
                    tty = TradingTransactionType.Sell;
                    product = new StockProduct("Sun");
                    tt = new TradingTransaction(tty, product, stockNumber, traderName);
                    PTPConnection.sendOrder(tt);
                    trader.update(stockNumber, 0, "sell", WebAppData.getStockService().getDaList());
                    WebAppData.updateTrader(trader);
                    trading = true;
                    break;
                case "buyIBM":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberIBM"));
                    tty = TradingTransactionType.Buy;
                    product = new StockProduct("IBM");
                    tt = new TradingTransaction(tty, product, stockNumber, traderName);
                    PTPConnection.sendOrder(tt);
                    trader.update(stockNumber, 2, "buy", WebAppData.getStockService().getDaList());
                    WebAppData.updateTrader(trader);
                    trading = true;
                    break;
                case "sellIBM":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberIBM"));
                    tty = TradingTransactionType.Sell;
                    product = new StockProduct("IBM");
                    tt = new TradingTransaction(tty, product, stockNumber, traderName);
                    PTPConnection.sendOrder(tt);
                    trader.update(stockNumber, 2, "sell", WebAppData.getStockService().getDaList());
                    WebAppData.updateTrader(trader);
                    trading = true;
                    break;
                case "buyApple":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberApple"));
                    tty = TradingTransactionType.Buy;
                    product = new StockProduct("apple");
                    tt = new TradingTransaction(tty, product, stockNumber, traderName);
                    PTPConnection.sendOrder(tt);
                    trader.update(stockNumber, 1, "buy", WebAppData.getStockService().getDaList());
                    WebAppData.updateTrader(trader);
                    trading = true;
                    break;
                case "sellApple":
                    stockNumber = Integer.parseInt(request.getParameter("stockNumberApple"));
                    tty = TradingTransactionType.Sell;
                    product = new StockProduct("apple");
                    tt = new TradingTransaction(tty, product, stockNumber, traderName);
                    PTPConnection.sendOrder(tt);
                    trader.update(stockNumber, 1, "sell", WebAppData.getStockService().getDaList());
                    WebAppData.updateTrader(trader);
                    trading = true;
                    break;
            }
            if(trading){
                response.sendRedirect("trade.jsp");
            }else if(request.getParameter("hitLogout")!=null){
                SessionStatusRequest ssr = new SessionStatusRequest(WebAppData.getTrader().getUserName(), SessionState.Disconnected);
                PTPConnection.sendStatusRequest(ssr);
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
