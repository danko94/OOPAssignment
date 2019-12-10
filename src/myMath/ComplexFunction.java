package myMath;

//import myMath.BinaryTree.Node;

public class ComplexFunction implements complex_function{

	private class Node {

		Operation op;
		function func;
		Node left;
		Node right;
		

		Node(){}

		Node(Operation op){
			this.op = op;
		}

		Node(Operation op, function func){
			this.op = Operation.None;
			this.func = null;
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

	private Node root;	
	private static String str = "";    //for toString method


	public ComplexFunction() {
		root = new Node(Operation.None, null);
	}

	public ComplexFunction(Operation oper, function f, function g){
		root = new Node(oper);
		insertFuncRight(g);
		insertFuncLeft(f);		
	}

	public ComplexFunction(function p) {
		if(p instanceof Monom || p instanceof Polynom) {
			root = new Node(Operation.None);
			root.setFunc(p);;
		}
		else
			this.root=((ComplexFunction)p).root;
	}
	public ComplexFunction(String oper, function f, function g) {
		Operation operation = Operation.Error;
		if(oper.equals("plus"))
			operation = Operation.Plus;
		else if(oper.equals("div"))
			operation = Operation.Divid;
		else if(oper.equals("mul"))
			operation = Operation.Times;
		else if(oper.equals("max"))
			operation = Operation.Max;
		else if(oper.equals("min"))
			operation = Operation.Min;
		
		this.root = new Node(operation);
		this.insertFuncLeft(f);
		this.insertFuncRight(g);
	}

	public ComplexFunction(Node root) {
		this.root=root;
	}
	public ComplexFunction(String s) throws RuntimeException {
		s = s.replaceAll("\\s+","");			//Replace all spaces in input string
		
		if(!checkInput(s)) {
			throw new RuntimeException("Invalid Input");
		}

		root = this.insertFunc(s);
	}
	
	@Override
	public double f(double x) throws RuntimeException {

		if(!isLeaf()) {
			Operation oper = this.getOp();
			switch(oper) {
			case Plus:
				return this.left().f(x)+this.right().f(x);
			case Times:
				return this.left().f(x)*this.right().f(x);
			case Divid:
				if((this.left().f(x)==0)&&(this.right().f(x)==0))		// 0/0 is undefined, and undefined==undefined=false
					return 0;
				return this.left().f(x)/this.right().f(x);
			case Min:
				return Math.min(this.left().f(x), this.right().f(x));
			case Max:
				return Math.max(this.left().f(x), this.right().f(x));
			case Comp:
				return this.left().f(this.right().f(x));
			default: 
				throw new RuntimeException("Invalid operation");		 //Error or None Operation

			}
		}
		else {
			return this.getFunc().f(x);
		}
	}
	
	@Override
	public function initFromString(String s) {
		ComplexFunction bT = new ComplexFunction(s);
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
	public function left() throws NullPointerException{
		Node n = root.getLeft();
		
		if(n==null) {
			throw new NullPointerException("Left of this ComplexFunction is null");
		}
		
		ComplexFunction bTb = new ComplexFunction(n);
		return bTb;

	}

	@Override
	public function right() throws NullPointerException{
		Node n = root.right;
		
		if(n==null) {
			throw new NullPointerException("Right of this ComplexFunction is null");
		}
		ComplexFunction bTb = new ComplexFunction(n);
		return bTb;
	}
	
	@Override
	public Operation getOp() {
		return root.op;
	}


	@Override
	public boolean equals(Object obj) throws RuntimeException {

		if(!(obj instanceof function)) {
			throw new RuntimeException("Given object is not a function.");
		}
		else {
			for(int i=-50;i<50;i++)
				
				if(this.f(i)!=((function)obj).f(i))
					return false;

			return true;
		}
	}

	@Override
	public String toString() {
		str="";
		this.createString(this.root);
		return str;
	}
	
	
	@Override
	public function copy() {

		ComplexFunction copy = new ComplexFunction(this.toString());
		return copy;
	}
	
	
	//Private methods
	
	private Node getLeft() {
		return root.left;
	}
	private Node getRight() {
		return root.right;
	}
	
	private function getFunc() {
		return this.root.func;
	}
	private boolean isLeaf() {
		return this.getLeft()==null;
	}
	
	private boolean checkInput(String s) {
		int pBalance = 0;			//Balance of parenthesis
		double pCounter = 0;		//Counter of parenthesis
		double cCounter = 0;		//Counter of commas
		
		if (s.charAt(0) == '(')
			return false;
		
		for (int i = 0; i < s.length(); i++) {
			if (pBalance < 0)
				return false;
			
			if (s.charAt(i) == '(') {
				pBalance++;
				pCounter++;
			}
			
			if (s.charAt(i) == ')') {
				pBalance--;
				pCounter++;
			}
			
			if (s.charAt(i) == ',')
				cCounter++;
		}
		
		pCounter /= 2;
		if (pCounter != cCounter || pBalance != 0)
			return false;
		
		for (int i = 1; i < s.length(); i++)
			if (s.charAt(i) == '(')
				if (s.charAt(i-1) == '(')
					return false;
		
		return true;
	}
	
	private void createString(Node node) {
		if (node.left==null) {
			str+=node.func;
			return;
		}

		str+=node.op+"(";

		createString(node.left);
		str+=",";

		createString(node.right);
		str+=")";
	}
	
	private Node insertFunc(String s) {

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


			int parenthesisCounter = -1; 

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
			} //end for
		}//end else
		
		return root;
	}

	private Node newRoot(Operation op) {
		Node root = new Node(op);
		root.left = this.root;
		return root;
	}

	private void insertFuncRight(function f1) throws RuntimeException {
		if(f1 instanceof Polynom || f1 instanceof Monom) {
			this.root.setRight(new Node());
			this.root.getRight().setFunc(f1);
		}
		else if(f1 instanceof ComplexFunction) {
			this.root.setRight(((ComplexFunction)f1).root);;
		}
		else {
			throw new RuntimeException("not compatible");
		}
	}

	private void insertFuncLeft(function f1) throws RuntimeException {
		if(f1 instanceof Polynom || f1 instanceof Monom) {
			this.root.setLeft(new Node());
			this.root.getLeft().setFunc(f1);
		}
		else if(f1 instanceof ComplexFunction) {
			this.root.setLeft(((ComplexFunction)f1).root);
		}
		else {
			throw new RuntimeException("not compatible");
		}
	}






}
