<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- sub-cover -->
<section class="sub-cover">
    <h3 class="sub-cover__title material-icons">고객 서비스 <small class="sub-cover__title--small">Smart City Data Hub</small></h3>
</section>
<hr>
<!-- sub-nav -->
${lnbHtml}
<!-- sub-nav -->
<hr>
<div class="sub__container">
    <!-- breadcrumb -->
   <%@ include file="/WEB-INF/jsp/tiles/tiles_breadcrumb.jspf" %>
    <!-- //breadcrumb -->
    
    <h3 class="sub__heading1">활용후기 등록<small class="sub__heading1--small">카테고리별 다양한 데이터를 확인하여 보시기 바랍니다.</small></h3>
    <div class="data-write">
      <form id="reviewForm" class="form">
        <fieldset>
          <legend>활용후기 등록 폼</legend>
          <!-- 활용후기 이미지 등록 -->
          <div class="sub__content">
            <table class="form-table">
              <caption>활용후기 이미지 등록 폼 테이블</caption>
              <colgroup>
                <col width="10%">
                <col width="*">
                <col width="10%">
                <col width="*">
              </colgroup>
              <tbody>
                <tr class="representative-image" style="border-bottom-width:0;">
                  <th scope="row"><i class="icon__require">*</i>대표이미지</th>
                  <td colspan="3">
                    <div class="representative-image__area" style="padding-bottom:0;">
                      <!-- [D] 이미지가 있을 때 -->
                      <img class="representative-image__thumb-image" :src="imageFile.src" v-if="imageFile.exists" />
                      <!-- [D] 이미지가 없을 때 -->
                      <i class="representative-image__thumb-image--none material-icons" v-if="!imageFile.exists"></i>
                      <div class="button__group">
                          <label class="button button__secondary" for="imgUpload"><input class="hidden" type="file" name="imageFile" id="imgUpload" ref="imgUpload" @change="selectImageFile"/>사진 변경</label>
                          <button class="button button__danger" type="button" v-if="imageFile.exists" @click="deleteImageFile">삭제</button>
                      </div>
                    </div>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- // 활용후기 이미지 등록 -->

          <!-- 활용사례정보 -->
          <div class="sub__content" style="padding-bottom:25px;">
            <h4 class="sub__heading2">활용사례정보</h4>
            <table class="form-table">
              <caption>활용사례정보 등록 폼 테이블</caption>
              <colgroup>
                <col width="10%">
                <col width="*">
                <col width="10%">
                <col width="*">
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>서비스 제목</th>
                  <td colspan="3"><input class="input" type="text" name="reviewTitle" ref="reviewTitle" v-model="review.reviewTitle" title="서비스 제목" required></td>
                </tr>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>서비스 주소</th>
                  <td colspan="3"><input class="input" type="text" ref="reviewSrc" name="reviewSrc" v-model="review.reviewSrc" title="서비스 주소" required></td>
                </tr>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>서비스 설명</th>
                  <td colspan="3"><textarea class="textarea" ref="reviewDesc" name="reviewDesc" v-model="review.reviewDesc" title="서비스 설명" required></textarea></td>
                </tr>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>데이터 활용목적</th>
                  <td colspan="3"><textarea class="textarea" ref="reviewPurpose" name="reviewPurpose" v-model="review.reviewPurpose" title="데이터 활용목적" required></textarea></td>
                </tr>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>활용 이미지</th>
                  <td colspan="3">
                      <n2m-file-uploader :saved-files="review.reviewFileList" :only-img="true" @select-file="selectFile" @delete-file="deleteFile"/>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- // 활용사례정보 -->

          <!-- 추가정보 -->
          <div class="sub__content" style="padding-bottom:25px;">
            <h4 class="sub__heading2">추가정보</h4>
            <table class="form-table">
              <caption>추가정보 등록 폼 테이블</caption>
              <colgroup>
                <col width="10%">
                <col width="*">
                <col width="10%">
                <col width="*">
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row">개발자</th>
                  <td><input class="input" type="text" ref="reviewDeveloperId" name="reviewDeveloperId" v-model="review.reviewDeveloperId"></td>
                  <th scope="row">서비스개시일</th>
                  <td><label class="label__datepicker label__datepicker--full material-icons"><input ref="reviewOpenDt" id="reviewOpenDt" name="reviewOpenDt" title="서비스개시일" class="input input__datepicker" type="text" v-model="review.reviewOpenDt"/></label></td>
                </tr>
                <tr>
                  <th scope="row">개발유형</th>
                  <td><input class="input" type="text" ref="reviewDevType" name="reviewDevType" v-model="review.reviewDevType"></td>
                  <th scope="row">개발자유형</th>
                  <td><input class="input" type="text" ref="reviewDeveloperType" name="reviewDeveloperType" v-model="review.reviewDeveloperType"></td>
                </tr>
                <tr>
                  <th scope="row">개발자소재지</th>
                  <td><input class="input" type="text" ref="reviewDeveloperLoc" name="reviewDeveloperLoc" v-model="review.reviewDeveloperLoc"></td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- // 추가정보 -->

          <!-- 활용데이터셋 -->
          <div class="sub__content">
            <h5 class="sub__heading2"><i class="icon__require">*</i>활용데이터셋</h5>
            <table class="board-table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="*%">
                <col width="20%">
                <col width="20%">
                <col width="20%">
                <col width="30px">
              </colgroup>
              <thead>
                <tr>
                  <th scope="col">데이터명</th>
                  <th scope="col">카테고리</th>
                  <th scope="col">상태</th>
                  <th scope="col">소유자</th>
                  <th scope="col" class="data-model-row"><button class="button__data-model--add material-icons" type="button" @click="openPopup('OPEN_POPUP_REVIEW_DATASETLIST')"><span class="hidden">데이터 모델 추가하기</span></button></th>
                </tr>
              </thead>
              <tbody>
                <tr v-for="dataset in reviewDatasetList">
                  <th class="text--left" scope="row">{{dataset.title}}</th>
                  <td class="text--center">{{dataset.categoryName}}</td>
                  <td class="text--center">{{dataset.statusName}}</td>
                  <td class="text--left">{{dataset.modelOwnerId}}</td>
                  <td class="data-model-row"><button class="button__data-model--remove material-icons" type="button" @click="onRowClick(dataset)"><span class="hidden">데이터 모델 삭제하기</span></button></td>
                </tr>
              </tbody>
            </table>
          </div>
          <!-- // 활용데이터셋 -->
          <div class="sub__bottom">
            <div class="button__group">
              <a class="button button__primary" href="#none" @click="createReview">등록</a>
              <!-- <a class="button button__primary" href="#none">삭제</a> -->
              <a class="button button__outline--primary" href="<c:url value="/review/pageList.do"/>">취소</a>
            </div>
          </div>
        </fieldset>
      </form>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/review/popup/reviewDatasetListPopup.jsp"/>
