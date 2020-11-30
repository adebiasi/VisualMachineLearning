package examples.rockets;

import processing.core.PApplet;

public class Barrier {

	float barrierx, barriery, barrierw, barrierh;

	public Barrier(float barrierx, float barriery, float barrierw, float barrierh) {
		super();
		this.barrierx = barrierx;
		this.barriery = barriery;
		this.barrierw = barrierw;
		this.barrierh = barrierh;
	}
	
	public void drawBarrier(PApplet pApplet) {
		// draw the barrier
		pApplet.fill(255, 0, 0);
		pApplet.stroke(128);
		pApplet.rectMode(PApplet.CORNER);
		pApplet.rect(barrierx, barriery, barrierw, barrierh);
		pApplet.strokeWeight(1);
		pApplet.stroke(55, 0, 0);
		//line(barrierx, barriery - 10, target.x - 10, target.y + 10);
		//line(barrierx + barrierw, barriery - 10, target.x + 10, target.y + 10);
	}
	
}
