package eden;

import static guru.nidi.graphviz.model.Factory.*;

import guru.nidi.graphviz.attribute.Attributes;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Label.Location;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.MutableGraph;
import guru.nidi.graphviz.model.MutableNode;
import eden.TreeNode;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ShowProcedureTree {

// public:
	public static void storeImage(TreeNode root, String title) throws IOException {
		
		MutableGraph graph = toGraph(root, title);
		Graphviz.fromGraph(graph).render(Format.PNG).toFile(new File(PATH + title + ".png"));
	}
	
	public static void setPath(String path) {
		PATH = path;
	}
	
//private:
	private static String PATH = "graph/";
	
	private static int count = 0;
	
	private static MutableGraph toGraph(TreeNode root, String title) {
		
		MutableGraph graph = mutGraph(title).setDirected(true);
		graph.graphAttrs().add(Label.graphName().locate(Location.TOP));
		Map<TreeNode, MutableNode> map = new HashMap<TreeNode, MutableNode>();
		dfsNode(root, graph, map);
		count = 0;
		return graph;
	}
	
	private static void dfsNode(TreeNode node, MutableGraph graph, Map<TreeNode, MutableNode> map) {
		
		if (node == null) {
			return;
		}
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 8; i++) {
			if ((node.values >> i & 1) == 1) {
				buffer.append(toThreeBitString(i) + "\n");
			}
		}
		if (buffer.length() != 0 && node.zeroEdge == null) {
			buffer.append("*");
		}
		MutableNode graphNode = mutNode((count++) + "");
		graphNode.add(Shape.RECTANGLE, Style.ROUNDED, Label.of(buffer.toString()));
		graph.add(graphNode);
		map.put(node, graphNode);
		if (node.values == 0) {
			graphNode.add(Style.DASHED);
			if (node.former != null) {
				map.get(node.former).add(Attributes.attr("peripheries", 2));
			}
		}
		if (node.former != null) {
			graph.add(map.get(node.former).addLink(to(graphNode).add(Label.of(node.type + ""))));
		}
		dfsNode(node.zeroEdge, graph, map);
		dfsNode(node.oneEdge, graph, map);
	}
	
	private static String toThreeBitString(int num) {
		
		StringBuffer buffer = new StringBuffer();
		for (int i = 0; i < 3; i++) {
			buffer.insert(0, num & 1);
			num >>= 1;
		}
		return buffer.toString();
	}
	
}
