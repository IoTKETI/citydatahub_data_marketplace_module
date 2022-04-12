<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<h3 class="content__title">데이터민원 상세</h3>
<form>
  <fieldset>
    <legend>필드셋 제목</legend>
    <!-- section-write -->
    <section class="section">
     <div class="section__header">
            <h4 class="section__title">민원 질문</h4>
          </div>
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
              <td>
                sample
              </td>
              <th>등록일시</th>
              <td>
                sample
              </td>
            </tr>
            <tr>
              <th>유형</th>
              <td>
                sample
              </td>
              <th>조회수</th>
              <td>
                sample
              </td>
            </tr>
            <tr>
              <th>제목</th>
              <td colspan="3">
                sample
              </td>
            </tr>
            <tr class="va-t">
              <th>민원내용</th>
              <td colspan="3">
                 최고의 시절이었고, 또한 최악의 시절이었다. 지혜의 시기였고, 또한 어리석음의 시기였다. 믿음의 시대였고, 또한 불신의 시대였다.<br> 
                    빛의 계절이었고, 또한 어둠의 계절이기도 했다. 희망의 봄이었고, 또한 절망의 겨울이기도 했다. 우리는 모든 것을 가지고 있었지만, 또한 아무것도 갖고 있지 않았다.<br>
                    우리 모두는 천국을 향해 가고 있었지만, 또한 그 반대쪽으로 가고 있기도 했다.<br>
                    <br>
                    최고의 시절이었고, 또한 최악의 시절이었다. 지혜의 시기였고, 또한 어리석음의 시기였다. 믿음의 시대였고, 또한 불신의 시대였다.<br> 
                    빛의 계절이었고, 또한 어둠의 계절이기도 했다. 희망의 봄이었고, 또한 절망의 겨울이기도 했다. 우리는 모든 것을 가지고 있었지만, 또한 아무것도 갖고 있지 않았다.<br>
                    우리 모두는 천국을 향해 가고 있었지만, 또한 그 반대쪽으로 가고 있기도 했다.<br>
                    <br>
              </td>
            </tr>
            <tr class="va-t">
                  <th>첨부파일</th>
                  <td colspan="3" class="file__group">
                    <ul class="file__list">
                      <li class="file__item file__item--none">첨부파일이 없습니다.</li>
                      <li class="file__item">indoku.jpg</li>
                      <li class="file__item">indoku.jpg</li>
                      <li class="file__item">indoku.jpg</li>
                    </ul>
                  </td>
                </tr>
          </tbody>
        </table>
      </div>
    </section>
    <!-- //section-default -->
    <div class="button__group">
      <button class="button__primary" type="button" onclick="location.href='<c:url value="/datacomplaint/pageList.do"/>'">삭제</button>
      <button class="button__secondary" type="button" onclick="location.href='<c:url value="/datacomplaint/pageList.do"/>'">목록</button>
    </div>
    
    <section class="section">
          <div class="section__header">
            <h4 class="section__title">민원 답변</h4>
          </div>
          <div class="section__content">
            <table class="table--column">
              <caption>테이블 제목</caption>
             <colgroup>
	            <col style="width:150px">
	            <col style="width:auto">
	            <col style="width:150px">
	            <col style="width:auto">
         	 </colgroup>
              <tbody>
                <tr>
                  <th>답변</th>
	              <td>
	                sample
	              </td>
             	 <th>등록일시</th>
            	  <td>
              	  sample
            	  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>
  </fieldset>
</form>

