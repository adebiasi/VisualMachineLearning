import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import processing.core.PApplet;

public class Sketch extends PApplet {
	// The Nature of Code
	// Daniel Shiffman
	// http://natureofcode.com

	// Simple Perceptron Example
	// See: http://en.wikipedia.org/wiki/Perceptron

	// Code based on text "Artificial Intelligence", George Luger

	// A list of points we will use to "train" the perceptron
	List<Trainer> training = new ArrayList<Trainer>();
	// A Perceptron object
	Perceptron ptron;

	// We will train the perceptron with one "Point" object at a time
	// int count = 0;

	// Coordinate space
	float xmin = -400;
	float ymin = -100;
	float xmax = 400;
	float ymax = 100;

	int answer = -1;
	
	boolean newTrainer = false;

	String message = "";

	// The function to describe a line
	/*float f(float x) {
		return 0.4f * x + 50;
	}*/

	public void settings() {
		size(1200, 800);
	}

	public void setup() {

		createPerceptron();

		// Create a random set of training points and calculate the "known"
		// answer
		/*
		 * for (int i = 0; i < 2000; i++) { float x = random(xmin, xmax); float
		 * y = random(ymin, ymax); int answer = 1; if (y < f(x)) answer = -1;
		 * training.add(new Trainer(x, y, answer)); }
		 */
		smooth();
	}

	public void draw() {
		background(255);
		// translate(width / 2, height / 2);

		// Draw the line
		//drawKnownLine();

		// Draw the line based on the current weights
		// Formula is weights[0]*x + weights[1]*y + weights[2] = 0
		drawGuessedLine();

		drawNextTrainer();
		
		if (!training.isEmpty() & newTrainer) {

			Trainer lastTrainer = training.get(training.size() - 1);
			// Train the Perceptron with one "training" point at a time
			float[] latestInputs = lastTrainer.inputs;
			// Guess the result
			int guessAnswer = ptron.feedforward(latestInputs);
			// Compute the factor for changing the weight based on the error
			// Error = desired output - guessed output
			// Note this can only be 0, -2, or 2
			// Multiply by learning constant
			int desiredAnswer = lastTrainer.answer;
			float error = desiredAnswer - guessAnswer;

			if (error != 0 || message.equals("")) {
				message = message + "With the weights [" + floatToString(ptron.weights[0]) + ","
						+ floatToString(ptron.weights[1]) + "," + floatToString(ptron.weights[2])
						+ "], for the latest inputs, the guess answer is " + guessAnswer + " and the true answer is "
						+ desiredAnswer + ", so the error is " + error;

				if (error > 0) {
					message = message + ". The weights must be decreased: ";
				} else if (error < 0) {
					message = message + ". The weights must be increased: ";
				}
			}
			// ptron.train(latestInputs,desiredAnswer);
			ptron.updateWeights(latestInputs, error);

			if (error != 0) {
				message = message + "[" + floatToString(ptron.weights[0]) + "," + floatToString(ptron.weights[1]) + ","
						+ floatToString(ptron.weights[2]) + "]\n";
			}

			newTrainer = false;
		}

		// Draw all the points based on what the Perceptron would "guess"
		// Does not use the "known" correct answer
		drawPoints();

		pushMatrix();
		// translate(-width / 2, -height / 2);
		text("Select the trainer answer (1/-1) using the keys \"1\" and \"2\". ", 10, 10);
		text(message, 10, 30);
		popMatrix();
	}

	private void drawNextTrainer() {
		if(answer>0){
			noFill();
		}else{
			fill(0);
		}
		ellipse(mouseX, mouseY, 16, 16);
	}

	private String floatToString(float num) {
		DecimalFormat df = new DecimalFormat();
		df.setMaximumFractionDigits(4);
		return df.format(num);
	}

	private void drawPoints() {
		pushMatrix();
		translate(width / 2, height / 2);
		for (int i = 0; i < training.size(); i++) {
			stroke(0);

			strokeWeight(1);
			fill(0);
			int guess = ptron.feedforward(training.get(i).inputs);
			//if (guess > 0)
			if(training.get(i).answer>0)
				noFill();
			int size;
			if (i == training.size() - 1) {
				size = 16;
			} else {
				size = 9;
			}
			ellipse(training.get(i).inputs[0], training.get(i).inputs[1], size, size);
		}
		popMatrix();
	}

	private void drawGuessedLine() {
		pushMatrix();
		translate(width / 2, height / 2);
		stroke(0);
		strokeWeight(1);
		float[] weights = ptron.getWeights();
		float x1 = xmin;
		float y1 = (-weights[2] - weights[0] * x1) / weights[1];
		float x2 = xmax;
		float y2 = (-weights[2] - weights[0] * x2) / weights[1];
		line(x1, y1, x2, y2);
		popMatrix();
	}

	/*private void drawKnownLine() {
		pushMatrix();
		translate(width / 2, height / 2);
		strokeWeight(4);
		stroke(127);
		float x1 = xmin;
		float y1 = f(x1);
		float x2 = xmax;
		float y2 = f(x2);
		line(x1, y1, x2, y2);
		popMatrix();
	}*/

	public void mousePressed() {
		System.out.println("clicked");
		// count = (count + 1);
		message = "";
		float x = mouseX - width / 2;
		float y = mouseY - height / 2;

		/*int answer = 1;
		if (y < f(x))
			answer = -1;*/
		training.add(new Trainer(x, y, answer));

		newTrainer = true;
		
	}

	public void keyPressed() {
		System.out.println("Key: "+key);
		  if (key == '1') {
			  answer = 1;
		  } else if (key == '2') {			 
			  answer = -1;
		  } else if (key == 'r') {			 
			  training = new ArrayList<Trainer>();
			  createPerceptron(); 
		  }
		  System.out.println("answer: "+answer);
	}

	private void createPerceptron() {
		ptron = new Perceptron(this, 3, 0.5f);
	}
	
	public static void main(String[] args) {
		String[] appletArgs = new String[] { "Sketch" };
		PApplet.main(appletArgs);
	}

}
