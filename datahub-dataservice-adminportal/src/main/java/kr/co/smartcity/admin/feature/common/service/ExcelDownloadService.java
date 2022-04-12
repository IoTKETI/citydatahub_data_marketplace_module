/*******************************************************************************
 * BSD 3-Clause License
 * 
 * Copyright (c) 2021, N2M
 * All rights reserved.
 * 
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 * 
 * 1. Redistributions of source code must retain the above copyright notice, this
 *    list of conditions and the following disclaimer.
 * 
 * 2. Redistributions in binary form must reproduce the above copyright notice,
 *    this list of conditions and the following disclaimer in the documentation
 *    and/or other materials provided with the distribution.
 * 
 * 3. Neither the name of the copyright holder nor the names of its
 *    contributors may be used to endorse or promote products derived from
 *    this software without specific prior written permission.
 * 
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE LIABLE
 * FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL
 * DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
 * SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER
 * CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
 * OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE
 * OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 ******************************************************************************/
package kr.co.smartcity.admin.feature.common.service;

import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.springframework.stereotype.Service;

import kr.co.smartcity.admin.common.component.HttpComponent;
import kr.co.smartcity.admin.feature.dataset.service.DatasetService;

@Service("excelDownloadService")
public class ExcelDownloadService extends HttpComponent {
	
	@Resource(name="datasetService")
	DatasetService datasetService;
	
	/**
	 * 엑셀 다운로드
	 * @Author      : junheecho
	 * @Date        : 2019. 10. 31.
	 * @param params
	 * @param response
	 * @param request
	 * @throws Exception 
	 */
	@SuppressWarnings("unchecked")
	public void downloadExcel(Map<String, Object> params, HttpServletResponse response, HttpServletRequest request) throws Exception {
		String datasetJson = datasetService.getDataset(params);
		
		Map<String, Object> m = toMap(datasetJson);
		String datasetTitle = (String) m.get("title");
		List<Map<String, String>> datasetOutputList = (List<Map<String, String>>) m.get("datasetOutputList");
		
		// 워크북 생성
		Workbook wb = new HSSFWorkbook();
		Sheet sheet = wb.createSheet("데이터셋 명세서");
		
		Row row = null;
		Cell cell = null;
		int rowNo = 0;
		
		// 타이틀 스타일 (글자크기 18, 아래 이중 테두리, 진하게, 가운데 정렬)
		CellStyle titleStyle = createTitleStyle(wb);
		// 서브 타이틀 스타일 (글자크기 11, 가운데 정렬)
		CellStyle subTitleStyle = createSubTitleStyle(wb);
		// 헤더용 스타일 (회색 배경)
	    CellStyle headerStyle = createHeaderStyle(wb); 
	    // 데이터용 스타일 (가는테두리)
	    CellStyle bodyStyle = createBodyStyle(wb);
	    // 데이터용 가운데 스타일 (가는테두리, 중간 정렬)
	    CellStyle bodyCenterStyle = createBodyCenterStyle(wb);
	    // 병합된 데이터용 스타일 (가는테두리, 중간 정렬, 진하게)
	    CellStyle mergedBodyStyle = createMergedBodyStyle(wb);
	    
	    row = sheet.createRow(rowNo++);
	    row.setHeightInPoints(28);
	    cell = row.createCell(0); // 병합
	    cell.setCellStyle(titleStyle);
	    cell.setCellValue("Open API 명세서");
	    cell = row.createCell(1); // 병합
	    cell.setCellStyle(titleStyle);
	    cell = row.createCell(2); // 병합
	    cell.setCellStyle(titleStyle);
	    cell = row.createCell(3); // 병합
	    cell.setCellStyle(titleStyle);
	    cell = row.createCell(4); // 병합
	    cell.setCellStyle(titleStyle);
	    sheet.addMergedRegion(new CellRangeAddress(rowNo-1, rowNo-1, 0, 4));
	    
	    row = sheet.createRow(rowNo++);
	    row.setHeightInPoints(23); // 셀높이
	    cell = row.createCell(0); // 병합
	    cell.setCellStyle(subTitleStyle);
	    cell.setCellValue(datasetTitle);
	    cell = row.createCell(1); // 병합
	    cell.setCellStyle(subTitleStyle);
	    cell = row.createCell(2); // 병합
	    cell.setCellStyle(subTitleStyle);
	    cell = row.createCell(3); // 병합
	    cell.setCellStyle(subTitleStyle);
	    cell = row.createCell(4); // 병합
	    cell.setCellStyle(subTitleStyle);
	    sheet.addMergedRegion(new CellRangeAddress(rowNo-1, rowNo-1, 0, 4));
	    
	    rowNo = rowNo + 1;
	    
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellValue("* 요청주소");
	    
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0); // 병합
	    cell.setCellValue(props.getDatapublishMsApiUrl() + "/dataservice/dataset/"+datasetOutputList.get(0).get("datasetId")+"/data");
	    sheet.addMergedRegion(new CellRangeAddress(rowNo-1, rowNo-1, 0, 4));
	    
