//初始化界面
function initPage() {
    //获取首页统计信息
    callSummaryInfo();
    //获取首页登录量图表
    callLoginChart();
}


//获取首页统计信息
function callSummaryInfo() {
    var jsInObj = null;
    restTemplate("GET", "/stats/summary", jsInObj, function (jsOutObj) {
        //console.debug(jsOutObj);
        $("#categCount").text(jsOutObj.categCount);
        $("#gdsCount").text(jsOutObj.gdsCount);
        $("#userCount").text(jsOutObj.userCount);
        $("#requestCount").text(jsOutObj.requestCount);
    });
}

//获取首页登录量图表
function callLoginChart() {
    var jsInObj = {};
    jsInObj.apiName = "/api/v1/actions/login";
    jsInObj.days = 7;
    restTemplate("GET", "/stats/apiChart", jsInObj, function (jsOutObj) {
        //console.debug(jsOutObj);
        var statDate = jsOutObj.statDate;
        var loginCount = jsOutObj.loginCount;
        var ipCount = jsOutObj.ipCount;
        initChart(statDate, loginCount, ipCount);
    });
}


//初始化图表
function initChart(statDate, loginCount, ipCount) {
    //在线人数曲线图
    var areaChartCanvas = $("#areaChart").get(0).getContext("2d");
    var areaChart = new Chart(areaChartCanvas);
    var areaChartData = {
        labels: statDate,
        datasets: [
            {
                label: "在线人数",
                fillColor: "rgba(0,166,94,0.9)",
                strokeColor: "rgba(0,166,94,0.8)",
                pointColor: "#3b8bba",
                pointStrokeColor: "rgba(0,166,94,1)",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(0,166,94,1)",
                data: ipCount
            }
        ]
    };
    var areaChartOptions = {
        showScale: true,
        scaleShowGridLines: false,
        scaleGridLineColor: "rgba(0,0,0,.05)",
        scaleGridLineWidth: 1,
        scaleShowHorizontalLines: true,
        scaleShowVerticalLines: true,
        bezierCurve: true,
        bezierCurveTension: 0.3,
        pointDot: false,
        pointDotRadius: 4,
        pointDotStrokeWidth: 1,
        pointHitDetectionRadius: 20,
        datasetStroke: true,
        datasetStrokeWidth: 2,
        datasetFill: true,
        legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].lineColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
        maintainAspectRatio: true,
        responsive: true
    };
    areaChart.Line(areaChartData, areaChartOptions);

    //使用次数柱状图
    var barChartCanvas = $("#barChart").get(0).getContext("2d");
    var barChart = new Chart(barChartCanvas);
    var barChartOptions = {
        scaleBeginAtZero: true,
        scaleShowGridLines: true,
        scaleGridLineColor: "rgba(0,0,0,.05)",
        scaleGridLineWidth: 1,
        scaleShowHorizontalLines: true,
        scaleShowVerticalLines: true,
        barShowStroke: true,
        barStrokeWidth: 2,
        barValueSpacing: 5,
        barDatasetSpacing: 1,
        legendTemplate: "<ul class=\"<%=name.toLowerCase()%>-legend\"><% for (var i=0; i<datasets.length; i++){%><li><span style=\"background-color:<%=datasets[i].fillColor%>\"></span><%if(datasets[i].label){%><%=datasets[i].label%><%}%></li><%}%></ul>",
        responsive: true,
        maintainAspectRatio: true
    };
    var barChartData = {
        labels: statDate,
        datasets: [
            {
                label: "使用次数",
                fillColor: "rgba(60,141,188,0.9)",
                strokeColor: "rgba(60,141,188,0.8)",
                pointColor: "#3b8bba",
                pointStrokeColor: "rgba(60,141,188,1)",
                pointHighlightFill: "#fff",
                pointHighlightStroke: "rgba(60,141,188,1)",
                data: loginCount
            }
        ]
    };
    barChart.Bar(barChartData, barChartOptions);
}