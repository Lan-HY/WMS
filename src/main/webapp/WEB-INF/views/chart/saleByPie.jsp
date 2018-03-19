<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <script type="text/javascript" src="/js/jquery/jquery.js"></script>
    <script type="text/javascript" src="/js/echarts/echarts-all.js"></script>
</head>
<body>
    <div id="main" style="width: 1100px;height:500px"></div>
    <script>
        var myChart = echarts.init(document.getElementById('main'));
        option = {
            title : {
                text: '销售报表',//图表标题
                subtext: '${groupType}',//副标题
                x : "center"//对其方式
            },
            tooltip : {
                trigger: 'item',
                formatter: "{a} <br/>{b} : {c} ({d}%)"
            },
            legend: {
                type: 'scroll',
                orient: 'vertical',
                x: "left",
                data: ${left} //分组类型 ["xx","oo"]
            },
            toolbox: {
                show : true,
                feature : {
                    dataView : {show: true, readOnly: true},
                    restore : {show: true},
                    saveAsImage : {show: true}
                }
            },
            series : [
                {
                    name: '销售总额',
                    type: 'pie',
                    radius : '60%',
                    center: ['50%', '50%'],
                    data: ${datas},//饼状图的数据[{name:"xx",value:100},{name:"00",value:50}]
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        myChart.setOption(option);
    </script>
</body>
</html>
