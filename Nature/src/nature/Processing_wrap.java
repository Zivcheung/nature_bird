package nature;

import processing.core.PApplet;
import processing.core.PVector;


import peasy.PeasyCam;

import nature.Bird;
import nature.Flock;

//import processing.event.*;


public class Processing_wrap extends PApplet{
	Flock flock;
	PeasyCam cam;
	int count;
	int box_width=600;
	int box_height=600;
	int box_depth=600;
	
	public static void main(String[] args) {
		// set PApplet to the package
		PApplet.main("nature.Processing_wrap");
	}
	
	public void settings() {
		size(1200,800,P3D);
	}
	public void setup() {
	    cam=new PeasyCam(this,800);
	    flock=new Flock();
	    
//	    for(int j=0;j<5;j++) {
//	    	
//	    	for(int i=0; i<5; i++) {
//	    		
//	    		for(int d=0; d<5;d++) {
//	    			flock.add_bird(new Bird(this,j*7,i*7,d*7));
//	    		}
//	    	}
//	    }
	    
	    for (int i = 0; i < 500; i++) {
//	        flock.add_bird(new Bird(this,(float)(Math.random()*box_width), 
//	        		(float)(Math.random()*box_height), 
//	        		(float)(Math.random()*box_depth)));
	    	flock.add_bird(new Bird(this,0,0,0));
	
	    }
	}
	
	public void draw() {
	    background(50);
	    directionalLight(255,255,255, 0, 1, -100); 
	    noFill();
	    stroke(0);
//	    System.out.println(count++);
	    line(-300,-300,-300, -300,300,-300);
	    line(-300,-300,300,  -300,300,300);
	    line(300,-300,300,    300,300,300);
	    line(300,-300,-300,   300,300,-300);
	    
	    line(-300,-300,-300,  300,-300,-300);
	    line(-300,-300,300,   300,-300,300);
	    line(-300,300,300,    300,300,300);
	    line(-300,300,-300,   300,300,-300);
	    
	    line(-300,-300,-300, -300,-300,300);
	    line(-300,300,-300,  -300,300,300);
	    line(300,300,-300,    300,300,300);
	    line(300,-300,-300,   300,-300,300);
	
	  
	    flock.run();

	}
}

