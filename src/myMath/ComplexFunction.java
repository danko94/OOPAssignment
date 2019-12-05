package myMath;

import java.util.StringTokenizer;

import myMath.BinaryTree.Node;


public class ComplexFunction implements complex_function {
	
	public class Node {
		
	    double value; 
	    Operation op;
	    boolean isInner;
	    function func;
	    Node left;
	    Node right;
	    Node parent;
	    
	    Node(){}
	    
	    Node(Operation op){
	    	this.op = op;
	    }
	 
	    Node(double value) {
	        this.value = value;
	        right = null;
	        left = null;
	    }
	    Node(Operation op, boolean isInner, function func){
	    	this.op = Operation.None;
	    	this.isInner = false;
	    	this.func = null;
	    	left = right = null;
	    }
	    Node(Polynom p){
	    	this.func = p;
	    
		}
	    
	    public Node insertFunc(String s) {
			
			Node root = new Node();
			
			if(!s.contains(""+',')) {
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

		public void setFunc(function func) {
			this.func = func;
		}

		public void newRoot(Operation plus, function f1) {
			Node root = new Node();
			root.op = plus;
			root.left = complexFunc;
			root.right = (Node) f1;
		}
	}

	
	
	
	private Node complexFunc;

	public ComplexFunction() {
		
		complexFunc = new Node();		
	}
	
	public ComplexFunction(String s) {
		
		complexFunc = new Node();
		complexFunc = complexFunc.insertFunc(s);		
	}


	@Override
	public double f(double x) {
		
		return 0;
	}

	@Override
	public void plus(function f1) {
		//complexFunc.newRoot(Operation.Plus);
		
	}
	
	
	
	
	
	

	

	
	@Override
	public void mul(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void div(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void max(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void min(function f1) {
		// TODO Auto-generated method stub

	}

	@Override
	public void comp(function f1) {
		// TODO Auto-generated method stub

	}

	@Override 
	public function left() {
		return null;
				}

	@Override
	
	public function right() {
		return null;
	}

	

	@Override
	public Operation getOp() {

		return complexFunc.op;
	}
	


	@Override
	public function initFromString(String s) {
		
		ComplexFunction comFunc = new ComplexFunction(s);
		
		return comFunc;
	}
}
