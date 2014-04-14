<%@ page import="webclient.WebAppData" %>
<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Register</title>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
        <link href="js/datePicker/css/blitzer/jquery-ui.css" rel="stylesheet" type="text/css" />
        <script src="http://code.jquery.com/jquery-latest.min.js"></script>
        <script src="js/datePicker/js/jquery-ui.js"></script>
        <script src="js/registerFunctions.js"></script>
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
                    <form name="userCreate" action="manageforms" method="post">
                        <p>
                            <label for="userNameUpdate">Username: </label><br />
                            <input type="text" name="userNameUpdate" class="long" value="<%= WebAppData.getTrader().getUser().getUserName() %>" disabled="diabled" />
                        </p>
                        <p>
                            <label for="firstName">First name: </label><br />
                            <input type="text" name="firstName" class="long" value="<%= WebAppData.getTrader().getUser().getFirstName() %>" />
                        </p>
                        <p>
                            <label for="lastName">Last name: </label><br />
                            <input type="text" name="lastName" class="long" value="<%= WebAppData.getTrader().getUser().getLastName() %>" />
                        </p>
                        <p>
                            <label for="dateOfBirth">Date of birth: </label><br />
                            <input type="text" name="dateOfBirth" id="datePicker" class="long" onkeyup="dateCheck();" onchange="dateCheck();" value="<%= WebAppData.getTrader().getUser().getDateOfBirth() %>" />
                            <input type="hidden"  id="date_ok" name="password_ok"  value="false" />
                            &nbsp;
                            <span class="verif" id="date_check">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </p>
                        <p>
                            <label for="password">Password: </label><br />
                            <input type="password" name="password" id="password"  onkeyup="passwordLength();" onchange="passwordLength();" class="long" />
                            <input type="hidden"  id="password_ok" name="password_ok"  value="false" />
                            &nbsp;
                            <span class="verif" id="password_check">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </p>
                        <p>
                            <label for="passwordRepeat">Repeat password: </label><br />
                            <input type="password" name="passwordRepeat" id="passwordRepeat" onkeyup="passwordCheck();" onchange="passwordCheck();" class="long" />
                            <input type="hidden"  id="passwordRepeat_ok" name="passwordRepeat_ok"  value="false" />
                            &nbsp;
                            <span class="verif" id="passwordRepeat_check">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        </p>
                        <p>
                            <input type="submit" value="Update" id="registerSignup" class="long" disabled="disabled" />
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