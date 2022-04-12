<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<c:set var="popup_mode" value="${(writeYn eq 'Y' or modifiedYn eq 'Y') ? '' : 'disabled'}"/>
<div class="modal" :style="programRegistPopupVisible ? 'display:block;':'display:none;'">
    <div class="modal__wrap">
        <div class="modal__content w-650">
            <div class="modal__header">
                <h4 class="modal__title">프로그램 등록/수정 팝업</h4>
                <button class="modal__button--close" type="button" @click="closeProgramPopup"><span class="hidden">모달 닫기</span></button>
            </div>
            <div class="modal__body">
                <div class="section">
                    <div class="section__content">
                        <table class="table--row">
                            <caption>프로그램</caption>
                            <colgroup>
                                <col style="width:150px">
                                <col style="width:auto">
                                <col style="width:150px">
                                <col style="width:auto">
                            </colgroup>
                            <tbody>
                                <tr>
                                    <th class="icon__require">프로그램명</th>
                                    <td colspan="3">
                                        <input class="input__text" type="text" v-model="programInfo.programNm" ${popup_mode} ref="programNm" required/>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="icon__require">구분</th>
                                    <td colspan="3">
                                        <select class="select" v-model="programInfo.programDivision" ${popup_mode} ref="programDivision" required>
                                            <option name="programDivision" value="" disabled>-선택-</option>
											<c:forEach var="code" items="${n2m:getCodeList('CG_ID_PROGRAM_ACCESS_TYPE')}">
											    <option value="${code.codeId}">${code.codeName}</option>
											</c:forEach>
					                    </select>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="icon__require">프로그램주소</th>
                                    <td colspan="3">
                                        <input class="input__text" type="text" v-model="programInfo.programUrl" ${popup_mode} ref="programUrl" required/>
                                    </td>
                                </tr>
                                <tr>
                                    <th class="icon__require">설명</th>
                                    <td colspan="3">
                                        <textarea rows="5" cols="30" v-model="programInfo.programDesc" ${popup_mode} ref="programDesc" required></textarea>
                                    </td>
                                </tr>
	                            <tr>
                                    <th>사용여부</th>
                                    <td colspan="3">
                                        <input class="input__checkbox" id="checkboxProgramUseFl" type="checkbox" v-model="programInfo.programUseFl" true-value="Y" false-value="N" ${popup_mode}/>
                                        <label class="label__checkbox" for="checkboxProgramUseFl"></label>
                                    </td>
                                </tr>
	                            <tr>
	                                <th>인가여부</th>
	                                <td colspan="3">
	                                    <input class="input__checkbox" id="checkboxProgramUseTokenYn" type="checkbox" v-model="programInfo.programUseTokenYn" true-value="Y" false-value="N" ${popup_mode}/>
	                                    <label class="label__checkbox" for="checkboxProgramUseTokenYn"></label>
	                                </td>
	                            </tr>
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
            <div class="modal__footer">
                <c:if test="${writeYn eq 'Y'}">
                    <button class="button__primary" type="button" @click="createProgram" v-if="!programRegistPopupEdit">저장</button>
                </c:if>
                <c:if test="${modifiedYn eq 'Y'}">
                    <button class="button__primary" type="button" @click="modifyProgram" v-if="programRegistPopupEdit">저장</button>
                </c:if>
<%--                 <c:if test="${deleteYn eq 'Y'}"> --%>
<!--                     <button class="button__secondary" type="button" @click="deleteProgram" v-if="programRegistPopupEdit">삭제</button> -->
<%--                 </c:if> --%>
                <button class="button__secondary" type="button" @click="closeProgramPopup">닫기</button>
            </div>
        </div>
    </div>
</div>
<script>
var programRegistPopupMixin={
    data: function(){
        return {
        	programInfo:{
                programNm: "",
                programDivision: "",
                programUrl: "",
                programDesc: "",
                programUseFl: "Y",
                programUseTokenYn: "Y",
                programgroupOid : "${param.programGroupOid}"
            },
            programListCallback: function(){},
            programRegistPopupEdit: false, // false-등록팝업, true-수정팝업
            programRegistPopupVisible : false
        }
    },
    methods: {
    	createProgram: function(){
            
            Valid.init(vm, vm.programInfo);
            var constraints = {};
            constraints = Valid.getConstraints();
            if(Valid.validate(constraints)) return;
            
            if(Modal.REGIST()) return;
            var request = $.ajax({
                url: N2M_CTX + "/programgroup/program/create.do",
                method: "POST",
                contentType: "application/json",
                dataType: "json",
                data: JSON.stringify(vm.programInfo)
            });
            request.done(function(data){
                Modal.OK();
                vm.programListCallback();
                vm.closeProgramPopup();
            });
        },
        modifyProgram: function(){
            
            Valid.init(vm, vm.programInfo);
            var constraints = {};
            constraints = Valid.getConstraints();
            if(Valid.validate(constraints)) return;
            
            if(Modal.MODIFY()) return;
            var request = $.ajax({
                url: N2M_CTX + "/programgroup/program/modify.do",
                method: "PUT",
                contentType: "application/json",
//                 dataType: "json",
                data: JSON.stringify(vm.programInfo)
            });
            request.done(function(data){
                Modal.OK();
                vm.programListCallback();
                vm.closeProgramPopup();
            });
        },
        closeProgramPopup: function(){
            vm.programInfo = {
                programNm: "",
                programDivision: "",
                programUrl: "",
                programDesc: "",
                programUseFl: "Y",
                programUseTokenYn: "Y",
                programgroupOid: "${param.programGroupOid}"
            }
            vm.programRegistPopupEdit = false;
            vm.programRegistPopupVisible = false;
        },
        openProgramRegistPopup: function(programInfo, programGroupOid, callback){
        	if(programInfo && programInfo.programOid){
                vm.programInfo = programInfo;
                vm.programRegistPopupEdit = true;
            }else{
                vm.programRegistPopupEdit = false;
            }
            vm.programInfo.programGroupOid = programGroupOid;
            vm.programRegistPopupVisible = true;
            vm.programListCallback = callback;
        }
    }
}
</script>