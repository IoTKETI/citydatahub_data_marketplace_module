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
package kr.co.n2m.smartcity.admin.feature.program.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.LoggerFactory;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.admin.common.component.CommonComponent;
import kr.co.n2m.smartcity.admin.common.exceptions.GlobalException;
import kr.co.n2m.smartcity.admin.common.exceptions.NotFoundException;
import kr.co.n2m.smartcity.admin.common.utils.StringUtil;
import kr.co.n2m.smartcity.admin.common.vo.PageVo;
import kr.co.n2m.smartcity.admin.feature.program.mapper.ProgramGroupMapper;
import kr.co.n2m.smartcity.admin.feature.program.mapper.ProgramMapper;
import kr.co.n2m.smartcity.admin.feature.program.vo.ProgramGroupVo;
import kr.co.n2m.smartcity.admin.feature.program.vo.ProgramVo;
import kr.co.n2m.smartcity.admin.feature.program.vo.SrchProgramGroupVo;
import kr.co.n2m.smartcity.admin.feature.program.vo.SrchProgramVo;

@Service
public class ProgramService extends CommonComponent{
	
	Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private ProgramMapper programMapper;
	
	@Autowired
	private ProgramGroupMapper programGroupMapper;

	/**
	 * 프로그램 목록 조회 
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getProgramList(SrchProgramVo srchProgramVo) {
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			int totalListSize = programMapper.selectProgramCount(srchProgramVo);
			srchProgramVo.setTotalListSize(totalListSize);
			
			List<ProgramVo> list = programMapper.selectProgramList(srchProgramVo);
			if(srchProgramVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchProgramVo);
				pageVo.setTotalListSize(totalListSize);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			resMap.put("list", list);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return resMap;
	}

	/**
	 * 프로그램 권한 목록 조회
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getAuthProgramList(SrchProgramVo srchProgramVo) {
		Map<String, Object> resMap = new HashMap<String, Object>();
		try {
			List<ProgramVo> programList = programMapper.selectAuthProgramList(srchProgramVo);
			resMap.put("page", null);
			resMap.put("list", programList);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
		return resMap;
	}

	/**
	 * 프로그램 상세 조회 
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramVo
	 * @return
	 */
	@Transactional
	public ProgramVo getProgram(long programOid, long programGroupOid) {
		ProgramVo programVo = null;
		try {
			programVo = programMapper.selectProgram(programOid, programGroupOid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
		return programVo;
	}

	/**
	 * 프로그램 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramVo
	 * @return
	 */
	@Transactional
	public String createProgram(SrchProgramVo srchProgramVo) {
		long id;
		try {
			srchProgramVo.setProgramOid(StringUtil.generateKey());
			id = srchProgramVo.getProgramgroupOid();
			int count = programMapper.insertProgram(srchProgramVo);
			if(count<=0) throw new GlobalException();

		}catch(Exception e) {
			throw e;
		}
		
		return String.valueOf(id);
	}
	
	/**
	 * 프로그램 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyProgram(SrchProgramVo srchProgramVo) throws Exception {
		
		try {
			int count = programMapper.updateProgram(srchProgramVo);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 프로그램 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteProgram(SrchProgramVo srchProgramVo) throws Exception {
		
		try {
			int count = programMapper.deleteProgram(srchProgramVo);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
		
	}
	
	/**
	 * 프로그램 그룹 목록 조회
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramGroupVo
	 * @return
	 */
	@Transactional
	public Map<String,Object> getProgramGroupList(SrchProgramGroupVo srchProgramGroupVo) {
		Map<String, Object> resMap = new HashMap<>();
		
		try {
			int totalListSize = programGroupMapper.selectProgramGroupCount(srchProgramGroupVo);
			srchProgramGroupVo.setTotalListSize(totalListSize);
			List<ProgramGroupVo> list = programGroupMapper.selectProgramGroupList(srchProgramGroupVo);
			if(srchProgramGroupVo.isPaging()) {
				PageVo pageVo = new PageVo();
				BeanUtils.copyProperties(pageVo, srchProgramGroupVo);
				pageVo.setTotalListSize(totalListSize);
				resMap.put("page", pageVo);
			}else{
				resMap.put("page", null);
			}
			resMap.put("list", list);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		
		return resMap;
	}
	
	/**
	 * 프로그램 그룹 상세 조회
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramGroupVo
	 * @return
	 */
	@Transactional
	public ProgramGroupVo getProgramGroup(long programGroupOid) {
		ProgramGroupVo programGroupVo = null;
		try {
			programGroupVo = programGroupMapper.selectProgramGroup(programGroupOid);
		} catch (Exception e) {
			throw new GlobalException(e);
		}
		return programGroupVo;
	}

	/**
	 * 프로그램 그룹 등록
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramGroupVo
	 * @return
	 */
	@Transactional
	public String createProgramGroup(SrchProgramGroupVo srchProgramGroupVo) {
		long id;
		
		try {
			srchProgramGroupVo.setProgramgroupOid(StringUtil.generateKey());
			id = srchProgramGroupVo.getProgramgroupOid();
			int count = programGroupMapper.insertProgramGroup(srchProgramGroupVo);
			if(count<=0) throw new GlobalException();

		}catch(Exception e) {
			throw new GlobalException(e);
		}
		
		return String.valueOf(id); 
	}
	
	/**
	 * 프로그램 그룹 수정
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramGroupVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void modifyProgramGroup(SrchProgramGroupVo srchProgramGroupVo) throws Exception {
		try {
			int count = programGroupMapper.updateProgramGroup(srchProgramGroupVo);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}

	/**
	 * 프로그램 그룹 삭제
	 * @Author      : junheecho
	 * @Date        : 2019. 8. 20.
	 * @param srchProgramGroupVo
	 * @return
	 * @throws Exception 
	 */
	@Transactional
	public void deleteProgramGroup(SrchProgramGroupVo srchProgramGroupVo) throws Exception {
		
		try {
			int count = programGroupMapper.deleteSubProgram(srchProgramGroupVo);
			if(count<=0) throw new NotFoundException();
			count = programGroupMapper.deleteProgramGroup(srchProgramGroupVo);
			if(count<=0) throw new NotFoundException();
		}catch(Exception e) {
			throw e;
		}
	}
	
}
