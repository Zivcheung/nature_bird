package nature;
import processing.core.PApplet;
import processing.core.PVector;
import nature.Bird;

import peasy.PeasyCam;
//import processing.event.*;


public class Processing_wrap extends PApplet{
	Bird bird_a;
	PeasyCam cam;
	
	public static void main(String[] args) {
		// set PApplet to the package
		PApplet.main("nature.Processing_wrap");
	}
	
	public void settings() {
		size(800,800,P3D);
	}
	public void setup() {
	    bird_a=new Bird(this);
	    bird_a.set_velocity(new PVector(200,0,0));
	    cam=new PeasyCam(this,400);
	}
	
	public void draw() {
	    background(200);
		PVector target=new PVector(500,500,500);
		bird_a.steer(target);
		bird_a.update();
		bird_a.show();
		
	}
}

