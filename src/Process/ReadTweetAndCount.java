package Process;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

import Method.Grid;

public class ReadTweetAndCount {

	public HashMap<Grid, Integer> boxRank = new HashMap<Grid, Integer>();
	public HashMap<String, Integer> rowRank = new HashMap<String, Integer>();
	public HashMap<String, Integer> columnRank = new HashMap<String, Integer>();

	public void ReadTwits(String filepath, HashMap<Grid, Integer> grids) throws IOException {

		InputStream is = new FileInputStream(filepath);

		InputStreamReader isr = new InputStreamReader(is);
		JsonReader reader = new JsonReader(isr);

		// read file in stream mode
		reader.beginArray();

		while (reader.hasNext()) {
			// ReadLine(reader);

			reader.beginObject();
			while (reader.hasNext()) {
				String name = reader.nextName();
				if (name.equals("json")) {
					reader.beginObject();
					while (reader.hasNext()) {
						String name_coordinates = reader.nextName();
						if (name_coordinates.equals("coordinates")) {
							reader.beginObject();
							while (reader.hasNext()) {

								String coordinates = reader.nextName();
								if (coordinates.equals("coordinates")) {
									// System.out.println(reader.nextString());
									readCoordinates(reader, grids);
								} else {
									reader.skipValue();
								}
							}
							reader.endObject();
						} else {
							reader.skipValue();
						}
					}
					reader.endObject();

				} else {
					reader.skipValue();
				}

			}
			reader.endObject();
		}
		reader.endArray();
		reader.close();

		for (Grid box : boxRank.keySet()) {
			System.out.println("box:  " + box.getId() + "  " + boxRank.get(box));
		}

		for (String a : rowRank.keySet()) {
			System.out.println("box:  " + a + "  " + rowRank.get(a));
		}
		for (String box : columnRank.keySet()) {
			System.out.println("box:  " + box + "  " + columnRank.get(box));
		}

	}

	public void readCoordinates(JsonReader reader, HashMap<Grid, Integer> grids) throws IOException {

		ArrayList<Double> curpoint = new ArrayList<Double>();
		reader.beginArray();
		while (reader.hasNext()) {

			curpoint.add(reader.nextDouble());
		}

		// check the position
		double first = curpoint.get(0), second = curpoint.get(1);
		if (first > 0 && second < 0) {
			for (Grid g : grids.keySet()) {
				// whether it is in the box
				if (first <= g.getXmax() && first >= g.getXmin() && second <= g.getYmax() && second >= g.getYmin()) {
					grids.put(g, (grids.get(g)) + 1);

					String row = g.getId().charAt(0) + "-Row";
					String column = "Column: " + g.getId().charAt(1);
					if (!rowRank.keySet().contains(row)) {
						rowRank.put(row, 1);
					} else {
						rowRank.put(row, rowRank.get(row) + 1);
					}
					if (!columnRank.keySet().contains(column)) {
						columnRank.put(column, 1);
					} else {
						columnRank.put(column, columnRank.get(column) + 1);
					}
				}
			}
		} else {
			// System.out.println("first is 0 or smaller than second");
		}
		reader.endArray();
	}

}
