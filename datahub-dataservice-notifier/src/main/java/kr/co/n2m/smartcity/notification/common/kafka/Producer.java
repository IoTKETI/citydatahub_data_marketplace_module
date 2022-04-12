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
package kr.co.n2m.smartcity.notification.common.kafka;

import java.util.Map;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.clients.producer.RecordMetadata;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.google.gson.Gson;

import ch.qos.logback.classic.Logger;
import kr.co.n2m.smartcity.notification.common.component.CommonComponent;
import kr.co.n2m.smartcity.notification.common.utils.StringUtil;

@Component
public class Producer extends CommonComponent {
	
	@Value("${spring.kafka.bootstrap-servers}")
	private String kafkaServers;
	
	@Autowired
	private KafkaProducer<String, String> kafkaProducer;
	
	@Autowired Gson gson;
	
	
	@Async("threadPoolTaskSender")
	public void send(String topic, Map<String, Object> sendData) {
		
		String trName = Thread.currentThread().getName();
		logger("Thread Name : "+trName);
		
		logger("##########################");
		logger(topic);
		logger(kafkaServers);
		logger("##########################");
		String sendDataJson = gson.toJson(sendData);
		logger("##########################");
		logger("전달데이터 : " + sendDataJson);
		logger("##########################");
		
		long startTime = System.currentTimeMillis();
    	
		logger("##########################");
		
    	ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>(topic, StringUtil.getLocalHostName("localhost"), sendDataJson);
    	kafkaProducer.send(producerRecord, new KafkaCallBack(startTime, sendDataJson));
	}
}

class KafkaCallBack implements Callback {
	
	private Logger logger = (Logger) LoggerFactory.getLogger(this.getClass());
	
	private final long startTime;
	private final String message;
	
	public KafkaCallBack(long startTime, String message) {
		this.startTime = startTime;
		this.message = message;
	}
	
	public void onCompletion(RecordMetadata metadata, Exception exception) {   //비동기 콜백 함수         
		long elapsedTime = System.currentTimeMillis() - startTime;
		if (metadata != null) {
			logger.info("respone-message(" +  message + ") sent to partition(" + metadata.partition() +"), " + "offset(" + metadata.offset() + ") in " + elapsedTime + "ms");
		} else {
			logger.info("error!!!!!!!!!!");
		}
	}
}
