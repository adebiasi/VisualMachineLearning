package examples.travelingSalesperson;

import core.IFitnessEvaluator;
import core.IPhenotype;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.IntList;

public class PathFitness implements IFitnessEvaluator{

	@Override
	public double evaluateFitness(IPhenotype genotype) {
		//System.out.println("evaluateFitness");
		double distance = ((Path)genotype).distance;
		double fitness = 1 / (PApplet.pow((float)distance,5) + 1);
		//((Path)genotype).fitness = fitness;
		return fitness;
	}

	
	
}
