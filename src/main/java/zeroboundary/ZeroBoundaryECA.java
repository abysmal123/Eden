package zeroboundary;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Queue;

public final class ZeroBoundaryECA {
	
// public:
	public ZeroBoundaryECA(final String r, int n) {
		
		if (n < 1) {
			throw new IllegalArgumentException("ϸ��������Ϊ1 ��"
					+ "Number of cells should be at Least 1. Input n: " + n);
		}
		this.n = n;
		if (r.length() != 8) {
			throw new IllegalArgumentException("ECA�Ĺ��򳤶ȱ���Ϊ8 ��"
					+ "Length of ECA's rules must be 8. Input rules: " + r);
		}
		if (r.charAt(0) != '0' && r.charAt(0) != '1') {
			throw new IllegalArgumentException("�������Ϊ01����"
					+ "Input rules must be binary. Input rules: " + r);
		}
		rules = r.charAt(0) == '1' ? 1 : 0;
		for (int i = 1; i < 8; i++) {
			rules <<= 1;
			if (r.charAt(i) == '1') {
				rules++;
			} else if (r.charAt(i) != '0') {
				throw new IllegalArgumentException("�������Ϊ01����"
						+ "Input rules must be binary. Input rules: " + r);
			}
		}
		Map<Integer, TreeNode> visitedList = new HashMap<Integer, TreeNode>();
		List<String> edens = new ArrayList<String>();
		Queue<TreeNode> nodeList = new ArrayDeque<TreeNode>();
		rootNode = new TreeNode(3);
		nodeList.add(rootNode);
		while (!nodeList.isEmpty()) {
			TreeNode curr = nodeList.poll();
			if (curr.values == 0) {
				continue;
			}
			int zeroValues = 0;
			int oneValues = 0;
			if (visitedList.containsKey(curr.values)) {
				zeroValues = visitedList.get(curr.values).zeroEdge.values;
				oneValues = visitedList.get(curr.values).oneEdge.values;
			} else {
				for (int i = 0; i < 8; i++) {
					if (((curr.values >> i) & 1) == 1) {
						int pos = (i << 1) % 8;
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
		StringBuffer sb = new StringBuffer();
		dfsTree(rootNode, 0, sb, edens);
		edenSet = new String[edens.size()];
		for (int i = 0; i < edens.size(); i++) {
			edenSet[i] = edens.get(i);
		}
	}
	
	private void dfsTree(TreeNode node, int level, StringBuffer sb, List<String> edens) {
		
		if (level > n) {
			return;
		}
		if (node.values == 0) {
			edens.add(sb.toString());
			return;
		}
		if (level == n) {
			boolean isEden = true;
			for (int i = 0; i < 8; i++) {
				if (((node.values >> i) & 1) == 1 && (i & 1) == 0) {
					isEden = false;
					break;
				}
			}
			if (isEden) {
				edens.add(sb.toString());
			}
			return;
		}
		sb.append('0');
		dfsTree(node.zeroEdge, level + 1, sb, edens);
		sb.deleteCharAt(sb.length() - 1);
		sb.append('1');
		dfsTree(node.oneEdge, level + 1, sb, edens);
		sb.deleteCharAt(sb.length() - 1);
	}
	
	public int getRules() {
		
		return rules;
	}
	
	public int getN() {
		
		return n;
	}
	
	public TreeNode getRootNode() {
		
		return rootNode;
	}
	
	public boolean hasEden() {
		
		return edenSet.length != 0 ? true : false;
	}
	
	public String[] getEdens() {
		
		int len = edenSet.length;
		String[] edens = new String[len];
		for (int i = 0; i < len; i++) {
			edens[i] = new String(edenSet[i]);
		}
		return edens;
	}
	
// private:
	private int rules;
	
	private int n;

	private TreeNode rootNode;
	
	private String[] edenSet;
	
}
