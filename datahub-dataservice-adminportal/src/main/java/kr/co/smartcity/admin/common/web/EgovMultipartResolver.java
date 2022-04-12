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
package kr.co.smartcity.admin.common.web;

/*
 * Copyright 2001-2006 The Apache Software Foundation.
 *
 * Licensed under the Apache License, Version 2.0 (the ";License&quot;);
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS"; BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartResolver;

/**
 * 실행환경의 파일업로드 처리를 위한 기능 클래스
 * @author 공통서비스개발팀 이삼섭
 * @since 2009.06.01
 * @version 1.0
 * @see
 *
 * <pre>
 * << 개정이력(Modification Information) >>
 *
 *   수정일      수정자           수정내용
 *  -------    --------    ---------------------------
 *   2009.3.25  이삼섭          최초 생성
 *   2011.06.11 서준식          스프링 3.0 업그레이드 API변경으로인한 수정
 *
 * </pre>
 */
public class EgovMultipartResolver extends CommonsMultipartResolver {
	private static final String MULTIPART = "multipart/";
	
    public EgovMultipartResolver() {
    }
    @Override
    public boolean isMultipart(HttpServletRequest request) {
    	return request != null && isMultipartContent(request);
    }

    public static final boolean isMultipartContent(HttpServletRequest request) {
        final String method = request.getMethod().toLowerCase();
        if (!method.equals("post") && !method.equals("put")) {
            return false;
        }
        String contentType = request.getContentType();
        if (contentType == null) {
            return false;
        }
        if (contentType.toLowerCase().startsWith(MULTIPART)) {
            return true;
        }
        return false;
    }
    
    /**
     * 첨부파일 처리를 위한 multipart resolver를 생성한다.
     *
     * @param servletContext
     */
    public EgovMultipartResolver(ServletContext servletContext) {
	super(servletContext);
    }

    /**
     * multipart에 대한 parsing을 처리한다.
     */
    @SuppressWarnings("rawtypes")
	@Override
    protected MultipartParsingResult parseFileItems(List fileItems, String encoding) {

    //스프링 3.0변경으로 수정한 부분
    MultiValueMap<String, MultipartFile> multipartFiles = new LinkedMultiValueMap<String, MultipartFile>();
	Map<String, String[]> multipartParameters = new HashMap<String, String[]>();

	// Extract multipart files and multipart parameters.
	for (Iterator<?> it = fileItems.iterator(); it.hasNext();) {
	    FileItem fileItem = (FileItem)it.next();

	    if (fileItem.isFormField()) {

		String value = null;
		if (encoding != null) {
		    try {
			value = fileItem.getString(encoding);
		    } catch (UnsupportedEncodingException ex) {
			if (logger.isWarnEnabled()) {
			    logger.warn("Could not decode multipart item '" + fileItem.getFieldName() + "' with encoding '" + encoding
				    + "': using platform default");
			}
			value = fileItem.getString();
		    }
		} else {
		    value = fileItem.getString();
		}
		String[] curParam = multipartParameters.get(fileItem.getFieldName());
		if (curParam == null) {
		    // simple form field
		    multipartParameters.put(fileItem.getFieldName(), new String[] { value });
		} else {
		    // array of simple form fields
		    String[] newParam = StringUtils.addStringToArray(curParam, value);
		    multipartParameters.put(fileItem.getFieldName(), newParam);
		}
	    } else {

		if (fileItem.getSize() > 0) {
		    // multipart file field
		    CommonsMultipartFile file = new CommonsMultipartFile(fileItem);


		    //스프링 3.0 업그레이드 API변경으로인한 수정
		    List<MultipartFile> fileList = new ArrayList<MultipartFile>();
		    fileList.add(file);


		    if (multipartFiles.put(fileItem.getName(), fileList) != null) { // CHANGED!!
			throw new MultipartException("Multiple files for field name [" + file.getName()
				+ "] found - not supported by MultipartResolver");
		    }
		    if (logger.isDebugEnabled()) {
			logger.debug("Found multipart file [" + file.getName() + "] of size " + file.getSize() + " bytes with original filename ["
				+ file.getOriginalFilename() + "], stored " + file.getStorageDescription());
		    }
		}

	    }
	}

	return new MultipartParsingResult(multipartFiles, multipartParameters, null);
    }
}
