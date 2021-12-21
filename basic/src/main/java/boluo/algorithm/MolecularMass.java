package boluo.algorithm;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Stream;

/**
 * @Author dingc
 * @Date 2021/12/21 21:07
 */
public class MolecularMass {

	private static final Logger logger = LoggerFactory.getLogger(MolecularMass.class);

	public static void main(String[] args) {
		// C 质量 12
		// H 质量 1
		// O 质量 16

		// 三种元素组合起来 总质量为:46

		for (int i = 1; i < 3; i++) {
			for (int j = 1; j < 3; j++) {
				for (int k = 1; k < 19; k++) {
					if (16 * i + 12 * j + k == 46) {
						logger.info("O: {}, C: {}, H: {}", i, j, k);
					}
				}
			}
		}

		for (int i = 1; i < 3; i++) {
			for (int j = 1; j < 3; j++) {
				int k = 46 - (16 * i + 12 * j);
				if (16 * i + 12 * j + k == 46) {
					logger.info("O: {}, C: {}, H: {}", i, j, k);
				}
			}
		}

		// 设: O有x个, C有y个, H有z个
		// 16 * x + 12 * y + 1 * z = 46

		int massO = 16;
		int massC = 12;
		int massH = 1;

		// x,y,z的最大数量
		int maxO = (46 - massC - massH) / massO;
		int maxC = (46 - massO - massH) / massC;
		int maxH = (46 - massO - massC) / massH;

		// 记录使用n个O原子之后, 剩余的总质量
		List<Integer> list = Lists.newArrayList();
		for (int i = 1; i <= maxO; i++) {
			list.add(46 - i * massO);
		}
		System.out.println("Aa");

		for (int i = 1; i <= massC; i++) {
			// 取出这一位的O之后, 剩下的总质量
			int max = list.get(i);
			// C的数量固定之后, H的数量
			int h = (max - massC * i) / massH;
			System.out.println("aa");
		}

	}

}
