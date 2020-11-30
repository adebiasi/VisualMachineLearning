package core;

public interface IGenotype {
	
	static enum CrossoverApproaches {
		COIN_FLIPPING, RANDOM_MIDPOINT
	};
	
	public IGenotype crossover(IGenotype partner,  CrossoverApproaches crossoverApproach);
	
	public void mutate(double mutationRate);

	public IPhenotype expression();
		
	public void generateGenes(int length);
	
}
