package SPLT_A4;

public class BST_Node {
	String data;
	BST_Node left;
	BST_Node right;
	BST_Node par; //par...not necessarily required, but can be useful in splay tree
	boolean justMade; //could be helpful if you change some of the return types on your BST_Node insert.
	//I personally use it to indicate to my SPLT insert whether or not we increment size.

	BST_Node(String data){ 
		this.data=data;
		this.justMade=true;
	}

	BST_Node(String data, BST_Node left,BST_Node right,BST_Node par){ //feel free to modify this constructor to suit your needs
		this.data=data;
		this.left=left;
		this.right=right;
		this.par=par;
		this.justMade=true;
	}

	// --- used for testing  ----------------------------------------------
	//
	// leave these 3 methods in, as is (meaning also make sure they do in fact return data,left,right respectively)

	public String getData(){ return data; }
	public BST_Node getLeft(){ return left; }
	public BST_Node getRight(){ return right; }

	// --- end used for testing -------------------------------------------


	// --- Some example methods that could be helpful ------------------------------------------
	//
	// add the meat of correct implementation logic to them if you wish

	// you MAY change the signatures if you wish...names too (we will not grade on delegation for this assignment)
	// make them take more or different parameters
	// have them return different types
	//
	// you may use recursive or iterative implementations

	//note: I personally find it easiest to make this return a Node,(that being the node splayed to root), you are however free to do what you wish.
	public BST_Node containsNode(String s){ //it was me
		if(this.data.equals(s)){
			this.justMade = false;
			BST_Node temp = this;
			splay(this);
			return temp;
		}
		if(s.compareTo(this.data) > 0){
			if(this.right == null){
				this.justMade = false;
				BST_Node temp = this;
				splay(this);
				return temp;
			}
			this.right.par = this;
			return this.right.containsNode(s);
		}
		else{
			if(this.left == null){
				this.justMade = false;
				BST_Node temp = this;
				splay(this);
				return temp;
			}
			this.left.par = this;
			return this.left.containsNode(s);
		}
	}
	//Really same logic as above note
	public BST_Node insertNode(String s){ 
		if(s == null){
			return null;
		}
		BST_Node node = new BST_Node(s);
		if (s.compareTo(data) > 0){
			if(this.right != null){
				return this.right.insertNode(s);
			}
			else{
				this.right = node;
				this.right.par = this;
				BST_Node temp = this.right;
				splay(this.right);
				return temp;
			}  
		}
		else if(s.compareTo(data) < 0){
			if(this.left != null){
				return this.left.insertNode(s);
			}
			else{
				this.left = node;
				this.left.par = this;
				BST_Node temp = this.left;
				splay(this.left);
				return temp;
			}
		}
		else{
			this.justMade = false;
			BST_Node temp = this;
			splay(this);
			return temp;
		}
	} 
	//I personal do not use the removeNode internal method in my impl since it is rather easily done in 
	//SPLT, feel free to try to delegate this out, however we do not "remove" like we do in BST
	public BST_Node removeNode(String s){ 
		if(s == null){
			return null;
		}
		BST_Node right = this.right;
		BST_Node left = this.left;

		this.right = null;
		this.left = null; 

		if(left != null){
			left.par = null;
			left = left.findMax();
			if(right != null){
				left.right = right;
				left.right.par = left;
			}
			return left;
		}
		right.par = null;
		return right;
	} 

	public BST_Node findMin(){ 
		if(this.left == null){
			this.justMade = false;
			BST_Node temp = this;
			splay(this);
			return temp;
		}
		return this.left.findMin();
	} 
	public BST_Node findMax(){ 
		if(this.right == null){
			this.justMade = false;
			BST_Node temp = this;
			splay(this);
			return temp;
		}
		return this.right.findMax();
	}


	public int getHeight(){
		int l=0;
		int r=0;
		if(left!=null)l+=left.getHeight()+1;
		if(right!=null)r+=right.getHeight()+1;
		return Integer.max(l, r);
	}


