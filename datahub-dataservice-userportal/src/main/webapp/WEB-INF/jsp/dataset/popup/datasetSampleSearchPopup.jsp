<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- modal -->
  <div class="modal modal--dataset-select" :class="datasetSampleSearchPopupVisible ? 'js-modal-show':''">
    <div class="modal__outer">
      <div class="modal__inner">
        <div class="modal__header">
          <h3 class="hidden">모달 타이틀</h3>
          <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeDatasetSampleSearchPopup"><span class="hidden">모달 닫기</span></button>
        </div>
        <div class="modal__body">
          <h4 class="modal__heading1">요청변수(HEADER)</h4>
          <div class="modal__box">
            <table class="modal__form-table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="130px">
                <col width="*">
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row">Authorization</th>
                  <td><input type="text" class="input" v-model="cookieValue" readonly/></td>
                </tr>
                <tr>
                  <th scope="row">Content-Type</th>
                  <td><input type="text" class="input" value="application/json" readonly/></td>
                </tr>
              </tbody>
            </table>
          </div>
          <h4 class="modal__heading1">요청변수(PARAMETER)</h4>
          <div class="modal__box">
            <table class="modal__form-table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="130px">
                <col width="*">
              </colgroup>
              <tbody>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>datatype</th>
                  <td>
                     <select ref="datatype" class="select" v-model="data.datatype" @change="datatypeChange" title="datatype" required>
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_SAMPLE_DATA_TYPE')}" varStatus="status">
                        <c:if test="${code.codeUseFl eq 'Y'}">
                          <option value="${code.codeName}">${code.codeName}</option>
                        </c:if>
                      </c:forEach>
                    </select>
                  </td>
                </tr>
                <tr>
                  <th scope="row">options</th>
                  <td>
                     <select ref="options" class="select" v-model="data.options" title="options">
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
                  <th scope="row"><i class="icon__require">*</i>timeAt</th>
                  <td>
                     <select ref="timeAt" class="select" v-model="data.timeAt" :disabled="data.datatype === 'lastdata'" title="timeAt" required>
                       <c:forEach var="code" items="${n2m:getCodeList('CG_ID_SAMPLE_DATE_TYPE')}">
                         <c:if test="${code.codeUseFl eq 'Y'}">
                           <option value="${code.codeName}">${code.codeName}</option>
                         </c:if>
                       </c:forEach>
                    </select>
                  </td>
                </tr>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>timeproperty</th>
                  <td>
                     <select ref="timeproperty" class="select" v-model="data.timeproperty" :disabled="data.datatype === 'lastdata'" title="timeproperty" required>
	                     <c:forEach var="code" items="${n2m:getCodeList('CG_ID_SAMPLE_DATE_TYPE2')}">
		                     <c:if test="${code.codeUseFl eq 'Y'}">
		                         <option value="${code.codeName}">${code.codeName}</option>
		                     </c:if>
	                     </c:forEach>
                    </select>
                  </td>
                </tr>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>time</th>
                  <td><label class="label__datepicker label__datepicker--full material-icons"><input ref="time" id="startDateTime" name="time" title="time" class="input input__datepicker" type="text" v-model="data.time" :disabled="data.datatype === 'lastdata'"/></label></td>
                </tr>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>endtime</th>
                  <td><label class="label__datepicker label__datepicker--full material-icons"><input ref="endtime" id="endDateTime" name="endtime" title="endtime" class="input input__datepicker" type="text" v-model="data.endtime" :disabled="data.datatype === 'lastdata'"/></label></td>
                </tr>
                <tr>
                  <th scope="row">limit</th>
                  <td><input ref="limit" type="number" class="input" :disabled="data.datatype === 'lastdata'" title="limit"/></td>
                </tr>
                <!-- <tr>
                  <th scope="row">q</th>
                  <td><input ref="q" type="text" class="input" v-model="data.q" :disabled="data.datatype === 'lastdata'" title="q"/></td>
                </tr> -->
              </tbody>
            </table>
          </div>
          <div class="modal__buttons">
            <a class="button button__danger" href="#none" @click="getSampleData">검색</a>
          </div>
          <h4 class="modal__heading1">샘플요청</h4>
          <div class="modal__box--type2" style="padding:0">
            <table class="modal__result-table">
              <caption>해당 테이블에 대한 캡션</caption>
              <colgroup>
                <col width="130px">
                <col width="*">
              </colgroup>
              <thead>
                <tr>
                  <th>구분</th>
                  <th>Open API 샘플 테스트</th>
                </tr>
              </thead>
              <tbody>
                <tr>
                  <th scope="row">요청주소</th>
                  <td>{{endPointAddr}}</td>
                </tr>
                <tr>
                  <th scope="row">API결과</th>
                  <td class="pre">
					<pre class="pre__box">{{resultData}}</pre>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </div>
        <div class="modal__footer">
          <div class="button__group">
            <a class="button button__outline--secondary js-modal-close" href="#none" @click="closeDatasetSampleSearchPopup">닫기</a>
          </div>
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
			vm.datasetId = id;
			
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
			if (vm.datasetId) vm.requestData.datasetId = vm.datasetId;
			vm.requestData.contentType = "application/json";
			
			vm.loadingFlag = true;
			
			//TODO 데이터규격 2.0 기준으로 Query 조건 변경 할 것.
			var request = $.ajax({
                url: SMC_CTX + "/dataset/sample/get.do",
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
			
			request.always(function(data) {
				vm.loadingFlag = false;
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