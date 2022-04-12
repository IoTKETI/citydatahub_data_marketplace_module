<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- sub-cover -->
<section class="sub-cover">
	<h3 class="sub-cover__title sub-cover__title--data material-icons">데이터<small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<hr>
 <!-- sub-nav -->
<%@ include file="../tiles/tiles_sub_nav.jspf" %>
 <!-- sub-nav -->
<hr>
<!-- process -->
<div class="process__wrap">
  <ol class="process__list">
	<c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_STATUS')}" varStatus="status">
	   <!-- is-current -->
	    <li class="process__item material-icons" :class="{'is-current': dataset.status == '${code.codeId}'}">
	      <span class="process__text-step">step${status.count}</span>
	      <span class="process__text">${code.codeName}</span>
	    </li>
	</c:forEach>
  </ol>
</div>
<!-- // process -->
<div class="sub__container">
  <!-- breadcrumb -->
  <%@ include file="../tiles/tiles_breadcrumb.jspf" %>
  <!-- //breadcrumb -->
  <h3 class="sub__heading1">데이터<small class="sub__heading1--small">카테고리별 다양한 데이터를 확인하여 보시기 바랍니다.</small></h3>
  <div class="data-view">
    <div class="data-view__header">
      <div class="data-unit">
        <div class="data-view__thumb">
	            <img v-if="imageFile.exists" width="165" height="150" class="data__image" :src="imageFile.src">
	            <img v-else width="165" height="150" class="data__image" src="<c:url value="/smartcity/img/dummy/thumb_empty_150x150.jpg"/>">
        </div>
        <dl class="data-unit__caption">
          <dt class="data-unit__title">{{dataset.title}}</dt>
          <dd class="data-unit__category">{{dataset.categoryName}}</dd>
          <dd class="data-unit__info">
            <span class="data-unit__view material-icons"><span class="hidden">조회수</span>{{dataset.retrievalCount}}</span>
            <span class="data-unit__like material-icons"><span class="hidden">관심 상품으로 담긴 회수</span>{{dataset.datasetWishlistTotalCount}}</span></dd>
        </dl>
      </div>
      <div class="data-unit__rating"  @click="openPopup('OPEN_POPUP_DATASET_SATISFACTION')">
        <h4 class="hidden">만족도 평가하기</h4>
        <div class="rating rating--disabled">
          <input class="rating__input" id="rating5" type="radio" name="rating" value="5" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--full material-icons" for="rating5" title="5점"></label>
          <input class="rating__input" id="rating4Half" type="radio" name="rating" value="4.5" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--half material-icons" for="rating4Half" title="4.5점"></label>
          <input class="rating__input" id="rating4" type="radio" name="rating" value="4" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--full material-icons" for="rating4" title="4점"></label>
          <input class="rating__input" id="rating3Half" type="radio" name="rating" value="3.5" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--half material-icons" for="rating3Half" title="3.5점"></label>
          <input class="rating__input" id="rating3" type="radio" name="rating" value="3" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--full material-icons" for="rating3" title="3점"></label>
          <input class="rating__input" id="rating2Half" type="radio" name="rating" value="2.5" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--half material-icons" for="rating2Half" title="2.5점"></label>
          <input class="rating__input" id="rating2" type="radio" name="rating" value="2" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--full material-icons" for="rating2" title="2점"></label>
          <input class="rating__input" id="rating1Half" type="radio" name="rating" value="1.5" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--half material-icons" for="rating1Half" title="1.5점"></label>
          <input class="rating__input" id="rating1" type="radio" name="rating" value="1" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--full material-icons" for="rating1" title="1점"></label>
          <input class="rating__input" id="ratingHalf" type="radio" name="rating" value="0.5" disabled v-model="dataset.datasetSatisfactionRatingScoreAverage">
          <label class="rating__label rating__label--half material-icons" for="ratingHalf" title="0.5점"></label>
        </div>
        <p class="rating__count">{{dataset.datasetSatisfactionRatingCount}}명 참여</p>
      </div>
      <div class="data-unit__buttons">
        <button class="data-unit__button data-unit__button--like-add material-icons" :class="{ 'is-active': dataset.hasDatasetWishlist}" type="button" @click="onClick('CLICK_DATASET_WISHLIST')">관심상품담기</button>
        <button class="data-unit__button data-unit__button--request material-icons" :class="{ 'is-active': dataset.hasDatasetUseRequest}" type="button" @click="openPopup('OPEN_POPUP_DATASET_USAGE')">데이터이용신청</button>
        <a class="data-unit__button data-unit__button--excel material-icons" type="button" v-if="dataset.dataOfferType == 'file'" @click="openPopup('OPEN_POPUP_DATASET_EXCEL_DOWNLOAD')">엑셀 다운로드</a>
      </div>
    </div>
    <div class="metadata-table">
      <table>
        <caption>메타 데이터 표</caption>
        <colgroup>
          <col width="20%">
          <col width="20%">
          <col width="20%">
          <col width="20%">
          <col width="20%">
        </colgroup>
        <tbody>
          <tr>
            <th scope="col">제공방식(포맷)</th>
            <th scope="col">데이터유형</th>
            <th scope="col">제공기관</th>
            <th scope="col">제공시스템</th>
            <th scope="col">담당자/연락처</th>
          </tr>
          <tr>
            <td>
                <span class="badge badge--green" v-if="dataset.dataOfferTypeName">{{dataset.dataOfferTypeName}}</span>
                <span class="badge badge--blue" v-if="dataset.dataOfferType == 'file' && dataset.extensionName">{{dataset.extensionName}}</span>
            </td>
            <td>
                <span class="badge badge--orange" v-if="dataset.dataOfferTypeName">{{dataset.dataTypeName}}</span>
            </td>
            <td>{{dataset.provider}}</td>
            <td>{{dataset.providerSystem}}</td>
            <td>{{dataset.dsModelOwnerNm}} {{dataset.dsModelOwnerPhone}}</td>
          </tr>
          <tr>
            <th scope="col">이메일</th>
            <th scope="col">갱신주기</th>
            <th scope="col">이용료</th>
            <th scope="col">등록일</th>
            <th scope="col">최종수정일</th>
          </tr>
          <tr>
            <td>{{dataset.dsModelOwnerEmail}}</td>
            <td>{{dataset.updatePeriod}}</td>
            <td><span class="badge badge--pink">무료</span></td>
            <td>{{dataset.createdAt|date}}</td>
            <td>{{dataset.modifiedAt|date}}</td>
          </tr>
        </tbody>
      </table>
    </div>
    <div class="sub__content">
      <h4 class="sub__heading2">내용</h4>
      <p class="sub__text">{{dataset.description}}</p>
    </div>
    <div class="sub__content">
      <h4 class="sub__heading2">키워드</h4>
      <p class="sub__text">{{dataset.keywords}}</p>
    </div>
    <div class="sub__content">
      <h4 class="sub__heading2">라이선스</h4>
      <p class="sub__text">{{dataset.licenseName}}</p>
    </div>
    <div class="sub__content">
      <h4 class="sub__heading2">제약 사항</h4>
      <p class="sub__text">{{dataset.constraints}}</p>
    </div>
    <ul class="tab__list">
      <li class="tab__item is-active"><a class="tab__button" href="#none">상세정보</a>
        <div class="tab__panel">
          <div class="sub__content">
            <h5 class="sub__heading3">상세정보</h5>
            <ul class="sub__list">
              <li class="sub__item">데이터명세서에 대한 API 상세 설명입니다.</li>
              <li class="sub__item">일일접속 1000건 으로 제안 되어있습니다.</li>
              <li class="sub__item">더 자세한 내용은 사용자 가이드 파일 정보에 첨부되어있는 가이드 문서를 참조해 주시기 바랍니다.</li>
            </ul>
          </div>
          <div class="sub__content" v-if="dataset.dataOfferType != 'file'">
            <h5 class="sub__heading3">End Point 주소</h5>
            <div class="address-box">
              <input ref="endPointAddr" class="address-box__input" type="text" :value="'${endpoint}/dataservice/dataset/'+ dataset.id + '/data'" readonly>
               <button class="button button__secondary" type="button" @click="openPopup('OPEN_POPUP_DATASET_SAMPLE_SEARCH')">샘플보기</button>
            </div>
          </div>
          <div class="sub__content" v-if="dataset.dataOfferType != 'file'">
            <h5 class="sub__heading3">요청변수(HEADER)</h5>
            <table class="board-table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="15%">
                <col width="25%">
                <col width="45%">
                <col width="15%">
              </colgroup>
              <thead>
                <tr>
                  <th class="text--left" scope="col">항목명</th>
                  <th class="text--left" scope="col">항목설명</th>
                  <th class="text--left" scope="col">샘플데이터</th>
                  <th class="text--center" scope="col">필수여부</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(header, index) in introHeaderInfo" style="cursor:auto;">
                  <th class="text--left" scope="row">{{header.headerName}}</th>
                  <th class="text--left" scope="row">{{header.headerDesc}}</th>
                  <th class="text--left" scope="row">{{header.sampleData}}</th>
                  <th class="text--center" scope="row">{{header.required}}</th>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="sub__content" v-if="dataset.dataOfferType != 'file'">
            <h5 class="sub__heading3">요청변수(PARAMETER)</h5>
            <table class="board-table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="15%">
                <col width="25%">
                <col width="25%">
                <col width="20%">
                <col width="10%">
                <col width="10%">
              </colgroup>
              <thead>
                <tr>
                  <th class="text--left" scope="col">항목명</th>
                  <th class="text--left" scope="col">항목설명</th>
                  <th class="text--left" scope="col">샘플데이터</th>
                  <th class="text--left" scope="col">비고</th>
                  <th class="text--center" scope="col">이력여부</th>
                  <th scope="col">필수여부</th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="(param, index) in introParamInfo" style="cursor:auto;">
