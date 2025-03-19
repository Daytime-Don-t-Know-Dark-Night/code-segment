package com.boluo.core.http;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ByteArrayEntity;
import org.apache.http.entity.ContentType;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;

import java.io.IOException;
import java.io.InputStream;

/**
 * @author chao
 * @datetime 2023-07-23 20:55
 * @description
 */
public class Https {

    private static final CloseableHttpClient http;
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(3);
        cm.setDefaultMaxPerRoute(2);
        http = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    public static JsonNode postHttp(String url, JsonNode data) {

        RequestBuilder builder = RequestBuilder.post()
                .setUri(url)
                .setHeader("User-Agent", "Mozilla/5.0")
                .setEntity(new ByteArrayEntity(data.toString().getBytes(), ContentType.APPLICATION_JSON));

        HttpUriRequest req = builder.build();
        try (CloseableHttpResponse res = http.execute(req)) {
            Preconditions.checkArgument(res.getStatusLine().getStatusCode() == 200, res);
            Thread.sleep(100);
            try (InputStream is = res.getEntity().getContent()) {
                return mapper.readTree(is);
            }
        } catch (IOException | InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

}
