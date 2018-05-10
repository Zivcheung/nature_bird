package nature;

import nature.Bird;
import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.*;

public class Leader {
	Bird lead_bird;
	Table path_table;
	ArrayList<PVector> path;
	PVector bound;
	PApplet parent;
	int prograss;
	float bird_size=20;


	public Leader(PApplet _parent,PVector _bound){
		path=new ArrayList<PVector>();
		parent=_parent;
		path_table=parent.loadTable("F:\\3y_sem2_2018\\Computational prototyping\\path.csv");
		bound=_bound;
		prograss=0;
	}
	
	public void initialization() {
		load_path();
		inital_leader();
		lead_bird.max_speed=10;
	}
	
	public void run() {
		move();
		lead_bird.run_self(false);
//		render();
	}
	
	public void load_path() {
			
			for(TableRow row:path_table.rows()) {
				float t_x=row.getFloat(0);
				float t_y=row.getFloat(1);
				float t_z=row.getFloat(2);
				float x=t_y;
				float y=-t_z;
				float z=t_x;
				path.add(new PVector(x,y,z));
			}
	}
	
	public void inital_leader() {
		PVector start=path.get(0);
		System.out.println(start);
		lead_bird=new Bird(parent, start.x, start.y, start.z, bound);
	}
	
	
	public void move() {
		lead_bird.steer(path.get(prograss+1),false);
		float dist=PVector.dist(path.get(prograss+1), lead_bird.position);
		if(dist<30) {
			prograss++;
			if(prograss>path.size()) prograss=0;
		}
//		System.out.println(prograss);
		
	}
	
	public void render() {
		parent.pushMatrix();
	    parent.translate(lead_bird.position.x,lead_bird.position.y,lead_bird.position.z);
//	    System.out.println(lead_bird.position);
	    parent.rotateY(PApplet.atan2(-lead_bird.velocity.z,lead_bird.velocity.x));
	    parent.rotateZ(PApplet.asin(lead_bird.velocity.y/lead_bird.velocity.mag()));
	    parent.stroke(0,0,0);
	    parent.noFill();
	    parent.noStroke();
	    parent.fill(255,255,255);
	    //draw bird
	    parent.beginShape(PApplet.TRIANGLES);
	    parent.vertex(3*bird_size,0,0);
	    parent.vertex(-3*bird_size,2*bird_size,0);
	    parent.vertex(-3*bird_size,-2*bird_size,0);
	    
	    parent.vertex(3*bird_size,0,0);
	    parent.vertex(-3*bird_size,2*bird_size,0);
	    parent.vertex(-3*bird_size,0,2*bird_size);
	    
	    parent.vertex(3*bird_size,0,0);
	    parent.vertex(-3*bird_size,0,2*bird_size);
	    parent.vertex(-3*bird_size,-2*bird_size,0);
	    
	    
	    parent.vertex(2*bird_size,0,0);
	    parent.vertex(-1*bird_size,0,0);
	    parent.vertex(-1*bird_size,-8*bird_size);//wing
	    
	    parent.vertex(2*bird_size,0,0);
	    parent.vertex(-1*bird_size,0,0);
	    parent.vertex(-1*bird_size,8*bird_size);//wing
	    
	    
	    parent.vertex(-3*bird_size,0,2*bird_size);
	    parent.vertex(-3*bird_size,2*bird_size,0);
	    parent.vertex(-3*bird_size,-2*bird_size,0);
	    parent.endShape();
	    //box(10);
	    parent.popMatrix();
	}
	
	public void show_path() {
		parent.strokeWeight(1);
		parent.stroke(255);
		parent.beginShape(PApplet.LINES);
		for(int i=1; i<path.size()-1; i++) {
			
			parent.vertex(path.get(i-1).x,path.get(i-1).y,path.get(i-1).z);
			parent.vertex(path.get(i).x,path.get(i).y,path.get(i).z);
			
		}
		parent.endShape();
	}
	
}
