package Ex1Testing;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.ComplexFunction;
import Ex1.Monom;
import Ex1.Polynom;
import junit.framework.Assert;

class MonomTest {

	@Test
	void testNonEmptyMonom() {
		Monom m1 = new Monom("3x^3");
		assertNotNull(m1);
	}

	@Test
	void testDerivativeMonom() {
		Monom m1 = new Monom("3x^3");
		Monom result = new Monom("9x^2");

		Monom newMonom = m1.derivative();
		assertEquals(result, newMonom);
	}

	@Test
	void testFMonom() {
		Monom m = new Monom("2x^2");
		assertEquals(8, m.f(2));
	}

	@Test
	void testAddMonom() {
		Monom monom1 = new Monom("2x");
		Monom monom2 = new Monom("3x");
		Monom result = new Monom("5x");

		monom1.add(monom2);
		assertEquals(result, monom1);
	}

	@Test
	void testMultiplyMonom() {
		Monom monom1 = new Monom("5x^2");
		Monom monom2 = new Monom("2x");
		Monom result = new Monom("10x^3");

		monom1.multipy(monom2);
		assertEquals(result, monom1);
	}

	@Test
	void testEqualsMonom() {
		Monom monom1 = new Monom("5x^2");
		Monom monom2 = new Monom("5x^2");
		Monom monom3 = new Monom("1");
		
		Monom m = new Monom("3x^2");
		Polynom p1 = new Polynom("3x^2+4x+5");
		Polynom p2 = new Polynom("4x+5");
		ComplexFunction cF = new ComplexFunction("Plus(3x^2,Plus(4x,-4x))");
		p1.substract(p2);

		assertEquals(m, p1);
		assertEquals(m, cF);
		assertEquals(monom1, monom2);
		assertNotEquals(monom2, monom3);
	}

	@Test
	void testStringConstructor(){
		String numberFormat = "x^y";
		try {
			Monom numForEx = new Monom(numberFormat);	
		} catch (Exception e) {
			assertTrue(e instanceof NumberFormatException);
		}
		String nullPointer = null;
		try {
			Monom numForEx = new Monom(nullPointer);	
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}
		String runTime = "x^-1";
		try {
			Monom numForEx = new Monom(runTime);	
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}



	}

}
