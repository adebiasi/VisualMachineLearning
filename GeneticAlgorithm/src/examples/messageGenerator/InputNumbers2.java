package examples.messageGenerator;

import java.util.Random;

import core.Genotype;
import core.IPhenotype;

public class InputNumbers2 extends Genotype {

	@Override
	public IPhenotype expression() {
		return new GeneratedMessage(this);
	}

	@Override
	public Object generateGene() {
		// from 97 to 122 inclusive
		Random random = new Random();
		int range = 26;// from 0 to 25 inclusive
		int randInt = random.nextInt(range);
		return (int) (97 + randInt);
	}

	@Override
	public Object mutateGene(Object gene) {
		return generateGene();
	}


}
