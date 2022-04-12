<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :class="categorySearchPopupVisible ? 'js-modal-show':''">
  <div class="modal__outer">
	<div class="modal__inner">
	  <div class="modal__header">
	    <h3 class="hidden">카테고리 선택 팝업</h3>
	    <button class="modal__button--close js-modal-close material-icons" type="button" @click="closeCategorySearchPopup"><span class="hidden">모달 닫기</span></button>
	  </div>
	  <div class="modal__body">
	    <ul id="categorytree"></ul>
	  </div>
	  <div class="modal__footer">
	    <div class="button__group">
          <a class="button button__secondary" href="#none" @click="selectCategory">선택</a>
          <a class="button button__outline--secondary js-modal-close" href="#none" @click="closeCategorySearchPopup">닫기</a>
        </div>
      </div>
	</div>
  </div>
</div>

<script>
var categorySearchPopupMixin={
	data: function(){
		return {
			categorySearchPopupVisible: false,
			selectedCategory: {},
			selectCategoryCallback: function(){},
		}
	},
	mounted: function(){
		var request = $.ajax({
			method: "GET",
			url: SMC_CTX + "/dataset/getCategoryList.do",
			dataType: "json"
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
			
			$("#categorytree").fancytree({
				source: convert(rows),
				activate: function(event, data){
					var node = data.node;
	 				vm.selectedCategory = node.data;
				}
			});
		}); 


	},
	methods: {
		selectCategory: function(){
			vm.selectCategoryCallback(vm.selectedCategory);
			vm.closeCategorySearchPopup();
		},
		openCategorySearchPopup: function(callback){
			vm.selectCategoryCallback = callback;
			vm.categorySearchPopupVisible = true;
			$("html").addClass("is-scroll-blocking");
		},
		closeCategorySearchPopup: function(){
			vm.categorySearchPopupVisible = false;
			$("html").removeClass("is-scroll-blocking");
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
				key     : row.id,
				title   : row.name,
				id      : row.id,
                text    : row.name,
				desc    : row.desc,
				writer  : row.writer
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
					key     : row.id,
                    title   : row.name,
                    id      : row.id,
                    text    : node.text + ' > ' + row.name,
                    desc    : row.desc,
                    writer  : row.writer
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