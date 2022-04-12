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
package kr.co.n2m.smartcity.datapublish.common.kafka;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PreDestroy;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import kr.co.n2m.smartcity.datapublish.common.component.CommonComponent;
import kr.co.n2m.smartcity.datapublish.common.utils.StringUtil;
import kr.co.n2m.smartcity.datapublish.feature.kafka.service.ThreadService;

@Component
public class Consumer extends CommonComponent implements ApplicationRunner {

	private static final Logger LOG = LoggerFactory.getLogger(Consumer.class);
	
	@Value("${spring.profiles.active:}")
	private String activeProfile;
	
	@Autowired
	private KafkaConsumer<String, Object> kafkaConsumer;
	
	@Autowired 
	private ThreadService threadService;
	
	/** Kafka Consumer Thread 구동 flag **/ 
	private boolean isBreak = false;
	
	/**
	 * 
	 * <pre>Kafka 수신부</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 21.
	 */
	public void receive() {
		
		Collection<String> topics = new ArrayList<String>();
		topics.add(KafkaConst.DATAPUBLISH.topic());
		topics.add(KafkaConst.STRUCTURED_DATA_INGEST_SUCCESS_EVENT.topic());
		topics.add(KafkaConst.DATASET_ADAPTOR_PROCESS_EVENT.topic());
		kafkaConsumer.subscribe(topics);
		
		String receiverKey = StringUtil.getLocalHostName("localhost");
		
		while(!isBreak) {
			ConsumerRecords<String, Object> records = kafkaConsumer.poll(Duration.ofMillis(500));
    		
			try {
				for (ConsumerRecord<String, Object> record : records) {
					
					String topic		= (String) record.topic();
					String key 			= (String) record.key();
					
					byte[] b 			= (byte[]) record.value();
					String message		= new String(b);
					
					LOG.debug(String.format("activeProfile = %s, record key = %s, receiver key = %s, Topic : %s, Received message : %s", activeProfile, key, receiverKey, topic, message));
					
					if ((StringUtil.equals("local", activeProfile) || StringUtil.isEmpty(activeProfile)) && !StringUtil.equals(key, receiverKey)) {
						break;
					}

    				if ( KafkaConst.STRUCTURED_DATA_INGEST_SUCCESS_EVENT.topic().equals(topic)) {
    					
    					String bodyMessage = new String(b, 5, b.length - 5);
    					
    					Map<String, Object> sendDataMap = new HashMap<>();
    					sendDataMap.put("code"	, KafkaMessageCode.INGESTLAYER_TO_DATA_PUBLISH.message());
    					sendDataMap.put("data"	, bodyMessage); 
    					
    					message = toJson(sendDataMap);
    					
    				} else if ( KafkaConst.DATASET_ADAPTOR_PROCESS_EVENT.topic().equals(topic)) {
    					Map<String, Object> sendDataMap = new HashMap<>();
    					sendDataMap.put("code"	, KafkaMessageCode.PROC_DATASET_ADAPTOR_MOD.message());
    					sendDataMap.put("data"	, message); 
    					
    					message = toJson(sendDataMap);
    					
    				}
    				threadService.process(message);
				}
			} catch (Exception e) {
				LOG.error(e.getMessage(), e);
			}
		}
		Thread.currentThread().interrupt();
	}

	/**
	 * 
	 * <pre>어플리케이션 구동 완료 시 Kafka Consumer 구동</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 28.
	 */
	@Override
	public void run(ApplicationArguments args) throws Exception {
		new Thread(new Runnable() {
			
			@Override
			public void run() {
				receive();
			}
		}).start();
	}
	
	/**
	 * 
	 * <pre>어플리케이션 종료 시 Kafka Consumer 종료</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 28.
	 */
	@PreDestroy
	public void close() {
		LOG.debug("Pre destory Application.. stoping demon..");
		isBreak = true;
	}
}
