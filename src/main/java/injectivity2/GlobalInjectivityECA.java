package injectivity2;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class GlobalInjectivityECA {
	
// public:
	public static boolean injectivity(final String r) {
		
		if (r.length() != 8) {
			throw new IllegalArgumentException("���򳤶ȱ���Ϊ8 ��"
					+ "Length of rules must be 8. Input rules: " + r);
		}
		if (r.charAt(0) != '0' && r.charAt(0) != '1') {
			throw new IllegalArgumentException("�������Ϊ01����"
					+ "Input rules must be binary. Input rules: " + r);
		}
		int rules = (r.charAt(0) == '1' ? 1 : 0);
		for (int i = 1; i < 8; i++) {
			rules <<= 1;
			if (r.charAt(i) == '1') {
				rules++;
			} else if (r.charAt(i) != '0') {
				throw new IllegalArgumentException("�������Ϊ01����"
						+ "Input rules must be binary. Input rules: " + r);
			}
		}
		if (Integer.bitCount(rules) != 4) {
			return false;
		}
		Map<Integer, ValueSet> tree = new HashMap<Integer, ValueSet>();
		Set<String> visited = new HashSet<String>();
		Queue<Integer> nodeList = new ArrayDeque<Integer>();
		ValueSet root = new ValueSet(new int[]{1, 2, 4, 8});
		tree.put(1, root);
		visited.add(hash(root));
		nodeList.offer(2);
		nodeList.offer(3);
		while (!nodeList.isEmpty()) {
			int idx = nodeList.poll();
			ValueSet father = tree.get(idx / 2);
			int[] subValues = new int[4];
			int tupleCount = 0;
			int periodicCount = 0;
			for (int k = 0; k < 4; k++) {
				for (int i = 0; i < 4; i++) {
					if (((father.subValues[k] >> i) & 1) == 1) {	
						int pos = (i << 1);
						for (int j : new int[]{0, 1}) {
							if (((rules >> (pos + j)) & 1) == (idx & 1)) {
								tupleCount++;
								subValues[k] |= (1 << ((pos + j) % 4));
								if ((pos + j) % 4 == k) {
									periodicCount++;
									if (periodicCount > 1) {
										return false;
									}
								}
							}
						}
					}
				}
				for (int i = 0; i < 2; i++) {
					if (((subValues[k] >> i) & 1) == 1 && ((subValues[k] >> (i + 2)) & 1) == 1) {
						for (int j : new int[]{0, 1}) {
							if (((rules >> ((i << 1) + j)) & 1) == ((rules >> (((i + 2) << 1) + j)) & 1)) {
								return false;
							}
						}	
					}
				}
			}
			if (tupleCount == 0) {
				return false;
			}
			ValueSet curr = new ValueSet(subValues);
			if (visited.contains(hash(curr))) {
				continue;
			}
			visited.add(hash(curr));
			tree.put(idx, curr);
			nodeList.add(2 * idx);
			nodeList.add(2 * idx + 1);
		}
		return true;
	}
	
	public static void printInjectiveRules() {
		
		long begin = System.currentTimeMillis();
		int count = 0;
		for (int i = 255; i >= 128; i--) {
			String r = toSixteenBitString(i);
			if (injectivity(r)) {
				System.out.println(r);
				count++;
			}
		}
		System.out.println("�ܼ�" + count + "������");
		long end = System.currentTimeMillis();
		System.out.println("ִ����ʱ��" + String.valueOf(end - begin) + "ms.");
	}
	
	public static String toSixteenBitString(int num) {
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			buffer.insert(0, num & 1);
			num >>= 1;
		}
		return buffer.toString();
	}

// private:
	private static String hash(ValueSet vs) {
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < vs.subValues.length; i++) {
			buffer.append(vs.subValues[i] + ".");
		}
		return buffer.toString();
	}
	
}
