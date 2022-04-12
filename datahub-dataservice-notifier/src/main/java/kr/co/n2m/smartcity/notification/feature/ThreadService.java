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
package kr.co.n2m.smartcity.notification.feature;

import java.util.Map;
import java.util.concurrent.Future;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import kr.co.n2m.smartcity.notification.common.component.HttpComponent;
import kr.co.n2m.smartcity.notification.common.kafka.KafkaMessageCode;
import kr.co.n2m.smartcity.notification.common.sender.service.SendService;

@Component
public class ThreadService extends HttpComponent {
	
	@Autowired SendService sendService;
	
	@Async("threadPoolTaskReceiver")
	public Future<String> process(String message) throws Exception {
		
		String trName = Thread.currentThread().getName();
		logger.debug("Thread Name : "+trName);
		
		Map<String, Object> params = convertMap(message);
		
		String code = (String) params.get("code");
		KafkaMessageCode messageCode = KafkaMessageCode.getMessageCode(code);
		logger.debug("messageCode Name : "+messageCode);
		switch(messageCode) {
		case INGESTLAYER_TO_DATA_PUBLISH:
			try {
				sendService.sendMessage(params);
				// ProductMS Kafka 서버 전송
				// TODO : 전송로직 추가
				
				// StatisticsMS Kafka 서버 전송
				// TODO : 전송로직 추가
				
			} catch (Exception ex) {
				// 200 NOK
				logger(ex.getMessage());
				// TODO
				// 실패 된 경우 재전송을 위해 실패 TOPIC 으로 보내도록 함!!
			}
			break;
		default:
			break;
		}
		
		return new AsyncResult<String>("Success");
	}
}
