package examples.rockets;

import java.text.DecimalFormat;
import java.util.List;

import core.Phenotype;
import processing.core.PApplet;
import processing.core.PVector;

// Daniel Shiffman
// http://codingtra.in
// http://patreon.com/codingtrain
// Code for: https://youtu.be/bGz7mv2vD6g
// Processing transcription: Chuck England

class Rocket extends Phenotype {

	final float maxvelocity = 2;
	float maxForce = (float) 0.02;

	boolean clickedOn; // Did i click on it?

	Double fitness = 0.0;
	PVector pos;
	PVector vel;
	PVector acc;
	DNA dna;

	float leftForce;
	float rightForce;
	float topForce;
	float bottomForce;

	int diameter = 30;

	// float fitness = 0;
	boolean hitTarget = false;
	boolean crashed = false;

	Rocket() {
		this(new DNA());
	}

	Rocket(DNA dna_) {
		super(dna_);
		// pos = new PVector(mySketch.width / 2, mySketch.height - 20);
		vel = new PVector();
		acc = new PVector(0.0f, -0.1f);
		dna = dna_;

		fitness = 0.0;
		hitTarget = false;
		crashed = false;

	}

	void applyForce(PVector force) {
		acc.add(force);
	}

	float distanceToTarget(PVector target) {
		return PApplet.dist(pos.x, pos.y, target.x, target.y);
	}

	void update(PVector target, int age, List<Barrier> barriers, Boundary boundary) {
		float d = distanceToTarget(target);
		if (d < 10) {
			hitTarget = true;
			pos = target.copy();
		}

		crashed = checkBarriersCrash(barriers) || checkBoundaryCrash(boundary);

		int geneIndex = age * 4;

		leftForce = (float) dna.getGenes().get(geneIndex);
		rightForce = (float) dna.getGenes().get(geneIndex + 1);
		topForce = (float) dna.getGenes().get(geneIndex + 2);
		bottomForce = (float) dna.getGenes().get(geneIndex + 3);

		PVector currForce = new PVector(leftForce - rightForce, topForce - bottomForce);
		currForce.setMag(maxForce);
		applyForce(currForce);

		if (!hitTarget && !crashed) {
			vel.add(acc);
			pos.add(vel);
			acc.mult(0);
			vel.limit(maxvelocity);
		}
	}

	boolean checkBoundaryCrash(Boundary boundary) {
		if (pos.x + diameter / 2 > boundary.rightBoundary || pos.x - diameter / 2 < boundary.leftBoundary
				|| pos.y + diameter / 2 > boundary.topBoundary || pos.y - diameter / 2 < boundary.downBoundary) {
			return true;
		}
		return false;
	}

	boolean checkBarriersCrash(List<Barrier> barriers) {
		for (Barrier barrier : barriers) {
			if (pos.x + diameter / 2 > barrier.barrierx && pos.x - diameter / 2 < (barrier.barrierx + barrier.barrierw)
					&& pos.y + diameter / 2 > barrier.barriery
					&& pos.y - diameter / 2 < (barrier.barriery + barrier.barrierh)) {
				return true;
			}
		}
		return false;
	}

	void show(MySketch mySketch) {
		mySketch.pushMatrix();
		mySketch.noStroke();
		if (hitTarget) {
			mySketch.fill(50, 205, 50);
		} else if (this.crashed) {
			mySketch.fill(128, 128, 128);
		} else {
			mySketch.fill(255, 150);
		}

		mySketch.translate(pos.x, pos.y);
		// mySketch.rotate(vel.heading());

		// draw rocket body
		mySketch.rectMode(MySketch.CENTER);
		// mySketch.rect(0, 0, 25, 5);

		mySketch.ellipse(0, 0, diameter, diameter);

		// draw nose cone
		// mySketch.fill(165, 42, 42);
		// mySketch.ellipse(12, 0, 10, 5);

		if (!hitTarget && !crashed) {
			// draw thrust flame

			if (leftForce > 0) {
				/*
				 * float abs = PApplet.abs(vel.x); System.out.println("------");
				 * System.out.println("abs: "+abs);
				 * 
				 * float map = PApplet.map(abs,0.0f,5.0f,0.0f,1.0f);
				 * System.out.println("map: "+map);
				 */

				drawFlame(mySketch, 0, leftForce);// PApplet.map(leftForce,0,5,0,1));
			}
			if (rightForce > 0) {
				drawFlame(mySketch, PApplet.PI, rightForce);// PApplet.map(rightForce,0,5,0,1));
			}
			if (topForce > 0) {
				drawFlame(mySketch, PApplet.PI / 2, topForce);// PApplet.map(topForce,0,5,0,1));
			}
			if (bottomForce > 0) {
				drawFlame(mySketch, -PApplet.PI / 2, bottomForce);// PApplet.map(bottomForce,0,1,0,1));
			}

		}

		if (fitness != 0) {
			mySketch.fill(255, 255, 255);
			mySketch.textSize(10);
			mySketch.text(new DecimalFormat("#.0").format(fitness), 0, 0);
		}
		mySketch.popMatrix();
	}

	private void drawFlame(MySketch mySketch, float rotation, float power) {
		// System.out.println("power: " +power);
		mySketch.pushMatrix();
		mySketch.rotate(rotation);
		mySketch.fill(255, 140 + mySketch.random(0, 115), mySketch.random(0, 128));
		mySketch.beginShape();
		mySketch.vertex(-15, -3);
		mySketch.vertex(-15 - 10.0f * power, 0);
		mySketch.vertex(-15, 3);
		mySketch.endShape();
		mySketch.popMatrix();
	}

	boolean clicked(int mx, int my) {
		PVector mousePos = new PVector(mx, my);
		if (pos.dist(mousePos) < (diameter / 2))
			clickedOn = true;
		return clickedOn;
	}

	void released() {
		clickedOn = false;
	}
}
