package examples.rockets;

import java.awt.Rectangle;

import processing.core.PConstants;

//The Nature of Code
//Daniel Shiffman
//http://natureofcode.com

//Interactive Selection
//http://www.genarts.com/karl/papers/siggraph91.html

//import java.awt.Rectangle;

class Button {
	
	Rectangle r; // Button's rectangle
	String txt; // Button's text
	boolean enabled; 
	boolean clickedOn; 
	boolean rolloverOn; 

	Button(int x, int y, int w, int h, String s) {
		r = new Rectangle(x, y, w, h);
		txt = s;
		enabled = false;
	}

	void display(MySketch mySketch) {
		if(enabled){
		// Draw rectangle and text based on whether rollover or clicked
		mySketch.rectMode(PConstants.CORNER);
		mySketch.stroke(255);
		mySketch.noFill();
		if (rolloverOn)
			mySketch.fill(0.5f);
		if (clickedOn)
			mySketch.fill(0);
		mySketch.rect(r.x, r.y, r.width, r.height);
		float b = 0.0f;
		if (clickedOn)
			b = 1f;
		else if (rolloverOn)
			b = 0.2f;
		else
			b = 1.0f;
		mySketch.fill(255, 150);
		mySketch.textAlign(PConstants.LEFT);
		mySketch.text(txt, r.x + 10, r.y + 14);
		}
	}

	
	
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	// Methods to check rollover, clicked, or released (must be called from
	// appropriate
	// Places in draw, mousePressed, mouseReleased
	boolean rollover(int mx, int my) {
		if (r.contains(mx, my))
			rolloverOn = true;
		else
			rolloverOn = false;
		return rolloverOn;
	}

	boolean clicked(int mx, int my) {
		if (r.contains(mx, my))
			clickedOn = true;
		return clickedOn;
	}

	void released() {
		clickedOn = false;
	}

	public boolean isEnabled() {
		return enabled;
	}

}