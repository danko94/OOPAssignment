package Ex1Testing;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import Ex1.Monom;
import Ex1.Polynom;
import Ex1.Polynom_able;

class PolynomTest {

	@Test
	void testNonEmptyPolynom() {
		Polynom newPolynom1 = new Polynom("7x^5+4x^2+3x+3");
		assertNotNull(newPolynom1);
		
		Polynom newPolynom2 = new Polynom();
		String[] Monoms = {"7x^5","1","3x^2","x","x^2","2x","2"};
		for (int i = 0; i < Monoms.length; i++) {
			try {
			Monom m = new Monom(Monoms[i]);
			newPolynom2.add(m);
			} catch (Exception e) {
				System.out.println("Invalid monom has been inserted! Refer it as 0");
			}
		}
		
		assertNotNull(newPolynom2);
		assertTrue(newPolynom2.equals(newPolynom1));
	}

	@Test
	void testFPolynom() {
		Polynom newPolynom1 = new Polynom("5x^5+4x^4+3x^3+2x^2");
		Polynom newPolynom2 = new Polynom("1");
		
		assertEquals(14, newPolynom1.f(1));
		assertEquals(1, newPolynom2.f(50));
		
		assertTrue(newPolynom1.f(1) != newPolynom2.f(1));
		assertFalse(newPolynom1.f(1) == newPolynom2.f(1));
	}
	
	@Test
	void testAddPolynom() {
		Polynom newPolynom1 = new Polynom("5x^4+6x^3+8x^2-3x+4");
		Polynom newPolynom2 = new Polynom("2x^5+11x^2+2");
		Polynom result1 = new Polynom("2x^5+5x^4+6x^3+19x^2-3x+6");
		
		newPolynom1.add(newPolynom2);
		assertTrue(newPolynom1.equals(result1));
		
		Polynom newPolynom3 = new Polynom("3x+1");
		Polynom newPolynom4 = new Polynom("x+2");
		Polynom result2 = new Polynom("x");
		
		newPolynom3.add(newPolynom4);
		assertEquals(false, newPolynom3.equals(result2));
	}
	
	@Test
	void testAddPolynomMonom() {
		Polynom newPolynom1 = new Polynom("x^2+1");
		Monom newMonom = new Monom("4x^2");
		Polynom result1 = new Polynom("5x^2+1");
		
		newPolynom1.add(newMonom);
		assertEquals(true, newPolynom1.equals(result1));
	}
	
	@Test
	void testMultiplyPolynoms() {
		Polynom newPolynom1 = new Polynom("-2x^5+5x^4+6x^3-3x^2-3x+2");
		Polynom newPolynom2 = new Polynom("2x^5+11x^2+2");
		Polynom result1 = new Polynom("-4x^10+10x^9+12x^8-28x^7+49x^6+66x^5-23x^4-21x^3+16x^2-6x+4");
		
		newPolynom1.multiply(newPolynom2);
		assertEquals(true, newPolynom1.equals(result1));
		
		Polynom newPolynom3 = new Polynom("x+1");
		Polynom newPolynom4 = new Polynom("x+2");
		Polynom result2 = new Polynom("3x+2");
		
		newPolynom3.multiply(newPolynom4);
		assertFalse(newPolynom3.equals(result2));
	}
	
	@Test
	void testMultiplyPolynomMonom() {
		Polynom newPolynom = new Polynom("x+1");
		Monom newMonom = new Monom("3x");
		Polynom result1 = new Polynom("3x^2+3x");
		
		newPolynom.multiply(newMonom);
		assertEquals(result1, newPolynom);
	}
	
	
	@Test
	void testSubstract() {
		Polynom newPolynom1 = new Polynom("10x^5+6x^2+9");
		Polynom newPolynom2 = new Polynom("10x^5+6x^2+9");
		newPolynom1.substract(newPolynom2);
		Polynom result1 = new Polynom("0");
		assertEquals(result1, newPolynom1);
		Polynom newPolynom3 = new Polynom("6x^3+1");
		Polynom newPolynom4 = new Polynom("4x^2+4");
		newPolynom3.substract(newPolynom4);
		Polynom_able result2 = new Polynom("6x^3-4x^2-3");
		assertEquals(result2, newPolynom3);
	}
	
	
	@Test
	void testEquals() {
		Polynom newPolynom1 = new Polynom("2x^3");
		Polynom newPolynom2 = new Polynom("5x^3");
		assertNotEquals(newPolynom1, newPolynom2);
	}
	
	@Test
	void testIsZero() {
		Polynom newPolynom1 = new Polynom("0x^3+0x^2+0x+0");
		assertTrue(newPolynom1.isZero());
	}
	
	@Test
	void testRoot() {
		Polynom newPolynom = new Polynom("x^3");
		double pRoot = newPolynom.root(-3.14, 2.72, Monom.EPSILON);
		assertFalse(pRoot == 1);
	}
	
	@Test
	void testCopy() {
		Polynom newPolynom1 = new Polynom("10x^5+6x^2+9");
		Polynom_able newPolynom2 = newPolynom1.copy();
		newPolynom1.add(new Polynom("x^11+x^2"));
		
		assertNotEquals(newPolynom1, newPolynom2);
	}
	
	@Test
	void testDerivative() {
		Polynom newPolynom1 = new Polynom("2x^3+5x^2+6x+1");
		Polynom newPolynom2 = new Polynom("6x^2+10x+6");
		Polynom_able newPolynomder = newPolynom1.derivative();
		
		assertEquals(newPolynom2, newPolynomder);
		assertFalse(newPolynom1.derivative() == newPolynom2.derivative().derivative());
	}
	
	@Test
	void testArea() {
		Polynom newPolynom = new Polynom("x^3");
		double pArea = newPolynom.area(0, 2, Monom.EPSILON);
		assertTrue(pArea == 3.9999996006061598);
	}
	
	@Test
	void testStringConstructor(){
		String numberFormat = "x^y+2";
		try {
			Polynom numForEx = new Polynom(numberFormat);	
		} catch (Exception e) {
			assertTrue(e instanceof NumberFormatException);
		}
		String nullPointer = null;
		try {
			Polynom numForEx = new Polynom(nullPointer);	
		} catch (Exception e) {
			assertTrue(e instanceof NullPointerException);
		}
		String runTime = "x^-1";
		try {
			Polynom numForEx = new Polynom(runTime);	
		} catch (Exception e) {
			assertTrue(e instanceof RuntimeException);
		}
		
		
		
	}
}
