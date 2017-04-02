package Process;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.HashMap;
import java.util.stream.Stream;

import mpi.MPI;
import mpjdev.javampjdev.MPJDev;

public class TestDriver {

	public static void main(String[] args) throws IOException {

		String fileMelb = "src/data/melbGrid.json";
		String fileTwitter = "src/data/tinyTwitter.json";

		long stamp_start = System.currentTimeMillis();
		System.out.println("start at:  " + stamp_start);

		// // using default args
		MPI.Init(args);

		int myrank = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();

		int masterRank = 0;
		int tag = 100;
		int unitSize = 1;

		// handle the master
		if (myrank == masterRank) {

			File twitters = new File(fileTwitter);

			Stream<String> lines = Files.lines(twitters.toPath());
			long total_tweet_count = lines.count();
			System.out.println("total processors number is: " + size);
			System.out.println("total line number is " + total_tweet_count);

			int workload_per_task = (int) (total_tweet_count / (size - 1));
			System.out.println("per task workload:  " + workload_per_task);

			int sendbuf[] = new int[unitSize * (size - 1)];
			for (int i = 0; i < sendbuf.length; i++) {
				sendbuf[i] = workload_per_task * i;
			}

			for (int i = 1; i < size; i++) {
				MPI.COMM_WORLD.Send(sendbuf, unitSize * (i - 1), unitSize, MPI.INT, i, tag);
			}
		} else {

			int recebuf[] = new int[unitSize];
			MPI.COMM_WORLD.Recv(recebuf, 0, unitSize, MPI.INT, masterRank, tag);

			int start_line = recebuf[0];
			Grid g = new Grid();
            HashMap<Grid, Integer> grids = g.readGrid(fileMelb);
            System.out.println("grids: "+ grids.size());
		}

		MPI.Finalize();

		long stamp_end = System.currentTimeMillis();
		System.out.println("end at:  " + stamp_end + " duration is: " + (stamp_end - stamp_start));
	}

}