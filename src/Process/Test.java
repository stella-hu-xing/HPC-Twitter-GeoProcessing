package Process;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.stream.Stream;

public class Test {

	public static void main(String args[]) {
		String filename = "src/data/melbGrid.json";
		File f = new File(filename);

		Grid g = new Grid();
		// long len = f.length();
		try (Stream<String> lines = Files.lines(f.toPath())) {
			System.out.println("counts: " + lines.count());

			HashMap<Grid, Integer> grids = g.readGrid(filename);
			HashMap<String, Integer> row = g.readRow(grids);
			System.out.println(row);
			HashMap<String, Integer> column = g.readColumn(grids);
			System.out.println(column);
			

		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
