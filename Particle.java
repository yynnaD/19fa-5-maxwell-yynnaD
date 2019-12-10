import java.awt.Color;
import java.awt.Graphics;

public class Particle {
	int x,y;
	double vx, vy;
	boolean side; //true = left
	
	public Particle(int x, int y, double vx, double vy, boolean side) {
		this.x = x;
		this.y = y;
		this.vx = vx;
		this.vy = vy;
		this.side = side;
		
		
	}

	public void move(boolean open) {
		x += vx;
		y += vy;
		
		//full play area
		if(x <= 20) {vx *= -1; x = 20;}
		if(y <= 0) {vy *= -1; y = 0;}
		if(x >= 470) {vx *= -1; x = 470;}
		if(y >= 390) {vy *= -1; y = 390;}
		
		//issues with balls getting stuck
		if(open) {
			if((side && x >= 240 && vx > 0) || (!side && x <= 250 && vx < 0)){
				if(y <= 225 && y >= 165) {
					side = !side;
				}
				else {vx *= -1;}
			}
		}
		else {
			if(side && x >= 240 && vx > 0) {vx *= -1;}
			else if(!side && x <= 250 && vx < 0) {vx *= -1;}
		}
		
	}
	
	public void drawMe(Graphics g) {
		if(getSpeed() < 4) {
			g.setColor(Color.blue);
		}
		else 
			g.setColor(Color.red);
		
		g.fillOval(x, y, 10, 10);
	}
	
	public double getSpeed() {
		return Math.sqrt((Math.pow(vx, 2)) + (Math.pow(vy, 2)));
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public double getVx() {
		return vx;
	}
	
	public double getVy() {
		return vy;
	}
}