<script>
$(function() {
    Vue.use(N2MFileUploader);
    vm = new Vue({
        el : '#content',
        mixins: [reviewDatasetListPopupMixin],
        data : function() {
            return {
                review : {
                    reviewTitle: "",
                    reviewSrc: "",
                    reviewDesc: "",
                    reviewPurpose: "",
                    reviewDeveloperId: "",
                    reviewOpenDt: "",
                    reviewDevType: "",
                    reviewCreDt: "",
                    reviewDeveloperType: "",
                    reviewDeveloperLoc: "",
                    reviewFileList: []
                },
                imageFile: {},
                fileList : [],
                reviewDatasetList: []
            }
        },
        mounted: function() {
        	$("#reviewOpenDt").datetimepicker({
                timeFormat:'HH:mm:ss',
                hour:00,
                minute:00,
                second:00,
                controlType:'select',
                oneLine:true,
                onSelect: function(date){
                    vm.review.reviewOpenDt = date;
                }
            });
        },
        methods : {
            deleteFile: function(index){
            	this.fileList.splice(index, 1);
            },
            selectFile: function(files, append){
                if(append){
                    for(var i=0; i<files.length; i++){
                        vm.fileList.push(files[i]);
                    }
                }else{
                    vm.fileList = files;
                }
            },
            createReview : function() {
                Valid.init(vm, vm.review);
                
                var constraints = {};
                constraints = Valid.getConstraints();
                
            	if(!vm.imageFile.exists){
                    alert("대표이미지는 필수 값입니다.");
                    return;
                }
                
                if(Valid.validate(constraints)) return;
                
                if (vm.fileList.length <= 0){
                    alert("활용 이미지는 필수 값입니다.");
                    return;
                }
                
                if (vm.reviewDatasetList.length <= 0) {
                	alert("활용데이터셋은 필수 값입니다.");
                	return;
                }
                
                if(Modal.REGIST()) return;
                
                var reviewFormData = new FormData(document.getElementById("reviewForm"));
                
                if (vm.imageFile.exists) {
                	reviewFormData.append("files0", vm.imageFile.resource);
                }
                
                for (var i = 0; i < vm.fileList.length; i++){
                	reviewFormData.append("files"+(i+1), vm.fileList[i]);
                }
                
               	var dsOidList = [];
               	
                for (var j = 0; j < vm.reviewDatasetList.length; j++) {
                	var reviewDsOid = vm.reviewDatasetList[j].id;
                	
                	dsOidList.push(reviewDsOid);
                }
                reviewFormData.append("dsOidList", dsOidList);
                
                var request = $.ajax({
                    method : "POST",
                    url : SMC_CTX + "/review/create.do",
                    enctype: "multipart/form-data",
                    processData: false,
                    contentType: false,
                    data : reviewFormData
                });
                
                request.done(function(data) {
                    Modal.OK();
                    location.href = SMC_CTX + "/review/pageList.do";
                });
                
                request.fail(function(data) {
                    console.log("FAIL");
                });
            },
            selectImageFile: function(e){
                var file = e.target.files[0];
                var fileName = file.name;
                var fileSize = file.size;
                var fileExt = fileName.slice(fileName.lastIndexOf(".") + 1).toLowerCase();
                var input = this.$refs.imgUpload;
                input.type = 'text';
                input.type = 'file';
                
                if (!(fileExt == "gif" || fileExt == "jpg" || fileExt == "png" || fileExt == "jpeg")) {
                    alert("이미지 파일 첨부만 가능합니다.");
                    return;
                }
                
                if(fileSize > Math.pow(1024, 2) * 5){
                    alert("대표 이미지는 5MB 이하만 업로드 가능합니다.");
                    return;
                }
                
                vm.imageFile = {};
                vm.imageFile.exists   = true;
                vm.imageFile.resource = file;
                vm.imageFile.src      = URL.createObjectURL(file);
            },
            deleteImageFile: function(e){
                vm.imageFile = {};
                vm.imageFile.exists   = false;
            },
            openPopup: function(code){
                switch(code){
                    case 'OPEN_POPUP_REVIEW_DATASETLIST':
                        vm.openReviewDatasetListPopup(function(selectedDatasetList){
                            vm.reviewDatasetList = selectedDatasetList;
                        });
                        break;
                }
            },
            onRowClick: function(row) {
                for (var i = 0; i < vm.reviewDatasetList.length; i++) {
                    if (row.id === vm.reviewDatasetList[i].id) {
                        vm.reviewDatasetList.splice(i, 1);
                    }
                }
            }
        }
    });
});
</script>
