package nature;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.*;
import nature.Sorting;

public class Screen{
	Table point_table;
//	TableRow row;
	ArrayList<PVector> points=new ArrayList<PVector>();
	ArrayList<PVector> locations=new ArrayList<PVector>();
	Sorting cells;
	float detect_rad;
	
	float[] open_size=new float[378];        //change every time
	PApplet parent;
	int sensitivity=60;
	
	int rad;//need to be the same with sorting parameter
	
	Screen(PApplet chanel){
		parent=chanel;
		point_table=parent.loadTable("F:\\3y_sem2_2018\\Computational prototyping\\points_coordinate1.csv");
		rad=200;
		detect_rad=100f;
	}
	
	public void load_points() {
		
		for(TableRow row:point_table.rows()) {
			float t_x=row.getFloat(0);
			float t_y=row.getFloat(1);
			float t_z=row.getFloat(2);
			float x=t_y;
			float y=-t_z;
			float z=t_x;
			points.add(new PVector(x,y,z));
//			System.out.print(x);
//			System.out.print("    ");
//			System.out.print(y);
//			System.out.print("    ");
//			System.out.print(z);
//			System.out.println("..");
			
		}
	}
	void initialize() {
		load_points();
		locate();
	}
	void run() {
		reset_size();
		calculate();
		render();
		
	}
	
	public void render() {
		parent.strokeWeight(2);
		parent.stroke(255);
		for(int i=0;i<points.size();i++) {
			PVector point=points.get(i);
			float open_radius=150*(open_size[i]/sensitivity);
//			System.out.println(open_radius);
			parent.pushMatrix();
			parent.translate(point.x,point.y,point.z);
			parent.rotateX((float)1.88);
			drawCylinder(1f,open_radius,150f,10);
			parent.popMatrix();
		}
	}
	public void drawCylinder(float topRadius, float bottomRadius, float tall, int sides) {
		  float angle = 0;
		  float angleIncrement = PApplet.TWO_PI / sides;
		  parent.beginShape(PApplet.QUAD_STRIP);
		  for (int i = 0; i < sides + 1; ++i) {
		    parent.vertex(topRadius*PApplet.cos(angle), 0, topRadius*PApplet.sin(angle));
		    parent.vertex(bottomRadius*PApplet.cos(angle), tall, bottomRadius*PApplet.sin(angle));
		    angle += angleIncrement;
		  }
		  parent.endShape();
		  
//		  // If it is not a cone, draw the circular top cap
//		  if (topRadius != 0) {
//		    angle = 0;
//		    parent.beginShape(PApplet.TRIANGLE_FAN);
//		    
//		    // Center point
//		    parent.vertex(0, 0, 0);
//		    for (int i = 0; i < sides + 1; i++) {
//		    	parent.vertex(topRadius * PApplet.cos(angle), 0, topRadius * PApplet.sin(angle));
//		      angle += angleIncrement;
//		    }
//		    parent.endShape();
//		  }
//
//		  // If it is not a cone, draw the circular bottom cap
//		  if (bottomRadius != 0) {
//		    angle = 0;
//		    parent.beginShape(PApplet.TRIANGLE_FAN);
//
//		    // Center point
//		    parent.vertex(0, tall, 0);
//		    for (int i = 0; i < sides + 1; i++) {
//		    	parent.vertex(bottomRadius * PApplet.cos(angle), tall, bottomRadius * PApplet.sin(angle));
//		      angle += angleIncrement;
//		    }
//		    parent.endShape();
//		  }
		}
	public void update_cells(Sorting _cells) {
		cells=_cells;
	}
	
	public void locate() {
		    // determine which zone the boid belongs to
			for(PVector p:points) {
		    int ix = (int) (p.x / rad);
		    int iy = (int) (p.y / rad);
		    int iz = (int) (p.z / rad);
		    locations.add(new PVector(ix,iy,iz));
		   }
	}
	public void reset_size() {
		for(int i=0;i<open_size.length;i++) {
			open_size[i]=open_size[i]-0.5f;
			open_size[i]=PApplet.constrain(open_size[i], 1, sensitivity);
			
		}
	}
	
	public void calculate() {
		for(int i=0;i<points.size();i++) {
			PVector machanism_pos=points.get(i);
			PVector loc=locations.get(i);
			ArrayList<Bird> nearby=cells.getNeighbors((int)loc.x, (int)loc.y, (int)loc.z);
			for(Bird bird:nearby) {
				float distance=PVector.dist(bird.position,machanism_pos);
				if(distance<detect_rad) {
					open_size[i]++;
					open_size[i]=PApplet.constrain(open_size[i], 1, sensitivity);
				}
			}
			
		}
		
	}
	
	

}
