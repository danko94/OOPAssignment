package Ex1;

import java.io.IOException;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.3x+5.0)";
		String t = "Divid(420x,69x)";
		
		ComplexFunction cF1 = new ComplexFunction(s);
		ComplexFunction cF2 = new ComplexFunction(t);
		
		Monom m = new Monom("3x^2");
		Polynom p1 = new Polynom("3x^2+4x+5");
		Polynom p2 = new Polynom("4x+5");
		ComplexFunction cF = new ComplexFunction("Plus(3x^2,Plus(4x,-4x))");
		
		p1.substract(p2);
		
		System.out.println(m.equals(p1));
		System.out.println(m.equals(cF));

		System.out.println();
		System.out.println();


}
}
