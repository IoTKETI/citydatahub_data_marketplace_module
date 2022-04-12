<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
${gnbHtml}
<hr>

<script>
$(function(){
	var ids = [];
	<c:forEach items="${navigation}" var="nav">
	   ids.push("${nav.menuId}");
	</c:forEach>
	
	for (var idx=0; idx < ids.length; idx++) {
		var $menu = $("#"+ids[idx]);
	    
		if ($menu.length <= 0) continue;
		
		if (idx == 0) {
			$menu.addClass("is-current");
		} else {
			$menu.addClass("is-active");
		}
	}
	
});
</script>