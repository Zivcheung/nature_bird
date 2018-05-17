package nature;
import processing.core.PApplet;
import processing.core.PVector;

import java.util.ArrayList;

import nature.Bird;
import nature.Sorting;
import nature.Leader;

public class Flock {
	ArrayList<Bird> birds;
	Sorting cells;
	PVector flock_center;
	PApplet parent;
	Leader leader;
	PVector boundary=new PVector(7000,10000,7000);
	float speed,force,sep_multipler,co_multipler,ali_multipler;
	 Flock(PApplet _parent) {
		parent=_parent;
		birds=new ArrayList<Bird>();
		leader=new Leader(parent,boundary);
		cells=new Sorting(birds,10000,10000,10000,200);//not include top	
		
	    leader.initialization();
	}
	void run() {
		for(Bird b:birds) {
			b.run(birds);
		}
	}
	void optimized_run() {
		cells.update();
		leader.run();
		  // compare bird to other Birds in neighboring zones, and calculate centroid
		  flock_center = new PVector(0,0,0);
		  for (int i=0; i < cells.nx; i++) {      
		    for (int j=0; j < cells.ny; j++) {
		      for (int k=0; k < cells.nz; k++) {
		        ArrayList<Bird> neighbors = cells.getNeighbors(i,j,k);
		        for (Bird b : cells.get(i,j,k)) {
		          flock_center.add(b.position.x, b.position.y, b.position.z);
		          b.run(neighbors,leader.lead_bird);
		        }
		      }
		    }
		  }
		  flock_center.mult((float)(1.0 / birds.size()));
//		  leader.show_path();
	}
	public Sorting get_sorted() {
		return cells;
	}
	void add_bird(Bird b) {
		birds.add(b);
	}
	void set_speed() {
		for(Bird b:birds) {
			b.max_speed=
		}
	}

}
