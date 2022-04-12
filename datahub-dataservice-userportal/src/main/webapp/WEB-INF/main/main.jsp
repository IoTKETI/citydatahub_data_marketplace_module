<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>

메인화면
<div id="content" v-cloak>
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
		methods: {
			
		}
	});
});

</script>

