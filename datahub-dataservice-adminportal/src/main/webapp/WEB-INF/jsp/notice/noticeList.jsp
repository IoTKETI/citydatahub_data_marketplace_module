<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
    <h3 class="content__title">공지사항목록</h3>
    <form>
      <fieldset>
        <legend>필드셋 제목</legend>
        <section class="section">
          <div class="section__header">
            <h4 class="section__title">공지사항 검색</h4>
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
                      <option value="writer">등록자</option>
                    </select>
                    <input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue" @keypress.enter.prevent="getNoticeList(1)"/>
                  </td>
                  <th>등록일</th>
                  <td>
                  <div class="picker__group">
                  	<label class="label__picker"><input id="fromDate" type="text" class="input__picker" v-model="pageInfo.fromDate"></label><span class="picker__period">~</span><label class="label__picker"><input id="toDate" type="text" class="input__picker" v-model="pageInfo.toDate"></label>
                  </div>
                  </td>
                </tr>
                <tr>
                  <th>팝업공지</th>
                  <td>
                    <select class="select select--full" v-model="pageInfo.schCondition3">
                      <option value="">전체</option>
                      <option value="Y">예</option>
                      <option value="N">아니오</option>
                    </select>
                  </td>
                  <!-- <th>우선공지</th>
                  <td>
                    <select class="select select--full" v-model="pageInfo.schCondition2">
                      <option value="">전체</option>
                      <option value="Y">예</option>
                      <option value="N">아니오</option>
                    </select>
                  </td> -->
                </tr>
              </tbody>
            </table>
          </div>
          <div class="button__group">
            <button class="button__primary" type="button" @click="getNoticeList(1)">검색</button>
          </div>
        </section>
      </fieldset>
    </form>
    
    <section class="section">
        <div class="section__header">
            <h4 class="section__title">공지사항 목록</h4>
        </div>
        <div class="section__content">
        <el-table :data="list" border style="width:100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="onRowClick" :row-style="priorityNotice" @sort-change="onSortChange" :default-sort = "{prop: pageInfo.sortField, order: pageInfo.sortOrder}">
            <el-table-column prop="noticeId" label="번호" width="120" sortable="custom">
                <template slot-scope="scope">
                    {{ scope.row.noticeFirstFl == 'N' ? scope.row.noticeId : '우선공지' }}
                </template>
            </el-table-column>
            <el-table-column prop="noticeTitle" label="제목" align="left" sortable="custom"></el-table-column>
<!--             <el-table-column prop="noticeCreUsrNm" label="등록자ID" width="160" sortable="custom"></el-table-column> -->
            <el-table-column prop="noticeFirstFl" label="우선공지" width="120">
                <template slot-scope="scope">
                    {{ scope.row.noticeFirstFl | flag }}
                </template>
            </el-table-column>
            <el-table-column prop="noticeMainPopFl" label="팝업공지" width="120" sortable="custom">
                <template slot-scope="scope">
                    {{scope.row.noticeMainPopFl | flag}}
                </template>
            </el-table-column>
            <el-table-column prop="noticeCreDt" label="등록일시" width="240" sortable="custom">
                <template slot-scope="scope">
                    {{scope.row.noticeCreDt | date}}
                </template>
            </el-table-column>
            <el-table-column prop="fileCnt" label="파일" width="80" sortable="custom">
                <template slot-scope="scope">
                     <i class="icon__file" v-if="scope.row.fileCnt >= 1"></i>
                </template>
            </el-table-column>
        </el-table>
        <n2m-pagination :pager="pageInfo" @pagemove="getNoticeList"/>
      </div> 
    </section>
    <div class="button__group">
        <c:if test="${writeYn eq 'Y'}">
            <button class="button__primary" type="button" onclick="location.href='<c:url value="/notice/pageEdit.do"/>'">등록</button>
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
                    loading: true,
                }
            },
            methods : {
            	priorityNotice: function(obj){
                    return obj.row.noticeFirstFl == 'Y' ? 'background-color: #fff5ee;' : '';
                },
                getNoticeList : function(curPage) {
                    vm.loading = true;
                    vm.pageInfo.curPage = curPage;
                    var request = $.ajax({
                        url : N2M_CTX + "/notice/getList.do",
                        method : "GET",
                        dataType : "json",
                        data : {
                            schCondition : vm.pageInfo.schCondition, // 검색어
                            schValue     : vm.pageInfo.schValue,
                            schCondition3 : vm.pageInfo.schCondition3,
                            fromDate : vm.pageInfo.fromDate,
                            toDate : vm.pageInfo.toDate,
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
                onRowClick : function(row){
                    	location.href = N2M_CTX + "/notice/pageDetail.do?noticeOid="+row.noticeOid;
                },
                onSortChange: function(column){
                    vm.pageInfo.sortField=column.prop;
                    vm.pageInfo.sortOrder=column.order;
  
                    vm.keepPageInfo(function(curPage){
                    	vm.getNoticeList(curPage);
                    });
                }
            }
        });
        
        vm.keepPageInfo(function(curPage){
            if(!vm.pageInfo.sortField && !vm.pageInfo.sortOrder){
                vm.getNoticeList(curPage);
            }
        });
    });
</script>