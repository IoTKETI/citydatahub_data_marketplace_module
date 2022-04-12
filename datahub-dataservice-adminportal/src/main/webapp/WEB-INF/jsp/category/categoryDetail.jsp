<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div id="content" v-cloak>
<h3 class="content__title">카테고리상세</h3>
<form>
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
              <th>카테고리명</th>
              <td colspan="1">
                {{category.categoryNm}}
              </td>
              <th>등록자</th>
              <td colspan="1">
                {{category.categoryCreUsrNm}}
              </td>
            </tr>
            <tr>
              <th>상위카테고리</th>
              <td colspan="3">
                {{category.categoryParentNm}}
              </td>
            </tr>
            <tr>
              <th>설명</th>
              <td colspan="3" v-html="$options.filters.enter(category.categoryDesc)">
              </td>
            </tr>
            <tr>
              <th>대표이미지</th>
              <td class="file__group" colspan="3">
	              <ul class="file__list">
	                  <li class="file__item file__item--none" v-if="category.categoryImgSize === 0">대표이미지가 없습니다.</li>
	                  <li class="file__item" v-if="category.categoryImgSize > 0">
	                      <img class="file__item-thumbnail" alt="" src='<c:url value="/category/downloadFile.do?categoryOid=${param.categoryOid}"/>'/>
	                  </li>
	              </ul>
              </td>
            </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <c:if test="${modifiedYn eq 'Y'}">
        <button class="button__primary" type="button"  onclick="location.href='<c:url value="/category/pageModify.do?categoryOid=${param.categoryOid}"/>'">수정</button>
      </c:if>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/category/pageList.do"/>'">목록</button>
    </div>
  </fieldset>
</form>
</div>

<script>
    $(function(){
        vm = new Vue({
            el: '#content',
            data: function(){
                return {
                    category: {
                        categoryOid: "${param.categoryOid}"
                    }
                }
            },
            methods: {
                getCategory: function(){
                    var request = $.ajax({
                        url: N2M_CTX + "/category/get.do",
                        method: "GET",
                        dataType: "json",
                        data: vm.category
                    });
                    request.done(function(data){
                        vm.category = data;
                    });
                },
                download: function(categoryOid){
                    location.href = N2M_CTX + "/category/downloadFile.do?categoryOid="+categoryOid;
                }
            }
        })
        vm.getCategory();
    });
</script>
