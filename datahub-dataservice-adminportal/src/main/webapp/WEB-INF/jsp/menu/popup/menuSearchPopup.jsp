<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :style="menuSearchPopupVisible ? 'display:block;':'display:none;'">
	<div class="modal__wrap">
		<div class="modal__content w-650">
			<div class="modal__header">
				<h4 class="modal__title">메뉴 선택 팝업</h4>
				<button class="modal__button--close" type="button" @click="closeMenuSearchPopup"><span class="hidden">모달 닫기</span></button>
			</div>
			<div class="modal__body">
		        <div class="modal__box">
					<ul id="menutree"></ul>
		        </div>
			</div>
			<div class="modal__footer">
				<button class="button__primary" type="button" @click="selectMenu">선택</button>
				<button class="button__secondary" type="button" @click="closeMenuSearchPopup">닫기</button>
			</div>
		</div>
	</div>
</div>
<script>
var menuSearchPopupMixin={
	data: function(){
		return {
			menuSearchPopupVisible: false,
			selectedMenu: {},
			selectMenuCallback: function(){},
		}
	},
	mounted: function(){
		var request = $.ajax({
			method: "GET",
			url: N2M_CTX + "/menu/getList.do",
			dataType: "json"
		});
		request.done(function(data){
			var rows = data.list.map(function(row){ 
				return { 
					id: row.menuOid , 
					parentId: row.menuParentId , 
					menuNm: row.menuNm
				}
			});
			$("#menutree").fancytree({
				source: convert(rows),
				activate: function(event, data){
					var node = data.node;
	 				vm.selectedMenu = node.data;
					
				}
			});
		});


	},
	methods: {
		selectMenu: function(){
			vm.selectMenuCallback(vm.selectedMenu);
			vm.closeMenuSearchPopup();
		},
		openMenuSearchPopup: function(callback){
			vm.selectMenuCallback = callback;
			vm.menuSearchPopupVisible = true;
		},
		closeMenuSearchPopup: function(){
			vm.menuSearchPopupVisible = false;
		}
	}
}
function convert(rows){
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
				id           : row.id,
				text         : row.menuNm
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
					id           : row.id,
					text         : row.menuNm
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
</script>