package core;

import java.util.ArrayList;
import java.util.List;

public abstract class Genotype implements IGenotype {

	private List<Object> genes;

	public Genotype() {
		super();
		genes = new ArrayList<Object>();
	}

	public void setGenes(List<Object> genes) {
		this.genes = genes;
	}

	@Override
	public IGenotype crossover(IGenotype partner, CrossoverApproaches crossoverApproach) {
		IGenotype child = null;
		try {
			switch (crossoverApproach) {
			case COIN_FLIPPING:
				child = coinFlippingApproach(partner);
			case RANDOM_MIDPOINT:
				child = randomMidPointPicking(partner);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return child;
	}

	private IGenotype randomMidPointPicking(IGenotype partner) throws Exception {

		Genotype child = this.getClass().getConstructor().newInstance();
				
		double midPoint = Math.random() * genes.size();

		for (int i = 0; i < genes.size(); i++) {
			if (i > midPoint)
				child.genes.add(genes.get(i));
			else
				child.genes.add(genes.get(i));
		}

		return child;
	}

	private IGenotype coinFlippingApproach(IGenotype partner) throws Exception {

		Genotype child = this.getClass().getConstructor().newInstance();

		for (int i = 0; i < genes.size(); i++) {
			if (Math.random() > 0.5)
				child.genes.add(genes.get(i));
			else
				child.genes.add(genes.get(i));
		}

		return child;
	}

	@Override
	public void mutate(double mutationRate) {

		//double mutationRate = 0.01;
		for (int i = 0; i < genes.size(); i++) {

			if (Math.random() < mutationRate) {
				genes.set(i, this.mutateGene(genes.get(i)));
			}

		}

	}

	public List<Object> getGenes() {
		return genes;
	}

	public abstract Object generateGene();
	
	public abstract Object mutateGene(Object gene);

	public void generateGenes(int length) {
		genes = new ArrayList<Object>();
		for (int i = 0; i < length; i++) {
			genes.add(this.generateGene());
		}
	}

}
