<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal modal__program-search" :style="programSearchPopupVisible ? 'display:block;':'display:none;'">
	<div class="modal__wrap">
		<div class="modal__content">
			<div class="modal__header">
				<h4 class="modal__title">프로그램 그룹 선택 팝업</h4>
				<button class="modal__button--close" type="button" @click="closeProgramSearchPopup"><span class="hidden">모달 닫기</span></button>
			</div>
			<div class="modal__body">
				<section class="section">
					<div class="section__header">
						<h4 class="section__title">프로그램그룹 검색</h4>
					</div>
					<div class="section__content">
						<table class="table--row">
							<caption>테이블 제목</caption>
							<colgroup>
								<col style="width:150px">
								<col style="width:auto">
							</colgroup>
							<tbody>
								<tr>
									<th>검색어</th>
									<td>
										<select class="select" v-model="programGroupPageInfo.schCondition">
											<option disabled value="">-선택-</option>
											<option value="programGroupNm">프로그램그룹명</option>
										</select>
										<input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="programGroupPageInfo.schValue" @keypress.enter.prevent="getProgramGroupList(1)">
									</td>
								</tr>
							</tbody>
						</table>
					</div>
					<div class="button__group">
						<button class="button__primary" type="button" @click="getProgramGroupList(1)">조회</button>
					</div>
				</section>
				<section class="section">
					<div class="section__header">
						<h4 class="section__title">프로그램 그룹목록</h4>
					</div>
					<div class="section__content">
						<el-table :data="programGroupList" border empty-text="검색 결과가 존재하지 않습니다." v-loading="programGroupLoading" @row-click="onRowClick" @sort-change="onSortChange" highlight-current-row>
							<el-table-column prop="rownum" label="번호" width="60" sortable="custom"> </el-table-column>
							<el-table-column prop="programgroupNm" label="프로그램그룹명" align="left" sortable="custom"></el-table-column>
							<el-table-column prop="programgroupDesc" label="프로그램그룹 설명" align="left" sortable="custom"></el-table-column>
							<el-table-column prop="programgroupCreDt" label="등록일시" width="160" sortable="custom">
							  <template slot-scope="scope">
			                    {{ scope.row.programgroupCreDt | date }}
			                  </template>
							</el-table-column>
						</el-table>
						<n2m-pagination :pager="programGroupPageInfo" @pagemove="getProgramGroupList"/>
					</div>
				</section>
				<section class="section">
					<div class="section__header">
						<h4 class="section__title">프로그램 목록{{selectedProgram.text ? ' ['+selectedProgram.text + ']' : ''}}</h4>
					</div>
					<div class="section__content">
						<el-table :data="programList" border empty-text="검색 결과가 존재하지 않습니다." v-loading="programLoading">
							<el-table-column prop="rownum" label="번호" width="60"></el-table-column>
							<el-table-column prop="programDivisionNm" label="구분" width="60"></el-table-column>
							<el-table-column prop="programNm" label="프로그램 명" align="left"></el-table-column>
							<el-table-column prop="programUrl" label="프로그램 주소" align="left"></el-table-column>
							<el-table-column prop="programCreDt" label="등록일시" width="160">
							  <template slot-scope="scope">
			                    {{ scope.row.programCreDt | date }}
			                  </template>
							</el-table-column>
						</el-table>
						<n2m-pagination :pager="programPageInfo" @pagemove="getProgramList"/>
					</div>
				</section>
			</div>
			<div class="modal__footer">
				<button class="button__primary" type="button" @click="selectProgram">선택</button>
				<button class="button__secondary" type="button" @click="closeProgramSearchPopup">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
var programSearchPopupMixin={
	data: function(){
		return {
			programSearchPopupVisible: false,
			programGroupLoading: false,
			programLoading: false,
			programGroupOid: "",
			programGroupList: [],
			programList: [],
			selectedProgram: {},
			selectProgramCallback: function(){},
			programGroupPageInfo: {
				curPage: 1,
				pageListSize: 5,
				schValue: "",
				schCondition: "",
				sortField: "",
				sortOrder: ""
			},
			programPageInfo: {
				curPage: 1,
				pageListSize: 5,
			}
		}
	},
	methods: {
		getProgramGroupList : function(curPage) {
			vm.programGroupLoading = true;
			var request = $.ajax({
				url : N2M_CTX + "/programgroup/getList.do",
				method : "GET",
				dataType : "json",
				data : {
					schCondition : vm.programGroupPageInfo.schCondition,
					schValue	 : vm.programGroupPageInfo.schValue,
					pageListSize : vm.programGroupPageInfo.pageListSize,
					curPage	     : curPage,
					sortField    : vm.programGroupPageInfo.sortField,
					sortOrder    : vm.programGroupPageInfo.sortOrder,
				}
			});
			request.done(function(data) {
				vm.programGroupLoading = false;
				vm.programGroupList = data.list;
				vm.programGroupPageInfo= data.page;
				vm.selectedProgram = {};
				vm.getProgramList();
			});
			request.fail(function(data){
				vm.programGroupLoading = false;
			});
		},
		getProgramList: function(curPage){
			vm.programLoading = true;
			var request = $.ajax({
				url: N2M_CTX + "/programgroup/program/getList.do",
				method: "GET",
				dataType: "json",
				data: {
					curPage: curPage,
					pageListSize : vm.programPageInfo.pageListSize,
					programgroupOid: vm.selectedProgram.id
				}
			});
			request.done(function(data){
				vm.programLoading = false;
				vm.programList = data.list;
				vm.programPageInfo = data.page;
			});
			request.fail(function(){
				vm.programLoading = false;
			});
		},
		onRowClick: function(row){
			vm.selectedProgram.id = row.programgroupOid;
			vm.selectedProgram.text = row.programgroupNm;
			vm.getProgramList(1);
		},
		onSortChange: function(column){
			vm.programGroupPageInfo.sortField=column.prop;
			vm.programGroupPageInfo.sortOrder=column.order;
			vm.getProgramGroupList(1);
		},
		selectProgram: function(){
			vm.selectProgramCallback(vm.selectedProgram);
			vm.closeProgramSearchPopup();
		},
		openProgramSearchPopup: function(callback){
			vm.selectProgramCallback = callback;
			vm.getProgramGroupList(1);
			vm.programSearchPopupVisible = true;
		},
		closeProgramSearchPopup: function(){
			vm.programSearchPopupVisible = false;
		}
	}
}
</script>