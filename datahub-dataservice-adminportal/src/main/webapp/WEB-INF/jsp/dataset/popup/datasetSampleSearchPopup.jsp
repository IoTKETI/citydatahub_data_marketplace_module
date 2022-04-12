<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :style="datasetSampleSearchPopupVisible ? 'display:block;':'display:none;'">
    <div class="modal__wrap">
      <div class="modal__content w-1000">
        <div class="modal__header">
          <h4 class="modal__title">모달 타이틀</h4>
          <button class="modal__button--close button__modal--close" type="button" @click="closeDatasetSampleSearchPopup"><span class="hidden">모달 닫기</span></button>
        </div>
        <div class="modal__body">
          <div class="section">
            <div class="section__header">
              <h4 class="section__title">요청변수(HEADER)</h4>
            </div>
            <div class="section__content">
              <table class="table--row">
                <caption>요청변수(HEADER) 테이블</caption> 
                <colgroup>
                  <col style="width: 150px;">
                  <col style="width: auto;">
                </colgroup> 
                <tbody>
                  <tr>
                    <th>Authorization</th>
                    <td><input type="text" class="input__text" v-model="cookieValue" readonly ></td>
                  </tr>
                  <tr>
                    <th>Content-Type</th>
                    <td><input type="text" class="input__text" value="application/json" readonly></td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
          <div class="section" style="margin-top:10px;">
            <div class="section__header">
              <h4 class="section__title">요청변수(PARAMETER)</h4>
            </div>
            <div class="section__content">
              <table class="table--row">
                <caption>요청변수(PARAMETER) 테이블</caption> 
                <colgroup>
                  <col style="width: 150px;">
                  <col style="width: auto;">
                </colgroup> 
                <tbody>
                  <tr>
                    <th class="icon__require">datatype</th>
                    <td>
                      <select ref="datatype" class="select select--full" v-model="data.datatype" @change="datatypeChange" title="datatype" required>
                        <c:forEach var="code" items="${n2m:getCodeList('CG_ID_SAMPLE_DATA_TYPE')}" varStatus="status">
                        <c:if test="${code.codeUseFl eq 'Y'}">
                          <option value="${code.codeName}">${code.codeName}</option>
                        </c:if>
                        </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <th>options</th>
                    <td>
                      <select ref="options" class="select select--full" v-model="data.options" title="options">
                      <option value="">선택</option>
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_SAMPLE_OPTIONS')}">
                        <c:if test="${code.codeUseFl eq 'Y'}">
                          <option value="${code.codeName}">${code.codeName}</option>
                        </c:if>
                      </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <th class="icon__require">timeAt</th>
                    <td>
                      <select ref="timeAt" class="select select--full" v-model="data.timeAt" :disabled="data.datatype === 'lastdata'" title="timeAt" required>
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_SAMPLE_DATE_TYPE')}">
                        <c:if test="${code.codeUseFl eq 'Y'}">
                          <option value="${code.codeName}">${code.codeName}</option>
                        </c:if>
                      </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <th class="icon__require">timeproperty</th>
                    <td>
                      <select ref="timeproperty" class="select select--full" v-model="data.timeproperty" :disabled="data.datatype === 'lastdata'" title="timeproperty" required>
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_SAMPLE_DATE_TYPE2')}">
                        <c:if test="${code.codeUseFl eq 'Y'}">
                          <option value="${code.codeName}">${code.codeName}</option>
                        </c:if>
                      </c:forEach>
                      </select>
                    </td>
                  </tr>
                  <tr>
                    <th class="icon__require">time</th>
                    <!-- <td><label class="label__picker"><input id="startDateTime" name="time" title="time" type="text" class="input__picker" disabled="disabled"></label></td> -->
                    <td><label class="label__picker"><input ref="time" id="startDateTime" name="time" title="time" class="input__picker" type="text" v-model="data.time" :disabled="data.datatype === 'lastdata'"/></label></td>
                  </tr>
                  <tr>
                    <th class="icon__require">endtime</th>
                    <!-- <td><label class="label__picker"><input id="endDateTime" name="endtime" title="endtime" type="text" class="input__picker" disabled="disabled"></label></td> -->
                    <td><label class="label__picker"><input ref="endtime" id="endDateTime" name="endtime" title="endtime" class="input__picker" type="text" v-model="data.endtime" :disabled="data.datatype === 'lastdata'"/></label></td>
                  </tr>
                  <tr>
                    <th>limit</th>
                    <td><input ref="limit" type="number" class="input__text" :disabled="data.datatype === 'lastdata'" title="limit"></td>
                  </tr>
                </tbody>
              </table>
            </div>
            <div class="button__group">
              <button class="button__primary" type="button" @click="getSampleData">검색</button>
            </div>
          </div>
          <div class="section" style="margin-top:10px;">
            <div class="section__header">
              <h4 class="section__title">샘플요청</h4>
            </div>
            <div class="section__content">
              <table class="table--row">
                <caption>샘플요청 테이블</caption> 
                <colgroup>
                  <col style="width: 150px;">
                  <col style="width: auto;">
                </colgroup>
                <thead>
                  <tr>
                    <th>구분</th>
                    <th>Open API 샘플 테스트</th>
                  </tr>
                </thead>
                <tbody>
                  <tr>
                    <th>요청주소</th>
                    <td>{{endPointAddr}}</td>
                  </tr>
                  <tr>
                    <th>API결과</th>
                    <td class="pre">
                      <pre class="pre__box">{{resultData}}</pre>
                    </td>
                  </tr>
                </tbody>
              </table>
            </div>
          </div>
        </div>

        <div class="modal__footer">
          <button class="button__secondary button__modal--close" type="button" @click="closeDatasetSampleSearchPopup">닫기</button>
        </div>
      </div>
    </div>
  </div>
  

