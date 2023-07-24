package others;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;

import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

/**
 * @author chao
 * @datetime 2023-07-23 20:19
 * @description
 */
public class Translate {

    private static final CloseableHttpClient http;
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final String PATH = "http://api.fanyi.baidu.com/api/trans/vip/translate";
    private static final String USER_AGENT = "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36 Edg/80.0.361.69";

    static {
        PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
        cm.setMaxTotal(3);
        cm.setDefaultMaxPerRoute(2);
        http = HttpClients.custom()
                .setConnectionManager(cm)
                .build();
    }

    public static void main(String[] args) throws Exception {
        System.out.println("-----开始翻译-----");
        translateText();
        System.out.println("AAAaaaa");
    }


    public static String translateText() throws Exception {

        String query = "请你尽快回复我的消息";
        String appid = "20230723001754843";
        String securityKey = "F4qGUBPFJhUkmg2tpIsw";

        List<NameValuePair> nvps = new ArrayList();
        nvps.add(new BasicNameValuePair("q", query));
        nvps.add(new BasicNameValuePair("from", "zh"));
        nvps.add(new BasicNameValuePair("to", "en"));
        nvps.add(new BasicNameValuePair("appid", appid));

        // 随机数
        String salt = String.valueOf(System.currentTimeMillis());
        nvps.add(new BasicNameValuePair("salt", salt));

        // 签名
        String src = appid + query + salt + securityKey; // 加密前的原文
        nvps.add(new BasicNameValuePair("sign", MD5.md5(src)));

        JsonNode resp = postHttp(PATH, nvps);

        return "";
    }


    private static JsonNode postHttp(String url, List<NameValuePair> parameters) {

        RequestBuilder builder = RequestBuilder.post()
                .setUri(url)
                .setHeader("User-Agent", USER_AGENT)
                .setEntity(new UrlEncodedFormEntity(parameters, StandardCharsets.UTF_8));

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











