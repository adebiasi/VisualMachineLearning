package core;

import java.util.List;

public interface IGeneticAlgorithm {
	
	public List<IPhenotype> step(List<IPhenotype> population, IFitnessEvaluator fitnessFunction) throws Exception;

	public List<Double> selection(List<IPhenotype> population, IFitnessEvaluator fitnessFunction);

	public List<IPhenotype> reproduction(int newPopulationSize, List<IPhenotype> population, List<Double> fitnessList)
			throws Exception;
	
}
