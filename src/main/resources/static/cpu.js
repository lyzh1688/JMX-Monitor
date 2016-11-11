
var cpuChart = echarts.init(document.getElementById('cpu'));
cpu = {};
$.ajaxSetup({
  async: false
  });

function getCpu(){
    var cpuInfo = {};
    $.get("/monitorCpu", {}, function(data, status) {
        //console.log(data.runnable);
		cpuInfo = {ratio:data.ratio,
		                time: data.time.hour + ":" + data.time.min + ":" + data.time.sec};
    });
	return cpuInfo;
}

cpuOption = {
    title: {
        text: 'CPU监控'
    },
    tooltip: {
        trigger: 'axis'
    },
    legend: {
        data:['ratio',
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
            stack: 'CPU',
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

cpu.timeTicket = setInterval(function (){
        var cpuInfo = getCpu();

        var count = 20;

        //x 轴变化
        var times = cpuOption.xAxis.data;
        times.splice(0,1);
        times.push(cpuInfo.time);

        //使用内存变化
        var ratio = cpuOption.series[0].data;
        if(ratio.length < count){
            ratio.push(cpuInfo.ratio);
        }
        else
        {
            ratio.splice(0,1);
            ratio.push(cpuInfo.ratio);
        }


        cpuChart.setOption(cpuOption);

}, 1000);