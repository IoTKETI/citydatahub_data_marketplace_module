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
package kr.co.n2m.smartcity.datapublish.feature.dataset.service.requestUsage;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import kr.co.n2m.smartcity.datapublish.common.component.HttpComponent;
import kr.co.n2m.smartcity.datapublish.common.exceptions.ConflictException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.datapublish.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.common.vo.PageVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.mapper.DatasetMapper;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.DatasetVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.DatasetPricePoliciesPeriodPriceVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.DatasetPricePoliciesVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.pricePolicies.SrchDatasetPricePoliciesVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestPaymentVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestReceptionVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestRefundVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.DatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestPaymentVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestReceptionVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestRefundVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.requestUsage.SrchDatasetUseRequestVo;
import kr.co.n2m.smartcity.datapublish.feature.dataset.vo.usageHistory.SrchDatasetUseHistoryVo;

@Service
public class DatasetRequestUsageService extends HttpComponent {
	@Autowired 
	private DatasetMapper datasetMapper;
	
	@Value("${smartcity.datacore.server}")
	private String dataCoreApiUrl;
	
	/**
	 * 
	 * ???????????? ???????????? ?????? ??????
	 * @Author      : kyunga
	 * @Date        : 2019. 10. 15.
	 * @param srchDatasetVo
	 * @return
	 */
	public Map<String, Object> getDatasetUsageList(SrchDatasetUseRequestVo srchDatasetUseRequestVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = datasetMapper.selectDatasetUsageCount(srchDatasetUseRequestVo);
			srchDatasetUseRequestVo.setTotalListSize(totalListSize);
			
			List<DatasetUseRequestVo> list = datasetMapper.selectDatasetUsageList(srchDatasetUseRequestVo);
			
			if(srchDatasetUseRequestVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchDatasetUseRequestVo);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			
			resMap.put("list", list);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}
	
