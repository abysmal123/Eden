package eden;

import java.util.ArrayDeque;
import java.util.Queue;

public final class BuildTree {
	
// public:
	public BuildTree(String r) {
		
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
	}
	
	public TreeNode withRoot(int values) {
		
		boolean[] visited = new boolean[256];
		visited[0] = true;
		Queue<TreeNode> nodeList = new ArrayDeque<TreeNode>();
		TreeNode rootNode = new TreeNode(values);
		nodeList.add(rootNode);
		while (!nodeList.isEmpty()) {
			TreeNode curr = nodeList.poll();
			if (visited[curr.values]) {
				continue;
			}
			visited[curr.values] = true;
			int zeroValues = 0;
			int oneValues = 0;
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
			TreeNode nextZeroEdge = new TreeNode(zeroValues);
			nextZeroEdge.former = curr;
			nextZeroEdge.type = 0;
			nodeList.add(nextZeroEdge);
			curr.zeroEdge = nextZeroEdge;
			TreeNode nextOneEdge = new TreeNode(oneValues);
			nextOneEdge.former = curr;
			nextOneEdge.type = 1;
			nodeList.add(nextOneEdge);
			curr.oneEdge = nextOneEdge;
		}
		return rootNode;
	}
	
// private:
	private int rules;
	
}
