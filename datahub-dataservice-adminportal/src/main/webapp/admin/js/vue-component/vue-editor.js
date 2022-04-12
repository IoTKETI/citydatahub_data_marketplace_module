var N2MEditor = {};
var oEditors = [];
N2MEditor.install = function(Vue, options){ 
	Vue.component('n2m-editor', {
	props: {
		id:null,
		name : null
	},
	mounted: function(){
		var vm = this;
		var sLang = "ko_KR";    // 언어 (ko_KR/ en_US/ ja_JP/ zh_CN/ zh_TW), default = ko_KR

        // 추가 글꼴 목록
        //var aAdditionalFontSet = [["MS UI Gothic", "MS UI Gothic"], ["Comic Sans MS", "Comic Sans MS"],["TEST","TEST"]];

        nhn.husky.EZCreator.createInIFrame({
            oAppRef: oEditors,
            elPlaceHolder: "ir1",
            sSkinURI: N2M_CTX + "/admin/plugins/navereditor2/SmartEditor2Skin.html",  
            htParams : {
                bUseToolbar : true,             // 툴바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseVerticalResizer : true,     // 입력창 크기 조절바 사용 여부 (true:사용/ false:사용하지 않음)
                bUseModeChanger : true,         // 모드 탭(Editor | HTML | TEXT) 사용 여부 (true:사용/ false:사용하지 않음)
                //bSkipXssFilter : true,        // client-side xss filter 무시 여부 (true:사용하지 않음 / 그외:사용)
                //aAdditionalFontList : aAdditionalFontSet,     // 추가 글꼴 목록
                fOnBeforeUnload : function(){
                    //alert("완료!");
                },
                I18N_LOCALE : sLang
            }, //boolean
            fOnAppLoad : function(){
            	vm.$emit("onappload");
                //예제 코드
                //oEditors.getById["ir1"].exec("PASTE_HTML", ["로딩이 완료된 후에 본문에 삽입되는 text입니다."]);
            },
            fCreator: "createSEditor2"
        });
	},
	
	template: '\
		<textarea :id="id" :name="name" type="textarea" autosize></textarea>\
		'
	});
}
