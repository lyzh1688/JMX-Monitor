
var threadChart = echarts.init(document.getElementById('threadMemory'));
threadMemory = {};
$.ajaxSetup({
  async: false
  });
var threadMemoryInfo = {};
function getThreadMemory(){



    $.get("/monitorThreadMemory", {}, function(data, status) {

		threadMemoryInfo = data;
    });

	return threadMemoryInfo;
}

threadMemoryOption = {
    title: {
        text: '线程内存监控'
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
for(var k = 0 ; k < threadMemoryInfo.length;k++){
    threadOption.series.push(        {
                                         name:threadMemoryInfo[k].threadName,
                                         type:'line',
                                         stack: '总量',
                                         data:[0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
                                     });
}

threadMemory.timeTicket = setInterval(function (){
        var threadMemoryInfo = getThreadMemory();

        var count = 20;

        //x 轴变化
        var times = threadOption.xAxis.data;
        times.splice(0,1);
        times.push(threadInfo.time);

        //使用内存变化
        var runnable = threadOption.series;
        for(var i = 0 ; i < threadMemoryInfo.length;++i){
            runnable[i].splice(0,1);
            runnable[i].push(threadMemoryInfo.memory);
        }
        /*
        var runnable = threadOption.series[0].data;
        if(runnable.length < count){
            runnable.push(threadInfo.runnable);
        }
        else
        {
            runnable.splice(0,1);
            runnable.push(threadInfo.runnable);
        }
        */

        threadChart.setOption(threadMemoryOption);

}, 1000);