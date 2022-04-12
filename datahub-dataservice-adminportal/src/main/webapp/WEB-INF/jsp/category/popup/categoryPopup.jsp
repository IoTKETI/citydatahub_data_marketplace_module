<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :style="categoryPopupVisible ? 'display:block;':'display:none;'">
	<div class="modal__wrap">
		<div class="modal__content w-650">
			<div class="modal__header">
				<h4 class="modal__title">카테고리 선택 팝업</h4>
				<button class="modal__button--close" type="button" @click="closeCategoryPopup"><span class="hidden">모달 닫기</span></button>
			</div>
<!-- 	        <div class="section__content"> -->
<!-- 	            <el-table :data="categoryList" style="width:100%;" row-key="id" border default-expand-all empty-text="데이터가 존재하지 않습니다." @row-click="selectCategory"> -->
<!-- 	              <el-table-column prop="text" label="카테고리 목록" align="left"></el-table-column> -->
<!-- 	            </el-table> -->
<!-- 	        </div> -->
			<div class="modal__body">
				<div class="section">
			        <div class="section__content">
			            <table id="treegrid">
			              <colgroup>
			                  <col width="*"></col>
			                  <col width="*"></col>
			                  <col width="120px"></col>
			              </colgroup>
			              <thead>
			                  <tr>
			                      <th>카테고리명</th>
			                      <th>설명</th>
			                      <th>등록자</th>
			                  </tr>
			              </thead>
			              <!-- Optionally define a row that serves as template, when new nodes are created: -->
			              <tbody>
			                  <tr>
			                      <td class="ta-l"></td>
			                      <td></td>
			                      <td style="text-align: center;"></td>
			                  </tr>
			              </tbody>
			            </table>
			        </div>
				</div>	
			</div>	
			<div class="modal__footer">
				<button class="button__primary" type="button" @click="selectCategory">선택</button>
				<button class="button__secondary" type="button" @click="closeCategoryPopup">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
var codePopupMixin={
	data: function(){
		return {
			categoryList : [],
			categoryPopupVisible: false,
			checkedCategory:[]
		}
	},
    methods: {
    	selectCategory: function(row){
            vm.category.categoryParentOid = row.key;
            vm.category.categoryParentNm = row.title;
            vm.closeCategoryPopup();
        },
        closeCategoryPopup: function(){
            vm.categoryPopupVisible = false;
        },
        getCategoryList: function(){
            var request = $.ajax({
                url: N2M_CTX + "/category/getList.do",
                method: "GET",
                dataType: "json",
                data: vm.categoryList,
                timeout: 3000
            });
            request.done(function(data){
                var rows = data.list.map(function(row){
                    return { 
                        id: row.categoryOid, 
                        parentId: row.categoryParentOid, 
                        name: row.categoryNm, 
                        desc: row.categoryDesc, 
                        writer: row.categoryCreUsrNm
                    } 
                });
                $("#treegrid").fancytree({
                    extensions: ["table"],
                    table: {
                        indentation: 20,      // indent 20px per node level
                    },
                    source: vm.convert(rows),
                    activate: function(event, data){
                        var node = data.node;
                        
                        vm.selectCategory(node);
                        
                    },
                    renderColumns: function(event, data) {
                        var node = data.node,
                        $tdList = $(node.tr).find(">td");
                        // (index #0 is rendered by fancytree by adding the checkbox)
                        $tdList.eq(1).text(node.data.desc);
                        // (index #2 is rendered by fancytree)
                        $tdList.eq(2).text(node.data.writer);
                        // Rendered by row template:
                    }
                });
            });
        },
        openCategoryListPopup: function(){
        	vm.categoryPopupVisible = true;
        	vm.getCategoryList();
        },
        convert : function(rows){
            function exists(rows, parentId){
                for(var i=0; i<rows.length; i++){
                    if (rows[i].id == parentId) return true;
                }
                return false;
            }
            
            var nodes = [];
                
            for(var i=0; i<rows.length; i++){
                var row = rows[i];
                if (!exists(rows, row.parentId)){
                    nodes.push({
                        key : row.id,
                        title : row.name,
                        desc : row.desc,
                        writer : row.writer
                    });
                }
            }
            
            return nodes;
        }
    }
}
</script>