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
	    
	    for(int j=0;j<4;j++) {
	    	
	    	for(int i=0; i<4; i++) {
	    		
	    		for(int d=0; d<4;d++) {
	    			flock.add_bird(new Bird(this,j*4,i*4,d*4));
	    		}
	    	}
	    }
	}
	
	public void draw() {
	    background(50);
//	    System.out.println(count++);
	    flock.run();

	}
}

