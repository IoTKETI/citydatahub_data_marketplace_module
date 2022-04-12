<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
      <h2 class="hidden">본문</h2>
      <!-- sub-cover -->
      <section class="sub-cover">
        <h3 class="sub-cover__title sub-cover__title--data material-icons">데이터<small class="sub-cover__title--small">Smart City Data Hub</small></h3>
      </section>
      <hr>
      <!-- sub-nav -->
	<%@ include file="../tiles/tiles_sub_nav.jspf" %>
      <!-- sub-nav -->
      <hr>
      <!-- category-nav -->
      <nav class="category-nav">
        <h3 class="category-nav__title"><span class="hidden">카테고리</span></h3>
        <ul class="category-nav__list">
          <li class="category-nav__item" v-if="subCategoryList.length === 0">
              <a href="#none" class="category-nav__link material-icons" @click="onClick('CLICK_CATEGORY_FILTER_ALL', mainCategoryList)">전체카테고리</a>
          </li>
          <li class="category-nav__item" v-for="(category, index) in mainCategoryList" :class="containsCategory(category.categoryOid)" v-if="subCategoryList.length === 0">
              <a href="#none" class="category-nav__link material-icons" @click="onClick('CLICK_CATEGORY_FILTER', category)">{{category.categoryNm}}</a>
          </li>
          <li class="category-nav__item" v-if="subCategoryList.length !== 0" @click="onClick('CLICK_CATEGORY_RESET')">
              <a href="#none" class="category-nav__link material-icons">상위 메뉴</a>
          </li>
          <li class="category-nav__item" v-for="(category, index) in subCategoryList" :class="containsCategory(category.categoryOid)">
              <a href="#none" class="category-nav__link material-icons" @click="onClick('CLICK_SUB_CATEGORY_FILTER', category)">{{category.categoryNm}}</a>
          </li>
        </ul>
      </nav>
      <!-- //category-nav -->
      <hr>
      
      <div class="sub__container">
      	<!-- breadcrumb -->
        <%@ include file="../tiles/tiles_breadcrumb.jspf" %>
        <!-- //breadcrumb -->
        <h3 class="sub__heading1">데이터<small class="sub__heading1--small">카테고리별 다양한 데이터를 확인하여 보시기 바랍니다.</small></h3>
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
	                  <th scope="row">제목</th>
	                  <td>
	                      <input class="input input__search" type="search" v-model="pageInfo.searchTitle" @keypress.enter="getDatasetList">
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
	                    <select class="select" v-model="pageInfo.dataOfferType">
	                      <option value="">선택</option>
                          <c:forEach var="code" items="${n2m:getCodeList('CG_ID_OFFER_TYPE')}">
                              <option value="${code.codeId}">${code.codeName}</option>
                          </c:forEach>
	                    </select>
	                  </td>
	                  <th scope="row">진행상태</th>
	                  <td>
	                    <select class="select" v-model="pageInfo.searchStatus">
                            <option value="release_all">전체</option>
                            <c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_STATUS')}" varStatus="status">
                                <c:if test="${status.index == 6 or status.index == 3}">
                                    <option value="${code.codeId}">${code.codeName}</option>
                                </c:if>
                            </c:forEach>
                        </select>
	                  </td>
	                  <td rowspan="2"><button class="button__submit" type="button" @click="getDatasetList(1, true)">검색</button></td>
	                </tr>
	              </tbody>
	            </table>
	          </fieldset>
	        </form>
        </div>
        <div class="sub__content">
          <h4 class="sub__heading2">상세목록</h4>
          <div class="data-list__filter">
            <form class="filter-form" action="">
              <fieldset>
                <legend>상세 목록 필터 폼</legend>
                <div class="filter-form__group">
                  <label class="label"><strong class="label__emphasis">전체 {{pageInfo.totalListSize}} 건 </strong>{{pageInfo.curPage}}/{{pageInfo.totalPage}} 페이지</label>
                  <select class="select" v-model="selectedMainCategoryOid" @change="changeMainCategory">
                    <option value="">선택</option>
                    <option :value="category.categoryOid" v-for="category in mainCategoryList">{{category.categoryNm}}</option>
                  </select>
                  <select class="select" v-model="selectedSubCategoryOid" @change="changeSubCategory" :disabled="subCategoryList.length === 0">
                    <option value="">선택</option>
                    <option :value="category.categoryOid" v-for="category in subCategoryList">{{category.categoryNm}}</option>
                  </select>
                </div>
              </fieldset>
            </form>
          </div>
          <ul class="data-list">
          	<li class="data-list__item" v-if="list.length === 0">
          		<div class="data-unit data-unit--none">
          			데이터가 없습니다.
          		</div>
          	</li>
          <!--  -->
            <li class="data-list__item" v-for="dataset in list">
              <div class="data-unit">
                <a class="data-unit__link" :href="'/dataset/pageDetail.do?backUrl=/dataset/pageList.do?nav=${param.nav}&id='+dataset.id">
                  <div class="data-list__thumb">
                    <img class="data-list__image material-icons" :src="fileDownload(dataset.id, dataset.datasetImageFileId)" onerror="this.src='<c:url value="/smartcity/img/dummy/thumb_empty_100x100.jpg"/>'"/>
                  </div>
                  <dl class="data-unit__caption">
                  	<dd class="data-unit__category">
                  		<span class="data-unit__status">[{{dataset.statusName}}]</span>{{dataset.categoryName}}
                  	</dd>
                    <dt class="data-unit__title">{{dataset.title}}</dt>
                    <dd class="data-unit__text">{{dataset.description}}</dd>
                    <dd class="data-unit__badge">
                    	<span class="badge badge--blue" v-if="dataset.extensionName">{{dataset.extensionName}}</span>
                    	<span class="badge badge--green" v-if="dataset.dataOfferTypeName">{{dataset.dataOfferTypeName}}</span>
                    	<span class="badge badge--orange" v-if="dataset.dataTypeName">{{dataset.dataTypeName}}</span>
                    	<span class="badge badge--pink">{{dataset.status != 'paidMode' ? '무료': '유료'}} </span>
                    </dd>
                    
                  </dl>
                </a>
                <div class="data-unit__info">
                  <span class="data-unit__owner">{{dataset.creatorId}}</span>
                  <span class="data-unit__date">{{dataset.createdAt|date}}</span>
                  <span class="data-unit__view material-icons"><span class="hidden">조회수</span>{{dataset.retrievalCount}}</span>
                  <span class="data-unit__like material-icons"><span class="hidden">관심 상품으로 담긴 회수</span>{{dataset.datasetWishlistTotalCount}}</span>
                  <button class="data-unit__button data-unit__button--like-add material-icons" type="button" :class="{ 'is-active': dataset.hasDatasetWishlist }" @click="onClick('CLICK_DATASET_WISHLIST', dataset)">관심상품담기</button>
                  <button class="data-unit__button data-unit__button--request material-icons" type="button" :class="{ 'is-active': dataset.hasDatasetUseRequest}" type="button" @click="openPopup('OPEN_POPUP_DATASET_USAGE', dataset)">데이터이용신청</button>
                </div>
              </div>
            </li>
          </ul>
        </div>
        <div class="sub__bottom">
          <div>
	          <n2m-pagination :pager="pageInfo" @pagemove="getDatasetList"/>
          </div>
          <div class="button__group">
          
            <a class="button button__primary" href="<c:url value="/dataset/pageEdit.do?backUrl=/dataset/pageList.do?nav=${param.nav}"/>">등록</a>
          </div>
        </div>
      </div>
      
