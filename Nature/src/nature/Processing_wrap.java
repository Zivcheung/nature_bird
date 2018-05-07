package nature;

import processing.core.PApplet;
import processing.core.PVector;


import peasy.PeasyCam;

import nature.Bird;
import nature.Flock;
import nature.Screen;

//import processing.event.*;


public class Processing_wrap extends PApplet{
	Flock flock;
	PeasyCam cam;
	int count;
	PVector boundary=new PVector(2000,2200,2000);
	Screen screen;
	
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
	    screen=new Screen(this);
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
	    	flock.add_bird(new Bird(this,boundary.x/2,boundary.y/2,boundary.z/2));
	
	    }
	    screen.load_points();
	}
	
	public void draw() {
	    background(50);
	    directionalLight(255,255,255, 0, 1, -100); 
	    noFill();
	    stroke(255);
//	    System.out.println(count++);
	    line(0,0,0,           0,boundary.y,0);
	    line(0,0,0,           0,0,boundary.z);
	    line(0,0,0,           boundary.x,0,0);
	    
	    line(boundary.x,boundary.y,boundary.z, boundary.x,0,boundary.z);
	    line(boundary.x,boundary.y,boundary.z, 0,boundary.y,boundary.z);
	    line(boundary.x,boundary.y,boundary.z, boundary.x,boundary.y,0);
	    
	    line(boundary.x,0,0,                   boundary.x,0,boundary.z);
	    line(boundary.x,0,0,                   boundary.x,boundary.y,0);
	    line(0,0,boundary.z,                   boundary.x,0,boundary.z);
	    line(0,boundary.y,0,                   0,boundary.y,boundary.z);
	    line(0,boundary.y,boundary.z,          0,0,boundary.z);
	    line(0,boundary.y,0,                   boundary.x,boundary.y,0);
	
	
	  
	    flock.optimized_run();
	    screen.run();

	}
}

