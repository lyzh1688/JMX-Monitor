
var myChart = echarts.init(document.getElementById('main'));
app = {};
$.ajaxSetup({
  async: false
  });
var data = [];
var now =  1000;

function desendMinutes(date,seconds)
 {
          seconds=parseInt(seconds);
          var   interTimes=seconds*1000;
          interTimes=parseInt(interTimes);
          return new Date(Date.parse(date)-interTimes);
  }

function getMemory(){
    var appInfo = {};
    $.get("/monitorMemory", {}, function(data, status) {
        console.log(data.used);

		appInfo = {used:data.used,
		                committed:data.committed,
		                max:data.max,
		                time: data.time.hour + ":" + data.time.min + ":" + data.time.sec};
    });
//    $.post('/monitorapp', {}, function(data) {
//				used = data.used;
//			});
	return appInfo;
}

option = {
    title: {
        text: 'JVM内存监控'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['使用内存',
        '分配内存',
        //'最大内存'
        ]
    },
    grid: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        containLabel: true
    },
    toolbox: {
        feature: {
            saveAsImage: {}
        }
    },
    xAxis: {
        type: 'category',
        boundaryGap: false,
        data: ['1','2','3','4','5','6','7','8','9','10','11','12','13','14','15','16','17','18','19','20']
    },
    yAxis: {
        type: 'value'
    },
    visualMap: {
        left: '3%',
        right: '4%',
        bottom: '3%',
        pieces: [
        {
            gt: 0,
            lte: 35000,
            color: '#ffde33'
        },
        {
                    gt: 35000,
                    lte: 50000,
                    color: '#cc0033'
                }
                ],
        outOfRange: {
            color: '#999'
        }
    },
    series: [
        {
            name:'使用内存',
            type:'line',
            stack: '总量',
            data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],
            markLine: {
                            silent: true,
                            data: [{
                                yAxis: 35000
                            }, ]
                        }
        },
        {
            name:'分配内存',
            type:'line',
            stack: '总量',
            data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        },
//        {
//            name:'最大内存',
//            type:'line',
//            stack: '总量',
//            data:[150, 232, 201, 154, 190, 330, 410]
//        },

    ]
};
clearInterval(app.timeTicket);

app.timeTicket = setInterval(function (){
        var memInfo = getMemory();

        var count = 20;

        //x 轴变化
        var times = option.xAxis.data;
        times.splice(0,1);
        times.push(memInfo.time);

        //使用内存变化
        var usedapp = option.series[0].data;
        if(usedapp.length < count){
            usedapp.push(memInfo.used);
        }
        else
        {
            usedapp.splice(0,1);
            usedapp.push(memInfo.used);
        }

        //申请内存变化

        var commitedapp = option.series[1].data;
        if(commitedapp.length < count){
            //commitedapp.push(memInfo.committed);
            commitedapp.push(memInfo.committed - memInfo.used);
        }
        else
        {
            commitedapp.splice(0,1);
            commitedapp.push(memInfo.committed - memInfo.used);

            //commitedapp.push(memInfo.committed);
        }

        //最大内存变化
        /*
        var maxapp = option.series[2].data;
        if(maxapp.length < 10){
            maxapp.push(memInfo.max);
        }
        else
        {
            maxapp.splice(0,1);
            maxapp.push(memInfo.max);
        }
        */
        myChart.setOption(option);

}, 1000);