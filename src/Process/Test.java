package Process;

import java.util.HashMap;

public class Test {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

		HashMap<String, Integer> row = new HashMap<String, Integer>();
		row.put("aa", 1);
		row.put("aa", 2);
		row.put("bb", 2);
		row.put("aa", 3);
		
		System.out.println(row);
	}

}
