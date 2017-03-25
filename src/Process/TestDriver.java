package Process;

import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;

import com.fasterxml.jackson.core.JsonFactory;
import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonToken;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class TestDriver {

	public static void main(String[] args) {

		TestDriver td = new TestDriver();
		try {
			td.readGrids("src/data/melbGrid.json");
		} catch (JsonParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public Hashtable<Grid, Integer> readGrids(String filename) throws JsonParseException, IOException {
		Hashtable<Grid, Integer> result = new Hashtable<Grid, Integer>();

		File file = new File(filename);
		ObjectMapper mapper = new ObjectMapper();
		// Grid[] grids =
		// mapper.readValue(file,TypeFactory.defaultInstance().constructArrayType(Grid.class));
		//
		// Grid[] array = mapper.readValue(file, Grid[].class);
		// System.out.println(array);
		JsonFactory factory = new JsonFactory();
		JsonParser parser = factory.createJsonParser(file);
		while (!parser.isClosed()) {
			JsonToken jsonToken = parser.nextToken();
			if (JsonToken.FIELD_NAME.equals(jsonToken)) {
				String fieldName = parser.getCurrentName();

				System.out.println(fieldName);

				Grid[] array1 = mapper.readValue(file, Grid[].class);
				System.out.println(array1);

				jsonToken = parser.nextToken();

				if (fieldName.equals("features")) {
					if (fieldName.equals("properties")) {
						Grid g = new Grid();
						if ("id".equals(fieldName)) {
							// car.brand = parser.getValueAsString();
							String id = parser.getValueAsString();
							System.out.println("--id is :" + id);
							g.setId(id);
						} else if (fieldName.equals("xmin")) {
							float xmin = parser.getFloatValue();
							System.out.println("--xmin is :" + xmin);
							g.setXmin(xmin);
						}

						result.put(g, 0);
					}

				}

			}
		}
		// // System.out.println("jsonToken = " + jsonToken);
		return result;
	}

	// return result;

	public Hashtable<String, Integer> assignTwitsToGrids(Hashtable<String, Integer> cur, String fileName) {

		return cur;
	}
}
