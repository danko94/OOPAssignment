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
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

public class Functions_GUI implements functions{
	
	public class Parameters {
		
		int Width, Height, Resolution;
		double [] Range_X, Range_Y;


	}


	private ArrayList<function> functions = new ArrayList();

	private static Color[] Colors = {Color.blue, Color.cyan,
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


	@Override
	public void drawFunctions(int width, int height, Range rx, Range ry, int resolution) throws RuntimeException{

		if(rx.isEmpty() ||ry.isEmpty()) 
			throw new RuntimeException("Range is empty");

		double minX = rx.get_min(), maxX = rx.get_max();
		double minY = ry.get_min(), maxY = ry.get_max();
		double stepSize = (Math.abs(minX-maxX))/resolution;

		StdDraw.setCanvasSize(width,height);
		StdDraw.setXscale(minX,maxX);
		StdDraw.setYscale(minY,maxY);

		////////horizontal lines
		StdDraw.setPenColor(Color.GRAY);
		for(double i=minX;i<=maxX;i++) {
			StdDraw.line(i, minY, i, maxY);
		}
		////////vertical lines
		for(double i=minY;i<=maxY;i++) {
			StdDraw.line(minX, i, maxX, i);
		}
		StdDraw.setPenColor(Color.BLACK);
		StdDraw.setFont(new Font("Comic Sans MS", Font.BOLD, 15));
		for(double i=minX;i<=maxX;i++) {
			StdDraw.text(i, -0.4, Integer.toString((int) i));  //double?
		}
		for(double i=minY;i<=maxY;i++) {
			if(i!=0)
				StdDraw.text(-0.2,i+0.05,Integer.toString((int) i));
		}

		StdDraw.setPenRadius(0.005);
		
		//y axis
		StdDraw.line(0, minY, 0, maxY);
		
		//x axis
		StdDraw.line(minX, 0, maxX, 0);


		for(int i=0;i<functions.size();i++) {				
			System.out.println(functions.get(i).toString());
			StdDraw.setPenColor(Colors[i%Colors.length]);
			
			for(double j=minX;j<maxX;j+=stepSize) {		//Plot functions
				try {
					StdDraw.line(j, functions.get(i).f(j), j+(stepSize), functions.get(i).f(j+(stepSize)));
				} catch (NumberFormatException e) {
					//Don't do anything, when Division by 0
				}
			}


		}

	}
	@Override
	public void drawFunctions(String json_file){

		Gson gson = new Gson();


		Parameters params = new Parameters();
		
		try 
		{
			FileReader reader = new FileReader(json_file);
			params = gson.fromJson(reader,Parameters.class);
		} 
		catch (FileNotFoundException e) {
			e.printStackTrace();
		}

		
		Range rx = new Range(params.Range_X[0], params.Range_X[1]);
		Range ry = new Range(params.Range_Y[0], params.Range_Y[1]);

		drawFunctions(params.Width, params.Height, rx, ry, params.Resolution);


	}


}

