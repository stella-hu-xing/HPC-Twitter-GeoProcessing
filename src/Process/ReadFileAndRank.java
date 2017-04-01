//package Process;
//
//import java.io.FileNotFoundException;
//import java.io.FileReader;
//import java.io.IOException;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.LinkedHashMap;
//import java.util.LinkedList;
//import java.util.List;
//import java.util.Map;
//
//import org.json.simple.JSONArray;
//import org.json.simple.JSONObject;
//import org.json.simple.parser.JSONParser;
//import org.json.simple.parser.ParseException;
//
//public class ReadFileAndRank {
//
//	/**
//	 * 
//	 * @param filename
//	 * @return
//	 */
//	public HashMap<Grid, Integer> readGrid(String filename) {
//		HashMap<Grid, Integer> grids = new HashMap<Grid, Integer>();
//
//		JSONParser parser = new JSONParser();
//
//		try {
//			Object obj = parser.parse(new FileReader(filename));
//
//			JSONObject jsonObject = (JSONObject) obj;
//
//			JSONArray features = (JSONArray) jsonObject.get("features");
//
//			int counter = 0;
//
//			while (counter < features.size()) {
//				JSONObject row = (JSONObject) features.get(counter);
//
//				JSONObject properties = (JSONObject) row.get("properties");
//
//				counter++;
//
//				Grid g = new Grid();
//
//				g.setId(properties.get("id").toString());
//				g.setXmax(Double.parseDouble(properties.get("xmax").toString()));
//				g.setXmin(Double.parseDouble(properties.get("xmin").toString()));
//				g.setYmax(Double.parseDouble(properties.get("ymax").toString()));
//				g.setYmin(Double.parseDouble(properties.get("ymin").toString()));
//
//				grids.put(g, 0);
//			}
//
//		} catch (FileNotFoundException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		} catch (ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//		return grids;
//	}
//
//	/**
//	 * 
//	 * @param grids
//	 * @param twitsFile
//	 * @return
//	 */
//	public void ReadTwits(HashMap<Grid, Integer> grids, String twitsFile) {
//
//		JSONParser parser = new JSONParser();
//
//		Object obj;
//		try {
//			obj = parser.parse(new FileReader(twitsFile));
//
//			JSONArray contents = (JSONArray) obj;
//
//			int counter = 0;
//
//			while (counter < contents.size()) {
//				JSONObject row = (JSONObject) contents.get(counter);
//				JSONObject json = (JSONObject) row.get("json");
//				JSONObject corridnates = (JSONObject) json.get("coordinates");
//
//				JSONArray corridnate = (JSONArray) corridnates.get("coordinates");
//
//				// handle this coordinate and put it in corresponded box and add
//				// into ranking
//
//				double first = Double.parseDouble(corridnate.get(0).toString());
//				double second = Double.parseDouble(corridnate.get(1).toString());
//				if (first > 0 && second < 0) {
//					for (Grid g : grids.keySet()) {
//						// whether it is in the box
//						if (first <= g.getXmax() && first >= g.getXmin() && second <= g.getYmax()
//								&& second >= g.getYmin()) {
//							grids.put(g, (grids.get(g)) + 1);
//
//						}
//					}
//				} else {
//					// System.out.println("first is 0 or smaller than second");
//				}
//				counter++;
//			}
//
//		} catch (IOException | ParseException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
//
//	}
//
//	public HashMap<Grid, Integer> getGridRank(HashMap<Grid, Integer> grids) {
//
//		grids = sortByValues(grids);
//		for (Grid g : grids.keySet()) {
//			System.out.println("Grid Ranking:   " + g.id + "   " + grids.get(g));
//		}
//		return grids;
//	}
//
//	public HashMap<String, Integer> getRowRank(HashMap<Grid, Integer> grids) {
//		HashMap<String, Integer> rowRank = new HashMap<String, Integer>();
//
//		for (Grid g : grids.keySet()) {
//			String key = g.getId().charAt(0) + "-Row";
//			if (!rowRank.keySet().contains(key)) {
//
//				rowRank.put(key, grids.get(g));
//
//			} else {
//				rowRank.put(key, rowRank.get(key) + grids.get(g));
//
//			}
//		}
//
//		rowRank = sortByValues(rowRank);
//		for (String g : rowRank.keySet()) {
//			System.out.println("Row Ranking:   " + g + "   " + rowRank.get(g));
//		}
//		return rowRank;
//	}
//
//	public HashMap<String, Integer> getColumnRank(HashMap<Grid, Integer> grids) {
//		HashMap<String, Integer> columnRank = new HashMap<String, Integer>();
//
//		for (Grid g : grids.keySet()) {
//			String key = "Column " + g.getId().charAt(1);
//			if (!columnRank.keySet().contains(key)) {
//				columnRank.put(key, grids.get(g));
//			} else {
//				columnRank.put(key, columnRank.get(key) + grids.get(g));
//
//			}
//		}
//
//		columnRank = sortByValues(columnRank);
//		for (String g : columnRank.keySet()) {
//			System.out.println("Column Ranking:   " + g + "   " + columnRank.get(g));
//		}
//
//		return columnRank;
//	}
//
//	private static HashMap sortByValues(HashMap map) {
//		List list = new LinkedList(map.entrySet());
//		// Defined Custom Comparator here
//
//		Collections.sort(list, new Comparator() {
//			public int compare(Object o1, Object o2) {
//				return ((Comparable) ((Map.Entry) (o2)).getValue()).compareTo(((Map.Entry) (o1)).getValue());
//			}
//		});
//
//		// Here I am copying the sorted list in HashMap
//		// using LinkedHashMap to preserve the insertion order
//		HashMap sortedHashMap = new LinkedHashMap();
//		for (Iterator it = list.iterator(); it.hasNext();) {
//			Map.Entry entry = (Map.Entry) it.next();
//			sortedHashMap.put(entry.getKey(), entry.getValue());
//		}
//		return sortedHashMap;
//	}
//
//}
