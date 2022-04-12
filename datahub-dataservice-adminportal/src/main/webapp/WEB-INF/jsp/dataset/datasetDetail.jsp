<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content">
	<h3 class="content__title">데이터</h3>
	<section class="section">
	  <div class="section__content">
	    <div class="data-view">
	      <div class="data-view__header">
	        <div class="data-unit">
	          <div class="data-view__thumb">
	            <img v-if="imageFile.exists" width="165" height="150" class="data__image" :src="imageFile.src">
	            <img v-else width="165" height="150" class="data__image" src="<c:url value="/admin/img/dummy/thumb_empty_150x150.jpg"/>">
	          </div>
	          <dl class="data-unit__caption">
	            <dt class="data-unit__title">{{dataset.title}}</dt>
	            <dd class="data-unit__category">{{dataset.categoryName}}</dd>
	            <dd class="data-unit__info">
	              <span class="data-unit__view material-icons"><span class="hidden">조회수</span>{{dataset.retrievalCount}}</span>
	              <span class="data-unit__like material-icons"><span class="hidden">관심 상품으로 담긴 회수</span>{{dataset.datasetWishlistTotalCount}}</span>
	            </dd>
	          </dl>
	        </div>
	        <div class="data-unit__rating" @click="openPopup('OPEN_POPUP_DATASET_SATISFACTION')">
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
	        <a class="data-unit__button data-unit__button--excel material-icons" type="button" href="<c:url value="/downloadExcelFile.do?&id=${param.id}"/>">엑셀 다운로드</a>
	        </div>
	      </div>
	      <table class="table--column" style="border-top:1px solid #eee;">
	        <caption>테이블 제목</caption>
	        <colgroup>
	          <col style="width:auto">
	          <col style="width:auto">
	          <col style="width:auto">
	          <col style="width:auto">
	          <col style="width:auto">
	        </colgroup>
	        <thead>
	          <tr>
	            <th>제공방식(포맷)</th>
	            <th>데이터유형</th>
	            <th>제공기관</th>
	            <th>제공시스템</th>
	            <th>담당자/연락처</th>
	          </tr>
	        </thead>
	        <tbody>
	          <tr>
	            <td>
	              <span class="badge badge--green" v-if="dataset.dataOfferTypeName">{{dataset.dataOfferTypeName}}</span>
	              <span class="badge badge--blue" v-if="dataset.dataOfferType == 'file' && dataset.extensionName">{{dataset.extensionName}}</span>
	            </td>
	            <td><span class="badge badge--orange" v-if="dataset.dataOfferTypeName">{{dataset.dataTypeName}}</span></td>
	            <td>{{dataset.provider}}</td>
	            <td>{{dataset.providerSystem}}</td>
	            <td>{{dataset.dsModelOwnerNm}} {{dataset.dsModelOwnerPhone}}</td>
	          </tr>
	        </tbody>
	      </table>
	      <table class="table--column" style="border-top:1px solid #eee; border-bottom:1px solid #eee;">
	        <caption>테이블 제목</caption>
	        <colgroup>
	          <col style="width:auto">
	          <col style="width:auto">
	          <col style="width:auto">
	          <col style="width:auto">
	          <col style="width:auto">
	        </colgroup>
	        <thead>
	          <tr>
	            <th>이메일</th>
	            <th>갱신주기</th>
	            <th>이용료</th>
	            <th>등록일</th>
	            <th>최종수정일</th>
	          </tr>
	        </thead>
	        <tbody>
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
	  </div>
	  
	  <div class="section__content">
	    <section class="section">
	      <table class="table--row">
		    <caption>상세정보 테이블</caption>
		    <colgroup>
		      <col style="width:150px">
		      <col style="width:auto">
		    </colgroup>
		    <tbody>
		      <tr>
		        <th class="va-t">내용</th>
		        <td>{{dataset.description}}</td>
		      </tr>
		      <tr>
		        <th class="va-t">키워드</th>
		        <td>{{dataset.keywords}}</td>
		      </tr>
		      <tr>
		        <th class="va-t">라이선스</th>
		        <td>{{dataset.licenseName}}</td>
		      </tr>
		      <tr>
		        <th class="va-t">제약 사항</th>
		        <td>{{dataset.constraints}}</td>
		      </tr>
		    </tbody>
		  </table>
	    </section>
	  </div>
	</section>
	
	<ul class="tab__list">
	  <li class="tab__item is-active"><a class="tab__button" href="#none" :ref="">상세정보</a>
	    <div class="tab__panel">
	      <section class="section">
	        <div class="section__content">
	          <table class="table--row">
	            <caption>상세정보 테이블</caption>
	            <colgroup>
	              <col style="width:150px">
	              <col style="width:auto">
	            </colgroup>
	            <tbody>
	              <tr>
	                <th class="va-t">상세정보</th>
	                <td>
			                  데이터명세서에 대한 API 상세 설명입니다.<br>
			                  일일접속 1000건 으로 제안 되어있습니다.<br>
			                  더 자세한 내용은 사용자 가이드 파일 정보에 첨부되어있는 가이드 문서를 참조해 주시기 바랍니다.
	                </td>
	              </tr>
	              <tr>
	                <th>End Point 주소</th>
	                <td>
	                  <input ref="endPointAddr" class="input__text w-with-68" type="text" :value="'${endpoint}/dataservice/dataset/'+ dataset.id + '/data'" readonly><button class="button__outline w-68" type="button" @click="openPopup('OPEN_POPUP_DATASET_SAMPLE_SEARCH')">샘플보기</button>
	                </td>
	              </tr>
	              <tr class="va-t">
	                <th>첨부파일</th>
	                <td class="file__group">
	                  <ul class="file__list">
	                    <li class="file__item file__item--none" v-if="dataset.datasetFileList.length === 0">첨부파일이 없습니다.</li>
	                    <li class="file__item" v-for="(file, index) in dataset.datasetFileList">
	                      <a class="" :href="fileDownload(dataset.id, file.oid)">{{file.name}}<small class="attach-file__size">{{file.size | size}}</small></a>
	                    </li>
	                  </ul>
	                </td>
	              </tr>
	            </tbody>
	          </table>
	        </div>
	      </section>
	      <section class="section">
	        <div class="section__header">
	          <h4 class="section__title">요청변수(HEADER)</h4>
	        </div>
	        <div class="section__content">
	          <table class="table--column">
	            <caption>테이블 제목</caption>
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
	              <tr v-for="(header, index) in introHeaderInfo">
	                <td class="text--left" scope="row">{{header.headerName}}</td>
	                <td class="text--left" scope="row">{{header.headerDesc}}</td>
	                <td class="text--left" scope="row">{{header.sampleData}}</td>
	                <td class="text--center" scope="row">{{header.required}}</td>
	              </tr>
	            </tbody>
	          </table>
	        </div>
	      </section>
	      <section class="section">
	        <div class="section__header">
	          <h4 class="section__title">요청변수(PARAMETER)</h4>
	        </div>
	        <div class="section__content">
	          <table class="table--column">
	            <caption>테이블 제목</caption>
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
	              <tr v-for="(param, index) in introParamInfo">
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
	      </section>
	      <section class="section">
	        <div class="section__header">
	          <h4 class="section__title">DataInstance 정보</h4>
	        </div>
	        <div class="section__content">
	          <table class="table--column">
	            <caption>테이블 제목</caption>
	            <colgroup>
	              <col width="20%">
	              <col width="80%">
	            </colgroup>
	            <thead>
	              <tr>
	                <th scope="col">No</th>
	                <th scope="col">DataInstance Name</th>
	              </tr>
	            </thead>
	            <tbody>
	              <tr v-if="dataset.datasetInstanceList.length === 0">
	                <td colspan="2">DataInstance 정보가 없습니다.</td>
	              </tr>
	              <tr v-for="(inst, index) in dataset.datasetInstanceList" style="cursor:auto;">
	                <td class="text--center" scope="row">{{index+1}}</td>
	                <td class="text--center" scope="row">{{inst.instanceId}}</td>
	              </tr>
	            </tbody>
	          </table>
	        </div>
	      </section>
	      <section class="section" v-if="dataset.datasetSearchInfoList.length > 0">
	        <div class="section__header">
	          <h4 class="section__title">데이터 조회조건정보</h4>
	        </div>
	        <div class="section__content">
	          <table class="table--column">
	            <caption>테이블 제목</caption>
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
	      </section>
	      <section class="section">
	        <div class="section__header">
	          <h4 class="section__title">출력정보</h4>
	        </div>
	        <div class="section__content">
	          <table class="table--column">
	            <caption>테이블 제목</caption>
	            <colgroup>
	              <col width="20%">
	              <col width="40%">
	              <col width="40%">
	            </colgroup>
	            <thead>
	              <tr>
	                <th class="text--center" scope="col">No</th>
	                <th class="text--left"   scope="col">출력명</th>
	                <th class="text--left"   scope="col">설명</th>
	              </tr>
	            </thead>
	            <tbody>
	              <tr v-if="dataset.datasetOutputList.length === 0">
	                  <td colspan="3">데이터셋 출력 정보가 없습니다.</td>
	              </tr>
	              <tr v-for="(datasetOutput, index) in dataset.datasetOutputList">
	                <td scope="row">{{index+1}}</td>
	                <td class="text--left">{{datasetOutput.aliasColumnName}}</td>
	                <td class="text--left">{{datasetOutput.description}}</td>
	              </tr>
	            </tbody>
	          </table>
	        </div>
	      </section>
	    </div>
	  </li>
	  <li class="tab__item"><a class="tab__button" href="#none">문의하기</a>
	    <div class="tab__panel">
	      <section class="section">
	        <div class="section__content">
	          <table class="table--row">
	            <caption>상세정보 테이블</caption>
	            <colgroup>
	              <col style="width:150px">
	              <col style="width:auto">
	            </colgroup>
	            <tbody>
	              <tr>
	                <th class="va-t">문의하기</th>
	                <td>
			                  해당 데이터 및 사용방법에 대해 궁금한 사항이 있는 경우 문의 하시기 바랍니다.<br>
			                  문의하신 내용은 제공자가 확인 후 답변 회산을 줄 예정입니다.
	                </td>
	              </tr>
	              <tr>
	                <th>문의내용</th>
	                <td>
	                  <textarea class="textarea" v-model="dataset.question" ref="question" title="문의내용" required></textarea>
	                  <div class="submit-box">
	                    <div class="checkbox__wrap">
	                      <!-- <input class="input__checkbox" id="lock" type="checkbox" checked=""><label class="label__checkbox" for="lock">비밀글</label> -->
	                      <input class="input__checkbox" id="lock" type="checkbox" v-model="dataset.isSecret" true-value="Y" false-value="N">
	                      <label class="label__checkbox material-icons" for="lock">비밀글</label>
	                    </div>
	                    <button class="button__outline" type="button" @click="onClick('CLICK_DATASET_QNA_QUESTION')">등록</button>
	                  </div>
	                </td>
	              </tr>
	            </tbody>
	          </table>
	        </div>
	      </section>
	      <section class="section">
	        <div class="section__header">
	          <h4 class="section__title">문의내용</h4>
	        </div>
	        <div class="section__content">
	          <table class="table--column">
	            <caption>데이터 명세서 등록 폼 테이블</caption>
	            <colgroup>
	              <col style="width:15%">
	              <col style="width:auto">
	              <col style="width:15%">
	              <col style="width:20%">
	              <col style="width:10%">
	            </colgroup>
	            <thead>
	              <tr>
	                <th>상태</th>
	                <th>내용</th>
	                <th>문의자</th>
	                <th>등록일시</th>
	                <th>답변확인</th>
	              </tr>
	            </thead>
	            <tbody class="accordion-board">
	              <tr v-if="dataset.datasetInquiryList.length === 0">
	                <td class="text--center" colspan="5">문의 내역이 없습니다.</td>
	              </tr>
	              <template v-for="(inquiry, index) in dataset.datasetInquiryList">
	                <tr class="accordion-board__title">
	                  <td>{{inquiry.statusName}}</td>
	                  <td class="ta-l">{{inquiry.question}}<i class="icon__lock" v-if="inquiry.isSecret == 'Y'"><span class="hidden">비밀글</span></i></td>
	                  <td>{{inquiry.creatorId}}</td>
	                  <td>{{inquiry.createdAt|date}}</td>
	                  <td><button class="button__outline" type="button" @click="inquiry.display = !inquiry.display">수정</button></td>
	                </tr>
	                <tr class="accordion-board__text" v-if="!(inquiry.isSecret == 'Y' && loginUserId != dataset.modelOwnerId && inquiry.creatorId != loginUserId)">
	                  <td colspan="5" v-if="loginUserId != dataset.modelOwnerId && !inquiry.answer">답변 내용이 없습니다.</td>
	                  <td colspan="5" v-if="loginUserId != dataset.modelOwnerId && inquiry.answer">{{inquiry.answer}}</td>
	                  <td class="accordion-board__form" colspan="5" v-if="loginUserId == dataset.modelOwnerId">
	                    <textarea class="textarea" v-model="inquiry.answer" ref="answer" title="답변" required></textarea>
	                    <button class="button__outline" type="button" @click="onClick('CLICK_DATASET_QNA_ANSWER', inquiry)">답변등록</button>
	                  </td>
	                </tr>
	              </template>
	            </tbody>
	          </table>
	        </div>
	      </section>
	    </div>
	  </li>
	</ul>
  
    <div class="button__group">
        <a class="button button__primary" v-if="dataset.status != 'prodMode' && dataset.modelOwnerId == loginUserId" href="<c:url value="/dataset/pageModify.do?id=${param.id}"/>">수정</a>
        <a class="button button__outline button__outline--primary" href="#none" @click="onClick('CLICK_MOVE_DATASET_LIST')">목록</a>
    </div>
	<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetUsagePopup.jsp"/>
	<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetSatisfactionPopup.jsp"/>
	<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetSampleSearchPopup.jsp"/>
