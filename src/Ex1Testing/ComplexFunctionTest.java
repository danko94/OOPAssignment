package Ex1Testing;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Operation;
import Ex1.Polynom;
import Ex1.function;

class ComplexFunctionTest {

	@Test
	void testComplexFunction() {
		ComplexFunction newCF = new ComplexFunction();
		assertNull(((ComplexFunction)newCF.left()));
		assertEquals(Operation.None, newCF.getOp());
		assertNull(newCF.right());
	}
	
	@Test
	void testComplexFunctionString() {
		String complexString = "Times(Plus(x,3),7x^2+5x+8)";
		ComplexFunction newCF = new ComplexFunction(complexString);
		
		function newF1 = new ComplexFunction("x");
		function newF2 = new ComplexFunction("3");
		function newF3 = new ComplexFunction("7x^2+5x+8");
		
		assertEquals(Operation.Times, newCF.getOp());
		assertEquals(Operation.Plus, ((ComplexFunction)newCF.left()).getOp());
		assertEquals(newF1, ((ComplexFunction)newCF.left()).left());
		assertEquals(newF2, ((ComplexFunction)newCF.left()).right());
		assertEquals(newF3, newCF.right());
	}
	
	@Test
	void testComplexFunctionFunction() {
		function anyFunc = new ComplexFunction("Plus(x,2)");
		ComplexFunction newF = new ComplexFunction(anyFunc);
		
		function newF1 = new ComplexFunction("x");
		function newF2 = new ComplexFunction("2");
		
		assertEquals(newF1, newF.left());
		assertEquals(newF2, newF.right());
	}
	
	@Test
	void testComplexFunctionOperationFunctionFunction() {
		function anyFunc1 = new ComplexFunction("Plus(x,2)");
		function anyFunc2 = new ComplexFunction("x^5");
		ComplexFunction newF = new ComplexFunction(Operation.Comp, anyFunc1, anyFunc2);
		
		function newF1 = new ComplexFunction("x");
		function newF2 = new ComplexFunction("2");
		function newF3 = new ComplexFunction("x^5");
		
		assertEquals(Operation.Comp, newF.getOp());
		assertEquals(Operation.Plus, ((ComplexFunction)newF.left()).getOp());
		assertEquals(newF1, ((ComplexFunction)newF.left()).left());
		assertEquals(newF2, ((ComplexFunction)newF.left()).right());
		assertEquals(newF3, newF.right());
	}

	@Test
	void testComplexFunctionStringFunctionFunction() {
		function anyFunc1 = new ComplexFunction("Plus(x^7,2x+5)");
		function anyFunc2 = new ComplexFunction("3x^5+6x^2+9");
		ComplexFunction newF = new ComplexFunction("max", anyFunc1, anyFunc2);
		
		function newF1 = new ComplexFunction("x^7");
		function newF2 = new ComplexFunction("2x+5");
		function newF3 = new ComplexFunction("3x^5+6x^2+9");
		
		assertEquals(Operation.Max, newF.getOp());
		assertEquals(Operation.Plus, ((ComplexFunction)newF.left()).getOp());
		assertEquals(newF1, ((ComplexFunction)newF.left()).left());
		assertEquals(newF2, ((ComplexFunction)newF.left()).right());
		assertEquals(newF3, newF.right());
	}
	
	@Test
	void testF() {
		ComplexFunction newF = new ComplexFunction("Plus(Plus(x^5,2x+9),x)");
		assertEquals(47, newF.f(2));
		assertFalse(47 == newF.f(1));
	}
	
	@Test
	void testInitFromString() {
		ComplexFunction newF = new ComplexFunction("Plus(x,3)");
		
		function newF1 = new ComplexFunction("x");
		function newF2 = new ComplexFunction("3");
		
		assertEquals(Operation.Plus, newF.getOp());
		assertEquals(newF1, newF.left());
		assertEquals(newF2, newF.right());
	}
	
	@Test
	void testCopy() {
		String complexString = "Times(Plus(x,3),7x^2+5x+8)";
		ComplexFunction newCF1 = new ComplexFunction(complexString);
		ComplexFunction newCF2 = (ComplexFunction)newCF1.copy();
		
		function newF1 = new ComplexFunction("x");
		function newF2 = new ComplexFunction("3");
		function newF3 = new ComplexFunction("7x^2+5x+8");
		
		assertEquals(Operation.Times, newCF1.getOp());
		assertEquals(Operation.Plus, ((ComplexFunction)newCF1.left()).getOp());
		assertEquals(newF1, ((ComplexFunction)newCF2.left()).left());
		assertEquals(newF2, ((ComplexFunction)newCF2.left()).right());
		assertEquals(newF3, newCF2.right());
		
		newCF1.mul(newF1);
		assertNotEquals(newCF2, newCF1);
	}
	
