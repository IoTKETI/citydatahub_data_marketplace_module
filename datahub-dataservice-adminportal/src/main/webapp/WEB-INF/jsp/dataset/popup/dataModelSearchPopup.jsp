<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- modal -->
<div ref="dataModelPopup" class="modal" :style= "dataModelSearchPopupVisible ? 'display: block' : ''">
	<div class="modal__wrap">
		<div class="modal__content" style="width:1330px;">
			<div class="modal__header">
				<h4 class="modal__title">데이터모델 검색 팝업</h4>
				<button class="modal__button--close button__modal--close" type="button" @click="closeDataModelSearchPopup"><span class="hidden">모달 닫기</span></button>
			</div>
			<div class="modal__body" v-show="step === 1">
				<div class="section">
					<div class="section__header">
						<h4 class="section__title">데이터모델 검색</h4>
					</div>
					<div class="section__content">
						<table class="table--row">
							<caption>데이터모델 검색 테이블</caption> 
							<colgroup>
								<col style="width: 150px;"> 
								<col style="width: auto;">
							</colgroup> 
							<tbody>
								<tr>
									<th>검색어</th> 
									<td>
										<select class="select">
											<option value="선택">데이터 모델명</option> 
										</select>
										<input type="text" placeholder="검색어를 입력하세요." class="input__text" v-model="schValue" @keypress.enter="getDataModelList">
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="button__group">
						<button type="button" class="button__primary" @click="getDataModelList">검색</button>
					</div>
				</div>
				
				<div class="section section__banner" style="margin:10px 0;">
					<div class="select-result__banner swiper-container">
						<ul class="select-result__banner-list swiper-wrapper">
							<li class="select-result__banner-item swiper-slide" v-for="(model, index) in dataModelList" @click="onRowClick(model)">
								<p class="select-result__banner-date">
									<strong class="select-result__banner-date--emphasis"></strong>
								</p>
								<a class="select-result__banner-link" href="#none">
									<span class="select-result__banner-text--new"></span>
									<strong class="select-result__banner-text--category">{{model.type}}</strong>
								</a>
							</li>
						</ul>
					</div>
					<button class="select-result__banner-button select-result__banner-button--prev" type="button"><span class="hidden">이전으로</span></button>
					<button class="select-result__banner-button select-result__banner-button--next" type="button"><span class="hidden">다음으로</span></button>
				</div>
				
				<div class="section">
					<div class="section__content">
						<table class="table--row">
							<caption>해당 테이블 캡션</caption>
							<colgroup>
								<col style="width:150px">
								<col style="width:auto">
							</colgroup>
							<tbody>
								<tr>
								  <th>데이터모델명</th>
								  <td><input class="input__text" type="text" v-model="selectedDataModel.id" disabled></td>
								</tr>
								<tr>
								  <th>소유자</th>
								  <td><input class="input__text" type="text" v-model="selectedDataModel.creator" disabled/></td>
								</tr>
								<tr>
									<th class="va-t">컬럼정보</th>
									<td>
										<div class="fixed-table__wrap">
											<div class="fixed-table__thead">
												<table class="table--column fixed-table">
													<caption>테이블 제목</caption>
													<colgroup>
														<col style="width:60px">
														<col style="width:auto">
														<col style="width:auto">
													</colgroup>
													<thead>
														<tr>
															<th>구분</th>
															<th>컬럼명</th>
															<th>컬럼설명</th>
															<th></th>
														</tr>
													</thead>
												</table>
											</div>
											<div class="fixed-table__tbody" style="height:205px;">
												<table class="table--column fixed-table">
													<caption>테이블 제목</caption>
													<colgroup>
														<col style="width:60px">
														<col style="width:auto">
														<col style="width:auto">
													</colgroup>
													<tbody>
														<tr v-if="!selectedDataModel.attributes">
															<td colspan="3">데이터 출력 정보가 없습니다.</td>
														</tr>
														<tr v-for="(attr, index) in selectedDataModel.attributes">
															<th class="text--center" scope="row">{{index+1}}</th>
															<td class="text--center" scope="row">{{attr.name}}</td>
															<td class="text--center" scope="row">{{attr.valueType}}</td>
														</tr>
													</tbody>
												</table>
											</div>
										</div>
									</td>
								</tr>
							</tbody>
						</table>
					</div>
				</div>
			</div>
			<div class="modal__body" v-show="step === 2">
				<section>
					<div class="section__header">
						<h4 class="hidden">데이터모델 검색 상세결과</h4>
					</div>
					<div class="section__content">
						<el-table :data="datasetOriginList" border empty-text="검색  결과가 존재하지 않습니다." @selection-change="selectDatasetOrigin">
							<el-table-column type="selection"></el-table-column>
							<el-table-column prop="id"          label="ID"          align="left"></el-table-column>
							<el-table-column prop="name"        label="NAME"        align="left"></el-table-column>
							<el-table-column prop="category"    label="CATEGORY"    align="left"></el-table-column>
							<el-table-column prop="description" label="DESCRIPTION" align="left"></el-table-column>
						</el-table>
					</div>
				</section>
			</div>
			<div class="modal__body" v-show="step === 3">
				<div class="section">
					<div class="section__content">
						<table class="table--row">
						<colgroup>
							<col style="width: 150px;"> 
							<col style="width: auto;">
						</colgroup> 
						<tbody>
							<tr>
								<th>제공유형</th> 
								<td>
									<select class="select select--full" v-model="dataType">
										<c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_DATA_TYPE')}">
											<option value="${code.codeId}">${code.codeName}</option>
										</c:forEach>
									</select>
								</td>
							</tr>
						</tbody>
						</table>
					</div>
				</div>
				<div class="section" v-if="dataType == 'instance'">
					<div class="section__header">
						<h4 class="hidden">데이터모델 검색 상세결과</h4>
					</div>
					<div class="section__content">
						<el-table :data="datasetInstanceList" border empty-text="검색  결과가 존재하지 않습니다." @selection-change="selectDatasetInstance">
							<el-table-column type="selection"></el-table-column>
							<el-table-column prop="id"          label="DataInstance ID" align="left"></el-table-column>
							<el-table-column prop="createdAt"   label="등록일자"          align="left">
								<template slot-scope="scope">
									{{scope.row.createdAt|date}}
								</template>
							
							</el-table-column>
						</el-table>
					</div>
				</div>
				<section class="modal__select-result modal__select-result--info material-icons" v-if="dataType == 'filter'">
					<div class="box--right">
					    <button class="button__data-model--add material-icons" type="button" @click="addFilter"><span class="hidden">필드 추가하기</span></button>
                        <!-- <button class="table-button table-button__remove material-icons" @click="deleteFilter" type="button"><span class="hidden">삭제</span></button> -->
					</div>
				    <ul class="full-field__list" v-if="datasetSearchInfoList.length > 0">
				       <li class="full-field__item" v-for="(datasetSearchInfo, index) in datasetSearchInfoList">
			               <select class="select" v-model="datasetSearchInfo.mainAttribute" @change="selectFilter(index)">
	                           <option value="" disabled>--선택--</option>
	                           <option :value="key" v-for="(value, key) in datasetSearchInfo.main">{{key}}</option>
	                       </select>
	                       <select class="select" v-model="datasetSearchInfo.subAttribute" :disabled="isEmpty(datasetSearchInfo.sub)" :ref="'subAttribute_' + index">
	                           <option value="" disabled>--선택--</option>
	                           <option :value="key" v-for="(value, key) in datasetSearchInfo.sub">{{key}}</option>
	                       </select>
	                       <select class="select width--80px" v-model="datasetSearchInfo.symbol" :ref="'symbol_' + index">
	                           <option value="" disabled>--선택--</option>
	                           <c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_SEARCH_TYPE')}">
	                              <option value="${code.codeId}|${code.codeName}">${code.codeName}</option>
	                           </c:forEach>
	                       </select>
	                       <input class="input" type="text" v-model="datasetSearchInfo.value"  :ref="'value_' + index"/>
	                       <button class="button__data-model--remove material-icons" type="button" @click="deleteFilter(index)"><span class="hidden">필드 삭제하기</span></button>
				       </li>
				    </ul>
				</section>
			</div>
			<div class="modal__footer">
				<button class="button__secondary"                      @click="prevStep"        v-show="step !== 1">이전</button>
				<button class="button__secondary"                      @click="nextStep"        v-show="step !== 3">다음</button>
				<button class="button__secondary button__modal--close" @click="selectDataModel" v-show="step === 3">선택</button>
			</div>
		</div>
	</div>
