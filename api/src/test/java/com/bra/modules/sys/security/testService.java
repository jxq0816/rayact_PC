package com.bra.modules.sys.security;

import org.junit.Assert;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * Created by xiaobin on 16/2/16.
 */
public class TestService {

    private RestTemplate restTemplate = new RestTemplate();

    @Test
    public void testServiceHelloSuccess() {
        String username = "admin";
        String param11 = "param11";
        String param12 = "param12";
        String param2 = "param2";
        String key = "dadadswdewq2ewdwqdwadsadasd";
        MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
        params.add("username", username);
        params.add("param1", param11);
        params.add("param1", param12);
        params.add("param2", param2);
        String digest = HmacSHA256Utils.digest(key, params);
        System.out.println("digest==="+digest);
        params.add("digest", digest);
        String url = UriComponentsBuilder
                .fromHttpUrl("http://localhost:8080/api/hello/")
                .queryParams(params).build().toUriString();
        ResponseEntity responseEntity = restTemplate.getForEntity(url, String.class);
        System.out.println(responseEntity.getBody());
       // Assert.assertEquals("hello" + param11 + param12 + param2, responseEntity.getBody());
    }
}
