package nature;

import java.util.ArrayList;
import processing.core.PApplet;
import processing.core.PVector;
import processing.data.*;

public class Screen{
	Table point_table;
//	TableRow row;
	ArrayList<PVector> points=new ArrayList<PVector>();
	PApplet parent;
	Screen(PApplet chanel){
		parent=chanel;
		point_table=parent.loadTable("F:\\3y_sem2_2018\\Computational prototyping\\points_coordinate.csv");
	}
	public void run() {
		render_point();
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
			System.out.print(x);
			System.out.print("    ");
			System.out.print(y);
			System.out.print("    ");
			System.out.print(z);
			System.out.println("..");
			
		}
	}
	
	public void render_point() {
		parent.strokeWeight(2);
		parent.stroke(255);
		for(PVector point:points) {
			parent.pushMatrix();
			parent.translate(point.x,point.y,point.z);
			parent.rotateX((float)1.88);
			drawCylinder(1f,150f,250f,10);
			parent.popMatrix();
		}
	}
	void drawCylinder(float topRadius, float bottomRadius, float tall, int sides) {
		  float angle = 0;
		  float angleIncrement = PApplet.TWO_PI / sides;
		  parent.beginShape(PApplet.QUAD_STRIP);
		  for (int i = 0; i < sides + 1; ++i) {
		    parent.vertex(topRadius*PApplet.cos(angle), 0, topRadius*PApplet.sin(angle));
		    parent.vertex(bottomRadius*PApplet.cos(angle), tall, bottomRadius*PApplet.sin(angle));
		    angle += angleIncrement;
		  }
		  parent.endShape();
		  
		  // If it is not a cone, draw the circular top cap
		  if (topRadius != 0) {
		    angle = 0;
		    parent.beginShape(PApplet.TRIANGLE_FAN);
		    
		    // Center point
		    parent.vertex(0, 0, 0);
		    for (int i = 0; i < sides + 1; i++) {
		    	parent.vertex(topRadius * PApplet.cos(angle), 0, topRadius * PApplet.sin(angle));
		      angle += angleIncrement;
		    }
		    parent.endShape();
		  }

		  // If it is not a cone, draw the circular bottom cap
		  if (bottomRadius != 0) {
		    angle = 0;
		    parent.beginShape(PApplet.TRIANGLE_FAN);

		    // Center point
		    parent.vertex(0, tall, 0);
		    for (int i = 0; i < sides + 1; i++) {
		    	parent.vertex(bottomRadius * PApplet.cos(angle), tall, bottomRadius * PApplet.sin(angle));
		      angle += angleIncrement;
		    }
		    parent.endShape();
		  }
		}

}
