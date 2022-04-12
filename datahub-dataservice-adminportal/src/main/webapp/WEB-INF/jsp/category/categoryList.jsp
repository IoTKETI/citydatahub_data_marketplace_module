<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
    <h3 class="content__title">카테고리목록</h3>
    <section class="section">
        <div class="section__header">
            <h4 class="section__title">카테고리 목록</h4>
        </div>
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
                        <td></td>
                        <td></td>
                        <td></td>
                    </tr>
                </tbody>
              </table>
		</div>
    </section>
    <div class="button__group">
        <c:if test="${writeYn eq 'Y'}">
            <button class="button__primary" type="button" onclick="location.href='<c:url value="/category/pageEdit.do"/>'">등록</button>
        </c:if>
    </div>
</div>
<script>
  $(function(){
      Vue.use(N2MPagination);
      vm = new Vue({
          el: '#content',
          data: function(){
              return {
                    categoryList : [],
                    popupVisible: false,
                    checkedCategory:[],
                    src: function(id){
                    	return N2M_CTX + "/category/downloadFile.do?categoryOid="+id;
                    }
                }
            },
            methods: {
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
//                         vm.categoryList = vm.convert(rows);
                        
                    	$("#treegrid").fancytree({
                            extensions: ["table"],
                            table: {
                                indentation: 20,      // indent 20px per node level
                            },
                            source: vm.convert(rows),
                            activate: function(event, data){
                                var node = data.node;
                                location.href=N2M_CTX + '/category/pageDetail.do?categoryOid='+node.key;
                            },
                            renderColumns: function(event, data) {
                                var node = data.node,
                                $tdList = $(node.tr).find(">td");
                                // (index #0 is rendered by fancytree by adding the checkbox)
                                $tdList.eq(1).text(node.data.desc);
                                // (index #2 is rendered by fancytree)
                                $tdList.eq(2).text(node.data.writer);
                                // Rendered by row template:
//                              $tdList.eq(4).html("<input type='checkbox' name='like' value='" + node.key + "'>");
                            }
                        });
                    	
                    });
                },
                onRowClick : function(row){
                	location.href=N2M_CTX + '/category/pageDetail.do?categoryOid='+row.id;
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
                        
                    var toDo = [];
                    for(var i=0; i<nodes.length; i++){
                        toDo.push(nodes[i]);
                    }
                    while(toDo.length){
                        var node = toDo.shift(); // the parent node
                        // get the children nodes
                        for(var i=0; i<rows.length; i++){
                            var row = rows[i];
                            if (row.parentId == node.key){
                                var child = {
                               		key : row.id,
                                    title : row.name,
                                    desc : row.desc,
                                    writer : row.writer
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
        })
        vm.getCategoryList();
    });
</script>
