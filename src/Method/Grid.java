package Method;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;

public class Grid {

	private String id;

	private double xmin;
	private double xmax;
	private double ymin;
	private double ymax;

	public Grid() {

	}

	public Grid(String id, double xmin, double xmax, double ymin, double ymax) {

		this.id = id;
		this.xmax = xmax;
		this.xmin = xmin;
		this.ymax = ymax;
		this.ymin = ymin;
	}

	/**
	 * 
	 * @param filename
	 * @return
	 */
	public ArrayList<Grid> readGrid(String filename) {

		ArrayList<Grid> boxRank = new ArrayList<Grid>();

		JsonParser parser = new JsonParser();

		try {
			JsonElement ele = parser.parse(new FileReader(filename));

			if (ele.isJsonObject()) {
				JsonObject jsonObject = ele.getAsJsonObject();
				JsonArray features = (JsonArray) jsonObject.get("features");

				for (JsonElement eachline : features) {

					JsonObject line = eachline.getAsJsonObject();
					JsonObject g = (JsonObject) line.get("properties");

					Grid grid = new Grid(g.get("id").getAsString(), g.get("xmin").getAsDouble(),
							g.get("xmax").getAsDouble(), g.get("ymin").getAsDouble(), g.get("ymax").getAsDouble());

					boxRank.add(grid);
				}
			}

		} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
			e.printStackTrace();
		}

		return boxRank;
	}

	public HashMap<String, Integer> readRow(HashMap<Grid, Integer> boxes) {
		HashMap<String, Integer> rows = new HashMap<String, Integer>();
		for (Grid g : boxes.keySet()) {
			String rowId = g.getId().charAt(0) + "-Row";
			if (!rows.containsKey(rowId)) {
				rows.put(rowId, 0);
			}
		}
		return rows;
	}

	public HashMap<String, Integer> readColumn(HashMap<Grid, Integer> boxes) {
		HashMap<String, Integer> columns = new HashMap<String, Integer>();
		for (Grid g : boxes.keySet()) {
			String columnId = "Column " + g.getId().charAt(1);
			if (!columns.containsKey(columnId)) {
				columns.put(columnId, 0);
			}
		}
		return columns;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public double getXmin() {
		return xmin;
	}

	public void setXmin(double xmin) {
		this.xmin = xmin;
	}

	public double getXmax() {
		return xmax;
	}

	public void setXmax(double xmax) {
		this.xmax = xmax;
	}

	public double getYmin() {
		return ymin;
	}

	public void setYmin(double ymin) {
		this.ymin = ymin;
	}

	public double getYmax() {
		return ymax;
	}

	public void setYmax(double ymax) {
		this.ymax = ymax;
	}

}
