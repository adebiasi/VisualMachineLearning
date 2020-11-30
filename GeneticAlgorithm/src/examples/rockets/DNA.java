package examples.rockets;
// Daniel Shiffman

import java.util.Random;

// http://codingtra.in
// http://patreon.com/codingtrain
// Code for: https://youtu.be/bGz7mv2vD6g
// Processing transcription: Chuck England

import core.Genotype;
import core.IPhenotype;

public class DNA extends Genotype {

	@Override
	public IPhenotype expression() {
		Rocket rocket = new Rocket(this);
		return rocket;
	}

	@Override
	public Object generateGene() {
		return new Random().nextFloat();
	}

	@Override
	public Object mutateGene(Object gene) {
		return this.generateGene();
	}

}
