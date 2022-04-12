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
package kr.co.n2m.smartcity.admin.feature.codegroup.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.beanutils.BeanUtils;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.component.CommonComponent;
import kr.co.n2m.smartcity.admin.common.exceptions.ConflictException;
import kr.co.n2m.smartcity.admin.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.admin.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.admin.common.kafka.KafkaMessageCode;
import kr.co.n2m.smartcity.admin.common.kafka.ProducerForSync;
import kr.co.n2m.smartcity.admin.common.vo.PageVo;
import kr.co.n2m.smartcity.admin.feature.codegroup.mapper.CodeGroupMapper;
import kr.co.n2m.smartcity.admin.feature.codegroup.mapper.CodeMapper;
import kr.co.n2m.smartcity.admin.feature.codegroup.vo.CodeGroupVo;
import kr.co.n2m.smartcity.admin.feature.codegroup.vo.CodeVo;
import kr.co.n2m.smartcity.admin.feature.codegroup.vo.SrchCodeGroupVo;
import kr.co.n2m.smartcity.admin.feature.codegroup.vo.SrchCodeVo;

@Service
public class CodeGroupService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private CodeGroupMapper codeGroupMapper;
	@Autowired
	private CodeMapper codeMapper;
	
	@Autowired ProducerForSync kafkaProducer;
	
	/**
	 * 코드그룹 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeGroupVo
	 * @return
	 */
	@Transactional
	public String createCodeGroup(SrchCodeGroupVo srchCodeGroupVo) {
		String id;
		try {
			int count = codeGroupMapper.insertCodeGroup(srchCodeGroupVo);
			if(count<=0) throw new GlobalException();
			id = srchCodeGroupVo.getCodeGroupId();
			Map<String, Object> sendData = new HashMap<>();
			sendData.put("code", KafkaMessageCode.CODEGROUP_DATA_REG_SYNC.message());
			sendData.put("data", srchCodeGroupVo);
			kafkaProducer.send(sendData);
			
		}catch(Exception e) {
			throw e;
		}
		return id;
	}
	
	/**
	 * 코드그룹 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeGroupVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyCodeGroup(SrchCodeGroupVo srchCodeGroupVo) throws Exception {
		try {
			int count = codeGroupMapper.updateCodeGroup(srchCodeGroupVo);
			if(count<=0) throw new NotFoundException();
			
			Map<String, Object> sendData = new HashMap<>();
			sendData.put("code", KafkaMessageCode.CODEGROUP_DATA_MOD_SYNC.message());
			sendData.put("data", srchCodeGroupVo);
			kafkaProducer.send(sendData);
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 코드그룹 목록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeGroupVo
	 * @return
	 */
	public Map<String, Object> getCodeGroupList(SrchCodeGroupVo srchCodeGroupVo,HashMap<String, String> params) {
		Map<String, Object> resMap = new HashMap<>();
		Gson gson = new Gson();
		String allYN ="";
		allYN = gson.fromJson(params.get("all"), String.class);
		if(allYN == null) {
			try {
				int totalListSize = codeGroupMapper.selectCodeGroupCount(srchCodeGroupVo);
				srchCodeGroupVo.setTotalListSize(totalListSize);
				
				List<CodeGroupVo> list = codeGroupMapper.selectCodeGroupList(srchCodeGroupVo);
				if (srchCodeGroupVo.isPaging()) {
					PageVo pageVo = new PageVo();
					pageVo.setTotalListSize(totalListSize);
					BeanUtils.copyProperties(pageVo, srchCodeGroupVo);
					
					resMap.put("page", pageVo);
				} else {
					resMap.put("page", null);
				}
				
				resMap.put("list", list);
			}catch(Exception e) {
				throw new GlobalException(e);
			}
			return resMap;
		}else{
			try {
				List<CodeGroupVo> codeGroupList = codeGroupMapper.selectAllCode(srchCodeGroupVo);
				resMap.put("codeGroupList", codeGroupList);
			}catch(Exception e) {
				throw new GlobalException(e);
			}
			return resMap;	
		}
		
	}

	/**
	 * 코드그룹 카운트 목록
	 * @Author      : hk-lee
	 * @Date        : 2019. 10. 18.
	 * @param srchCodeGroupVo
	 * @return
	 */
	public int getCodeGroupCount(SrchCodeGroupVo srchCodeGroupVo) {
		return codeGroupMapper.selectCodeGroupCount(srchCodeGroupVo);
	}
	
	/**
	 * 코드그룹 상세
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeGroupVo
	 * @return
	 */
	public CodeGroupVo getCodeGroup(String codeGroupId) {
			CodeGroupVo codeGroupVo = null;
		try {
			codeGroupVo = codeGroupMapper.selectCodeGroup(codeGroupId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return codeGroupVo;
	}

	/**
	 * 코드 등록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeVo
	 * @return
	 */
	@Transactional
	public String createCode(SrchCodeVo srchCodeVo) {
		String id;
		try {
			codeMapper.insertCode(srchCodeVo);
			id = srchCodeVo.getCodeGroupId();
			
			Map<String, Object> sendData = new HashMap<>();
			sendData.put("code", KafkaMessageCode.CODE_DATA_REG_SYNC.message());
			sendData.put("data", srchCodeVo);
			kafkaProducer.send(sendData);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return id;
	}
	
	/**
	 * 코드 목록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeVo
	 * @return
	 */
	public Map<String,Object> getCodeList(SrchCodeVo srchCodeVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			int totalListSize = codeMapper.selectCodeCount(srchCodeVo);
			srchCodeVo.setTotalListSize(totalListSize);
			
			List<CodeVo> list = codeMapper.selectCodeList(srchCodeVo);
			if (srchCodeVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchCodeVo);
				
				resMap.put("page", pageVo);
			} else {
				resMap.put("page", null);
			}
			resMap.put("list", list);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;	
	}
	
	/**
	 * 코드 상세
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeVo
	 * @return
	 */
	public CodeVo getCode(String codeGroupId, String codeId) {
		CodeVo codeVo = null;
		try {
			codeVo = codeMapper.selectCode(codeGroupId, codeId);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return codeVo;	
	}
	
	/**
	 * 코드 수정
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 15.
	 * @param srchCodeVo
	 * @return
	 */
	@Transactional
	public void modifyCode(SrchCodeVo srchCodeVo) {
		try {
			int count = codeMapper.updateCode(srchCodeVo);
			if(count<=0) throw new NotFoundException();
			Map<String, Object> sendData = new HashMap<>();
			sendData.put("code", KafkaMessageCode.CODE_DATA_MOD_SYNC.message());
			sendData.put("data", srchCodeVo);
			kafkaProducer.send(sendData);
			
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}

	/**
	 * 
	 * 코드 전체 목록
	 * @Author      : hk-lee
	 * @Date        : 2019. 7. 16.
	 * @param srchCodeVo
	 * @return
	 */
	public Map<String,Object> getAllCode(SrchCodeGroupVo srchCodeGroupVo) {
		Map<String, Object> resMap = new HashMap<>();
		try {
			List<CodeGroupVo> codeGroupList = codeGroupMapper.selectAllCode(srchCodeGroupVo);
			resMap.put("list", codeGroupList);
			resMap.put("page", null);
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return resMap;	
	}

	/**
	 * 코드그룹ID 중복 체크
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 20.
	 * @param codeGroupVo
	 * @return
	 */
	public void checkCodeGroupId(SrchCodeGroupVo srchCodeGroupVo) {
		try {
			int count = codeGroupMapper.selectCodeGroupCountById(srchCodeGroupVo);
			if (count > 0) throw new ConflictException();
		}catch(Exception e) {
			throw new GlobalException(e);
		}
	}
	
	/**
	 * 코드ID 중복 체크
	 * @Author      : hk-lee
	 * @Date        : 2019. 8. 20.
	 * @param codeVo
	 * @return
	 */
	public int checkCodeId(SrchCodeVo srchCodeVo) {
		int count;
		try {
			count = codeMapper.selectCodeCountById(srchCodeVo);
			if (count > 0) throw new ConflictException();
		}catch(Exception e) {
			throw new GlobalException(e);
		}
		return count;
	}

	

}
