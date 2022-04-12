<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- CSS -->
<link rel="stylesheet" href="<c:url value="/smartcity/css/lib/slick.css"/>">
<link rel="stylesheet" href="<c:url value="/smartcity/css/lib/swiper.min.css"/>">
<link rel="stylesheet" href="<c:url value="/smartcity/css/lib/jquery-ui.css"/>">
<link rel="stylesheet" href="<c:url value="/smartcity/css/lib/jquery-ui-timepicker-addon.css"/>"/>
<link rel="stylesheet" href="<c:url value="/smartcity/css/lib/tippy.css"/>">
<link rel="stylesheet" href="<c:url value="/smartcity/css/sub.css"/>">
<link rel="stylesheet" href="<c:url value="/smartcity/plugins/fancytree/skin-win7/ui.fancytree.min.css"/>"/>

<script src="<c:url value="/smartcity/js/lib/jquery-3.4.1.min.js"/>"></script>
<script src="<c:url value="/smartcity/js/lib/jquery-ui.min.js"/>"></script>
<script src="<c:url value="/smartcity/js/lib/jquery-ui-timepicker-addon.js"/>"></script>
<script src="<c:url value="/smartcity/js/lib/swiper.min.js"/>"></script>
<script src="<c:url value="/smartcity/js/lib/popper.js"/>"></script>
<script src="<c:url value="/smartcity/js/lib/tippy.js"/>"></script>
<script src="<c:url value="/smartcity/js/ui.js"/>"></script>


<!-- VUE.JS -->
<script src="<c:url value="/smartcity/js/lib/vue.js"/>"></script>
  
<!-- MOMENT.JS -->
<script src="<c:url value="/smartcity/js/lib/moment.js"/>"></script>

<!-- VALIDATE.JS -->
<script src="<c:url value="/smartcity/js/lib/validate.js"/>"></script>

<!-- FANCYTREE -->
<script src="<c:url value="/smartcity/plugins/fancytree/jquery.fancytree-all-deps.js"/>"></script>

<!-- NAVER EDITOR 2.10 -->
<script src="<c:url value="/smartcity/plugins/navereditor2/js/service/HuskyEZCreator.js"/>"></script>

<!-- ECHARTS -->
<script src="<c:url value="/smartcity/js/lib/echarts.js"/>"></script>

<!-- COMMON -->
<script src="<c:url value="/smartcity/js/common/message.js"/>"></script>
<script src="<c:url value="/smartcity/js/common/custom_valid.js"/>"></script>
<script src="<c:url value="/smartcity/js/common/modal.js"/>"></script>

<!-- VUE COMPONENT -->
<script src="<c:url value="/smartcity/js/common/vue-component/vue-filter.js"/>"></script>
<script src="<c:url value="/smartcity/js/common/vue-component/vue-pagination.js"/>"></script>
<script src="<c:url value="/smartcity/js/common/vue-component/vue-file-uploader.js"/>"></script>
<script src="<c:url value="/smartcity/js/common/vue-component/vue-editor.js"/>"></script>
<script>
var SMC_CTX="${pageContext.request.contextPath}";

var __init__flag = false;
var N2M_UI;
var vm;

$.ajaxSetup({
    cache: false,
    error: function(res){
    	if(res.status == 401){
            location.href = SMC_CTX + "/login/oauthlogin.do";
        }else if(res.status == 500){
        	var detailMessage = res.responseJSON.detail;
        	
        	if (detailMessage) {
        		alert(detailMessage);
        	} else {
        		alert("내부 처리 중 오류가 발생하였습니다.");	
        	}
        }
    }
});

Vue.mixin({
	created: function(){
		
	},
	mounted: function(){
		if(!__init__flag){
			$("#fromDate").datepicker({
				dateFormat: 'yy-mm-dd',
				onSelect: function(date){
					$("#toDate").datepicker('option', 'minDate', new Date(date));
					vm.pageInfo.fromDate = date; 
				}
			});
			$("#toDate").datepicker({
				dateFormat: 'yy-mm-dd',
				onSelect: function(date){
					$("#fromDate").datepicker('option', 'maxDate', new Date(date));
					vm.pageInfo.toDate = date;
				}
			});
			
			//N2M UI 스크립트 초기화
			N2M_UI = N2M.ui();
			//첫 페이지 로딩시 툴팁 초기화
			tippy(".tooltip", {
				placement: 'right',
				animation: 'scale',
				inertia: true,
				theme: 'gradient',
			});
			
			__init__flag = true;
		}
	},
	methods: {
		keepPageInfo: function(callback){
			if($.isFunction(callback)){
				callback(vm.pageInfo.curPage);
			}
		},
		fileDownload: function(oid, fileOid){
			if(!fileOid) return "";
			return SMC_CTX + "/downloadFile.do?oid="+oid+"&fileOid="+fileOid;
		},
		fileDownloadMain: function(fileOid, type , menuNm){
			if(!fileOid) return "";
			return SMC_CTX + "/downloadFile.do?type="+type+"&menu="+menuNm+"&oid="+fileOid;
		},
		logout: function() {
			var request = $.ajax({
                url : SMC_CTX + "/login/logout.do",
                method : "GET",
                contentType : "application/text"
            });
            request.done(function(data) {
                window.location.href = SMC_CTX + '/main/pageMain.do';
            });
		},
		goTotalSearchList: function() {
			if (!vm.totalSch.trim()) {
				alert("검색어를 입력해주세요.");
				
				return;
			}
			
			location.href = SMC_CTX + "/search/pageList.do?totalSch=" + vm.totalSch;
		},
		totalSchKeyUp: function() {
            var regExp = /[\{\}\[\]\/?.,;:|\)*~`!^\-_+<>@\#$%&\\\=\(\'\"]/gi;
            
            if (regExp.test(vm.totalSch)) vm.totalSch = vm.totalSch.replace(regExp, "");
        }
	},
	data: function(){
		return {
			loginUserId: "${sessionScope.userInfo.userId}",
			loadingFlag: false,
			totalSch: "",
			pageInfo:{
				curPage: "${searchInfo != null ? searchInfo.curPage : 1}"*1 || 1,
				pageListSize: 10,
				blockSize: 5,
				totalListSize: 0,
				schValue: "${searchInfo != null ? searchInfo.schValue : ''}",
				schValue2: "${searchInfo != null ? searchInfo.schValue2 : ''}",
				schValue3: "${searchInfo != null ? searchInfo.schValue3 : ''}",
				schValue4: "${searchInfo != null ? searchInfo.schValue4 : ''}",
				schCondition: "${searchInfo != null ? searchInfo.schCondition : ''}",
				schCondition2: "${searchInfo != null ? searchInfo.schCondition2 : ''}",
				schCondition3: "${searchInfo != null ? searchInfo.schCondition3 : ''}",
				schCondition4: "${searchInfo != null ? searchInfo.schCondition4 : ''}",
				sortField: "${searchInfo != null ? searchInfo.sortField : ''}",
				sortOrder: "${searchInfo != null ? searchInfo.sortOrder : ''}",
				fromDate: "${searchInfo != null ? searchInfo.fromDate : ''}",
				toDate: "${searchInfo != null ? searchInfo.toDate : ''}"
			}
		}
	}
});
</script>
