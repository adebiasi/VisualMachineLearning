var counter;
var n_samples = 1000;
let x_array = [];

function setup() {
  createCanvas(800, 800);
  counter = 0;
  //frameRate(1000);
  background(50);
  
  
  for (let i = 0; i < n_samples; i++) {
    x_array[i] = 0.01;
  }
  
}

function draw() {
  //background(50);
  
  for (let i = 0; i < n_samples; i++) {
    var r = map(i, 0, n_samples, 0, 4);
    x_array[i] = r * (x_array[i]) * (1.0-x_array[i]);
 
  counter+=1;
  
  if(counter>0 & counter<1000000){
    stroke(255,40);
    //noStroke();
  point(map(i, 0, n_samples, 0, width), height - x_array[i]*height, 1);
}

     }
    
}