	    rowNo = rowNo + 1;
	    
	    // HTTP HEAD 요청변수
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellValue("* HTTP HEAD 요청변수");
	    
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("항목명");
	    cell = row.createCell(1);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("항목설명");
	    cell = row.createCell(2);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("샘플데이터");
	    cell = row.createCell(3); // 병합
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("필수여부");
	    cell = row.createCell(4); // 병합
	    cell.setCellStyle(headerStyle);
	    sheet.addMergedRegion(new CellRangeAddress(rowNo-1, rowNo-1, 3, 4));
	    
	    //인증토큰
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("Authorization");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("인증토큰");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("eyJhbGciOiJSUzI1NiIsInR5cCI6IkpXVCJ9.eyJ0eXAiOiJ1IiwidXNlcmlkIjoiY2l0eWh1YjA4Iiwibmlja25hbWUiOiJ0ZXN0OCIsImVtYWlsIjoidGVzdEB0ZXN0LmNvbSIsInJvbGUiOiJtYSIsImlhdCI6MTU3MTExNzk0OCwiZXhwIjoxNTcxMTIxNTQ4LCJhdWQiOiJ1cm46Y2l0eWh1Yjp0ZXN0bW9kdWxlIiwiaXNzIjoidXJuOmNpdHlodWI6c2VyY3VyaXR5In0.KFiQer_IO4ZmJHWX5MpfY2ZkANmry1BKZsN8koyc5mkoLE6jQlUWX0zRybiXv9RXDBsCOeySc5iRP2rRlJGSIjQaek4xUs4FF9qxtP6YkwtvJ6nb_eFJN3BJlbjSoL3J3G31_TEQTx-AEWBGIqBQZd_F_dqe8oci5NGAg2bW5cBPJvbvnT2EBlxfL6DAV3do2YH-rrFOgFV4OsoQVeqt663fxd97nYMBzBBCLJ_iCNFzsLGc2fdg1xEmauVwDPvpJvTZzXa0yggIP-IB77LXpPW8I6Gq3BYO8hEM-ybPDygm3vMfdpQP5FSdmBtSfk9pmrhxnk3OTxhRSS7UMBNqyA");
	    cell = row.createCell(3); //병합
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("필수");
	    cell = row.createCell(4); //병합
	    cell.setCellStyle(bodyStyle);
	    sheet.addMergedRegion(new CellRangeAddress(rowNo-1, rowNo-1, 3, 4));
	    
	    //Content Type
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("Content-Type");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("Content Type");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("application/json");
	    cell = row.createCell(3); //병합
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("필수");
	    cell = row.createCell(4); //병합
	    cell.setCellStyle(bodyCenterStyle);
	    sheet.addMergedRegion(new CellRangeAddress(rowNo-1, rowNo-1, 3, 4));
	    
	    // 2줄 띄우기
	    rowNo = rowNo + 2;
	    
	    // 요청변수
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellValue("* 요청변수 Query Param");
	    
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("항목명");
	    cell = row.createCell(1);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("항목설명");
	    cell = row.createCell(2);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("샘플데이터");
	    cell = row.createCell(3);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("비고");
	    cell = row.createCell(4);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("필수여부");
	    
	    //데이터유형
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("datatype");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("데이터유형(최종값, 이력데이터)");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("lastdat/historydata");
	    cell = row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("");
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("필수");
	    
	    //옵션
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("options");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("옵션");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("normalValues/keyValuesHistory");
	    cell = row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("");
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("선택");
	    
