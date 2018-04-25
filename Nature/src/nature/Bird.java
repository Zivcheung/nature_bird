package nature;
import processing.core.PApplet;
import java.util.ArrayList;
import processing.core.PVector;

public class Bird{
	PApplet parent;
	PVector position,velocity,acceleration;
	PVector maxforce;
	float max_speed;
	//float life_time;
	private boolean alive;
	Bird(PApplet a){
		parent=a;
		velocity=new PVector(0,0,0);
		position=new PVector(0,0,0);
		acceleration=new PVector(0,0,0);
		alive=true;
		max_speed=4;
		//life_time=50;
	}
	
	public void run() {
		
	}
	
	public void render() {
		parent.pushMatrix();
		parent.lights();
		parent.noStroke();
		parent.translate(position.x,position.y,position.z);
		parent.sphere(20);
		parent.popMatrix();
	}

	
	public void update() {
		velocity.add(acceleration);
		velocity.limit(max_speed);
		position.add(velocity);
		clr_force();
	}
	
	public void fly_to(PVector target) {
		PVector desire=PVector.sub(target,position);
		desire.normalize();
		desire.mult(max_speed);
		PVector steerforce=PVector.sub(desire,velocity);
		
		add_force(steerforce);
		
	}

	public PVector separation(ArrayList<Bird> bs){
		float ideal_dist=20f;
		
		for(Bird other:bs) {
			PVector dist_dir=PVector.sub(this.position, other.position);
			float dist=dist_dir.mag();
			if(dist>0&&dist<ideal_dist) {
				dist_dir.normalize();
				
			}
		}
		
		return;
	}

	
	public void set_velocity(PVector v) {
		velocity=v;
	}
	
	private void add_force(PVector a){
		acceleration=acceleration.add(a);
	}

	public boolean is_live() {
		return alive;	
	}
	
	public void clr_force() {
		acceleration.set(0f,0f,0f);
	}
	
	
	
	
	
	
	
	
	
	
	
}
