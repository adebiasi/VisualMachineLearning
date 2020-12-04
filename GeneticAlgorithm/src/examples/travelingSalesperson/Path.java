package examples.travelingSalesperson;

import java.util.List;

import core.IGenotype;
import core.Phenotype;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.IntList;

public class Path extends Phenotype {

	DNA dna;
	public IntList citiesIndexes;
	public double distance;

	public Path() throws Exception {	
		super(new DNA());
	}

	public Path(IGenotype genotype) {
		super(genotype);
		//System.out.print("new Path: ");
		if(genotype!=null){
		this.dna = (DNA) genotype;
		calculateCitiesIndexesAndDistance(dna.getGenes());
		}
		//calculateDistance(citiesIndexes);
		/*for(Integer currInt : citiesIndexes){
			System.out.print(currInt+", ");
		}*/
		//System.out.println("dna: "+dna.toString());
		//distance = calcDistance(CC_035_4_TSP_GA.cities, citiesIndexes);
		//System.out.println("distance: "+distance);
	}
	
	public void calculateCitiesIndexesAndDistance(List<Object> indPosList){
		citiesIndexes = new IntList();
		IntList initIndexesOrder = CC_035_4_TSP_GA.initIndexesOrder.copy();
		for (int i = 0; i < CC_035_4_TSP_GA.totalCities; i++) {
			int currGene = (int)indPosList.get(i);
			int dnaIndexTaken = (int) currGene % initIndexesOrder.size();
			int citiesIndex = initIndexesOrder.remove(dnaIndexTaken);
			citiesIndexes.append(citiesIndex);
		}
		
		calculateDistance(citiesIndexes);
	}
	
	public void calculateDistance(IntList citiesIndexes) {		
		distance = calcDistance(CC_035_4_TSP_GA.cities, citiesIndexes);
		System.out.println("distance: "+distance);
	}
	
	double calcDistance(PVector[] points, IntList order) {
		double sum = 0;
		  for (int i = 0; i < order.size() - 1; i++) {
		    int cityAIndex = order.get(i);
		    PVector cityA = points[cityAIndex];
		    int cityBIndex = order.get(i + 1);
		    PVector cityB = points[cityBIndex];
		    double d = PApplet.dist(cityA.x, cityA.y, cityB.x, cityB.y);
		    //System.out.print(d+",");
		    sum += d;
		  }
		  //System.out.println(" tot: "+sum);
		  return sum;
		}

}
