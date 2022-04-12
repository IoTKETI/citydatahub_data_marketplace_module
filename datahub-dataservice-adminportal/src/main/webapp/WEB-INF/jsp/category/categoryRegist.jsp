<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
    <h3 class="content__title">카테고리등록</h3>
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
                <tr>
                  <th>등록자</th>
                  <td colspan="3">
                    ${sessionScope.user.userNm}
                  </td>
                </tr>
                <tr>
                  <th class="icon__require">카테고리명</th>
                  <td colspan="3">
                    <input class="input__text" name="categoryNm" title="카테고리명" type="text" v-model="category.categoryNm" ref="categoryNm" required/>
                  </td>
                </tr>
                <tr>
                  <th>상위카테고리</th>
                  <td colspan="3">
                    <input type="hidden" name="categoryParentOid" v-model="category.categoryParentOid">
                    <input class="input__text w-with-94" type="text" title="상위카테고리" v-model="category.categoryParentNm" readonly/>
                    <button class="button__outline w-94" type="button" @click="openCategoryPopup">카테고리 선택</button>
                  </td>
                </tr>
                <tr>
                  <th class="icon__require">설명</th>
                  <td colspan="3">
                    <textarea rows="10" cols="100" name="categoryDesc" title="설명" v-model="category.categoryDesc" ref="categoryDesc" required></textarea>
                  </td>
                </tr>
                <tr>
                  <th>대표이미지</th>
                  <td colspan="1">
                    <n2m-file-uploader :saved-files="category.fileList"  @select-file="selectFile" :single="true"/>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
        <!-- //section-default -->
        <div class="button__group">
          <button class="button__primary" type="button" @click="createCategory">저장</button>
          <button class="button__secondary" type="button" onclick="location.href='<c:url value="/category/pageList.do"/>'">취소</button>
        </div>
      </fieldset>
    </form>
    
    <!-- 코드 등록 팝업 -->
    <jsp:include page="/WEB-INF/jsp/category/popup/categoryPopup.jsp"/>
</div>

<script>
$(function() {
    Vue.use(N2MFileUploader);
    vm = new Vue({
        el : '#content',
        mixins: [codePopupMixin],
        data : function() {
            return {
                category : {
                    categoryOid : "",
                    categoryParentOid : 0,
                    categoryNm : "",
                    categoryDesc : "",
                    fileList : []
                },
                fileList : [],
                selectedCategory: {}
            }
        },
        methods : {
            selectFile: function(files){
                vm.fileList = files;
            },
            createCategory : function() {
                Valid.init(vm, vm.category);
                var constraints = {};
                constraints = Valid.getConstraints();
                if(Valid.validate(constraints)) return;
                
                if(Modal.REGIST()) return;
                
                var formData = new FormData(document.getElementById('categoryForm'));
                
                formData.append("files", vm.fileList[0]);
                
                var request = $.ajax({
                    method : "POST",
                    url : N2M_CTX + "/category/create.do",
                    enctype: "multipart/form-data",
                    processData: false,
                    contentType: false,
                    data : formData
                });
                request.done(function(data) {
                    Modal.OK();
                    location.href = N2M_CTX + "/category/pageList.do";
                });
            },
            openCategoryPopup: function(){
                vm.openCategoryListPopup();
            }
        }
    });
});
</script>
