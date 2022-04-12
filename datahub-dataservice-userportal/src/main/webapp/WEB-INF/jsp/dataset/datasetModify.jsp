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
<div class="sub__container">
    <!-- breadcrumb -->
    <%@ include file="../tiles/tiles_breadcrumb.jspf" %>
    <!-- //breadcrumb -->
    <h3 class="sub__heading1">기본정보<small class="sub__heading1--small">텍스트 내용이 들어가는 영역</small></h3>
    <div class="page-write">
        <form id="datasetForm" class="form">
			<input type="hidden" name="id" v-model="dataset.id" />
			<input type="hidden" name="status" v-model="dataset.status" />
			<div class="sub__content">
				<fieldset>
				<legend>데이터 명세서 수정 폼</legend>
				<table class="form-table" style="margin-bottom:13px">
					<caption>데이터 명세서 수정 폼 테이블</caption>
					<colgroup>
						<col width="10%">
						<col width="*">
						<col width="10%">
						<col width="*">
					</colgroup>
					<tbody>
						<tr>
							<th scope="row"><i class="icon__require">*</i>제목</th>
							<td colspan="3">
								<input class="input" type="text" name="title" ref="title" v-model="dataset.title" title="제목" required>
							</td>
						</tr>
						<tr class="representative-image">
							<th scope="row">대표이미지</th><!-- <i class="icon__require">*</i> -->
							<td colspan="3">
							<div class="representative-image__area">
								<!-- [D] 이미지가 있을 때 -->
								<img class="representative-image__thumb-image" :src="imageFile.src" v-if="imageFile.exists" />
								<!-- [D] 이미지가 없을 때 -->
								<i class="representative-image__thumb-image--none material-icons" v-else></i>
								<div class="button__group">
									<label class="button button__secondary" for="imgUpload"><input class="hidden" type="file" id="imgUpload" ref="imgUpload" @change="selectImageFile"/>사진 변경</label>
									<button class="button button__danger" type="button" v-if="imageFile.exists" @click="deleteImageFile">삭제</button>
								</div>
							</div>
							</td>
						</tr>
					</tbody>
				</table>
			</div>
			
			<div class="sub__content">
				<h4 class="sub__heading2">대상데이터</h4>
				<div class="filter-form" action="">
					<fieldset>
					<legend>상세 목록 필터 폼</legend>
					<div class="filter-form__group">
						<label class="label" style="width:130px"><i class="icon__require">*</i>데이터모델</label>
						<input type="hidden" name="modelId"      v-model="dataModel.modelId"  title="데이터모델" required/>
						<input type="hidden" name="modelName" v-model="dataModel.modelName"  title="데이터모델" required/>
						
						<input class="input" type="search" v-model="dataModel.modelId" readonly/>
						<button class="button button__secondary" type="button" @click="openPopup('OPEN_POPUP_DATA_MODEL')">찾기</button>
					</div>
					</fieldset>
				</div>
			</div>
			
			<div class="sub__content" v-if="dataset.datasetOriginList && dataset.datasetOriginList.length > 0">
				<h4 class="sub__heading2">데이터 셋</h4>
				<div class="fixed-table__wrap">
					<div class="fixed-table__thead">
						<table class="fixed-table">
							<caption>해당 테이블에 대한 캡션</caption>
							<colgroup>
								<col width="10%">
								<col width="15%">
								<col width="30%">
								<col width="30%">
								<col width="15%">
							</colgroup>
							<thead>
								<tr>
									<th scope="col">구분</th>
									<th scope="col">ID</th>
									<th scope="col">NAME</th>
									<th scope="col">DESCRIPTION</th>
									<th scope="col"></th>
								</tr>
							</thead>
					</table>
				</div>
				<div class="fixed-table__tbody" style="min-height: 30px; max-height: 300px">
					<table class="fixed-table">
						<caption>해당 테이블에 대한 캡션</caption>
						<colgroup>
							<col width="10%">
							<col width="15%">
							<col width="30%">
							<col width="30%">
							<col width="15%">
						</colgroup>
						<tbody>
							<tr v-for="(datasetOrigin, index) in dataset.datasetOriginList">
								<th class="text--center" scope="row">{{index+1}}</th>
								<td class="text--center" scope="row">{{datasetOrigin.id}}</td>
								<td class="text--center" scope="row">{{datasetOrigin.name}}</td>
								<td class="text--center" scope="row">{{datasetOrigin.description}}</td>
								<td class="text--center" scope="row">
									<button class="button button__secondary" type="button" @click="openPopup('OPEN_POPUP_DATASET_DETAIL', datasetOrigin)">상세 보기</button>
								</td>
								<input type="hidden" :name="'datasetOriginList[' + index + '].id'"              v-model="dataset.datasetOriginList[index].key"/>
								<input type="hidden" :name="'datasetOriginList[' + index + '].datasetOriginId'" v-model="dataset.datasetOriginList[index].id"/>
							</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			
			<div class="sub__content" v-if="dataset.datasetInstanceList && dataset.datasetInstanceList.length > 0">
				<h4 class="sub__heading2">DataInstance</h4>
				<div class="fixed-table__wrap">
                   <div class="fixed-table__thead">
                     <table class="fixed-table">
                       <caption>해당 테이블에 대한 캡션</caption>
                       <colgroup>
                         <col width="10%">
                         <col width="45%">
                         <col width="45%">
                       </colgroup>
                       <thead>
                         <tr>
                            <th scope="col">구분</th>
                            <th scope="col">DataInstance명</th>
                            <th scope="col">수정일자</th>
                         </tr>
                       </thead>
                     </table>
                   </div>
                   <div class="fixed-table__tbody" style="min-height: 30px; max-height: 300px">
                     <table class="fixed-table">
                       <caption>해당 테이블에 대한 캡션</caption>
                       <colgroup>
                         <col width="10%">
                         <col width="45%">
                         <col width="45%">
                       </colgroup>
                       <tbody>
                           <tr v-for="(dataInstance, index) in dataset.datasetInstanceList">
                               <input type="hidden" :name="'datasetInstanceList['+index+'].id'" v-model="vm.dataset.datasetInstanceList[index].id"/>
                               <input type="hidden" :name="'datasetInstanceList['+index+'].instanceId'" v-model="vm.dataset.datasetInstanceList[index].instanceId"/>
                               <th class="text--center" scope="row">{{index+1}}</th>
                               <td class="text--center"><a href="#none">{{dataInstance.instanceId}}</a></td>
                               <td>{{dataInstance.createdAt|date}}</td>
                           </tr>
                       </tbody>
                     </table>
                   </div>
                 </div>
			</div>
			
			<div class="sub__content" v-if="dataset.datasetSearchInfoList && dataset.datasetSearchInfoList.length > 0">
	          <h5 class="sub__heading3">데이터 조회조건정보</h5>
	          <div class="fixed-table__wrap">
	             <div class="fixed-table__thead">
	               <table class="fixed-table">
	                 <caption>해당 테이블에 대한 캡션</caption>
	                 <colgroup>
	                   <col width="10%">
	                   <col width="30%">
	                   <col width="20%">
	                   <col width="20%">
	                   <col width="20%">
	                 </colgroup>
	                 <thead>
	                   <tr>
	                     <th scope="col">구분</th>
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
	                   <col width="10%">
	                   <col width="30%">
	                   <col width="20%">
	                   <col width="20%">
	                   <col width="20%">
	                 </colgroup>
	                 <tbody>
	                     <tr v-for=" (searchInfo, index) in dataset.datasetSearchInfoList" style="cursor:auto;">
							<input type="hidden" :name="'datasetSearchInfoList[' + index + '].id'"               v-model="dataset.datasetSearchInfoList[index].id"/>
							<input type="hidden" :name="'datasetSearchInfoList[' + index + '].mainAttribute'"    v-model="dataset.datasetSearchInfoList[index].mainAttribute"/>
							<input type="hidden" :name="'datasetSearchInfoList[' + index + '].subAttribute'"     v-model="dataset.datasetSearchInfoList[index].subAttribute"/>
							<input type="hidden" :name="'datasetSearchInfoList[' + index + '].symbol'"           v-model="dataset.datasetSearchInfoList[index].symbol"/>
							<input type="hidden" :name="'datasetSearchInfoList[' + index + '].value'"            v-model="dataset.datasetSearchInfoList[index].value"/>
							
							<th class="text--center" scope="row">{{index+1}}</th>
							<th class="text--left" scope="row">{{searchInfo.mainAttribute}}</th>
							<th class="text--left" scope="row">{{searchInfo.subAttribute}}</th>
							<th class="text--center" scope="row">{{searchInfo.symbolName}}</th>
							<th class="text--center" scope="row">{{searchInfo.value}}</th>
	                     </tr>
	                 </tbody>
	               </table>
	             </div>
	           </div>
	        </div>
	        
			<div class="sub__content" v-if="dataModel.modelId">
				<h4 class="sub__heading3">데이터셋 정보
					<button class="button button__primary" type="button" @click="initDataset" style="margin-left:10px;">기본 정보 불러오기</button>
				</h4>
				<table class="form-table">
					<caption>데이터 명세서 수정 폼 테이블</caption>
					<colgroup>
					<col width="10%">
					<col width="*">
					<col width="10%">
					<col width="*">
					</colgroup>
					<tbody>
					<tr>
						<th scope="row"><i class="icon__require">*</i>제공유형</th>
						<td>
							<select class="select" name="dataOfferType" ref="dataOfferType" v-model="dataset.dataOfferType" title="제공유형" required>
								<option value="" disabled>선택</option>
								<c:forEach var="code" items="${n2m:getCodeList('CG_ID_OFFER_TYPE')}">
									<option value="${code.codeId}">${code.codeName}</option>
								</c:forEach>
							</select>
						</td>
						<th scope="row"><i class="icon__require">*</i>소유권</th>
						<td><input class="input" type="text" name="ownership" ref="ownership" v-model="dataset.ownership" title="소유권" required></td>
					</tr>
					<tr>
						<th scope="row"><i class="icon__require">*</i>제공기관</th>
						<td>
							<input class="input" type="text" name="provider" ref="provider" v-model="dataset.provider" title="제공기관" required>
						</td>
						<th scope="row"><i class="icon__require">*</i>제공시스템</th>
						<td>
							<input class="input" type="text" name="providerSystem" ref="providerSystem" v-model="dataset.providerSystem" title="제공시스템" required>
						</td>
					</tr>
					<tr>
						<th scope="row"><i class="icon__require">*</i>데이터유형</th>
						<td>
							<select class="select" name="dataType" ref="dataType" v-model="dataset.dataType" title="데이터유형" required>
								<option value="" disabled>선택</option>
								<c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATA_TYPE')}">
									<option value="${code.codeId}">${code.codeName}</option>
								</c:forEach>
							</select>
						</td>
						<th scope="row">확장자</th>
						<td>
							<select class="select" name="extension" ref="extension" v-model="dataset.extension" title="확장자" :disabled="dataset.dataOfferType!='file'">
								<option value="" disabled>선택</option>
								<c:forEach var="code" items="${n2m:getCodeList('CG_ID_EXT_TYPE')}">
									<option value="${code.codeId}">${code.codeName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row"><i class="icon__require">*</i>갱신주기</th>
						<td><input class="input" type="text" name="updatePeriod" ref="updatePeriod" v-model="dataset.updatePeriod" title="갱신주기" required></td>
						<th></th>
						<td></td>
					</tr>
					<tr>
						<th scope="row">키워드</th>
						<td colspan="3">
							<input type="hidden" name="keywords" ref="keywords" v-model="dataset.keywords">
							<div class="keyword">
	                          <ul class="keyword__list">
	                            <li class="keyword__input-wrap"><input class="keyword__input" type="text" ></li>
	                          </ul>
	                        </div>
						</td>
					</tr>
					<tr>
						<th scope="row">라이선스</th>
						<td colspan="3">
							<select class="select" name="license" ref="license" v-model="dataset.license" title="라이선스" required>
								<option value="" disabled>선택</option>
								<c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_LICENSE_TYPE')}">
									<option value="${code.codeId}">${code.codeName}</option>
								</c:forEach>
							</select>
						</td>
					</tr>
					<tr>
						<th scope="row">제약 사항</th>
						<td colspan="3">
							<textarea class="textarea" name="constraints" ref="constraints" v-model="dataset.constraints" title="제약 사항"></textarea>
						</td>
					</tr>
					<tr>
						<th scope="row">설명</th>
						<td colspan="3">
							<textarea class="textarea" name="description" ref="description" v-model="dataset.description" title="설명"></textarea>
						</td>
					</tr>
					</tbody>
				</table>
				</fieldset>
			</div>
			
			<div class="sub__content">
				<h4 class="sub__heading3">추가 정보
				</h4>
				<table class="form-table">
					<caption>데이터 명세서 수정 폼 테이블</caption>
					<colgroup>
					<col width="10%">
					<col width="*">
					<col width="10%">
					<col width="*">
					</colgroup>
					<tbody>
					<tr>
						<th scope="row"><i class="icon__require">*</i>상태</th>
						<td>
							<input class="input" type="text" v-model="dataset.statusName" disabled>
						</td>
						<th scope="row"><i class="icon__require">*</i>카테고리</th>
						<td>
							<input type="hidden" name="categoryId" ref="categoryId" v-model="dataset.categoryId" title="카테고리" required/>
							<input class="input with-button-1"  type="text" v-model="dataset.categoryName" disabled><button class="button button__secondary" type="button" @click="openPopup('OPEN_POPUP_CATEGORY')">선택</button>
						</td>
					</tr>
					<tr>
						<th scope="row">첨부파일</th>
						<td colspan="3">
							<n2m-file-uploader :saved-files="dataset.datasetFileList" @select-file="selectFile" @delete-file="deleteFile"/>
						</td>
					</tr>
					</tbody>
				</table>
				</fieldset>
			</div>
			
			<div class="sub__content sub__content--output" v-if="dataset.datasetOutputList && dataset.datasetOutputList.length > 0">
				<h4 class="sub__heading3">출력정보</h4>
				<table class="board-table">
					<caption>해당 테이블에 대한 캡션</caption>
					<colgroup>
					<col width="10%">
					<col width="20%">
					<col width="20%">
					<col width="*">
					</colgroup>
					<thead>
					<tr>
						<th scope="col">공개여부</th>
						<th scope="col">원컬럼명</th>
						<th scope="col">별칭컬럼명</th>
						<th scope="col">설명</th>
					</tr>
					</thead>
					<tbody>
						<tr v-for="(output, index) in dataset.datasetOutputList">
							<input type="hidden" :name="'datasetOutputList['+index+'].id'"             v-model="dataset.datasetOutputList[index].id"/>
							<input type="hidden" :name="'datasetOutputList['+index+'].open'"           v-model="dataset.datasetOutputList[index].open"/>
							<input type="hidden" :name="'datasetOutputList['+index+'].realColumnName'" v-model="dataset.datasetOutputList[index].realColumnName"/>
							
							<th class="text--center" scope="row">
								<input type="checkbox" :id="'checkbox1-'+index" class="checkbox hidden" v-model="dataset.datasetOutputList[index].open"/>
								<label :for="'checkbox1-'+index" class="label__checkbox material-icons"></label>
							</th>
							<td>
								{{output.realColumnName}}
							</td>
							<td><input class="input" type="text" :name="'datasetOutputList['+index+'].aliasColumnName'" v-model="dataset.datasetOutputList[index].aliasColumnName"/></td>
							<td><input class="input" type="text" :name="'datasetOutputList['+index+'].description'"     v-model="dataset.datasetOutputList[index].description"/></td>
						</tr>
					</tbody>
				</table>
			</div>
			
		</form>
        <div class="sub__bottom">
            <div class="button__group">
                <a class="button button__primary" href="#none" @click="modifyDataset">저장</a>
                <a class="button button__outline button__outline--primary" href="<c:url value="/dataset/pageList.do"/>">목록</a>
            </div>
        </div>
    </div>
</div>
<jsp:include page="/WEB-INF/jsp/dataset/popup/dataModelSearchPopup.jsp"/>
<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetDetailPopup.jsp"/>
<jsp:include page="/WEB-INF/jsp/dataset/popup/categorySearchPopup.jsp"/>


<!-- //main -->
<script>
$(function() {
    Vue.use(N2MFileUploader);
    vm = new Vue({
        el : '#content',
        mixins: [dataModelSearchPopupMixin, categorySearchPopupMixin, datasetDetailPopupMixin],
        data : function() {
            return {
            	dataset:{
					id                           : "",     //데이터셋 고유번호 (필수)
					title                        : "",     //데이터셋 제목 (필수)
					categoryId                   : "",     //카테고리고유번호
					categoryName                 : "",     //카테고리고유번호
					provider                     : "",     //제공기관
					providerSystem               : "",     //제공시스템
					dataOfferType                : "",     //제공유형(코드)
					dataOfferTypeName            : "",     //제공유형(라벨)
					extension                    : "",     //확장자(코드)
					extensionName                : "",     //확장자(라벨)
					dataType                     : "",     //데이터유형(코드)
					dataTypeName                 : "",     //데이터유형(라벨)
					ownership                    : "",     //소유권
					updatePeriod                 : "",     //갱신주기
					description                  : "",     //데이터셋 설명
					modelId                      : "",     //데이터모델ID
					modelName                    : "",     //데이터모델명
					keywords                     : "",     //키워드
					license                      : "",     //라이선스
					constraints                  : "",     //제약사항
					modelOwnerId                 : "",     //데이터소유자
					status                       : "",     //데이터셋 상태(코드)
					statusName                   : "",     //데이터셋 상태(라벨)
					datasetOriginList            : [],
					datasetOutputList            : [],
					datasetInstanceList          : [],
					datasetSearchInfoList        : [],
					datasetFileList              : [],
				},
				dataModel                    : {},
				imageFile: {},
				fileList: [],
                deleteFileOids      : [],
            }
        },
        methods : {
            getDataset : function() {
            	vm.loadingFlag = true;
            	
                var request = $.ajax({
                    url : SMC_CTX + "/dataset/get.do",
                    method : "GET",
                    dataType : "json",
                    data : {
                        id : "${param.id}"
                    }
                });
                request.done(function(data) {
                    vm.dataset = data;
                    
                    vm.dataModel.modelId      = data.modelId;
                    vm.dataModel.modelName = data.modelName;

                    if(vm.dataset.datasetImageFileId != 0){
                        vm.imageFile.exists = true;
                        vm.imageFile.src = vm.fileDownload(vm.dataset.id, vm.dataset.datasetImageFileId);
                    }
                    
                    if(vm.dataset.datasetFileList && vm.dataset.datasetFileList.length > 0){
                    	vm.dataset.datasetFileList = vm.dataset.datasetFileList.map(function(file){
                    		var result = {};
                    		if(file.dsFileFlag != 'file'){
	                    		result = {
	                    			id   : file.id,
	                    			name : file.physicalName,
	                    			size : file.size,
	                    		}
                    		}else{
                    			result = {};
                    		}
                    		return result;
                    	});
                    }
                    
                    vm.dataset.categoryId = vm.dataset.categoryId == 0 ? "" : vm.dataset.categoryId;
                    
                    function createKeyword(keyword){
                        if(!vm.dataset.keywords){
                            vm.dataset.keywords += keyword;
                        }else{
                            vm.dataset.keywords += ","+keyword;
                        }
                    }
                    function deleteKeyword(deleteIndex){
                        var keywordList = vm.dataset.keywords.split(",");
                        keywordList.splice(deleteIndex, 1);
                        vm.dataset.keywords = keywordList.join(",");
                    }
                    N2M_UI.keyword(createKeyword, deleteKeyword, vm.dataset.keywords ? vm.dataset.keywords.split(",") : []);
                    
                    vm.loadingFlag = false;
                });
            },
            modifyDataset: function(){
				Valid.init(vm, vm.dataset);
				
				var constraints = {};
				constraints = Valid.getConstraints(); 
				
				if(Valid.validate(constraints)) return;
				if(Modal.MODIFY()) return;
				
				var formData = new FormData(document.getElementById('datasetForm'));
				
				//대표이미지파일
				if(vm.imageFile.exists && vm.imageFile.resource){
					formData.append("files0", vm.imageFile.resource);
				}
				
				//일반첨부파일
				for(var i=0; i<vm.fileList.length; i++){
					formData.append("files"+(i+1), vm.fileList[i]);
				}
				
				//삭제할파일
				for(var i=0; i<vm.deleteFileOids.length; i++){
					formData.append("deleteFileOids["+i+"]", vm.deleteFileOids[i]);
				}
				
				var request = $.ajax({
					method: 'POST',
					url : SMC_CTX + "/dataset/modify.do",
					processData: false,
					contentType: false,
					data: formData
				});
				request.done(function(data){
					Modal.OK();
					location.href = SMC_CTX + "/mypage/dataset/pageDetail.do?id=${param.id}&nav=${param.nav}";
				});
			},
			openPopup: function(code, data){
				switch(code){
					case 'OPEN_POPUP_DATASET_DETAIL':
						var datasetOrigin = data;
						vm.openDatasetDetailPopup(datasetOrigin);
						break;
					case 'OPEN_POPUP_DATA_MODEL':
						vm.openDataModelSearchPopup(function(selectedData){
							vm.dataset.searchType            = selectedData.searchType;
							vm.dataModel                     = selectedData.dataModel;
							vm.dataset.datasetOutputList     = selectedData.datasetOutputList;
							vm.dataset.datasetOriginList     = selectedData.datasetOriginList;
							vm.dataset.datasetInstanceList   = selectedData.datasetInstanceList;
							vm.dataset.datasetSearchInfoList = selectedData.datasetSearchInfoList;
						});
						break;
					case 'OPEN_POPUP_CATEGORY':
						vm.openCategorySearchPopup(function(selectedCategory) {
						    vm.dataset.categoryId    = selectedCategory.id;
						    vm.dataset.categoryName  = selectedCategory.text;
						})
						break;
                }
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
            deleteFile: function(index, oid){
            	if(oid){
	                vm.dataset.datasetFileList.splice(index, 1);
	                vm.deleteFileOids.push(oid);
            	}else{
            		vm.fileList.splice(index, 1);
            	}
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
                
                if(vm.dataset.datasetImageFileId != 0){
                	vm.deleteFileOids.push(vm.dataset.datasetImageFileId);
                }
                
                vm.imageFile = {};
                vm.imageFile.exists   = true;
                vm.imageFile.resource = file;
                vm.imageFile.src      = URL.createObjectURL(file);
            },
            deleteImageFile: function(imgFileOid){
            	if(imgFileOid && typeof imgFileOid !== "object"){
            		vm.deleteFileOids.push(imgFileOid);
            	}
                vm.imageFile = {};
                vm.imageFile.exists   = false;
            },
	        initDataset: function(){
	        	var datasetOrigin = vm.dataset.datasetOriginList[0];
	        	
	        	vm.dataset.dataOfferType  = "api";								//제공유형
	        	vm.dataset.ownership      = datasetOrigin.ownership;			//소유권
	        	vm.dataset.updatePeriod   = datasetOrigin.updateInterval;		//갱신주기
	        	vm.dataset.description    = datasetOrigin.description;			//데이터셋 설명
	        	vm.dataset.provider       = datasetOrigin.providerOrganization;	//제공기관
	        	vm.dataset.providerSystem = datasetOrigin.providerSystem;		//제공시스템
	        	vm.dataset.constraints    = datasetOrigin.restrictions;			//제약사항
	        	//키워드
	        	//라이센스
	        	//확장자
	        	//데이터유형
	        }
        }
    });
    
    vm.getDataset();
});
</script>