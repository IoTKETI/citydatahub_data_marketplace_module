<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- sub-cover -->
<section class="sub-cover">
  <h3 class="sub-cover__title sub-cover__title--search material-icons">카테고리별 데이터셋 현황 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<hr>
<!-- sub-nav -->
<%@ include file="../tiles/tiles_sub_nav.jspf" %>
<!-- sub-nav -->
<hr>
<div class="sub__container">
	<%@ include file="../tiles/tiles_breadcrumb.jspf" %>
    <h3 class="sub__heading1">카테고리별 데이터셋 현황</h3>
	<div class="sub__content">
		<div class="treemap__container">
			<div class="menu">
				<ul id="categorytree"></ul>
			</div>
			<div class="content">
				<div ref="main" style="width: 1000px; height:600px;"></div>
			</div>
		</div>
	</div>
</div>

<script>
$(function(){
	vm = new Vue({
		el: '#content',
		data: function(){
			return {
				chartInstance: {},
				pageInfo: {
					srchCategoryLevel : 1,
					srchCategoryOid   : 0,
				}	
			}
		},
		mounted: function(){
			this.makeMenu();
			this.makeChart(true);
		},
		methods: {
			makeMenu: function(){
				var request = $.ajax({
					method: "GET",
					url: "/dataset/getCategoryList.do",
					dataType: "json"
				});
				request.done(function(data){
					 var rows = data.list.map(function(row){ 
						return { 
							id       : row.categoryOid, 
		                    parentId : row.categoryParentOid, 
		                    name     : row.categoryNm, 
						}
					});
					vm.initMenu(rows);
				});
			},
			makeChart: function(first){
				var request = $.ajax({
					method: "GET",
					url: SMC_CTX + "/statistics/chart/getList.do",
					data: {
						srchCategoryLevel: this.pageInfo.srchCategoryLevel,
						srchCategoryOid  : this.pageInfo.srchCategoryOid,
					},
					dataType: "json"
				});
				request.done(function(data){
					var total = 0; 
					var rows = data.map(function(row){
						total += row.datasetCount;
						return { 
							id       : row.categoryOid, 
		                    parentId : row.categoryParentOid, 
		                    name     : row.categoryNm,
		                    count    : row.datasetCount,
		                    level    : row.level,
						}
					});
					if(!first && rows.length === 0){
						if(!confirm("선택한 카테고리의 데이터셋 목록으로 이동하시겠습니까?")) return;
						location.href = SMC_CTX + "/dataset/pageList.do?categoryId=" + vm.pageInfo.srchCategoryOid; 
					}else{
						if(total > 0){
							if($.isFunction(vm.chartInstance.dispose)){
								vm.chartInstance.dispose();
							}
							vm.initChart(rows, total);
						}else{
							if(!confirm("하위 카테고리의 데이터셋이 없습니다.\n선택한 카테고리의 데이터셋 목록으로 이동하시겠습니까?")) return;
							location.href = SMC_CTX + "/dataset/pageList.do?categoryId=" + vm.pageInfo.srchCategoryOid; 
						}
					}
				});
			},
			initMenu: function(rows){
				$("#categorytree").fancytree({
					init: function(){
						$("ul.fancytree-container").css({ height: "535px" });
					},
					activate: function(event, data){
		 				vm.pageInfo.srchCategoryLevel = data.node.data.level + 1;
						vm.pageInfo.srchCategoryOid   = data.node.data.id;
						vm.makeChart();
					},
					source: (function(rows){
						function exists(rows, parentId){
							for(var i=0; i<rows.length; i++){
								if (rows[i].id == parentId) return true;
							}
							return false;
						}
						
						var nodes = [];
						// get the top level nodes
						for(var i=0; i<rows.length; i++){
							var row = rows[i];
							if (!exists(rows, row.parentId)){
								nodes.push({
									key     : row.id,
									title   : row.name,
									id      : row.id,
					                text    : row.name,
					                level   : 1
								});
							}
						}
						
						var toDo = [];
						for(var i=0; i<nodes.length; i++){
							toDo.push(nodes[i]);
						}
						while(toDo.length){
							var node = toDo.shift();		// the parent node
							// get the children nodes
							for(var i=0; i<rows.length; i++){
								var row = rows[i];
								if (row.parentId == node.key){
									var child = {
										key     : row.id,
					                    title   : row.name,
					                    id      : row.id,
					                    text    : node.text + ' > ' + row.name,
					                    level   : node.level + 1,
					                    parent  : node.id,
									};
									if (node.children){
											node.children.push(child);
									} else {
											node.children = [child];
									}

									toDo.push(child);
									node.folder = true;
									node.expanded = true;
								}
							}
						}
						return nodes;
					})(rows)
				});
			},
			initChart: function(rows, total){
				this.chartInstance = echarts.init(this.$refs.main, "light");
				this.chartInstance.setOption({
					tooltip: {
						formatter: function(params){
							return [
	                            params.name,
		                        params.data.value
	                        ].join(': ')
						}
					},
					series: [{
						name: "최상위",
						type: 'treemap',
						roam: false,
						nodeClick: false,
						visibleMin: 0,
						label: {
			                normal: {
			                    position: 'insideTopLeft',
			                    formatter: function (params) {
			                        return [
			                            '{name|' + params.name + '}',
			                            '{hr|}',
	 		                            '{label|' + ((params.data.value / total) * 100).toFixed(2) + '%}'
			                        ].join('\n');
			                    },
			                    rich: {
			                        label: {
			                            fontSize: 23,
			                            color: '#fff',
			                            padding: [2, 4],
			                            lineHeight: 25,
			                            align: 'right'
			                        },
			                        name: {
			                            fontSize: 14,
			                            color: '#fff'
			                        },
			                        hr: {
			                            width: '100%',
			                            borderColor: 'rgba(255,255,255,0.2)',
			                            borderWidth: 0.5,
			                            height: 0,
			                            lineHeight: 10
			                        }
			                    }
			                }
						},
						levels: [
				            {
				                upperLabel: {
				                    show: false
				                }
				            },
				            {
				                itemStyle: {
				                    borderWidth: 1,
				                    gapWidth: 1,
				                    borderColorSaturation: 0.6
				                }
				            },
				            {
				                colorSaturation: [0.35, 0.5],
				                itemStyle: {
				                    borderWidth: 1,
				                    gapWidth: 1,
				                    borderColorSaturation: 0.5
				                }
				            }
				        ],
		                upperLabel: {
		                    show: true,
		                    height: 30
		                },
						data: (function(rows){
							function exists(rows, parentId){
								for(var i=0; i<rows.length; i++){
									if (rows[i].id == parentId) return true;
								}
								return false;
							}
							
							var nodes = [];
							// get the top level nodes
							for(var i=0; i<rows.length; i++){
								var row = rows[i];
								if (!exists(rows, row.parentId)){
									nodes.push({
										key     : row.id,
										value   : row.count,
										name    : row.name,
										level   : row.level,
									});
								}
							}
							return nodes;
						})(rows)
					}]
				});
				
				this.chartInstance.off("click");
				this.chartInstance.on("click", function(params){
					var data = params.data;
					if(!data){
						vm.pageInfo.srchCategoryLevel = 1;
						vm.pageInfo.srchCategoryOid   = 0;
					}else{
						vm.pageInfo.srchCategoryLevel = data.level + 1;
						vm.pageInfo.srchCategoryOid   = data.key;
					}
					vm.makeChart();
				});
			}
		}
	});
});

</script>