<!--                   <td colspan="5" v-if="index === 2"><span class="sub__heading1">아래 데이터는 datatype이 historydata인 경우에만 추가</span></td> -->
                  <td class="text--left" scope="row">{{param.paramName}}</td>
                  <td class="text--left" scope="row">{{param.paramDesc}}</td>
                  <td class="text--left" scope="row">{{param.sampleData}}</td>
                  <td class="text--left" scope="row">{{param.etc}}</td>
                  <td class="text--center" scope="row">{{param.historyYn}}</td>
                  <td class="text--center" scope="row">{{param.required}}</td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="sub__content">
            <h5 class="sub__heading3">첨부파일</h5>
            <div class="attach-file__box">
              <p class="attach-file__download--none" v-if="dataset.datasetFileList.length === 0">첨부파일이 없습니다</p>
              <a class="attach-file__download material-icons" :href="fileDownload(dataset.id, file.oid)" v-for="(file, index) in dataset.datasetFileList" ><!--
              -->{{file.name}} <small class="attach-file__size">{{file.size | size}}</small></a>
            </div>
          </div>
          <div class="sub__content" v-if="dataset.datasetInstanceList.length > 0 && dataset.dataOfferType != 'file'">
            <h5 class="sub__heading3">DataInstance정보</h5>
            <div class="fixed-table__wrap">
               <div class="fixed-table__thead">
                 <table class="fixed-table">
                   <caption>해당 테이블에 대한 캡션</caption>
                   <colgroup>
                     <col width="20%">
                     <col width="80%">
                   </colgroup>
                   <thead>
                     <tr>
                       <th scope="col">No</th>
                       <th scope="col">DataInstance명</th>
                     </tr>
                   </thead>
                 </table>
               </div>
               <div class="fixed-table__tbody" style="min-height: 30px; max-height: 300px">
                 <table class="fixed-table">
                   <caption>해당 테이블에 대한 캡션</caption>
                   <colgroup>
                     <col width="20%">
                     <col width="80%">
                   </colgroup>
                   <tbody>
                       <tr v-if="dataset.datasetInstanceList.length === 0">
                           <td colspan="3">DataInstance 정보가 없습니다.</td>
                       </tr>
                       <tr v-for="(inst, index) in dataset.datasetInstanceList" style="cursor:auto;">
                           <th class="text--center" scope="row">{{index+1}}</th>
                           <th class="text--center" scope="row">{{inst.instanceId}}</th>
                       </tr>
                   </tbody>
                 </table>
               </div>
             </div>
          </div>
          <div class="sub__content" v-if="dataset.datasetSearchInfoList.length > 0 && dataset.dataOfferType != 'file'">
            <h5 class="sub__heading3">데이터 조회조건정보</h5>
            <div class="fixed-table__wrap">
               <div class="fixed-table__thead">
                 <table class="fixed-table">
                   <caption>해당 테이블에 대한 캡션</caption>
                   <colgroup>
                     <col width="20%">
                     <col width="20%">
                     <col width="20%">
                     <col width="20%">
                     <col width="20%">
                   </colgroup>
                   <thead>
                     <tr>
                       <th class="text--center" scope="col">No</th>
                       <th class="text--left"   scope="col">컬럼명</th>
                       <th class="text--left"   scope="col">서브컬럼명</th>
                       <th class="text--center" scope="col">비교</th>
                       <th class="text--center" scope="col">값</th>
                     </tr>
                   </thead>
                 </table>
               </div>
               <div class="fixed-table__tbody" style="min-height: 30px; max-height: 300px">
                 <table class="fixed-table">
                   <caption>해당 테이블에 대한 캡션</caption>
                   <colgroup>
                     <col width="20%">
                     <col width="20%">
                     <col width="20%">
                     <col width="20%">
                     <col width="20%">
                   </colgroup>
                   <tbody>
                       <tr v-for=" (search, index) in dataset.datasetSearchInfoList" style="cursor:auto;">
                           <th class="text--center" scope="row">{{index+1}}</th>
                           <th class="text--left" scope="row">{{search.mainAttribute}}</th>
                           <th class="text--left" scope="row">{{search.subAttribute}}</th>
                           <th class="text--center" scope="row">{{search.symbolName}}</th>
                           <th class="text--center" scope="row">{{search.value}}</th>
                       </tr>
                   </tbody>
                 </table>
               </div>
             </div>
          </div>
          <div class="sub__content" v-if="dataset.dataOfferType != 'file'">
            <h5 class="sub__heading3">출력정보</h5>
            <table class="board-table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="20%">
                <col width="40%">
                <col width="40%">
              </colgroup>
              <thead>
                <tr>
                  <th class="text--left" scope="col">No</th>
                  <th class="text--left" scope="col">출력명</th>
                  <th class="text--left" scope="col">설명</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="dataset.datasetOutputList.length === 0">
                    <td colspan="3">데이터셋 출력 정보가 없습니다.</td>
                </tr>
                <tr v-for="(datasetOutput, index) in dataset.datasetOutputList" style="cursor:auto;">
                  <th scope="row">{{index+1}}</th>
                  <td class="text--left">{{datasetOutput.aliasColumnName}}</td>
                  <td class="text--left">{{datasetOutput.description}}</td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
      </li>
      <li class="tab__item"><a class="tab__button " href="#none">문의하기</a>
        <div class="tab__panel">
          <div class="sub__content">
            <h5 class="sub__heading3">문의하기</h5>
            <ul class="sub__list">
              <li class="sub__item">해당 데이터 및 사용방법에 대해 궁금한 사항이 있는 경우 문의 하시기 바랍니다.</li>
              <li class="sub__item">문의하신 내용은 제공자가 확인 후 답변 회산을 줄 예정입니다.</li>
            </ul>
          </div>
          <div class="sub__content">
            <h5 class="sub__heading3">문의내용</h5>
            <textarea class="textarea" v-model="dataset.question" ref="question" title="문의내용" required></textarea>
            <div class="submit-box">
              <div class="checkbox__wrap">
                <input class="checkbox hidden" id="lock" type="checkbox" v-model="dataset.isSecret" true-value="Y" false-value="N">
                <label class="label__checkbox material-icons" for="lock">비밀글</label>
              </div>
              <a class="button button__secondary" href="#none" @click="onClick('CLICK_DATASET_QNA_QUESTION')">등록</a>
            </div>
          </div>
          <div class="sub__content">
            <h5 class="sub__heading3">문의내용</h5>
            <table class="board-table accordion-board">
              <caption>데이터 명세서 등록 폼 테이블</caption>
              <colgroup>
                <col width="15%">
                <col width="*">
                <col width="15%">
                <col width="20%">
                <col width="15%">
              </colgroup>
              <thead>
                <tr>
                  <th scope="column">상태</th>
                  <th scope="column">내용</th>
                  <th scope="column">문의자</th>
                  <th scope="column">등록일시</th>
                  <th scope="column">답변확인</th>
                </tr>
              </thead>
              <tbody>
                <tr v-if="dataset.datasetInquiryList && dataset.datasetInquiryList.length === 0">
                    <td  class="text--center" colspan="5">문의 내역이 없습니다.</td>
                </tr>
                <template v-for="(inquiry, index) in dataset.datasetInquiryList">
                    <tr class="accordion-board__title">
                      <td scope="row">{{inquiry.statusName}}</td>
                      <td class="text--left">{{inquiry.question}}<i class="icon__lock" v-if="inquiry.isSecret == 'Y'"><span class="hidden">비밀글</span></i></td>
                      <td>{{inquiry.creatorId}}</td>
                      <td>{{inquiry.createdAt|date}}</td>
                      <td>
                          <button class="button button__danger button__small" type="button" @click="inquiry.display = !inquiry.display">수정</button>
                      </td>
                    </tr>
                    <!-- ANSWER -->
                    <tr class="accordion-board__text" v-if="!(inquiry.isSecret == 'Y' && loginUserId != dataset.providerId && inquiry.creatorId != loginUserId)">
                      <td colspan="5" v-if="loginUserId != dataset.providerId && !inquiry.answer">답변 내용이 없습니다.</td>
                      <td colspan="5" v-if="loginUserId != dataset.providerId && inquiry.answer">{{inquiry.answer}}</td>
                      <td class="accordion-board__form" colspan="5" v-if="loginUserId == dataset.providerId">
                        <textarea class="textarea" v-model="inquiry.answer" ref="answer" title="답변" required></textarea>
                        <button class="button button__secondary" type="button" @click="onClick('CLICK_DATASET_QNA_ANSWER', inquiry)">답변등록</button>
                      </td>
                    </tr>
                </template>
              </tbody>
            </table>
          </div>
        </div>
      </li>
    </ul>
  </div>
  
    <div class="sub__bottom">
        <div class="button__group">
        	<c:if test="${alivePlatformServer}">
	            <a class="button button__primary" v-if="dataset.providerId == loginUserId" href="#none" @click="openPopup('OPEN_POPUP_DEVICE_CONNECT')">디바이스 확인</a>
        	</c:if>
            <a class="button button__outline button__outline--primary" v-if="dataset.providerId == loginUserId && (dataset.status == 'registration' || dataset.status == 'releaseReject')" href="#none" @click="releaseRequestDataset">출시요청</a>
            <a class="button button__outline button__outline--primary" v-if="dataset.enabledDatasetPayments && dataset.providerId == loginUserId && (dataset.status == 'prodMode' || dataset.status == 'paidReject')" href="#none" @click="openPopup('OPEN_POPUP_DATASET_REQUEST_PAID')">유료화요청</a>
            <a class="button button__primary" v-if="dataset.providerId == loginUserId && dataset.status != 'prodMode'" href="<c:url value="/mypage/dataset/pageModify.do?nav=${param.nav}&id=${param.id}"/>">수정</a>
            <a class="button button__outline button__outline--primary" href="#none" @click="onClick('CLICK_MOVE_DATASET_LIST', '${param.backUrl}')">목록</a>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetUsagePopup.jsp"/>
