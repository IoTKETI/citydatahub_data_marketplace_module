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
package kr.co.n2m.smartcity.datapublish.feature.data.service;

import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.retrofit.RetrofitService;
import kr.co.n2m.smartcity.datapublish.common.utils.ExcelUtil;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.DatasetService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.columns.DatasetColumnsService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.datasetOrigin.DatasetOriginService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.instance.DatasetInstanceService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.requestUsage.DatasetRequestUsageService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.service.searchCondition.DatasetSearchConditionService;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.DatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.columns.SrchDatasetOutputVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.DatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.datasetOrigin.SrchDatasetOriginVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.instance.DatasetInstanceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.instance.SrchDatasetInstanceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.sample.SimpleDatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.DatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.searchCondition.SrchDatasetSearchInfoVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.usageHistory.SrchDatasetUseHistoryVo;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Service
public class DataService extends CommonComponent{
	
	@Value("${smartcity.datacore.server}")
	private String dataCoreApiUrl;
	
	@Value("${smartcity.datamanager.server}")
	private String datamanagerServerUrl;
	
	@Autowired
	@Qualifier("customHttpClient")
	private OkHttpClient client;
	
	/**
	 * 데이터셋 서비스
	 */
	@Autowired DatasetService datasetService;
	@Autowired DatasetInstanceService datasetInstanceService;
	@Autowired DatasetRequestUsageService datasetRequestUsageService;
	@Autowired DatasetColumnsService datasetColumnsService;
	@Autowired DatasetSearchConditionService datasetSearchConditionService;
	@Autowired DatasetOriginService datasetOriginService;
	
	@Autowired RetrofitService retrofitService;
	
	/**
	 * Kafka Producer
	 */
//	@Autowired 
//	private Producer kafkaProducer;

