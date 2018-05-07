package nature;

import processing.core.PApplet;
import processing.core.PVector;


import peasy.PeasyCam;

import nature.Bird;
import nature.Flock;
import nature.Screen;
import nature.Sorting;

//import processing.event.*;


public class Processing_wrap extends PApplet{
	Flock flock;
	PeasyCam cam;
	int count;
	PVector boundary=new PVector(1600,1700,2000);
	Screen screen;
	
	public static void main(String[] args) {
		// set PApplet to the package
		PApplet.main("nature.Processing_wrap");
	}
	
	public void settings() {
		fullScreen(P3D);
//		size(1200,800,P3D);
	}
	public void setup() {
	    cam=new PeasyCam(this,800);
	    flock=new Flock();
	    screen=new Screen(this);
	    
	    for (int i = 0; i < 1000; i++) {
//	        flock.add_bird(new Bird(this,(float)(Math.random()*box_width), 
//	        		(float)(Math.random()*box_height), 
//	        		(float)(Math.random()*box_depth)));
	    	flock.add_bird(new Bird(this,boundary.x/2,boundary.y/2,boundary.z/2,boundary));
	
	    }
	    screen.initialize();
	}
	
	public void draw() {
	    background(50);
	    ambientLight(255,172,6);
	    directionalLight(100,50,6, 0, 1, -100); 
	    noFill();
	    stroke(255);
//	    System.out.println(count++);
	    
//	    
//	    line(0,0,0,           0,boundary.y,0);
//	    line(0,0,0,           0,0,boundary.z);
//	    line(0,0,0,           boundary.x,0,0);
//	    
//	    line(boundary.x,boundary.y,boundary.z, boundary.x,0,boundary.z);
//	    line(boundary.x,boundary.y,boundary.z, 0,boundary.y,boundary.z);
//	    line(boundary.x,boundary.y,boundary.z, boundary.x,boundary.y,0);
//	    
//	    line(boundary.x,0,0,                   boundary.x,0,boundary.z);
//	    line(boundary.x,0,0,                   boundary.x,boundary.y,0);
//	    line(0,0,boundary.z,                   boundary.x,0,boundary.z);
//	    line(0,boundary.y,0,                   0,boundary.y,boundary.z);
//	    line(0,boundary.y,boundary.z,          0,0,boundary.z);
//	    line(0,boundary.y,0,                   boundary.x,boundary.y,0);
	
	
	  
	    flock.optimized_run();
	    Sorting cells=flock.get_sorted();
	    screen.update_cells(cells);
	    screen.run();

	}
}

