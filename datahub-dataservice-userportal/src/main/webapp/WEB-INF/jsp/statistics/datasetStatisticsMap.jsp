<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script type="text/javascript" src="https://openapi.map.naver.com/openapi/v3/maps.js?ncpClientId=kfmwda4fkc&submodules=geocoder"></script>
<script type="text/javascript" src="<c:url value="/smartcity/js/common/navermaps/MarkerClustering.js"/>"></script>
<!-- sub-cover -->
<section class="sub-cover">
  <h3 class="sub-cover__title sub-cover__title--search material-icons">지도 기반 데이터셋 데이터 현황 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<!-- sub-nav -->
<%@ include file="../tiles/tiles_sub_nav.jspf" %>
<!-- sub-nav -->
<hr>
<div class="sub__container">
	<%@ include file="../tiles/tiles_breadcrumb.jspf" %>
    <h3 class="sub__heading1">지도 기반 데이터셋 데이터 현황</h3>
</div>
<div class="sub__container mapping">
    <div class="drawer">
        <div class="sub__content sub__menu-tab">
            <nav class="category-nav">
              <h3 class="category-nav__title"><span class="hidden">카테고리</span></h3>
              <ul class="category-nav__list">
                <li class="category-nav__item drawer-nav__list" v-if="subCategoryList.length === 0">
                    <a href="#none" class="category-nav__link material-icons" @click="onClick('CLICK_CATEGORY_FILTER_ALL', mainCategoryList)">전체카테고리</a>
                </li>
                <li class="category-nav__item drawer-nav__list" v-for="(category, index) in mainCategoryList" :class="containsCategory(category.categoryOid)" v-if="subCategoryList.length === 0">
                    <a href="#none" class="category-nav__link material-icons" @click="onClick('CLICK_CATEGORY_FILTER', category)">{{category.categoryNm}}</a>
                </li>
                <li class="category-nav__item drawer-nav__list" v-if="subCategoryList.length !== 0" @click="onClick('CLICK_CATEGORY_RESET')">
                    <a href="#none" class="category-nav__link material-icons">상위 메뉴</a>
                </li>
                <li class="category-nav__item drawer-nav__list" v-for="(category, index) in subCategoryList" :class="containsCategory(category.categoryOid)">
                    <a href="#none" class="category-nav__link material-icons" @click="onClick('CLICK_SUB_CATEGORY_FILTER', category)">{{category.categoryNm}}</a>
                </li>
              </ul>
            </nav>
        </div>
        <div class="sub__content sub__menu-tab">
            <form class="search-form" action="">
                <fieldset>
                    <legend>데이터 검색 폼</legend>
                    <table class="form-table drawer-form-table">
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
	                            <th scope="row">제목</th>
	                            <td>
	                                <input class="input input__search" type="search" v-model="pageInfo.searchTitle" @keypress.enter="getDatasetList()">
	                            </td>
	                            <th scope="row">등록일</th>
	                            <td>
	                              <div class="datepicker">
	                                  <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" type="text" v-model="pageInfo.fromDate" autocomplete="off"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" type="text" v-model="pageInfo.toDate" autocomplete="off"></label>
	                              </div>
	                            </td>
                            </tr>
	                        <tr>
	                            <th scope="row">제공유형</th>
	                            <td>
	                              <select class="select choice" v-model="pageInfo.dataOfferType">
	                                <option value="">선택</option>
                                    <c:forEach var="code" items="${n2m:getCodeList('CG_ID_OFFER_TYPE')}">
                                        <option value="${code.codeId}">${code.codeName}</option>
                                    </c:forEach>
	                              </select>
	                            </td>
	                            <th scope="row">진행상태</th>
	                            <td>
	                              <select class="select choice" v-model="pageInfo.searchStatus">
                                      <option value="release_all">전체</option>
                                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_STATUS')}" varStatus="status">
                                          <c:if test="${status.index == 6 or status.index == 3}">
                                              <option value="${code.codeId}">${code.codeName}</option>
                                          </c:if>
                                      </c:forEach>
                                  </select>
	                            </td>
	                            <td rowspan="2"><button class="button__submit button__find" type="button" @click="getDatasetList(1, true)">검색</button></td>
	                        </tr>
                        </tbody>
                    </table>
                </fieldset>
            </form>
        </div>
		<div class="sub__content sub__menu-list">
			<ul class="data-list" style="height: 300px; overflow-x: hidden;">
				<li class="data-list__item data-list__menu" v-if="list.length === 0">
					<div class="data-unit data-unit--none">
						데이터가 없습니다.
					</div>
				</li>
				<li class="data-list__item data-list__menu" v-for="dataset in list">
					<div class="data-unit">
						<a class="data-unit__link" @click="selectDataset(dataset)">
							<div class="data-list__thumb">
								<img class="data-list__image material-icons" :src="fileDownload(dataset.id, dataset.datasetImageFileId)" onerror="this.src='<c:url value="/smartcity/img/dummy/thumb_empty_100x100.jpg"/>'"/>
							</div>
							<dl class="data-unit__caption">
								<dt class="data-unit__title menu-title">{{dataset.title}}</dt>
								<dd class="data-unit__category menu-sub">
									<span class="data-unit__status">[{{dataset.statusName}}]</span>{{dataset.categoryName}}
								</dd>
								<dd class="data-unit__text menu-sub">{{dataset.description}}</dd>
								<dd class="data-unit__badge">
									<span class="badge badge--blue" v-if="dataset.extensionName">{{dataset.extensionName}}</span>
									<span class="badge badge--green" v-if="dataset.dataOfferTypeName">{{dataset.dataOfferTypeName}}</span>
									<span class="badge badge--orange" v-if="dataset.dataTypeName">{{dataset.dataTypeName}}</span>
									<span class="badge badge--pink">{{dataset.status != 'paidMode' ? '무료': '유료'}} </span>
								</dd>
							</dl>
						</a>
					</div>
				</li>
			</ul>
		</div>
		<div class="sub__bottom sub__menu-button">
			<n2m-pagination :pager="pageInfo" @pagemove="getDatasetList"/>
		</div>
	</div>
	
	
    <!-- 데이터셋 팝업창 -->
    <div id="" class="modal js-modal-show">
        <!-- 임시로 style 넣었습니다. 삭제해주세요. -->
        <div class="popup__outer popup__outer-none" style="top: 0;" :style="{ left: togglePopup ? '300px': '0px' }">
            <div class="popup__inner">
                <div class="modal__header">
                    <h3 class="hidden">지도기반 데이터셋 데이터 팝업</h3>
                    <button class="modal__button--close-pop js-modal-close material-icons" type="button" @click="togglePopup = false"><span class="hidden">모달 닫기</span></button>
                </div>
                <div class="modal__body">
                    <section class="modal__service-request">
                        <h4 class="modal__heading1" v-hide="!statisticsPopupTitle">{{statisticsPopupTitle}}</h4>
                        <h5 class="modal__heading4">출력정보</h5>
                        <div class="service-request__address">
                            <div class="select-result__middle">
                                <table>
                                    <tbody>
                                        <tr>
                                            <td>
                                                <div class="fixed-table__wrap">
                                                    <div class="fixed-table__thead">
                                                        <table class="fixed-table fixed-popup">
                                                            <caption>해당 테이블에 대한 캡션</caption>
                                                            <colgroup>
                                                                <col width="5%">
                                                                <col width="50%">
                                                                <col width="45%">
                                                            </colgroup>
                                                            <thead>
                                                                <tr>
                                                                    <th scope="col">NO</th>
                                                                    <th scope="col">출력명</th>
                                                                    <th scope="col">설명</th>
                                                                </tr>
                                                            </thead>
                                                        </table>
                                                    </div>
                                                    <div class="fixed-table__popup">
                                                        <table class="fixed-table fixed-popup">
                                                            <caption>해당 테이블에 대한 캡션</caption>
                                                            <colgroup>
                                                                <col width="5%">
                                                                <col width="50%">
                                                                <col width="45%">
                                                            </colgroup>
                                                            <tbody>
                                                                <tr v-for="(datasetOutput, index) in statisticsDatasetOutputList">
                                                                    <th scope="row">{{index+1}}</th>
                                                                    <td>{{datasetOutput.name}}</td>
                                                                    <td>{{datasetOutput.description}}</td>
                                                                </tr>
                                                            </tbody>
                                                        </table>
                                                    </div>
                                                </div>
                                            </td>
                                        </tr>
                                    </tbody>
                                </table>
                            </div>
                        </div>
                        <div>
                            <ul class="data-write-map">
                            </ul>
                        </div>
                    </section>
                </div>
            </div>
            <!-- 슬라이드 버튼 -->
            <button class="button-menu-right--toggle" type="button" @click="togglePopup = !togglePopup">
                <span class="hidden">펼치기</span>
                <i class="drawer__icon-arrow"></i></button>
            <!-- 슬라이드 버튼 -->
        </div>
    </div>
    <!-- 데이터셋 팝업창 -->
	
    <div class="drawer-map">
		<div id="map" style="width:100%;height:819px;"></div>
    </div>
