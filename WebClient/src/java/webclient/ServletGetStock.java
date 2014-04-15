package webclient;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import dataTransferObjects.StockProductDTO;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.ArrayList;
import javax.jms.*;
import javax.naming.InitialContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.json.simple.JSONObject;

/**
 * Permits to update the stock list whenever it's called
 * @author Jerome
 */
@WebServlet(urlPatterns = {"/ServletGetStock"})
public class ServletGetStock extends HttpServlet implements MessageListener {

    private ArrayList<StockProductDTO> stocks;
    
    @Override
    public synchronized void init() throws ServletException {
        this.stocks = new ArrayList<>();
        initializeSubscriber();
    }
    
    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     * 
     * Send the stock list when it is called
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        ArrayList<StockProductDTO> list = WebAppData.getHistoryStocks();
        
        DecimalFormat df = new DecimalFormat("##0.00");
        double ratePrice = 0.0;
        JSONObject obj2 = new JSONObject();
        
        if(!list.isEmpty()){
            for(int i=0; i<list.size()/3; i++){
            JSONObject obj = new JSONObject();
                for(int j=0; j<3; j++){
                    obj.put(list.get((3*i)+j).getStockName(), list.get((3*i)+j).getStockPrice());
                    if((i+1)<list.size()/3 && list.get((3*(i+1))+j).getStockPrice() != 0.0){
                        ratePrice = ((list.get((3*i)+j).getStockPrice()/ list.get((3*(i+1))+j).getStockPrice()) - 1) * 100;
                    }else{
                        ratePrice = 0.0;
                    }
                    obj.put(list.get(j).getStockName()+"Rate", df.format(ratePrice)+"%");
                }
                obj2.put(i, obj);
            }
        }
       
        response.setContentType("application/json");  // Set content type of the response so that jQuery knows what it can expect.
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(obj2.toJSONString());
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
     * initialize a subscriber connection that reads messages sent from the market
     */
        public void initializeSubscriber(){
            final String TOPIC_NAME = "market";

            TopicConnectionFactory connectionFactory = new com.sun.messaging.TopicConnectionFactory();

            TopicConnection connection = null;
            try {

                connection = connectionFactory.createTopicConnection();
                TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
                Topic topic = new com.sun.messaging.Topic(TOPIC_NAME);
                TopicSubscriber subscriber = session.createSubscriber(topic);
                MessageListener listener = new ServletGetStock();
                subscriber.setMessageListener(listener);
                connection.start();
                synchronized (listener) {
                    //listener.wait();
                }
            } catch (Exception e) {
                System.out.println("Exception occurred: " + e.toString());
                System.exit(1);
            }

            System.out.println("Client started!!");
        }
    
    /**
     * Reads the messages sent from the market and stores them in a list
     * @param message the message received from the market
     */
    @Override
    public void onMessage(javax.jms.Message message) {
        
        ObjectMessage stockProducts = null;
        stockProducts = (ObjectMessage) message;
        try {
            this.stocks = (ArrayList<StockProductDTO>) stockProducts.getObject();
            WebAppData.addStocks(this.stocks);
            WebAppData.newStockService();
            WebAppData.setStockServiceList(this.stocks);
        } catch (JMSException ex) {
            
        }
            synchronized (this) {
                this.notify();
            }
    }

}