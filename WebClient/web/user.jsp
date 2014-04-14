<%@page import="dataTransferObjects.UserDTO"%>
<%@page import="java.text.DateFormat"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@ page import="webclient.WebAppData" %>
<%
    if(WebAppData.getTrader() == null || WebAppData.getTrader().getUser() == null){
        response.sendRedirect("index.jsp");
    }
    UserDTO user;
    if(WebAppData.getEditOther()){
        user = WebAppData.getUserToModifyByAdmin();
    }else{
        user = WebAppData.getTrader().getUser();
    }
%>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Register</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link href="js/datePicker/css/blitzer/jquery-ui.css" rel="stylesheet" type="text/css" />
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="js/datePicker/js/jquery-ui.js"></script>
        <script src="js/userFunctions.js"></script>
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
                    <form name="userModify" action="manageforms" method="post">
                        <p>
                            <label for="userNameUpdate">Username: </label><br />
                            <input type="text" name="userNameUpdate" class="long" value="<%= user.getUserName() %>" disabled="diabled" />
                            <input type="hidden" name="userUpdate" value="self" />
                        </p>
                        <p>
                            <label for="firstName">First name: </label><br />
                            <input type="text" name="firstName" id="firstName" class="long" onkeyup="firstnameCheck();" onchange="firstnameCheck();" value="<%= user.getFirstName() %>" />
                            <input type="hidden"  id="firstname_ok" name="firstname_ok"  value="true" />
                            &nbsp;
                            <span class="verif" id="firstname_check">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </p>
                        <p>
                            <label for="lastName">Last name: </label><br />
                            <input type="text" name="lastName" id="lastName" class="long" onkeyup="lastnameCheck();" onchange="lastnameCheck();" value="<%= user.getLastName() %>" />
                            <input type="hidden"  id="lastname_ok" name="lastname_ok"  value="true" />
                            &nbsp;
                            <span class="verif" id="lastname_check">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </p>
                        <p>
                            <%
                                DateFormat df = new SimpleDateFormat("dd.MM.yyyy");
                            %>
                            <label for="dateOfBirth">Date of birth: </label><br />
                            <input type="text" name="dateOfBirth" id="datePicker" class="long" onkeyup="dateCheck();" onchange="dateCheck();" value="<%= df.format(user.getDateOfBirth()) %>" />
                            <input type="hidden"  id="date_ok" name="password_ok"  value="true" />
                            &nbsp;
                            <span class="verif" id="date_check">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </p>
                        <p>
                            <a href="password.jsp">
                                <input type="button" class="button xlong" value="Change Password" />
                            </a>
                        </p>
                        <p>
                            <input type="submit" value="Update" id="userUpdate" class="long" />
                            &nbsp;
                            <a href="trade.jsp">
                                <input type="button" class="button long" value="Cancel" />
                            </a>
                        </p>
                    </form>
                </div>
            </div>
            <div id="footer">
                MockStock, group 2, HEC April 2014
            </div>
        </div>
    </body>
</html>