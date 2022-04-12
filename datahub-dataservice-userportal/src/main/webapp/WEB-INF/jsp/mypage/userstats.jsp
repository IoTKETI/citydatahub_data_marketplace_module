<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- sub-cover -->
<section class="sub-cover">
  <h3 class="sub-cover__title sub-cover__title--data material-icons">마이페이지 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<!-- sub-nav -->
 ${lnbHtml}
<!-- sub-nav -->
<hr>
<div class="sub__container">
 <h3 class="sub__heading1">데이터 사용자 현황</h3>
           <div class="sub__content">

 </div>
        <div class="sub__content">
	        <form class="search-form" action="">
	          <fieldset>
	            <legend>데이터 검색 폼</legend>
	            <table class="form-table">
	              <caption>데이터 검색 테이블</caption>
	              <colgroup>
	                <col width="6%">
	                <col width="*">
	                <col width="6%">
	                <col width="*">
	                <col width="14%">
	              </colgroup>
	              <tbody>
	                <tr>
	                  <th scope="row">검색유형</th>
	                  <td><select class="select" v-model="pageInfo.schCondition">
	                      <option disabled value="">--선택--</option>
	                    <option value=""></option>
	                    <option value=""></option>
	                    </select>
	                    <input class="input input__search" type="search" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue">
	                  </td>
	                  <th scope="row">기간</th>
	                  <td>
	                    <div class="datepicker">
	                      <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" v-model="pageInfo.fromDate" type="text"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" v-model="pageInfo.toDate" type="text"></label>
	                    </div>
	                  </td>
	                  <td rowspan="2"><button class="button__submit" type="button" @click="">검색</button></td>
	                </tr>
	              </tbody>
	            </table>
	          </fieldset>
	        </form>
        </div>
        <div class="sub__content">
         <div id="container" style="left:10px;width: 1000px;height:400px;"></div> 
         </div>
        <div class="sub__content">
          <h4 class="sub__heading2">Table</h4>
          <table class="board-table">
            <caption>해당 테이블에 대한 캡션</caption>
            <colgroup>
              <col width="10%">
              <col width="40%">
              <col width="20%">
              <col width="30%">
            </colgroup>
            <thead>
              <tr>
                <th scope="col">title1</th>
                <th scope="col">title2</th>
                <th scope="col">title3</th>
                <th scope="col">title4</th>
              </tr>
            </thead>
            <tbody>
            <tr v-if="list.length === 0">
            <td colspan="5">데이터가 없습니다.</td>
        	
            </tbody>
          </table>
        </div>
        <div class="sub__bottom">
          <div>
	          <n2m-pagination :pager="pageInfo" @pagemove=""/>
          </div>
          
        </div>
 </div>
    <script >
        
        $(function(){
        	Vue.use(N2MPagination);
        	vm = new Vue({
        		el: '#content',
        		data: function(){
        			return {
        			  list: [],
      				  loading: true
        			}
        		},
        		methods: {
        			getData: function(curPage){
    				
    				},
        		},
        		mounted: function() {
        			var dom = document.getElementById("container");
        	        var myPageChart = echarts.init(dom);
        	        var app = {};
        	        option = null;
        	        option = {
        	            title: {
        	                
        	            },
        	            tooltip: {
        	                trigger: 'axis'
        	            },
        	            legend: {
        	                data: ['data1', 'data2','data3']
        	            },
        	            toolbox: {
        	            	language : "en",
        	                show: true,
        	                feature: {
        	                    magicType: {
        	                        type: ['bar','line'],
        	                        show: true,
        	                        title: ''
        	                    },
        	                    restore: {
        	                    	show: true,
        	                        title: '초기화'
        	                    },
        	                    saveAsImage: {
        	                    	show: true,
        	                        title: '저장'
        	                    }
        	                }
        	            },
        	            xAxis: {
        	                type: 'category',
        	                boundaryGap: true,
        	                data: [1, 2, 3, 4, 5,6,7,8,9,10,11,12]
        	            },
        	            yAxis: {
        	                type: 'value',
        	                axisLabel: {
        	                    formatter: '{value}', 
        	                }
        	            }, 
        	            series: [{
        	                    name: 'data1',
        	                    type: 'bar',
        	                    data: [55,66,77,88,44,45,56,76,88,99,58,60],
        	                    markPoint: {
        	                        symbol:'arrow',
        	                        data: [{
        	                            type: 'max',
        	                            name: 'max'
        	                        }]
        	                    },
        	                    markLine: {
        	                        data: [{
        	                            type: 'average',
        	                            name: 'average'
        	                        }]
        	                    }
        	                },
        	                {
        	                    name: 'data2',
        	                    type: 'bar',
        	                    data: [10,11,22,11,4,2,1,6,22,13,23,17],
        	                    markPoint: {
        	                        symbol:'arrow',
        	                        data: [{
        	                            type: 'max',
        	                            name: 'max'
        	                        } 
        	                    ]
        	                    },
        	                    markLine: {
        	                        data: [{
        	                                type: 'average',
        	                                name: 'average'
        	                            },
        	                            [{
        	                                symbol: 'none',
        	                                x: '70%',
        	                                yAxis: 'max'
        	                            }, {
        	                                symbol: 'circle',
        	                                label: {
        	                                    normal: {
        	                                        position: 'start',
        	                                        formatter: '최대값'
        	                                    }
        	                                },
        	                                type: 'max',
        	                                name: '최댓값'
        	                            }]
        	                        ]
        	                    }
        	                },
        	                {
        	                    name: 'data3',
        	                    type: 'line',
        	                    data: [55,66,77,88,44,45,56,76,88,99,58,60],
        	                    markPoint: {
        	                        symbol:'arrow',
        	                        data: [{
        	                            type: 'max',
        	                            name: 'max'
        	                        }]
        	                    },
        	                    markLine: {
        	                        data: [{
        	                            type: 'average',
        	                            name: 'average'
        	                        }]
        	                    }
        	                },
        	            ]
        	        };;
        	        if (option && typeof option === "object") {
        	            myPageChart.setOption(option, true);
        	        }
        		},
        	});
        });
    </script>
