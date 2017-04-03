package Method;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.util.ArrayList;
import java.util.HashMap;

import com.google.gson.JsonArray;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

public class Count {

	public int[] ReadTwits(String content, ArrayList<Grid> grids) throws IOException {

		int[] results = new int[grids.size()];
		for (int i = 0; i < results.length; i++) {
			results[i] = 0;
		}

		JsonReader reader = new JsonReader(new StringReader(content));

		// read file in stream mode
		reader.beginObject();

		while (reader.hasNext()) {
			// ReadLine(reader);

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
								Grid id = readCoordinates(reader, grids);
								if (id != null) {
									results[grids.indexOf(id)]++;
								}
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
		reader.close();

		return results;
	}

	public Grid readCoordinates(JsonReader reader, ArrayList<Grid> grids) throws IOException {

		Grid result = null;
		ArrayList<Double> curpoint = new ArrayList<Double>();
		reader.beginArray();
		while (reader.hasNext()) {

			curpoint.add(reader.nextDouble());
		}

		// check the position
		double first = curpoint.get(0), second = curpoint.get(1);
		if (first > 0 && second < 0) {
			for (Grid g : grids) {
				// whether it is in the box
				if (first <= g.getXmax() && first >= g.getXmin() && second <= g.getYmax() && second >= g.getYmin()) {

					result = g;
				}
			}
		}
		reader.endArray();

		return result;
	}

	public void CountAndRank(int[] allresutls) {

	}
}
