<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
      <h2 class="hidden">본문</h2>
<!-- sub-cover -->
<section class="sub-cover">
  <h3 class="sub-cover__title sub-cover__title--data material-icons">마이페이지 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<!-- sub-nav -->
<%@ include file="../tiles/tiles_sub_nav.jspf" %>
<!-- sub-nav -->
<hr>
      <div class="sub__container">  
      	<!-- breadcrumb -->
        <%@ include file="../tiles/tiles_breadcrumb.jspf" %>
        <!-- //breadcrumb -->
        <h3 class="sub__heading1">관심데이터 검색</h3>
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
	                  <th scope="row">검색어</th>
	                  <td><select class="select" v-model="pageInfo.schCondition">
	                      <option value="">선택</option>
	                      <option value="title">제목</option>
	                      <option value="content">내용</option>
	                    </select>
	                    <input class="input input__search" type="search" v-model="pageInfo.schValue" @keypress.enter="getDatasetWishlList()">
	                  </td>
	                  <th scope="row">등록일</th>
	                  <td>
	                    <div class="datepicker">
	                      <label class="label__datepicker material-icons"><input class="input input__datepicker" id="fromDate" type="text" v-model="pageInfo.fromDate"></label><span class="period">~</span><label class="label__datepicker material-icons"><input class="input input__datepicker" id="toDate" type="text" v-model="pageInfo.toDate"></label>
	                    </div>
	                  </td>
	                </tr>
	                <tr>
	                  <th scope="row">제공유형</th>
	                  <td>
	                    <select class="select" v-model="pageInfo.schCondition2">
	                      <option value="">선택</option>
                          <c:forEach var="code" items="${n2m:getCodeList('CG_ID_OFFER_TYPE')}">
                              <option value="${code.codeId}">${code.codeName}</option>
                          </c:forEach>
	                    </select>
	                  </td>
	                  <th scope="row">진행상태</th>
	                  <td>
	                    <select class="select" v-model="pageInfo.schCondition3">
                            <option value="">선택</option>
                            <c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_STATUS')}" varStatus="status">
                                <c:if test="${status.index > 5}">
                                    <option value="${code.codeId}">${code.codeName}</option>
                                </c:if>
                            </c:forEach>
                        </select>
	                  </td>
	                  <td rowspan="2"><button class="button__submit" type="button" @click=getDatasetWishlList()>검색</button></td>
	                </tr>
	              </tbody>
	            </table>
	          </fieldset>
	        </form>
        </div>
       <div class="sub__content">
          <h4 class="sub__heading2">상세목록</h4>
          
          <ul class="data-list">
          <li class="data-list__item" v-if="list.length === 0">
          		<div class="data-unit data-unit--none">
          			데이터가 없습니다.
          		</div>
          	</li>
          <!--  -->
            <li class="data-list__item" v-for="dataset in list">
              <div class="data-unit">
                <a class="data-unit__link" :href="'/dataset/pageDetail.do?id='+dataset.id+'&backUrl='+vm.returnUrl">
                  <div class="data-list__thumb">
                    <img class="data-list__image material-icons" :src="fileDownload(dataset.id, dataset.datasetImageFileId)" onerror="this.src='<c:url value="/smartcity/img/dummy/thumb_empty_100x100.jpg"/>'"/>
                  </div>
                  <dl class="data-unit__caption">
                  	<dd class="data-unit__category">
                  		<span class="data-unit__status" v-if="dataset.dsStatus != 'prodMode'">[{{dataset.statusName}}]</span>{{dataset.categoryName}}
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
	          <n2m-pagination :pager="pageInfo" @pagemove="getDatasetWishlList"/>
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
					list : [],
					context : '${requestScope["javax.servlet.forward.request_uri"]}',
					param: "${param.nav}",
					returnUrl :"",
				}
			},
			methods : {
				getDatasetWishlList : function(curPage) {
					vm.loadingFlag = true;
					var searchData = {};
                    searchData.curPage         = curPage;
                    searchData.schValue        = vm.pageInfo.schValue;
                    searchData.schCondition    = vm.pageInfo.schCondition;
                    searchData.schCondition2   = vm.pageInfo.schCondition2;
                    searchData.schCondition3   = vm.pageInfo.schCondition3;
                    searchData.fromDate        = vm.pageInfo.fromDate;
                    searchData.toDate          = vm.pageInfo.toDate;
                    
					var request = $.ajax({
						url : SMC_CTX + "/mypage/wishlist/getList.do",
						method : "GET",
						dataType: "json",
						data: searchData
					});
					
					request.done(function(data) {
						vm.loadingFlag = false;
						vm.returnUrl = vm.context +"?nav="+vm.param;
						vm.list = data.list;
						vm.pageInfo = data.page;
					});
					
					request.fail(function(data) {
						vm.loadingFlag = false;
						console.log("FAIL");
					});
				},
				containsCategory: function(categoryOid){
					return {
						'is-select': vm.dsCategoryOidList.indexOf(categoryOid) != -1
					};
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
                                    vm.getDatasetWishlList(vm.pageInfo.curPage);
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
                                    vm.getDatasetWishlList(vm.pageInfo.curPage);
                                });
                            }
                        }
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
                        	
                            if(dataset.hasDatasetUseRequest){
                                if(confirm("이용신청을 취소하시겠습니까?")){
                                    var request = $.ajax({
                                        url : SMC_CTX + "/dataset/use/remove.do",
                                        method : "DELETE",
                                        contentType: "application/json",
                                        data : JSON.stringify({
                                            id        : dataset.datasetUseRequestId,
                                            datasetId : dataset.id
                                            
                                        })
                                    });
                                    request.done(function(data) {
                                        Modal.OK();
                                        vm.getDatasetWishlList(vm.pageInfo.curPage);
                                    });
                                }
                            }else{
                                vm.openDatasetUsagePopup(dataset.id, function(success){
                                    if(success){
                                    	vm.getDatasetWishlList(vm.pageInfo.curPage);
                                    }
                                });
                            }
                            break;
                    }
                }
            }
		});
		vm.keepPageInfo(function(curPage) {
			vm.getDatasetWishlList(curPage);
		});
	});
</script>
