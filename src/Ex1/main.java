package Ex1;

import java.io.IOException;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		String s = "Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.3x+5.0)";
		String t = "Divid(420x,69x)";
		
		ComplexFunction cF1 = new ComplexFunction(s);
		ComplexFunction cF2 = new ComplexFunction(t);
		
		Functions_GUI data = new Functions_GUI();
		data.add(cF1);
		data.add(cF2);
		
		try {
			data.saveToFile("saveToFileTest.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}		}

}
