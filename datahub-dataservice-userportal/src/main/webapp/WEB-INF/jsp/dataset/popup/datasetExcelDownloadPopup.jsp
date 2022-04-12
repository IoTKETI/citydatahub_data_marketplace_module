<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- modal -->
  <div class="modal modal--dataset-select" :class="datasetExcelDownloadPopupVisible ? 'js-modal-show':''">
    <div class="modal__outer">
      <div class="modal__inner">
        <div class="modal__header">
          <h3 class="hidden">모달 타이틀</h3>
          <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeDatasetExcelDownloadPopup"><span class="hidden">모달 닫기</span></button>
        </div>
        <div class="modal__body">
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
                  <th scope="row"><i class="icon__require">*</i>timeAt</th>
                  <td>
                     <select ref="timeAt" class="select" v-model="excelParam.timeAt" :disabled="excelParam.datatype === 'lastdata'" title="timeAt" required>
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
                     <select ref="timeproperty" class="select" v-model="excelParam.timeproperty" title="timeproperty" required>
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
                  <td><label class="label__datepicker label__datepicker--full material-icons"><input ref="time" id="startExcelDateTime" name="time" title="time" class="input input__datepicker" type="text" v-model="excelParam.time" autocomplete="off"/></label></td>
                </tr>
                <tr>
                  <th scope="row"><i class="icon__require">*</i>endtime</th>
                  <td><label class="label__datepicker label__datepicker--full material-icons"><input ref="endtime" id="endExcelDateTime" name="endtime" title="endtime" class="input input__datepicker" type="text" v-model="excelParam.endtime" autocomplete="off"/></label></td>
                </tr>
                <tr>
                  <th scope="row">limit</th>
                  <td><input type="number" class="input" title="limit" v-model="excelParam.limit"/></td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="modal__buttons">
            <a class="button button__danger" href="#none" @click="datasetExcelDownload">다운로드</a>
            <a class="button button__outline--secondary js-modal-close" href="#none" @click="closeDatasetExcelDownloadPopup">닫기</a>
          </div>
        </div>
      </div>
    </div>
  </div>
  

<script>
var datasetExcelDownloadPopupMixin={
	data: function(){
		return {
			datasetExcelDownloadPopupVisible: false,
			excelParam: {
				datasetId: "",
				datatype: "historydata",
				timeAt: "between",
				timeproperty: "modifiedAt",
				time: moment().add(-1, 'month').format("YYYY-MM-DD[T]HH:mm:ss[Z]"),
				endtime: moment().format("YYYY-MM-DD[T]HH:mm:ss[Z]"),
				limit: 50,
			}
		}
	},
	watch: {
		"excelParam.limit": function(){
			if(this.excelParam.limit > 5000){
				this.excelParam.limit = 5000;
			}
		}
	},
	mounted: function() {
		$("#startExcelDateTime").datetimepicker({
            timeFormat:'\'T\'HH:mm:ss\'Z\'',
            controlType:'select',
            oneLine:true,
            onSelect: function(date){
                $("#endExcelDateTime").datetimepicker('option', 'minDateTime', new Date(date));
                vm.excelParam.time = date.split(' ').join('');
            }
        });
        
        $("#endExcelDateTime").datetimepicker({
            timeFormat:'\'T\'HH:mm:ss\'Z\'',
            hour:23,
            minute:59,
            second:59,
            controlType:'select',
            oneLine:true,
            onSelect: function(date){
                $("#startExcelDateTime").datetimepicker('option', 'maxDateTime', new Date(date));
                vm.excelParam.endtime = date.split(' ').join('');
            }
        });
	},
	methods: {
		datasetExcelDownload: function(datasetId){
			var request = $.ajax({
				method: "GET",
				url: SMC_CTX + "/validateDownloadExcelFile.do?" + $.param(vm.excelParam),
			});
			
			request.done(function(data){
				if(data){
					data = JSON.parse(data);
					alert(data.detail);
				}else{
					location.href = SMC_CTX + "/downloadExcelFile.do?" + $.param(vm.excelParam);
				}
			});
			
		},
		openDatasetExcelDownloadPopup: function(datasetId){
			vm.datasetExcelDownloadPopupVisible = true;
			vm.excelParam.datasetId = datasetId;
			$("html").addClass("is-scroll-blocking");
		},
		closeDatasetExcelDownloadPopup: function(){
			vm.datasetExcelDownloadPopupVisible = false;
			
			$("html").removeClass("is-scroll-blocking");
		}
	}
}
</script>