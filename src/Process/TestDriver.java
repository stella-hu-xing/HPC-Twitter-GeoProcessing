package Process;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Hashtable;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

public class TestDriver {

	public static void main(String[] args) {

		TestDriver td = new TestDriver();
		td.readGrid("src/data/melbGrid.json");

	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public Hashtable<Grid, Integer> readGrid(String filename) {
		Hashtable<Grid, Integer> grids = new Hashtable<Grid, Integer>();

		JSONParser parser = new JSONParser();

		try {
			Object obj = parser.parse(new FileReader(filename));

			JSONObject jsonObject = (JSONObject) obj;

			JSONArray features = (JSONArray) jsonObject.get("features");

			int counter = 0;

			while (counter < features.size()) {
				JSONObject row = (JSONObject) features.get(counter);

				JSONObject properties = (JSONObject) row.get("properties");
				System.out.println("currrr   " + properties);

				counter++;

				Grid g = new Grid();
				String id = (String) properties.get("id");
				System.out.println("id :" + id);
				double xmin = (double) properties.get("xmin");
				double xmax = (double) properties.get("xmax");
				double ymin = (double) properties.get("ymin");
				double ymax = (double) properties.get("ymax");

				g.setId(id);
				g.setXmax(xmax);
				g.setXmin(xmin);
				g.setYmax(ymax);
				g.setYmin(ymin);

				grids.put(g, 0);
			}

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		System.out.println(grids);
		return grids;
	}

	/**
	 * 
	 * @param grids
	 * @param twitsFile
	 * @return
	 */
	public Hashtable<Grid, Integer> ReadTwits(Hashtable<Grid, Integer> grids, String twitsFile) {

		JSONParser parser = new JSONParser();

		Object obj;
		try {
			obj = parser.parse(new FileReader(twitsFile));
			// JSONObject jsonObject = (JSONObject) obj;

			JSONArray contents = (JSONArray) obj;

			int counter = 0;

			while (counter < contents.size()) {
				JSONObject row = (JSONObject) contents.get(counter);
				JSONObject json = (JSONObject) row.get("json");
				
			}

		} catch (IOException | ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return grids;
	}

	public Hashtable<String, Integer> getBoxRank() {
		Hashtable<String, Integer> order = new Hashtable<String, Integer>();

		return order;
	}

	public Hashtable<String, Integer> getRowRank() {
		Hashtable<String, Integer> order = new Hashtable<String, Integer>();

		return order;
	}

	public Hashtable<String, Integer> getColumnRand() {
		Hashtable<String, Integer> order = new Hashtable<String, Integer>();

		return order;
	}
}
