<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">카테고리수정</h3>
<form id="categoryForm">
  <fieldset>
    <legend>필드셋 제목</legend>
    <!-- section-write -->
    <section class="section">
      <div class="section__content">
        <table class="table--row">
          <caption>테이블 제목</caption>
          <colgroup>
            <col style="width:150px">
            <col style="width:auto">
            <col style="width:150px">
            <col style="width:auto">
          </colgroup>
          <tbody>
            <input type="hidden" name="categoryOid" value="${param.categoryOid}" />
            <input type="hidden" name="deleteFileOids" v-model="category.deleteFileOids" />
            <tr>
              <th class="icon__require">카테고리명</th>
              <td colspan="1">
                <input class="input__text" name="categoryNm" title="카테고리명" type="text" v-model="category.categoryNm" ref="categoryNm" required/>
              </td>
              <th>등록자</th>
              <td colspan="1">
                {{category.categoryCreUsrNm}}
              </td>
            </tr>
            <tr>
              <th>상위카테고리</th>
              <td colspan="3">
                <input type="hidden" name="categoryParentOid" v-model="category.categoryParentOid">
                <input class="input__text w-with-94" name="categoryParentNm" title="상위카테고리" type="text" v-model="category.categoryParentNm" readonly/>
                <button class="button__outline w-94" type="button" @click="openCategoryPopup">카테고리선택</button>
              </td>
            </tr>
            <tr>
              <th class="icon__require">설명</th>
              <td colspan="3">
                <textarea rows="10" cols="130" name="categoryDesc" title="설명" v-model="category.categoryDesc" ref="categoryDesc" required></textarea>
              </td>
            </tr>
            <tr>
              <th>대표이미지</th>
              <td class="file__group" colspan="3">
                <n2m-file-uploader :saved-files="category.fileList" @select-file="selectFile" @delete-file="deleteFile" :single="true" />
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <button class="button__primary" type="button"  @click="modifyCategory">저장</button>
      <c:if test="${deleteYn eq 'Y'}">
        <button class="button__secondary" type="button" @click="deleteCategory">삭제</button>
      </c:if>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/category/pageDetail.do?categoryOid=${param.categoryOid}"/>'">취소</button>
    </div>
  </fieldset>
</form>

    <!-- 코드 등록 팝업 -->
    <jsp:include page="/WEB-INF/jsp/category/popup/categoryPopup.jsp"/>
</div>

<script>
    $(function(){
    	Vue.use(N2MFileUploader);
        vm = new Vue({
            el: '#content',
            mixins: [codePopupMixin],
            data: function(){
                return {
                    category: {
                        categoryOid: "${param.categoryOid}",
                        fileList : [],
                        deleteFileOids : []
                    },
                    categoryList: [],
                    fileList : [],
                    selectedCategory: {},
                    deleteFileOids : [],
                    popupVisible: false
                }
            },
            methods: {
            	selectFile: function(files){
                    vm.fileList = files;
                },
                deleteFile: function(index, oid){
                    // 삭제버튼 누른 파일 oid를 vm.category.deleteFileOids 리스트에 넣기
                    vm.category.fileList.splice(index, 1);
                    vm.deleteFileOids.push(oid);
                    vm.category.deleteFileOids = vm.deleteFileOids;
                    
                },
                getCategory: function(){
                    var request = $.ajax({
                        url: N2M_CTX + "/category/get.do",
                        method: "GET",
                        dataType: "json",
                        data: vm.category
                    });
                    request.done(function(data){
                    	data.category.fileList = [];
                        vm.category = data.category;
                        if(data.category.categoryImgSize > 0){
	                        vm.category.fileList = [{
	                        	name : data.category.categoryImgOrgNm,
	                        	oid : data.category.categoryOid
	                        }];
                        }
                    });
                },
                deleteCategory: function(){
                    if(Modal.DELETE()) return;
                    var request = $.ajax({
                        url: N2M_CTX + "/category/remove.do",
                        method: "DELETE",
                        contentType: "application/json",
//                         dataType: "json",
                        data: JSON.stringify(vm.category)
                    });
                    request.done(function(data){
                        Modal.OK();
                        location.href = N2M_CTX + "/category/pageList.do";
                    });
                },
                modifyCategory : function() {
                    Valid.init(vm, vm.category);
                    var constraints = {};
                    constraints = Valid.getConstraints();
                    if(Valid.validate(constraints)) return;
                                        
                    var formData = new FormData(document.getElementById('categoryForm'));
                    
                    formData.append("files", vm.fileList[0]);
                    
                    if(Modal.MODIFY()) return;
                    
                    var request = $.ajax({
                        method : "PUT",
                        url : N2M_CTX + "/category/modify.do",
                        enctype: "multipart/form-data",
                        processData: false,
                        contentType: false,
                        data : formData
                    });
                    request.done(function(data) {
                    	Modal.OK();
                        location.href = N2M_CTX + "/category/pageDetail.do?categoryOid="+vm.category.categoryOid;
                    });
                },
                openCategoryPopup: function(){
                    vm.openCategoryListPopup();
                }
            }
        })
        vm.getCategory();
    });
</script>
