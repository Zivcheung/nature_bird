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
		cells=new Sorting(birds,15000,15000,15000,400);//not include top   and  if change radius screen need to change as well	
	    leader.initialization();
	    speed=5f;
	    
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
//		          System.out.println("force: "+b.maxforce+"speed: "+b.max_speed);
		        }
		      }
		    }
		  }
		  flock_center.mult((float)(1.0 / birds.size()));
		  leader.show_path();
	}
	public Sorting get_sorted() {
		return cells;
	}
	void add_bird(Bird b) {
		birds.add(b);
	}
	void set_force(float force) {
		System.out.println("force changed"+force);
		for(Bird b:birds) {
			b.maxforce=force;
			System.out.println("force changed to   "+b.maxforce);
		}
	}
	void set_speed(float speed) {
		for(Bird b:birds) {
			b.max_speed=speed;
		}
	}
	void set_align(float multiplier) {
		for(Bird b:birds) {
			b.align_multiplier=multiplier;
		}
	}
	void set_cohe(float multiplier) {
		for(Bird b:birds) {
			b.cohe_multiplier=multiplier;
		}
	}
	void set_sep(float multiplier) {
		for(Bird b:birds) {
			b.sep_multiplier=multiplier;
		}
	}
	void set_chase(float multiplier) {
		for(Bird b:birds) {
			b.chase_multiplier=multiplier;
		}
	}

}
