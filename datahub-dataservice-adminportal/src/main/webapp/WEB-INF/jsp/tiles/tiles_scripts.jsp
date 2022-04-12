<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<!-- CSS -->
<link rel="stylesheet" href="<c:url value="/admin/css/jquery-ui.css"/>"/>
<link rel="stylesheet" href="<c:url value="/admin/css/jquery-ui-timepicker-addon.css"/>"/>
<link rel="stylesheet" href="<c:url value="/admin/css/element-ui.css"/>"/>
<link rel="stylesheet" href="<c:url value="/admin/css/easyui/easyui.css"/>"/>
<link rel="stylesheet" href="<c:url value="/admin/css/easyui/icon.css"/>"/>
<link rel="stylesheet" href="<c:url value="/admin/plugins/fancytree/skin-win7/ui.fancytree.min.css"/>"/>
<link rel="stylesheet" href="<c:url value="/admin/css/sub.css"/>"/>
<link rel="stylesheet" href="<c:url value="/admin/css/swiper.min.css"/>"/>
<link rel="stylesheet" href="<c:url value="/admin/css/tippy.css"/>">

<!-- JS -->
<!-- JQUERY -->
<script src="<c:url value="/admin/js/jquery-3.4.1.min.js"/>"></script>
<script src="<c:url value="/admin/js/jquery-ui.min.js"/>"></script>
<script src="<c:url value="/admin/js/jquery-ui-timepicker-addon.js"/>"></script>
<!-- VUE.JS -->
<script src="<c:url value="/admin/js/vue.js"/>"></script>
<!-- ELEMENT UI -->
<script src="<c:url value="/admin/js/element-ui.js"/>"></script>
<!-- MOMENT.JS -->
<script src="<c:url value="/admin/js/moment.js"/>"></script>
<!-- VALIDATE.JS -->
<script src="<c:url value="/admin/js/validate.js"/>"></script>
<!-- EASY UI -->
<script src="<c:url value="/admin/js/jquery.easyui.min.js"/>"></script>
<!-- ECHARTS -->
<script src="<c:url value="/admin/js/echarts.js"/>"></script>

<script src="<c:url value="/admin/js/ui.js"/>"></script>

<script src="<c:url value="/admin/js/swiper.min.js"/>"></script>

<script src="<c:url value="/admin/js/popper.js"/>"></script>
<script src="<c:url value="/admin/js/tippy.js"/>"></script>

<!-- FANCYTREE -->
<script src="<c:url value="/admin/plugins/fancytree/jquery.fancytree-all-deps.js"/>"></script>

<!-- NAVER EDITOR 2.10 -->
<script src="<c:url value="/admin/plugins/navereditor2/js/service/HuskyEZCreator.js"/>"></script>


<!-- COMMON -->
<script src="<c:url value="/admin/js/common-ui.js"/>"></script>
<script src="<c:url value="/admin/js/common/message.js"/>"></script>
<script src="<c:url value="/admin/js/common/custom_valid.js"/>"></script>
<script src="<c:url value="/admin/js/common/modal.js"/>"></script>



<!-- VUE COMPONENT -->
<script src="<c:url value="/admin/js/vue-component/vue-filter.js"/>"></script>
<script src="<c:url value="/admin/js/vue-component/vue-pagination.js"/>"></script>
<script src="<c:url value="/admin/js/vue-component/vue-file-uploader.js"/>"></script>
<script src="<c:url value="/admin/js/vue-component/vue-editor.js"/>"></script>
<script src="<c:url value="/admin/js/vue-component/vue-chart.js"/>"></script>
<script>
var N2M_CTX="${pageContext.request.contextPath}";

var vm;
var N2M_UI;

$.ajaxSetup({
	cache: false,
	complete: function(res) {
		
	},
	error: function(res) {
		if (res.status == 401) {
// 			location.href = N2M_CTX + "/login/oauthlogin.do";

		} else if (res.status == 500) {
			alert("내부 처리 중 오류가 발생하였습니다.");
		} else if( res.status != 200) {
//         	location.href = N2M_CTX + "/login/oauthlogin.do";
        }
	}
});

$(function(){
	showModal();
	showNav();
	showMenu();
	uploadFile(); 
	setInputNumber();

	//메뉴 활성화 설정	
	var $menu = $("li._${sessionScope._nav}");
	if($menu.find('ul.nav__depth2').length === 0){		//하위뎁스가 없을때
		if($menu.hasClass('nav__item')){				//자신이 하위뎁스인경우
			$menu.find('a').addClass("nav__link--active");
			$menu.closest('li.nav__depth1').addClass("nav__depth1--active");
			$menu.closest('ul.nav__depth2').show();
		}else{
			$menu.addClass("nav__depth1--active");		//일반적인 경우
		}
	}else{//하위뎁스가 있을때
		$menu.addClass("nav__depth1--active");
		$menu.find('ul.nav__depth2').show();
	}

});

Vue.mixin({
	mounted: function(){
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
		
		//N2m UI 스크립트 초기화
		N2M_UI = N2M.ui();
		//첫 페이지 로딩시 툴팁 초기화
        tippy(".ds__tooltip", {
            placement: 'right',
            animation: 'scale',
            inertia: true,
            theme: 'gradient',
        });
	},
	methods: {
		keepPageInfo: function(callback){
			if($.isFunction(callback)){
				callback(vm.pageInfo.curPage);
			}
		},
		fileDownload: function(oid, fileOid){
            if(!fileOid) return "";
            return N2M_CTX + "/downloadFile.do?oid="+oid+"&fileOid="+fileOid;
        }
	},
	data: function(){
		return {
			loginUserId: "${sessionScope.user.userId}",
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
