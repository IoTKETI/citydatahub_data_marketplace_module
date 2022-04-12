<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
    <h3 class="content__title">Q&A목록</h3>
    <form>
      <fieldset>
        <legend>필드셋 제목</legend>
        <section class="section">
          <div class="section__header">
            <h4 class="section__title">Q&A 검색</h4>
          </div>
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
                <tr>
                  <th>검색어</th>
                  <td>
                    <select class="select" v-model="pageInfo.schCondition">
                      <option value="">전체</option>
                      <option value="title">제목</option>
                      <option value="writer">작성자</option>
                    </select>
                    <input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue" @keypress.enter.prevent="getQnaList(1)" />
                  </td>
                  <th>등록일</th>
                  <td>
                  	<div class="picker__group">
                  		<label class="label__picker"><input id="fromDate" class="input__picker" type="text" v-model="pageInfo.fromDate"></label><span class="picker__period">~</span><label class="label__picker"><input id="toDate" class="input__picker" type="text" v-model="pageInfo.toDate"></label>
                  	</div>
                  </td>
                </tr>
                <tr>
                  <th>유형</th>
                  <td>
                    <select class="select select--full" v-model="pageInfo.schCondition2">
                      <option value="">전체</option>
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_TYPE')}">
                        <option value="${code.codeId}">${code.codeName}</option>
                      </c:forEach>
                    </select>
                  </td>
                  <th>진행상태</th>
                  <td>
                    <select class="select select--full" v-model="pageInfo.schCondition3">
                      <option value="">전체</option>
                      <c:forEach var="code" items="${n2m:getCodeList('CG_ID_QNA_STATUS')}">
                        <option value="${code.codeId}">${code.codeName}</option>
                      </c:forEach>
                    </select>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
          <div class="button__group">
            <button class="button__primary" type="button" @click="getQnaList(1)">검색</button>
          </div>
        </section>
      </fieldset>
    </form>
    
    <section class="section">
        <div class="section__header">
            <h4 class="section__title">Q&A 목록</h4>
        </div>
        <div class="section__content">
        <el-table :data="list" border style="width:100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="onRowClick" @sort-change="onSortChange" :default-sort = "{prop: pageInfo.sortField, order: pageInfo.sortOrder}">
            <el-table-column prop="qnaId" label="번호" width="120" sortable="custom"></el-table-column>
            <el-table-column prop="qnaTitle" label="제목" align="left" sortable="custom"></el-table-column>
            <el-table-column prop="qnaQuestionTypeNm" label="유형" width="160" sortable="custom"></el-table-column>
            <el-table-column prop="qnaQuestionUsrNm" label="작성자ID" align="left" width="160" sortable="custom"></el-table-column>
            <el-table-column prop="qnaStatusNm" label="진행상태" width="160" sortable="custom"></el-table-column>
            <el-table-column prop="qnaQuestionDt" label="등록일시" width="240" sortable="custom">
              <template slot-scope="scope">
                    {{scope.row.qnaQuestionDt|date}}
              </template>
            </el-table-column>
        </el-table>
        <n2m-pagination :pager="pageInfo" @pagemove="getQnaList"/>
      </div>
    </section>
    <div class="button__group">
       <c:if test="${writeYn eq 'Y'}">
            <button class="button__primary" type="button" onclick="location.href='<c:url value="/qna/pageEdit.do"/>'">등록</button>
      </c:if>
    </div>
</div>
<script>
    $(function() {
        Vue.use(N2MPagination);
        vm = new Vue({
            el : '#content',
            data : function() {
                return {
                    list : [],
                    loading: true
                }
            },
            methods : {
                getQnaList : function(curPage) {
                    vm.loading = true;
                    vm.pageInfo.curPage = curPage;
                    var request = $.ajax({
                        url : N2M_CTX + "/qna/getList.do",
                        method : "GET",
                        dataType : "json",
                        data : {
                            schCondition : vm.pageInfo.schCondition,
                            schValue     : vm.pageInfo.schValue,
                            fromDate : vm.pageInfo.fromDate,
                            toDate : vm.pageInfo.toDate,
                            schCondition2 : vm.pageInfo.schCondition2,
                            schCondition3 : vm.pageInfo.schCondition3,
                            curPage      : vm.pageInfo.curPage,
                            sortField     : vm.pageInfo.sortField,
                            sortOrder     : vm.pageInfo.sortOrder,
                        }
                    });
                    request.done(function(data) {
                        vm.loading = false;
                        vm.list = data.list;
                        vm.pageInfo = data.page;
                    });
                    request.fail(function(data){
                        vm.loading = false;
                    });
                },
                onRowClick: function(row){
                    location.href = N2M_CTX + "/qna/pageDetail.do?qnaOid="+row.qnaOid;
                },
                onSortChange: function(column){
                    vm.pageInfo.sortField=column.prop;
                    vm.pageInfo.sortOrder=column.order;
                    
                    vm.keepPageInfo(function(curPage){
                        vm.getQnaList(curPage);
                    });
                }
            }
        });

        vm.keepPageInfo(function(curPage){
            if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
                vm.getQnaList(curPage);
            }
        });
    });
</script>
