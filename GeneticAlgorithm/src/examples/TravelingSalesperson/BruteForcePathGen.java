package examples.TravelingSalesperson;

import java.util.ArrayList;
import java.util.List;

public class BruteForcePathGen {

	static int numPathGenerated = 0;

	static public Path generatePath() throws Exception {

		// List<Object> genes = new
		// ArrayList<Object>(CC_035_4_TSP_GA.totalCities);
		List<Object> indPosList = generateIndPosList();
		//DNA dna = new DNA();
		//dna.setGenes(indPosList);
		Path path = new Path(null);
		path.calculateCitiesIndexesAndDistance(indPosList);
		//path.calculateDistance(citiesIndexes);

		return path;
	}

	private static List<Object> generateIndPosList() {
		List<Object> res = new ArrayList<Object>();
		int prevQuotient = numPathGenerated;
		int maxValue = CC_035_4_TSP_GA.totalCities;
		for (int i = 0; i < CC_035_4_TSP_GA.totalCities; i++) {
			int nextQuotient = prevQuotient / maxValue;
			int remainder = prevQuotient % maxValue;
			prevQuotient = nextQuotient;
			res.add(remainder);
			maxValue--;
		}
		System.out.println();
		numPathGenerated++;
		return res;
	}

}