<script>
var datasetSampleSearchPopupMixin={
	data: function(){
		return {
			datasetSampleSearchPopupVisible: false,
			data: {
				datatype: "",
				options: "",
				timeAt: "",
				timeproperty: "",
				time: "",
				endtime: "",
				limit: "",
			},
			endPointAddr: "",
			cookieValue: "",
			resultData: [],
			requestData: {},
			id: ""
		}
	},	
	mounted: function() {
		var dataTypeOpts = this.$refs.datatype.children;
		var timeAtOpts = this.$refs.timeAt.children;
		var timePropertyOpts = this.$refs.timeproperty.children;
		
		if (dataTypeOpts.length) this.data.datatype = dataTypeOpts[0].value;
		
		if (this.data.datatype != "lastdata") {
			if (timeAtOpts.length) this.data.timeAt = timeAtOpts[0].value;
			if (timePropertyOpts.length) this.data.timeproperty = timePropertyOpts[0].value;
		}

		$("#startDateTime").datetimepicker({
            timeFormat:'HH:mm:ss',
            controlType:'select',
            oneLine:true,
            onSelect: function(date){
                $("#endDateTime").datetimepicker('option', 'minDate', new Date(date));
                vm.data.time = date;
            }
        });
        
        $("#endDateTime").datetimepicker({
            timeFormat:'HH:mm:ss',
            hour:23,
            minute:59,
            second:59,
            controlType:'select',
            oneLine:true,
            onSelect: function(date){
                $("#startDateTime").datetimepicker('option', 'maxDate', new Date(date));
                vm.data.endtime = date;
            }
        });
        
        if (document.cookie) {
        	var array = document.cookie.split((escape('chaut') + '='));
        	
        	if (array.length >= 2) {
        		this.cookieValue = "Bearer " + array[1];
        	}
        }
	},
	methods: {
		openDatasetSampleSearchPopup: function(endPointAddr, id){
			vm.datasetSampleSearchPopupVisible = true;
			vm.endPointAddr = endPointAddr;
			vm.id = id;
			
			$("html").addClass("is-scroll-blocking");

			vm.getSampleData();
		},
		closeDatasetSampleSearchPopup: function(){
			vm.datasetSampleSearchPopupVisible = false;
			
			$("html").removeClass("is-scroll-blocking");
		},
		getSampleData: function() {
			vm.requestData = {};
			
			if (vm.data.datatype !== "lastdata") {
				validate.extend(validate.validators.datetime, {
	                parse: function(value, options) {
	                  return +moment.utc(value);
	                },
	                format: function(value, options) {
	                  var format = options.dateOnly ? "YYYY-MM-DD" : "YYYY-MM-DD hh:mm:ss";
	                  return moment.utc(value).format(format);
	                }
	            });
				
				Valid.init(vm, vm.data);
	            var constraints = {};
	            
	            constraints = Valid.getConstraints();
				
	            constraints.time.datetime = {datetime : true, message: MSG_VALID_INVALID};
	            constraints.endtime.datetime = {datetime : true, message: MSG_VALID_INVALID};
	            
	            if(Valid.validate(constraints)) return;
			}
			
			for (var key in vm.data) {
				if (vm.$refs[key].value) {
					vm.requestData[key] = vm.$refs[key].value;
				} 
			}
			
			if (vm.id) vm.requestData.id = vm.id;
			vm.requestData.contentType = "application/json";
			
			var request = $.ajax({
                url: N2M_CTX + "/dataset/sample/get.do",
                method: "GET",
                dataType: "json",
                contentType: "application/json",
                data: vm.requestData
            });
			
			request.done(function(data) {
				if (data.length > 0) vm.resultData = data[data.length - 1];
				else vm.resultData = [];
			});
			
			request.fail(function(data) {
				vm.resultData = [];
			});
		},
		datatypeChange: function() {
			if (vm.data.datatype !== "lastdata") {
				var timeAtOpt = vm.$refs.timeAt.children;
				var timePropertyOpt = vm.$refs.timeproperty.children;
				
				if (timeAtOpt.length) vm.data.timeAt = timeAtOpt[0].value;
				if (timePropertyOpt.length) vm.data.timeproperty = timePropertyOpt[0].value;
			} else {
				vm.data.options = "";
				vm.data.timeAt = "";
				vm.data.timeproperty = "";
				vm.data.time = "";
				vm.data.endtime = "";
				vm.data.limit = "";
			}
		}
	}
}
</script>