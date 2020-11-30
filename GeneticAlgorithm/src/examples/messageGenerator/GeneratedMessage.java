package examples.messageGenerator;

import core.Phenotype;

public class GeneratedMessage extends Phenotype {

	private final String message;
	
	public GeneratedMessage(InputNumbers2 inputNumbers) {
		super(inputNumbers);

		int[] values = new int[inputNumbers.getGenes().size()];
		int i = 0;
		for(Object o : inputNumbers.getGenes()){
			
			values[i]=(int)o;
			i++;
			
		}
		message = SecretMessageGenerator.produceMessage(values);		
		
	}

	public String getMessage(){
		return message;
	}

	public static class SecretMessageGenerator {

		public static String produceMessage(int[] numbers){
			char[] chars = new char[numbers.length];
			for(int i = 0; i<numbers.length;i++){
				chars[i]=(char)numbers[i];
			}
			return String.valueOf(chars);
		}
		
	}

	
}


