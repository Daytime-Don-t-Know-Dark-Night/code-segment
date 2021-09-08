package boluo.jsoup;

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
		String cookie = "Hm_lvt_194df3105ad7148dcf2b98a91b5e727a=1631095642; lastCity=100010000; __g=-; __c=1631095643; __l=l=%2Fwww.zhipin.com%2Fjob_detail%2F%3Fquery%3D%25E9%2594%2580%25E5%2594%25AE%26city%3D101120800%26industry%3D100504%26position%3D&s=3&friend_source=0; __a=80672064.1631095643..1631095643.2.1.2.2; __zp_stoken__=67f6cC3xVDTVhX19GaDl5NGMpe0RATx1lFkYkKUVrcjA6YFsVWzBjO11dGm4rMX0eWjtNRFdEcVICNhcZKG4SIyVmMiF1MTdtelF9Zn4ZOgcYYgMeZ1MSHyg0cQ1RRwJcahhdV3c1DlB%2BCQU5; Hm_lpvt_194df3105ad7148dcf2b98a91b5e727a=1631096964";

		Document d1 = currRequest(httpContext, url, cookie);
		Elements es = d1.select("div .primary-wrapper div div span.job-name");
		Elements es2 = d1.select("div .primary-wrapper div div .red");
		Elements es3 = d1.select("div .company-text h3");
		List<String> position = es.stream().map(Element::text).collect(Collectors.toList());
		List<String> salary = es2.stream().map(Element::text).collect(Collectors.toList());
		List<String> company = es3.stream().map(Element::text).collect(Collectors.toList());

		// Preconditions.checkNotNull(position.size() == salary.size(), "...");

		for (int i = 0; i < position.size(); i++) {
			System.out.println(position.get(i) + ", " + salary.get(i) + ", " + company.get(i));
		}

		List<Document> nextRes = Stream.iterate(2, i -> i + 1).limit(7)
				.map(i -> currRequest(httpContext, url + "&page=" + i, cookie))
				.collect(Collectors.toList());


		System.out.println(d1);
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
