var N2MChart = {};
N2MChart.install = function(Vue, options){ 
  Vue.component('n2m-chart', {
    props: {
    	options: null
    },
    methods: {
    },
    mounted: function(){
    	console.log(this.options);
    	var $chart = this.$refs.chart;
    	var myChart = echarts.init($chart);
    	 // specify chart configuration item and data
        var option = {
            title: {
                text: 'ECharts entry example'
            },
            tooltip: {},
            legend: {
                data:['Sales']
            },
            xAxis: {
                data: ["shirt","cardign","chiffon shirt","pants","heels","socks"]
            },
            yAxis: {},
            series: [{
                name: 'Sales',
                type: 'bar',
                data: [5, 20, 36, 10, 10, 20]
            }]
        };

        // use configuration item and data specified to show chart
        myChart.setOption(option);
    },
    template: '\
    	<div>\
    		<div ref="chart" style="width: 600px;height:400px;"></div>\
    	</div>\
    '
  });
}
