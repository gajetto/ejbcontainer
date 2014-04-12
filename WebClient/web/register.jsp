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
                <div class="center">
                    <form name="userCreate" action="manageforms" method="post">
                        <label for="userName">Username: </label>
                        <input type="text" name="userName" class="long" />
                        <br />
                        <label for="firstName">First name: </label>
                        <input type="text" name="firstName" class="long" />
                        <br />
                        <label for="lastName">Last name: </label>
                        <input type="text" name="lastName" class="long" />
                        <br />
                        <label for="dateOfBirth">Date of birth: </label>
                        <input type="number" name="dateOfBirth" id="datePicker" class="long" />
                        <br />
                        <label for="password">Password: </label>
                        <input type="password" name="password" id="password"  onkeyup="passwordLength();" onchange="passwordLength();" class="long" />
                        <input type="hidden"  id="password_ok" name="password_ok"  value="false" />
                        &nbsp;
                        <span class="verif" id="password_check">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <br />
                        <label for="passwordRepeat">Repeat password: </label>
                        <input type="password" name="passwordRepeat" id="passwordRepeat" onkeyup="passwordCheck();" onchange="passwordCheck();" class="long" />
                        <input type="hidden"  id="passwordRepeat_ok" name="passwordRepeat_ok"  value="false" />
                        &nbsp;
                        <span class="verif" id="passwordRepeat_check">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;</span>
                        <br />
                        <input type="submit" value="Signup" class="long" />
                    </form>
                </div>
            </div>
            <div id="footer">
                MockStock, group 2, HEC April 2014
            </div>
        </div>
    </body>
</html>