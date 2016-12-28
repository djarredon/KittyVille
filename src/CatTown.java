import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
	protected int food, happiness, science, magic, popCap;

	Timer timer;
	protected int xMax, yMax;
	private Container c;
	
	public CatTown(String name) {
		super(name);
		this.name = name;
		this.xMax = 700;
		this.yMax = 700;
		setSize(this.xMax, this.yMax);
		
		population = 3;
		popCap = 10;
		unemployed = new CatList(population);

		happiness = startingHappiness = 3;
		
		hunt = new Building("Hunting Grounds");

		nipFarm = electrician = mageTower = toymaker
				= architect = scienceLab 
				= null;

		c = null;
		setLayout(null);

		timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openScreen();
			}
		});

		screenOne();
		//openScreen();
	}

	private void screenOne() {
		timer.start();
	}
	
	private void openScreen() {
		repaint();
		getContentPane().removeAll();

		/*
		food
			-.5 food per cat
		happiness
			-.1 happiness per cat
			-.5 happiness if food < 0
		science
		magic
		population cap
		 */
		food -= .5 * population;
		happiness -= .1 * population;
		if (food < 0)
			happiness -= .5;



		c = getContentPane();
		c.add(resources());
		c.add(Hunt(true));
		c.add(Farm(nipFarm != null));


		c.add(catArea());
		
	}

	/*
	 *		  Cat population
	 *		  population max
	 *		  [0] = food
	 * 		  [1] = happiness
	 * 		  [2] = science
	 * 		  [3] = magic
	 * 		  [4] = population cap
	 * 		  food, happiness, science, magic, popCap;
	 */
	private JPanel resources() {
		JPanel resourcePanel = new JPanel(null);
		resourcePanel.setSize(600, 60);
		resourcePanel.setLocation(10,10);
		resourcePanel.setBackground(Color.LIGHT_GRAY);

		JTextField popCount = new JTextField("Population: " + population);
		popCount.setEditable(false);
		popCount.setSize(100, 20);
		popCount.setLocation(10,10);

		JTextField unemployedCount = new JTextField("Unemployed: " + unemployed.getCount());
		unemployedCount.setEditable(false);
		unemployedCount.setSize(100, 20);
		unemployedCount.setLocation(10, 30);

		JTextField popCapCount = new JTextField("Population Max: " + popCap);
		popCapCount.setEditable(false);
		popCapCount.setSize(150, 20);
		popCapCount.setLocation(120, 10);

		JTextField foodCount = new JTextField("Food: " + food);
		foodCount.setEditable(false);
		foodCount.setSize(100, 20);
		foodCount.setLocation(280,10);

		JTextField happyCount = new JTextField("Happiness: " + happiness);
		happyCount.setEditable(false);
		happyCount.setSize(100, 20);
		happyCount.setLocation(280, 30);

		JTextField scienceCount = new JTextField("Science: " + science);
		scienceCount.setEditable(false);
		scienceCount.setSize(100, 20);
		scienceCount.setLocation(390, 10);

		JTextField magicCount = new JTextField("Magic: " + magic);
		magicCount.setEditable(false);
		magicCount.setSize(100, 20);
		magicCount.setLocation(390, 30);


		resourcePanel.add(popCount);
		resourcePanel.add(unemployedCount);
		resourcePanel.add(popCapCount);

		resourcePanel.add(foodCount);
		resourcePanel.add(happyCount);
		resourcePanel.add(scienceCount);
		resourcePanel.add(magicCount);

		return resourcePanel;
	}

	private JPanel catArea() {
		JPanel catPanel = new JPanel(null);
		catPanel.setSize(200, 200);
		catPanel.setLocation(240, 320);
		catPanel.setBackground(Color.LIGHT_GRAY);

		JButton buyCat = new JButton("Recruit Cat");
		buyCat.setLocation(30,60);
		buyCat.setSize(150, 30);

		JTextField buyCatDescription = new JTextField("  Cats cost 100 food each");
		buyCatDescription.setEditable(false);
		buyCatDescription.setLocation(30, 100);
		buyCatDescription.setSize(150, 30);


		catPanel.add(buyCat);
		catPanel.add(buyCatDescription);

		buyCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (food >= 100 && population + 1 <= popCap) {
					food -= 100;
					unemployed.addCat(1);
					++population;
					openScreen();
				}
			}
		});

		return catPanel;
	}

	private JPanel Farm(boolean activated) {
		JPanel farmPanel = new JPanel(null);
		farmPanel.setSize(200,200);
		farmPanel.setLocation(240,100);
		farmPanel.setBackground(Color.cyan);

		if (!activated) {
			Building temp = new Building("Catnip Farm");
			JButton buyFarm = new JButton("Buy Catnip Farm");
			//buyFarm.
			buyFarm.setLocation(30, 60);
			buyFarm.setSize(150, 30);


			farmPanel.add(buyFarm);

			buyFarm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (food >= 100) {
						nipFarm = new Building("Catnip Farm");
						food -= nipFarm.getCost();
						openScreen();
					}
				}
			});
		}
		else {
			JTextField farmText = new JTextField(nipFarm.getName());
			farmText.setSize(150,20);
			farmText.setLocation(10,10);
			farmText.setEditable(false);

			JTextField workerText = new JTextField("Farmers: " + nipFarm.getCount());
			workerText.setEditable(false);
			workerText.setSize(150, 20);
			workerText.setLocation(10, 30);

			double[] array = nipFarm.getOutput();
			JTextArea outputText;
			if (array == null)
				outputText = new JTextArea("Resources per tick: 0" );
			else
				outputText = new JTextArea("Resources per tick:\n" + arrayToString(array) );

			outputText.setEditable(false);
			outputText.setSize(150, 100);
			outputText.setLocation(10, 50);


			farmPanel.add(workerText);
			farmPanel.add(outputText);


			JButton addCat = new JButton("Add Cat");
			addCat.setLocation(10, 160);
			addCat.setSize(80, 30);

			JButton removeFarmCat = new JButton("Remove Cat");
			removeFarmCat.setLocation(100, 160);
			removeFarmCat.setSize(80,30);



			farmPanel.add(farmText);
			farmPanel.add(addCat);
			farmPanel.add(removeFarmCat);


			double [] temp = nipFarm.getOutput();
			food += temp[0];
			happiness += temp[1];
			science += temp[2];
			magic += temp[3];
			popCap += temp[4];


			addCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (unemployed != null && unemployed.getCount() > 0)
						nipFarm.addCat(unemployed.removeCat());
					openScreen();
				}
			});

		}

		return farmPanel;
	}
	
	private JPanel Hunt(boolean activated) {
		JPanel huntPanel = new JPanel(null);
		huntPanel.setSize(200,200);
		huntPanel.setLocation(20,100);
		huntPanel.setBackground(Color.GREEN);


		JTextField huntText = new JTextField(hunt.getName());
		huntText.setSize(150,20);
		huntText.setLocation(10,10);
		huntText.setEditable(false);

		if (hunt != null) {
			JTextField workerText = new JTextField("Hunters: " + hunt.getCount());
			workerText.setEditable(false);
			workerText.setSize(150, 20);
			workerText.setLocation(10, 30);

			double[] array = hunt.getOutput();
			JTextArea outputText;
			if (array == null)
				outputText = new JTextArea("Resources per tick: 0" );
			else
				outputText = new JTextArea("Resources per tick:\n" + arrayToString(array) );

			outputText.setEditable(false);
			outputText.setSize(150, 100);
			outputText.setLocation(10, 50);


			huntPanel.add(workerText);
			huntPanel.add(outputText);
		}

		JButton addCat = new JButton("Add Cat");
		addCat.setLocation(10, 160);
		addCat.setSize(80, 30);

		JButton removeHuntCat = new JButton("Remove Cat");
		removeHuntCat.setLocation(100, 160);
		removeHuntCat.setSize(80,30);



		huntPanel.add(huntText);
		huntPanel.add(addCat);
		huntPanel.add(removeHuntCat);

		double [] temp = hunt.getOutput();
		food += temp[0];
		happiness += temp[1];
		science += temp[2];
		magic += temp[3];
		popCap += temp[4];


		addCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (unemployed != null && unemployed.getCount() > 0)
					hunt.addCat(unemployed.removeCat());
				openScreen();
			}
		});

		return huntPanel;
	}

	/*
	 * output [0] = food
	 * 		  [1] = happiness
	 * 		  [2] = science
	 * 		  [3] = magic
	 * 		  [4] = population cap
	 */
	private String arrayToString(double[] array) {
		if (array == null)
			return "";
		if (array.length != hunt.resourceNum)
			return "";
		String toReturn = "   Food: " + array[0] + "\n" +
				"   Happiness: " + array[1] + "\n" +
				"   Science: " + array[2] + "\n" +
				"   Magic: " + array[3] + "\n" +
				"   Population: " + array[4];

		return toReturn;
	}
	
	

}
