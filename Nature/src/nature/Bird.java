package nature;
import processing.core.PApplet;
import processing.core.PGraphics;


import java.util.ArrayList;
import processing.core.PVector;

public class Bird{
	float neighbour_scope;
	float bird_size;
	PApplet parent;
	PVector position,velocity,acceleration;
	PVector boundary;
	float maxforce;
	float max_speed;
	//float life_time;
	private boolean alive;
	PVector sep_force;
	PVector align_force;
	PVector cohe_force;
//	PGraphics t_box;
	
	Bird(PApplet a,float x,float y,float z,PVector bound){
		bird_size=2;
		parent=a;
		velocity=new PVector(parent.random(-1,1),parent.random(-1,1),parent.random(-1,1)); 
		position=new PVector(x,y,z);
		acceleration=new PVector(0,0,0);
		boundary=bound;
		alive=true;
		max_speed=5;
		maxforce=0.06f;
		neighbour_scope=300;
		//life_time=50;
//		t_box=parent.createGraphics(parent.width,parent.height,PGraphics.P3D);
	}
	
	public void run(ArrayList<Bird> bs) {
		flocking(bs);
		update();
		render();
//		check_bounds();
		wall();
//		render_bounds();
	}
	public void run_self() {
		update();
		render();
//		check_bounds();
		wall();
	}
	
	public void render() {
		parent.pushMatrix();
	    parent.translate(position.x,position.y,position.z);
	    parent.rotateY(PApplet.atan2(-velocity.z,velocity.x));
	    parent.rotateZ(PApplet.asin(velocity.y/velocity.mag()));
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

	

	
	public void update() {
		velocity.add(acceleration);
		velocity.limit(max_speed);
		position.add(velocity);
		clr_force();
	}
	
	PVector avoid(PVector target,boolean weight)
	  {
	    PVector steer = new PVector(); //creates vector for steering
	    steer.set(PVector.sub(position,target)); //steering vector points away from target
	    if(weight)
	      steer.mult(1/PApplet.sq(PVector.dist(position,target)));
	    return steer;
	  }
	
	public void flocking(ArrayList<Bird> bs) {
		sep_force=separation(bs);
		align_force=alignment(bs);
		cohe_force=cohesion(bs);
		
		sep_force.mult(1f);
		align_force.mult(1f);
		cohe_force.mult(3f);
		
		add_force(sep_force);
		add_force(align_force);
		add_force(cohe_force);	
	}
	
	
	 void check_bounds() {
		    if (position.x > boundary.x) position.x =0;
		    if (position.x < 0)          position.x = boundary.x;
		    if (position.y > boundary.y) position.y =0;
		    if (position.y < 0)   		 position.y = boundary.y;
		    if (position.z > boundary.z) position.z =0;
		    if (position.z < 0) 	     position.z = boundary.z;


	 }  
	 
	 void wall() {
	      acceleration.add(PVector.mult(avoid(new PVector(position.x,boundary.y,position.z),true),15));
	      acceleration.add(PVector.mult(avoid(new PVector(position.x,0,position.z),true),15));
	      acceleration.add(PVector.mult(avoid(new PVector(boundary.x,position.y,position.z),true),15));
	      acceleration.add(PVector.mult(avoid(new PVector(0,position.y,position.z),true),15));
	      acceleration.add(PVector.mult(avoid(new PVector(position.x,position.y,boundary.z),true),15));
	      acceleration.add(PVector.mult(avoid(new PVector(position.x,position.y,0),true),15));
	 }
	//separation return sum of all repulse and scaled inversely proportional to distance
	public PVector separation(ArrayList<Bird> bs){
		PVector rep_sum=new PVector(0f,0f,0f);//sum of repulse force
		
		for(Bird other:bs) {
			PVector dist_dir=PVector.sub(this.position, other.position);
			float dist=dist_dir.mag();
			if(dist>0&&dist<neighbour_scope) {
				dist_dir.normalize();//make it to one
				dist_dir.div(dist);//reverse proportional to distance
				rep_sum.add(dist_dir);
			}
		}
		return rep_sum;
	}
	//alignment return a sum of all neighbor velocity
	public PVector alignment(ArrayList<Bird> bs){
		PVector vel_sum=new PVector(0f,0f,0f);
		int count=0;
		for(Bird other:bs) {
			float dist=PVector.dist(this.position, other.position);
			if(dist>0&&dist<neighbour_scope) {
				vel_sum.add(other.velocity);
				count++;
			}
		}
		if(count>0) {
			vel_sum.div((float)count);
			vel_sum.limit(maxforce);
		}
		return vel_sum;
	}
	//cohesion return a steer force
	public PVector cohesion(ArrayList<Bird> bs){
		PVector steer=new PVector(0f,0f,0f);
		PVector sum=new PVector(0f,0f,0f);
		int count=0;
		
		for(Bird other:bs) {
			float dist=PVector.dist(this.position, other.position);
			if(dist>0&&dist<neighbour_scope) {
				sum.add(other.position);
				count++;
			}
		}

		if(count>0) {
			sum.div((float)count);	
		}
		steer=PVector.sub(sum,position);
		steer.limit(maxforce);
		return steer;
	}
	
	public PVector steer(PVector target, boolean arrival) {
		float arrivel_dist=100f;
		PVector desired=new PVector();
		PVector steerforce=new PVector();
		desired=PVector.sub(target,position);
		float dist=desired.mag();
		desired.normalize();
		if(dist<arrivel_dist&&arrival) {
			float m=PApplet.map(dist,0,arrivel_dist,0,max_speed);
			desired.mult(m);
		}else {
			desired.mult(max_speed);
		}
		steerforce=PVector.sub(desired,velocity);
		steerforce.limit(maxforce);
		add_force(steerforce.mult(3));
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
		acceleration.mult(0);
	}
	
	
	
	
	
	
	
	
	
	
}
