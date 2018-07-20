package entity;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map.Entry;
import tools.Tool;
/**
 * 该类是树结构的实体类
 * */
public class TreeNode implements Serializable{
	//序列化版本id
	private static final long serialVersionUID = 1L;
	ArrayList<TreeNode> children; //拥有子树的节点
	ArrayList<String> path; //路径
	HashMap<String,Long> TargetValueMap; //叶节点
	public HashMap<String, Long> getTargetValueMap() {
		return TargetValueMap;
	}
	//TODO
	public void setTargetValueMap(HashMap<String, Long> targetValueMap) {
		TargetValueMap = targetValueMap;
	}
	
	public ArrayList<TreeNode> getChildren() {
		return children;
	}
	public void setChildren(ArrayList<TreeNode> children) {
		this.children = children;
	}
	//为节点添加子树
	public void addChildren(TreeNode node){
		this.children.add(node);
	}
	//TODO
	//获取所有的决策树中的路径
	public ArrayList<String> getPath() {
		return path;
	}
	//获取某个路径
	public String getPath(int i) {
		return path.get(i);
	}
	public void setPath(ArrayList<String> path) {
		this.path = path;
	}
	
	
	String Value;
	int    index;
	String NodeName;
	int size;
	public int getSize() {
		return size;
	}
	//TODO
	public void setSize(int size) {
		this.size = size;
	}
	//TODO
	public String getValue() {
		return Value;
	}
	//TODO
	public void setValue(String value) {
		Value = value;
	}
	//TODO
	public int getIndex() {
		return index;
	}
	//TODO
	public void setIndex(int index) {
		this.index = index;
	}
	public String getNodeName() {
		return NodeName;
	}
	//TODO
	public void setNodeName(String nodeName) {
		NodeName = nodeName;
	}
	public TreeNode(){
		children = new ArrayList<TreeNode>();
		path = new ArrayList<String>();
	}
	//显示路径，格式为（path1，path2...）
	public String showPath(){
		String returnMessage = "(";
		for(String p:path){
			returnMessage+=" "+p+" ";
		}
		returnMessage+=")";
		return returnMessage;
	}
	//为路径的集合中添加路径
	public void addPath(String s){
		path.add(s);
	}
	//TODO
	//判断树种的节点是否为叶子节点
	public boolean isLeaf(){
		return this.NodeName.toLowerCase().startsWith("leaf");
	}
	//TODO
	public TreeNode getMostChild(){
		int max = 0;
		TreeNode result = null;
		for(TreeNode c:children){
			if(c.getSize()>max){
				max = c.getSize();
				result = c;
			}
		}
		return result;
	}
	//TODO
	//查找子节点
	public TreeNode findChild(String data,boolean isdiscrete){
		if(data.equals("?"))
			return getMostChild();
		//连续值根据区间判断
		if(!isdiscrete){
			String cmp = getPath().get(0).replace("<=", "");
			if(Tool.cmp(data, cmp)){
				return children.get(0);
			}
			return children.get(1);
		}else{ //离散值判断是否相等即可
			for(int i=0;i<path.size();i++){
				String cmp = path.get(i).replace("=", "");
				if(Tool.equalString(data, cmp)){
					return children.get(i);
				}
			}
		}
		return getMostChild();
	}
	public String mostTargetValue(){
		Iterator<Entry<String, Long>> it = TargetValueMap.entrySet().iterator();
		String returnMessage = null;
		long max = Long.MIN_VALUE;
		while(it.hasNext()){
			Entry<String,Long> e = it.next();
			if(e.getValue()>max){
				max = e.getValue();
				returnMessage = e.getKey();
			}
		}
		return returnMessage;
	}
}
