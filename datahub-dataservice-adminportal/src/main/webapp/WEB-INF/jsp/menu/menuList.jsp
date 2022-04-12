<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
	<h3 class="content__title">메뉴목록</h3>
	<section class="section">
		<div class="section__header">
			<h4 class="section__title">메뉴 목록</h4>
		</div>
		<div class="section__content">
			<table id="treegrid">
				<colgroup>
					<col width="350px"></col>
					<col width="150px"></col>
					<col width="200px"></col>
					<col width="*"></col>
				</colgroup>
				<thead>
					<tr>
						<th>메뉴명</th>
						<th>메뉴ID</th>
						<th>프로그램그룹명</th>
						<th>URL</th>
					</tr>
				</thead>
				<!-- Optionally define a row that serves as template, when new nodes are created: -->
				<tbody>
					<tr>
						<td></td>
						<td style="text-align: center;"></td>
						<td style="text-align: center;"></td>
						<td></td>
					</tr>
				</tbody>
			  </table>
		</div>
	</section>
	<div class="button__group">
		<c:if test="${writeYn eq 'Y'}">
			<button class="button__primary" type="button" onclick="location.href='<c:url value="/menu/pageEdit.do"/>'">등록</button>
		</c:if>
	</div>
</div>
<script>
$(function(){
	vm = new Vue({
		el: '#content',
		data: function(){
			return {
				list: [],
				loading: true
			}
		},
		methods: {
			getMenuList: function(){
				var request = $.ajax({
					url: N2M_CTX + "/menu/getList.do",
					method: "GET",
					dataType: "json",
				});
				request.done(function(data){
					vm.loading = false;
					var rows = data.list.map(function(row){ 
						return { 
							id		   : row.menuOid,
							parentId	  : row.menuParentId,
							menuNm		: row.menuNm,
							menuId		: row.menuId,
							programGrpNm  : row.programGrpNm,
							menuUrl	   : row.menuUrl,
							
						} 
					});

					$("#treegrid").fancytree({
						extensions: ["table"],
						table: {
							indentation: 20,	  // indent 20px per node level
						},
						source: vm.convert(rows),
						activate: function(event, data){
							var node = data.node;
							location.href=N2M_CTX + '/menu/pageDetail.do?menuOid='+node.key;
							
						},
						renderColumns: function(event, data) {
							var node = data.node,
							$tdList = $(node.tr).find(">td");
							// (index #0 is rendered by fancytree by adding the checkbox)
							$tdList.eq(1).text(node.data.menuId);
							$tdList.eq(2).text(node.data.programGrpNm);
							// (index #2 is rendered by fancytree)
							$tdList.eq(3).text(node.data.menuUrl);
							// Rendered by row template:
//							$tdList.eq(4).html("<input type='checkbox' name='like' value='" + node.key + "'>");
						}
					});
				});
				request.fail(function(data){
					vm.loading = false;
				});

			},
			convert: function (rows){
				function exists(rows, parentId){
					for(var i=0; i<rows.length; i++){
						if (rows[i].id == parentId) return true;
					}
					return false;
				}
				
				var nodes = [];
				// get the top level nodes
				for(var i=0; i<rows.length; i++){
					var row = rows[i];
					if (!exists(rows, row.parentId)){
						nodes.push({
							key          : row.id,
							title        : row.menuNm,
							menuId       : row.menuId,
							programGrpNm : row.programGrpNm,
							menuUrl      : row.menuUrl,
						});
					}
				}
				
				var toDo = [];
				for(var i=0; i<nodes.length; i++){
					toDo.push(nodes[i]);
				}
				while(toDo.length){
					var node = toDo.shift();		// the parent node
					// get the children nodes
					for(var i=0; i<rows.length; i++){
						var row = rows[i];
						if (row.parentId == node.key){
							var child = {
								key		     : row.id,
								title		 : row.menuNm,
								menuId		 : row.menuId,
								programGrpNm : row.programGrpNm,
								menuUrl      : row.menuUrl,
							};
							if (node.children){
									node.children.push(child);
							} else {
									node.children = [child];
							}

							toDo.push(child);
							node.folder = true;
							node.expanded = true;
						}
					}
				}
				return nodes;
			}
		}
	});

	vm.getMenuList();
});
</script>