var Modal = (function(){
	var CONFIRM_MSG_REGIST = "등록하시겠습니까?";
	var CONFIRM_MSG_MODIFY = "수정하시겠습니까?";
	var CONFIRM_MSG_DELETE = "삭제하시겠습니까?";
	var ALERT_MSG_OK       = "정상적으로 처리되었습니다.";
	var ALERT_MSG_ERROR    = "알 수 없는 오류가 발생하였습니다.";
	
	return {
		REGIST: function(){
			return !confirm(CONFIRM_MSG_REGIST);
		},
		MODIFY: function(){
			return !confirm(CONFIRM_MSG_MODIFY);
		},
		DELETE: function(){
			return !confirm(CONFIRM_MSG_DELETE);
		},
		OK: function(){
			alert(ALERT_MSG_OK);
		},
		ERROR: function(){
			alert(ALERT_MSG_ERROR);
		}
	}
})();