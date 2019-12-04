package myMath;

import java.util.StringTokenizer;

public class main {

	public static void main(String[] args) {

		/*StringTokenizer st = new StringTokenizer(s, "(),");

		BinaryTree bt = new BinaryTree();
		BinaryTree.Node root = bt.root;
		while (st.hasMoreTokens()) {
			String token = st.nextToken();
			Operation op = Operation.Error;
			String pol = "-x1234567890";

			if(pol.contains(""+(token.charAt(0)))&&root.left==null){
				root.left.func = new Polynom(token);
				root.left.op = Operation.None;
				root.left.parent = root;
			}
			else if(pol.contains(""+(token.charAt(0)))) {
				root.right.func = new Polynom(token);
				root.right.op = Operation.None;
				root.right.parent = root;
			}
			else {
				for (Operation c : Operation.values()) {
					if (c.name().equals(token)) {
						op = Operation.valueOf(token);
					}
				}
			}
			root.op = op;
			if(root.left==null) {
				root.left = ;
			}

		}*/
		
		String s = "Min(Plus(Divid(x^2,x^3),Comp(2,3)),3)";
		BinaryTree bt = new BinaryTree(s);
		BinaryTree bt1 = new BinaryTree();
		
		//bt = (BinaryTree)bt.initFromString(s);
		//bt.root=bt.insertFunc(s);
		bt1.root=bt1.insertFunc(s);
		System.out.println(bt.root.left.right.getOp());
		//bt.root = bt.newRoot(Operation.Divid);
		
		bt.min(bt1);

		//bt.insertFuncRight(bt1);
		System.out.println(bt.root.right.getOp());
		System.out.println(bt.root.getOp());

		

	}
}



