<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<script src="<c:url value="/smartcity/js/lib/jquery-3.4.1.min.js"/>"></script>
<script>
var SMC_CTX = "${request.pageContext.contextPath}";
function onClick(code){
	switch(code){
	case "PAY_COMPLETE":
		var request = $.ajax({
			method: "PATCH",
			url: SMC_CTX + "/dataset/pay_proc.do",
			contentType: "application/json",
			data: JSON.stringify({
				id        : "${param.id}",
				datasetId : "${param.datasetId}",
				requestId : "${param.requestId}",
				payStatus : "pay_complete",
			})
		});
		request.done(function(){
			alert("결제가 완료되었습니다.");
			if($.isFunction(self.callback)){
				self.callback();
			}
			self.close();
		});
		request.fail(function(e){
			console.log(e);
		});
		break;
	case "PAY_ERROR":
		var request = $.ajax({
			method: "PATCH",
			url: SMC_CTX + "/dataset/pay_proc.do",
			contentType: "application/json",
			data: JSON.stringify({
				id        : "${param.id}",
				datasetId : "${param.datasetId}",
				requestId : "${param.requestId}",
				payStatus : "pay_error",
			})
		});
		request.done(function(){
			alert("결제가 취소되었습니다.");
			if($.isFunction(self.callback)){
				self.callback();
			}
			self.close();
		});
		request.fail(function(e){
			console.log(e);
		});
		break;
	}
}
</script>
<button onclick="onClick('PAY_COMPLETE')">결제완료</button>
<button onclick="onClick('PAY_ERROR')">취소</button>