	//you could have this return or take in whatever you want..so long as it will do the job internally. As a caller of SPLT functions, I should really have no idea if you are "splaying or not"
	//I of course, will be checking with tests and by eye to make sure you are indeed splaying
	//Pro tip: Making individual methods for rotateLeft and rotateRight might be a good idea!
	private void splay(BST_Node toSplay) { 
		while(toSplay.par != null){
			if(toSplay.par.par == null){
				this.rotate(toSplay);
			}
			else{
				if(toSplay == toSplay.par.left && toSplay.par == toSplay.par.par.left){
					this.zigZig(toSplay.par);
					this.zigZig(toSplay);
				}

				else if(toSplay == toSplay.par.left && toSplay.par == toSplay.par.par.right){
					this.zigZag(toSplay);
				}

				else if(toSplay == toSplay.par.right && toSplay.par == toSplay.par.par.right){
					this.zigZig(toSplay.par);
					this.zigZig(toSplay);
				}

				else if(toSplay == toSplay.par.right && toSplay.par == toSplay.par.par.left){
					this.zigZag(toSplay);
				}
			}
		}
	} 
	public void rotate(BST_Node node){
		//right rotate
		if(node == node.par.left){
			node.par.left = node.right;
			if(node.right != null){
				node.right.par = node.par;
			}
			node.par.par = node;
			node.right = node.par;
		}
		//left rotate
		else{
			node.par.right = node.left;
			if(node.left != null){
				node.left.par = node.par;
			}
			node.par.par = node;
			node.left = node.par;
		}
		node.par = null;
	}

	private void zigZig(BST_Node node) {
		if (node.par != null) {
			BST_Node temp;
			boolean isLeft = false;
			if (node.par.par != null) {
				temp = node.par.par;
				if (node.par == node.par.par.left) {
					isLeft = true;
				} 
				else 
					isLeft = false;
			} 
			else 
				temp = null;

			if (node == node.par.right) {
				if (node.left != null) {
					node.par.right = node.left;
					node.par.right.par = node.par;
				} 
				else 
					node.par.right = null;

				node.left = node.par;
				node.left.par = node;
			} 
			else {
				if (node.right != null) {
					node.par.left = node.right;
					node.par.left.par = node.par;
				} 
				else 
					node.par.left = null;

				node.right = node.par;
				node.right.par = node;
			}
			if (temp != null) {
				if (isLeft) {
					temp.left = node;
				} 
				else 
					temp.right = node;
				node.par = temp;
			} 
			else 
				node.par = null;
		}
	}
	private void zigZag(BST_Node node) {
		if (node.par.par != null && node.par != null) {
			BST_Node ggpar; 
			boolean gpIsLeft = false;
			if (node.par.par.par != null) { 
				ggpar = node.par.par.par;
				if (node.par.par == node.par.par.par.left) 
					gpIsLeft = true;
				else 
					gpIsLeft = false;
			} 
			else 
				ggpar = null;
			
			if (node.par == node.par.par.left) {
				if (node.left != null) {
					node.par.right = node.left;
					node.par.right.par = node.par;
				} 
				else 
					node.par.right = null;

				if (node.right != null) {
					node.par.par.left = node.right;
					node.par.par.left.par = node.par.par;
				} 
				else 
					node.par.par.left = null;

				node.right = node.par.par;
				BST_Node temp = null;

				if (node.right.par != null) 
					temp = node.right.par;
				
				node.right.par = node;
				node.left = node.par;
				node.left.par = node;
				
				if (temp != null) 
					node.par = temp;	
				else 
					node.par = null;
			}
			else {
				if (node.right != null) {
					node.par.left = node.right;
					node.par.left.par = node.par;
				} 
				else 
					node.par.left = null;
				
				if (node.left != null) {
					node.par.par.right = node.left;
					node.par.par.right.par = node.par.par;
				} 
				else 
					node.par.par.right = null;
				
				node.left = node.par.par;
				node.left.par = node;
				node.right = node.par;
				node.right.par = node;
			}
			if (ggpar != null) {
				if (gpIsLeft) {
					ggpar.left = node;
					node.par = ggpar;
				} 
				else {
					ggpar.right = node;
					node.par = ggpar;
				}
			} 
			else 
				node.par = null;
		}
	}
	// --- end example methods --------------------------------------




	// --------------------------------------------------------------------
	// you may add any other methods you want to get the job done
	// --------------------------------------------------------------------
}