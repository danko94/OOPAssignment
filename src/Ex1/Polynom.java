 package Ex1;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.StringTokenizer;

import Ex1.Monom;
/**
 * This class represents a Polynom with add, multiply functionality, it also should support the following:
 * 1. Riemann's Integral: https://en.wikipedia.org/wiki/Riemann_integral
 * 2. Finding a numerical value between two values (currently support root only f(x)=0).
 * 3. Derivative
 * 
 * @author Boaz
 *
 */
public class Polynom implements Polynom_able{

	private LinkedList<Monom> polynom;
	

	/**
	 * Zero (empty polynom)
	 */
	public Polynom() {
		polynom = new LinkedList<Monom>();
		polynom.add(new Monom(Monom.ZERO));
	}
	/**
	 * init a Polynom from a String such as:
	 *  {"x", "3+1.4X^3-34x", "(2x^2-4)*(-1.2x-7.1)", "(3-3.4x+1)*((3.1x-1.2)-(3X^2-3.1))"};
	 * @param s: is a string represents a Polynom
	 */
	public Polynom(String s) throws NullPointerException, NumberFormatException, RuntimeException {						//add string=null
		polynom = new LinkedList<Monom>();
		
		s = s.replaceAll("\\s+","");
		
		String st1 = "?"+s.charAt(0);
		for(int i=1;i<s.length();i++) {
			char c = s.charAt(i);
			char last = s.charAt(i-1);
			if(c=='+'&&last!='^') {
				st1+="?+";
			}
			else if(c=='-'&&last!='^') {
				st1+="?-";
			}
			else {
				st1+=c;
			}
		}

		StringTokenizer stok = new StringTokenizer(st1, "?");
		
		while(stok.hasMoreTokens()) {
			Monom m = new Monom(stok.nextToken());
			if(m.get_coefficient()!=0) {
				this.add(m);
			}
		}
		
		
		
	}

	@Override
	public double f(double x) {
		double ans = 0;	
		Iterator<Monom> iter = this.iteretor();
		while(iter.hasNext()){
			ans+=iter.next().f(x);
		}
		return ans;
	}

	@Override
	public void add(Polynom_able p1) {
		Iterator<Monom> p1_iter = ((Polynom)p1).iteretor();

		while(p1_iter.hasNext()) {
			add(p1_iter.next());
		}
	}


	//needs more checks

	@Override
	public void add(Monom m1) {
		
		if(m1.isZero()) {						//don't add zero monom
			return;
		}
		
		Iterator<Monom> iter = this.iteretor();

		
		while(iter.hasNext()) {
			Monom m = iter.next();
			if(m.get_power()==m1.get_power()) {
				m.add(m1);
				return;
			}
		}
		polynom.add(m1);
		polynom.sort(Monom._Comp);
		
	}

	@Override
	public void substract(Polynom_able p1) {
		Iterator<Monom> iter = ((Polynom)p1).iteretor();
		
		Polynom toSub = new Polynom();
		
		while(iter.hasNext()) {
			Monom m = iter.next();
			double coeff = m.get_coefficient();
			int exp = m.get_power();
			m = new Monom(-coeff, exp);
			toSub.add(m);
		}
		
		this.add(toSub);
		

	}

	@Override
	public void multiply(Polynom_able p1) {
		Iterator<Monom> p1_iter = ((Polynom)p1).iteretor();
		
		Polynom orig = (Polynom)this.copy();				//make a deep copy of original polynom
		this.substract(this);							//original polynom = 0

		while(p1_iter.hasNext()) {
			Polynom p2 = (Polynom)orig.copy();	
			p2.multiply(p1_iter.next());
			this.add(p2);
		}
		
	
		
	}
	@Override
	public boolean equals(Object obj) throws RuntimeException{
		if(obj instanceof Polynom) {
			return this.equals((Polynom)obj);
		}
		else if(obj instanceof ComplexFunction){
			ComplexFunction cF = new ComplexFunction(this);
			return ((ComplexFunction)obj).equals(cF);
			}
		else if(obj instanceof Monom) {
			Polynom p = new Polynom();
			p.add((Monom)obj);
			return this.equals(p);			
		}
		else
			throw new RuntimeException("Incompatible object");
	}


