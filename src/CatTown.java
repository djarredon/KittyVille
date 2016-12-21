import java.awt.*;
import javax.swing.*;

public class CatTown extends JFrame{
	protected String name;			//name of town
	protected int population;		//population count
	protected CatList unemployed;	//cats not employed
	//buildings
	protected Building hunt, nipFarm, electrician, mageTower,
						toymaker, architect, scienceLab;

	//resources
	protected int startingHappiness;
	protected int food, happiness, science, magic;
	
	protected int xMax, yMax;
	private Container c;
	
	public CatTown(String name) {
		super(name);
		this.name = name;
		this.xMax = 400;
		this.yMax = 400;
		setSize(this.xMax, this.yMax);
		
		population = 3;
		happiness = 0;
		startingHappiness = 3;
		
		hunt = new Building("Hunter Stuff");
		
		nipFarm = electrician = mageTower = toymaker
				= architect = scienceLab 
				= null;
		
		c = null;
		setLayout(null);
		openScreen();
	}
	
	private void openScreen() {
		repaint();
		getContentPane().removeAll();
		
		JLabel label = new JLabel("Hello World!");
		label.setSize(100, 20);
		label.setLocation(20,20);
		
		c = getContentPane();
		c.add(label);
		
	}
	
	private JPanel Hunt(boolean activated) {
		JPanel huntPanel = new JPanel();

		if (activated) {
			huntPanel.setForeground(Color.BLUE);
		}


		return huntPanel;
	}
	
	

}
