package examples.rockets;

import java.util.ArrayList;
import java.util.List;

import core.GeneticAlgorithm;
import core.IPhenotype;
import processing.core.PApplet;
import processing.core.PVector;

public class MySketch extends PApplet {

	List<IPhenotype> population;
	PVector target;
	// float maxforce = (float) 0.2;

	int age;
	int frame;
	// float stat;
	int lifeSpan = 50;
	int generation = 0;

	Button fitnessButton;
	Button newGenButton;

	List<Barrier> barriers;
	Boundary boundary;

	GeneticAlgorithm geneticAlgorithm;
	RocketFitness rocketFitness;
	private List<Double> fitness;

	/*
	 * public void settings() { size(500, 500);
	 * 
	 * }
	 */
	public void settings() {
		System.out.println("setup");
		size(1024, 768);
		// population = new Population();
		// rocket = new Rocket();
		target = new PVector(width / 2, 50);
		age = 0;
		frame = 0;
		barriers = new ArrayList<Barrier>();

		fitnessButton = new Button(20, 80, 220, 20, "calculate fitness");
		newGenButton = new Button(20, 110, 220, 20, "evolve new generation");

		float barrierH = 40.0f;
		float barrierW = (width / 8.0f);

		barriers.add(new Barrier((width - barrierW) * 0.5f, (height - barrierH) * 0.5f, barrierW, barrierH));
		barriers.add(new Barrier((width - barrierW) * 0.7f, (height - barrierH) * 0.2f, barrierW, barrierH));
		barriers.add(new Barrier((width - barrierW) * 0.2f, (height - barrierH) * 0.2f, barrierW, barrierH));

		boundary = new Boundary(0, 0, width, height);

		rocketFitness = new RocketFitness(target, boundary);
		geneticAlgorithm = new GeneticAlgorithm(DNA.class, lifeSpan * 4);

		try {
			System.out.println("geneticAlgorithm");
			population = geneticAlgorithm.init(100);

			for (IPhenotype p : population) {
				((Rocket) p).pos = new PVector(width / 2, height - 20);
			}

			System.out.println("population init: " + population.size());
		} catch (Exception e) {
			e.printStackTrace();
		}

		System.out.println("setup done");
	}

	public void draw() {
		// System.out.println("draw");
		// System.out.println(population.size());

		background(5);

		// Display the buttons
		newGenButton.display(this);
		newGenButton.rollover(mouseX, mouseY);

		fitnessButton.display(this);
		fitnessButton.rollover(mouseX, mouseY);

		// draw the target
		stroke(255);
		fill(128);
		ellipse(target.x, target.y, 20, 20);
		fill(100);
		noStroke();
		strokeWeight(2);
		ellipse(target.x + 2, target.y - 2, 10, 10);

		for (Barrier barrier : barriers) {
			barrier.drawBarrier(this);
		}

		// System.out.println(population.size());

		if (age < lifeSpan) {
			update(population);
			if (frame % 30 == 0) {
				age = age + 1;
			}
			// evolve();

			frame++;
		}
		show(population);
		textSize(18);
		noStroke();
		fill(255, 128, 0);
		text("Generation: " + generation, 20, 20);
		text("Age: " + age + "/" + lifeSpan, 20, 40);
		text("Frame: " + frame, 20, 60);

	}

	// If the button is clicked, evolve next generation
	public void mousePressed() {

		if (age >= lifeSpan) {

			if (fitnessButton.clicked(mouseX, mouseY)) {
				System.out.println("calculateFitness");
				calculateFitness();
				fitnessButton.released();
			}
			if (newGenButton.clicked(mouseX, mouseY)) {
				System.out.println("evolve");
				evolve();
				newGenButton.released();
			}

			if (fitness == null) {
				fitness = new ArrayList<Double>(population.size());
				for (int i = 0; i < population.size(); i++) {
					fitness.add(0.0);
				}
			}

			for (int i = 0; i < population.size(); i++) {
				Rocket rocket = ((Rocket) population.get(i));
				if (rocket.clicked(mouseX, mouseY)) {
					rocket.fitness += 1.0;
					fitness.set(i, rocket.fitness);
					rocket.released();
				}
			}
		}
	}

	private void calculateFitness() {
		// if (age >= lifeSpan) {
		try {
			fitness = geneticAlgorithm.selection(population, rocketFitness);
			for (int i = 0; i < fitness.size(); i++) {
				((Rocket) population.get(i)).fitness = fitness.get(i);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		// }
	}

	private void evolve() {
		// if (age >= lifeSpan) {
		try {
			population = geneticAlgorithm.reproduction(population.size(), population, fitness);
		} catch (Exception e) {
			e.printStackTrace();
		}
		for (IPhenotype p : population) {
			((Rocket) p).pos = new PVector(width / 2, height - 20);
		}
		age = 0;
		generation++;
		// }
	}

	public static void main(String[] passedArgs) {
		String[] appletArgs = new String[] { "examples.rockets.MySketch" };
		PApplet.main(appletArgs);
	}

	void update(List<IPhenotype> population) {
		for (IPhenotype rocket : population) {
			((Rocket) rocket).update(target, age, barriers, boundary);
		}
	}

	void show(List<IPhenotype> population) {
		for (IPhenotype rocket : population) {
			((Rocket) rocket).show(this);
		}
	}
}
