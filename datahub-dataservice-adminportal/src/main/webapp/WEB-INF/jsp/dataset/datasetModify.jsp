<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content">
	<form id="datasetForm">
		<input type="hidden" name="id" v-model="dataset.id" />
		<input type="hidden" name="status" v-model="dataset.status" />
		<h3 class="content__title">데이터셋 등록</h3>
		<div class="section">
			<div class="section__header">
				<h4 class="section__title">기본 정보</h4>
			</div>
			<div class="section__content">
				<fieldset>
					<legend>필드셋 제목</legend>
					<table class="table--row">
						<caption>테이블 제목</caption>
						<colgroup>
							<col style="width:150px">
							<col style="width:auto">
							<col style="width:150px">
							<col style="width:auto">
						</colgroup>
						<tbody>
							<tr>
								<th class="icon__require">제목</th>
								<td colspan="3">
									<input class="input__text" type="text" name="title" ref="title" v-model="dataset.title" title="제목" required>
								</td>
							</tr>
							<tr>
								<th class="icon__require">
									대표이미지
									<label class="button__primary" for="imgUpload"><input class="hidden" type="file" id="imgUpload" ref="imgUpload" @change="selectImageFile"/>사진 변경</label>
									<button class="button__secondary" type="button" v-if="imageFile.exists" @click="deleteImageFile">삭제</button>
									<!-- <button class="button__primary" type="button">사진변경</button><button class="button__secondary" type="button">삭제</button> -->
								</th>
								<td colspan="3">
									<div class="representative-image__area">
										<!-- [D] 이미지가 있을 때 -->
										<img class="representative-image__thumb-image" :src="imageFile.src" v-if="imageFile.exists">
										<!-- <img class="representative-image__thumb-image" src="../img/dummy/thumb_empty_150x150.jpg"> -->
										<!-- [D] 이미지가 없을 때 -->
										<i class="representative-image__thumb-image--none material-icons" v-else></i>
									</div>
								</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</div>
		</div>
		
		<div class="section">
			<div class="section__header">
				<h4 class="section__title">제공자 정보</h4>
			</div>
			<div class="section__content">
				<fieldset>
					<legend>필드셋 제목</legend>
					<table class="table--row">
						<caption>테이블 제목</caption>
						<colgroup>
							<col style="width:150px">
							<col style="width:auto">
							<col style="width:150px">
							<col style="width:auto">
						</colgroup>
						<tbody>
							<tr>
								<th class="icon__require">제공자</th>
								<td>
									<input type="hidden" name="providerId" ref="providerId" v-model="dataset.providerId" title="제공자" required/>
									<input class="input__text w-with-68" type="text" v-model="dataset.providerId" disabled>
									<button class="button__outline w-68" type="button" @click="openPopup('OPEN_POPUP_DATEMODEL_OWNER')" ref="providerIdBtn">선택</button>
								</td>
								<th></th>
								<td></td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</div>
		</div>
	
		<div class="section">
			<div class="section__header">
				<h4 class="section__title">대상데이터</h4>
			</div>
			<div class="section__content">
					<fieldset>
						<legend>대상데이터 검색 폼</legend>
						<table class="table--row">
							<caption>데이터모델 검색 테이블</caption>
							<colgroup>
								<col style="width:150px">
								<col style="width:auto">
								<col style="width:150px">
								<col style="width:auto">
							</colgroup>
							<tbody>
								<tr>
									<th class="icon__require">데이터모델</th>
									<td>
										<input type="hidden" name="modelId"      v-model="dataModel.modelId"  title="데이터모델" required/>
										<input type="hidden" name="modelName" v-model="dataModel.modelName"  title="데이터모델" required/>
										
										<input class="input__text w-with-68" type="text" v-model="dataModel.modelId" readonly>
										<button class="button__outline w-68" type="button" @click="openPopup('OPEN_POPUP_DATA_MODEL')">선택</button>
									</td>
									<th></th>
									<td></td>
								</tr>
							</tbody>
						</table>
					</fieldset>
			</div>
		</div>
		
		<div class="section" v-if="dataset.datasetOriginList && dataset.datasetOriginList.length > 0">
			<div class="section__header">
				<h4 class="section__title">데이터 셋</h4>
			</div>
			<div class="section__content">
				<table class="table--column">
					<caption>테이블 제목</caption>
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
	
		<div class="section" v-if="dataset.datasetInstanceList && dataset.datasetInstanceList.length > 0">
			<div class="section__header">
				<h4 class="section__title">DataInstance</h4>
			</div>
			<div class="section__content">
				<table class="table--column">
					<caption>테이블 제목</caption>
					<colgroup>
						<col style="width:60px">
						<col style="width:auto">
						<col style="width:auto">
					</colgroup>
					<thead>
						<tr>
							<th>구분</th>
							<th>DataInstance명</th>
							<th>등록일자</th>
						</tr>
					</thead>
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
	
		<div class="section" v-if="dataset.datasetSearchInfoList && dataset.datasetSearchInfoList.length > 0">
			<div class="section__header">
				<h4 class="section__title">데이터 조회조건 정보</h4>
			</div>
			<div class="section__content">
				<table class="table--column">
					<caption>테이블 제목</caption>
					<colgroup>
						<col style="width:20%">
						<col style="width:20%">
						<col style="width:20%">
						<col style="width:20%">
						<col style="width:20%">
					</colgroup>
					<thead>
						<tr>
							<th>구분</th>
							<th>컬럼명</th>
							<th>서브컬럼명</th>
							<th>비교</th>
							<th>값</th>
						</tr>
					</thead>
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
		
		<div class="section" v-if="dataModel.modelId">
			<div class="section__header">
				<h4 class="section__title">
					데이터셋 정보
					<button class="button button__primary" type="button" @click="initDataset">기본 정보 불러오기</button>
				</h4>
				
			</div>
			<div class="section__content">
				<fieldset>
					<legend>필드셋 제목</legend>
					<table class="table--row">
						<caption>테이블 제목</caption>
						<colgroup>
							<col style="width:150px">
							<col style="width:auto">
							<col style="width:150px">
							<col style="width:auto">
						</colgroup>
						<tbody>
							<tr>
								<th class="icon__require">제공유형</th>
								<td>
									<select class="select select--full" name="dataOfferType" ref="dataOfferType" v-model="dataset.dataOfferType" title="제공유형" required>
										<option value="" disabled>선택</option>
										<c:forEach var="code" items="${n2m:getCodeList('CG_ID_OFFER_TYPE')}">
												<option value="${code.codeId}">${code.codeName}</option>
										</c:forEach>
									</select>
								</td>
								<th class="icon__require">소유권</th>
								<td>
									<input class="input__text" type="text" name="ownership" ref="ownership" v-model="dataset.ownership" title="소유권" required>
								</td>
							</tr>
							<tr>
								<th class="icon__require">제공기관</th>
								<td>
									<input class="input__text" type="text" name="provider" ref="provider" v-model="dataset.provider" title="제공기관" required>
								</td>
								<th class="icon__require">제공시스템</th>
								<td>
									<input class="input__text" type="text" name="providerSystem" ref="providerSystem" v-model="dataset.providerSystem" title="제공시스템" required>
								</td>
							</tr>
							<tr>
								<th class="icon__require">데이터유형</th>
								<td>
									<select class="select select--full" name="dataType" ref="dataType" v-model="dataset.dataType" title="데이터유형" required>
										<option value="" disabled>선택</option>
										<c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATA_TYPE')}">
												<option value="${code.codeId}">${code.codeName}</option>
										</c:forEach>
									</select>
								</td>
								<th>확장자</th>
								<td>
									<select class="select select--full" name="extension" ref="extension" v-model="dataset.extension" title="확장자" :disabled="dataset.dataOfferType!='file'">
										<option value="" disabled>선택</option>
										<c:forEach var="code" items="${n2m:getCodeList('CG_ID_EXT_TYPE')}">
												<option value="${code.codeId}">${code.codeName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
							<tr>
								<th class="icon__require">갱신주기</th>
								<td>
									<input class="input__text" type="text" name="updatePeriod" ref="updatePeriod" v-model="dataset.updatePeriod" title="갱신주기" required>
								</td>
								<th></th>
								<td></td>
							</tr>
							<tr>
								<th>키워드</th>
								<td colspan="3">
									<input class="input__text hidden" type="text" name="keywords" ref="keywords" v-model="dataset.keywords">
									<div class="keyword">
										<ul class="keyword__list">
											<li class="keyword__input-wrap"><input class="keyword__input" type="text" ></li>
										</ul>
									</div>
								</td>
							</tr>
							<tr>
								<th>라이선스</th>
								<td colspan="3"><input class="input__text" type="text" name="license" ref="license" v-model="dataset.license" title="라이선스"></td>
							</tr>
							<tr>
								<th class="va-t">제약 사항</th>
								<td colspan="3"><textarea class="textarea" name="constraints" ref="constraints" v-model="dataset.constraints" title="제약 사항"></textarea></td>
							</tr>
							<tr>
								<th class="va-t">설명</th>
								<td colspan="3"><textarea class="textarea" name="description" ref="description" v-model="dataset.description" title="설명"></textarea></td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</div>
		</div>
		
		<div class="section">
			<div class="section__header">
				<h4 class="section__title">추가 정보</h4>
			</div>
			<div class="section__content">
				<fieldset>
					<legend>필드셋 제목</legend>
					<table class="table--row">
						<caption>테이블 제목</caption>
						<colgroup>
							<col style="width:150px">
							<col style="width:auto">
							<col style="width:150px">
							<col style="width:auto">
						</colgroup>
						<tbody>
							<tr>
								<th class="icon__require">상태</th>
								<td>
									<input class="input__text" type="text" v-model="dataset.statusName" disabled>
								</td>
								<th class="icon__require">카테고리</th>
								<td>
									<input type="hidden" name="categoryId" ref="categoryId" v-model="dataset.categoryId" title="카테고리" required/>
									<input class="input__text w-with-68" type="text" v-model="dataset.categoryName" disabled><button class="button__outline w-68" type="button" @click="openPopup('OPEN_POPUP_CATEGORY')">선택</button>
								</td>
							</tr>
							<tr>
								<th class="va-t">첨부파일</th>
								<td class="file__group" colspan="3">
									<n2m-file-uploader :saved-files="dataset.datasetFileList" @select-file="selectFile" @delete-file="deleteFile"/>
								</td>
							</tr>
						</tbody>
					</table>
				</fieldset>
			</div>
		</div>
	
		<div class="section" v-if="dataset.datasetOutputList && dataset.datasetOutputList.length > 0">
			<div class="section__header">
				<h4 class="section__title">출력정보</h4>
			</div>
			<div class="section__content">
				<table class="table--column">
					<caption>테이블 제목</caption>
					<colgroup>
						<col style="width:60px">
						<col style="width:auto">
						<col style="width:auto">
						<col style="width:auto">
					</colgroup>
					<thead>
						<tr>
							<th>공개여부</th>
							<th>원컬럼명</th>
							<th>별칭컬럼명</th>
							<th>설명</th>
						</tr>
					</thead>
					<tbody>
						<tr v-for="(output, index) in dataset.datasetOutputList">
							<input type="hidden" :name="'datasetOutputList['+index+'].id'"             v-model="dataset.datasetOutputList[index].id"/>
							<input type="hidden" :name="'datasetOutputList['+index+'].open'"           v-model="dataset.datasetOutputList[index].open"/>
							<input type="hidden" :name="'datasetOutputList['+index+'].realColumnName'" v-model="dataset.datasetOutputList[index].realColumnName"/>
							
							<th class="text--center" scope="row">
								<input type="checkbox" :id="'checkbox1-'+index" class="input__checkbox" v-model="dataset.datasetOutputList[index].open"/>
								<label :for="'checkbox1-'+index" class="label__checkbox"></label>
							</th>
							<td>
								{{output.realColumnName}}
							</td>
							<td><input class="input__text" type="text" :name="'datasetOutputList['+index+'].aliasColumnName'" v-model="dataset.datasetOutputList[index].aliasColumnName"/></td>
							<td><input class="input__text" type="text" :name="'datasetOutputList['+index+'].description'" v-model="dataset.datasetOutputList[index].description"/></td>
						</tr>
					</tbody>
				</table>
			</div>
		</div>
	</form>
	<div class="button__group">
		<button class="button__primary" type="button" @click="modifyDataset">저장</button>
		<a class="button button__secondary" href="<c:url value="/dataset/pageList.do"/>">목록</a>
	</div>
	
	<jsp:include page="/WEB-INF/jsp/dataset/popup/dataModelSearchPopup.jsp"/>
	<jsp:include page="/WEB-INF/jsp/dataset/popup/datasetDetailPopup.jsp"/>
	<jsp:include page="/WEB-INF/jsp/dataset/popup/categorySearchPopup.jsp"/>
	<jsp:include page="/WEB-INF/jsp/dataset/popup/dataModelOwnerSearchPopup.jsp"/>
</div>


<!-- //main -->
<script>
$(function() {
	Vue.use(N2MFileUploader);
	vm = new Vue({
		el : '#content',
		mixins: [dataModelSearchPopupMixin, categorySearchPopupMixin, dataModelOwnerSearchPopupMixin, datasetDetailPopupMixin],
		data : function() {
			return {
				dataset:{
					id						: "",  //데이터셋 고유번호 (필수)
					title					: "",  //데이터셋 제목 (필수)
					categoryId				: "",  //카테고리고유번호
					categoryName			: "",  //카테고리고유번호
					provider				: "",  //제공기관
					providerSystem			: "",  //제공시스템
					dataOfferType			: "",  //제공유형(코드)
					dataOfferTypeName		: "",  //제공유형(라벨)
					extension				: "",  //확장자(코드)
					extensionName			: "",  //확장자(라벨)
					dataType				: "",  //데이터유형(코드)
					dataTypeName			: "",  //데이터유형(라벨)
					ownership				: "",  //소유권
					providerId				: "",  //데이터소유자
					updatePeriod			: "",  //갱신주기
					description				: "",  //데이터셋 설명
					modelId					: "",  //데이터모델ID
					modelName				: "",  //데이터모델명
					keyword					: "",  //키워드
					license					: "",  //라이선스
					constraints				: "",  //제약사항
					status					: "",  //데이터셋 상태(코드)
					statusName				: "",  //데이터셋 상태(라벨)
					datasetOriginList		: [],
					datasetOutputList		: [],
					datasetInstanceList		: [],
					datasetSearchInfoList	: [],
					datasetFileList			: [],
				},
				dataModel : {},
				imageFile: {},
				fileList: [],
                deleteFileOids      : [],
			}
		},
		mounted : function(){
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
			N2M_UI.keyword(createKeyword, deleteKeyword);
		},
		methods : {
			getDataset : function() {
            	vm.loadingFlag = true;
            	
                var request = $.ajax({
                    url : N2M_CTX + "/dataset/get.do",
                    method : "GET",
                    dataType : "json",
                    data : {
                        id : "${param.id}"
                    }
                });
                request.done(function(data) {
                    vm.dataset = data;
                    
                    vm.dataModel.modelId   = data.modelId;
                    vm.dataModel.modelName = data.modelName;

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
                    url : N2M_CTX + "/dataset/modify.do",
                    processData: false,
                    contentType: false,
                    data: formData
                });
                request.done(function(data){
                    Modal.OK();
                    location.href = N2M_CTX + "/dataset/pageList.do?nav=${param.nav}";
                });
            },
			openPopup: function(code){
				switch(code){
					case 'OPEN_POPUP_DATASET_DETAIL':
						var datasetOrigin = data;
						vm.openDatasetDetailPopup(datasetOrigin);
						break;
					case 'OPEN_POPUP_DATA_MODEL':
						if (!vm.dataset.providerId) {
							alert("제공자를 선택해주세요.");
							vm.$refs.providerIdBtn.focus();
							return;
						}
						vm.openDataModelSearchPopup(function(selectedData){
							vm.dataModel                     = selectedData.dataModel;
							vm.dataset.datasetOutputList     = selectedData.datasetOutputList;
							vm.dataset.datasetOriginList     = selectedData.datasetOriginList;
							vm.dataset.datasetInstanceList   = selectedData.datasetInstanceList;
							vm.dataset.datasetSearchInfoList = selectedData.datasetSearchInfoList;
						}, vm.dataset.providerId);
						break;
					case 'OPEN_POPUP_CATEGORY':
						vm.openCategorySearchPopup(function(selectedCategory) {
								vm.dataset.categoryId = selectedCategory.id;
								vm.dataset.categoryName	= selectedCategory.text;
						});
						break;
					case 'OPEN_POPUP_DATEMODEL_OWNER':
						vm.openDataModelOwnerSearchPopup(function(selectedOwner) {
							vm.dataset.providerId = selectedOwner[0].userId;
						});
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
				
				vm.imageFile = {};
				vm.imageFile.exists	 = true;
				vm.imageFile.resource = file;
				vm.imageFile.src			= URL.createObjectURL(file);
			},
			deleteImageFile: function(e){
				vm.imageFile = {};
				vm.imageFile.exists	 = false;
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
