package nature;
import java.util.ArrayList;
import processing.core.PApplet;

class Sorting
{  
  ArrayList[][][] zones;
  ArrayList[][][] neighbors;
  ArrayList<Bird> flock;
  int nx, ny, nz, sizex, sizey, sizez, rad;
  
  Sorting(ArrayList<Bird> _flock, int sizex, int sizey, int sizez, int rad)
  {
    this.sizex = sizex;
    this.sizey = sizey;
    this.sizez = sizez;
    this.rad = rad;
    
    flock=_flock;
    
    // number of zones
    nx = PApplet.floor(sizex / rad);
    ny = PApplet.floor(sizey / rad);
    nz = PApplet.floor(sizez / rad);
    
    // zone array and neighbors
    zones = new ArrayList[nx][ny][nz];
    neighbors = new ArrayList[nx][ny][nz];

    // initialize    
    for (int x=0; x<nx; x++) {
      for (int y=0; y<ny; y++) {
        for (int z=0; z<nz; z++) {
          zones[x][y][z] = new ArrayList<Bird>();
        }
      }
    }
    
    for (int x=0; x<nx; x++) {
      for (int y=0; y<ny; y++) {
        for (int z=0; z<nz; z++) {
          neighbors[x][y][z] = new ArrayList<ArrayList>();
        }
      }
    }
  }
    
  ArrayList<Bird> get(int i, int j, int k) {
    return zones[i][j][k];
  }
  
  ArrayList<Bird> getNeighbors(int i, int j, int k) {
    return neighbors[i][j][k];
  }

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
    // assign each Bird to correct zone
    for (int i=0; i<flock.size(); i++) {
      assign((Bird) flock.get(i));
    }
  }

  void assign(Bird bird) 
  {
    // determine which zone the boid belongs to
    int ix = (int) (bird.position.x / rad);
    int iy = (int) (bird.position.y / rad);
    int iz = (int) (bird.position.z / rad);
    zones[ix][iy][iz].add(bird);    
    
    // add Bird to all neighboring cells
    for (int i=PApplet.max(0,ix-1); i<PApplet.min(nx,ix+1); i++) {
      for (int j=PApplet.max(0,iy-1); j<PApplet.min(ny,iy+1); j++) {
        for (int k=PApplet.max(0,iz-1); k<PApplet.min(nz,iz+1); k++) {
          neighbors[i][j][k].add(bird);
        }
      }
    }    
  }  
}
