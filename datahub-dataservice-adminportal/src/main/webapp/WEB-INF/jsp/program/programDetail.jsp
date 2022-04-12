<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">프로그램그룹 상세</h3>
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
						    <tr>
                                <th>번호</th>
                                <td colspan="3">
                                    {{programGroup.programgroupId}}
                                </td>
                            </tr>
							<tr>
								<th>등록자</th>
								<td colspan="3">
									{{programGroup.programgroupCreUsrNm}}
								</td>
							</tr>
							<tr>
								<th>프로그램 그룹명</th>
								<td colspan="3">
									{{programGroup.programgroupNm}}
								</td>
							</tr>
							<tr>
								<th>프로그램그룹 설명</th>
								<td colspan="3" v-html="$options.filters.enter(programGroup.programgroupDesc)">
								</td>
							</tr>
							<tr>
								<th>등록일시</th>
								<td colspan="3">
									{{programGroup.programgroupCreDt | date}}
								</td>
							</tr>
							<tr>
                                <th>사용여부</th>
                                <td colspan="3">
                                    <input class="input__checkbox" id="checkboxUseFl" type="checkbox" v-model="programGroup.programgroupUseFl" true-value="Y" false-value="N" disabled/>
                                    <label class="label__checkbox" for="checkboxUseFl"></label>
                                </td>
                            </tr>
						</tbody>
					</table>
				</div>
			</section>
			<!-- //section-default -->
			<div class="button__group">
			    <c:if test="${modifiedYn eq 'Y'}">
				    <button class="button__primary" type="button"  onclick="location.href='<c:url value="/programgroup/pageModify.do?programGroupOid=${param.programGroupOid}"/>'">수정</button>
				</c:if>
				<button class="button__secondary" type="button" onclick="location.href='<c:url value="/programgroup/pageList.do"/>'">목록</button>
			</div>
		</fieldset>
	</form>
	
	<section class="section">
        <div class="section__header">
            <h4 class="section__title">프로그램 목록</h4>
        </div>
        <div class="section__content">
      
        <el-table :data="programGroup.programList" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다." v-loading="loading" @row-click="openProgramPopup">
            <el-table-column prop="rownum" label="번호" width="160"></el-table-column>
            <el-table-column prop="programDivisionNm" label="구분" width="120"></el-table-column>
            <el-table-column prop="programNm" label="프로그램 명" align="left"></el-table-column>
            <el-table-column prop="programUrl" label="프로그램 주소" align="left"></el-table-column>
            <el-table-column prop="programUseFl" label="사용여부" width="120">
                <template slot-scope="scope">
                    {{ scope.row.programUseFl | flag }}
                </template>
            </el-table-column>
            <el-table-column prop="programUseTokenYn" label="인가여부" width="120">
                <template slot-scope="scope">
                    {{ scope.row.programUseTokenYn | flag }}
                </template>
            </el-table-column>
            <el-table-column prop="programCreDt" label="등록일시" width="240">
                <template slot-scope="scope">
                    {{ scope.row.programCreDt | date }}
                </template>
            </el-table-column>
        </el-table>
        <n2m-pagination :pager="pageInfo" @pagemove="getProgramList"/>
      </div>
    </section>
    <div class="button__group">
        <c:if test="${writeYn eq 'Y'}">
            <button class="button__primary" type="button" @click="openProgramPopup">등록</button>
        </c:if>
    </div>
    
    <!-- 코드 등록 팝업 -->
    <jsp:include page="/WEB-INF/jsp/program/popup/programPopup.jsp"/>
</div>

<script>
$(function() {
	Vue.use(N2MPagination);
	vm = new Vue({
		el : '#content',
		mixins: [programRegistPopupMixin],
		data : function() {
			return {
				programGroup : {
				    programGroupOid: "${param.programGroupOid}",
				    programList: []
				},
                loading: true
			}
		},
		methods : {
			getProgramGroup : function() {
				var request = $.ajax({
					method : "GET",
					url: N2M_CTX + "/programgroup/get.do",
					data: {
						programgroupOid : "${param.programGroupOid}"
					},
					dataType: "json"
				});
				request.done(function(data) {
					vm.loading = false;
					vm.programGroup = data;
					vm.getProgramList(1);
				});
				request.fail(function(data){
                    vm.loading = false;
                });
			},
			getProgramList: function(curPage){
				var request = $.ajax({
                    url: N2M_CTX + "/programgroup/program/getList.do",
                    method: "GET",
                    dataType: "json",
                    data: {
                        curPage: curPage,
                        programgroupOid: "${param.programGroupOid}"
                    }
                });
                request.done(function(data){
                    vm.programGroup.programList = data.list;
                    vm.pageInfo = data.page;
                });
                vm.programList = [];
			},
			openProgramPopup: function(programInfo){
	            vm.openProgramRegistPopup(programInfo, vm.programGroup.programGroupOid, function(){
	            	vm.getProgramList(1);
	            });
	        }
		}
	});
	vm.getProgramGroup();
});
</script>
