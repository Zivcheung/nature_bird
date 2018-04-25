package nature;
import processing.core.PApplet;
import java.util.ArrayList;
import processing.core.PVector;

public class Bird{
	PApplet parent;
	PVector position,velocity,acceleration;
	float maxforce;
	float max_speed;
	//float life_time;
	private boolean alive;
	
	Bird(PApplet a,float x,float y,float z){

		parent=a;
		velocity=new PVector(0,0,0); 
		position=new PVector(x,y,z);
		acceleration=new PVector(0,0,0);
		alive=true;
		max_speed=4;
		maxforce=0.05f;
		//life_time=50;
	}
	
	public void run(ArrayList<Bird> bs) {
		flocking(bs);
		update();
		render();
	}
	
	public void render() {
		parent.pushMatrix();
		parent.lights();
		parent.noStroke();
		parent.translate(position.x,position.y,position.z);
		parent.sphere(2);
		parent.popMatrix();
	}

	
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
		
		sep_force.mult(1.5f);
		align_force.mult(1f);
		cohe_force.mult(1f);
		
		add_force(sep_force);
		add_force(align_force);
		add_force(cohe_force);	
	}
	
	public PVector separation(ArrayList<Bird> bs){
		float ideal_dist=20f;
		PVector steer=new PVector(0f,0f,0f);
		int count=0;
		
		for(Bird other:bs) {
			PVector dist_dir=PVector.sub(this.position, other.position);
			float dist=dist_dir.mag();
			if(dist>0&&dist<ideal_dist) {
				//anything wrong put back the normalize!
				dist_dir.normalize();
				dist_dir.div(dist);
				steer.add(dist_dir);
				count++;
			}
		}
//		if(count>0) {
//			steer.div(count);
//		}
		if(steer.mag()>0) {
			steer.normalize();
			steer.mult(max_speed);
			steer.sub(velocity);
			steer.limit(maxforce);
			System.out.println("in sep");
		}
		return steer;
	}
	
	public PVector alignment(ArrayList<Bird> bs){
		float neighbor_dist=50f;
		PVector steer=new PVector(0f,0f,0f);
		PVector sum=new PVector(0f,0f,0f);
		int count=0;
		
		for(Bird other:bs) {
			float dist=PVector.dist(this.position, other.position);
			if(dist>0&&dist<neighbor_dist) {
				//anything wrong put back the normalize!
				sum.add(other.velocity);
				count++;
			}
		}

		if(steer.mag()>0) {
			sum.normalize();
			sum.mult(max_speed);
			steer=PVector.sub(sum,velocity);
			steer.limit(maxforce);
			System.out.println("in al");
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
				//anything wrong put back the normalize!
				sum.add(other.position);
				count++;
			}
		}

		if(count>0) {
			sum.div(count);
			sum.mult(max_speed);
			steer=fly_to(sum);
			System.out.println("in co");
		}
		return steer;
	}
	
	public PVector fly_to(PVector target) {
		PVector desire=PVector.sub(target,position);
		desire.normalize();
		desire.mult(max_speed);
		PVector steerforce=PVector.sub(desire,velocity);
		
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
