package core;

public abstract class Phenotype implements IPhenotype{

	final IGenotype genotype;

	public Phenotype() throws Exception {
		throw new Exception();
	}
	
	public Phenotype(IGenotype genotype) {
		super();		
		this.genotype = genotype;
	}

	@Override
	public IGenotype getGenotype() {
		return genotype;
	}
	
	
	
}
