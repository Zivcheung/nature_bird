package nature;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import nature.Bird;

public class Flock {
	ArrayList<Bird> birds;
	
	
	 Flock() {
		birds=new ArrayList<Bird>();
	}
	void run() {
		for(Bird b:birds) {
			b.run();
		}
	}
	void add_bird(Bird b) {
		birds.add(b);
	}

}
