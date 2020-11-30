package core;

import java.util.ArrayList;
import java.util.List;

import core.IGenotype.CrossoverApproaches;

public class GeneticAlgorithm implements IGeneticAlgorithm {

	//private IFitnessEvaluator fitnessFunction;
	Class<? extends IGenotype> genotypeClass;
	private CrossoverApproaches crossoverApproach;
	private double mutationRate;
	int numGenes;

	public GeneticAlgorithm(Class<? extends IGenotype> genotypeClass, int numGenes){//, IFitnessEvaluator fitnessFunction) {
		this(genotypeClass, numGenes, CrossoverApproaches.RANDOM_MIDPOINT, 0.01);
	}

	public GeneticAlgorithm(Class<? extends IGenotype> genotypeClass, int numGenes, //IFitnessEvaluator fitnessFunction,
			CrossoverApproaches crossoverApproach, double mutationRate) {
		this.genotypeClass = genotypeClass;
		//this.fitnessFunction = fitnessFunction;
		this.crossoverApproach = crossoverApproach;
		this.numGenes = numGenes;
		this.mutationRate = mutationRate;
	}

	public List<IPhenotype> init(int numPopulation) throws Exception {
		List<IPhenotype> population = new ArrayList<IPhenotype>();
		for (int i = 0; i < numPopulation; i++) {
			IGenotype genotype = genotypeClass.newInstance();
			genotype.generateGenes(numGenes);
			// genotype.set
			population.add(genotype.expression());
		}
		return population;
	}

	/*
	 * private List<IPhenotype> phenotypeSelection(List<IPhenotype> population)
	 * { System.out.println("selection"); List<Double> fitnessList =
	 * evaluateFitness(population); List<IPhenotype> candidatesGenotypes =
	 * buildCandidatesPhenotypes(population,fitnessList); return
	 * candidatesGenotypes; }
	 */

	/*private List<IPhenotype> buildCandidatesPhenotypes(List<IPhenotype> population, List<Double> fitnessList) {
		List<IPhenotype> matingPool = new ArrayList<IPhenotype>();

		for (int i = 0; i < population.size(); i++) {
			IPhenotype currPhenotype = population.get(i);
			double currFitness = fitnessList.get(i);
			// Add each member n times according to its fitness score.
			int n = (int) (currFitness * 100);
			for (int j = 0; j < n; j++) {
				matingPool.add(currPhenotype);
			}
		}

		return matingPool;
	}
*/
	@Override
	public List<Double> selection(List<IPhenotype> population, IFitnessEvaluator fitnessFunction) {

		double maxFitness = 0;
		List<Double> fitnessList = new ArrayList<Double>();
		for (IPhenotype currPhenotype : population) {
			double fitness = fitnessFunction.evaluateFitness(currPhenotype);
			fitnessList.add(fitness);

			if (fitness > maxFitness) {
				maxFitness = fitness;
			}

		}

		
		System.out.println("maxFitness: " + maxFitness);
		return fitnessList;
	}

	@Override
	public List<IPhenotype> reproduction(int newPopulationSize, List<IPhenotype> population,
			List<Double> fitnessList) throws Exception {
		System.out.println("reproduction");
		fitnessList = evaluateProbability(fitnessList);
		List<IPhenotype> newPopulation = new ArrayList<IPhenotype>();

		for (int i = 0; i < newPopulationSize; i++) {

			IPhenotype child = sexualCombination(population, fitnessList);
			newPopulation.add(child);

		}
		return newPopulation;
	}

	private IPhenotype sexualCombination(List<IPhenotype> population, List<Double> fitnessList)
			throws Exception {
		int indexCandidateA = getIndexCandidate(population, null, fitnessList);
		int indexCandidateB = getIndexCandidate(population, indexCandidateA, fitnessList);

		if(indexCandidateA==indexCandidateB){
			throw new Exception("Index candidates are the same: "+indexCandidateA);
		}
		
		IGenotype candidateA = population.get(indexCandidateA).getGenotype();
		IGenotype candidateB = population.get(indexCandidateB).getGenotype();

		IGenotype childGenotype = candidateA.crossover(candidateB, crossoverApproach);
		childGenotype.mutate(mutationRate);
		IPhenotype child = childGenotype.expression();
		return child;
	}

	private int getIndexCandidate(List<IPhenotype> candidatesPhenotypes, Integer alreadyChosenIndex,
			List<Double> fitnessList) {
		
		double randValue = Math.random();
		if(alreadyChosenIndex!=null){
			randValue*=(1- fitnessList.get(alreadyChosenIndex));
		}
		int indexCandidate = 0;
		
		while (randValue > 0) {
			if ((alreadyChosenIndex==null)||(indexCandidate != alreadyChosenIndex)) {
				randValue -= fitnessList.get(indexCandidate);
			}
			
			indexCandidate++;
		}

		indexCandidate--;

		return indexCandidate;

	}

	@Override
	public List<IPhenotype> step(List<IPhenotype> population,IFitnessEvaluator fitnessFunction) throws Exception {
		List<Double> fitnessList = selection(population, fitnessFunction);
		
		return reproduction(population.size(), population, fitnessList);

	}

	private List<Double> evaluateProbability(List<Double> fitnessList) {
		
		for(Double value : fitnessList){
			System.out.println("value: "+value);
		}
		
		List<Double> normFitness = new ArrayList<Double>(fitnessList.size());
	
		double sumValues = 0;

		for (Double currFitnessValue : fitnessList) {
			sumValues += currFitnessValue;
		}

		for (int i = 0; i < fitnessList.size(); i++) {
			normFitness.add(fitnessList.get(i) / sumValues);
		}
		
		for(Double value : normFitness){
			System.out.println("value dopo: "+value);
		}
		
		return normFitness;
	}

}
