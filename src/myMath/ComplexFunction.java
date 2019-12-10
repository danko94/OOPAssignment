package myMath;

//import myMath.BinaryTree.Node;

public class ComplexFunction implements complex_function{

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
			root.func = p.copy();
		}
		else
			this.root=((ComplexFunction)p).root;
	}
	public ComplexFunction(String oper, function f, function g) {
		Operation operation = Operation.Error;
		if(oper=="plus")
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
	public ComplexFunction(String s) {
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

	void insertFuncRight(function f1) throws RuntimeException {
		if(f1 instanceof Polynom || f1 instanceof Monom) {
			this.root.right = new Node();
			this.root.right.func = f1;
		}
		else if(f1 instanceof ComplexFunction) {
			this.root.right = ((ComplexFunction)f1).root;
		}
		else {
			throw new RuntimeException("not compatible");
		}
	}

	void insertFuncLeft(function f1) throws RuntimeException {
		if(f1 instanceof Polynom || f1 instanceof Monom) {
			this.root.left = new Node();
			this.root.left.func = f1;
		}
		else if(f1 instanceof ComplexFunction) {
			this.root.left = ((ComplexFunction)f1).root;
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
	public double f(double x) throws RuntimeException {

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
			case Error:
				throw new RuntimeException("Invalid operation");
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
		Node n = root.left;
		ComplexFunction bTb = new ComplexFunction(n);
		return bTb;

	}
	@Override
	public function right() throws NullPointerException{
		Node n = root.right;  //nullpointer
		ComplexFunction bTb = new ComplexFunction(n);
		return bTb;
	}
	@Override
	public Operation getOp() {
		return root.op;
	}

	public boolean equals(Object obj) throws RuntimeException {

		if(!(obj instanceof function)) {
			throw new RuntimeException("Given object is not a function.");
		}
		else {
			for(int i=1;i<100;i++) 
				if(this.f(i)!=((function)obj).f(i))
					return false;

			return true;
		}
	}

	private static String s = "";

	private void createString(Node node) {
		if (node.left==null) {
			s+=node.func;
			return;
		}

		s=s+node.op+"(";

		createString(node.left);
		s+=",";

		createString(node.right);
		s+=")";
	}

	public String toString() {
		s="";
		this.createString(this.root);
		return s;
	}
	@Override
	public function copy() {

		ComplexFunction copy = new ComplexFunction(this.toString());
		return copy;
	}


}