	    //병합셀
    	row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(mergedBodyStyle);
	    cell.setCellValue("아래 데이터는 datatype이 historydata인 경우에만 추가");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell = row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyStyle);
	    sheet.addMergedRegion(new CellRangeAddress(rowNo-1, rowNo-1, 0, 4));
	    
	    //날짜검색조건(이전, 이후, 중간)
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("timeAt");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("날짜검색조건(이전, 이후, 중간)");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("before/after/berween");
	    cell = row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("");
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("필수");
	    
	    //날짜검색조건(생성일, 수정일)
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("timeproperty");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("날짜검색조건(생성일, 수정일)");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("createdAt/modifiedAt");
	    cell = row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("");
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("필수");
	    
	    //검색시작일자
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("time");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("검색시작일자");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("2019-10-01T00:20:00Z");
	    cell = row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("");
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("필수");
	    
	    //검색종료일자
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("endtime");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("검색종료일자");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("2019-10-01T14:20:00Z");
	    cell = row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("");
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("필수");
	    
	    //제한
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("limit");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("제한");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("default:1000 (요청limit이 없는 경우 기본값 1000)");
	    cell = row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("");
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("선택");
	    
	    //출력명
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("q");
	    cell = row.createCell(1);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("출력명");
	    cell = row.createCell(2);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("refParkingSpots==\"urn:datahub:OffStreetParking:yt_lot_2\";totalSpotNumber>10;name!=영등포");
	    cell = row.createCell(3);
	    cell.setCellStyle(bodyStyle);
	    cell.setCellValue("출력명에 대한 조건값\n한개이상 조건 입력 시 구분자 ';'");
	    cell = row.createCell(4);
	    cell.setCellStyle(bodyCenterStyle);
	    cell.setCellValue("선택");
		
	    // 2줄 띄우기
	    rowNo = rowNo + 2;
	    
	    // 출력결과
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellValue("* 출력결과");
	    
	    row = sheet.createRow(rowNo++);
	    cell = row.createCell(0);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("No");
	    cell = row.createCell(1);
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("출력명");
	    cell = row.createCell(2); //병합
	    cell.setCellStyle(headerStyle);
	    cell.setCellValue("출력설명");
	    cell = row.createCell(3); //병합
	    cell.setCellStyle(headerStyle);
	    cell = row.createCell(4); //병합
	    cell.setCellStyle(headerStyle);
	    sheet.addMergedRegion(new CellRangeAddress(rowNo-1, rowNo-1, 2, 4));
	    
	    // 데이터 출력 부분 생성
	    Map<String, String> datasetOutputMap;
	    for(int i=0; i<datasetOutputList.size(); i++) {
	    	datasetOutputMap = datasetOutputList.get(i);
	    	
	    	row = sheet.createRow(rowNo++);
	        cell = row.createCell(0);
	        cell.setCellStyle(bodyCenterStyle);
	        cell.setCellValue(i+1);
	        cell = row.createCell(1);
	        cell.setCellStyle(bodyStyle);
	        
	        cell.setCellValue(datasetOutputMap.get("aliasColName"));

	        cell = row.createCell(2);
	        cell.setCellStyle(bodyStyle);
	        cell.setCellValue(datasetOutputMap.get("outputDesc"));
	        cell = row.createCell(3);
	        cell.setCellStyle(bodyStyle);
	        cell = row.createCell(4);
	        cell.setCellStyle(bodyStyle);
	        sheet.addMergedRegion(new CellRangeAddress(rowNo-1, rowNo-1, 2, 4));
	    }
	    
	    final int PIXEL = 280;
	    sheet.setColumnWidth(0, 25 * PIXEL);
	    sheet.setColumnWidth(1, 30 * PIXEL);
	    sheet.setColumnWidth(2, 50 * PIXEL);
	    sheet.setColumnWidth(3, 30 * PIXEL);
	    sheet.setColumnWidth(4, 10 * PIXEL);
	    
	    datasetTitle = datasetTitle + "_명세서_" + LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".xls";
	    
	    if(isFirefoxBrowser(request)) {
	    	datasetTitle = "\""+ new String(datasetTitle.replaceAll("\\s", "_").getBytes("UTF-8"), "8859_1") + "\""; // firefox만 예외처리필요
	    } else {
	    	datasetTitle = URLEncoder.encode(datasetTitle, "UTF-8").replaceAll("\\+", "_"); // chrome, ie, opera ok
	    }
	    
	    response.setCharacterEncoding("UTF-8");
	    response.setContentType("ms-vnd/excel");
	    response.setHeader("Content-Disposition", "attachment;filename="+datasetTitle);
	    
	    // 엑셀 출력
	    wb.write(response.getOutputStream());
	    wb.close();
	}

	private CellStyle createTitleStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		
		// 아래 이중 테두리
	    style.setBorderTop(BorderStyle.NONE);
	    style.setBorderBottom(BorderStyle.DOUBLE);
	    style.setBorderLeft(BorderStyle.NONE);
	    style.setBorderRight(BorderStyle.NONE);
	    
	    // 가운데 정렬
	    style.setAlignment(HorizontalAlignment.CENTER);
	    style.setVerticalAlignment(VerticalAlignment.CENTER);
		
	    // 글자 크기 18, 진하게
	    Font font = wb.createFont();
	    font.setFontHeightInPoints((short)18);
	    font.setBold(true);
	    style.setFont(font);
	    
		return style;
	}

	private CellStyle createSubTitleStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		
		// 경계선 없음
		style.setBorderTop(BorderStyle.NONE);
		style.setBorderBottom(BorderStyle.NONE);
		style.setBorderLeft(BorderStyle.NONE);
		style.setBorderRight(BorderStyle.NONE);
		
		// 가운데 정렬
		style.setAlignment(HorizontalAlignment.CENTER);
		style.setVerticalAlignment(VerticalAlignment.CENTER);
		
		// 글자 크기 11
		Font font = wb.createFont();
		font.setFontHeightInPoints((short)11);
		style.setFont(font);
		
		return style;
	}
	
	private CellStyle createHeaderStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		
		// 가는 경계선
	    style.setBorderTop(BorderStyle.THIN);
	    style.setBorderBottom(BorderStyle.THIN);
	    style.setBorderLeft(BorderStyle.THIN);
	    style.setBorderRight(BorderStyle.THIN);
	    
	    // 배경 회색
	    style.setFillForegroundColor(HSSFColorPredefined.GREY_25_PERCENT.getIndex());
	    style.setFillPattern(FillPatternType.SOLID_FOREGROUND);
	    
	    // 가운데 정렬
	    style.setAlignment(HorizontalAlignment.CENTER);
	    style.setVerticalAlignment(VerticalAlignment.CENTER);
	    
		return style;
	}
	
	private CellStyle createBodyStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		
		// 가는 경계선
	    style.setBorderTop(BorderStyle.THIN);
	    style.setBorderBottom(BorderStyle.THIN);
	    style.setBorderLeft(BorderStyle.THIN);
	    style.setBorderRight(BorderStyle.THIN);
	    
	    // 세로 가운데 정렬
	    style.setVerticalAlignment(VerticalAlignment.CENTER);
	    
	    // 자동 줄바꿈
	    style.setWrapText(true);
		
		return style;
	}
	
	private CellStyle createBodyCenterStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		
		// 가는 경계선
	    style.setBorderTop(BorderStyle.THIN);
	    style.setBorderBottom(BorderStyle.THIN);
	    style.setBorderLeft(BorderStyle.THIN);
	    style.setBorderRight(BorderStyle.THIN);
	    
	    // 세로 가운데 정렬
	    style.setAlignment(HorizontalAlignment.CENTER);
	    style.setVerticalAlignment(VerticalAlignment.CENTER);
	    
	    // 자동 줄바꿈
	    style.setWrapText(true);
		
		return style;
	}
	
	private CellStyle createMergedBodyStyle(Workbook wb) {
		CellStyle style = wb.createCellStyle();
		
		// 가는 경계선
	    style.setBorderTop(BorderStyle.THIN);
	    style.setBorderBottom(BorderStyle.THIN);
	    style.setBorderLeft(BorderStyle.THIN);
	    style.setBorderRight(BorderStyle.THIN);
	    
	    // 가운데 정렬
	    style.setAlignment(HorizontalAlignment.CENTER);
	    style.setVerticalAlignment(VerticalAlignment.CENTER);
	    
	    // 진하게
	    Font font = wb.createFont();
	    font.setBold(true);
		style.setFont(font);
	    
		return style;
	}
	
	private boolean isFirefoxBrowser(HttpServletRequest request) {
		String header = request.getHeader("User-Agent");
		return header.contains("Firefox");
	}
	
}
