
package myMath;

import java.util.Comparator;


/**
 * This class represents a simple "Monom" of shape a*x^b, where a is a real number and a is an integer (summed a none negative), 
 * see: https://en.wikipedia.org/wiki/Monomial 
 * The class implements function and support simple operations as: construction, value at x, derivative, add and multiply. 
 * @author Boaz
 *
 */
public class Monom implements function{
	public static final Monom ZERO = new Monom(0,0);
	public static final Monom MINUS1 = new Monom(-1,0);
	public static final double EPSILON = 0.0000001;
	public static final Comparator<Monom> _Comp = new Monom_Comperator();
	public static Comparator<Monom> getComp() {return _Comp;}
	public Monom(double a, int b){
		this.set_coefficient(a);
		this.set_power(b);
	}
	public Monom(Monom ot) {
		this(ot.get_coefficient(), ot.get_power());
	}

	public double get_coefficient() {
		return this._coefficient;
	}
	public int get_power() {
		return this._power;
	}
	/** 
	 * this method returns the derivative monom of this.
	 * @return
	 */
	public Monom derivative() {
		if(this.get_power()==0) {return getNewZeroMonom();}
		return new Monom(this.get_coefficient()*this.get_power(), this.get_power()-1);
	}
	public double f(double x) {
		double ans=0;
		double p = this.get_power();
		ans = this.get_coefficient()*Math.pow(x, p);
		return ans;
	} 
	public boolean isZero() {return this.get_coefficient() == 0;}
	// ***************** add your code below **********************

	//string constructor need update("xx" as "x" "^" as "x")

	/**
	 * This constructor takes a string and extracts the coefficient and the exponent of the monom
	 * @param s string in the form ax^b
	 */
	public Monom(String s) throws NumberFormatException, RuntimeException, NullPointerException {

		s = s.replaceAll("\\s+","");

		if(multipleDelimiters(s)) {
			throw new RuntimeException("Invalid input: " + s);
		}
		if(s==null) {
			throw new NullPointerException("String is null.");
		}
		if(s.length()==0) {
			throw new RuntimeException("Input is empty string.");
		}


		String [] parts = s.split("x");

		int num_parts = parts.length;

		if(num_parts==0&&s.contains("x")) {												//input is "x"
			set_coefficient(1);
			set_power(1);
		}
		else if(num_parts==1) {											//only coefficient or exponent
			if(s.charAt(0)=='x'&&parts[0].charAt(0)=='^') {
				set_coefficient(1);

				set_power(Integer.parseInt(parts[0].substring(1)));

			}
			else if(s.contains("x")){
				set_power(1);
				if(parts[0].equals("-")) {							//input '-x'
					set_coefficient(-1);
				}
				else if(parts[0].equals("+")) {						//input '+x'
					set_coefficient(1);
				}
				else {

					set_coefficient(Double.parseDouble(parts[0]));
				}
			}
			else {
				set_power(0);

				set_coefficient(Double.parseDouble(parts[0]));
			}
		}
		else if(num_parts==2) {										//coeff and exponent expected
			if(!s.contains("^")) {
				throw new RuntimeException("Invalid input: " + s);	//an exponent is expected, if missing '^' sign, throw exception
			}
			if(parts[0].equals("-")) {								//coeff is -1 or 1
				set_coefficient(-1);
			}
			else if(parts[0].equals("+")) {
				set_coefficient(1);
			}
			else if(parts[0].equals("")) {
				set_coefficient(1);
			}
			else {

				set_coefficient(Double.parseDouble(parts[0]));
			}
			set_power(Integer.parseInt(parts[1].substring(1)));	//parse from index 1 since 0 is '^'



		}
		else {
			throw new RuntimeException("Invalid expression: " + s);
		}




	}


	/**
	 * This method adds to monoms together, if their power is the same
	 * @param m Monom to add
	 */

	public void add(Monom m) {
		
		if(m.isZero()) {							//monom to be added is zero monom
		}
		else if(this.isZero()) {					//current monom is zero monom
			this.set_coefficient(m.get_coefficient());
			this.set_power(m.get_power());
		}

		else if(this._power!=m._power) {
			throw new RuntimeException("Can not add two monoms with different exponents.");
			}
		else {
			this._coefficient+=m._coefficient;
		}
	}
	/**
	 * This method multiplies two monoms
	 * @param d Monom to multiply by
	 */

	public void multipy(Monom d) {
		_coefficient*=d._coefficient;
		_power+=d._power;		
	}
	/**
	 * This method builds a string in the format: ax^b
	 */
	public String toString() {
		String ans = "";

		if(_coefficient==0) {
			ans = "0";
		}
		else if(_power==0) {
			ans = ""+_coefficient;
		}			
		else if(_power==1) {
			ans = _coefficient + "x";
		}
		else {
			ans = _coefficient + "x^" + _power;
		}

		return ans;
	}

	/**
	 * This methods checks whether two monoms are equal
	 * @param m Monom to compare to
	 * @return true if equal, false if not
	 */
	public boolean equals(Monom m) {
		int thisPower = this.get_power();
		int mPower = m.get_power();
		
		boolean coeff_deviation = evalCoeffDeviation(this, m);
		
		if(this.isZero()&&m.isZero()) {
			return true;
		}
		else if(coeff_deviation||(thisPower!=mPower)) {
			return false;
		}
		else {
			return true;
		}
	}
	
	public boolean equals(Object obj) {
		if(obj instanceof Monom) {
			return this.equals((Monom)obj);
		}
		else {
			throw new RuntimeException("error.");
		}
	}
	/**
	 * Check whether monom coefficients deviate less than 0.0000001 from each other
	 * @param m Monom 1
	 * @param n Monom 2
	 * @return true if Monom coefficients of m and n are not in epsilon environment of each other, false if they are
	 */
	private Boolean evalCoeffDeviation(Monom m, Monom n) {
		return ((Math.abs(m.get_coefficient()-n.get_coefficient()))>0.000001);
	}

	/**
	 * Check if x is appearing multiple times in Monom (for exampe: 4xx^2), since split() method will remove the x's
	 * @param s Monom is string format
	 * @return true if x appearing more than once, false otherwise
	 */
	private boolean multipleDelimiters(String s) {	
		boolean mult_delim = false;

		int x_num = 0;

		for(int i=0; i<s.length(); i++) {
			if(s.charAt(i)=='x') {
				x_num++;
			}

		}
		if(x_num>1)
			mult_delim = true;
		return mult_delim;
	}
	// you may (always) add other methods.

	//****************** Private Methods and Data *****************


	private void set_coefficient(double a){
		this._coefficient = a;
	}
	private void set_power(int p) {
		if(p<0) {throw new RuntimeException("ERR the power of Monom should not be negative, got: "+p);}
		this._power = p;
	}
	private static Monom getNewZeroMonom() {return new Monom(ZERO);}
	private double _coefficient; 
	private int _power;
	@Override
	public function initFromString(String s) {
		// TODO Auto-generated method stub
		return null;
	}
	@Override
	public function copy() {
		Monom copy = new Monom(this.get_coefficient(), this.get_power());
		return copy;
	}


}
