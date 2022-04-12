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

import java.security.KeyFactory;
import java.security.PublicKey;
import java.security.spec.X509EncodedKeySpec;
import java.util.Date;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.util.Base64Utils;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import kr.co.smartcity.user.common.exceptions.HttpUnauthorizedExcpetion;
import kr.co.smartcity.user.common.util.StringUtil;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class AuthComponent extends HttpComponent{
	
	/**
	 * 
	 * <pre>인증 토큰 유효성 체크</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @param token
	 * @return
	 * @throws Exception 
	 */
	public boolean validateToken(String authToken) throws Exception {
		
		boolean bRet = false;
		
		try {
			if ( StringUtil.isEmpty(authToken)) return false;

			String token = authToken.replaceAll("Bearer", "").trim();
			
			Jws<Claims> claims = parseClaimsJws(token);
			if ( claims == null ) return false;
			if(!claims.getBody().getExpiration().before(new Date())) {
				bRet = true;
			}
		} catch (ExpiredJwtException e) {
			bRet = false;
		} catch (Exception ex) {
			throw ex;
		}
		return bRet;
		
	}
	
	/**
	 * 
	 * <pre>Alg (RSA) 공개키 정보 조회</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @return
	 * @throws Excpetion 
	 */
	public PublicKey getPublicKey(String token) throws Exception {
		
		PublicKey publicKey = null;
		
		OkHttpClient client = new OkHttpClient();
		Request request = new Request.Builder()
				.addHeader("Authorization", "Bearer " + token)
				.url(props.getUserPublicKeyUrl()).get().build();
		
		try {
			Response response = client.newCall(request).execute();
			String responseStr = response.body().string();
			Map<String, Object> responseMap = toMap(responseStr);
			String publicKeyStr = (String) responseMap.get("publickey");

			KeyFactory kf = null;
		
			kf = KeyFactory.getInstance("RSA");
			
			String publicKeyContent = publicKeyStr.replaceAll("\r\n", "").replace("-----BEGIN PUBLIC KEY-----", "").replace("-----END PUBLIC KEY-----", "").replaceAll(System.lineSeparator(), "");
			X509EncodedKeySpec keySpecX509 = new X509EncodedKeySpec(Base64Utils.decodeFromString(publicKeyContent));
			publicKey = kf.generatePublic(keySpecX509);

		} catch (Exception e) {
			throw e;
		}
		return publicKey;
	}
	
	/**
	 * 
	 * <pre>인증토큰 디코딩</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @param publicKey
	 * @param authToken
	 * @return
	 * @throws Exception 
	 */
	public Jws<Claims> parseClaimsJws(String authToken) throws Exception {
		if ( StringUtil.isEmpty(authToken)) return null;

		String token = authToken.replaceAll("Bearer", "").trim();
		
		Jws<Claims> claims = null;
		
		try {
			claims = Jwts.parser().setSigningKey(getPublicKey(token)).parseClaimsJws(token);
		} catch (ExpiredJwtException e) {
			throw new HttpUnauthorizedExcpetion(e);
		} catch (Exception e) {
			throw e;
		}
		return claims;
	}
	
	/**
	 * 
	 * <pre>Payload 정보의 ID값 반환</pre>
	 * @Author		: junyl
	 * @Date		: 2019. 10. 7.
	 * @param authToken
	 * @return
	 * @throws Exception 
	 */
	public String getUserId(String authToken) throws Exception {
		
		String id = "";
		try {
			Jws<Claims> claims = parseClaimsJws(authToken);
			
			if ( claims != null ) {
				Claims payload = claims.getBody();
				id = (String) payload.get("userId");
			}
		} catch (Exception e) {
			throw e;
		}
		return id; 
	}
	
}
