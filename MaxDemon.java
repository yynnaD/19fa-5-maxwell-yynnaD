import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Arrays;

public class MaxDemon implements ActionListener, MouseListener {
	JButton addParts, reset;
	static GamePanel game;
	static JLabel temp1, temp2;
	static double t1, t2; //temperature
	Timer t = new Timer(5, this);
	public MaxDemon() {
		t.start();
		
		JFrame frame = new JFrame("Maxwell's Demon");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLayout(null);
		frame.addMouseListener(this);
		
		game = new GamePanel();
		frame.add(game);
		game.setBounds(0,0,500,400);
		game.draw();
		
		addParts = new JButton("Add Particles");
		frame.add(addParts);
		addParts.setBounds(130, 500, 100, 50);
		addParts.addActionListener(this);
		
		reset = new JButton("Reset");
		frame.add(reset);
		reset.setBounds(260,500, 100,50);
		reset.addActionListener(this);
		
		temp1 = new JLabel();
		temp1.setText("Temperature: " + String.valueOf(t1));
		temp1.setBounds(65, 400, 200, 50);
		frame.add(temp1);
		
		temp2 = new JLabel();
		temp2.setText("Temperature: " + String.valueOf(t2));
		temp2.setBounds(300, 400, 200, 50);
		frame.add(temp2);
		
		frame.setSize(500,600);
		frame.setVisible(true);
	}
	
	public static void main(String[]args) {
		
		new MaxDemon();
		
		Thread repaint = new Thread() {
			@Override
			public void run(){
				try {
					while(true) {
						game.moveParticles();
						game.repaint();
						calculateTemp();
						Thread.sleep(25);
					}
				}
				catch(InterruptedException e) {e.printStackTrace();}
			}
		};
		repaint.start();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if(e.getSource() == addParts) { addParticles();	}
		else if(e.getSource() == reset) {resetGame();}
		
	}

	private void resetGame() {
		Arrays.fill(game.hotParts, null);
		Arrays.fill(game.coldParts, null);
		game.partCount = 0;
		game.removeAll();
		t1 = 0.0;
		t2 = 0.0;
		
	}

	private void addParticles() { 
		double angle = 2 * Math.PI * Math.random();
		Particle p = new Particle(200, 175,Math.cos(angle)*(Math.random()*3 + 5) , Math.sin(angle)*(Math.random()*3 +5), true); //x,y,vx,vy
		angle = 2 * Math.PI * Math.random();
		Particle p2 = new Particle(200,175, Math.cos(angle)*(Math.random()*1 + 1.8), Math.sin(angle)* (Math.random()*1 + 1.8), true);
		game.hotParts[game.partCount/2] = p;
		game.coldParts[game.partCount/2]= p2;
		
		angle = 2 * Math.PI * Math.random();
		Particle p3 = new Particle(300, 175,Math.cos(angle)*(Math.random()*3 + 5),Math.sin(angle)*(Math.random()*3 +5), false); 
		angle = 2 * Math.PI * Math.random();
		Particle p4 = new Particle(300, 175,Math.cos(angle)*(Math.random()*1 + 1.8),Math.sin(angle)* (Math.random()*1 + 1.8), false);
		game.hotParts[game.partCount/2+1] = p3;
		game.coldParts[game.partCount/2 +1]= p4;
		
		game.partCount+=4;
		game.repaint();
	}
	
	public static void calculateTemp() {
		int left = 0, right = 0;
		for(int i = 0; i < game.partCount/2; i++) {
			if(game.hotParts[i].side) {
				t1 += Math.pow(game.hotParts[i].getSpeed(), 2);
				left++;
			}
			else {
				t2 += Math.pow(game.hotParts[i].getSpeed(), 2);
				right++;
			}
			if(game.coldParts[i].side) {
				t1 += Math.pow(game.coldParts[i].getSpeed(), 2);
				left++;
			}
			else {
				t2 += Math.pow(game.coldParts[i].getSpeed(), 2);
				right++;
			}
		}
		
		
		t1 /= left;
		t2 /= right;
		
		t1 = Math.round(t1*10)/10.0;
		t2 = Math.round(t2*10)/10.0;
		
		temp1.setText("Temperature: " + t1);
		temp2.setText("Temperature: " + t2);
		
		left = 0;
		right = 0;
		t1 = 0;
		t2 = 0;
	}

	@Override
	public void mouseClicked(MouseEvent e) {}

	@Override
	public void mousePressed(MouseEvent e) {
		game.open = true;
		System.out.println("clicked at x: " + e.getX() + " y: " + e.getY());
	}

	@Override
	public void mouseReleased(MouseEvent e) {
		game.open = false;
		
	}

	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}

}
