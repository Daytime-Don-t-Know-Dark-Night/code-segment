package boluo.stream;

import com.fasterxml.jackson.databind.ObjectMapper;
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
import org.jsoup.select.Elements;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class StreamReduce1 {

	private static final Logger logger = LoggerFactory.getLogger(StreamReduce1.class);
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

		/**
		 * @param a 表达式执行结果的缓存
		 * @param b stream中的每一个元素
		 */
		Integer sum1 = Stream.of(1, 2, 3, 4, 5).reduce(new BinaryOperator<Integer>() {
			@Override
			public Integer apply(Integer a, Integer b) {
				System.out.printf("a = %d, b = %d %n", a, b);
				return a + b;
			}
		}).get();

		Integer sum2 = Stream.of(1, 2, 3, 4, 5).reduce(0, (a, b) -> a + b);

		// 前两种计算结果的类型必须和stream中的元素类型相同,

		/**
		 * @param 初始值, 初始值为什么类型, 最后返回的结果就是什么类型
		 * @param a, a的类型与初始值及返回结果一致
		 * @param b, 中间量的类型, 也就是stream中元素的类型
		 * @param expression
		 */
		ArrayList<String> sum3 = Stream.of(1, 2, 3, 4, 5).reduce(new ArrayList<String>(), (ArrayList<String> a, Integer b) -> {
			a.add("*" + b);
			return a;
		}, (a, b) -> b);


		HttpClientContext httpContext = HttpClientContext.create();
		String url = "http://www.boluo.com";
		String body = "&type=1";

		Document d1 = historyRequest(httpContext, url, body);
		Stream<CompletableFuture<Document>> d2 = Stream.iterate(2, i -> i + 1)
				.limit(20)
				.map(i -> CompletableFuture.supplyAsync(() -> historyRequest(httpContext, url, body + "&page=" + i)));

/* 		为保证线程start之后全部执行完成再往下执行, 可以先collect全部join
		Stream<Document> s2 = d2.collect(Collectors.toList()).stream().map(CompletableFuture::join);
		Stream.concat(Stream.of(d1), s2).flatMap(tr -> {
			Elements head = tr.select("");
			return head.stream().map(i -> {
				return i.text();
			});
		});		*/

		// 关键在于 CompletableFuture<Document> -> Stream<Document>
		CompletableFuture<Stream<Document>> d3 = d2.reduce(CompletableFuture.completedFuture(Stream.of()), (CompletableFuture<Stream<Document>> a, CompletableFuture<Document> b) -> {
			CompletableFuture<Stream<Document>> res = a.thenCombine(b, (ra, rb) -> {
				return Stream.concat(ra, Stream.of(rb));
			});
			return res;
		}, (a, b) -> b);

		// 此时:
		Stream<Document> pageContext = Stream.concat(
				Stream.of(d1),
				d3.join()
		);

		// *************************************************************************************************************

/* 		另一种方式
		Stream<CompletableFuture<Document>> s2 = Stream.iterate(2, i -> i + 1)
				.limit(20)
				.map(i -> CompletableFuture.supplyAsync(() -> {
					return historyRequest(httpContext, url, body + "&page=" + i);
				}));		*/

		Stream<CompletableFuture<Stream<Document>>> s2 = Stream.iterate(2, i -> i + 1)
				.limit(20)
				.map(i -> CompletableFuture.supplyAsync(() -> {
					Document doc = historyRequest(httpContext, url, body + "&page=" + i);
					return Stream.of(doc);
				}));

		// 这里可以直接用第二种签名, 此时a, b类型一致, 为: CompletableFuture<Stream<Document>>
		CompletableFuture<Stream<Document>> s3 = s2.reduce(CompletableFuture.completedFuture(Stream.of()), (CompletableFuture<Stream<Document>> a, CompletableFuture<Stream<Document>> b) -> {
			CompletableFuture<Stream<Document>> res = a.thenCombine(b, (ra, rb) -> {
				return Stream.concat(ra, rb);
			});
			return res;
		});

		Stream.concat(Stream.of(d1), s3.join());
	}

	private static Document historyRequest(HttpClientContext httpContext, String url, String body) {
		HttpUriRequest req = RequestBuilder.post()
				.setUri(url)
				.setHeader("Content-Type", "application/x-www-form-urlencoded")
				.setHeader("Referer", "http://www.boluo.com")
				.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36 Edg/80.0.361.69")
				.setEntity(new StringEntity(body, Charsets.UTF_8))
				.build();
		try (CloseableHttpResponse response = http.execute(req, httpContext)) {
			logger.info("{}, args: {}", req.getURI(), body);
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
