package myMath;

import java.awt.Color;
import java.awt.Font;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Functions_GUI implements functions{

	ArrayList<function> functions = new ArrayList();


	public static Color[] Colors = {Color.blue, Color.cyan,
			Color.MAGENTA, Color.ORANGE, Color.red, Color.GREEN, Color.PINK};


	@Override
	public int size() {
		return functions.size();
	}

	@Override
	public boolean isEmpty() {
		return functions.isEmpty();
	}

	@Override
	public boolean contains(Object o) {
		return functions.contains(o);
	}

	@Override
	public Iterator<function> iterator() {
		return functions.iterator();
	}

	@Override
	public Object[] toArray() {
		return functions.toArray();
	}

	@Override
	public <T> T[] toArray(T[] a) {
		return functions.toArray(a);
	}

	@Override
	public boolean add(function e) {
		return (this.functions.add(e));
	}

	@Override
	public boolean remove(Object o) {
		return functions.remove(o);
	}

	@Override
	public boolean containsAll(Collection<?> c) {
		return functions.containsAll(c);
	}

	@Override
	public boolean addAll(Collection<? extends function> c) {
		return functions.addAll(c);
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		return functions.removeAll(c);
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		return functions.retainAll(c);
	}

	@Override
	public void clear() {
		functions.clear();
	}

	@Override
	public void initFromFile(String file) throws IOException {


		try { 
			BufferedReader bR = new BufferedReader(new FileReader(file));
			String line = bR.readLine();
			while(line!=null) {
				String parts[] = line.split("f\\(x\\)= ");

				this.add(new ComplexFunction(parts[1]));
				line=bR.readLine();
			}
			bR.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	@Override
	public void saveToFile(String file) throws IOException {

		try
		{
			PrintWriter pw = new PrintWriter(new File(file));
			for(int i=0; i<functions.size();i++)
				pw.write(i+") " + Colors[i%Colors.length].toString() + " f(x)= " + functions.get(i).toString()+"\n");
			pw.close();
		}
		catch (IOException e)
		{
			e.printStackTrace();
			return;
		}

	}


	//@Override
	public void drawFunctions1(int width, int height, Range rx, Range ry, int resolution) {
		StdDraw.setCanvasSize(width, height);
		double maxY = ry.get_max();
		double minY = ry.get_min();
		double minX = rx.get_min();
		double maxX = rx.get_max();


		double[] x = new double[resolution+1];
		double[] y = new double[resolution+1];



		// rescale the coordinate system
		StdDraw.setXscale(minX, maxX);				//window size
		StdDraw.setYscale(minY, maxY);


		StdDraw.setPenColor(Color.LIGHT_GRAY);

		for (double i = minY; i <= maxY; i=i+1) { //vertical lines
			StdDraw.line(minX, i, maxX, i);
		}
		for (double i = minX; i <= maxX; i=i+1) {		//horizontal lines
			StdDraw.line(i, minY, i, maxY);
		}

		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setPenRadius(0.005);
		StdDraw.line(minX, 0, maxX, 0); //x axis

		StdDraw.line(0, minY, 0, maxY);  //y axis

		StdDraw.setFont(new Font("TimesRoman", Font.BOLD, 15));
		for (double i = minX; i <= maxX; i=i+1) {  //numbers on x axis
			StdDraw.text(i,0,Double.toString(i));
		}
		for (double i=minY; i <= maxY; i++) {		//numbers on y axis
			StdDraw.text(0, i, Double.toString(i));
		}


		for(int j = 0; j <= resolution; j++) {
			x[j] = minX+((double)j/resolution)*(maxX-minX);
		}
		for(int j = 0; j < this.functions.size(); j++) {
			StdDraw.setPenColor(Colors[j%Colors.length]);

			for(int i = 0; i<=resolution; i++) {
				y[i] = this.functions.get(j).f(x[i]);
			}
			//plot functions
			for (int i = 0; i < resolution; i++) {			
				StdDraw.line(x[i], y[i], x[i+1], y[i+1]);
			}

		}

	}

	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) {
		if(rx.isEmpty() ||ry.isEmpty()) return;

		double minX = rx.get_min(), maxX = rx.get_max();
		double minY = ry.get_min(), maxY = ry.get_max();
		StdDraw.setCanvasSize(width,height);
		StdDraw.setXscale(minX,maxX);
		StdDraw.setYscale(minY,maxY);

		////////horizontal lines
		StdDraw.setPenColor(Color.GRAY);
		for(double i=minX;i<=maxX;i++) {
			StdDraw.line(i, minY, i, maxY);
		}
		////////vertical lines
		for(double i=minY;i<=maxY;i+=2) {
			StdDraw.line(minX, i, maxX, i);
		}

		for(double i=minX;i<=maxX;i++) {
			if(i!=0)
				StdDraw.text(i, -0.30,Integer.toString((int) i));  //double?
		}

		for(double i=minY;i<=maxY;i++) {
			if(i!=0)
				StdDraw.text(-0.20,i,Integer.toString((int) i));
		}

		StdDraw.setPenColor(Color.black);
		StdDraw.setPenRadius(0.005);
		//y axis
		StdDraw.line(0, minY, 0, maxY);
		//x axis
		StdDraw.line(minX, 0, maxX, 0);

		double x_steps= (Math.abs(rx.get_max())+Math.abs(ry.get_min()))/resolution;
		for(int i=0;i<functions.size();i++) {
			System.out.println(functions.get(i).toString());
			StdDraw.setPenColor(Colors[i%Colors.length]);
			for(double j=minX;j<maxX;j+=x_steps) {
				StdDraw.line(j, functions.get(i).f(j), j+(x_steps), functions.get(i).f(j+(x_steps)));
			}


		}

	}
	@Override
	public void drawFunctions(String json_file) {

		Gson gson = new Gson();


		Parameters params = new Parameters();
		try 
		{
			//Option 2: from JSON file to Object
			FileReader reader = new FileReader(json_file);
			params = gson.fromJson(reader,Parameters.class);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		Iterator<JsonElement> iter = params.Range_X.iterator();
		Iterator<JsonElement> iter1 = params.Range_Y.iterator();


		Range rx = new Range(iter.next().getAsDouble(), iter.next().getAsDouble());
		Range ry = new Range(iter1.next().getAsDouble(), iter1.next().getAsDouble());

		drawFunctions(params.Width, params.Height, rx, ry, params.Resolution);


	}

		//System.out.println(params.Width);








	}

