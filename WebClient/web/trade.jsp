<%@page import="dataTransferObjects.StockProductDTO"%>
<%@page import="com.google.common.collect.Lists"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.util.List"%>
<%@page import="dataTransferObjects.TransactionDTO"%>
<%@ page import="webclient.WebAppData" %>
<%
    if(WebAppData.getUser() == null){
        response.sendRedirect("index.jsp");
    }
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Trade</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link rel="stylesheet" href="http://code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="http://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script src="js/stockFunctions.js"></script>
        <script>
            $(function() {
                $( "#tabs" ).tabs();
              });
            google.load("visualization", "1", {packages:["corechart"]});
            google.setOnLoadCallback(drawChart);
            $(document).ready(function () {
            var seconds = 6000; // time in milliseconds
            var reload = function() {
               $.ajax({
                  url: 'ServletGetStock',
                  cache: false,
                  success: function(data) {
                    printStock(data);
                    drawChart(getChartData(data));                     
                    setTimeout(function() {
                        reload();
                    }, seconds);
                 }
               });
             };
             reload();
        });
      
      
        </script>
    </head>
    <body>
        <div id="content">
            <div id="header">
                <div id="imgHeader">
                    <img src="images/logodop.gif" />
                </div>
                <div id="logoutDiv">
                    <form name="logout" action="manageforms" method="post">
                    <%
                        if(WebAppData.getUser().isIsAdmin()){
                             out.println("<a href=\"chooseUser.jsp\">");
                             out.println("  <input type=\"button\" class=\"button long\" value=\"admin\" />");
                             out.println("</a>");
                        }
                    %>
                        <input type="submit" name="logout" value="logout" id="logoutButton" class="redButton long" onclick="this.form.hitLogout.value=this.name" />
                        <input type="hidden" name="hitLogout"  />
                    <form>
                </div>
                <div class="clear">&nbsp;</div>
            </div>
            <div class="leftDiv">
                <div class="columnTitle">
                    Portfolio of: 
                    <a href="manageforms?editSelf=true">
                    <%= WebAppData.getUser().getUserName() %>
                    </a>
                </div>
                <div id="operations">
                    <div class="stockName">
                        &nbsp;
                    </div>
                    <div class="stockQtty">
                        Qtty
                    </div>
                    <div class="stockResult">
                       Result
                    </div>
                    <div class="stockBuyingPrice">
                        B price
                    </div>
                    <div class="clear">&nbsp;</div>
                    <form name="tradeStocks" action="manageforms" method="post">
                        <div class="stock">
                            <div class="stockName">
                                <label for="sunStockNumber">Sun: </label>
                            </div>
                            <div class="stockQtty" id="boughtSun">
                                <%= WebAppData.getUser().getMyStock().get(0).getStockQty() %>
                            </div>
                            <div class="stockResult">
                                <%= WebAppData.getUser().getMyStock().get(0).getResult() %>
                            </div>
                            <div class="stockBuyingPrice">
                                <%= (WebAppData.getUser().getMyStock().get(0).getStockQty()==0?0.0:WebAppData.getUser().getMyStock().get(0).getStockPrice()) %>
                            </div>
                            <input type="text" class="numbersOnly" id="stockNumberSun" onkeyup="enableButton('Sun');" name="stockNumberSun" />
                            <input type="submit" value="buy" id="buySun" name="buySun" disabled="disabled" onclick="this.form.hitButton.value=this.name" />
                            <input type="submit" value="sell" id="sellSun" name="sellSun" disabled="disabled" onclick="this.form.hitButton.value=this.name" />
                        </div>
                        <br />
                        <div class="stock">
                            <div class="stockName">
                                <label for="appleStockNumber">Apple: </label>
                            </div>
                            <div class="stockQtty" id="boughtApple">
                                <%= WebAppData.getUser().getMyStock().get(1).getStockQty() %>
                            </div>
                            <div class="stockResult">
                                <%= WebAppData.getUser().getMyStock().get(1).getResult() %>
                            </div>
                            <div class="stockBuyingPrice">
                                <%= (WebAppData.getUser().getMyStock().get(1).getStockQty()==0?0.0:WebAppData.getUser().getMyStock().get(1).getStockPrice()) %>
                            </div>
                            <input type="text" class="numbersOnly" id="stockNumberApple" onkeyup="enableButton('Apple');" name="stockNumberApple" />
                            <input type="submit" value="buy" id="buyApple" disabled="disabled" name="buyApple" onclick="this.form.hitButton.value=this.name" />
                            <input type="submit" value="sell" id="sellApple" disabled="disabled" name="sellApple" onclick="this.form.hitButton.value=this.name" />
                        </div>
                        <br />
                        <div class="stock">
                            <div class="stockName">
                                <label for="IBMStockNumber">IBM: </label>
                            </div>
                            <div class="stockQtty" id="boughtIBM">
                                <%= WebAppData.getUser().getMyStock().get(2).getStockQty() %>
                            </div>
                            <div class="stockResult">
                                <%= WebAppData.getUser().getMyStock().get(2).getResult() %>
                            </div>
                            <div class="stockBuyingPrice">
                                <%= (WebAppData.getUser().getMyStock().get(2).getStockQty()==0?0.0:WebAppData.getUser().getMyStock().get(2).getStockPrice()) %>
                            </div>
                            <input type="text" class="numbersOnly" id="stockNumberIBM" onkeyup="enableButton('IBM');" name="stockNumberIBM" />
                            <input type="submit" value="buy" disabled="disabled" name="buyIBM" id="buyIBM" onclick="this.form.hitButton.value=this.name" />
                            <input type="submit" value="sell" disabled="disabled" name="sellIBM" id="sellIBM" onclick="this.form.hitButton.value=this.name" />
                            <input type="hidden" name="hitButton" />
                        </div>
                    </form>
                    <div id="result">
                        
                    </div>
                </div>
            </div>
            <div class="rightDiv">
                <div id="tabs">
                    <ul>
                        <li>
                            <a href="#tabs-1">
                            Stock history
                            </a>
                        </li>
                        <li>
                            <a href="#tabs-2">
                            Transaction history
                            </a>
                        </li>
                    </ul>
                    <div class="stockList" id="tabs-1">
                        <div class="columnTitle">stocks</div>
                        <div class="newStock"></div>
                    </div>
                    <div class="transactionList" id="tabs-2">
                        <div class="columnTitle">transactions</div>
                        <%
                            DateFormat df = new SimpleDateFormat("dd.MM.yyyy hh:mm");
                            if(!WebAppData.getUser().getTransactionList().isEmpty()){
                                List<TransactionDTO> transactions = Lists.reverse(WebAppData.getUser().getTransactionList());
                                for(TransactionDTO transaction : transactions){
                                    
                                    String stockName = ((StockProductDTO)WebAppData.getStockService().getDaList().get(transaction.getStockID())).getStockName();
                                    
                                    out.println("<div class=\"newTransaction\">");
                                    out.println(df.format(transaction.getTransactionDate())+"<br />"+(transaction.isIsBuy()?"bought ":"sold ")+transaction.getQty()+ " stocks from "+stockName+" at $"+transaction.getStockPrice());
                                    out.println("<br /><br />");
                                    out.println("</div>");
                                }
                            }
                        %>
                        <div class="newTransaction"></div>
                    </div>
                </div>
            </div>
            <div id="chart_div">
                
            </div>
            <div id="footer">
                MockStock, group 2, HEC march 2014
            </div>
        </div>
    </body>
</html>