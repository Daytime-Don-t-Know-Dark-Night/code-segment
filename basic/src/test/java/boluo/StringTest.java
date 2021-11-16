package boluo;

import org.junit.Assert;

import java.lang.management.ManagementFactory;
import java.lang.management.MemoryMXBean;
import java.lang.management.MemoryUsage;
import java.util.stream.Stream;

public class StringTest {

	// TODO 不开辟新的空间, 将字符串最后一位截掉
	public static void main(String[] args) {

		StringBuilder sb = new StringBuilder("ABCDE");
		Stream.iterate(1, i -> i + 1).limit(200000).forEach(i -> sb.append("ABCDE"));

		MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
		MemoryUsage memoryUsage = memoryMXBean.getHeapMemoryUsage(); //堆内存使用情况
		long maxMemorySize = memoryUsage.getMax(); //最大可用内存
		System.out.println("最大可用内存: " + maxMemorySize);

		String str = sb.toString();
		String str2 = null;
		str2 = str.substring(0, str.length() - 1);
		Assert.assertEquals(str2.length(), str.length() - 1);
	}
}
