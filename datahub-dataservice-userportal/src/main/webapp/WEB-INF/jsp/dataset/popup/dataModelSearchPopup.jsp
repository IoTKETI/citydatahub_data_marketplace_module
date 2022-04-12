<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- modal -->
<div ref="dataModelPopup" class="modal modal--dataset-select" :class="dataModelSearchPopupVisible ? 'js-modal-show':''">
	<div class="modal__outer">
		<div class="modal__inner">
			<div class="modal__header">
				<h3 class="hidden">모달 타이틀</h3>
				<button class="modal__button--close js-modal-close material-icons" type="button" @click="closeDataModelSearchPopup"><span class="hidden">모달 닫기</span></button>
			</div>
			<div class="modal__body" v-show="step === 1">
				<section class="modal-cover">
					<ul class="modal-cover__select">
						<li>
							<a class="modal-cover__title modal-cover__title--topic material-icons choice" href="#none"><p>데이터 모델</p></a>
						</li>
						<li>
							<a class="modal-cover__title modal-cover__title--set material-icons unchoice" href="#none"><p>데이터 셋</p></a>
						</li>
						<li>
							<a class="modal-cover__title modal-cover__title--find material-icons unchoice" href="#none"><p>검색유형</p></a>
						</li>
					</ul>
				</section>
				<section class="modal__select-form">
				<div class="select-form">
					<fieldset>
					<legend>상세 목록 필터 폼</legend>
					<div class="select-form__group">
						<select class="select">
						<option value="선택">데이터 모델명</option>
						</select>
						<input class="input input__search" type="text" v-model="schValue" @keypress.enter="getDataModelList" placeholder="검색어를 입력하세요"/>
						<button class="button__search material-icons" type="button" @click="getDataModelList"><span class="hidden">검색</span></button>
					</div>
					</fieldset>
				</div>
				</section>
	
				<section class="modal__select-result">
				<div class="select-result__top">
					<h4 class="hidden">데이터모델 검색 결과</h4>
				</div>
				<div class="select-result__middle">
					<div class="select-result__banner swiper-container">
						<ul class="select-result__banner-list swiper-wrapper">
							<li class="select-result__banner-item swiper-slide" v-for="(model, index) in dataModelList" @click="onRowClick(model)">
								<a class="select-result__banner-link" href="#none">
										<strong class="select-result__banner-text--category">{{model.id}}</strong>
								</a>
							</li>
						</ul>
					</div>
					<button class="select-result__banner-button select-result__banner-button--prev" type="button"><span class="hidden">이전으로</span></button>
					<button class="select-result__banner-button select-result__banner-button--next" type="button"><span class="hidden">다음으로</span></button>
				</div>
				</section>
	
				<section class="modal__select-result modal__select-result--info material-icons">
				<div class="select-result__top">
					<h4 class="hidden">데이터모델 검색 상세결과</h4>
				</div>
				<div class="select-result__middle">
					<table class="select-result__table">
					<caption>해당 테이블에 대한 캡션</caption>
					<colgroup>
						<col width="15%">
						<col width="*">
					</colgroup>
					<tbody class="list1">
						<tr>
							<th scope="row">데이터ID</th>
							<td><input class="input" type="text" v-model="selectedDataModel.type" disabled/></td>
						</tr>
						<tr>
							<th scope="row" class="fixed-table__th">컬럼정보</th>
							<td>
								<div class="fixed-table__wrap">
									<div class="fixed-table__thead">
										<table class="fixed-table">
											<caption>해당 테이블에 대한 캡션</caption>
											<colgroup>
												<col width="16%">
												<col width="42%">
												<col width="42%">
												<col width="17px">
											</colgroup>
											<thead>
												<tr>
												<th scope="col">구분</th>
												<th scope="col">컬럼명</th>
												<th scope="col">컬럼설명</th>
												<th></th>
												</tr>
											</thead>
										</table>
									</div>
									<div class="fixed-table__tbody" style="height:91px">
										<table class="fixed-table">
											<caption>해당 테이블에 대한 캡션</caption>
											<colgroup>
												<col width="16%">
												<col width="42%">
												<col width="42%">
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
				</section>
			</div>
			<div class="modal__body" v-show="step === 2">
				<section class="modal-cover">
					<ul class="modal-cover__select">
						<li>
							<a class="modal-cover__title modal-cover__title--topic material-icons unchoice" href="#none"><p>데이터 모델</p></a>
						</li>
						<li>
							<a class="modal-cover__title modal-cover__title--set material-icons choice" href="#none"><p>데이터 셋</p></a>
						</li>
						<li>
							<a class="modal-cover__title modal-cover__title--find material-icons unchoice" href="#none"><p>검색유형</p></a>
						</li>
					</ul>
				</section>
				<section class="modal__select-result modal__select-result--info material-icons">
					<div>
						<ul class="modal__select-result__info">
							<li class="modal-cover__title--info material-icons">
								<p>선택 된 데이터 모델에 해당 하는 데이터셋 목록이 표시 됩니다.</p>
								<p>데이터셋은 최소 1개 이상 선택을 해야 합니다.</p>
								<p>데이터 인스턴스를 조회 시 선택 된 데이터셋은 검색 조건으로 추가 됩니다.</p>
							</li>
						</ul>
					</div>
					<div class="select-result__top">
						<h4 class="hidden">데이터모델 검색 상세결과</h4>
					</div>
					<div class="select-result__middle">
						<div class="fixed-table__wrap">
							<div class="fixed-table__thead">
								<table class="fixed-table">
									<caption>해당 테이블에 대한 캡션</caption>
									<colgroup>
										<col width="5%">
										<col width="40%">
										<col width="40%">
										<col width="15%">
									</colgroup>
									<thead>
										<tr>
											<th>
												<input id="allcheck" class="checkbox hidden" type="checkbox" @click="selectAllDatasetOrigin" v-model="datasetOriginSelected">
												<label for="allcheck" class="label__checkbox js-all-checkbox material-icons"></label>
											</th>
											<th scope="col">ID</th>
											<th scope="col">NAME</th>
											<th scope="col">CATEGORY</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="fixed-table__tbody" style="height:391px">
								<table class="fixed-table">
									<caption>해당 테이블에 대한 캡션</caption>
									<colgroup>
										<col width="5%">
										<col width="40%">
										<col width="40%">
										<col width="15%">
									</colgroup>
									<tbody>
										<tr v-if="datasetOriginList.length === 0">
											<td colspan="4">데이터 출력 정보가 없습니다.</td>
										</tr>
										<tr v-for="(datasetOrigin, index) in datasetOriginList">
											<th class="text--center" scope="row">
												<input :id="'datasetOrigin' + index" class="checkbox hidden" type="checkbox" :value="datasetOrigin" v-model="selectedDatasetOriginList" @change="selectDatasetOrigin"/>
												<label :for="'datasetOrigin' + index" class="label__checkbox js-all-checkbox material-icons"></label>
											</th>
											<td class="text--center" scope="row">{{datasetOrigin.id}}</td>
											<td class="text--center" scope="row">{{datasetOrigin.name}}</td>
											<td class="text--center" scope="row">{{datasetOrigin.category}}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</section>
			</div>
			<div class="modal__body" v-show="step === 3">
				<section class="modal-cover">
					<ul class="modal-cover__select">
						<li>
							<a class="modal-cover__title modal-cover__title--topic material-icons unchoice" href="#none"><p>데이터 모델</p></a>
						</li>
						<li>
							<a class="modal-cover__title modal-cover__title--set material-icons unchoice" href="#none"><p>데이터 셋</p></a>
						</li>
						<li>
							<a class="modal-cover__title modal-cover__title--find material-icons choice" href="#none"><p>검색유형</p></a>
						</li>
					</ul>
				</section>
				<section class="modal__select-form">
					<ul class="modal__select-result__info">
						<li class="modal-cover__title--info material-icons">
							<p>제공유형은 인스턴스형, 필터형 2가지 형태로 제공 됩니다.</p>
							<p>인스턴스형은 선택 된 모델 하위의 데이터셋에 속해 있는 데이터 인스턴스들의 목록 중 취사선택을 하여, 희망하는 데이터를 조회 할 수 있는 방식입니다.</p>
							<p>필터형의 경우, 데이터 인스턴스 조회시 속성 값을 기반으로 검색조건을 조합하여 데이터를 조회 하는 방식입니다.</p>
						</li>
					</ul>
					<div class="select-form">
						<fieldset>
						<legend>상세 목록 필터 폼</legend>
						<div class="select-form__group">
							<label class="label" style="width: 130px;color:#666666;">제공유형</label>
							<select class="select" v-model="dataType">
								<c:forEach var="code" items="${n2m:getCodeList('CG_ID_DATASET_DATA_TYPE')}">
									<option value="${code.codeId}">${code.codeName}</option>
								</c:forEach>
							</select>
						</div>
						</fieldset>
					</div>
				</section>
				<section class="modal__select-result modal__select-result--info material-icons" v-if="dataType == 'instance'">
					<div class="select-result__top">
						<h4 class="hidden">데이터모델 검색 상세결과</h4>
					</div>
					<div class="select-result__middle">
						<div class="fixed-table__wrap">
							<div class="fixed-table__thead">
								<table class="fixed-table">
									<caption>해당 테이블에 대한 캡션</caption>
									<colgroup>
										<col width="5%">
										<col width="60%">
										<col width="35%">
									</colgroup>
									<thead>
										<tr>
											<th>
												<input id="allcheck_2" class="checkbox hidden" type="checkbox" @click="selectAllDatasetInstance" v-model="datasetInstanceSelected">
												<label for="allcheck_2" class="label__checkbox js-all-checkbox material-icons"></label>
											</th>
											<th scope="col">DataInstance ID</th>
											<th scope="col">등록일자</th>
										</tr>
									</thead>
								</table>
							</div>
							<div class="fixed-table__tbody" style="height:391px">
								<table class="fixed-table">
									<caption>해당 테이블에 대한 캡션</caption>
									<colgroup>
										<col width="5%">
										<col width="60%">
										<col width="35%">
									</colgroup>
									<tbody>
										<tr v-if="datasetInstanceList.length === 0">
											<td colspan="4">데이터 출력 정보가 없습니다.</td>
										</tr>
										<tr v-for="(datasetInstance, index) in datasetInstanceList">
											<th class="text--center" scope="row">
												<input :id="'datasetInstance' + index" class="checkbox hidden" type="checkbox" :value="datasetInstance" v-model="selectedDatasetInstanceList" @change="selectDatasetInstance"/>
												<label :for="'datasetInstance' + index" class="label__checkbox js-all-checkbox material-icons"></label>
											</th>
											<td class="text--center" scope="row">{{datasetInstance.id}}</td>
											<td class="text--center" scope="row">{{datasetInstance.createdAt|date}}</td>
										</tr>
									</tbody>
								</table>
							</div>
						</div>
					</div>
				</section>
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
			<!-- 
			버튼 영역 
			-->
			<div class="modal__footer">
				<div class="button__group">
					<a class="button button__secondary" href="#none" @click="prevStep" v-show="step !== 1">이전</a>
					<a class="button button__secondary" href="#none" @click="nextStep" v-show="step !== 3">다음</a>
					<a class="button button__secondary" href="#none" @click="selectDataModel" v-show="step === 3">선택</a>
				</div>
			</div>
		</div>
	</div>
