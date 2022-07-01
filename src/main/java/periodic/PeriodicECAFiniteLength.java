package periodic;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Queue;
import java.util.Set;

public final class PeriodicECAFiniteLength {
	
// public:
	public static boolean hasEden(final String r) {
		
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
		Map<Integer, ValueSet> tree = new HashMap<Integer, ValueSet>();
		Set<Integer> visited = new HashSet<Integer>();
		Queue<Integer> nodeList = new ArrayDeque<Integer>();
		ValueSet root = new ValueSet(15, new int[]{1, 2, 4, 8});
		tree.put(1, root);
		visited.add(15);
		nodeList.offer(2);
		nodeList.offer(3);
		while (!nodeList.isEmpty()) {
			int idx = nodeList.poll();
			ValueSet father = tree.get(idx / 2);
			int values = getNextValues(father.values, rules, idx);
			if (values == 0) {
				return true;
			}
			int[] subValues = new int[4];
			boolean isEden = true;
			for (int i = 0; i < 8; i++) {
				for (int k = 0; k < 4; k++) {
					if (((father.subValues[k] >> i) & 1) == 1) {	
						int pos = (i << 1) % 8;
						for (int j : new int[]{0, 1}) {
							if (((rules >> (pos + j)) & 1) == (idx & 1)) {
								subValues[k] |= (1 << (pos + j));
								if ((pos + j) % 4 == k) {
									isEden = false;
								}
							}
						}
					}
				}
			}
			if (isEden) {
				return true;
			}
			if (visited.contains(values)) {
				continue;
			}
			visited.add(values);
			tree.put(idx, new ValueSet(values, subValues));
			nodeList.add(2 * idx);
			nodeList.add(2 * idx + 1);
		}
		return false;
	}

// private:
	private static int getNextValues(int values, int rules, int idx) {
		
		int nextValues = 0;
		for (int i = 0; i < 8; i++) {
			if (((values >> i) & 1) == 1) {
				int pos = (i << 1) % 8;
				for (int j : new int[]{0, 1}) {
					if (((rules >> (pos + j)) & 1) == (idx & 1)) { 
						nextValues |= (1 << (pos + j));
					}
				}
			}
		}
		return nextValues;
	}
	
}
