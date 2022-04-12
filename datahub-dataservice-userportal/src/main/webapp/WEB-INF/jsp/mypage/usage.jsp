<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<h2 class="hidden">본문</h2>
<!-- sub-cover -->
<section class="sub-cover">
  <h3 class="sub-cover__title sub-cover__title--data material-icons">마이페이지 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<hr>
<!-- sub-nav -->
<%@ include file="../tiles/tiles_sub_nav.jspf" %>
<!-- sub-nav -->
<hr>
<!-- category-nav 
<nav class="category-nav">
  <h3 class="category-nav__title"><span class="hidden">카테고리</span></h3>
  <ul class="category-nav__list">
    <li class="category-nav__item"><a href="#none" class="category-nav__link material-icons" @click="onClick('CLICK_CATEGORY_FILTER_ALL', categoryList)">전체카테고리</a></li>
    <li class="category-nav__item" v-for="(category, index) in categoryList" :class="containsCategory(category.categoryOid)">
      <a href="#none" class="category-nav__link material-icons" @click="onClick('CLICK_CATEGORY_FILTER', category)">{{category.categoryNm}}</a>
    </li>
  </ul>
</nav>-->
<!-- //category-nav -->
<div class="sub__container">
	<!-- breadcrumb -->
  <%@ include file="../tiles/tiles_breadcrumb.jspf" %>
  <!-- //breadcrumb -->
  <h3 class="sub__heading1">데이터이용내역 검색</h3>
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
                  <input class="input input__search" type="search" v-model="pageInfo.schValue" @keypress.enter="getDatasetUsageList()">
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
                <td rowspan="2"><button class="button__submit" type="button" @click=getDatasetUsageList()>검색</button></td>
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
      <li class="data-list__item" v-for="(dataset, index) in list">
        <div class="data-unit">
          <a class="data-unit__link" :href="'/dataset/pageDetail.do?id='+dataset.id+'&backUrl='+vm.returnUrl">
            <div class="data-list__thumb">
              <img class="data-list__image material-icons" :src="fileDownload(dataset.id, dataset.datasetImageFileId)" onerror="this.src='<c:url value="/smartcity/img/dummy/thumb_empty_100x100.jpg"/>'"/>
            </div>
            <dl class="data-unit__caption">
            	<dd class="data-unit__category">
            		<span class="data-unit__status" v-if="dataset.status != 'prodMode'">[{{dataset.statusName}}]</span>{{dataset.categoryName}}
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
            <button class="data-unit__button data-unit__button--postadd material-icons is-active" type="button" :style="{ 'visibility': dataset.status == 'paidMode' && dataset.datasetUseRequestPayment && dataset.datasetUseRequestPayment.completedAt ? '' : 'hidden'}" @click="dataset.expanded = !dataset.expanded">구매 내역 보기</button>
            <button class="data-unit__button data-unit__button--request material-icons" type="button" :class="{ 'is-active': dataset.hasDatasetUseRequest}" type="button" @click="openPopup('OPEN_POPUP_DATASET_USAGE', dataset)">데이터이용신청</button>
          </div>
        </div>
        <div v-if="dataset.expanded && dataset.status == 'paidMode' && dataset.datasetUseRequestPayment && dataset.datasetUseRequestPayment.completedAt">
            <div class="data-unit-detail">
                <dl class="data-unit__caption data-info">
                    <dt>데이터 정보</dt>
                    <dd><i class="icon__require">*</i><span>트래픽 유형 :</span> {{dataset.datasetUseRequestPolicies.trafficType == 'limit' ? '일일 제한' : '무제한'}}</dd>
                    <dd><i class="icon__require">*</i><span>일일제한량 :</span> {{dataset.datasetUseRequestPolicies.limit | comma}} 건</dd>
                </dl>
                <dl class="data-unit__caption data-info">
                    <dt>구매 정보</dt>
                    <dd><i class="icon__require">*</i><span>결제번호 :</span> {{dataset.datasetUseRequestPayment.id}}</dd>
                    <dd><i class="icon__require">*</i><span>결제금액 :</span> {{dataset.datasetUseRequestPrice.price | comma}} 원</dd>
                </dl>
                <dl class="data-unit__caption data-info">
                    <dt><br></dt>
                    <dd><i class="icon__require">*</i><span>결제일시 :</span> {{dataset.datasetUseRequestPayment.completedAt|date}}</dd>
                    <dd><i class="icon__require">*</i><span>만료일시 :</span> {{dataset.datasetUseRequestPayment.expiredAt|date}}</dd>
                </dl>
                <button class="data-unit__button-apply material-icons" type="button" v-if="!dataset.datasetUseRequestRefund || dataset.datasetUseRequestRefund.status == 'pay_error'" @click="openPopup('OPEN_POPUP_DATASET_REFUND', dataset)">환불 신청</button>
                <button class="data-unit__button-apply material-icons" type="button" v-if="dataset.datasetUseRequestRefund && dataset.datasetUseRequestRefund.status == 'pay_request'" style="background-color: #B2B2B2;">환불 처리중</button>
                <button class="data-unit__button-apply material-icons" type="button" v-if="dataset.datasetUseRequestRefund && dataset.datasetUseRequestRefund.status == 'pay_complete'" style="background-color: #B2B2B2;">환불 완료</button>
            </div>
        </div>
      </li>
    </ul>
  </div>
  <div class="sub__bottom">
    <div>
        <n2m-pagination :pager="pageInfo" @pagemove="getDatasetUsageList"/>
    </div>
  </div>
</div>

<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetUsagePopup.jsp"/>
<jsp:include page="/WEB-INF/jsp/mypage/popup/datasetRefundPopup.jsp"/>
<script>
	$(function() {
		Vue.use(N2MPagination);
		vm = new Vue({
			el : '#content',
			mixins: [datasetUsagePopupMixin, datasetRefundPopupMixin],
			data : function() {
				return {
					list : [],
					returnUrl :"",
				}
			},
			methods : {
				getDatasetUsageList : function(curPage) {
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
						url : SMC_CTX + "/mypage/usage/getList.do",
						method : "GET",
						dataType: "json",
						data: searchData
					});
					
					request.done(function(data) {
						vm.loadingFlag = false;
						vm.returnUrl = '${requestScope["javax.servlet.forward.request_uri"]}?nav=${param.nav}';
						vm.list = data.list.map(function(row){
							row.expanded = false;
							return row;
						});
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
                openPopup: function(code, val){
                	var dataset = val;
                    switch(code){
                        case "OPEN_POPUP_DATASET_USAGE":
                            vm.openDatasetUsagePopup(dataset, function(success){
                                if(success){
                                	vm.getDatasetUsageList(vm.pageInfo.curPage);
                                }
                            });
                            break;
          				case "OPEN_POPUP_DATASET_REFUND":
          					vm.openDatasetRefundPopup(dataset, function(){
                            	vm.getDatasetUsageList(vm.pageInfo.curPage);
          					});
          					break;
                    }
                },
			}
		});
		vm.keepPageInfo(function(curPage) {
			vm.getDatasetUsageList(curPage);
		});
	});
</script>
