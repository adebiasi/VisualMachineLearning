package examples.messageGenerator;

import core.IFitnessEvaluator;
import core.IPhenotype;

public class MessageFitness implements IFitnessEvaluator {

	String targetMessage;

	public MessageFitness(String targetMessage) {
		this.targetMessage = targetMessage;
	}

	@Override
	public double evaluateFitness(IPhenotype phenotype) {
		int score = 0;

		String message = ((GeneratedMessage) phenotype).getMessage();
		
		System.out.println("in fitness message: "+message);
		
		for (int i = 0; i < message.length(); i++) {
			if (message.charAt(i) == targetMessage.charAt(i)) {
				score++;
			}
		}

		double fitness = (double)score / (double)targetMessage.length();
		//System.out.println("dnaValue: "+dnaValue+" ("+fitness+")");
		return fitness;
	}

}