</div>

<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetUsagePopup.jsp"/>
<script>
	$(function() {
		Vue.use(N2MPagination);
		vm = new Vue({
			el : '#content',
			mixins: [datasetUsagePopupMixin],
			data : function() {
				return {
					list                    : [],
				    mainCategoryList        : [],
				    subCategoryList         : [],
				    selectedMainCategoryOid : ${not empty param.categoryId ? param.categoryId : '""'},
				    selectedSubCategoryOid  : "",
				    categoryOidList         : [${param.categoryId}],
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
            },
			methods : {
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
						}
					});
					
					request.done(function(data) {
						vm.mqttIp = data.mqttIp;
						vm.webSocketIp = data.webSocketIp;
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
                    case "CLICK_DATASET_WISHLIST":
                    	var dataset = val;

                    	if (vm.loginUserId == null || vm.loginUserId == "") {
                    		if(confirm("로그인이 필요한 서비스 입니다. 로그인을 하시겠습니까?")){
                    			location.href = SMC_CTX + '/login/oauthlogin.do';
                    		} 
                    		break;
                    	}
                        if(dataset.hasDatasetWishlist){
                            if(confirm("관심상품을 취소하시겠습니까?")){
                                var request = $.ajax({
                                    url : SMC_CTX + "/dataset/wishlist/remove.do",
                                    method : "DELETE",
                                    contentType: "application/json",
                                    data : JSON.stringify({
                                        id            : dataset.datasetWishlistId,
                                        datasetId     : dataset.id
                                    })
                                });
                                request.done(function(data) {
                                    Modal.OK();
                                    vm.getDatasetList(vm.pageInfo.curPage);
                                });
                                request.always(function(data){
                                	console.log("local");
                                });
                            }
                        }else{
                            if(confirm("관심상품을 등록하시겠습니까?")){
                                var request = $.ajax({
                                    url : SMC_CTX + "/dataset/wishlist/create.do",
                                    method : "POST",
                                    contentType: "application/json",
                                    data : JSON.stringify({
                                        id        : dataset.datasetWishlistId,
                                    	datasetId : dataset.id
                                    })
                                });
                                request.done(function(data) {
                                    Modal.OK();
                                    vm.getDatasetList(vm.pageInfo.curPage);
                                });
                            }
                        }
                        break;
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
                openPopup: function(code, val){
                	var dataset = val;
                    switch(code){
                        case "OPEN_POPUP_DATASET_USAGE":
                        	if (vm.loginUserId == null || vm.loginUserId == "") {
                        		if(confirm("로그인이 필요한 서비스 입니다. 로그인을 하시겠습니까?")){
                        			location.href = SMC_CTX + '/login/oauthlogin.do';
                        		} 
                        		break;
                        	}
                            vm.openDatasetUsagePopup(dataset, function(success){
                                if(success){
                                	vm.getDatasetList(vm.pageInfo.curPage);
                                }
                            });
                            break;
                    }
                }
			}
		});
		
		vm.getMainCategoryList();
	});
</script>
