package nature;
import processing.core.PApplet;
import processing.core.PGraphics;

import java.util.ArrayList;
import processing.core.PVector;

public class Bird{
	PApplet parent;
	PVector position,velocity,acceleration;
	PVector boundary;
	float maxforce;
	float max_speed;
	//float life_time;
	private boolean alive;
//	PGraphics t_box;
	
	Bird(PApplet a,float x,float y,float z){

		parent=a;
		velocity=new PVector((float)(Math.random()),(float)(Math.random()),(float)(Math.random())); 
		position=new PVector(x,y,z);
		acceleration=new PVector(0,0,0);
		boundary=new PVector(500,500,500);
		alive=true;
		max_speed=2;
		maxforce=0.1f;
		//life_time=50;
//		t_box=parent.createGraphics(parent.width,parent.height,PGraphics.P3D);
	}
	
	public void run(ArrayList<Bird> bs) {
		flocking(bs);
		update();
		render();
		check_bounds();
//		render_bounds();
	}
	
	public void render() {
		parent.pushMatrix();
		parent.lights();
		parent.noStroke();
		parent.translate(position.x,position.y,position.z);
		parent.sphere(2);
		parent.popMatrix();
	}
//	public void render_bounds() {
//		t_box.beginDraw();
//		t_box.pushMatrix();
//		t_box.lights();
//		t_box.noStroke();
////		t_box.translate(position.x,position.y,position.z);
//		t_box.box(boundary.x,boundary.y,boundary.z);
//		t_box.popMatrix();
//		t_box.endDraw();
//		t_box.tint(255, 126);
////		parent.image(t_box,0,0,0);
//	}

	
	public void update() {
		velocity.add(acceleration);
		velocity.limit(max_speed);
		position.add(velocity);
		clr_force();
	}
	

	
	public void flocking(ArrayList<Bird> bs) {
		PVector sep_force=separation(bs);
		PVector align_force=alignment(bs);
		PVector cohe_force=cohesion(bs);
		
		sep_force.mult(1f);
		align_force.mult(1f);
		cohe_force.mult(1f);
		
		add_force(sep_force);
		add_force(align_force);
		add_force(cohe_force);	
	}
	 void check_bounds() {
		    if (position.x > boundary.x) position.x = 0;
		    else if (position.x < 0) position.x = boundary.x;
		    if (position.y > boundary.y) position.y = 0;
		    else if (position.y < 0) position.y = boundary.y;
		    if (position.z > boundary.z) position.z = 0;
		    else if (position.z < 0) position.z = boundary.z;
	 }  
	 
	public PVector separation(ArrayList<Bird> bs){
		float ideal_dist=20f;
		PVector steer=new PVector(0f,0f,0f);
		int count=0;
		
		for(Bird other:bs) {
			PVector dist_dir=PVector.sub(this.position, other.position);
			float dist=dist_dir.mag();
			if(dist>0&&dist<ideal_dist) {
				dist_dir.normalize();
				dist_dir.div(dist);
				steer.add(dist_dir);
			}
		}
//		if(count>0) {
//			steer.div(count);
//		}
		if(steer.mag()>0) {
			steer.normalize();
			steer.mult(max_speed);
			steer.sub(velocity);
		}
		return steer;
	}
	
	public PVector alignment(ArrayList<Bird> bs){
		float neighbor_dist=50f;
		PVector steer=new PVector(0f,0f,0f);
		PVector sum=new PVector(0f,0f,0f);
		
		for(Bird other:bs) {
			float dist=PVector.dist(this.position, other.position);
			if(dist>0&&dist<neighbor_dist) {
				sum.add(other.velocity);
			}
		}

		if(sum.mag()>0) {
			sum.normalize();
			sum.mult(max_speed);
			steer=PVector.sub(sum,velocity);


		}
		return steer;
	}
	
	public PVector cohesion(ArrayList<Bird> bs){
		float neighbor_dist=50f;
		PVector steer=new PVector(0f,0f,0f);
		PVector sum=new PVector(0f,0f,0f);
		int count=0;
		
		for(Bird other:bs) {
			float dist=PVector.dist(this.position, other.position);
			if(dist>0&&dist<neighbor_dist) {
				sum.add(other.position);
				count++;
			}
		}

		if(count>0) {
			sum.div(count);
			steer=fly_to(sum);
			
		
		}
		return steer;
	}
	
	public PVector fly_to(PVector target) {
		PVector desire=PVector.sub(target,position);
		desire.normalize();
		desire.mult(max_speed);
		PVector steerforce=PVector.sub(desire,velocity);
		steerforce=steerforce.limit(maxforce);
		
		
		return steerforce;
		
	}
	
	public void set_velocity(PVector v) {
		velocity=v;
	}
	
	private void add_force(PVector a){
		acceleration.add(a);
	}

	public boolean is_live() {
		return alive;	
	}
	
	public void clr_force() {
		acceleration.set(0f,0f,0f);
	}
	
	
	
	
	
	
	
	
	
	
	
}
