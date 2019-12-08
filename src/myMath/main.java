package myMath;

import java.util.StringTokenizer;

public class main {

	public static void main(String[] args) {

		
		String s = "Plus(Max(Divid(x^2,x^3),Comp(2,3)),3)";
		String s1 = "None(x^2,x^3)";
		ComplexFunction bt = new ComplexFunction(s);
		ComplexFunction bt5 = new ComplexFunction(s);
		System.out.println(bt.equals(bt5));
		System.out.println(bt.f(100));
		ComplexFunction bt1 = new ComplexFunction();
		ComplexFunction bt2 = new ComplexFunction(s1);
		function g = bt.left();
		ComplexFunction h = (ComplexFunction)g;
		System.out.println(h.getOp());
		//bt = (BinaryTree)bt.initFromString(s);
		//bt.root=bt.insertFunc(s);
		bt1.root=bt1.insertFunc(s);
		System.out.println(bt.root.left.right.getOp());
		//bt.root = bt.newRoot(Operation.Divid);
		
		bt.plus(bt1);
	
		//bt.insertFuncRight(bt1);
		System.out.println(bt.root.right.getOp());
		System.out.println(bt.root.getOp());
		//System.out.println(bt2.f(1));
		//System.out.println(bt.f(5));
		
	   // bt.printPreorder(bt5.root);
		
	    System.out.println();
	    System.out.println(bt2);
	    System.out.println(bt5);
	    System.out.println(bt1);
	    System.out.println(bt);

	    


		

	}
}



