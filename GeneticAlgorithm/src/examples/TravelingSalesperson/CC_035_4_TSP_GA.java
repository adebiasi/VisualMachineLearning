package examples.TravelingSalesperson;

import java.util.ArrayList;
import java.util.List;
import core.GeneticAlgorithm;
import core.IPhenotype;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.IntList;

// Coding Train
// Ported to processing by Max (https://github.com/TheLastDestroyer)
// Origional JS by Daniel Shiffman
// http://patreon.com/codingtrain
// Code for this video: https://www.youtube.com/watch?v=M3KTWnTrU_c

public class CC_035_4_TSP_GA extends PApplet {

	final static int totalCities = 10;

	static PVector[] cities;
	static IntList initIndexesOrder;
	Integer generation = 0;

	List<IPhenotype> gaPopulation;
	GeneticAlgorithm geneticAlgorithm;
	PathFitness pathFitness;

	List<IPhenotype> forceBrutePopulation;

	int popSize = 300;

	private PathRecordInfo gaRecordInfo;
	private PathRecordInfo bfRecordInfo;

	public void settings() {
		size(1600, 800);

		cities = new PVector[totalCities];
		for (int i = 0; i < totalCities; i++) {
			PVector v = new PVector(random(width / 2), random(height / 2));
			cities[i] = v;
		}

		initIndexesOrder = new IntList();
		for (int i = 0; i < totalCities; i++) {
			initIndexesOrder.append(i);
		}

		geneticAlgorithm = new GeneticAlgorithm(DNA.class, totalCities, 0.1);
		gaRecordInfo = new PathRecordInfo("GA Route");
		try {
			gaPopulation = geneticAlgorithm.init(popSize);
		} catch (Exception e) {
			e.printStackTrace();
		}
		pathFitness = new PathFitness();

		bfRecordInfo = new PathRecordInfo("Brute force Route");
	}

	public void draw() {
		System.out.println("DRAW");
		background(0);
		// GA
		try {
			gaPopulation = geneticAlgorithm.step(gaPopulation, pathFitness);

			List<IPhenotype> bfPopulation = new ArrayList<IPhenotype>();
			for (int i = 0; i < popSize; i++) {
				bfPopulation.add(BruteForcePathGen.generatePath());
			}

			System.out.println("update gaRecordInfo");
			updateRecords(gaPopulation, gaRecordInfo);
			System.out.println("update bfRecordInfo");
			updateRecords(bfPopulation, bfRecordInfo);
		} catch (Exception e1) {
			e1.printStackTrace();
		}
		System.out.println("best ever: " + gaRecordInfo.bestEver);

		drawGAPaths(gaRecordInfo);
		translate(width / 2, 0);
		drawGAPaths(bfRecordInfo);
		generation++;
	}

	private void updateRecords(List<IPhenotype> population, PathRecordInfo recordInfo) {
		try {
			double currentRecord = Double.POSITIVE_INFINITY;
			for (int i = 0; i < population.size(); i++) {

				Path currPath = (Path) population.get(i);
				double currDistance = currPath.distance;
				if (currDistance < recordInfo.recordDistance) {
					System.out.println("bestEver");
					recordInfo.recordDistance = currDistance;
					recordInfo.bestEver = currPath;
					recordInfo.recordGeneration = generation;
				}
				if (currDistance < currentRecord) {
					// System.out.println("currentBest");
					currentRecord = currDistance;
					recordInfo.currentBest = currPath;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private void drawGAPaths(PathRecordInfo pathRecordInfo) {
		pushMatrix();
		drawPath(pathRecordInfo.bestEver,
				"Shortest " + pathRecordInfo.title + " ever in generation " + pathRecordInfo.recordGeneration + ": "
						+ pathRecordInfo.recordDistance,
				cities);

		translate(0, height / 2);
		drawPath(pathRecordInfo.currentBest, "Shortest " + pathRecordInfo.title + " of generation: " + generation,
				cities);
		popMatrix();
	}

	private void drawPath(Path path, String text, PVector[] cities) {
		stroke(255);
		strokeWeight(4);
		noFill();
		beginShape();
		for (int i = 0; i < path.citiesIndexes.size(); i++) {
			int n = path.citiesIndexes.get(i);
			vertex(cities[n].x, cities[n].y);
			ellipse(cities[n].x, cities[n].y, 16, 16);
			textSize(32);
			text(n, cities[n].x, cities[n].y);
			textSize(12);
		}
		endShape();
		text(text, 20, 20);
	}

	public static void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "examples.TravelingSalesperson.CC_035_4_TSP_GA" };
		PApplet.main(appletArgs);
	}

}
