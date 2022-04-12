<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<div class="modal" :style="popupVisible ? 'display:block;':'display:none;'">
	<div class="modal__wrap">
		<div class="modal__content w-650">
			<div class="modal__header">
				<h4 class="modal__title">{{PopupInfo.title}}</h4>
				<button class="modal__button--close" type="button" @click="closeCompanyPopup"><span class="hidden">모달 닫기</span></button>
			</div>
			<div class="modal__body">
				<form>
				  <fieldset>
				    <legend>필드셋 제목</legend>
				    <section class="section">
		      <div class="section__header">
				    <h4 class="section__title">업체검색</h4>
				  </div>
		      <div class="section__content">
		        <table class="table--row">
		          <caption>테이블 제목</caption>
		          <colgroup>
		            <col style="width:300px">
		            <col style="width:auto">
		          </colgroup>
		          <tbody>
		            <tr>
		             <th>업체명</th>
	                  <td>
	                  	<input class="input__text" type="text" placeholder="검색어를 입력하세요." v-model="pageInfo.schValue">
	                  </td>
		            </tr>
		          </tbody>
		        </table>
		      </div>
		      <div class="button__group">
		        <button class="button__primary" type="button" @click="getCompanyList(1)">검색</button>
		      </div>
		    </section>
		  </fieldset>
		</form>
			
			
			<section class="section">
			  <div class="section__header">
			    <h4 class="section__title">업체목록</h4>
			  </div>
			  <div class="section__content">
			  
			    <el-table :data="list" border style="width: 100%" empty-text="검색 결과가 존재하지 않습니다.">
				    <el-table-column prop="companyNm" label="업체명" align="left">
					    <template slot-scope="scope">
					  	    <el-button @click="selectcompany(scope.row.companyOid)" type="text" size="small">{{scope.row.companyNm}}</el-button>
						     </template>
					    </el-table-column>
				    <el-table-column prop="companyUsrNm" label="담당자이름" align="left"></el-table-column>
				    <el-table-column prop="companyPhone" label="전화번호" ></el-table-column>
				  </el-table>	  
				  <n2m-pagination :pager="pageInfo" @pagemove="getCompanyList"/>
			  </div>
			</section>
			</div>
			<div class="modal__footer">
				<button class="button__secondary" type="button" @click="closeCompanyPopup">닫기</button>
			</div>
		</div>
	</div>
</div>