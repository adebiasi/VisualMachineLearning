package examples.messageGenerator;

import java.util.List;

import core.GeneticAlgorithm;
import core.IPhenotype;

public class Main {

	static String target = // "asdkvofoifjwofjpfdsfbgfsb";
			"aaaaaaaaaa";
	
	public static void main(String[] args) throws Exception {

		MessageFitness dnaFitness = new MessageFitness(target);
		GeneticAlgorithm geneticAlgorithm = new GeneticAlgorithm(InputNumbers2.class, target.length());

		List<IPhenotype> population = geneticAlgorithm.init(100);
		
		for(IPhenotype p : population){
			System.out.println("getMessage: "+((GeneratedMessage)p).getMessage());
		}

		boolean found = false;
		System.out.println("population size: " + population.size());
		for (int i = 0; i < 200; i++) {
			System.out.println("-----step: " + i + "-----------");

			population = geneticAlgorithm.step(population, dnaFitness);
			for (IPhenotype dnaStr : population) {

				if (((GeneratedMessage) dnaStr).getMessage().equals(target)) {
					System.out.println("EXIT");

					found = true;
				}
			}

			if (found)
				break;
		}

	}

}