	@Test
	void testPlus() {
		ComplexFunction newCF1 = new ComplexFunction("Plus(5x^7,13+2x)");
		ComplexFunction newCF2 = new ComplexFunction("Times(x^2,x)");
		newCF1.plus(newCF2);
		
		function newF1 = new ComplexFunction("5x^7");
		function newF2 = new ComplexFunction("13+2x");
		function newF3 = new ComplexFunction("x^2");
		function newF4 = new ComplexFunction("x");
		
		assertEquals(Operation.Plus, newCF1.getOp());
		assertEquals(Operation.Plus, ((ComplexFunction)newCF1.left()).getOp());
		assertEquals(Operation.Times, ((ComplexFunction)newCF1.right()).getOp());
		assertEquals(newF1, ((ComplexFunction)newCF1.left()).left());
		assertEquals(newF2, ((ComplexFunction)newCF1.left()).right());
		assertEquals(newF3, ((ComplexFunction)newCF1.right()).left());
		assertEquals(newF4, ((ComplexFunction)newCF1.right()).right());
	}
	
	@Test
	void testMul() {
		ComplexFunction newCF1 = new ComplexFunction("Divid(x,3)");
		ComplexFunction newCF2 = new ComplexFunction("Comp(9x^2,6x+4)");
		newCF1.mul(newCF2);
		
		function newF1 = new ComplexFunction("x");
		function newF2 = new ComplexFunction("3");
		function newF3 = new ComplexFunction("9x^2");
		function newF4 = new ComplexFunction("6x+4");
		
		assertEquals(Operation.Times, newCF1.getOp());
		assertEquals(Operation.Divid, ((ComplexFunction)newCF1.left()).getOp());
		assertEquals(Operation.Comp, ((ComplexFunction)newCF1.right()).getOp());
		assertEquals(newF1, ((ComplexFunction)newCF1.left()).left());
		assertEquals(newF2, ((ComplexFunction)newCF1.left()).right());
		assertEquals(newF3, ((ComplexFunction)newCF1.right()).left());
		assertEquals(newF4, ((ComplexFunction)newCF1.right()).right());
	}
	
	@Test
	void testDiv() {
		ComplexFunction newCF1 = new ComplexFunction("Max(x,3)");
		ComplexFunction newCF2 = new ComplexFunction("Min(5x^2,x)");
		newCF1.div(newCF2);
		
		function newF1 = new ComplexFunction("x");
		function newF2 = new ComplexFunction("3");
		function newF3 = new ComplexFunction("5x^2");
		function newF4 = new ComplexFunction("x");
		
		assertEquals(Operation.Divid, newCF1.getOp());
		assertEquals(Operation.Max, ((ComplexFunction)newCF1.left()).getOp());
		assertEquals(Operation.Min, ((ComplexFunction)newCF1.right()).getOp());
		assertEquals(newF1, ((ComplexFunction)newCF1.left()).left());
		assertEquals(newF2, ((ComplexFunction)newCF1.left()).right());
		assertEquals(newF3, ((ComplexFunction)newCF1.right()).left());
		assertEquals(newF4, ((ComplexFunction)newCF1.right()).right());
	
	}
	
	@Test
	void testMax() {
		ComplexFunction newCF1 = new ComplexFunction("Divid(Times(x+10,3),4)");
		ComplexFunction newCF2 = new ComplexFunction("Comp(Plus(5x^2,x),6x)");
		newCF1.max(newCF2);
		
		function newF1 = new ComplexFunction("x+10");
		function newF2 = new ComplexFunction("3");
		function newF3 = new ComplexFunction("4");
		function newF4 = new ComplexFunction("5x^2");
		function newF5 = new ComplexFunction("x");
		function newF6 = new ComplexFunction("6x");
		
		assertEquals(Operation.Max, newCF1.getOp());
		assertEquals(Operation.Divid, ((ComplexFunction)newCF1.left()).getOp());
		assertEquals(Operation.Comp, ((ComplexFunction)newCF1.right()).getOp());
		assertEquals(Operation.Times, ((ComplexFunction)((ComplexFunction)newCF1.left()).left()).getOp());
		assertNull(((ComplexFunction)((ComplexFunction)newCF1.left()).right()).getOp());
		assertEquals(Operation.Plus, ((ComplexFunction)((ComplexFunction)newCF1.right()).left()).getOp());
		assertEquals(newF1, ((ComplexFunction)((ComplexFunction)newCF1.left()).left()).left());
		assertEquals(newF2, ((ComplexFunction)((ComplexFunction)newCF1.left()).left()).right());
		assertEquals(newF3, ((ComplexFunction)newCF1.left()).right());
		assertEquals(newF4, ((ComplexFunction)((ComplexFunction)newCF1.right()).left()).left());
		assertEquals(newF5, ((ComplexFunction)((ComplexFunction)newCF1.right()).left()).right());
		assertEquals(newF6, ((ComplexFunction)newCF1.right()).right());
	}
	
