package Process;

import java.util.HashMap;

public class TestDriver {

	public static void main(String[] args) {

		ReadFileAndRank rr = new ReadFileAndRank();
		HashMap<Grid, Integer> grids = rr.readGrid("src/data/melbGrid.json");
		rr.ReadTwits(grids, "src/data/smallTwitter.json");
		rr.getGridRank(grids);
		rr.getRowRank(grids);
		rr.getColumnRank(grids);
	}

}