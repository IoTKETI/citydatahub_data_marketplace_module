<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
  <!-- modal -->
  <div class="modal modal--dataset-select" :class="adaptorRequestPopupVisible ? 'js-modal-show':''">
	<div class="modal__outer">
	  <div class="modal__inner">
		<div class="modal__header">
		  <h3 class="hidden">모달 타이틀</h3>
		  <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeAdaptorRequestPopup"><span class="hidden">모달 닫기</span></button>
		</div>
		<div class="modal__body">
		    <section class="modal__select-result">
	          <div class="select-result__top">
	            <h4 class="hidden">데이터모델 검색 결과</h4>
	          </div>
	          <div class="select-result__middle">
	            <div class="select-result__banner swiper-container">
	              <ul class="select-result__banner-list swiper-wrapper">
	                <li class="select-result__banner-item swiper-slide" v-for="(model, index) in modelList" @click="onRowClick(model)">
	                  <p class="select-result__banner-date">
	                      {{model.creationTime|mainDateYM}}
	                      <strong class="select-result__banner-date--emphasis">{{model.creationTime|mainDateD}}</strong>
	                  </p>
	                  <a class="select-result__banner-link" href="#none">
	                      <span class="select-result__banner-text--new">{{model.creator}}</span>
	                      <strong class="select-result__banner-text--category">{{model.name}}</strong>
	                  </a>
	                </li>
	              </ul>
	            </div>
	            <button class="select-result__banner-button select-result__banner-button--prev" type="button"><span class="hidden">이전으로</span></button>
	            <button class="select-result__banner-button select-result__banner-button--next" type="button"><span class="hidden">다음으로</span></button>
	          </div>
	        </section>
            <tr v-if="dataType == 'filter'">
                <th scope="row">어댑터 신청 팝업</th>
                <td>
                    <div class="box--right">
                        <button class="button__data-model--add material-icons" type="button" @click="addFilter"><span class="hidden">필드 추가하기</span></button>
                        <!-- <button class="table-button table-button__remove material-icons" @click="deleteFilter" type="button"><span class="hidden">삭제</span></button> -->
                    </div>
                    <ul class="full-field__list" v-if="filters.length > 0">
                       <li class="full-field__item" v-for="(filter, index) in filters">
                           <select class="select" v-model="filter.key" @change="selectFilter(index)">
                               <option value="" disabled>--선택--</option>
                               <option :value="key" v-for="(value, key) in filter.main">{{key}}</option>
                           </select>
                           <select class="select" v-model="filter.subKey" :disabled="isEmpty(filter.sub)">
                               <option value="" disabled>--선택--</option>
                               <option :value="key" v-for="(value, key) in filter.sub">{{key}}</option>
                           </select>
                           <input class="input" type="text" v-model="filter.value"/>
                           <button class="button__data-model--remove material-icons" type="button" @click="deleteFilter(index)"><span class="hidden">필드 삭제하기</span></button>
                       </li>
                    </ul>
                </td>
              </tr>
		</div>
		<div class="modal__footer">
		  <div class="button__group">
			<a class="button button__secondary" href="#none">신청</a>
			<a class="button button__outline--secondary js-modal-close" href="#none" @click="closeAdaptorRequestPopup">닫기</a>
		  </div>
		</div>
	  </div>
	</div>
  </div>
  <!-- //modal -->


<script>
var adaptorRequestPopupMixin={
	data: function(){
		return {
			modelList: [],
			filters: [],
			dataType: "",
			adaptorRequestPopupVisible: false,
			selectAdaptorCallback: function(){},
		}
	},
	methods: {
		isNotEmpty: function(obj){
			return Object.keys(obj).length > 0;
		},
		isEmpty: function(obj){
			return Object.keys(obj).length === 0;
		},
		addFilter: function(){
			var res = {};
			var resKey = "";
            if(vm.instances && vm.instances.length > 0){
                var instance = vm.instances[0];
                for(var key in instance){
                    var obj = instance[key];
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
            }
            
            vm.filters.push({
                key: resKey,
                subKey: "",
                operator: "",
                value: "",
                main: res,
                sub: {},
            });
			
		},
		deleteFilter: function(index){
			vm.filters.splice(index ,1);
		},
		selectFilter: function(index){
			vm.filters[index].sub = vm.filters[index].main[vm.filters[index].key];
			vm.filters[index].subKey = "";
		},
		onRowClick: function(){
			
		},
		searchDataModelList: function(){
            vm.dataModelLoading=true;
            var request = $.ajax({
                method: 'GET',
                url: SMC_CTX + "/dataset/model/getList.do",
                data: {
                    dsModelNm: vm.schValue
                },
                dataType: 'json'
            });
            request.done(function(data){
                vm.modelList = data;
                vm.$nextTick(function(){
                    var swiper3 = new Swiper('.select-result__banner', {
                      autoplay            : {
                        delay : 2500,
                        disableOnInteraction : false,
                      },
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
                vm.dataModelLoading=false;
            })
        },
		openAdaptorRequestPopup: function(callback){
			vm.selectAdaptorCallback = callback;
			vm.adaptorRequestPopupVisible = true;
			vm.searchDataModelList();
			$("html").addClass("is-scroll-blocking");
		},
		closeAdaptorRequestPopup: function(){
			vm.adaptorRequestPopupVisible = false;
			$("html").removeClass("is-scroll-blocking");
		}
	}
}
</script>