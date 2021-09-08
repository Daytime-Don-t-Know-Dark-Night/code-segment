package boluo.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.base.Preconditions;
import org.apache.commons.compress.utils.Charsets;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.client.protocol.HttpClientContext;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class Reduce1 {

	private static final Logger logger = LoggerFactory.getLogger(Reduce1.class);
	private static final ObjectMapper mapper = new ObjectMapper();
	private static final CloseableHttpClient http;

	static {
		PoolingHttpClientConnectionManager cm = new PoolingHttpClientConnectionManager();
		cm.setMaxTotal(3);
		cm.setDefaultMaxPerRoute(3);
		http = HttpClients.custom()
				.setConnectionManager(cm)
				.build();
	}

	public static void main(String[] args) {

		HttpClientContext httpContext = HttpClientContext.create();
		// login(httpContext);

		String url = "http://www.baidu.com";
		String body = "";

		Document d1 = postRequest(httpContext, url, body + "&pageNum=" + 1);
		// Stream<CompletableFuture<Document>> d2 = ;
	}

	public static Document postRequest(HttpClientContext httpContext, String url, String body) {
		HttpUriRequest req = RequestBuilder.post()
				.setUri(url)
				.setHeader("Content-Type", "application/x-www-form-urlencoded")
				.setHeader("Referer", "http://www.saywash.com/saywash/WashCallManager/merchant/order/historyorder/index.do")
				.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36 Edg/80.0.361.69")
				.setEntity(new StringEntity(body, Charsets.UTF_8))
				.build();

		try (CloseableHttpResponse response = http.execute(req, httpContext)) {
			logger.info("{}, args: {}", req.getURI(), body);
			Preconditions.checkArgument(response.getStatusLine().getStatusCode() == 200, response);
			Thread.sleep(100);

			InputStream is = response.getEntity().getContent();
			return Jsoup.parse(is, "utf-8", "");
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}

	}
}
