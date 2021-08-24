// Code by Loïc BERTRAND
// Inpired by video "Chaotic Balls (and other animations) - Numberphile"
// https://www.youtube.com/watch?v=6z4qRhpBIyA

let container;
let balls = [];
let graphics;

function setup() {
  createCanvas(windowWidth, windowHeight);

  // Create the containing circle
  container = new Circle(width / 2, height / 2, min(width, height) * 0.45);

  // Create one ball for each color
  const colors = [
    color("#EC4899"),
    color("#FBBF24"),
    color("#34D399"),
    color("#60A5FA"),
  ];
  for (let i = 0; i < colors.length; i++) {
    // Very little offset produces very different paths (chaos theory)
    const offset = i * 1 + 1;
    balls[i] = new Ball(width / 2 + offset, height * 0.3, 12, colors[i]);
  }

  // Create an offscreen canvas to draw the trails
  graphics = createGraphics(width, height);
  graphics.background(0);
}

function draw() {
  // Update
  if (frameCount > 40) {
    const gravity = createVector(0, 0.4);

    for (const ball of balls) {
      ball.applyForce(gravity);
      ball.update();
      if (!ball.isInside(container)) {
        ball.bounce(container);
        ball.vel.mult(0.999); // friction
      }
    }
  }

  // Show
  image(graphics, 0, 0);
  for (const ball of balls) {
    ball.trail(graphics);
    ball.show();
  }
  container.show();
}
