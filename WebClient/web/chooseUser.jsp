<%@ page import="webclient.WebAppData" %>
<% 
    if(WebAppData.getTrader() == null || WebAppData.getTrader().getUser() == null){
        response.sendRedirect("index.jsp");
    }else if(!WebAppData.getTrader().getUser().isIsAdmin()){
        response.sendRedirect("trade.jsp");
    }
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Register</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="js/searchFunctions.js"></script>
    </head>
    <body>
        <div id="content">
            <div id="header">
                <div id="imgHeader">
                    <img src="images/logodop.gif" />
                </div>
                
                <div class="clear">&nbsp;</div>
            </div>
            <div id="centerDiv">
                <div class="center">
                    <img src="images/logoMockStock.jpg" />
                </div>
                <div>
                    <span class="columnTitle">Search for a user</span>
                    <br /><br />
                    <input type="text" id="userSearch" class="largeTextInput" onkeyup="searchUsers(this.value)" onchange="searchUsers(this.value)" />
                    <table id="userResult">
                        <tr id="userSearchHeader">
                            <th class="userSearchCell">Username</th>
                            <th class="userSearchCell">First Name</th>
                            <th class="userSearchCell">Last Name</th>
                            <th class="userSearchCell">Date Of Birth</th>
                            <th class="userSearchCell">Is Admin</th>
                            <th class="userSearchCell">Edit</th>
                        </tr>
                    </table>
                </div>
            </div>
            <div id="footer">
                MockStock, group 2, HEC April 2014
            </div>
        </div>
    </body>
</html>