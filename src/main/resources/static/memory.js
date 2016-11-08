
var myChart = echarts.init(document.getElementById('main'));
app = {};
$.ajaxSetup({
  async: false
  });
var data = [];
var now = +new Date(1997, 9, 3);
var oneDay = 24 * 3600 * 1000;
var value = Math.random() * 1000;

function getMemory(){
    var used = 0;
    $.get("/monitorMemory", {}, function(data, status) {
        used = data.used;
        console.log(used);
    });
//    $.post('/monitorMemory', {}, function(data) {
//				used = data.used;
//			});
	return used;
}

function randomData() {
    now = new Date(+now + oneDay);
    value = value + Math.random() * 21 - 10;
    return {
        name: now.toString(),
        value: [
            [now.getFullYear(), now.getMonth() + 1, now.getDate()].join('/'),
            getMemory()
        ]
    }
}


for (var i = 0; i < 10; i++) {
    data.push(randomData());
}


option = {
    title: {
        text: '动态数据 + 时间坐标轴'
    },
    tooltip: {
        trigger: 'axis',
        formatter: function (params) {
            params = params[0];
            var date = new Date(params.name);
            return date.getDate() + '/' + (date.getMonth() + 1) + '/' + date.getFullYear() + ' : ' + params.value[1];
        },
        axisPointer: {
            animation: false
        }
    },
    xAxis: {
        type: 'time',
        splitLine: {
            show: false
        }
    },
    yAxis: {
        type: 'value',
        boundaryGap: [0, '100%'],
        splitLine: {
            show: false
        }
    },
    series: [{
        name: '模拟数据',
        type: 'line',
        showSymbol: false,
        hoverAnimation: false,
        data: data
    }]
};
clearInterval(app.timeTicket);

app.timeTicket = setInterval(function (){

        var data0 = option.series[0].data;

        data0.push(randomData());

        myChart.setOption(option);

}, 1000);