import javax.swing.*;
import java.awt.*;
import java.util.*;

public class GamePanel extends JPanel{
	Particle[] hotParts = new Particle[100];
	Particle[] coldParts = new Particle[100];
	int partCount = 0;
	boolean open;
	
	
	public void draw() {
		repaint();
	} 
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		//gamespace
		g.setColor(Color.white);
		g.fillRect(20, 0, 460, 400);
		
		//barrier
		g.setColor(Color.black);
		g.fillRect(249, 0, 2, 400);
		
		//door
		if(open) {
			g.setColor(Color.white);
			g.fillRect(249, 160, 2, 80);
		}
		else {
			g.setColor(Color.green);
			g.fillRect(249, 160, 2, 80);
		}
		
		//paint particles
			for(int i=0; i < partCount/2; i++) {
				hotParts[i].drawMe(g);
				coldParts[i].drawMe(g);
			}
	}
	
	public void moveParticles() {
		for(int i = 0; i < partCount/2; i++) {
			hotParts[i].move(open);
			coldParts[i].move(open);
		}
		
	}

}
