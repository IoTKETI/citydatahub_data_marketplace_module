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
package kr.co.n2m.smartcity.datapublish.common.utils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;
import org.apache.poi.ss.util.CellRangeAddress;

public class ExcelUtil {
	public static final String FONT_NAME              = "Consolas";
	
	public static final int FONT_SIZE              = 9;
	public static final int HEADER_SIZE            = 2;
	public static final int CELL_STYLE_MAIN_HEADER = 0;
	public static final int CELL_STYLE_SUB_HEADER  = 1;
	public static final int CELL_STYLE_ROW_DATA    = 2;
	
	
	
	//===============================================================================================
	//PUBLIC FUNCTIONS
	//===============================================================================================
	public static Workbook createWorkbook(boolean xssf) throws IOException {
		return WorkbookFactory.create(xssf);
	}
	
	public static Row getRow(Sheet sheet, int rownum) {
		Row row = sheet.getRow(rownum);
		if (row == null) {
			row = sheet.createRow(rownum);
		}
		return row;
	}
	
	public static Cell getCell(Row row, int cellnum) {
		Cell cell = row.getCell(cellnum);
		if (cell == null) {
			cell = row.createCell(cellnum);
		}
		return cell;
	}
	
	public static Cell getCell(Sheet sheet, int rownum, int cellnum) {
		Row row = getRow(sheet, rownum);
		return getCell(row, cellnum);
	}
	
	public static void mergeCell(Sheet sheet, int startRow, int endRow, int startCol, int endCol) {
		sheet.addMergedRegion(new CellRangeAddress(startRow, endRow, startCol, endCol));
	}
	
	/**
	 * <pre>JSON 형태의 데이터를 엑셀로 변환</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 3.
	 * @param list
	 * @return
	 * @throws IOException
	 */
	public static Workbook toExcel(List<Map<String, Object>> list) throws IOException {
		Workbook workbook = createWorkbook(true);
		Sheet sheet = workbook.createSheet("데이터 목록");
		
		int rowCount = 0;
		for(Map<String, Object> data : list) {
			Row row = getRow(sheet, rowCount);
			if(rowCount == 0) {
				rowCount = writeHeader(sheet, data);
				continue;
			}
			writeRow(row, data);
			rowCount++;
		}
		
		setHeaderCellStyle(workbook);
		setRowCellStyle(workbook);
		try {
			setAutoSizeColumn(sheet);	//글꼴없을경우에러남
		} catch(Exception e) {
			
		}
		return workbook;
	}
	
	//===============================================================================================
	//PRIVATE FUNCTIONS
	//===============================================================================================
	private static CellStyle getCellStyle(Workbook workbook, int type) {
		CellStyle cellStyle = workbook.createCellStyle();
		cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
		cellStyle.setAlignment(HorizontalAlignment.CENTER);
		
		if(type == CELL_STYLE_MAIN_HEADER) {
			cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderBottom(BorderStyle.THIN);
		}else if(type == CELL_STYLE_SUB_HEADER) { 
			cellStyle.setFillForegroundColor(IndexedColors.GREY_25_PERCENT.getIndex());
			cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderBottom(BorderStyle.DOUBLE);
		}else if(type == CELL_STYLE_ROW_DATA) {
			cellStyle.setBorderTop(BorderStyle.THIN);
			cellStyle.setBorderRight(BorderStyle.THIN);
			cellStyle.setBorderLeft(BorderStyle.THIN);
			cellStyle.setBorderBottom(BorderStyle.THIN);
		}
		cellStyle.setFont(getFont(workbook));
		return cellStyle;
	}
	
	private static Font getFont(Workbook workbook) {
		Font font = workbook.createFont();
		font.setFontName(FONT_NAME);
		font.setFontHeightInPoints((short) FONT_SIZE);
		return font;
	}
	
	private static void setHeaderCellStyle(Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(0);
		
		Row row = getRow(sheet, 0);
		CellStyle headerStyle = getCellStyle(workbook, CELL_STYLE_MAIN_HEADER);
		
		Row subRow = getRow(sheet, 1);
		CellStyle subHeaderStyle = getCellStyle(workbook, CELL_STYLE_SUB_HEADER);
		
		
		int lastCellNum = row.getLastCellNum();
		for(int i=0; i<lastCellNum; i++) {
			getCell(row, i).setCellStyle(headerStyle);
		}
		int subLastCellNum = subRow.getLastCellNum();
		for(int i=0; i<subLastCellNum; i++) {
			getCell(subRow, i).setCellStyle(subHeaderStyle);
		}
	}
	
	private static void setRowCellStyle(Workbook workbook) {
		Sheet sheet = workbook.getSheetAt(0);
		
		CellStyle cellStyle = getCellStyle(workbook, CELL_STYLE_ROW_DATA);
		for(int i=HEADER_SIZE; i<=sheet.getLastRowNum(); i++) {
			Row row = getRow(sheet, i);
			int lastCellNum = row.getLastCellNum();
			
			for(int j=0; j<lastCellNum; j++) {
				getCell(row, j).setCellStyle(cellStyle);
			}
		}
		
	}
	
	private static void setAutoSizeColumn(Sheet sheet) {
		int lastCellNum = getRow(sheet, 0).getLastCellNum();
		for(int i=0; i<lastCellNum; i++) {
			sheet.autoSizeColumn(i);
		}
	}
	
	private static int writeHeader(Sheet sheet, Map<String, Object> data) {
		Row row  = getRow(sheet, 0);
		Row subRow = getRow(sheet, 1);
		
		int cellCount = 0;
		mergeCell(sheet, 0, 1, cellCount, cellCount);
		getCell(row, cellCount++).setCellValue("No");
		
		for(Entry<String, Object> entry : data.entrySet()){
			String key = entry.getKey();
			Object value = entry.getValue();
			
			if(value instanceof String) {
				mergeCell(sheet, 0, 1, cellCount, cellCount);
				getCell(row, cellCount++).setCellValue(key);
				
			}else if(value instanceof Map) {
				Map<String, Object> valueMap = (Map<String, Object>) value;
				int cellLength =  cellCount + valueMap.keySet().size() - 1;
				
				mergeCell(sheet, 0, 0, cellCount, cellLength);
				getCell(row, cellCount).setCellValue(key);
				
				for(Entry<String, Object> subEntry : valueMap.entrySet()) {
					getCell(subRow, cellCount++).setCellValue(subEntry.getKey());
				}
			}
		}
		return HEADER_SIZE;
	}
	
	private static void writeRow(Row row, Map<String, Object> data) {
		int cellCount = 0;
		
		getCell(row, cellCount++).setCellValue(row.getRowNum() - 1);
		
		for(Entry<String, Object> entry : data.entrySet()){
			Object value = entry.getValue();
			
			if(value instanceof String) {
				getCell(row, cellCount++).setCellValue(String.valueOf(value));
			}else if(value instanceof Map) {
				Map<String, Object> valueMap = (Map<String, Object>) value;
				
				for(Entry<String, Object> subEntry : valueMap.entrySet()) {
					getCell(row, cellCount++).setCellValue(String.valueOf(subEntry.getValue()));
				}
				
			}
		}
	}
	
}
