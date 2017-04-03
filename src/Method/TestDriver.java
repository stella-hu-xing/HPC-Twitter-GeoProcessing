package Method;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import mpi.MPI;

public class TestDriver {

	public static void main(String[] args) throws IOException {

		String fileMelb = "src/data/melbGrid.json";
		String fileTwitter = "src/data/smallTwitter.json";

		long stamp_start = System.currentTimeMillis();
		System.out.println("start at:  " + stamp_start);

		// // using default args
		MPI.Init(args);

		int myrank = MPI.COMM_WORLD.Rank();
		int size = MPI.COMM_WORLD.Size();

		int masterRank = 0;
		int tag = 100;
		int unitSize = 1;

		File f = new File(fileTwitter);

		BufferedReader br = new BufferedReader(new FileReader(fileTwitter));

		Stream<String> lines = Files.lines(f.toPath());
		long contentSize = lines.count() - 2;

		Grid g = new Grid();
		ArrayList<Grid> grids = g.readGrid(fileMelb);

		if (size > 1) {

			int workload_ave_task = (int) (contentSize / (size - 1));
			int remains = (int) (contentSize % (size - 1));

			// handle the master
			if (myrank == masterRank) {

				String line = br.readLine();
				line = br.readLine();
				int curWorker = 0;
				while (line != null && !line.equals("]")) {
					Object sendbuf[] = new Object[1];
					sendbuf[0] = (Object) line;
					// int worker = curWor % (size - 1) + 1;
					MPI.COMM_WORLD.Send(sendbuf, 0, unitSize, MPI.OBJECT, curWorker % (size - 1) + 1, tag);
					curWorker++;
					line = br.readLine();
				}

				System.out.println("send all");
				int[] answers = new int[16];

				for (int i = 1; i < size; i++) {
					int[] recebuf = new int[16];
					MPI.COMM_WORLD.Recv(recebuf, 0, 16, MPI.INT, i, i + tag);

					for (int k = 0; k < 16; k++) {
						answers[k] += recebuf[k];
					}

				}
				System.out.println("receive all");
				TestDriver td = new TestDriver();
				td.CalculateAndRank(answers, grids);

			} else {

				Count c = new Count();
				int[] sendbuf = new int[grids.size()];

				int count = 0;

				int workload = (myrank - remains) <= 0 ? workload_ave_task + 1 : workload_ave_task;

				while (count < workload) {

					String recebuf[] = new String[unitSize];
					MPI.COMM_WORLD.Recv(recebuf, 0, unitSize, MPI.OBJECT, masterRank, tag);

					int[] thisResult = c.ReadTwits(recebuf[0], grids);
					for (int i = 0; i < thisResult.length; i++) {
						sendbuf[i] += thisResult[i];
					}
					count++;
				}

				MPI.COMM_WORLD.Send(sendbuf, 0, sendbuf.length, MPI.INT, masterRank, myrank + tag);

			}

			MPI.COMM_WORLD.Barrier();
			// if only one core
		} else {
			int answers[] = new int[16];
			Count c = new Count();

			String line = br.readLine();
			line = br.readLine();
			while (line != null && !line.equals("]")) {
				int[] result = c.ReadTwits(line, grids);

				for (int k = 0; k < 16; k++) {
					answers[k] += result[k];
				}
				line = br.readLine();
			}

			TestDriver td = new TestDriver();
			td.CalculateAndRank(answers, grids);
		}

		MPI.Finalize();

		long stamp_end = System.currentTimeMillis();
		System.out.println("end at:  " + stamp_end + " duration is: " + (stamp_end - stamp_start));
	}

	public void CalculateAndRank(int[] allResults, ArrayList<Grid> grids) {
		HashMap<String, Integer> box = new HashMap<String, Integer>();

		for (int i = 0; i < allResults.length; i++) {
			box.put(grids.get(i).getId(), allResults[i]);

		}

		HashMap<String, Integer> columns = new HashMap<String, Integer>();
		HashMap<String, Integer> rows = new HashMap<String, Integer>();

		for (String s : box.keySet()) {
			String row = s.charAt(0) + "-Row";
			String column = "Column " + s.charAt(1);
			if (rows.containsKey(row)) {
				rows.put(row, rows.get(row) + box.get(s));
			} else {
				rows.put(row, box.get(s));
			}
			if (columns.containsKey(column)) {
				columns.put(column, columns.get(column) + box.get(s));
			} else {
				columns.put(column, box.get(s));
			}
		}

		box = this.sortByValues(box);
		columns = this.sortByValues(columns);
		rows = this.sortByValues(rows);
		for (String g : box.keySet()) {
			System.out.println("box Ranking:   " + g + "   " + box.get(g));
		}
		for (String g : rows.keySet()) {
			System.out.println("Row Ranking:   " + g + "   " + rows.get(g));
		}
		for (String g : columns.keySet()) {
			System.out.println("columns Ranking:   " + g + "   " + columns.get(g));
		}
	}

	private HashMap sortByValues(HashMap map) {
		List list = new LinkedList(map.entrySet());
		// Defined Custom Comparator here

		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
			}
		});

		// Here I am copying the sorted list in HashMap
		// using LinkedHashMap to preserve the insertion order
		HashMap sortedHashMap = new LinkedHashMap();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry entry = (Map.Entry) it.next();
			sortedHashMap.put(entry.getKey(), entry.getValue());
		}
		return sortedHashMap;
	}

}