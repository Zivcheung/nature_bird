package nature;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import nature.Bird;
import nature.Sorting;

public class Flock {
	ArrayList<Bird> birds;
	Sorting cells;
	PVector flock_center;
	
	 Flock() {
		birds=new ArrayList<Bird>();
		cells=new Sorting(birds,1500,1500,1500,100);
	}
	void run() {
		for(Bird b:birds) {
			b.run(birds);
		}
	}
	void optimized_run() {
		cells.update();
		  
		  // compare bird to other Birds in neighboring zones, and calculate centroid
		  flock_center = new PVector(0,0,0);
		  for (int i=0; i < cells.nx; i++) {      
		    for (int j=0; j < cells.ny; j++) {
		      for (int k=0; k < cells.nz; k++) {
		        ArrayList<Bird> neighbors = cells.getNeighbors(i,j,k);
		        for (Bird b : cells.get(i,j,k)) {
		          flock_center.add(b.position.x, b.position.y, b.position.z);
		          b.run(neighbors);
		        }
		      }
		    }
		  }
		  flock_center.mult((float)(1.0 / birds.size()));
	}
	void add_bird(Bird b) {
		birds.add(b);
	}

}
