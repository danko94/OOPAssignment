package myMath;

import myMath.BinaryTree.Node;

public class BinaryTree implements complex_function{
	
	public class Node {
		
	    double value;  // ?
	    Operation op;
	    function func;
	    Node left;
	    Node right;
	    Node parent;
	    
	    Node(){}
	    
	    Node(Operation op){
	    	this.op = op;
	    }
	 
	    Node(double value) {// ?
	        this.value = value;
	        right = null;
	        left = null;
	    }
	    Node(Operation op, function func){
	    	this.op = Operation.None;
	    	this.func = null;
	    	left = right = null;
	    }
	    Node(Polynom p){
	    	this.func = p;
	    }
		public double getValue() {
			return value;
		}
		public void setValue(double value) {
			this.value = value;
		}
		public Operation getOp() {
			return op;
		}
		public void setOp(Operation op) {
			this.op = op;
		}
		public function getFunc() {
			return func;
		}
		public void setFunc(function func) {
			this.func = func;
		}
		public Node getLeft() {
			return left;
		}
		public void setLeft(Node left) {
			this.left = left;
		}
		public Node getRight() {
			return right;
		}
		public void setRight(Node right) {
			this.right = right;
		}
	}
	
	Node root;
	
	public BinaryTree() {
		root = new Node(Operation.None, null);
	}
	public BinaryTree(Node root) {
		this.root=root;
	}
	public BinaryTree(String s) {
		root = this.insertFunc(s);
	}
	public Node insertFunc(String s) {
		
		Node root = new Node();
		
		if(!s.contains(",")) {
			Polynom p = new Polynom(s);
			root.setFunc(p);

		}
		else {
			String operator = "";
			Operation op = Operation.Error;
			int j = 0;
			while(s.charAt(j)!='(') {
				operator+=s.charAt(j++);
			}
			for (Operation c : Operation.values()) {
				if (c.name().equals(operator)) {
					op = Operation.valueOf(operator);
				}
			}
			root.op = op;


			int parenthesisCounter = -1;  //plus(plus(x^2,x^3),x^4)

			for(int i=s.length()-1;i>0;i--) {
				if(s.charAt(i)==')') {
					parenthesisCounter++;
				}
				else if(s.charAt(i)=='(') {
					parenthesisCounter--;
				}
				if(parenthesisCounter==0&&s.charAt(i)==',') {
					root.left=insertFunc(s.substring(j+1,s.length()-(s.length()-i)));
					root.right=insertFunc(s.substring(s.length()-(s.length()-i)+1, s.length()-1));
					break;
				}
			}


		}
		return root;
	}
	
	Node newRoot(Operation op) {
		Node root = new Node(op);
		root.left = this.root;
		return root;
	}
	
	void insertFuncRight(function f1) {
		if(f1 instanceof Polynom || f1 instanceof Monom) {
			this.root.right = new Node();
			this.root.right.func = f1;
		}
		else if(f1 instanceof BinaryTree) {
			this.root.right = ((BinaryTree)f1).root;
		}
		else {
			throw new RuntimeException("not compatible");
		}
	}
	
	
	Node getLeft() {
		return root.left;
	}
	Node getRight() {
		return root.right;
	}
	
	void setLeft(Node left) {
		root.left=left;
	}
	void setRight(function f1) {
		
	}
	@Override
	public double f(double x) {
		
		if(!isLeaf()) {
			Operation oper = this.getOp();
			switch(oper) {
			case Plus:
				return this.left().f(x)+this.right().f(x);
			case Times:
				return this.left().f(x)*this.right().f(x);
			case Divid:
				return this.left().f(x)/this.right().f(x);
			case Min:
				return Math.min(this.left().f(x), this.right().f(x));
			case Max:
				return Math.max(this.left().f(x), this.right().f(x));
			case Comp:
				return this.left().f(this.right().f(x));
			default:
				return 0;

			}
		}
		else {
			return this.getFunc().f(x);
		}
		
		
	}
	
	
	private function getFunc() {
		return this.root.func;
	}
	private boolean isLeaf() {
		return this.getLeft()==null;
	}
	@Override
	public function initFromString(String s) {
		BinaryTree bT = new BinaryTree(s);
		return bT;
	}
	@Override
	public void plus(function f1) {
		this.root = newRoot(Operation.Plus);
		this.insertFuncRight(f1);
		
	}
	@Override
	public void mul(function f1) {
		this.root = newRoot(Operation.Times);
		this.insertFuncRight(f1);
		
	}
	@Override
	public void div(function f1) {
		this.root = newRoot(Operation.Divid);
		this.insertFuncRight(f1);
	}
	@Override
	public void max(function f1) {
		this.root = newRoot(Operation.Max);
		this.insertFuncRight(f1);
		
	}
	@Override
	public void min(function f1) {
		this.root = newRoot(Operation.Min);
		this.insertFuncRight(f1);
		
	}
	@Override
	public void comp(function f1) {
		this.root = newRoot(Operation.Comp);
		this.insertFuncRight(f1);
		
	}
	@Override
	public function left() {//Nullpointer exception
		Node n = root.left;
		BinaryTree bTb = new BinaryTree(n);
		return bTb;
		
	}
	@Override
	public function right() {//Nullpointer exception
		Node n = root.right;
		BinaryTree bTb = new BinaryTree(n);
		return bTb;
	}
	@Override
	public Operation getOp() {
		return root.op;
	}

}
