<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

<div id="content" v-cloak>
<!-- <n2m-chart :type="'test'"></n2m-chart> -->
</div>

<script>
$(function(){
	Vue.use(N2MChart);
	vm = new Vue({
		el: '#content',
		data: function(){
			return {
			}
		},
		mounted: function() {
			alert("해당 사용자에게는 메뉴권한이 없습니다.");
		},
		methods: {
			
		}
	});
});

</script>