	/**
	 * ???????????? ???????????? ?????? 
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 24.
	 * @param datasetUseRequestVo
	 * @return 
	 * @throws Exception 
	 */
	@Transactional
	public String createDatasetUsage(SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		String resultKey = "";
		try {
			int datasetUsageCount = datasetMapper.selectDatasetUsageCount(srchDatasetUseRequestVo);
			if(datasetUsageCount > 0) {
				throw new ConflictException();
			}
			srchDatasetUseRequestVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetUsage(srchDatasetUseRequestVo);
			resultKey = String.valueOf(srchDatasetUseRequestVo.getId());
		}catch(ConflictException ce) {
			throw ce;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}
	
	/**
	 * <pre>???????????? ???????????? ????????????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 7. 15.
	 * @param datasetId
	 * @param requestUsageId
	 * @return
	 */
	public DatasetUseRequestVo getDatasetUsage(String datasetId, long requestUsageId) {
		DatasetUseRequestVo datasetUseRequestVo = null;
		try {
			datasetUseRequestVo = datasetMapper.selectDatasetUsage(datasetId, requestUsageId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetUseRequestVo;
	}


	/**
	 * <pre>
	 *  ???????????? ?????? ???????????? ????????? ??????
	 *  1) ????????? ?????? ??????
	 *  2) ???????????? ??????
	 *  3) ??????????????? ?????? ??????
	 *  ????????? ????????? ??????
	 * </pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 27.
	 * @param params ( datasetId, loginUserId )
	 * @return
	 */
	public boolean isMatchedDatasetUsage(Map<String, String> params) {
		String datasetId   = params.get("datasetId");
		String loginUserId = params.get("loginUserId");
		
		//===================================================================================
		//???????????? ?????? ??????
		DatasetVo datasetVo = datasetMapper.selectDataset(datasetId);
		
		SrchDatasetUseRequestVo srchDatasetUseRequestVo= new SrchDatasetUseRequestVo();
		srchDatasetUseRequestVo.setDatasetId(datasetId);
		srchDatasetUseRequestVo.setLoginUserId(loginUserId);
		List<DatasetUseRequestVo> datasetUsageList = datasetMapper.selectDatasetUsageList(srchDatasetUseRequestVo);
		
		//===================================================================================
		//???????????? ???????????? ?????? ??????
		DatasetUseRequestVo datasetUseRequestVo = null;
		if(datasetUsageList != null && datasetUsageList.size() > 0) {
			datasetUseRequestVo = datasetUsageList.get(0);
		}
		//???????????? ???????????? ????????? ??????
		if(datasetUseRequestVo == null) {
			if(StringUtil.equals("paidMode", datasetVo.getStatus())){
				params.put("code"   , "5004");
				params.put("message", "?????? ???????????? ???????????? ????????????.");
				return false;
			}else {
				params.put("code"   , "5004");
				params.put("message", "?????? ???????????? ???????????? ????????????.");
				return false;
			}
		}
		//===================================================================================
		//???????????????????????? ?????? ?????? ??????
		if(StringUtil.notEquals("paidMode", datasetVo.getStatus())) return true;
		//===================================================================================
		//???????????? ???????????? ?????? ??????(????????? ??????)
		SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo = new SrchDatasetUseRequestPaymentVo();
		srchDatasetUseRequestPaymentVo.setRequestId(datasetUseRequestVo.getId());
		List<DatasetUseRequestPaymentVo> datasetUseRequestPaymentList = datasetMapper.selectDatasetUsagePaymentList(srchDatasetUseRequestPaymentVo);
		DatasetUseRequestPaymentVo datasetUseRequestPaymentVo = null;
		if(datasetUseRequestPaymentList != null && datasetUseRequestPaymentList.size() > 0) {
			datasetUseRequestPaymentVo = datasetUseRequestPaymentList.get(0);
		}
		//???????????? ???????????? ?????? ??????
		if(datasetUseRequestPaymentVo == null) {
			params.put("code"   , "5004");
			params.put("message", "?????? ???????????? ???????????? ????????????.");
			return false;
		}else {
			if(StringUtil.equals("pay_request", datasetUseRequestPaymentVo.getPayStatus())){
				params.put("code"   , "5005");
				params.put("message", "????????? ?????? ?????? ???????????? ????????????.");
				return false;
			}else if(StringUtil.equals("pay_error", datasetUseRequestPaymentVo.getPayStatus())){
				params.put("code"   , "5006");
				params.put("message", "????????? ????????? ???????????? ????????????.");
				return false;
			}
		}
		//===================================================================================
		//???????????? ???????????? ?????? ??????(????????? ?????? ??????)
		SrchDatasetPricePoliciesVo srchDatasetPricePoliciesVo = new SrchDatasetPricePoliciesVo();
		srchDatasetPricePoliciesVo.setDatasetId(datasetId);
		List<DatasetPricePoliciesVo> datasetPricePoliciesList = datasetMapper.selectDatasetPricePoliciesList(srchDatasetPricePoliciesVo);
		DatasetPricePoliciesVo datasetPricePoliciesVo = null;
		if(datasetPricePoliciesList != null && datasetPricePoliciesList.size() > 0) {
			for(DatasetPricePoliciesVo datasetPricePolicies : datasetPricePoliciesList) {
				if(datasetPricePolicies.getId() == datasetUseRequestPaymentVo.getPriceId()) {
					datasetPricePoliciesVo = datasetPricePolicies;
				}
			}
		}
		//???????????? ???????????? ?????? ??????
		if(datasetPricePoliciesVo == null) {
			params.put("code"   , "5007");
			params.put("message", "???????????? ???????????? ????????? ???????????? ????????????.");
			return false;
		}
		//===================================================================================
		//????????? ???????????? ?????? ??????(?????? ????????? ???)
		Date nowDate = Calendar.getInstance().getTime();
		
		SrchDatasetUseHistoryVo srchDatasetUseHistoryVo = new SrchDatasetUseHistoryVo();
		srchDatasetUseHistoryVo.setDatasetId(datasetId);
		srchDatasetUseHistoryVo.setLoginUserId(loginUserId);
		String nowYMD = StringUtil.getCurrentDateString("yyyy-MM-dd", nowDate);
		srchDatasetUseHistoryVo.setFromDate(nowYMD);
		srchDatasetUseHistoryVo.setToDate(nowYMD);
		int trafficCount   = datasetMapper.selectDatasetUseHistoryCount(srchDatasetUseHistoryVo);
		
		//===================================================================================
		
		//1. ?????????/?????? ???????????????
		if(StringUtil.equals(datasetPricePoliciesVo.getTrafficType(), "limit")) {
			//????????? ?????? ?????? ??????
			if(trafficCount >= datasetPricePoliciesVo.getLimit()) {
				params.put("code"   , "5008");
				params.put("message", String.format("????????? ?????? ????????? ?????? ????????? ?????????????????????. (?????? ?????? ?????????: %d ???)", datasetPricePoliciesVo.getLimit()));
				return false;
			}
		}
		
		//2. ???????????? ??????
		String expiredAt = datasetUseRequestPaymentVo.getExpiredAt();
		if(StringUtil.isNotEmpty(expiredAt)) {
			try {
				Date expiredDate = StringUtil.getCurrentDate(expiredAt);
				if(expiredDate.getTime() - nowDate.getTime() < 0) {
					//?????? ??????
					params.put("code"   , "5009");
					params.put("message", String.format("???????????? ?????? ????????? ?????????????????????. (????????????: %s)", expiredAt));
					return false;
				}
			} catch (ParseException e) {
				//?????? ?????? ????????????
				params.put("code"   , "5010");
				params.put("message", String.format("???????????? ?????? ????????? ???????????? ????????????. (???: %s)", expiredAt));
				return false;
			}
			
		}
		return true;
	}
	
	/**
	 * ???????????? ???????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 9. 19.
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyDatasetUsage(SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDatasetUsage(srchDatasetUseRequestVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * <pre>???????????? ???????????? ????????????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 2. 25.
	 * @param srchDatasetUseRequestVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void patchDatasetUsage(SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDatasetUsagePart(srchDatasetUseRequestVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * ???????????? ???????????? ??????
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 21.
	 * @param datasetUseRequestVo
	 * @return
	 * @throws Exception 
	 */
	public void deleteDatasetUsage(SrchDatasetUseRequestVo srchDatasetUseRequestVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetUsage(srchDatasetUseRequestVo);
			if(iRet <= 0) throw new NotFoundException();
			
			//subscription ?????? id ??????
			int datasetSubscriptionDbCount = datasetMapper.selectDatasetSubscriptionDbCount(srchDatasetUseRequestVo);
			
			//?????? ???????????? ???????????? ??????!
			if(datasetSubscriptionDbCount == 0) {
				String datasetSubscription = datasetMapper.selectSubscriptionId(srchDatasetUseRequestVo);
				if (!"".equals(datasetSubscription)) {
					try {
						subscriptionCancelMethod(dataCoreApiUrl+"/subscriptions/"+datasetSubscription, "");						
						datasetMapper.deleteDataseSubScriptiontUsage(srchDatasetUseRequestVo);
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}
			
			//???????????? ???????????? ?????????????????? ??????
			SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo = new SrchDatasetUseRequestPaymentVo();
			srchDatasetUseRequestPaymentVo.setRequestId(srchDatasetUseRequestVo.getId());
			List<DatasetUseRequestPaymentVo> datasetUseRequestPaymentList =  datasetMapper.selectDatasetUsagePaymentList(srchDatasetUseRequestPaymentVo);
			if(datasetUseRequestPaymentList != null && datasetUseRequestPaymentList.size() > 0) {
				DatasetUseRequestPaymentVo datasetUseRequestPaymentVo = datasetUseRequestPaymentList.get(0);
				srchDatasetUseRequestPaymentVo.setId(datasetUseRequestPaymentVo.getId());
				datasetMapper.deleteDatasetUsagePayment(srchDatasetUseRequestPaymentVo);
			}
			//???????????? ???????????? ???????????? ??????
			SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo = new SrchDatasetUseRequestReceptionVo();
			srchDatasetUseRequestReceptionVo.setRequestId(srchDatasetUseRequestVo.getId());
			List<DatasetUseRequestReceptionVo> datasetUseRequestReceptionList =  datasetMapper.selectDatasetUsageReceptionList(srchDatasetUseRequestReceptionVo);
			if(datasetUseRequestReceptionList != null && datasetUseRequestReceptionList.size() > 0) {
				DatasetUseRequestReceptionVo datasetUseRequestPaymentVo = datasetUseRequestReceptionList.get(0);
				srchDatasetUseRequestReceptionVo.setId(datasetUseRequestPaymentVo.getId());
				datasetMapper.deleteDatasetUsageReception(srchDatasetUseRequestReceptionVo);
			}

		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>???????????? ???????????? ?????????????????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param srchDatasetUseRequestPaymentVo
	 * @return
	 */
	public Map<String, Object> getDatasetUsagePaymentList(SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = datasetMapper.selectDatasetUsagePaymentCount(srchDatasetUseRequestPaymentVo);
			srchDatasetUseRequestPaymentVo.setTotalListSize(totalListSize);
			
			List<DatasetUseRequestPaymentVo> list = datasetMapper.selectDatasetUsagePaymentList(srchDatasetUseRequestPaymentVo);
			
			if(srchDatasetUseRequestPaymentVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchDatasetUseRequestPaymentVo);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			
			resMap.put("list", list);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}

	/**
	 * <pre>???????????? ???????????? ?????????????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param srchDatasetUseRequestPaymentVo
	 * @return
	 */
	public String createDatasetUsagePayment(SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo) {
		String resultKey = "";
		try {
			srchDatasetUseRequestPaymentVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetUsagePayment(srchDatasetUseRequestPaymentVo);
			resultKey = String.valueOf(srchDatasetUseRequestPaymentVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>???????????? ???????????? ?????????????????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param datasetId
	 * @param requestUsageId
	 * @param payId
	 * @return
	 */
	public DatasetUseRequestPaymentVo getDatasetUsagePayment(long requestUsageId, long payId) {
		DatasetUseRequestPaymentVo datasetUseRequestPaymentVo = null;
		try {
			datasetUseRequestPaymentVo = datasetMapper.selectDatasetUsagePayment(payId, requestUsageId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetUseRequestPaymentVo;
	}

	/**
	 * <pre>???????????? ???????????? ?????????????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param srchDatasetUseRequestPaymentVo
	 * @throws Exception 
	 */
	public void modifyDatasetUsagePayment(SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo) throws Exception {
		try {
			String payStatus = srchDatasetUseRequestPaymentVo.getPayStatus();
			
			//???????????? ????????? ???????????? ??????
			if(StringUtil.equals(payStatus, "pay_complete")) {
				DatasetUseRequestPaymentVo datasetUseRequestPaymentVo = 
						datasetMapper.selectDatasetUsagePayment(srchDatasetUseRequestPaymentVo.getId(), srchDatasetUseRequestPaymentVo.getRequestId());
				
				DatasetPricePoliciesPeriodPriceVo datasetPricePoliciesPeriodPriceVo = 
						datasetMapper.selectDatasetPricePoliciesPeriodPrice(datasetUseRequestPaymentVo.getPriceId(), datasetUseRequestPaymentVo.getPeriodId());
				
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DATE, Integer.valueOf(datasetPricePoliciesPeriodPriceVo.getPeriodCd()));
				srchDatasetUseRequestPaymentVo.setExpiredAt(StringUtil.getCurrentDateString(calendar.getTime()));
			}
					
			int iRet = datasetMapper.updateDatasetUsagePayment(srchDatasetUseRequestPaymentVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>???????????? ???????????? ?????????????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 13.
	 * @param srchDatasetUseRequestPaymentVo
	 * @throws Exception 
	 */
	public void deleteDatasetUsagePayment(SrchDatasetUseRequestPaymentVo srchDatasetUseRequestPaymentVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetUsagePayment(srchDatasetUseRequestPaymentVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>???????????? ???????????? ????????? ???????????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 19.
	 * @param srchDatasetUseRequestReceptionVo
	 * @return
	 */
	public Map<String, Object> getDatasetUsageReceptionList(SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = datasetMapper.selectDatasetUsageReceptionCount(srchDatasetUseRequestReceptionVo);
			srchDatasetUseRequestReceptionVo.setTotalListSize(totalListSize);
			
			List<DatasetUseRequestReceptionVo> list = datasetMapper.selectDatasetUsageReceptionList(srchDatasetUseRequestReceptionVo);
			
			if(srchDatasetUseRequestReceptionVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchDatasetUseRequestReceptionVo);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			
			resMap.put("list", list);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}

	/**
	 * <pre>???????????? ???????????? ????????? ???????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 19.
	 * @param srchDatasetUseRequestReceptionVo
	 * @return
	 */
	public String createDatasetUsageReception(SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo) {
		String resultKey = "";
		try {
			srchDatasetUseRequestReceptionVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetUsageReception(srchDatasetUseRequestReceptionVo);
			resultKey = String.valueOf(srchDatasetUseRequestReceptionVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
		
	}

	/**
	 * <pre>???????????? ???????????? ????????? ???????????? ????????????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 19.
	 * @param datasetId
	 * @param requestUsageId
	 * @param recvId
	 * @return
	 */
	public DatasetUseRequestReceptionVo getDatasetUsageReception(String datasetId, long requestUsageId, long recvId) {
		DatasetUseRequestReceptionVo datasetUseRequestReceptionVo = null;
		try {
			datasetUseRequestReceptionVo = datasetMapper.selectDatasetUsageReception(datasetId, requestUsageId, recvId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetUseRequestReceptionVo;
	}

	/**
	 * <pre>???????????? ???????????? ????????? ???????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 19.
	 * @param srchDatasetUseRequestReceptionVo
	 * @throws Exception 
	 */
	public void modifyDatasetUsageReception(SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDatasetUsageReception(srchDatasetUseRequestReceptionVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
	}

	/**
	 * <pre>???????????? ???????????? ????????? ???????????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 11. 19.
	 * @param srchDatasetUseRequestReceptionVo
	 * @throws Exception 
	 */
	public void deleteDatasetUsageReception(SrchDatasetUseRequestReceptionVo srchDatasetUseRequestReceptionVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetUsageReception(srchDatasetUseRequestReceptionVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * <pre>???????????? ???????????? ?????? ?????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 */
	public Map<String, Object> getDatasetUsageRefundList(SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo) throws Exception {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = datasetMapper.selectDatasetUsageRefundCount(srchDatasetUseRequestRefundVo);
			srchDatasetUseRequestRefundVo.setTotalListSize(totalListSize);
			
			List<DatasetUseRequestRefundVo> list = datasetMapper.selectDatasetUsageRefundList(srchDatasetUseRequestRefundVo);
			
			if(srchDatasetUseRequestRefundVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchDatasetUseRequestRefundVo);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			
			resMap.put("list", list);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
		
	}

	/**
	 * <pre>???????????? ???????????? ?????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param srchDatasetUseRequestRefundVo
	 * @return
	 */
	public String createDatasetUsageRefund(SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo) throws Exception {
		String resultKey = "";
		try {
			srchDatasetUseRequestRefundVo.setId(StringUtil.generateKey());
			datasetMapper.insertDatasetUsageRefund(srchDatasetUseRequestRefundVo);
			resultKey = String.valueOf(srchDatasetUseRequestRefundVo.getId());
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resultKey;
	}

	/**
	 * <pre>???????????? ???????????? ?????? ?????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param datasetId
	 * @param requestUsageId
	 * @param refundId
	 * @return
	 */
	public DatasetUseRequestRefundVo getDatasetUsageRefund(String datasetId, String requestUsageId, String refundId) throws Exception {
		DatasetUseRequestRefundVo datasetUseRequestRefundVo = null;
		try {
			datasetUseRequestRefundVo = datasetMapper.selectDatasetUsageRefund(datasetId, requestUsageId, refundId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return datasetUseRequestRefundVo;
	}

	/**
	 * <pre>???????????? ???????????? ?????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param srchDatasetUseRequestRefundVo
	 */
	public void modifyDatasetUsageRefund(SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo) throws Exception {
		try {
			int iRet = datasetMapper.updateDatasetUsageRefund(srchDatasetUseRequestRefundVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
	}

	/**
	 * <pre>???????????? ???????????? ?????? ?????? ??????</pre>
	 * @Author      : hk-lee
	 * @Date        : 2020. 12. 7.
	 * @param srchDatasetUseRequestRefundVo
	 */
	public void deleteDatasetUsageRefund(SrchDatasetUseRequestRefundVo srchDatasetUseRequestRefundVo) throws Exception {
		try {
			int iRet = datasetMapper.deleteDatasetUsageRefund(srchDatasetUseRequestRefundVo);
			if(iRet <= 0) throw new NotFoundException();
		}catch(NotFoundException ne) {
			throw ne;
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
	}

}
