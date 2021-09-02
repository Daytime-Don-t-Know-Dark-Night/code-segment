package boluo.memory;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class ListMemoryTest {

	public static void main(String[] args) throws NoSuchFieldException, IllegalAccessException {

		Runtime r = Runtime.getRuntime();
		long startRAM = r.freeMemory();

		List<String> listRAM = new ArrayList<>();
		int loopTimes = 1000000;

		String str = "君不见黄河之水天上来，奔流到海不复回。\n" +
				"君不见高堂明镜悲白发，朝如青丝暮成雪。\n" +
				"人生得意须尽欢，莫使金樽空对月。\n" +
				"天生我材必有用，千金散尽还复来。\n" +
				"烹羊宰牛且为乐，会须一饮三百杯。\n" +
				"岑夫子，丹丘生，将进酒，杯莫停。\n" +
				"与君歌一曲，请君为我倾耳听。\n" +
				"钟鼓馔玉不足贵，但愿长醉不复醒。\n" +
				"古来圣贤皆寂寞，惟有饮者留其名。\n" +
				"陈王昔时宴平乐，斗酒十千恣欢谑。\n" +
				"主人何为言少钱，径须沽取对君酌。\n" +
				"五花马、千金裘，呼儿将出换美酒，与尔同销万古愁。";

		for (int i = 0; i < loopTimes; i++) {
			listRAM.add(str);
		}
		long endRAM = r.freeMemory();

		Field f = ArrayList.class.getDeclaredField("elementData");
		f.setAccessible(true);
		Object[] o = (Object[]) f.get(listRAM);

		// 1.使用RunTime内存管理类
		String result = "测试RAM结束, 占用内存空间约为: " + (startRAM - endRAM);
		System.out.println(result);

		// 2.使用反射的方式查询ArrayList的实际申请长度, 然后按照每个字符串申请了2字节进行计算
		result = "测试RAM结束, 占用内存空间约为: " + ((long) o.length * (long) str.length() * 2);
		System.out.println(result);

		// 3.使用反射的方式查询ArrayList的实际申请长度, 然后读取字符串的字节数组长度计算
		result = "测试RAM结束, 占用内存空间约为 : " + ((long) o.length * (long) str.getBytes().length);
		System.out.println(result);

	}
}