<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetSatisfactionPopup.jsp"/>
<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetSampleSearchPopup.jsp"/>
<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetExcelDownloadPopup.jsp"/>
<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetRequestPaidPopup.jsp"/>

<script>
    $(function() {
        vm = new Vue({
            el : '#content',
            mixins: [datasetUsagePopupMixin, datasetSampleSearchPopupMixin, datasetSatisfactionPopupMixin, datasetRequestPaidPopupMixin, datasetExcelDownloadPopupMixin],
            data : function() {
                return {
                    dataset:{
                        id                       : "",     //데이터셋 고유번호 (필수)
                        title                    : "",     //데이터셋 제목 (필수)
                        dsCategoryOid            : "",     //카테고리고유번호
                        provider                 : "",     //제공기관
                        providerSystem           : "",     //제공시스템
                        dataOfferType            : "",     //제공유형(코드)
                        dataOfferTypeName        : "",     //제공유형(라벨)
                        extension                : "",     //확장자(코드)
                        extensionName            : "",     //확장자(라벨)
                        dataType                 : "",     //데이터유형(코드)
                        dataTypeName             : "",     //데이터유형(라벨)
                        ownership                : "",     //소유권
                        updatePeriod             : "",     //갱신주기
                        description              : "",     //데이터셋 설명
                        modelId                  : "",     //데이터모델ID
                        modelName                : "",     //데이터모델명
                        keywords                 : "",     //키워드
                        license                  : "",     //라이선스
                        constraints              : "",     //제약사항
                        providerId               : "",     //데이터소유자
                        status                   : "",     //데이터셋 상태(코드)
                        statusName               : "",     //데이터셋 상태(라벨)
                        datasetInstanceList      : [],     //데이터인스턴스목록
                        datasetOutputList        : [],     //출력정보목록
                        datasetFileList          : [],     //파일목록
                        datasetInquiryList       : [],     //문의목록
                        datasetSearchInfoList    : [],     //조회조건목록
                    },
                    
                    imageFile: {},
                    fileList: [],
                    hasDataInstance: true,
                    introHeaderInfo: [
                        {
                            headerName: "Authorization",
                            headerDesc: "인증토큰",
                            sampleData: "",
                            required  : "필수",
                        },
                        {
                            headerName: "Content-Type",
                            headerDesc: "ContentType",
                            sampleData: "application/json",
                            required  : "필수",
                        },
                    ],
                    introParamInfo: [
                        {
                            paramName: "datatype",
                            paramDesc: "데이터유형(최종값,이력데이터)",
                            sampleData: "lastdata/historydata",
                            etc       : "lastdata/historydata",
                            historyYn : "-",
                            required  : "필수",
                        },
                        {
                            paramName: "options",
                            paramDesc: "옵션",
                            sampleData: "normalValues/keyValueHistory",
                            etc       : "",
                            historyYn : "-",
                            required  : "선택",
                        },
                        {
                            paramName: "timeAt",
                            paramDesc: "날짜검색조건(이전,이후,중간)",
                            sampleData: "before/after/between",
                            etc       : "",
                            historyYn : "O",
                            required  : "필수",
                        },
                        {
                            paramName: "timeproperty",
                            paramDesc: "날짜검색조건(생성일,수정일)",
                            sampleData: "createdAt/modifiedAt",
                            etc       : "",
                            historyYn : "O",
                            required  : "필수",
                        },
                        {
                            paramName: "time",
                            paramDesc: "검색시작일자",
                            sampleData: "2019-10-01T00:20:00Z",
                            etc       : "필수",
                            historyYn : "O",
                            required  : "",
                        },
                        {
                            paramName: "endtime",
                            paramDesc: "검색종료일자",
                            sampleData: "2019-10-01T00:20:00Z",
                            etc       : "필수",
                            historyYn : "O",
                            required  : "",
                        },
                        {
                            paramName: "limit",
                            paramDesc: "제한",
                            sampleData: "default:1000(요청limit이 없는경우 기본값 1000)",
                            etc       : "",
                            historyYn : "O",
                            required  : "선택",
                        },
                        {
                            paramName: "q",
                            paramDesc: "출력명",
                            sampleData: "address==영등포;indexRef>100",
                            etc       : "한개이상조건 입력시 구분자 ';''",
                            historyYn : "O",
                            required  : "선택",
                        },
                    ]
                }
            },
            methods : {
                getDataset : function(nohit) {
                    vm.loadingFlag = true;
                    var request = $.ajax({
                        url : SMC_CTX + "/dataset/get.do",
                        method : "GET",
                        dataType : "json",
                        data : {
                            id          : "${param.id}",
                            nohit       : nohit
                        }
                    });
                    request.done(function(data) {
                        vm.dataset = data;
                        
                        vm.dataset.datasetSatisfactionRatingScoreAverage = Vue.filter('satisfaction')(vm.dataset.datasetSatisfactionRatingScoreAverage);

                        if(vm.dataset.datasetImageFileId != 0){
	                        vm.imageFile.exists = true;
	                        vm.imageFile.src = vm.fileDownload(vm.dataset.id, vm.dataset.datasetImageFileId);
                        }
                        
                        if(vm.dataset.datasetFileList && vm.dataset.datasetFileList.length > 0){
                        	vm.dataset.datasetFileList = vm.dataset.datasetFileList.map(function(file){
                        		var result = {};
                        		if(file.type != 'image'){
    	                    		result = {
    	                    			oid: file.id,
    	                    			name: file.physicalName,
    	                    			size: file.size,
    	                    		}
                        		}else{
                        			result = {};
                        		}
                        		return result;
                        	});
                        }
                        
                        vm.$nextTick(function(){
                            N2M_UI.toggleTab();
                            N2M_UI.accordionBoard();
                        });
                        
                        vm.loadingFlag = false;
                    });
                    request.fail(function(e){
                    	Modal.ERROR();
                        vm.loadingFlag = false;
                    });
                },
                releaseRequestDataset: function(){
                    if(!confirm("데이터셋을 출시요청하시겠습니까?")) return;
                	vm.loadingFlag = true;
                    var request = $.ajax({
                        method : "PATCH",
                        url : SMC_CTX + "/dataset/modify.do",
                        contentType: "application/json",
                        data : JSON.stringify({
                            id          : "${param.id}",
                            status      : "releaseRequest",
                        })
                    });
                    request.done(function(data) {
                    	Modal.OK();
                    	location.href = SMC_CTX + "/dataset/pageDetail.do?id=${param.id}&backUrl=${param.backUrl}";
                        vm.loadingFlag = false;
                    });
                    request.fail(function(e){
                    	Modal.ERROR();
                        vm.loadingFlag = false;
                    });
                },
                onClick: function(code, val){
                    switch(code){
                    case "CLICK_DATASET_WISHLIST":
                        if(vm.dataset.hasDatasetWishlist){
                            if(confirm("관심상품을 취소하시겠습니까?")){
                                var request = $.ajax({
                                    url : SMC_CTX + "/dataset/wishlist/remove.do",
                                    method : "DELETE",
                                    contentType: "application/json",
                                    data : JSON.stringify({
                                        id        : vm.dataset.datasetWishlistId,
                                    	datasetId : vm.dataset.id
                                    })
                                });
                                request.done(function(data) {
                                    Modal.OK();
                                    vm.getDataset("yes");
                                });
                            }
                        }else{
                            if(confirm("관심상품을 등록하시겠습니까?")){
                                var request = $.ajax({
                                    url : SMC_CTX + "/dataset/wishlist/create.do",
                                    method : "POST",
                                    contentType: "application/json",
                                    data : JSON.stringify({
                                        datasetId : "${param.id}"
                                    })
                                });
                                request.done(function(data) {
                                    Modal.OK();
                                    vm.getDataset("yes");
                                });
                            }
                        }
                        break;
                    case "CLICK_MOVE_DATASET_LIST":
                        if(val){
                            location.href = SMC_CTX + val; 
                        }else{
                            location.href = SMC_CTX + "/dataset/pageList.do?nav=${param.nav}";
                        }
                        break;
                    case "CLICK_DATASET_QNA_QUESTION":
                    	Valid.init(vm, {
                    		question: vm.dataset.question,
                    		isSecret: vm.dataset.isSecret
                    	});
                        
                        var constraints = {};
                        constraints = Valid.getConstraints(); 
                        
                        if(Valid.validate(constraints)) return;
                        
                    	if(Modal.REGIST()) return;
                    	var request = $.ajax({
                            url : SMC_CTX + "/dataset/inquiry/create.do",
                            method : "POST",
                            contentType: "application/json",
                            data : JSON.stringify({
                                datasetId : "${param.id}",
                                question  : vm.dataset.question,
                                isSecret  : vm.dataset.isSecret,
                            })
                        });
                        request.done(function(data) {
                            Modal.OK();
                            vm.getDataset("yes");
                        });
                    	break;
                    case "CLICK_DATASET_QNA_ANSWER":
                    	var inquiry = val;
                    	Valid.init(vm, {
                    		answer: inquiry.answer,
                    	});
                        
                        var constraints = {};
                        constraints = Valid.getConstraints(); 
                        
                        if(Valid.validate(constraints)) return;
                        
                    	if(Modal.REGIST()) return;
                    	
                    	var request = $.ajax({
                            url : SMC_CTX + "/dataset/inquiry/modify.do",
                            method : "PUT",
                            contentType: "application/json",
                            data : JSON.stringify({
                                datasetId : "${param.id}",
                                id        : inquiry.id,
                                answer    : inquiry.answer,
                            })
                        });
                        request.done(function(data) {
                            Modal.OK();
                            vm.getDataset("yes");
                        });
                    	break;
                    }
                },
                openPopup: function(code, subCode){
                    switch(code){
                        case "OPEN_POPUP_DATASET_USAGE":
                            vm.openDatasetUsagePopup(vm.dataset, function(success){
                                if(success){
                                    vm.getDataset("yes");
                                }
                            });
                            break;
                        case "OPEN_POPUP_DATASET_SATISFACTION":
                        	if(!vm.dataset.hasDatasetSatisfactionRating){
	                            vm.openDatasetSatisfactionPopup(vm.dataset.id, vm.dataset.title, vm.dataset.providerId, function(success){
	                                if(success){
	                                    vm.getDataset("yes");
	                                }
	                            });
                        	}
                            break;
                        case "OPEN_POPUP_DATASET_SAMPLE_SEARCH":
                            vm.openDatasetSampleSearchPopup(vm.$refs.endPointAddr.value, vm.dataset.id);
                            break;
                        case "OPEN_POPUP_DATASET_REQUEST_PAID":
                        	vm.openDatasetRequestPaidPopup(vm.dataset.id, function(){});
                        	break;
                        case "OPEN_POPUP_DATASET_EXCEL_DOWNLOAD":
                        	vm.openDatasetExcelDownloadPopup(vm.dataset.id, function(){});
                        	break;

                        case "OPEN_POPUP_DEVICE_CONNECT":
                        	
                        	var request = $.ajax({
                                url : SMC_CTX + "/dataset/devices/getList.do",
                                method : "GET",
                                dataType : "json",
                                data : {
                                    datasetId          : "${param.id}"
                                }
                            });
                            request.done(function(data) {
                            	document.charset = "utf-8";	//IE처리용

                            	var form = document.createElement("form");
                				form.acceptCharset = "utf-8";
                				form.setAttribute("accept-charset", "utf-8");
                				form.setAttribute("method", "post");
                				form.setAttribute("target", "device_popup");
                				var accessTokenInput = document.createElement('input');
                				accessTokenInput.type = 'hidden';
                				accessTokenInput.name = "auth_token";
                				accessTokenInput.value = getCookie("chaut");
                				form.appendChild(accessTokenInput);

                           		var params = "callback=<spring:eval expression="@commonGlobals['Globals.dataPublishMs']"/>" + '/dataservice/dataset/'+ vm.dataset.id +'/devices';
                           		
                           		if (data && data.length > 0) {
                           			data.forEach(device => (params = params +"&apiGroupId=" + device.apiGroupId) );
                           		}
                           		var url = "<spring:eval expression="@commonGlobals['Globals.platformServerUrl']"/>" + '/apis/apiMapGroups/listPopup.do?' + params;
                            	
                            	form.setAttribute("action", url);                        	
                				document.body.appendChild(form);
                				 
                				var _width = "<spring:eval expression="@commonGlobals['Globals.platformDevicePopupWidth']"/>";
                				var _height = "<spring:eval expression="@commonGlobals['Globals.platformDevicePopupHeight']"/>";
                				var _left = Math.ceil(( window.screen.width - _width )/2);
                				var _top = Math.ceil(( window.screen.width - _height )/2); 
                			 
                				if( /Chrome/.test(navigator.userAgent) && /Google Inc/.test(navigator.vendor) ){
                					var popup = window.open(url, "device_popup", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top);
                				}else{
                					var popup = window.open(url, "device_popup", 'width='+ _width +', height='+ _height +', left=' + _left + ', top='+ _top +"resizable=yes,toolbar=yes,menubar=yes,location=yes");
                				}
                				form.submit();
                				document.body.removeChild(form);
                            	
                            });
                            request.fail(function(e){
                            	Modal.ERROR();
                            });
                            
                        	function getCookie(cookieName){
	                            var cookieValue=null;
	                            if(document.cookie){
	                                var array=document.cookie.split((escape(cookieName)+'='));
	                                if(array.length >= 2){
	                                    var arraySub=array[1].split(';');
	                                    cookieValue=unescape(arraySub[0]);
	                                }
	                            }
	                            return cookieValue;
	                        }
                            break;
                    }
                }
            }
        })
        vm.getDataset();
    });
</script>
