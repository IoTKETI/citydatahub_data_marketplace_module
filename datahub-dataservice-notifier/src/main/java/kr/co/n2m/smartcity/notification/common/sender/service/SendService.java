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
package kr.co.n2m.smartcity.notification.common.sender.service;

import java.net.URI;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import kr.co.n2m.smartcity.notification.common.component.CommonComponent;
import kr.co.n2m.smartcity.notification.common.kafka.Producer;
import kr.co.n2m.smartcity.notification.common.sender.modules.http.sender.HttpSender;
import kr.co.n2m.smartcity.notification.common.sender.modules.mqtt.sender.MqttSender;
import kr.co.n2m.smartcity.notification.common.sender.modules.websocket.sender.WebSocketSender;
import kr.co.n2m.smartcity.notification.common.sender.modules.websocket.sender.WebsocketClientEndpoint;


@Service
public class SendService extends CommonComponent{
	@Autowired Producer kafkaProducer;
	@Autowired HttpSender httpSender;
	@Autowired WebSocketSender webSocketSender;
	@Autowired MqttSender mqttSender;
	
	@Value("${spring.mqtt.mqtt-servers}")
	private String mqttServer;
	
	@Value("${spring.websocket.websocket-servers}")
	private String websocketServer;
	
	public void sendMessage(Map<String, Object> params) throws Exception{
		logger("params: "+params);
		try {
			String sendUrl 	= 	(String) params.getOrDefault("sendUrl", "");
			String topic    = 	sendUrl.substring(sendUrl.lastIndexOf("/")+1,sendUrl.length());
			String data     = 	(String) params.getOrDefault("data", "").toString();
			String type     = 	sendUrl.contains("http") ? "http" : 
				              	sendUrl.contains("mqtt") ? "mqtt" :	"ws";
			logger("type >>>>>>>>>>>>>>>>>>>>>>>>>> "+type);
			logger("topic >>>>>>>>>>>>>>>>>>>>>>>>>> "+topic);
			
			switch (type) {
			case "http":
				Map<String, Object> httpData = new HashMap<String, Object>();
				
				httpData.put("data", data);
				httpSender.send(sendUrl, httpData);
				break;
				
			case "mqtt":
		        final Consumer<HashMap<Object, Object>> message = (arg)->{
		            arg.forEach((key, value)->{ System.out.println( String.format("%s,%s", key, value) ); });            
		        };    
		        
		        mqttSender.connect(mqttServer, "");
		        mqttSender.sender(topic, data);
		        mqttSender.close();
				
				break;
				
			case "ws":
				WebsocketClientEndpoint clientEndPoint = new WebsocketClientEndpoint(new URI(websocketServer+topic));
				
				logger("WebsocketClientEndpoint sendMessage: "+data);
				clientEndPoint.sendMessage(data);
				break;

			default:
				break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