</div>


<script>
var dataModelSearchPopupMixin={
	data: function(){
		return {
			step                        : 1,
			dataType					: "empty",
			schValue					: "",
			dataModelList			    : [],
			datasetOriginList		    : [],
			datasetInstanceList		    : [],
			datasetSearchInfoList	    : [],
			selectedDataModel		    : {},
			selectedDatasetOriginList   : [],
			selectedDatasetInstanceList : [],
			datasetInstanceSelected	    : false,
			dataModelSearchPopupVisible : false,
			selectDataModelCallback	    : function(){},
		}
	},
	watch: {
		step: function(newStep, oldStep){
			var flow = newStep-oldStep;
			if(flow < 0){//prev
				//TODO:
			}else{//next
				if(newStep === 2){
			   		if (Object.keys(vm.selectedDataModel).length === 0) {
						alert("데이터모델을 선택해 주세요.");
						vm.step = oldStep;
						return false;
					}
					vm.getDatasetOriginList();
				}else if(newStep === 3){
					if (vm.selectedDatasetOriginList.length === 0) {
						alert("데이터셋은 1개 이상 선택을 해야 합니다.");
						vm.step = oldStep;
						return false;
					}
					vm.getDatasetInstanceList();
				}
			}
		},
	},
	methods: {
		prevStep: function(){
			vm.step--;
		},
		nextStep: function(){
			vm.step++;
		},
		isEmpty: function(obj){
			return Object.keys(obj).length === 0;
		},
		getDataModelList: function(){
			vm.loadingFlag=true;
			var request = $.ajax({
				method: 'GET',
				url: N2M_CTX + "/dataset/model/getList.do",
				data: {
					dsModelNm: vm.schValue
				},
				dataType: 'json'
			});
			request.done(function(data){
				vm.dataModelList = data;
				vm.$nextTick(function(){
					var swiper3 = new Swiper('.select-result__banner', {
					  direction		   : 'horizontal',
					  grabCursor		  : false,
					  initialSlide		: 0,
					  keyboard			: {
						enabled : true,
					  },
					  loop				: false,
					  navigation		  : {
						nextEl : '.select-result__banner-button--next',
						prevEl : '.select-result__banner-button--prev',
					  },
					  roundLengths		: true,
					  slidesPerView	   : 1, 
					  slidesPerGroup	  : 1,
					  spaceBetween		: 0,
					  breakpointsInverse  : true, 
					  breakpoints		 : { 
						640: {
						  slidesPerView: 2, 
						},
						1024: {
						  slidesPerView: 3, 
						},
					  },
					});
				});
			})
			request.done(function(data){
				vm.loadingFlag=false;
			})
		},
		getDatasetOriginList: function(dsModelId){
			vm.loadingFlag=true;
			var request = $.ajax({
				method: 'GET',
				url: N2M_CTX + "/dataset/datasetorigin/getList.do",
				data: {
					modelId : vm.selectedDataModel.id
				},
				dataType: 'json'
			});
			request.done(function(data){
				vm.loadingFlag=false;
				vm.datasetOriginList = data;
			});
			request.fail(function(data){
				vm.loadingFlag=false;
			});
		},
		selectDatasetOrigin: function(val){
			vm.selectedDatasetOriginList = val;
		},
		getDatasetInstanceList: function(dsModelId){
			vm.loadingFlag=true;
			vm.instances = [];
			var request = $.ajax({
				method: 'GET',
				url: N2M_CTX + "/dataset/instance/getList.do",
				data: {
					modelTypeUri	   : vm.selectedDataModel.typeUri
				},
				dataType: 'json'
			});
			request.done(function(data){
				vm.loadingFlag=false;
				vm.datasetInstanceList = data;
			});
			request.fail(function(data){
				vm.loadingFlag=false;
			});
		},
		selectAllDatasetInstance: function(){
			if(vm.selectedDatasetInstanceList.length === vm.datasetInstanceList.length){
				vm.selectedDatasetInstanceList = [];
			}else{
				vm.selectedDatasetInstanceList = [];
				vm.datasetInstanceList.forEach(function(datasetInstance){
					vm.selectedDatasetInstanceList.push(datasetInstance);
				});
			}
		},
		selectDatasetInstance: function(val){
			vm.selectedDatasetInstanceList = val;
			if(vm.selectedDatasetInstanceList.length > 0 && vm.selectedDatasetInstanceList.length === vm.datasetInstanceList.length){
				vm.datasetInstanceSelected = true;
			}else{
				vm.datasetInstanceSelected = false;
			}
		},
		selectDataModel: function(){
			if(!vm.isValidateDataModel()) return;
			
			if($.isFunction(vm.selectDataModelCallback)){
				var selectedDataModel			  = { modelId: vm.selectedDataModel.id, modelName: vm.selectedDataModel.typeUri };
				var selectedDatasetOriginList	  = vm.selectedDatasetOriginList;
				var selectedDatasetOutputList	  = vm.selectedDataModel.attributes.map(function(attribute){ return { realColumnName: attribute.name, aliasColumnName: attribute.name, description: attribute.valueType, open: true }; });
				var selectedDatasetInstanceList	= vm.selectedDatasetInstanceList.map(function(instance){ return { instanceId: instance.id }; });
				var selectedDatasetSearchInfoList  = vm.datasetSearchInfoList.map(function(searchInfo){
					var symbolArr = searchInfo.symbol.split("|");
					searchInfo.symbol = symbolArr[0];
					searchInfo.symbolName = symbolArr[1];
					return searchInfo;
				});
				
				if(vm.dataType == "empty"){
					vm.selectDataModelCallback({
						dataModel			 : selectedDataModel,
						datasetOutputList	 : selectedDatasetOutputList,
						datasetOriginList	 : selectedDatasetOriginList,
						datasetInstanceList   : [],
						datasetSearchInfoList : [],
					});
				} else if(vm.dataType == "instance"){
					vm.selectDataModelCallback({
						dataModel			 : selectedDataModel,
						datasetOutputList	 : selectedDatasetOutputList,
						datasetOriginList	 : selectedDatasetOriginList,
						datasetInstanceList   : selectedDatasetInstanceList,
						datasetSearchInfoList : [],
					});
				} else if(vm.dataType == "filter"){
					vm.selectDataModelCallback({
						dataModel			 : selectedDataModel,
						datasetOutputList	 : selectedDatasetOutputList,
						datasetOriginList	 : selectedDatasetOriginList,
						datasetInstanceList   : [],
						datasetSearchInfoList : selectedDatasetSearchInfoList,
					});
				}
			}
			vm.closeDataModelSearchPopup();
		},
		onRowClick: function(dataModel){
			vm.selectedDataModel = dataModel;
		},
		addFilter: function(){
			var res = {};
			var resKey = "";
			if(vm.datasetInstanceList && vm.datasetInstanceList.length > 0){
				var datasetInstance = vm.datasetInstanceList[0];
				for(var key in datasetInstance){
					var obj = datasetInstance[key];
					if(obj.type == 'Property'){
						//step 1-1. type이 Property인 경우
						//step 1-2. 현재 키 저장
						res[key]={};
						if(!resKey) resKey = key;
						
						//step 2-1. type이 Property이면서 value가 pair 일 경우
						//step 2-2. value 내의 키 저장
						if(obj.value instanceof Object && !(obj.value instanceof Array)){
							for(var subkey in obj.value){
								res[key][subkey] = {};
							}
						}
					}
				}
				vm.datasetSearchInfoList.push({
					mainAttribute : resKey,
					subAttribute  : "",
					symbol		: "",
					value		 : "",
					main		  : res,
					sub		   : res[resKey],
				});
			}
		},
		deleteFilter: function(index){
			vm.datasetSearchInfoList.splice(index ,1);
		},
		selectFilter: function(index){
			vm.datasetSearchInfoList[index].sub = vm.datasetSearchInfoList[index].main[vm.datasetSearchInfoList[index].mainAttribute];
			vm.datasetSearchInfoList[index].subAttribute = "";
		},
		isValidateDataModel : function() {
			// 데이터유형(인스턴스형) 체크
			if (vm.dataType == "instance" && vm.selectedDatasetInstanceList.length <= 0) {
				alert('DataInstance는 1개 이상 선택을 해야 합니다.');
				return false;
			}
			// 데이터유형(필터형) 체크
			if (vm.dataType == 'filter') {
				if (vm.datasetSearchInfoList.length <= 0) {
					alert("옵션은 1개 이상 추가(+) 를 해야 합니다.");
					return false;
				}
				
				for (var i=0; i < vm.datasetSearchInfoList.length; i++) {
					if (!vm.datasetSearchInfoList[i].mainAttribute) {
						alert("첫번째 컬럼을 선택 해 주세요.");
						vm.$refs['mainAttribute_'+i][0].focus();
						return false;
					}
					
					if (Object.keys(vm.datasetSearchInfoList[i].sub).length > 0 && !vm.datasetSearchInfoList[i].subAttribute) {
						alert("두번째 컬럼을 선택 해 주세요.");
						vm.$refs['subAttribute_'+i][0].focus();
						return false;
					}
					
					if (!vm.datasetSearchInfoList[i].symbol) {
						alert("연산 조건을 입력 해 주세요.");
						vm.$refs['symbol_'+i][0].focus();
						return false;
					}
					
					if (!vm.datasetSearchInfoList[i].value) {
						alert("값을 입력 해 주세요.");
						vm.$refs['value_'+i][0].focus();
						return false;
					}
				}
			}
			return true;
	   	},
		openDataModelSearchPopup: function(callback){
			vm.selectDataModelCallback = callback;
			vm.dataModelSearchPopupVisible = true;
			vm.getDataModelList();
			$("html").addClass("is-scroll-blocking");
		},
		closeDataModelSearchPopup: function(){
			vm.step						= 1;
			vm.dataType					= "empty";
			vm.schValue					= "";
			vm.dataModelList			   = [];
			vm.datasetOriginList		   = [];
			vm.datasetInstanceList		 = [];
			vm.datasetSearchInfoList	   = [];
			vm.selectedDataModel		   = {};
			vm.selectedDatasetOriginList   = [];
			vm.selectedDatasetInstanceList = [];
			vm.datasetInstanceSelected	 = false;
			vm.dataModelSearchPopupVisible = false;
			vm.selectDataModelCallback	 = function(){};
			$("html").removeClass("is-scroll-blocking");
		}
	}
}
</script>