</div>

<div id="markerTemplate" style="display:none;">
	<div>
	    <div class="pannel">
	        <h3 class="hidden">주차구역 정보</h3>
	        <div class="pannel__header">
	            <dl class="current-patient">
	                <dt class="current-patient__name">#title#</dt>
	                <dd class="current-patient__info">#date#</dd>
	            </dl>
	        </div>
	    </div>
	</div>
</div>
<div id="clusterTemplate" style="display:none;">
	<div>
		<div class="pannel cluster_marker not">
		    <h3 class="hidden">주차구역 정보</h3>
		    <div class="pannel__header">
		        <dl class="current-patient">
		            <dt class="current-patient__name">#title#</dt>
		            <dd class="current-patient__info">#date#</dd>
		        </dl>
		        <dl class="current-patient">
		            <dd class="current-patient__number">
		                <p></p>
		            </dd>
		        </dl>
		    </div>
		    <div class="pannel-tap cluster_popup hidden">
	            <h3 class="hidden">주차구역 정보</h3>
		        <div class="pannel__header">
		        <!-- TODO: 클러스터 행 추가 템플릿-->
		        </div>
		    </div>
		</div>
	</div>
</div>
<script>
$(function(){
	Vue.use(N2MPagination);
	vm = new Vue({
		el: '#content',
		data: function(){
			return {
				mapInstance             : {},
				markerClustering        : {},
				markers                 : [],
				list                    : [],
			    mainCategoryList        : [],
			    subCategoryList         : [],
			    selectedMainCategoryOid : "",
			    selectedSubCategoryOid  : "",
			    categoryOidList         : [${param.categoryId}],
			    togglePopup             : false,
			    statisticsPopupTitle    : "",
			    statisticsDatasetOutputList: []
			}
		},
		created: function() {
            this.pageInfo.searchStatus  = "release_all";
            this.pageInfo.searchTitle   = "${param.searchTitle}";
            this.pageInfo.dataOfferType = "${param.dataOfferType}";
            this.pageInfo.searchType    = "${searchType}";
            
            if(this.selectedMainCategoryOid){
            	this.getSubCategoryList();
            }
        },
        watch: {
        	"list": function(){
        		vm.$nextTick(function(){
        			vm.loadingFlag = false;
        		});
        	},
        	"markers": function(){
        		vm.markerCluster.markers = this.markers;
        		vm.markerCluster._redraw();
        	}
        },
		mounted: function(){
            this.mapInstance = new naver.maps.Map('map', {
                center: new naver.maps.LatLng(37.3595704, 127.105399),
                zoomControl: false,
                zoom: 10
            });
            
            this.createMarkerCluster();
		},
		methods: {
			changeMainCategory: function(){
        		if(!vm.selectedMainCategoryOid){
        			vm.categoryOidList = [];
            		vm.subCategoryList = [];
            		vm.selectedSubCategoryOid = "";
                	vm.getDatasetList(1);
        			return;
        		}
        		vm.categoryOidList = [vm.selectedMainCategoryOid];
        		vm.selectedSubCategoryOid = "";
        		vm.getSubCategoryList();
			},
			changeSubCategory: function(){
        		if(!vm.selectedSubCategoryOid){
            		vm.categoryOidList = [vm.selectedMainCategoryOid];
                	vm.getDatasetList(1);
        			return;
        		}
        		vm.categoryOidList = [vm.selectedSubCategoryOid];
            	vm.getDatasetList(1);
			},
			containsCategory: function(categoryOid){
				return {
					'is-select': vm.categoryOidList.indexOf(categoryOid) != -1
				};
			},
			getMainCategoryList: function(){
				var request = $.ajax({
                    url : SMC_CTX+"/main/category.do",
                    method : "GET",
                    data: {
                    	srchCategoryLevel : 1
                    },
                    dataType: "json",
                });
                request.done(function(data) {
                	vm.mainCategoryList = data.list;
                	
                	vm.keepPageInfo(function(curPage) {
                        vm.getDatasetList(curPage, true);
                    });
                });
			},
			getSubCategoryList: function(){
				if(!vm) vm = this;
				
				var request = $.ajax({
                    url : SMC_CTX+"/main/category.do",
                    method : "GET",
                    data: {
                    	srchCategoryOid   : vm.selectedMainCategoryOid,
                    	srchCategoryLevel : 2
                    },
                    dataType: "json",
                });
                request.done(function(data) {
                	vm.subCategoryList = data.list;
                	vm.getDatasetList(1);
                });
			},
			getDatasetList : function(curPage, loading) {
				if(loading){
					vm.loadingFlag = true;
				}
				var request = $.ajax({
					url : SMC_CTX + "/dataset/getList.do",
					method : "GET",
					dataType: "json",
					data: {
						curPage         : curPage,
						searchType      : vm.pageInfo.searchType,
						dataOfferType   : vm.pageInfo.dataOfferType,
						searchTitle     : vm.pageInfo.searchTitle,
						searchStatus    : vm.pageInfo.searchStatus,
						fromDate        : vm.pageInfo.fromDate,
						toDate          : vm.pageInfo.toDate,
						categoryOidList : vm.categoryOidList.join(","),
						pageListSize    : 5,
					}
				});
				
				request.done(function(data) {
					vm.list = data.list;
					vm.pageInfo = data.page;
				});
				
				request.fail(function(data) {
					console.log("FAIL");
					vm.loadingFlag = false;
				});
			},
            onClick: function(code, val){
                switch(code){
                case "CLICK_CATEGORY_FILTER_ALL":
                	vm.selectedMainCategoryOid = "";
                	var findSubCategoryIndex = vm.categoryOidList.indexOf(vm.selectedSubCategoryOid);
                	if(findSubCategoryIndex != -1){
                		vm.categoryOidList.splice(findSubCategoryIndex, 1);
                    	vm.selectedSubCategoryOid  = "";
                	}
                	
                	var mainCategoryList = val;
                	if(vm.categoryOidList.length !== mainCategoryList.length){
                		vm.categoryOidList = [];
                    	for(var i=0; i<mainCategoryList.length; i++){
                    		vm.categoryOidList.push(mainCategoryList[i].categoryOid);
                    	}
                	}else{
                	    vm.categoryOidList = [];
                	}
                	vm.getDatasetList(1);
                	break;
                case "CLICK_CATEGORY_FILTER":
                	// 기존에 입력되어있던 하위 카테고리 제거
                	var findSubCategoryIndex = vm.categoryOidList.indexOf(vm.selectedSubCategoryOid);
                	if(findSubCategoryIndex != -1){
                		vm.categoryOidList.splice(findSubCategoryIndex, 1);
                    	vm.selectedSubCategoryOid  = "";
                	}
                	
                	var category = val;
                	var findIndex = vm.categoryOidList.indexOf(category.categoryOid);
                	
                	//체크해제일때
                	if(findIndex != -1){
                		vm.categoryOidList.splice(findIndex, 1);
                		vm.subCategoryList = [];
                		
                		if(vm.categoryOidList.length === 1){
                			vm.selectedMainCategoryOid = vm.categoryOidList[0];
                    		vm.getSubCategoryList();
                		}else{
                			vm.selectedMainCategoryOid = "";
                        	vm.getDatasetList(1);
                		}
                	//체크일때
                	}else{
                    	vm.categoryOidList.push(category.categoryOid);
                    	
                    	if(vm.categoryOidList.length === 1){
                    		vm.selectedMainCategoryOid = category.categoryOid;
                    		vm.getSubCategoryList();
                    	}else{
                    		vm.selectedMainCategoryOid = "";
                        	vm.getDatasetList(1);
                    	}
                	}
                	break;
                case "CLICK_SUB_CATEGORY_FILTER":
                	// 기존에 입력되어있던 상위 카테고리 제거
                	var findMainCategoryIndex = vm.categoryOidList.indexOf(vm.selectedMainCategoryOid);
                	if(findMainCategoryIndex != -1){
                		vm.categoryOidList.splice(findMainCategoryIndex, 1);
                	}
                	
                	var category = val;
                	var findIndex = vm.categoryOidList.indexOf(category.categoryOid);
                	//체크해제일때
                	if(findIndex != -1){
                		vm.categoryOidList.splice(findIndex, 1);
                		if(vm.categoryOidList.length === 1){
                			vm.selectedSubCategoryOid = vm.categoryOidList[0];
                		}else{
                			vm.selectedSubCategoryOid = "";
                		}
                		
                    //체크일때
                	}else{
                    	vm.categoryOidList.push(category.categoryOid);
                    	
                    	if(vm.categoryOidList.length === 1){
                    		vm.selectedSubCategoryOid = category.categoryOid;
                    	}else{
                    		vm.selectedSubCategoryOid = "";
                    	}
                	}
                	
                	vm.getDatasetList(1);
                	break;
                case "CLICK_CATEGORY_RESET":
            		vm.selectedMainCategoryOid = "";
            		vm.selectedSubCategoryOid = "";
            		vm.categoryOidList = [];
                	vm.subCategoryList = [];
                	vm.getDatasetList(1);
                	break;
                }
            },
            getDatasetOutputList: function(data){
				var list = [];
				for(var key in data){
					var type = typeof data[key];
					list.push({
						name: key,
						description: typeof data[key]
					})
				}
				return list;
			},
			selectDataset: function(dataset){
				this.clearMarker();
				$("ul.data-write-map").empty();
				var request = $.ajax({
					url: SMC_CTX + "/dataset/sample/get.do",
					method: "GET",
					dataType: "json",
					contentType: "application/json",
					data: {
						"datasetId"    : dataset.id,
						"datatype"     : "historydata",
						"timeAt"      : "between",
						"timeproperty" : "modifiedAt",
						"time"         : "2020-11-04 18:08:37",
						"endtime"      : "2020-12-04 18:08:37",
						"limit"        : 50
					}
				});
				
				request.done(function(data) {
					function isNotEmptyList(list){
						return list && list.length > 0;
					}
					if(isNotEmptyList(data)){
						vm.statisticsDatasetOutputList = vm.getDatasetOutputList(data[0]);
					}
					data.forEach(function(row){
						if(!row.location) return;
						var locationArr = row.location.split(',');
						var latlng = new naver.maps.LatLng(locationArr[1], locationArr[0]);
						
						vm.searchCoordinateToAddress(latlng, function(htmlAddresses, response){
							vm.createMarker({
								coord: latlng,
								data:{
									title : htmlAddresses[0],
									date  : moment(row.modifiedAt).format("YYYYMMDDHHmmss"),
									json: JSON.stringify(row, null, "&nbsp;"),
									vueInstance: vm
								}
							});
						});
						vm.mapInstance.updateBy(latlng, 15);
					});
				});
				
			},
			createMarker: function(obj){
				var markerHtml = $("#markerTemplate").html();
				markerHtml = markerHtml 
					.replace("#title" , obj.data.title)
					.replace("#date#" , obj.data.date);
				var marker = new naver.maps.Marker({
					map      : this.mapInstance,
					position : obj.coord,
					icon     : {
						content: markerHtml,
						size: N.Size(40, 40),
				    	anchor: N.Point(40, 88),
					},
					UserData: obj.data,
				});
				this.markers.push(marker);
			},
			clearMarker: function(){
				this.markers.forEach(function(marker){
					marker.setMap(null);
				});
				this.markers = [];
			},
			createMarkerCluster: function(){
				this.markerCluster = new MarkerClustering({
					minClusterSize: 2,
					maxZoom: 20,
					map: this.mapInstance,
					markers: this.markers,
					gridSize: 120,
					icons: [{
						content: $("#clusterTemplate").html(),
						size: N.Size(40, 40),
						anchor: N.Point(40, 88),
					}],
					indexGenerator: [10, 100, 200, 500, 1000],
					onClick: function(clusterMarker, markers){
						var $cluster           = $(clusterMarker.getElement());
						var $clusterMarker     = $cluster.find(".cluster_marker").toggleClass("not");
						var $clusterPopup      = $cluster.find(".cluster_popup").toggleClass("hidden");
					},
					stylingFunction: function(clusterMarker, count) {
						$(clusterMarker.getElement()).find('.current-patient__number > p').text(count);
					}
				});
			},
			searchCoordinateToAddress: function(latlng, callback) {
				naver.maps.Service.reverseGeocode({
					coords: latlng,
					orders: [
						naver.maps.Service.OrderType.ADDR,
						naver.maps.Service.OrderType.ROAD_ADDR
					].join(',')
				}, function(status, response) {
					if (status === naver.maps.Service.Status.ERROR) {
						if (!latlng) {
							return alert('ReverseGeocode Error, Please check latlng');
						}
						if (latlng.toString) {
							return alert('ReverseGeocode Error, latlng:' + latlng.toString());
						}
						if (latlng.x && latlng.y) {
							return alert('ReverseGeocode Error, x:' + latlng.x + ', y:' + latlng.y);
						}
						return alert('ReverseGeocode Error, Please check latlng');
					}
                	
					var address = response.v2.address, htmlAddresses = [];
                	
					if (address.jibunAddress !== '') {
					    htmlAddresses.push(address.jibunAddress);
					}
                	
					if (address.roadAddress !== '') {
					    htmlAddresses.push(address.roadAddress);
					}
					
					if(typeof callback === 'function'){
						callback(htmlAddresses, response);
					}
				});
			}
		}
	});
	
	vm.getMainCategoryList();
});

</script>