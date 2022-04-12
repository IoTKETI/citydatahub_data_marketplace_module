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
package kr.co.n2m.smartcity.admin.common.config;

import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class KafkaProducerConfig {
	
	@Value("${spring.profiles.active:}")
	private String activeProfile;
	
	@Value("${spring.kafka.bootstrap-servers:}")
	private String kafkaServers;
	
	@Value("${spring.kafka.auth.user_id:}")
	private String KAFKA_USER_ID;
	
	@Value("${spring.kafka.auth.user_pw:}")
	private String KAFKA_USER_PW;
	
	private final String KAFKA_CLIENT_ID 	= "DemoProducer";
	
	/**
	 * <pre>운영 Profile 이 아닌 상태의 Kafka Producer 설정 값</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 17.
	 * @return
	 */
	public Map<String, Object> producerDefaultConfigs() {
		Map<String, Object> props =	new HashMap<String, Object>();
		
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, KAFKA_CLIENT_ID);
		props.put(ProducerConfig.ACKS_CONFIG, "1");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		
		return props;
	}
	
	/**
	 * <pre>운영 Profile 상태의 Kafka Producer 설정 값 </pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 17.
	 * @return
	 */
	public Map<String, Object> producerProdConfigs() {
		Map<String, Object> props =	new HashMap<String, Object>();
		
		props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServers);
		props.put(ProducerConfig.CLIENT_ID_CONFIG, KAFKA_CLIENT_ID);
		props.put(ProducerConfig.ACKS_CONFIG, "1");
		props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
		props.put("security.protocol", "SASL_PLAINTEXT"); 
		props.put("sasl.mechanism", "PLAIN"); 
		props.put("sasl.jaas.config", String.format("org.apache.kafka.common.security.scram.ScramLoginModule required username=\"%s\" password=\"%s\";", KAFKA_USER_ID, KAFKA_USER_PW));
		
		return props;
	}
	
	@Bean
	public KafkaProducer<String, String> kafkaProducer() {
		if ("test".equals(activeProfile)) {
			return new KafkaProducer<String, String>(producerProdConfigs());	
		}
		return new KafkaProducer<String, String>(producerDefaultConfigs());
	}
}
