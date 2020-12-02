package examples.TravelingSalesperson;

import java.util.Random;

import core.Genotype;
import core.IPhenotype;

public class DNA extends Genotype {

	@Override
	public IPhenotype expression() {
		Path path = null;
		try {
			path = new Path(this);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return path;
	}

	@Override
	public Object generateGene() {
		return new Random().nextInt(CC_035_4_TSP_GA.totalCities);
	}

	@Override
	public Object mutateGene(Object gene) {
		return this.generateGene();
	}

	@Override
	public String toString() {
		String res = "";
		for (Object gene : getGenes()) {
			res = res + gene + ",";
		}
		return res;
	}
}