	@Test
	void testMin() {
		ComplexFunction newCF1 = new ComplexFunction("Plus(Plus(x^7+17,3),x)");
		ComplexFunction newCF2 = new ComplexFunction("Times(Times(5x,3x),8)");
		newCF1.min(newCF2);
		
		function newF1 = new ComplexFunction("x^7+17");
		function newF2 = new ComplexFunction("3");
		function newF3 = new ComplexFunction("x");
		function newF4 = new ComplexFunction("5x");
		function newF5 = new ComplexFunction("3x");
		function newF6 = new ComplexFunction("8");
		
		assertEquals(Operation.Min, newCF1.getOp());
		assertEquals(Operation.Plus, ((ComplexFunction)newCF1.left()).getOp());
		assertEquals(Operation.Times, ((ComplexFunction)newCF1.right()).getOp());
		assertEquals(Operation.Plus, ((ComplexFunction)((ComplexFunction)newCF1.left()).left()).getOp());
		assertEquals(Operation.Times, ((ComplexFunction)((ComplexFunction)newCF1.right()).left()).getOp());
		assertEquals(newF1, ((ComplexFunction)((ComplexFunction)newCF1.left()).left()).left());
		assertEquals(newF2, ((ComplexFunction)((ComplexFunction)newCF1.left()).left()).right());
		assertEquals(newF3, ((ComplexFunction)newCF1.left()).right());
		assertEquals(newF4, ((ComplexFunction)((ComplexFunction)newCF1.right()).left()).left());
		assertEquals(newF5, ((ComplexFunction)((ComplexFunction)newCF1.right()).left()).right());
		assertEquals(newF6, ((ComplexFunction)newCF1.right()).right());
	}

	@Test
	void testComp() {
		ComplexFunction newCF1 = new ComplexFunction("Times(x^2,5)");
		ComplexFunction newCF2 = new ComplexFunction("Plus(x^3+2x,7)");
		newCF1.comp(newCF2);
		
		function newF1 = new ComplexFunction("x^2");
		function newF2 = new ComplexFunction("5");
		function newF3 = new ComplexFunction("x^3+2x");
		function newF4 = new ComplexFunction("7");
		
		assertEquals(Operation.Comp, newCF1.getOp());
		assertEquals(Operation.Times, ((ComplexFunction)newCF1.left()).getOp());
		assertEquals(Operation.Plus, ((ComplexFunction)newCF1.right()).getOp());
		assertEquals(newF1, ((ComplexFunction)newCF1.left()).left());
		assertEquals(newF2, ((ComplexFunction)newCF1.left()).right());
		assertEquals(newF3, ((ComplexFunction)newCF1.right()).left());
		assertEquals(newF4, ((ComplexFunction)newCF1.right()).right());
		
		assertTrue(500 == newCF1.f(1));
	}

	@Test
	void testToString() {
		ComplexFunction newCF1 = new ComplexFunction("Comp(Plus(Divid(Times(x+10,3),4),3x^4),x^5)");
		String treeToString = newCF1.toString();
		String checkEq = "Comp(Plus(Divid(Times(1.0x+10.0,3.0),4.0),3.0x^4),1.0x^5)";
		assertEquals(checkEq, treeToString);
	}

	@Test
	void testEqualsObject() {
		ComplexFunction newCF1 = new ComplexFunction("Plus(x^2+4x,6)");
		ComplexFunction newCF2 = new ComplexFunction("Plus(x^2+4x,6)");
		Polynom anyPolynom = new Polynom("3x^2+4x");
		assertEquals(true, newCF1.equals(newCF2));
		assertEquals(false, newCF2.equals(anyPolynom));
	}
}