	/**
	 * 
	 * <pre>
	 * 데이터셋 이용신청을 한 사용자에게 데이터(Open API) Polling 방식으로 데이터를 제공 한다.
	 * 데이터셋 상태 구분에 따라 제공받을 수 있는 대상자가 구분이 된다.
	 * 
	 * "개발 데이터셋"
	 *   -> 데이터모델 소유자 및 협력자로 등록 된 사용자에게만 데이터 제공.
	 * 
	 * "출시 데이터셋"
	 *   -> 이용신청을 한 사용자에게 데이터 제공.
	 * </pre>
	 * 
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @param datasetId	: 데이터셋 고유번호
	 * @param isTest	: 샘플테스트 진행여부 (이용신청 체크 하지 않음)
	 * @param params
	 * @return
	 * @throws Exception
	 */
	public ResponseEntity<Object> getDataList(String datasetId, String loginUserId, Map<String, String> params, boolean isTest) throws Exception {
		
		
		/*
		 * -----------------------------------------------------
		 * Call URI 예시
		 * -----------------------------------------------------
		 * http://localhost:7930/dataservice/dataset/{datasetId}/data?datatype=historydata&options=normalizedHistory&timeAt=between&timeproperty=modifiedAt&time=2019-10-01T00:20:00Z&endtime=2019-10-01T01:00:00Z&q=refParkingLot=="urn:datahub:OffStreetParking:yt_lot_2"
		 * 
		 * 
		 * -----------------------------------------------------
		 * process flow
		 * -----------------------------------------------------
		 * 1. 요청 파라미터 유효성 검사
		 * 2. 데이터셋 상세정보 조회
		 *   2-1 : [개발데이터셋] 본인 또는 협력자 대상인지 확인
		 *   2-2 : [출시데이터셋] 이용신청 대상인지 확인
		 * 3. 데이터셋 출력정보 조회
		 * 4. DataLake API 서버 데이터 호출 및 NGSI_LD 가공처리
		 * 5. 활용 내역 저장
		 * 6. 데이터 회신 
		 */
		
		String resultStr = "";
		try {
			
			// 1. 요청 파라미터 유효성 검사
			Map<String, String> mValidatedParam = StringUtil.validateRequestParams(params);
			
			// 2. 데이터셋 상세 조회
			SimpleDatasetVo datasetObj = datasetService.getSimpleDatasetProd(datasetId);
			
			if ( datasetObj == null ) throw new GlobalException(5001, "일치하는 데이터셋ID가 존재하지 않습니다. : " + datasetId);
			
			String datasetStatus 					= datasetObj.getDsStatus(); // 데이터셋 상태
			
			SrchDatasetInstanceVo srchDatasetInstanceVo = new SrchDatasetInstanceVo();
			srchDatasetInstanceVo.setDatasetId(datasetId);
			
			//TODO 데이터 규격 1.0 -> 2.0 으로 변경 할 것
			List<DatasetInstanceVo> instanceList 	= datasetInstanceService.getDatasetInstanceList(srchDatasetInstanceVo); // 데이터셋 인스턴스리스트
			
			// 데이터 모델상세 조회 
			Request.Builder builder = new Request.Builder();
			Request request = builder
					.addHeader("Accept", "application/json")
					.url(HttpUrl.parse(datamanagerServerUrl + "/datamodels/" + datasetObj.getDsModelId()).newBuilder().build())
					.get()
					.build();
			
			Response response = client.newCall(request).execute();
			if (!response.isSuccessful()) throw new GlobalException(5006, "데이터모델 상세 정보를 조회 할 수 없습니다. : " + datasetObj.getDsModelId());
			
			Map<String, Object> dataModel = toMap(response.body().string());
			
			mValidatedParam.put("type", (String) dataModel.get("typeUri"));

			// 정식 이용요청건인 경우
			if (!isTest) {
				//================================================================
				//이용신청 대상자 유효성체크
				Map<String, String> validParams = new HashMap<>();
				validParams.put("datasetId"  , datasetId);
				validParams.put("loginUserId", loginUserId);
				if (!datasetRequestUsageService.isMatchedDatasetUsage(validParams)) {
					throw new GlobalException(Integer.valueOf(validParams.get("code")), validParams.get("message"));
				}
				//================================================================
			}
			
			// 3. 데이터셋 출력정보 조회
			SrchDatasetOutputVo srchDatasetOutputVo = new SrchDatasetOutputVo();
			srchDatasetOutputVo.setDatasetId(datasetId);
			List<DatasetOutputVo> outputList = datasetColumnsService.getDatasetOutputList(srchDatasetOutputVo);
			if (outputList == null || outputList.size() == 0) {
				throw new GlobalException(5005, "데이터셋의 출력정보가 존재하지 않습니다. : " + datasetId);
			}

			// 4. DataLake API 조회 및 NGSI_LD 포맷 JSON 가공처리
			// TODO. 조회 조건이 인스턴스형인 경우에만 해당되도록. 우선 데이터 존재여부로 판단.
			if (instanceList.size() > 0) {
				String instanceId = instanceList.stream()
						.map(instanceInfo -> String.valueOf(instanceInfo.getInstanceId()))
						.collect(Collectors.joining(","));
				
				mValidatedParam.put("id", instanceId);
			}
			
			SrchDatasetSearchInfoVo srchDatasetSearchInfoVo = new SrchDatasetSearchInfoVo();
			srchDatasetSearchInfoVo.setDatasetId(datasetId);
			List<DatasetSearchInfoVo> searchInfoList = datasetSearchConditionService.getDatasetSearchInfoList(srchDatasetSearchInfoVo);
			
			
			List<String> paramArr = new ArrayList<>();
			for(DatasetSearchInfoVo searchInfo : searchInfoList) {
				String param = "";
				String mainKey = searchInfo.getMainAttribute();
				String subKey = searchInfo.getSubAttribute();
				String operator = searchInfo.getSymbolName();
				String searchValue = searchInfo.getValue();
				
				//서브키가 있을때
				if(StringUtil.isNotEmpty(subKey)) {
					//검색값이 숫자일경우
					if(StringUtil.isNumeric(searchValue)) {
						param= String.format("%s[%s]%s%s", mainKey, subKey, operator, searchValue);
					}else {
					//검색값이 문자열일경우
						param = String.format("%s[%s]%s\"%s\"", mainKey, subKey, operator, searchValue);
					}
					//서브키가 없을때
				}else {
					//검색값이 숫자일경우
					if(StringUtil.isNumeric(searchValue)) {
						param= String.format("%s%s%s", mainKey, operator, searchValue);
					}else {
					//검색값이 문자열일경우
						param = String.format("%s%s\"%s\"", mainKey, operator, searchValue);
					}
				}
				paramArr.add(param);
			}
			
			//DATASET ORIGIN 정보 추가
			SrchDatasetOriginVo srchDatasetOriginVo = new SrchDatasetOriginVo();
			srchDatasetOriginVo.setDatasetId(datasetId);
			List<DatasetOriginVo> datasetOriginList = datasetOriginService.getDatasetOriginList(srchDatasetOriginVo);
			if(datasetOriginList != null && datasetOriginList.size() > 0) {
				List<String> datasetIdList = new ArrayList<>();
				for(DatasetOriginVo datasetOriginVo : datasetOriginList) {
					datasetIdList.add(datasetOriginVo.getDatasetOriginId());
				}
				mValidatedParam.put("datasetId", String.join(",", datasetIdList));
			}
			
			if(paramArr.size() > 0) {
				String qParam = String.join(";", paramArr);
				mValidatedParam.put("q", qParam);
			}
			resultStr = retrofitService.getConvertData(outputList, mValidatedParam);

			if(!isTest) {
				//데이터셋 활용내역 등록(http, ondemand)
				SrchDatasetUseHistoryVo srchDatasetUseHistoryVo = new SrchDatasetUseHistoryVo();
				srchDatasetUseHistoryVo.setId(StringUtil.generateKey());
				srchDatasetUseHistoryVo.setDatasetId(datasetId);
				srchDatasetUseHistoryVo.setUserId(loginUserId);
				srchDatasetUseHistoryVo.setProtocol("http");
				srchDatasetUseHistoryVo.setTransmissionType("ondemand");
				srchDatasetUseHistoryVo.setPayTf("paidMode".equals(datasetStatus));
				datasetService.createDatasetUseHistory(srchDatasetUseHistoryVo);
			}else {
				resultStr = toJson(StringUtil.convertNgsiLdToJsonMap(toList(resultStr)));
			}
			
		} catch (Exception e) {
			throw e;
		}
		// 8. 데이터 회신
		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);
		return new ResponseEntity<Object>(resultStr, headers, HttpStatus.OK);
	}

	public void downloadDataListExcel(String datasetId, String loginUserId, Map<String, String> params, HttpServletResponse response) throws Exception {

		
		
		/*
		 * -----------------------------------------------------
		 * Call URI 예시
		 * -----------------------------------------------------
		 * http://localhost:7930/dataservice/dataset/{datasetId}/data?datatype=historydata&options=normalizedHistory&timeAt=between&timeproperty=modifiedAt&time=2019-10-01T00:20:00Z&endtime=2019-10-01T01:00:00Z&q=refParkingLot=="urn:datahub:OffStreetParking:yt_lot_2"
		 * 
		 * 
		 * -----------------------------------------------------
		 * process flow
		 * -----------------------------------------------------
		 * 1. 요청 파라미터 유효성 검사
		 * 2. 데이터셋 상세정보 조회
		 *   2-1 : [개발데이터셋] 본인 또는 협력자 대상인지 확인
		 *   2-2 : [출시데이터셋] 이용신청 대상인지 확인
		 * 3. 데이터셋 출력정보 조회
		 * 4. DataLake API 서버 데이터 호출 및 NGSI_LD 가공처리
		 * 5. 활용 내역 저장
		 * 6. 데이터 회신 
		 */
		
		String resultStr = "";
		try {
			
			// 1. 요청 파라미터 유효성 검사
			Map<String, String> mValidatedParam = StringUtil.validateRequestParams(params);
			
			// 2. 데이터셋 상세 조회
			SimpleDatasetVo datasetObj = datasetService.getSimpleDatasetProd(datasetId);
			
			if ( datasetObj == null ) throw new GlobalException(5001, "일치하는 데이터셋ID가 존재하지 않습니다. : " + datasetId);
			
			String datasetStatus 					= datasetObj.getDsStatus(); // 데이터셋 상태
			
			SrchDatasetInstanceVo srchDatasetInstanceVo = new SrchDatasetInstanceVo();
			srchDatasetInstanceVo.setDatasetId(datasetId);
			List<DatasetInstanceVo> instanceList 	= datasetInstanceService.getDatasetInstanceList(srchDatasetInstanceVo); // 데이터셋 인스턴스리스트
			
			mValidatedParam.put("type", datasetObj.getDsModelName()); 

			//================================================================
			//이용신청 대상자 유효성체크
			Map<String, String> validParams = new HashMap<>();
			validParams.put("datasetId"  , datasetId);
			validParams.put("loginUserId", loginUserId);
			if (!datasetRequestUsageService.isMatchedDatasetUsage(validParams)) {
				throw new GlobalException(Integer.valueOf(validParams.get("code")), validParams.get("message"));
			}
			//================================================================
			
			// 3. 데이터셋 출력정보 조회
			SrchDatasetOutputVo srchDatasetOutputVo = new SrchDatasetOutputVo();
			srchDatasetOutputVo.setDatasetId(datasetId);
			List<DatasetOutputVo> outputList = datasetColumnsService.getDatasetOutputList(srchDatasetOutputVo);
			if (outputList == null || outputList.size() == 0) {
				throw new GlobalException(5005, "데이터셋의 출력정보가 존재하지 않습니다. : " + datasetId);
			}

			// 4. DataLake API 조회 및 NGSI_LD 포맷 JSON 가공처리
			// TODO. 조회 조건이 인스턴스형인 경우에만 해당되도록. 우선 데이터 존재여부로 판단.
			if (instanceList.size() > 0) {
				String instanceId = instanceList.stream()
						.map(instanceInfo -> String.valueOf(instanceInfo.getInstanceId()))
						.collect(Collectors.joining(","));
				
				mValidatedParam.put("id", instanceId);
			}
			
			SrchDatasetSearchInfoVo srchDatasetSearchInfoVo = new SrchDatasetSearchInfoVo();
			srchDatasetSearchInfoVo.setDatasetId(datasetId);
			List<DatasetSearchInfoVo> searchInfoList = datasetSearchConditionService.getDatasetSearchInfoList(srchDatasetSearchInfoVo);
			
			
			List<String> paramArr = new ArrayList<>();
			for(DatasetSearchInfoVo searchInfo : searchInfoList) {
				String param = "";
				String mainKey = searchInfo.getMainAttribute();
				String subKey = searchInfo.getSubAttribute();
				String operator = searchInfo.getSymbolName();
				String searchValue = searchInfo.getValue();
				
				//서브키가 있을때
				if(StringUtil.isNotEmpty(subKey)) {
					//검색값이 숫자일경우
					if(StringUtil.isNumeric(searchValue)) {
						param= String.format("%s[%s]%s%s", mainKey, subKey, operator, searchValue);
					}else {
					//검색값이 문자열일경우
						param = String.format("%s[%s]%s\"%s\"", mainKey, subKey, operator, searchValue);
					}
					//서브키가 없을때
				}else {
					//검색값이 숫자일경우
					if(StringUtil.isNumeric(searchValue)) {
						param= String.format("%s%s%s", mainKey, operator, searchValue);
					}else {
					//검색값이 문자열일경우
						param = String.format("%s%s\"%s\"", mainKey, operator, searchValue);
					}
				}
				paramArr.add(param);
			}
			
			//DATASET ORIGIN 정보 추가
			SrchDatasetOriginVo srchDatasetOriginVo = new SrchDatasetOriginVo();
			srchDatasetOriginVo.setDatasetId(datasetId);
			List<DatasetOriginVo> datasetOriginList = datasetOriginService.getDatasetOriginList(srchDatasetOriginVo);
			if(datasetOriginList != null && datasetOriginList.size() > 0) {
				String datasetIdParams = "datasetId==";
				for(DatasetOriginVo datasetOriginVo : datasetOriginList) {
					String datasetOriginId = datasetOriginVo.getDatasetOriginId();
					datasetIdParams += "\"" + datasetOriginId + "\",";
				}
				paramArr.add(datasetIdParams);
			}
			
			if(paramArr.size() > 0) {
				String qParam = String.join(";", paramArr);
				mValidatedParam.put("q", qParam);
			}
			
			
			resultStr = retrofitService.getConvertData(outputList, mValidatedParam);

			//데이터셋 활용내역 등록(http, ondemand)
			SrchDatasetUseHistoryVo srchDatasetUseHistoryVo = new SrchDatasetUseHistoryVo();
			srchDatasetUseHistoryVo.setId(StringUtil.generateKey());
			srchDatasetUseHistoryVo.setDatasetId(datasetId);
			srchDatasetUseHistoryVo.setUserId(loginUserId);
			srchDatasetUseHistoryVo.setProtocol("http");
			srchDatasetUseHistoryVo.setTransmissionType("ondemand");
			srchDatasetUseHistoryVo.setPayTf("paidMode".equals(datasetStatus));
			datasetService.createDatasetUseHistory(srchDatasetUseHistoryVo);
			
			//====엑셀 다운로드====
			
			//1. NGSI-LD 데이터 재구성
			List<Map<String, Object>> dataList = StringUtil.convertNgsiLdToJsonMap(toList(resultStr));
			
			//2. 엑셀 생성
			Workbook workbook = ExcelUtil.toExcel(dataList);
			Sheet sheet = workbook.createSheet("검색조건");
			ExcelUtil.mergeCell(sheet, 0, 0, 0, 1);
			Row row = null;
			
			row = ExcelUtil.getRow(sheet, 0);
			ExcelUtil.getCell(row, 0).setCellValue(String.valueOf("검색조건"));
			
			row = ExcelUtil.getRow(sheet, 1);
			ExcelUtil.getCell(row, 0).setCellValue(String.valueOf("시작일"));
			ExcelUtil.getCell(row, 1).setCellValue(String.valueOf(params.get("time")));
			
			row = ExcelUtil.getRow(sheet, 2);
			ExcelUtil.getCell(row, 0).setCellValue(String.valueOf("종료일"));
			ExcelUtil.getCell(row, 1).setCellValue(String.valueOf(params.get("endtime")));
			
			
			String fileName = "";
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
			String nowDate = sdf.format(Calendar.getInstance().getTime());
			fileName += datasetObj.getDsTitle();
			fileName += "_";
			fileName += nowDate;
			
			response.setCharacterEncoding("UTF-8");
			response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet");
			response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(fileName, "UTF-8") + ".xlsx\"");
			workbook.write(response.getOutputStream());
		} catch (Exception e) {
			throw e;
		}
	}

	public ResponseEntity<Object> checkDataList(String datasetId, String loginUserId, Map<String, String> params) throws Exception {
		try {
			// 1. 요청 파라미터 유효성 검사
			StringUtil.validateRequestParams(params);
			
			// 2. 데이터셋 상세 조회
			SimpleDatasetVo datasetObj = datasetService.getSimpleDatasetProd(datasetId);
			if ( datasetObj == null ) throw new GlobalException(5001, "일치하는 데이터셋ID가 존재하지 않습니다. : " + datasetId);
			
			// 3. 이용신청 대상자 유효성 검사
			Map<String, String> validParams = new HashMap<>();
			validParams.put("datasetId"  , datasetId);
			validParams.put("loginUserId", loginUserId);
			if (!datasetRequestUsageService.isMatchedDatasetUsage(validParams)) {
				throw new GlobalException(Integer.valueOf(validParams.get("code")), validParams.get("message"));
			}
		} catch (Exception e) {
			throw e;
		}
		return new ResponseEntity<Object>(null, null, HttpStatus.OK);
	}
}
