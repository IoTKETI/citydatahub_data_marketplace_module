<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- modal -->
  <div class="modal" :style= "datasetDetailPopupVisible ? 'display: block' : ''">
    <div class="modal__wrap">
      <div class="modal__content" style="width:1330px;">
        <div class="modal__header">
          <h4 class="modal__title">데이터셋 상세 조회</h4>
          <button class="modal__button--close button__modal--close" type="button" @click="closeDatasetDetailPopup"><span class="hidden">모달 닫기</span></button>
        </div>
        <div class="modal__body">
			<div class="section">
				<div class="section__header">
					<h4 class="section__title">데이터셋 기본 상세 정보</h4>
				</div>
				<div class="section__content">
					<table class="table--row">
						<colgroup>
			                <col width="15%">
			                <col width="35%">
			                <col width="15%">
			                <col width="35%">
						</colgroup> 
						<tbody>
			                <tr>
			                  <th class="text--right" scope="row">데이터셋ID</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.id" readonly/></td>
			                  <th class="text--right" scope="row">데이터셋 이름</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.name" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">갱신 주기</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.updateInterval" readonly/></td>
			                  <th class="text--right" scope="row">분류 체계</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.category" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">제공 기관</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.providerOrganization" readonly/></td>
			                  <th class="text--right" scope="row">제공 시스템</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.providerSystem" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">데이터 유형</th>
			                  <td><input type="text" class="input__text" readonly/></td>
			                  <th class="text--right" scope="row">소유권</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.ownership"  readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">키워드</th>
			                  <td colspan="3"><input type="text" class="input__text" v-model="DatasetOrigin.keywords" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">라이선스</th>
			                  <td colspan="3"><input type="text" class="input__text" v-model="DatasetOrigin.license" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">제공 API 주소</th>
			                  <td colspan="3"><input type="text" class="input__text" v-model="DatasetOrigin.providingApiUri" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">제약 사항</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.restrictions" readonly/></td>
			                  <th class="text--right" scope="row">확장자</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.datasetExtension" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">제공 항목</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.datasetItems" readonly/></td>
			                  <th class="text--right" scope="row">지역 범위</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.targetRegions" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">가공데이터셋 식별자</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.sourceDatasetIds" readonly/></td>
			                  <th class="text--right" scope="row">데이터 저장 위치</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.dataStoreUri" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">품질 검증 여부</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.qualityCheckEnabled" readonly/></td>
			                  <th class="text--right" scope="row">데이터 식별자</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.dataIdentifierType" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">설명</th>
			                  <td colspan="3"><textarea class="textarea" v-model="DatasetOrigin.description" readonly></textarea></td>
			                </tr>
						</tbody>
					</table>
				</div>
			</div>
        </div>
        <div class="modal__body">
			<div class="section">
				<div class="section__header">
					<h4 class="section__title">데이터 모델 정보</h4>
				</div>
				<div class="section__content">
					<table class="table--row">
						<colgroup>
			                <col width="15%">
			                <col width="35%">
			                <col width="15%">
			                <col width="35%">
						</colgroup> 
						<tbody>
			                <tr>
			                  <th class="text--right" scope="row">모델 유형</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.dataModelType" readonly/></td>
			                  <th class="text--right" scope="row">모델 namespace</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.dataModelNamespace" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">모델 버전</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.dataModelVersion" readonly/></td>
			                  <th class="text--right" scope="row">데이터 식별자</th>
			                  <td><input type="text" class="input__text" value="DatasetOrigin.sourceDatasetIds" readonly/></td>
			                </tr>
						</tbody>
					</table>
				</div>
	        </div>
        </div>
        <div class="modal__body">
			<div class="section__header">
				<h4 class="section__title">데이터 모델 정보</h4>
			</div>
			<div class="section">
				<div class="section__content">
					<table class="table--row">
						<colgroup>
			                <col width="15%">
			                <col width="35%">
			                <col width="15%">
			                <col width="35%">
						</colgroup> 
						<tbody>
			                <tr>
			                  <th class="text--right" scope="row">생성자</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.creatorId" readonly/></td>
			                  <th class="text--right" scope="row">생성 시간</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.createdAt" readonly/></td>
			                </tr>
			                <tr>
			                  <th class="text--right" scope="row">수정자</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.modifierId" readonly/></td>
			                  <th class="text--right" scope="row">수정 시간</th>
			                  <td><input type="text" class="input__text" v-model="DatasetOrigin.modifiedAt" readonly/></td>
			                </tr>
						</tbody>
					</table>
				</div>
	        </div>
        </div>
        <div class="modal__footer">
            <button class="button__secondary button__modal--close" href="#none" @click="closeDatasetDetailPopup">닫기</button>
        </div>
      </div>
    </div>
  </div>

<script>
var datasetDetailPopupMixin={
	data: function(){
		return {
			datasetDetailPopupVisible: false,
			DatasetOrigin: {},
		}
	},	
	methods: {
		openDatasetDetailPopup: function(datasetOrigin){
			vm.datasetDetailPopupVisible   = true;
			vm.DatasetOrigin = datasetOrigin;
			$("html").addClass("is-scroll-blocking");
		},
		closeDatasetDetailPopup: function(){
			vm.datasetDetailPopupVisible = false;
			$("html").removeClass("is-scroll-blocking");
		},
	}
}
</script>