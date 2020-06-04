package SPLT_A4;

public class SPLT implements SPLT_Interface{
  private BST_Node root;
  private int size;
  
  public SPLT() {
    this.size = 0;
  } 
  
  public BST_Node getRoot() { //please keep this in here! I need your root node to test your tree!
    return root;
  }

@Override
public boolean insert(String s) {
	if(root == null && s != null){
		root = new BST_Node(s);
		size++;
		return true;
	}
	BST_Node node = root.insertNode(s);
	if(node != null){
		root = node;
	}
	if(node.justMade){
		size++;
		return true;
	}
	return false;
}

@Override
public boolean remove(String s) {
	// TODO Auto-generated method stub
	if(root == null || s == null){
		return false;
	}
	if(root.left == null && root.right == null){
		root = null;
		size--;
		return true;
	}
	if(this.contains(s)){
		root = root.removeNode(s);
		size--;
		return true;
	}
	else{
		return false;
	}
}

@Override
public String findMin() {
	if(root == null){
		return null;
	}
	BST_Node node = root.findMin();
	root = node;
	return node.data;
}

@Override
public String findMax() {
	if(root == null){
		return null;
	}
	BST_Node node = root.findMax();
	root = node;
	return node.data;
}

@Override
public boolean empty() {
	if(root == null){
		return true;
	}
	return false;
}

@Override
public boolean contains(String s) {
	if(root == null){
		return false;
	}
	BST_Node node = root.containsNode(s);
	root = node;
	if(node.data.equals(s)){
		return true;
	}
	return false;
}

@Override
public int size() {
	if(root == null){
		return 0;
	}
	return size;
}

@Override
public int height() {
	if(size() == 0){
		return -1;
	}
	return root.getHeight();
}  

}