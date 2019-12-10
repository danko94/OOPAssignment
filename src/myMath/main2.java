package myMath;

public class main2 {

	public static void main(String[] args) {
		String s = "abc f(x)= 2";
		String parts[] = s.split("f\\(x\\)= ");
		System.out.println(parts.length);
	}

}
