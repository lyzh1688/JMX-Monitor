
var threadChart = echarts.init(document.getElementById('thread'));
thread = {};
$.ajaxSetup({
  async: false
  });

function getThread(){
    var threadInfo = {};
    $.get("/monitorThread", {}, function(data, status) {
        console.log(data.runnable);
		threadInfo = {runnable:data.runnable,
		                notRunnable:data.notRunnable,
		                time: data.time.hour + ":" + data.time.min + ":" + data.time.sec};
    });
	return threadInfo;
}

threadOption = {
    title: {
        text: '线程监控'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['Runnable',
        'Not Runnable',
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
    series: [
        {
            name:'Runnable',
            type:'line',
            stack: '总量',
            data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
        },
        {
            name:'Not Runnable',
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

thread.timeTicket = setInterval(function (){
        var threadInfo = getThread();

        var count = 20;

        //x 轴变化
        var times = threadOption.xAxis.data;
        times.splice(0,1);
        times.push(threadInfo.time);

        //使用内存变化
        var runnable = threadOption.series[0].data;
        if(runnable.length < count){
            runnable.push(threadInfo.runnable);
        }
        else
        {
            runnable.splice(0,1);
            runnable.push(threadInfo.runnable);
        }

        //申请内存变化

        var notRunnable = threadOption.series[1].data;
        if(notRunnable.length < count){
            //commitedMemory.push(threadInfo.committed);
            notRunnable.push(threadInfo.notRunnable - threadInfo.runnable);
        }
        else
        {
            notRunnable.splice(0,1);
            notRunnable.push(threadInfo.notRunnable - threadInfo.runnable);

            //commitedMemory.push(threadInfo.committed);
        }

        //最大内存变化
        /*
        var maxMemory = threadOption.series[2].data;
        if(maxMemory.length < 10){
            maxMemory.push(threadInfo.max);
        }
        else
        {
            maxMemory.splice(0,1);
            maxMemory.push(threadInfo.max);
        }
        */
        threadChart.setOption(threadOption);

}, 1000);