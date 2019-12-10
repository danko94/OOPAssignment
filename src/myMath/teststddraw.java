package myMath;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import com.google.gson.Gson;

public class teststddraw {

	public static void main(String[] args) {

		String s = "Plus(Max(Divid(x^2,x^3),Comp(2,3)),3)";
		String s1 = "None(x^2,x^3)";
		ComplexFunction bt = new ComplexFunction(s);
		ComplexFunction bt1 = new ComplexFunction(s1);


		ArrayList<function> functions = new ArrayList();
		functions.add(bt);
		functions.add(bt1);

		Functions_GUI fGUI1 = new Functions_GUI();

		fGUI1.add(bt);
		fGUI1.add(bt1);

		try {
			fGUI1.saveToFile("txtfile.txt");

		} catch (Exception e) {
			// TODO: handle exception
		}

		System.out.println(fGUI1.functions.get(1));




		Functions_GUI fGUI = new Functions_GUI();
		try {
			fGUI.initFromFile("txtfile.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println(fGUI.functions.get(0));
		
		Range rx= new Range(-10,10);
		Range ry = new Range(-10,10);
		int resolution = 200;
		
		Functions_GUI fGUI5 = new Functions_GUI();
		
		fGUI5.add(new Polynom("x^2"));
		fGUI5.add(new Polynom("x^3"));
		
		fGUI5.drawFunctions(1000, 1000, rx, ry, resolution);
		
		

	}

}
