/*
 * UserGUI.java
 *
 * Created on 20 mars 2007, 11:11
 */
package trader;
 
import dataTransferObjects.StockProductDTO;
import java.awt.BorderLayout;
import java.util.ArrayList;
import java.awt.event.*;
import java.lang.Math;
import java.text.DecimalFormat;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.jms.Connection;
import javax.jms.ConnectionFactory;
import javax.jms.JMSException;
import javax.jms.MessageListener;
import javax.jms.MessageProducer;
import javax.jms.ObjectMessage;
import javax.jms.Queue;
import javax.jms.Session;
import javax.jms.Topic;
import javax.jms.TopicConnection;
import javax.jms.TopicConnectionFactory;
import javax.jms.TopicSession;
import javax.jms.TopicSubscriber;
import javax.swing.JOptionPane;
import javax.swing.JTextField;


import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.xy.XYDataset;
import org.jfree.data.xy.XYSeries;
import org.jfree.data.xy.XYSeriesCollection;
import richclient.EvolutionChart;
import trading.TradingTransaction;
import trading.TradingTransactionType;

/**
 *
 */
public class UserGUI extends javax.swing.JFrame { //implements MessageListener {

    private int qtty;
    private Trader trader;
    private int nbStocksReceive = 0;
    private EvolutionChart ec;
//    private MessageListener listener;

    private XYSeries sunStocks = new XYSeries("Sun");
    private XYSeries appleStocks = new XYSeries("Apple");
    private XYSeries ibmStocks = new XYSeries("IBM");

    private String displayText = "";

    private ArrayList<StockProductDTO> stocks = null;
    
    private String name;
    private TraderManager traderM;
    private MarketManager marketM;

//    final private String TOPIC_NAME = "market";
//    final  static String QUEUE_NAME = "Trader";

//    private TopicConnectionFactory connectionFactory = new com.sun.messaging.TopicConnectionFactory();
//
//    private TopicConnection connection = null;

    private double priceSave1 = 0.0;
    private double priceSave2 = 0.0;
    private double priceSave3 = 0.0;

    private double buyingPrice1 = 0.0;
    private double buyingPrice2 = 0.0;
    private double buyingPrice3 = 0.0;

    /**
     * Creates new form UserGUI
     */
    public UserGUI(String name, TraderManager traderM, MarketManager marketM) {

        this.name = name;
        this.traderM = traderM;
        this.marketM = marketM;
        
        marketM.setuGUI(this);
        traderM.setuGUI(this);
        
        ec = new EvolutionChart();

        //cre un nouveau trader pour l'utilisateur
        trader = new Trader(name);
        initComponents();
        jLabel1.setText("Portofolio of " + name);
        System.out.println("Trader " + name + " join the market!");
        displayMyStock();
        this.setVisible(true);

        marketM.clientSubscribe();
    }

