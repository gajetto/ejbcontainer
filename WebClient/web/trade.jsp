<%@ page import="webclient.WebAppData" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Trade</title>
    </head>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script type="text/javascript" src="https://www.google.com/jsapi"></script>
        <script src="js/functions.js"></script>
        <script>
            google.load("visualization", "1", {packages:["corechart"]});
            google.setOnLoadCallback(drawChart);
            $(document).ready(function () {
            var seconds = 6000; // time in milliseconds
            var reload = function() {
               $.ajax({
                  url: 'servletgetstock',
                  cache: false,
                  success: function(data) {
                    console.log(data);
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
                        <input type="submit" name="logout" value="logout" id="logoutButton" onclick="this.form.hitLogout.value=this.name" />
                        <input type="hidden" name="hitLogout"  />
                    <form>
                </div>
                <div class="clear">&nbsp;</div>
            </div>
            <div class="leftDiv">
                <div class="columnTitle">Portfolio of: <%= WebAppData.getTrader().getName() %></div>
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
                                <%= WebAppData.getTrader().getStockQuantity(0) %>
                            </div>
                            <div class="stockResult">
                                <%= WebAppData.getTrader().getStockResult(0) %>
                            </div>
                            <div class="stockBuyingPrice">
                                <%= (WebAppData.getTrader().getStockQuantity(0)==0?0.0:WebAppData.getTrader().getMyStock().get(0).getStockPrice()) %>
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
                                <%= WebAppData.getTrader().getStockQuantity(1) %>
                            </div>
                            <div class="stockResult">
                                <%= WebAppData.getTrader().getStockResult(1) %>
                            </div>
                            <div class="stockBuyingPrice">
                                <%= (WebAppData.getTrader().getStockQuantity(1)==0?0.0:WebAppData.getTrader().getMyStock().get(1).getStockPrice()) %>
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
                                <%= WebAppData.getTrader().getStockQuantity(2) %>
                            </div>
                            <div class="stockResult">
                                <%= WebAppData.getTrader().getStockResult(2) %>
                            </div>
                            <div class="stockBuyingPrice">
                                <%= (WebAppData.getTrader().getStockQuantity(2)==0?0.0:WebAppData.getTrader().getMyStock().get(2).getStockPrice()) %>
                            </div>
                            <input type="text" class="numbersOnly" id="stockNumberIBM" onkeyup="enableButton('IBM');" name="stockNumberIBM" />
                            <input type="submit" value="buy" disabled="disabled" name="buyIBM" id="buyIBM" onclick="this.form.hitButton.value=this.name" />
                            <input type="submit" value="sell" disabled="disabled" name="sellIBM" id="sellIBM" onclick="this.form.hitButton.value=this.name" />
                            <input type="hidden" name="hitButton" />
                        </div>
                    </form>
                    <div id="result">
                        Result: <%= WebAppData.getTrader().getTotalResult() %>
                    </div>
                </div>
            </div>
            <div class="rightDiv">
                <div class="columnTitle">stocks</div>
                <div id="stockList">
                    <div class="newStock"></div>
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