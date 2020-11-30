package core;

import java.util.ArrayList;
import java.util.List;

public class Test {

	public static void main(String[] args) {
		
		double randValue = Math.random();
		Integer alreadyChosenIndex = 2;
		
		List<Double> fitnessList = new ArrayList<Double>();
		fitnessList.add(0.3);
		fitnessList.add(0.2);
		fitnessList.add(0.1);
		fitnessList.add(0.4);
		if(alreadyChosenIndex!=null){
			randValue*=fitnessList.get(alreadyChosenIndex);
		}
		int indexCandidate = 0;
		
		while (randValue > 0) {
			if ((alreadyChosenIndex==null)||(indexCandidate != alreadyChosenIndex)) {
				randValue -= fitnessList.get(indexCandidate);
			}
			
			indexCandidate++;
		}

		indexCandidate--;
		
	}
	
}
