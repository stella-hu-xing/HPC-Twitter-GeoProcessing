package Process;

import java.io.IOException;
import java.util.HashMap;

import mpi.MPI;

public class TestDriver {

	public static void main(String[] args) throws IOException {

		long stamp_before_preprocess = System.currentTimeMillis();
		System.out.println("start at:  " + stamp_before_preprocess);

		// ReadFileAndRank rr = new ReadFileAndRank();
		// HashMap<Grid, Integer> grids = rr.readGrid("src/data/melbGrid.json");
		// rr.ReadTwits(grids, "src/data/tinyTwitter.json");
		// rr.getGridRank(grids);
		// rr.getRowRank(grids);
		// rr.getColumnRank(grids);

		ReadInStream ris = new ReadInStream();
		HashMap<Grid, Integer> gridss = ris.readGrid("src/data/melbGrid.json");
		ris.ReadTwits("src/data/tinyTwitter.json", gridss);

		long stamp_end = System.currentTimeMillis();
		System.out.println("start at:  " + stamp_end + " the duration is: " + (stamp_end - stamp_before_preprocess));
		//
		// // using default args
		MPI.Init(args);

		int rank = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();
		int master_rank = 0;
		final int START_OFFSET = 0;

		int total_tweet_count = gridss.size();
		int work_load_per_task = total_tweet_count / size;

	}

}