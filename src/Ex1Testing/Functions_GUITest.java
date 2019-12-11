package Ex1Testing;

import static org.junit.Assert.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Iterator;
import java.util.Scanner;

import org.junit.jupiter.api.*;

import myMath.*;


/**
 * Note: minor changes (thanks to Amichai!!)
 * The use of "get" was replaced by iterator!
 * 
 * Partial JUnit + main test for the GUI_Functions class, expected output from the main:
 * 0) java.awt.Color[r=0,g=0,b=255]  f(x)= plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0)
1) java.awt.Color[r=0,g=255,b=255]  f(x)= plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)
2) java.awt.Color[r=255,g=0,b=255]  f(x)= div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)
3) java.awt.Color[r=255,g=200,b=0]  f(x)= -1.0x^4 +2.4x^2 +3.1
4) java.awt.Color[r=255,g=0,b=0]  f(x)= +0.1x^5 -1.2999999999999998x +5.0
5) java.awt.Color[r=0,g=255,b=0]  f(x)= max(max(max(max(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)
6) java.awt.Color[r=255,g=175,b=175]  f(x)= min(min(min(min(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),plus(div(+1.0x +1.0,mul(mul(+1.0x +3.0,+1.0x -2.0),+1.0x -4.0)),2.0)),div(plus(-1.0x^4 +2.4x^2 +3.1,+0.1x^5 -1.2999999999999998x +5.0),-1.0x^4 +2.4x^2 +3.1)),-1.0x^4 +2.4x^2 +3.1),+0.1x^5 -1.2999999999999998x +5.0)

 * @author boaz_benmoshe
 *
 */
