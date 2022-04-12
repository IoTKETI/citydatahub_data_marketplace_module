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
package kr.co.smartcity.user.common.component;

import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
public class Properties {

	@Value("${Globals.oauthAdminClientId}") 
	private String adminClientId;
	
	@Value("${Globals.oauthAdminClientSecretId}") 
	private String adminClientSecretId;
	
	@Value("${Globals.oauthNormalClientId}") 
	private String normalClientId;
	
	@Value("${Globals.oauthNormalClientSecretId}") 
	private String normalClientSecretId;
	
	@Value("${Globals.portalMs}")
	private String portalMsApiUrl;
	
	@Value("${Globals.dataPublishMs}")
	private String datapublishMsApiUrl;
	
	@Value("${Globals.oauthRedirectUrl}")
	private String oauthRedirectUri;
	
	@Value("#{'${Globals.sso.site}'.split(',')}")
	private List<String> authSSOSite;
	
	@Value("${Globals.oauthAdminServerUrl}")
	private String authAdminServerUrl;
	
	@Value("${Globals.oauthNormalServerUrl}")
	private String authNormalServerUrl;
	
	@Value("${Globals.oauthAuthorizationCodeUrl}")
	private String authorizationCodeUrl;
	
	@Value("${Globals.oauthAdminAccessTokenUrl}")
	private String adminAccessTokenUrl;
	
	@Value("${Globals.oauthNormalAccessTokenUrl}")
	private String normalAccessTokenUrl;
	
	@Value("${Globals.oauthAdminUserInfoUrl}")
	private String adminUserInfoUrl;
	
	@Value("${Globals.oauthNormalUserInfoUrl}")
	private String normalUserInfoUrl;
	
	@Value("${Globals.oauthPublickeyUrl}")
	private String userPublicKeyUrl;
	
	@Value("${Globals.oauthLogout}")
	private String logoutUrl;
	
	@Value("${Globals.dataCoreServerUrl}")
	private String dataCoreServerUrl;
	
	@Value("${Globals.dataModelApiUrl}")
	private String dataModelApiUrl;
	
	@Value("${Globals.dataEntitiesServerUrl}")
	private String dataEntitiesServerUrl;
	
	@Value("${Globals.blockChainServerUrl}")
	private String blockChainServerUrl;
	
	@Value("${Globals.gateway.public.ip}")
	private String gatewayPublicIp;
	
	@Value("${Globals.division}")
	private String mode;
	
	@Value("${Globals.platformServerUrl}")
	private String platformServerUrl;
	
	@Value("${Globals.mqtt.ip}")
	private String mqttIp;
	
	@Value("${Globals.websocket.ip}")
	private String websocketIp;
}
