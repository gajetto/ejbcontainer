<html lang="en">
    <head>
        <meta charset="utf-8">
        <title>Trade</title>
    </head>
        <link href="css/style.css" rel="stylesheet" type="text/css" />
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
                    <form name="userForm" action="manageforms" method="post">
                        <p>
                            <label for="userName">Username: </label><br />
                            <input type="text" name="userName" class="long" />
                        </p>
                        <p>
                            <label for="userName">Password: </label><br />
                            <input type="password" name="password" class="long" />
                        </p>
                        <p>
                            <input type="submit" value="Login" class="long" />
                            &nbsp;
                            <a href="register.jsp">
                                <input type="button" class="button long" value="signup" />
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