class Functions_GUITest {
	public static void main(String[] a) {
		functions data = FunctionsFactory();
		int w=1000, h=600, res=200;
		Range rx = new Range(-10,10);
		Range ry = new Range(-5,15);
		//data.drawFunctions(w,h,rx,ry,res);
		String file = "function_file.txt";
		String file2 = "function_file2.txt";
		try {
			data.saveToFile(file);
			Functions_GUI data2 = new Functions_GUI();
			data2.initFromFile(file);
			data.saveToFile(file2);
		}
		catch(Exception e) {e.printStackTrace();}

		String JSON_param_file = "GUI_params.txt";
		//data.drawFunctions(JSON_param_file);
	}
	private functions _data=null;

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
		_data = FunctionsFactory();
	}

	@Test
	void testFunctions_GUI() {
		String arr [] = {"Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0)",
				"Plus(Divid(1.0x+1.0,Times(Times(1.0x+3.0,1.0x-2.0),1.0x-4.0)),2.0)",
				"Divid(Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),-1.0x^4+2.4x^2+3.1)", "-1.0x^4+2.4x^2+3.1",
		 		"0.1x^5-1.2999999999999998x+5.0",
		"Max(Max(Max(Max(Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),Plus(Divid(1.0x+1.0,Times(Times(1.0x+3.0,1.0x-2.0),1.0x-4.0)),2.0)),Divid(Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),-1.0x^4+2.4x^2+3.1)),-1.0x^4+2.4x^2+3.1),0.1x^5-1.2999999999999998x+5.0)",
		"Min(Min(Min(Min(Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),Plus(Divid(1.0x+1.0,Times(Times(1.0x+3.0,1.0x-2.0),1.0x-4.0)),2.0)),Divid(Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.2999999999999998x+5.0),-1.0x^4+2.4x^2+3.1)),-1.0x^4+2.4x^2+3.1),0.1x^5-1.2999999999999998x+5.0)"};
		
		Functions_GUI fGUIActual = new Functions_GUI();
		
		for(int i=0;i<arr.length;i++) {
			fGUIActual.add(new ComplexFunction(arr[i]));
			}
		Iterator <function> iter = fGUIActual.iterator();
		Iterator <function> dataIter = _data.iterator();
	
		while(iter.hasNext()) {
			assertEquals(dataIter.next(), iter.next());
		}
		
	}

	@Test
	void testInitFromFile() {
		String s = "Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.3x+5.0)";
		String t = "Divid(420x,69x)";

		ComplexFunction cF1 = new ComplexFunction(s);
		ComplexFunction cF2 = new ComplexFunction(t);

		Functions_GUI expectedGUI = new Functions_GUI();
		expectedGUI.add(cF1);
		expectedGUI.add(cF2);

		String exp = "0) java.awt.Color[r=0,g=0,b=255] f(x)= Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.3x+5.0)\n" + 
				"1) java.awt.Color[r=0,g=255,b=255] f(x)= Divid(420.0x,69.0x)";

		try {
			FileWriter fW = new FileWriter(new File("initFromFileTest.txt"));
			fW.append(exp);
			fW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		Functions_GUI actualGUI = new Functions_GUI();

		try {
			actualGUI.initFromFile("initFromFileTest.txt");
		} catch (IOException e) {
			e.printStackTrace();
		}

		Iterator <function> iterAct = actualGUI.iterator();
		Iterator <function> iterExp = expectedGUI.iterator();

		while(iterAct.hasNext()) {
			assertEquals(iterExp.next(), iterAct.next());
		}


	}

	@Test
	void testSaveToFile() {

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
		}

		String exp = "0) java.awt.Color[r=0,g=0,b=255] f(x)= Plus(-1.0x^4+2.4x^2+3.1,0.1x^5-1.3x+5.0)\n" + 
				"1) java.awt.Color[r=0,g=255,b=255] f(x)= Divid(420.0x,69.0x)";

		try {
			FileWriter fW = new FileWriter(new File("saveToFileExpected.txt"));
			fW.append(exp);
			fW.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		String actual="";
		try {
			File fActual = new File("saveToFileTest.txt");
			Scanner scan = new Scanner(fActual);
			while (scan.hasNextLine()) {
				actual += scan.nextLine()+"\n";
			}
			//scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		String expected="";

		try {
			File fExpected = new File("saveToFileExpected.txt");
			Scanner scan = new Scanner(fExpected);
			while (scan.hasNextLine()) {
				expected += scan.nextLine()+"\n";
			}
			scan.close();
		} catch (Exception e) {
			e.printStackTrace();
		}

		assertEquals(expected, actual);


	}

	@Test
	void testDrawFunctions() {
		int w=1000, h=600, res=200;
		Range rx = new Range(-10,10);
		Range ry = new Range(-5,15);
		_data.drawFunctions(w,h,rx,ry,res);

	}

	@Test
	void testDrawFunctionsIntIntRangeRangeInt() {
		_data.drawFunctions("GUI_params.txt");
	}

	public static functions FunctionsFactory() {
		functions ans = new Functions_GUI();
		String s1 = "3.1 +2.4x^2 -x^4";
		String s2 = "5 +2x -3.3x +0.1x^5";
		String[] s3 = {"x +3","x -2", "x -4"};
		Polynom p1 = new Polynom(s1);
		Polynom p2 = new Polynom(s2);
		Polynom p3 = new Polynom(s3[0]);
		ComplexFunction cf3 = new ComplexFunction(p3);
		for(int i=1;i<s3.length;i++) {
			cf3.mul(new Polynom(s3[i]));
		}

		ComplexFunction cf = new ComplexFunction(Operation.Plus, p1,p2);
		ComplexFunction cf4 = new ComplexFunction("div", new Polynom("x +1"),cf3);
		cf4.plus(new Monom("2"));
		ans.add(cf.copy());
		ans.add(cf4.copy());
		cf.div(p1);
		ans.add(cf.copy());
		String s = cf.toString();
		function cf5 = cf4.initFromString(s1);
		function cf6 = cf4.initFromString(s2);


		ans.add(cf5.copy());
		ans.add(cf6.copy());
		Iterator<function> iter = ans.iterator();
		function f = iter.next();
		ComplexFunction max = new ComplexFunction(f);
		ComplexFunction min = new ComplexFunction(f);
		while(iter.hasNext()) {
			f = iter.next();
			max.max(f);
			min.min(f);
		}
		ans.add(max);
		ans.add(min);	

		return ans;
	}
}




