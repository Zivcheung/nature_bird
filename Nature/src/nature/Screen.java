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
		parent.strokeWeight(10);
		parent.stroke(255);
		parent.beginShape(PApplet.POINTS);
		for(PVector point:points) {
			parent.vertex(point.x,point.y,point.z);	
		}
		parent.endShape();
	}

}
