<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">데이터 분석가요청 상세</h3>
    <form>
      <fieldset>
        <legend>필드셋 제목</legend>
        <!-- section-write -->
        <section class="section">
          <div class="section__content">
            <table class="table--row">
              <caption>테이블 제목</caption>
              <colgroup>
                <col style="width:150px">
                <col style="width:auto">
                <col style="width:150px">
                <col style="width:auto">
              </colgroup>
              <tbody>
                <!-- <input type="hidden" name="analyOid" v-model="analy.analyOid" /> -->
                <tr>
                  <th>요청상태</th>
                  <td colspan="1">
                    <select class="select select--full" v-model="analy.requestStatus" disabled>
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_APPR_REQUEST_STATUS')}">
	                    <option value="${code.codeId}">${code.codeName}</option>
	                  </c:forEach>
                    </select>
                  </td>
                  <th>등록자</th>
                  <td colspan="1">{{analy.creatorName}}</td>
                </tr>
                <tr>
                  <th>신청사유</th>
                  <td colspan="3" v-html="$options.filters.enter(analy.reason)">
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
        <!-- //section-default -->
        <!-- <div class="button__group">
        </div> -->
      </fieldset>
    </form>
    
    <section class="section">
        <div class="section__header">
            <h4 class="section__title">데이터모델</h4>
        </div>
        <div class="section__content">
            <table class="table--row">
                <caption>테이블 제목</caption>
                <colgroup>
                    <col style="width: 150px">
                    <col style="width: auto">
                    <col style="width: 150px">
                    <col style="width: auto">
                </colgroup>
                <tbody>
                    <tr v-for="dataModel in analy.dataModelList">
                        <th>데이터모델 ID</th>
                        <td>{{dataModel.modelId}}</td>
                        <th>데이터모델명</th>
                        <td >{{dataModel.modelName}}</td>
                    </tr>
                </tbody>
            </table>
        </div>
    </section>
    <div class="button__group">
        <button class="button__primary" type="button" @click="modifyAnaly('apprComplete')">승인</button>
        <button class="button__secondary" type="button" onclick="location.href='<c:url value="/analy/pageList.do"/>'">목록</button>
    </div>
</div>
<script>
$(function() {
    vm = new Vue({
        el : '#content',
        data : function() {
            return {
                analy : {},
                dataModelList: []
            }
        },
        methods : {
            getAnaly : function() {
                var request = $.ajax({
                    url : N2M_CTX + "/analy/get.do",
                    method : "GET",
                    dataType : "json",
                    data : {
                    	id: "${param.id}"
                    }
                });
                
                request.done(function(data) {
                    vm.analy = data;
//                     vm.dataModelList = data.datamodelList;
                });
            },
            modifyAnaly: function(sts) {
                if (!confirm("승인하시겠습니까?")) return;
                
                var modifyData = {
	                id             : vm.analy.id,
	                requestStatus  : sts,
	                requestor      : vm.analy.creatorId
                };
                
                var request = $.ajax({
                    url: N2M_CTX + "/analy/modify.do",
                    method: "PUT",
                    contentType: "application/json",
                    data: JSON.stringify(modifyData)
                });
              
                request.done(function(data){
                    Modal.OK();
                    location.href = N2M_CTX + "/analy/pageList.do";
                });
                
                request.fail(function(data) {
                    vm.loading = false;
                });
            }
        }
    })
    vm.getAnaly();
});
</script>
