// Code by Loïc BERTRAND
// Inpired by video "Chaotic Balls (and other animations) - Numberphile"
// https://www.youtube.com/watch?v=6z4qRhpBIyA

class Ball {
  constructor(x, y, r, c) {
    this.pos = createVector(x, y); // position
    this.vel = createVector(0, 0); // velocity
    this.acc = createVector(0, 0); // acceleration
    this.r = r; // radius
    this.c = c || color(255); // color

    this.prevPos = this.pos.copy(); // previous position
  }

  applyForce(force) {
    this.acc.add(force);
  }

  update() {
    this.prevPos = this.pos.copy();

    this.vel.add(this.acc);
    this.pos.add(this.vel);
    this.acc.mult(0);
    this.vel.mult(0.99999); // air resistance
  }

  // true if this ball is inside circle c
  isInside(c) {
    const d = dist(c.pos.x, c.pos.y, this.pos.x, this.pos.y);
    return d < c.r - this.r;
  }

  // Bounces inside circle c
  bounce(c) {
    const circleCenter = c.pos;

    // Keep the ball inside the circle c
    this.pos
      .sub(circleCenter)
      .setMag(c.r - this.r)
      .add(circleCenter);

    // Calculate new velocity of the ball (bounce)
    const normal = p5.Vector.sub(this.pos, circleCenter);
    const angleOfIncidence = this.vel.heading() - normal.heading();
    const angleOfReflection = this.vel.heading() - 2 * angleOfIncidence;
    this.vel.setHeading(angleOfReflection);
    this.vel.mult(-1);
  }

  show(g = window) {
    g.noStroke();
    g.fill(this.c);
    g.circle(this.pos.x, this.pos.y, this.r * 2);
  }

  trail(g = window) {
    g.strokeWeight(1);
    g.stroke(this.c);
    g.line(this.prevPos.x, this.prevPos.y, this.pos.x, this.pos.y);
  }
}
