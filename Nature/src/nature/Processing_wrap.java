package nature;

import processing.core.PApplet;
import processing.core.PVector;

import ddf.minim.analysis.*;
import ddf.minim.*;


import peasy.PeasyCam;
import controlP5.*;

import nature.Bird;
import nature.Flock;
import nature.Screen;
import nature.Sorting;
import nature.Leader;

//import processing.event.*;


public class Processing_wrap extends PApplet{
	Flock flock;
	PeasyCam cam;
	ControlP5 cp5;
	//
	Minim minim;
	AudioPlayer music;
	FFT fft;
	String windowName;
	
	//
	int count;
	PVector boundary=new PVector(7000,10000,7000);
	Screen screen;
	Leader leader;
	
	int bands = 512;
	float[] spectrum = new float[bands];
	
	public static void main(String[] args) {
		// set PApplet to the package
		PApplet.main("nature.Processing_wrap");
	}
	
	public void settings() {
//		fullScreen(P3D);
		size(1200,800,P3D);

	}
	public void setup() {
	    cam=new PeasyCam(this,4000);
	    flock=new Flock(this);
	    screen=new Screen(this);
	    cp5=new ControlP5(this);
	    
	    cam.setMinimumDistance(0);
	    cam.setMaximumDistance(10000);
	    
	    minim = new Minim(this);
	    music = minim.loadFile("jingle.mp3", 512);
	    music.loop();
	    
	    perspective(PI/3.0f, (float)this.width/(float)this.height, 4000/10.0f, 4000*10.0f);
	    for (int i = 0; i < 1000; i++) {
//	        flock.add_bird(new Bird(this,(float)(Math.random()*box_width), 
//	        		(float)(Math.random()*box_height), 
//	        		(float)(Math.random()*box_depth)));
	    	flock.add_bird(new Bird(this,boundary.x/2-1000,boundary.y/2,boundary.z/2,boundary));
	
	    }
	    screen.initialize();
	    //set GUI
	    initial_interface();
	    
	}
	
	public void draw() {
	  
		dis_cam();//disable peasycam while dragging slider
	    background(50);
	    colorMode(HSB,360,100,100);
	    fft.forward(music.mix);
	    int i =10;
        float bandDB = 20 * log( 2 * fft.getBand(i) / fft.timeSize() );
      // given some reasonable range
        float bandHeight = map( bandDB, 0, -150, 0, 44 );
        float band = map( bandDB, 0, -150, 0, 100 );
	    ambientLight(bandHeight,100,100);
	    
//	    directionalLight(170,100,100, 0, 1, -100); 
	    colorMode(RGB,255,255,255);
	    noFill();
	    stroke(255);
	    
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
	    Sorting cells=flock.get_sorted();
	    screen.update_cells(cells);
	    screen.run();
	    gui();//integrate controlP5 with 3d scene
	    

	}
	
	void gui() {
		  hint(DISABLE_DEPTH_TEST);
		  cam.beginHUD();
		  cp5.draw();
		  cam.endHUD();
		  hint(ENABLE_DEPTH_TEST);
		}
	
	void initial_interface() {
		//slider 1
		cp5.addSlider("max_force")
		 .setValue(0.07f)
	     .setPosition(100,100)
	     .setSize(100,20)
	     .setRange(0.0f,0.3f)
	     
	     ;
		cp5.addSlider("max_speed")
		 .setValue(5f)
	     .setPosition(100,150)
	     .setSize(100,20)
	     .setRange(2,20)
	     ;
		cp5.addSlider("align_force")
		.setValue(1)
	     .setPosition(100,200)
	     .setSize(100,20)
	     .setRange(0,20)
	     ;
		cp5.addSlider("separate_force")
		 .setValue(1)
	     .setPosition(100,250)
	     .setSize(100,20)
	     .setRange(0,20)
	     ;
		cp5.addSlider("cohesion_force")
		 .setValue(3)
	     .setPosition(100,300)
	     .setSize(100,20)
	     .setRange(0,20)
	     ;
		cp5.addSlider("chase_force")
		 .setValue(3)
	     .setPosition(100,350)
	     .setSize(100,20)
	     .setRange(0,20)
	     ;
		cp5.setAutoDraw(false);
	}
	
	void dis_cam() {
		//disable peasycam while dragging slider
		if (cp5.isMouseOver()) {
		    cam.setActive(false);
		  } else {
		    cam.setActive(true);
		  }
	}
	
	public void max_force(float force) {
		flock.set_force(force);
		
	}
	public void max_speed(float force) {
		flock.set_speed(force);
		System.out.println("speed changed"+force);
	}
	public void align_force(float force) {
		flock.set_align(force);
		System.out.println("align changed"+force);
	}
	public void cohesion_force(float force) {
		flock.set_cohe(force);
		System.out.println("cohesion changed"+force);
	}
	public void separation_force(float force) {
		flock.set_sep(force);
		System.out.println("separation changed"+force);
	}
	public void chase_force(float force) {
		flock.set_chase(force);
		System.out.println("chase changed"+force);
	}
	
}