    /**
     * Display all the information about the stocks in the view
     */
    private void displayMyStock() {
        jLabel2.setText(trader.getMyStock().get(0).getStockName());
        jLabel3.setText(trader.getMyStock().get(1).getStockName());
        jLabel4.setText(trader.getMyStock().get(2).getStockName());
        jLabel9.setText(trader.getTextQuantity(0));
        jLabel10.setText(trader.getTextResult(0));
        jLabel11.setText(trader.getTextQuantity(1));
        jLabel15.setText(trader.getTextResult(1));
        jLabel13.setText(trader.getTextQuantity(2));
        jLabel14.setText(trader.getTextResult(2));
        jLabel16.setText("" + buyingPrice1);
        jLabel17.setText("" + buyingPrice2);
        jLabel18.setText("" + buyingPrice3);

        jLabel5.setText("Total result: " + trader.getTotalResult());
    }

//    /**
//     * Connection to the JMS in order to enter the market for pub/sub
//     */
//    private void clientSubscribe() {
//        //Connection to JMS
//        try {
//            connection = connectionFactory.createTopicConnection();
//            TopicSession session = connection.createTopicSession(false, Session.AUTO_ACKNOWLEDGE);
//            Topic topic = new com.sun.messaging.Topic(TOPIC_NAME);
//            TopicSubscriber subscriber = session.createSubscriber(topic);
////                TopicSubscriber subscriber = session.createDurableSubscriber(topic, trader.getName());
//            listener = this;
//            subscriber.setMessageListener(listener);
//            connection.start();
//
//        } catch (Exception e) {
//            System.out.println("Exception occurred: " + e.toString());
//            System.exit(1);
//        }
//
//        System.out.println("Client started!!");
//
//        synchronized (listener) {
//            try {
//                listener.wait();
//            } catch (InterruptedException ex) {
//                Logger.getLogger(UserGUI.class.getName()).log(Level.SEVERE, null, ex);
//            }
//        }
//    }
    
//    /**
//     * Connection to JMS for the point to point when a buying or a selling happen
//     */
//    private void clientPTP(String type, int stockID) {
//        TradingTransactionType tty; 
//        if (type.equals("buy")) {
//            tty = TradingTransactionType.Buy;
//        }
//        else {
//            tty = TradingTransactionType.Sell;
//        }
//        
//        StockProduct p = new StockProduct(trader.getMyStock().get(stockID).getStockName());
//        TradingTransaction tt = new TradingTransaction(tty, p, qtty, trader.getName());
//        
//        ConnectionFactory connectionFactory = new com.sun.messaging.ConnectionFactory();
//        Connection connection = null;
//        try {
//            connection = connectionFactory.createConnection();
//            Queue queue = new com.sun.messaging.Queue(QUEUE_NAME);
//            Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
//            MessageProducer producer = session.createProducer(queue);
//            ObjectMessage message = session.createObjectMessage(tt);
//            System.out.println("Sending order [" + message.getObject().toString()+ "]");
//            producer.send(message);
//            
//            connection.close();
//            
//        } catch (Exception e) {
//            System.out.println("Exception occurred: " + e.toString());
//            System.exit(1);
//        }
//    }
    
//    /**
//     * Get the object from the JMS
//     * @param message 
//     */
//    @Override
//    public void onMessage(javax.jms.Message message) {
//
//        ObjectMessage stockProducts = null;
//        stockProducts = (ObjectMessage) message;
//        try {
//            stocks = (ArrayList<StockProduct>) stockProducts.getObject();
//        } catch (JMSException ex) {
//            Logger.getLogger(UserGUI.class.getName()).log(Level.SEVERE, null, ex);
//        }
//        updateStocks();
//        synchronized (this) {
//            this.notify();
//        }
//    }

    /**
     * Update the field on the GUI and the chart
     */
    public void updateStocks() {

        DecimalFormat df = new DecimalFormat("##0.00");
        double ratePrice1 = 0.0;
        double ratePrice2 = 0.0;
        double ratePrice3 = 0.0;

        if ((priceSave1 != 0) && (priceSave2 != 0) && (priceSave3 != 0)) {
            ratePrice1 = ((stocks.get(0).getStockPrice() / priceSave1) - 1) * 100;
            ratePrice2 = ((stocks.get(1).getStockPrice() / priceSave2) - 1) * 100;
            ratePrice3 = ((stocks.get(2).getStockPrice() / priceSave3) - 1) * 100;
        }

        //TextArea panel
        displayText = "          ***************New prices*********************\n"
                + "          " + stocks.get(0).getStockName() + ": " + stocks.get(0).getStockPrice() + ", Rate: " + df.format(ratePrice1) + "%\n"
                + "          " + stocks.get(1).getStockName() + ": " + stocks.get(1).getStockPrice() + ", Rate: " + df.format(ratePrice2) + "%\n"
                + "          " + stocks.get(2).getStockName() + ": " + stocks.get(2).getStockPrice() + ", Rate: " + df.format(ratePrice3) + "%\n"
                + "          " + "________________________________________" + "\n"
                + displayText;
        jTextArea1.setText(displayText.toString());

        priceSave1 = stocks.get(0).getStockPrice();
        priceSave2 = stocks.get(1).getStockPrice();
        priceSave3 = stocks.get(2).getStockPrice();

        //JPanel 2 Graphical chart
        JFreeChart chart = ec.createChart(updateDataset());
        ChartPanel chartPanel = new ChartPanel(chart);
        chartPanel.setPreferredSize(new java.awt.Dimension(500, 270));
        chartPanel.setVisible(true);
        jPanel2.add(BorderLayout.CENTER, chartPanel);
        jPanel2.repaint();

        ++nbStocksReceive;
    }

    /**
     * Creates a sample dataset
     */
    public XYDataset updateDataset() {

        sunStocks.add(nbStocksReceive, stocks.get(0).getStockPrice());

        appleStocks.add(nbStocksReceive, stocks.get(1).getStockPrice());

        ibmStocks.add(nbStocksReceive, stocks.get(2).getStockPrice());

        final XYSeriesCollection dataset = new XYSeriesCollection();
        dataset.addSeries(sunStocks);
        dataset.addSeries(appleStocks);
        dataset.addSeries(ibmStocks);

        return dataset;

    }

