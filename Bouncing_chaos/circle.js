class Circle {
  constructor(x, y, r) {
    this.pos = createVector(x, y);
    this.r = r;
  }

  show(g = window) {
    g.noFill();
    g.stroke(255);
    g.strokeWeight(2);
    g.circle(this.pos.x, this.pos.y, this.r * 2);
  }
}
