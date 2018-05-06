package nature;
import java.util.ArrayList;
import processing.core.PApplet;

public class Sorting {
  ArrayList<Bird>[][][] zoness;
  ArrayList<ArrayList<ArrayList<Bird>>> zones;
  ArrayList<ArrayList<ArrayList<ArrayList<Bird>>>> neighbors;
  int nx, ny, nz, sizex, sizey, sizez, rad;
  
  Sorting(int sizex, int sizey, int sizez, int rad)
  {
    this.sizex = sizex;
    this.sizey = sizey;
    this.sizez = sizez;
    this.rad = rad;
    ArrayList[][][] zoness= new ArrayList[nx][ny][nz]zoness;
    // number of zones
    nx = (int) Math.floor(sizez/rad);
    ny = (int) Math.floor(sizey / rad);
    nz = (int) Math.floor(sizez / rad);
    
    // zone array and neighbors
    zones = new ArrayList<ArrayList<ArrayList<Bird>>>();
    neighbors = new ArrayList<ArrayList<ArrayList<ArrayList<Bird>>>>();
    zoness= new ArrayList[nx][ny][nz];
  }
    
//  ArrayList<Boid> get(int i, int j, int k) {
//    return zones[i][j][k];
//  }
//  
//  ArrayList<Boid> getNeighbors(int i, int j, int k) {
//    return neighbors[i][j][k];
//  }

  void update() 
  {
    // clear all zones first
    for (int x=0; x<nx; x++) {
      for (int y=0; y<ny; y++) {
        for (int z=0; z<nz; z++) {
          zones[x][y][z].clear();
          neighbors[x][y][z].clear();
        }
      }
    }    
    // assign each boid to correct zone
    for (int i=0; i<flock.size(); i++) {
      assign((Boid) flock.get(i));
    }
  }

  void assign(Boid boid) 
  {
    // determine which zone the boid belongs to
    int ix = (int) (boid.pos.x / rad);
    int iy = (int) (boid.pos.y / rad);
    int iz = (int) (boid.pos.z / rad);
    zones[ix][iy][iz].add(boid);    
    
    // add boid to all neighboring cells
    for (int i=max(0,ix-1); i<min(nx,ix+1); i++) {
      for (int j=max(0,iy-1); j<min(ny,iy+1); j++) {
        for (int k=max(0,iz-1); k<min(nz,iz+1); k++) {
          neighbors[i][j][k].add(boid);
        }
      }
    }    
  }  
}

