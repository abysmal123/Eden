package injectivity;

import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Queue;

public final class GlobalSurjectivityD4 {
	
// public:
	public static boolean hasEden(final String r) {
		
		if (r.length() != 16) {
			throw new IllegalArgumentException("规则长度必须为16 。"
					+ "Length of rules must be 16. Input rules: " + r);
		}
		if (r.charAt(0) != '0' && r.charAt(0) != '1') {
			throw new IllegalArgumentException("规则必须为01串。"
					+ "Input rules must be binary. Input rules: " + r);
		}
		int rules = 0;
		rules = r.charAt(0) == '1' ? 1 : 0;
		for (int i = 1; i < 16; i++) {
			rules <<= 1;
			if (r.charAt(i) == '1') {
				rules++;
			} else if (r.charAt(i) != '0') {
				throw new IllegalArgumentException("规则必须为01串。"
						+ "Input rules must be binary. Input rules: " + r);
			}
		}
		if (Integer.bitCount(rules) != 8) {
			return true;
		}
		Map<Integer, TreeNode> visitedList = new HashMap<Integer, TreeNode>();
		Queue<TreeNode> nodeList = new ArrayDeque<TreeNode>();
		TreeNode rootNode = new TreeNode(rules);
		nodeList.add(rootNode);
		while (!nodeList.isEmpty()) {
			TreeNode curr = nodeList.poll();
			if (curr.values == 0) {
				return true;
			}
			int zeroValues = 0;
			int oneValues = 0;
			if (visitedList.containsKey(curr.values)) {
				zeroValues = visitedList.get(curr.values).zeroEdge.values;
				oneValues = visitedList.get(curr.values).oneEdge.values;
			} else {
				for (int i = 0; i < 16; i++) {
					if (((curr.values >> i) & 1) == 1) {
						int pos = (i << 1) % 16;
						for (int j : new int[]{0, 1}) {
							if (((rules >> (pos + j)) & 1) == 0) {
								zeroValues |= (1 << (pos + j));
							} else {
								oneValues |= (1 << (pos + j));
							}
						}
					}
				}
			}
			visitedList.putIfAbsent(curr.values, curr);
			if (visitedList.containsKey(zeroValues)) {
				curr.zeroEdge = visitedList.get(zeroValues);
			} else {
				TreeNode nextZeroEdge = new TreeNode(zeroValues);
				nextZeroEdge.type = 0;
				curr.zeroEdge = nextZeroEdge;
				nodeList.add(curr.zeroEdge);
			}
			if (visitedList.containsKey(oneValues)) {
				curr.oneEdge = visitedList.get(oneValues);
			} else {
				TreeNode nextOneEdge = new TreeNode(oneValues);
				nextOneEdge.type = 1;
				curr.oneEdge = nextOneEdge;
				nodeList.add(curr.oneEdge);
			}			
		}
		return false;
	}
	
	public static void printSurjectiveRules() {
		
		long begin = System.currentTimeMillis();
		count = 0;
		rulesCharArr = new char[16];
		Arrays.fill(rulesCharArr, '0');
		dfs(0, 0);
		System.out.println("总计" + count + "条规则");
		long end = System.currentTimeMillis();
		System.out.println("执行用时：" + String.valueOf(end - begin) + "ms.");
	}
	
// private:
	private static void dfs(int oneCount, int pos) {
		
		if (oneCount == 8) {
			String r = new String(rulesCharArr);
			if (!hasEden(r)) {
				count++;
				System.out.println(new String(rulesCharArr) + " " + count);
			}
			return;
		}
		if (8 - oneCount > 16 - pos) {
			return;
		}
		rulesCharArr[pos] = '1';
		dfs(oneCount + 1, pos + 1);
		rulesCharArr[pos] = '0';
		dfs(oneCount, pos + 1);
	}
	
	private static char[] rulesCharArr;
	
	private static int count;
	
}
