package examples.rockets;

import core.IFitnessEvaluator;
import core.IPhenotype;
import processing.core.PApplet;
import processing.core.PVector;

class RocketFitness implements IFitnessEvaluator{
  
	PVector target;
	Boundary boundary;
	
  public RocketFitness(PVector target, Boundary boundary) {
		super();
		this.target=target;
		this.boundary=boundary;
		// TODO Auto-generated constructor stub
	}

@Override
  public double evaluateFitness(IPhenotype phenotype) {
  Rocket rocket = (Rocket) phenotype;
  
  float d = rocket.distanceToTarget(target);
    double fitness = PApplet.map(d, boundary.leftBoundary, boundary.rightBoundary, boundary.topBoundary, boundary.downBoundary);
    if (rocket.hitTarget) {
      fitness *= 10;
    } else if (rocket.crashed) {
      fitness /= 10;
    }
    return fitness;  
  }


}