    /**
     * Check the field when a buying is done and if something is wrong, display a dialog box
     * @param jtf JTextField
     * @return boolean true if the field is a number, false otherwise
     */
    private boolean checkBuyField(JTextField jtf) {
        int qttyTemp = 0;
        boolean ok = false;
        String value = null;
        if (!jtf.getText().trim().equals("")) {
            value = jtf.getText().trim();
            if (value.contains("b")) {      
                value = value.substring(0, value.length() - 1);
            }
            try {
                //qttyTemp = new Integer(jtf.getText().trim()).intValue();
                qttyTemp = new Integer(value).intValue();
                ok = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error, " + e + " is not a number. "
                        + "Please try again.", "Warning", JOptionPane.ERROR_MESSAGE);
                ok = false;
                jtf.setText("");
            }
            if (qttyTemp < 0) {
                qtty = Math.abs(qttyTemp);
                JOptionPane.showMessageDialog(this, "A negative number has been detected and will be convert to a positive number. ", "Warning", JOptionPane.ERROR_MESSAGE);
            } else {
                qtty = qttyTemp;
            }
        }
        return ok;
    }

    /**
     * Check the field when a selling is done and if something is wrong, display a dialog box
     * @param jtf JTextField
     * @param stockID int
     * @return boolean true if the field is a number, false otherwise
     */
    private boolean checkSellField(JTextField jtf, int stockID) {
        int qttyTemp = 0;
        boolean ok = false;
        String value = null;
        if (!jtf.getText().trim().equals("")) {
            value = jtf.getText().trim();
            if (value.contains("s")) {           
                value = value.substring(0, value.length() - 1);
            }
            try {
                qttyTemp = new Integer(value).intValue();
                ok = true;
            } catch (NumberFormatException e) {
                JOptionPane.showMessageDialog(this, "Error, " + e + " is not a number. "
                        + "Please try again.", "Warning", JOptionPane.ERROR_MESSAGE);
                ok = false;
                jtf.setText("");
            }
            if (qttyTemp < 0) {
                qttyTemp = Math.abs(qttyTemp);
                qtty = Math.abs(qttyTemp);
                JOptionPane.showMessageDialog(this, "A negative number has been detected and will be convert to a positive number. ", "Warning", JOptionPane.ERROR_MESSAGE);
            }
            if (qttyTemp > trader.getMyStock().get(stockID).getStockQty()) {
                JOptionPane.showMessageDialog(this, "You can not sell more than you have in stocks.", "Warning", JOptionPane.ERROR_MESSAGE);
                jtf.setText("");
                ok = false;
            }else{
                qtty = qttyTemp;
            }
        }
        return ok;
    }

    /**
     * Action when the selling button is pressed
     * @param jtf JTextField
     * @param stockID int
     */
    private void sellAction(JTextField jtf, int stockID) {
        String type = "sell";

        if (checkSellField(jtf, stockID)) {
            traderM.clientPTP(type, stockID);
            trader.update(qtty, stockID, type, stocks);
            jtf.setText("");
            qtty=0;

            if (trader.getMyStock().get(stockID).getStockQty() == 0) {
                if (stockID == 2) {
                    buyingPrice3 = 0;
                } else if (stockID == 1) {
                    buyingPrice2 = 0;
                } else {
                    buyingPrice1 = 0;
                }

            }
            displayMyStock();
        }
    }

    /**
     * Action when the buying button is pressed
     * @param jtf JTextField
     * @param stockID int
     */
    private void buyAction(JTextField jtf, int stockID) {
        String type = "buy";

        if (checkBuyField(jtf)) {
            traderM.clientPTP(type, stockID);
            trader.update(qtty, stockID, type, stocks);
            jtf.setText("");

            if (qtty > 0) {
                if (stockID == 2) {
                    if (buyingPrice3 == 0) {
                        buyingPrice3 = stocks.get(stockID).getStockPrice();
                    } else {
                        buyingPrice3 = (buyingPrice3 + stocks.get(stockID).getStockPrice()) / 2;
                    }
                } else if (stockID == 1) {
                    if (buyingPrice2 == 0) {
                        buyingPrice2 = stocks.get(stockID).getStockPrice();
                    } else {
                        buyingPrice2 = (buyingPrice2 + stocks.get(stockID).getStockPrice()) / 2;
                    }
                } else {
                    if (buyingPrice1 == 0) {
                        buyingPrice1 = stocks.get(stockID).getStockPrice();
                    } else {
                        buyingPrice1 = (buyingPrice1 + stocks.get(stockID).getStockPrice()) / 2;
                    }
                }
            }
            displayMyStock();
        }
    }

    public ArrayList<StockProductDTO> getStocks() {
        return stocks;
    }

    public void setStocks(ArrayList<StockProductDTO> stocks) {
        this.stocks = stocks;
    }

    public int getQtty() {
        return qtty;
    }

    public void setQtty(int qtty) {
        this.qtty = qtty;
    }

    public Trader getTrader() {
        return trader;
    }

    public void setTrader(Trader trader) {
        this.trader = trader;
    }
    
    

    
    
    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jpanel3 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        qttbuypdtField1 = new javax.swing.JTextField();
        qttbuypdtField2 = new javax.swing.JTextField();
        qttbuypdtField3 = new javax.swing.JTextField();
        jBuy1 = new javax.swing.JButton();
        jSell1 = new javax.swing.JButton();
        jBuy2 = new javax.swing.JButton();
        jSell2 = new javax.swing.JButton();
        jBuy3 = new javax.swing.JButton();
        jSell3 = new javax.swing.JButton();
        jLabel5 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel13 = new javax.swing.JLabel();
        jLabel14 = new javax.swing.JLabel();
        jLabel15 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        jLabel17 = new javax.swing.JLabel();
        jLabel18 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jLabel20 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        jPanel1 = new javax.swing.JPanel();
        jTabbedPane1 = new javax.swing.JTabbedPane();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTextArea1 = new javax.swing.JTextArea();
        jPanel2 = new javax.swing.JPanel();
        jMenuBar1 = new javax.swing.JMenuBar();
        jMenu1 = new javax.swing.JMenu();
        jMenuItem1 = new javax.swing.JMenuItem();
        jMenu2 = new javax.swing.JMenu();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        setMinimumSize(new java.awt.Dimension(1103, 578));
        setResizable(false);

        jpanel3.setBackground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        jpanel3.setForeground(javax.swing.UIManager.getDefaults().getColor("Button.light"));
        jpanel3.setPreferredSize(new java.awt.Dimension(770, 529));
        jpanel3.setRequestFocusEnabled(false);

        jLabel1.setFont(new java.awt.Font("Futura", 0, 24)); // NOI18N
        jLabel1.setText("Portfolio");

        jLabel2.setText("pdtLabel1");

        jLabel3.setText("pdtLabel2");

        jLabel4.setText("pdtLabel3");

        qttbuypdtField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qttbuypdtField1ActionPerformed(evt);
            }
        });
        qttbuypdtField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qttbuypdtField1KeyReleased(evt);
            }
        });

        qttbuypdtField2.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qttbuypdtField2KeyReleased(evt);
            }
        });

        qttbuypdtField3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                qttbuypdtField3ActionPerformed(evt);
            }
        });
        qttbuypdtField3.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                qttbuypdtField3KeyReleased(evt);
            }
        });

        jBuy1.setText("Buy");
        jBuy1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuy1ActionPerformed(evt);
            }
        });

        jSell1.setText("Sell");
        jSell1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSell1ActionPerformed(evt);
            }
        });

        jBuy2.setText("Buy");
        jBuy2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuy2ActionPerformed(evt);
            }
        });

        jSell2.setText("Sell");
        jSell2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSell2ActionPerformed(evt);
            }
        });

        jBuy3.setText("Buy");
        jBuy3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jBuy3ActionPerformed(evt);
            }
        });

        jSell3.setText("Sell");
        jSell3.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jSell3ActionPerformed(evt);
            }
        });

        jLabel5.setText("totalLabel");

        jLabel7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/images/logodop.gif"))); // NOI18N

        jLabel6.setText("Quantity");

        jLabel8.setText("Results");

        jLabel9.setText("jLabel9");

        jLabel10.setText("jLabel10");

        jLabel11.setText("jLabel11");

        jLabel13.setText("jLabel13");

        jLabel14.setText("jLabel14");

        jLabel15.setText("jLabel15");

        jLabel12.setText("Buying price");

        jLabel16.setText("jLabel16");

        jLabel17.setText("jLabel17");

        jLabel18.setText("jLabel18");

        jLabel19.setText("Shortkeys:");

        jLabel20.setText("s - to sell");

        jLabel21.setText("b - to buy");

        org.jdesktop.layout.GroupLayout jpanel3Layout = new org.jdesktop.layout.GroupLayout(jpanel3);
        jpanel3.setLayout(jpanel3Layout);
        jpanel3Layout.setHorizontalGroup(
            jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpanel3Layout.createSequentialGroup()
                .addContainerGap()
                .add(jLabel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .add(656, 656, 656))
            .add(jpanel3Layout.createSequentialGroup()
                .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jpanel3Layout.createSequentialGroup()
                        .add(50, 50, 50)
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel4, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jLabel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 63, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(18, 18, 18)
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jLabel6)
                            .add(jLabel9)
                            .add(jLabel11)
                            .add(jLabel13))
                        .add(10, 10, 10)
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jLabel8)
                            .add(jLabel10)
                            .add(jLabel15)
                            .add(jLabel14))
                        .add(18, 18, 18)
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.CENTER)
                            .add(jLabel12)
                            .add(jLabel16)
                            .add(jLabel17)
                            .add(jLabel18))
                        .add(9, 9, 9)
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                            .add(jpanel3Layout.createSequentialGroup()
                                .add(qttbuypdtField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jBuy1)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jSell1))
                            .add(jpanel3Layout.createSequentialGroup()
                                .add(qttbuypdtField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jBuy3)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jSell3))
                            .add(jpanel3Layout.createSequentialGroup()
                                .add(qttbuypdtField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 51, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                                .add(jBuy2)
                                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                                .add(jSell2))))
                    .add(jpanel3Layout.createSequentialGroup()
                        .add(50, 50, 50)
                        .add(jLabel5))
                    .add(jpanel3Layout.createSequentialGroup()
                        .add(18, 18, 18)
                        .add(jLabel7))
                    .add(jpanel3Layout.createSequentialGroup()
                        .add(320, 320, 320)
                        .add(jLabel19)
                        .addPreferredGap(org.jdesktop.layout.LayoutStyle.UNRELATED)
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                            .add(jLabel20)
                            .add(jLabel21))))
                .addContainerGap(org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jpanel3Layout.setVerticalGroup(
            jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jpanel3Layout.createSequentialGroup()
                .add(jLabel7, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 80, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel1)
                .add(18, 18, 18)
                .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel6)
                    .add(jLabel8)
                    .add(jLabel12))
                .add(31, 31, 31)
                .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
                    .add(jpanel3Layout.createSequentialGroup()
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel2, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel9)
                            .add(jLabel10)
                            .add(jLabel16))
                        .add(24, 24, 24)
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel3, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel11)
                            .add(jLabel15)
                            .add(qttbuypdtField2, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jBuy2)
                            .add(jSell2)
                            .add(jLabel17))
                        .add(19, 19, 19)
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jLabel4, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .add(jLabel13)
                            .add(jLabel14)
                            .add(qttbuypdtField3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                            .add(jBuy3)
                            .add(jSell3)
                            .add(jLabel18)))
                    .add(jpanel3Layout.createSequentialGroup()
                        .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                            .add(jSell1)
                            .add(jBuy1)
                            .add(qttbuypdtField1, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                        .add(0, 0, Short.MAX_VALUE)))
                .add(18, 18, 18)
                .add(jLabel5)
                .add(129, 129, 129)
                .add(jpanel3Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.BASELINE)
                    .add(jLabel19)
                    .add(jLabel21))
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jLabel20)
                .add(50, 50, 50))
        );

        jPanel1.setBackground(new java.awt.Color(255, 255, 255));

        jTabbedPane1.setPreferredSize(new java.awt.Dimension(300, 124));

        jTextArea1.setEditable(false);
        jTextArea1.setColumns(20);
        jTextArea1.setRows(5);
        jScrollPane1.setViewportView(jTextArea1);

        jTabbedPane1.addTab("Text", jScrollPane1);

        jPanel2.setLayout(new java.awt.BorderLayout());
        jTabbedPane1.addTab("Graphical chart", jPanel2);

        org.jdesktop.layout.GroupLayout jPanel1Layout = new org.jdesktop.layout.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, 171, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(jTabbedPane1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );

        jMenu1.setText("File");

        jMenuItem1.setAccelerator(javax.swing.KeyStroke.getKeyStroke(java.awt.event.KeyEvent.VK_Q, java.awt.event.InputEvent.CTRL_MASK));
        jMenuItem1.setText("Quit");
        jMenuItem1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jMenuItem1ActionPerformed(evt);
            }
        });
        jMenu1.add(jMenuItem1);

        jMenuBar1.add(jMenu1);

        jMenu2.setText("Edit");
        jMenuBar1.add(jMenu2);

        setJMenuBar(jMenuBar1);

        org.jdesktop.layout.GroupLayout layout = new org.jdesktop.layout.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(layout.createSequentialGroup()
                .addContainerGap()
                .add(jpanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 600, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(org.jdesktop.layout.LayoutStyle.RELATED)
                .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(org.jdesktop.layout.GroupLayout.LEADING)
            .add(org.jdesktop.layout.GroupLayout.TRAILING, layout.createSequentialGroup()
                .addContainerGap()
                .add(layout.createParallelGroup(org.jdesktop.layout.GroupLayout.TRAILING)
                    .add(jPanel1, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, org.jdesktop.layout.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .add(jpanel3, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE, 532, org.jdesktop.layout.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void jSell3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSell3ActionPerformed
        sellAction(qttbuypdtField3, 2);
    }//GEN-LAST:event_jSell3ActionPerformed

    private void jBuy3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuy3ActionPerformed
        buyAction(qttbuypdtField3, 2);
    }//GEN-LAST:event_jBuy3ActionPerformed

    private void jSell2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSell2ActionPerformed
        sellAction(qttbuypdtField2, 1);
    }//GEN-LAST:event_jSell2ActionPerformed

    private void jBuy2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuy2ActionPerformed
        buyAction(qttbuypdtField2, 1);
    }//GEN-LAST:event_jBuy2ActionPerformed

    private void jSell1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jSell1ActionPerformed
        sellAction(qttbuypdtField1, 0);
    }//GEN-LAST:event_jSell1ActionPerformed

    private void jBuy1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jBuy1ActionPerformed
        buyAction(qttbuypdtField1, 0);
    }//GEN-LAST:event_jBuy1ActionPerformed

    private void qttbuypdtField3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qttbuypdtField3ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qttbuypdtField3ActionPerformed

    private void qttbuypdtField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_qttbuypdtField1ActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_qttbuypdtField1ActionPerformed

    private void qttbuypdtField1KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qttbuypdtField1KeyReleased
        int key = evt.getKeyCode();

        if (key == KeyEvent.VK_S) {
            sellAction(qttbuypdtField1, 0);
        } else if (key == KeyEvent.VK_B) {
            buyAction(qttbuypdtField1, 0);
        }
    }//GEN-LAST:event_qttbuypdtField1KeyReleased

    private void qttbuypdtField2KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qttbuypdtField2KeyReleased
        int key = evt.getKeyCode();

        if (key == KeyEvent.VK_S) {
            sellAction(qttbuypdtField2, 1);
        } else if (key == KeyEvent.VK_B) {
            buyAction(qttbuypdtField2, 1);
        }
    }//GEN-LAST:event_qttbuypdtField2KeyReleased

    private void qttbuypdtField3KeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_qttbuypdtField3KeyReleased
        int key = evt.getKeyCode();

        if (key == KeyEvent.VK_S) {
            sellAction(qttbuypdtField3, 2);
        } else if (key == KeyEvent.VK_B) {
            buyAction(qttbuypdtField3, 2);
        }
    }//GEN-LAST:event_qttbuypdtField3KeyReleased

    private void jMenuItem1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jMenuItem1ActionPerformed
        traderM.deconnection(name);
        System.exit(1);
    }//GEN-LAST:event_jMenuItem1ActionPerformed

    /**
     * @param args the command line arguments
     */

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton jBuy1;
    private javax.swing.JButton jBuy2;
    private javax.swing.JButton jBuy3;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JMenu jMenu1;
    private javax.swing.JMenu jMenu2;
    private javax.swing.JMenuBar jMenuBar1;
    private javax.swing.JMenuItem jMenuItem1;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton jSell1;
    private javax.swing.JButton jSell2;
    private javax.swing.JButton jSell3;
    private javax.swing.JTabbedPane jTabbedPane1;
    private javax.swing.JTextArea jTextArea1;
    private javax.swing.JPanel jpanel3;
    private javax.swing.JTextField qttbuypdtField1;
    private javax.swing.JTextField qttbuypdtField2;
    private javax.swing.JTextField qttbuypdtField3;
    // End of variables declaration//GEN-END:variables

}