</div>

<script>
    $(function() {
        vm = new Vue({
            el : '#content',
            mixins: [datasetUsagePopupMixin, datasetSampleSearchPopupMixin, datasetSatisfactionPopupMixin],
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
                        modelOwnerId             : "",     //데이터소유자
                        status                   : "",     //데이터셋 상태(코드)
                        statusName               : "",     //데이터셋 상태(라벨)
                        datasetInstanceList      : [],     //데이터인스턴스목록
                        datasetOutputList        : [],     //출력정보목록
                        datasetFileList          : [],     //파일목록
                        datasetCollaborationList : [],     //협업자목록
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
                        url : N2M_CTX + "/dataset/get.do",
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
                        console.log(e);
                    });
                },
                onClick: function(code, val){
                    switch(code){
                    case "CLICK_DATASET_WISHLIST":
                        if(vm.dataset.hasDatasetWishlist){
                            if(confirm("관심상품을 취소하시겠습니까?")){
                                var request = $.ajax({
                                    url : N2M_CTX + "/dataset/wishlist/remove.do",
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
                                    url : N2M_CTX + "/dataset/wishlist/create.do",
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
                        if("${param.backUrl}"){
                            location.href = N2M_CTX + "${param.backUrl}"; 
                        }else{
                            location.href = N2M_CTX + "/dataset/pageList.do?nav=${param.nav}";
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
                            url : N2M_CTX + "/dataset/inquiry/create.do",
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
                            url : N2M_CTX + "/dataset/inquiry/modify.do",
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
                openPopup: function(code){
                    switch(code){
                        case "OPEN_POPUP_DATASET_USAGE":
                            if(vm.dataset.hasDatasetUseRequest){
                                if(confirm("이용신청을 취소하시겠습니까?")){
                                    var request = $.ajax({
                                        url : N2M_CTX + "/dataset/use/remove.do",
                                        method : "DELETE",
                                        contentType: "application/json",
                                        data : JSON.stringify({
                                            id        : vm.dataset.datasetUseRequestId,
                                            datasetId : vm.dataset.id,
                                        })
                                    });
                                    request.done(function(data) {
                                        Modal.OK();
                                        vm.getDataset("yes");
                                    });
                                }
                            }else{
                                vm.openDatasetUsagePopup(vm.dataset.id, function(success){
                                    if(success){
                                        vm.getDataset("yes");
                                    }
                                });
                            }
                            break;
                        case "OPEN_POPUP_DATASET_SATISFACTION":
                        	if(!vm.dataset.hasDatasetSatisfactionRating){
	                            vm.openDatasetSatisfactionPopup(vm.dataset.id, function(success){
	                                if(success){
	                                    vm.getDataset("yes");
	                                }
	                            });
                        	}
                            break;
                        case "OPEN_POPUP_DATASET_SAMPLE_SEARCH":
                            vm.openDatasetSampleSearchPopup(vm.$refs.endPointAddr.value, vm.dataset.id);
                            break;
                    }
                },
            }
        })
        vm.getDataset();
    });
</script>
