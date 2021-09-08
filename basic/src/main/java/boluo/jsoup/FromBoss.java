package boluo.jsoup;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.google.common.base.Preconditions;
import org.apache.commons.compress.utils.Charsets;
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
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FromBoss {

	private static final ObjectMapper mapper = new ObjectMapper();
	private static final Logger logger = LoggerFactory.getLogger(FromBoss.class);
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

		String url = "https://www.zhipin.com/job_detail/?query=%E9%94%80%E5%94%AE&city=101120800&industry=100504&position=";

		HttpClientContext httpContext = HttpClientContext.create();
		String cookie = "SERVERID=606144fb348bc19e48aededaa626f54e|1631107279|1631107278; ___gtid=-587531361; __fid=e6ac7fa0f4ec09a8e4366339d60fc5c6; lastCity=100010000; __c=1631107312; __g=-; __a=43415077.1631022356.1631026318.1631107312.7.3.1.7; __zp_stoken__=67f6cC31zIFAeAQ50PjJ5DWkTWAJjUSBIYggCOFU4bjcibCl3BV95NmMpAl8pNigkWjtNRFduBDpVHzkZUVMnJzFWODYlIzZlKVdzYXgZOgcYYgMkMlQQLjBATwBLKFxcahhdV3c1DlB%2BCQU5";

		Document d1 = currRequest(httpContext, url, cookie);
		Stream<Document> nextRes = Stream.iterate(2, i -> i + 1).limit(7)
				.map(i -> currRequest(httpContext, url + "&page=" + i, cookie));

		List<ObjectNode> res = Stream.concat(
				Stream.of(d1),
				nextRes
		).flatMap(i -> {
			Elements es = i.select("div");
			return es.stream().map(tr -> {

				ObjectNode obj = mapper.createObjectNode();
				Elements es1 = tr.select(".primary-wrapper div div span.job-name");
				Elements es2 = tr.select(".primary-wrapper div div .red");
				Elements es3 = tr.select(".company-text h3");

				Preconditions.checkNotNull(es1.size() == es2.size(), "...");
				for (int k = 0; k < es1.size(); k++) {
					obj.put("位置", es1.get(k).text());
					obj.put("薪水", es2.get(k).text());
					obj.put("公司", es3.get(k).text());
				}
				return obj;
			});

		}).collect(Collectors.toList());

		System.out.println(res);
	}


	private static Document currRequest(HttpClientContext httpContext, String url, String cookie) {

		HttpUriRequest req = RequestBuilder.get()
				.setUri(url)
				.setHeader("accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/avif,image/webp,image/apng,*/*;q=0.8,application/signed-exchange;v=b3;q=0.9")
				.setHeader("accept-encoding", "gzip, deflate, br")
				.setHeader("accept-language", "zh-CN,zh;q=0.9,en-US;q=0.8,en;q=0.7")
				.setHeader("cache-control", "max-age=0")
				.setHeader("cookie", cookie)
				.setHeader("Host", "www.zhipin.com")
				.setHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/93.0.4577.63 Safari/537.36")

				.build();

		try (CloseableHttpResponse response = http.execute(req, httpContext)) {

			logger.info("{}", req.getURI());
			Preconditions.checkArgument(response.getStatusLine().getStatusCode() == 200, response);
			Thread.sleep(100);
			try (InputStream is = response.getEntity().getContent()) {
				return Jsoup.parse(is, "utf-8", "");
			}
		} catch (IOException | InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
