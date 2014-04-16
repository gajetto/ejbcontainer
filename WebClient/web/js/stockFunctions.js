$(document).ready(function() {
    $(".numbersOnly").keydown(function (e) {
        // Allow: backspace, delete, tab, enter
        if ($.inArray(e.keyCode, [46, 8, 9, 27, 13]) !== -1 ||
             // Allow: Ctrl+A
            (e.keyCode == 65 && e.ctrlKey === true) || 
             // Allow: home, end, left, right
            (e.keyCode >= 35 && e.keyCode <= 39)) {
                 // let it happen, don't do anything
                 return;
        }
        // Ensure that it is a number and stop the keypress
        if ((e.shiftKey || (e.keyCode < 48 || e.keyCode > 57)) && (e.keyCode < 96 || e.keyCode > 105)) {
            e.preventDefault();
        }
    });
});

function enableButton(stockName){
    if($("#stockNumber"+stockName).val() != ''){
        $("#buy"+stockName).prop("disabled", false);
        if(($("#stockNumber"+stockName).val())<=(parseInt($("#bought"+stockName).text()))){
            $("#sell"+stockName).prop("disabled", false);
        }else{
            $("#sell"+stockName).prop("disabled", true);
        }
    }else{
        $("#buy"+stockName).prop("disabled", true);
        $("#sell"+stockName).prop("disabled", true);
    }
}

function printStock(data){

        var finalDiv = '';
        $.each(data, function(i, stock){
            var ibm = stock.IBM;
            var ibmRate = stock.IBMRate;
            var sun = stock.Sun;
            var sunRate = stock.SunRate;
            var apple = stock.Apple;
            var appleRate = stock.AppleRate;

            var div = '<div class="newStock">\n\
                        <div class="stockNewPrice">New prices</div>\n\
                        <div class="stockInfo">\n\
                            <div class="stockName">Sun:</div>\n\
                            <div class="stockPrice">'+sun+'</div>\n\
                            <div class="stockRate">'+sunRate+'</div>\n\
                        </div><br />\n\
                        <div class="stockInfo">\n\
                            <div class="stockName">Apple:</div>\n\
                            <div class="stockPrice">'+apple+'</div>\n\
                            <div class="stockRate">'+appleRate+'</div>\n\
                        </div><br />\n\
                        <div class="stockInfo">\n\
                            <div class="stockName">IBM:</div>\n\
                            <div class="stockPrice">'+ibm+'</div>\n\
                            <div class="stockRate">'+ibmRate+'</div>\n\
                        </div><br />\n\
                    </div><br /><br />';
        finalDiv = finalDiv+div;
        })
        $(".newStock").html(finalDiv);
               
}

function getChartData(data){
    
    var size = countProperties(data)
    var max = 10
    var tab = [
          ['Number', 'IBM', 'Sun', 'Apple']
        ];
        for(var i=max; i>=0; i--){
            var tab2 = [max-i, 0, 0, 0]
            if(i<size){
                tab2 = [max-i, data[i].IBM, data[i].Sun, data[i].Apple]
            }
            tab.push(tab2);
        }
        return tab;
}

function drawChart(stocks) {
        var data = google.visualization.arrayToDataTable(stocks);

            var options = {
              title: 'Stock Performance'
            };

            var chart = new google.visualization.LineChart(document.getElementById('chart_div'));
            chart.draw(data, options);
    }

function countProperties(obj) {
  var prop;
  var propCount = 0;

  for (prop in obj) {
    propCount++;
  }
  return propCount;
}