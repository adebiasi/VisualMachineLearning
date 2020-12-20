// Daniel Shiffman
// http://codingtra.in
// http://patreon.com/codingtrain

// Color Predictor
// https://youtu.be/KtPpoMThKUs

// Inspired by Jabril's SEFD Science
// https://youtu.be/KO7W0Qq8yUE
// https://youtu.be/iN3WAko2rL8

let r, g, b;
let brain;

let which = 'black';

let wButton;
let bButton;

function pickColor() {
  r = random(255);
  g = random(255);
  b = random(255);
  redraw();
}

function setup() {
  createCanvas(600, 300);
  noLoop();
  brain = new NeuralNetwork(3, 3, 2);

  for (let i = 0; i < 10000; i++) {
	let rgb = [random(255),random(255),random(255)];
    let targets = trainColor(rgb[0],rgb[1],rgb[2]);
    let inputs = [rgb[0] / 255, rgb[1] / 255, rgb[2] / 255];
    brain.train(inputs, targets);
  }

  pickColor();
}

function mousePressed() {
  // let targets;
  // if (mouseX > width / 2) {
  //   targets = [0, 1];
  // } else {
  //   targets = [1, 0];
  // }
  // let inputs = [r / 255, g / 255, b / 255];
  //
  // brain.train(inputs, targets);

  pickColor();
}

function colorPredictor(r, g, b) {
  console.log(floor(r + g + b));
  let inputs = [r / 255, g / 255, b / 255];
  let outputs = brain.predict(inputs);
  //console.log(outputs);

  if (outputs[0] > outputs[1]) {
    return 'black';
  } else {
    return 'white';
  }

  // if (r + g + b > 300) {
  //   return "black";
  // } else {
  //   return "white";
  // }
}

function oracle(r, g, b) {
  console.log(floor(r + g + b));
  let outputs = trainColor(r,g,b);
  //console.log(outputs);

  if (outputs[0] > outputs[1]) {
	  console.log("oracle: should be black. "+outputs[0]+" > " +outputs[1])
    return 'black';
  } else {
	  console.log("oracle: should be white."+outputs[0]+" < " +outputs[1])
    return 'white';
  }

  // if (r + g + b > 300) {
  //   return "black";
  // } else {
  //   return "white";
  // }
}

function trainColor(r, g, b) {
	let sum = (r + g + b);
	let res = (255 * 3) / 2;
  if (sum > res) {	  
	  console.log(sum +">"+ res);
    return [1, 0];
  } else {
	  console.log(sum +"<"+ res);
    return [0, 1];
  }
}

function draw() {
  background(r, g, b);
  strokeWeight(4);
  stroke(0);
  line(width / 2, 0, width / 2, height);

  textSize(64);
  noStroke();
  fill(0);
  textAlign(CENTER, CENTER);
  text('black', 150, 100);
  fill(255);
  text('white', 450, 100);

  textSize(10);
  let which = colorPredictor(r, g, b);
  if (which === 'black') {
    fill(0);
    ellipse(150, 180, 60);
	fill(255);
	text('n. network', 150, 180);
  } else {
    fill(255);
    ellipse(450, 180, 60);
	fill(0);
	text('neural\n network', 450, 180);
  }
  
  let true_answer = oracle(r, g, b);
  if (true_answer === 'black') {
    fill(0);
    ellipse(150, 260, 60);
	fill(255);
	text('oracle', 150, 260);
  } else {
    fill(255);
    ellipse(450, 260, 60);
	fill(0);
	text('oracle', 450, 260);
  }
}
