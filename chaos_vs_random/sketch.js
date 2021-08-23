var counter;
var n_samples = 10000;
let x = 0.01;
let x2 = 0.01;
function setup() {
  createCanvas(800, 800);
  counter = 0;
  //frameRate(1000);
  background(50);
  
}

function draw() {
  //background(50);
  var r = 4;
  //for (let i = 0; i < n_samples; i++) {
    //var r = map(i, 0, n_samples, 0, 4);
    x = r * (x) * (1.0-x);
 
 var r2 = 3.8;
x2 = r2 * (x2) * (1.0-x2);
  
 // if(counter>0 & counter<1000000){
    stroke(255,50);
   // noStroke();
 // point(width*0.5, map(x, 0, r/2, height, 0));
  line(0, map(x, 0, r/4, height, 0),width/3,map(x, 0, r/4, height, 0));
  line(width/3, map(x2, 0, r2/4, height, 0),2*width/3,map(x2, 0, r2/4, height, 0));
  var random_value = random()*height;
  line(2*width/3, random_value,width,random_value);
//}

 //    }
    
}