</div>
<!-- //modal -->


<script>
var dataModelSearchPopupMixin={
	data: function(){
		return {
			step: 1,
			dataType                    : "empty",
			schValue                    : "",
			dataModelList               : [],
			datasetOriginList           : [],
			datasetInstanceList         : [],
			datasetSearchInfoList       : [],
			selectedDataModel           : {},
			selectedDatasetOriginList   : [],
			selectedDatasetInstanceList : [],
			datasetOriginSelected       : false,
			datasetInstanceSelected     : false,
			dataModelSearchPopupVisible : false,
			selectDataModelCallback     : function(){},
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
				url: SMC_CTX + "/dataset/model/getList.do",
				data: {
					modelName: vm.schValue
				},
				dataType: 'json'
			});
			request.done(function(data){
				vm.dataModelList = data;
				vm.$nextTick(function(){
					var swiper3 = new Swiper('.select-result__banner', {
				      direction           : 'horizontal',
				      grabCursor          : false,
				      initialSlide        : 0,
				      keyboard            : {
				        enabled : true,
				      },
				      loop                : false,
				      navigation          : {
				        nextEl : '.select-result__banner-button--next',
				        prevEl : '.select-result__banner-button--prev',
				      },
				      roundLengths        : true,
				      slidesPerView       : 1, 
				      slidesPerGroup      : 1,
				      spaceBetween        : 0,
				      breakpointsInverse  : true, 
				      breakpoints         : { 
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
				url: SMC_CTX + "/dataset/datasetorigin/getList.do",
				data: {
					modelId      : vm.selectedDataModel.id,
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
		selectAllDatasetOrigin: function(){
			if(vm.selectedDatasetOriginList.length === vm.datasetOriginList.length){
				vm.selectedDatasetOriginList = [];
			}else{
				vm.selectedDatasetOriginList = [];
				vm.datasetOriginList.forEach(function(datasetOrigin){
					vm.selectedDatasetOriginList.push(datasetOrigin);
				});
			}
			
		},
		selectDatasetOrigin: function(){
			if(vm.selectedDatasetOriginList.length > 0 && vm.selectedDatasetOriginList.length === vm.datasetOriginList.length){
				vm.datasetOriginSelected = true;
			}else{
				vm.datasetOriginSelected = false;
			}
		},
		
		getDatasetInstanceList: function(dsModelId){
			vm.loadingFlag=true;
			vm.instances = [];
			var request = $.ajax({
				method: 'GET',
				url: SMC_CTX + "/dataset/instance/getList.do",
				data: {
					modelId      : vm.selectedDataModel.id,
					modelTypeUri	: vm.selectedDataModel.typeUri,
					searchType     : vm.selectedDatasetOriginList.map(function(datasetOrigin){
						return '"' + datasetOrigin.id + '"';
					}).join(",")
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
			if(vm.selectedDatasetInstanceList.length > 0 && vm.selectedDatasetInstanceList.length === vm.datasetInstanceList.length){
				vm.selectedDatasetInstanceList = [];
			}else{
				vm.selectedDatasetInstanceList = vm.datasetInstanceList.map(function(datasetInstance){
					return datasetInstance;
				});
			}
		},
		selectDatasetInstance: function(){
			if(vm.selectedDatasetInstanceList.length > 0 && vm.selectedDatasetInstanceList.length === vm.datasetInstanceList.length){
				vm.datasetInstanceSelected = true;
			}else{
				vm.datasetInstanceSelected = false;
			}
		},
		selectDataModel: function(){
			if(!vm.isValidateDataModel()) return;
			
			if($.isFunction(vm.selectDataModelCallback)){
				var selectedDataModel              = { modelId: vm.selectedDataModel.id, modelName: vm.selectedDataModel.typeUri };
				var selectedDatasetOriginList      = vm.selectedDatasetOriginList;
				var selectedDatasetOutputList      = vm.selectedDataModel.attributes.map(function(attribute){ return { realColumnName: attribute.name, aliasColumnName: attribute.name, description: attribute.valueType, open: true }; });
				var selectedDatasetInstanceList    = vm.selectedDatasetInstanceList.map(function(instance){ return { instanceId: instance.id }; });
				var selectedDatasetSearchInfoList  = vm.datasetSearchInfoList.map(function(searchInfo){
					
					var symbolArr = searchInfo.symbol.split("|");
					searchInfo.symbol = symbolArr[0];
					searchInfo.symbolName = symbolArr[1];
					return searchInfo;
				});
				
				if(vm.dataType == "empty"){
					vm.selectDataModelCallback({
						searchType            : vm.dataType,
						dataModel             : selectedDataModel,
						datasetOutputList     : selectedDatasetOutputList,
						datasetOriginList     : selectedDatasetOriginList,
						datasetInstanceList   : [],
						datasetSearchInfoList : [],
					});
				} else if(vm.dataType == "instance"){
					vm.selectDataModelCallback({
						searchType            : vm.dataType,
						dataModel             : selectedDataModel,
						datasetOutputList     : selectedDatasetOutputList,
						datasetOriginList     : selectedDatasetOriginList,
						datasetInstanceList   : selectedDatasetInstanceList,
						datasetSearchInfoList : [],
					});
				} else if(vm.dataType == "filter"){
					vm.selectDataModelCallback({
						searchType            : vm.dataType,
						dataModel             : selectedDataModel,
						datasetOutputList     : selectedDatasetOutputList,
						datasetOriginList     : selectedDatasetOriginList,
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
	                symbol        : "",
	                value         : "",
	                main          : res,
	                sub           : res[resKey],
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
			vm.step                        = 1;
			vm.dataType                    = "empty";
			vm.schValue                    = "";
			vm.dataModelList               = [];
			vm.datasetOriginList           = [];
			vm.datasetInstanceList         = [];
			vm.datasetSearchInfoList       = [];
			vm.selectedDataModel           = {};
			vm.selectedDatasetOriginList   = [];
			vm.selectedDatasetInstanceList = [];
			vm.datasetOriginSelected       = false;
			vm.datasetInstanceSelected     = false;
			vm.dataModelSearchPopupVisible = false;
			vm.selectDataModelCallback     = function(){};
			$("html").removeClass("is-scroll-blocking");
		}
	}
}
</script>