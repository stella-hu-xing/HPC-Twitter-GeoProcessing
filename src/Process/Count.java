package Process;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.stream.JsonReader;

public class Count {

	public int[] ReadTwits(String filepath, HashMap<Grid, Integer> grids, int startLine, int unitSize) throws IOException {

		int[] results = new int[grids.size()];

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

		return results;
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

				}
			}
		} else {
			// System.out.println("first is 0 or smaller than second");
		}
		reader.endArray();
	}

}