	public boolean equals(Polynom_able p1) {  					//needs testing
		Iterator<Monom> p1_iter = ((Polynom)p1).iteretor();
		Iterator<Monom> this_iter = this.iteretor();
		boolean flag = true;
		
		Polynom wo_zero = new Polynom();						//create 2 new polynom that do not include zero monoms
		Polynom this_wo_zero = new Polynom();					//because the polynoms could have monoms like 0x^7
		
		while(p1_iter.hasNext()) {
			Monom m = p1_iter.next();
			if(!m.isZero()) {
				wo_zero.add(m);
			}
		}
		
		while(this_iter.hasNext()) {
			Monom m = this_iter.next();
			if(!m.isZero()) {
				this_wo_zero.add(m);
			}
		}
		
		Iterator<Monom> wo_zero_iter = wo_zero.iteretor();
		Iterator<Monom> this_wo_zero_iter = this_wo_zero.iteretor();
		
		while(wo_zero_iter.hasNext()&&this_wo_zero_iter.hasNext()) {
			Monom m = wo_zero_iter.next();
			Monom n = this_wo_zero_iter.next();
			if(!m.equals(n)) {
				flag=false;
			}
		}
		
		while(wo_zero_iter.hasNext()) {
			if(!wo_zero_iter.next().isZero()) {
				flag=false;
			}
		}
		while(this_wo_zero_iter.hasNext()) {
			if(!this_wo_zero_iter.next().isZero()) {
				flag=false;
			}
		}
		
		return flag;
	}

	
	
	@Override
	public boolean isZero() {	
		Iterator<Monom> iter = this.iteretor();
		
		while(iter.hasNext()) {
			if(!iter.next().isZero()) {
				return false;
			}
		}
		return true;
		
	}

	@Override
	public double root(double x0, double x1, double eps) throws RuntimeException {
		
		double f_x0 = this.f(x0);
		double f_x1 = this.f(x1);
		
		if(f_x0*f_x1>0) {
			throw new RuntimeException("There is no (single) root in the given range");	//both function values are pos or neg
		}
		if(f_x0==0){
			return x0;
			}
		if(f_x1==0) {
			return x1;
		}
		
		if(x1<x0) {							//swap x0 and x1 if x1<x0
			double temp = x0;
			x0 = x1;
			x1 = temp;
		}
		boolean leftIsNeg = f_x0<0;
		
		double margin = 1;
		double root = 0;
		
		
		while(margin>0) {					//basically while(true)
			double mid = x0+((x1-x0)/2);
			double fValMid = this.f(mid);
			if(Math.abs(fValMid)<eps) {
				return mid;
			}
			else if(leftIsNeg&&fValMid<0) {
				x0=mid;
			}
			else if(!leftIsNeg&&fValMid>0){
				x0=mid;
			}
			else if(!leftIsNeg&&fValMid<0){
				x1=mid;
 			}
			else {
				x1=mid;
			}
		}
		return root;
	}

	@Override	 
	public function copy() {
		Iterator<Monom> iter = this.iteretor();
		Polynom p_copy= new Polynom();
		
		while(iter.hasNext()) {
			Monom m = iter.next();
			p_copy.add(new Monom(m.get_coefficient(),m.get_power()));
		}
		
		return p_copy; 
	}
	

	@Override
	public Polynom_able derivative() {
		Iterator<Monom> iter = this.iteretor();
		
		Polynom p2 = new Polynom();
		
		while(iter.hasNext()) {
			Monom m = new Monom(iter.next().derivative());
			p2.add(m);
		}
		return p2;
	}

	@Override
	public double area(double x0, double x1, double eps) {
		double area = 0;
		
		if(x1<=x0) {							//0 by definition
			return area;
		}
		
		for(double n = x0; n<x1; n+=eps) {
			double f_n = this.f(n);
			double step_area = eps*f_n;
			if(f_n>=0) {
				area+=step_area;
			}
		}
		return area;
	}

	@Override
	public Iterator<Monom> iteretor() {
		Iterator<Monom> it = polynom.listIterator(); 
		return it;
	}
	@Override
	public void multiply(Monom m1) {
		Iterator<Monom> iter = this.iteretor();
		while(iter.hasNext()) {
			iter.next().multipy(m1);
		}
	}
	
	public String toString() {
		
		String ans ="";
		
		boolean first = true;
		
		Iterator<Monom> iter = this.iteretor();
		
		while(iter.hasNext()) {
			Monom m = iter.next();
			if(first&&!m.isZero()) {
				ans+=m.toString();
				first = false;
			}
			else if(!m.isZero()){
				if(m.get_coefficient()>0) {				//coefficient is positive
					ans+= "+"+m.toString();
				}
				else {
					ans+=m.toString();					//coefficient is negative, don't add "+"
				}
			}
		}
		if(ans.equals("")) {							//Polynom is empty
			ans="0";
		}
		return ans;
		
	}
	@Override
	public function initFromString(String s) {
		
		Polynom polynom = new Polynom(s);
		return polynom;
	}
	
	
	


}
