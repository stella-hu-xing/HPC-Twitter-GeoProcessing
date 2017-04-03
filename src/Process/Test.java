package Process;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Stream;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import Method.Grid;

public class Test {

	public static void main(String args[]) throws FileNotFoundException {

		ArrayList<String> grids = new ArrayList<String>();
		grids.add("a");
		grids.add("b");
		grids.add("c");
		int[] allResults = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };

		HashMap<String, Integer> box = new HashMap<String, Integer>();

		int[] counts = new int[grids.size()];
		for (int i = 0; i < allResults.length; i++) {
			counts[i % 3] += allResults[i];
		}

		for (int i = 0; i < counts.length; i++) {
			box.put(grids.get(i), counts[i]);
			System.out.println(grids.get(i) + "   :" + box.get(grids.get(i)));
		}

	}

}
