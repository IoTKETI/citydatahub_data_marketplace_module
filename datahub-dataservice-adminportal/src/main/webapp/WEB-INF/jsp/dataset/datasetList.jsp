<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content">
    <h3 class="content__title">데이터셋</h3>
    <form>
      <fieldset>
        <legend>데이터셋 검색 폼</legend>
        <section class="section">
          <div class="section__header">
            <h4 class="section__title">데이터셋 검색</h4>
          </div>
          <div class="section__content">
            <table class="table--row">
              <caption>데이터셋 검색 제목</caption>
              <colgroup>
                <col style="width:150px">
                <col style="width:auto">
                <col style="width:150px">
                <col style="width:auto">
              </colgroup>
              <tbody>
	                <tr>
	                  <th scope="row">검색어</th>
	                  <td>
	                    <select class="select" v-model="pageInfo.schCondition">
	                      <option value="">선택</option>
	                      <option value="title">제목</option>
	                      <option value="content">내용</option>
	                    </select>
	                    <input class="input__text" type="text" v-model="pageInfo.schValue" @keypress.enter="getDatasetList()">
	                  </td>
	                  <th scope="row">등록일</th>
	                  <td>
	                    <div class="picker__group">
	                      <label class="label__picker"><input class="input__picker" id="fromDate" type="text" v-model="pageInfo.fromDate"></label><span class="period">~</span><label class="label__picker"><input class="input__picker" id="toDate" type="text" v-model="pageInfo.toDate"></label>
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
	                </tr>
              </tbody>
            </table>
          </div>
          <div class="button__group">
            <button class="button__primary" type="button" @click="getDatasetList(1)">검색</button>
          </div>
        </section>
      </fieldset>
    </form>
    
    <section class="section">
      <div class="section__header">
        <h4 class="section__title">데이터셋 목록</h4>
      </div>
      <div class="section__content">
        <form class="filter-form" action="">
          <fieldset>
            <legend>상세 목록 필터 폼</legend>
            <div class="filter-form__group">
              <label class="label"><strong class="label__emphasis">전체 {{pageInfo.totalListSize}} 건 </strong>{{pageInfo.curPage}}/{{pageInfo.totalPage}} 페이지</label>
              <select class="select">
                <option value="선택">선택</option>
              </select>
              <select class="select">
                <option value="선택">선택</option>
              </select>
              <select class="select">
                <option value="선택">선택</option>
              </select>
              <select class="select select--full">
                <option value="선택">선택</option>
              </select>
              <input class="input__text" type="text" placeholder="검색어를 입력하세요">
              <button class="button__search material-icons" type="button" @click="getDatasetList(1)"><span class="hidden">검색</span></button>
            </div>
          </fieldset>
        </form>
        <ul class="data-list">
          <li class="data-list__item" v-if="list.length === 0">
            <div class="data-unit data-unit--none">데이터가 없습니다.</div>
          </li>
          <li class="data-list__item" v-for="dataset in list">
            <div class="data-unit">
              <a class="data-unit__link" :href="'/dataset/pageDetail.do?nav=${param.nav}&backUrl=/dataset/pageList.do?nav=${param.nav}&id='+dataset.id">
                  <div class="data-list__thumb">
                    <img class="data-list__image material-icons" :src="fileDownload(dataset.id, dataset.datasetImageFileId)" onerror="this.src='<c:url value="/admin/img/dummy/thumb_empty_100x100.jpg"/>'"/>
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
                    	<span class="badge badge--pink">무료</span>
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
        <n2m-pagination :pager="pageInfo" @pagemove="getDatasetList"/>
      </div>
    </section>
    
    <div class="button__group">
      <a class="button button__primary" href="<c:url value="/dataset/pageEdit.do?backUrl=/dataset/pageList.do?nav=${param.nav}"/>">등록</a>
    </div>
    
	<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetUsagePopup.jsp"/>
</div>
<script>
	$(function() {
		Vue.use(N2MPagination);
		vm = new Vue({
			el : '#content',
			mixins: [datasetUsagePopupMixin],
			data : function() {
				return {
					list : []
				}
			},
			methods : {
				getDatasetList : function(curPage) {
					var searchData = {};
                    searchData.curPage         = curPage;
                    searchData.schValue        = vm.pageInfo.schValue;
                    searchData.schCondition    = vm.pageInfo.schCondition;
                    searchData.schCondition2   = vm.pageInfo.schCondition2;
                    searchData.schCondition3   = vm.pageInfo.schCondition3;
                    searchData.fromDate        = vm.pageInfo.fromDate;
                    searchData.toDate          = vm.pageInfo.toDate;
					
					var request = $.ajax({
						url : N2M_CTX + "/dataset/getList.do",
						method : "GET",
						dataType: "json",
						data: searchData
					});
					
					request.done(function(data) {
						vm.list = data.list;
						vm.pageInfo = data.page;
					});
					
					request.fail(function(data) {
					});
				},
                onClick: function(code, val){
                    switch(code){
                    case "CLICK_DATASET_WISHLIST":
                    	var dataset = val;

                        if(dataset.hasDatasetWishlist){
                            if(confirm("관심상품을 취소하시겠습니까?")){
                                var request = $.ajax({
                                    url : N2M_CTX + "/dataset/wishlist/remove.do",
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
                            }
                        }else{
                            if(confirm("관심상품을 등록하시겠습니까?")){
                                var request = $.ajax({
                                    url : N2M_CTX + "/dataset/wishlist/create.do",
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
                    }
                },
                openPopup: function(code, val){
                	var dataset = val;
                    switch(code){
                        case "OPEN_POPUP_DATASET_USAGE":
                            if(dataset.hasDatasetUseRequest){
                                if(confirm("이용신청을 취소하시겠습니까?")){
                                    var request = $.ajax({
                                        url : N2M_CTX + "/dataset/use/remove.do",
                                        method : "DELETE",
                                        contentType: "application/json",
                                        data : JSON.stringify({
                                            id        : dataset.datasetUseRequestId,
                                            datasetId : dataset.id
                                            
                                        })
                                    });
                                    request.done(function(data) {
                                        Modal.OK();
                                        vm.getDatasetList(vm.pageInfo.curPage);
                                    });
                                }
                            }else{
                                vm.openDatasetUsagePopup(dataset.id, function(success){
                                    if(success){
                                    	vm.getDatasetList(vm.pageInfo.curPage);
                                    }
                                });
                            }
                            break;
                    }
                }
			}
		});
		
		vm.keepPageInfo(function(curPage) {
            vm.getDatasetList(curPage);
        });
	});
</script>
