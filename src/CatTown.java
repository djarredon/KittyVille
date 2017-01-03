import javafx.geometry.HPos;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
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
	protected double food, happiness, science, magic, popCap;
	protected double[] perSecond;

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

		happiness = startingHappiness = 10;
		food = 50;

		perSecond = new double[5];
		for (int i = 0; i < 5; ++i)
			perSecond[i] = 0;
		
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

		//get base resources per tick
		for (int i = 0; i < 5; ++i) {
			if (hunt != null)
				perSecond[i] = hunt.getOutput()[i];
			if (nipFarm != null)
				perSecond[i] += nipFarm.getOutput()[i];
			if (electrician != null)
				perSecond[i] += electrician.getOutput()[i];
			if (mageTower != null)
				perSecond[i] += mageTower.getOutput()[i];
			if (toymaker != null)
				perSecond[i] += toymaker.getOutput()[i];
			if (architect != null)
				perSecond[i] += architect.getOutput()[i];
			if (scienceLab != null)
				perSecond[i] += scienceLab.getOutput()[i];
		}
		//apply penalties
		perSecond[0] -= .5 * population;	//food per cat
		perSecond[1] -= .01 * population;	//happiness per cat
		//apply resources
		food += perSecond[0];
		happiness += perSecond[1];
		if (food < 0)
			happiness += perSecond[1];
		science += perSecond[2];
		magic += perSecond[3];
		popCap += perSecond[4];

		c = getContentPane();
		c.add(resources());
		c.add(Hunt(true));
		c.add(Farm(nipFarm != null));
		c.add(ScienceLab(scienceLab != null));
		c.add(ArchitectFirm(architect != null));


		c.add(catArea());
		
	}

	private void techScreen() {
		//On this screen the player can buy tech upgrades.

		repaint();
		getContentPane().removeAll();

		Dimension buttonSize = new Dimension(200, 30);

		//back button
		JButton back = new JButton("Back");
		back.setLocation(20, 20);
		back.setSize(buttonSize);

		//buttons for upgrades
		JButton upgradeHunt = new JButton("Upgrade Hunt");
		upgradeHunt.setLocation(20, 100);





		c = getContentPane();

		c.add(back);



		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				openScreen();
			}
		});

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
		resourcePanel.setSize(650, 60);
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

		JTextField popCapCount = new JTextField("Population Max: " + format(popCap));
		popCapCount.setEditable(false);
		popCapCount.setSize(150, 20);
		popCapCount.setLocation(120, 10);

		JTextField foodCount = new JTextField("Food: " + format(food)
				+ "  per tick: " + format(perSecond[0]));
		foodCount.setEditable(false);
		foodCount.setSize(200, 20);
		foodCount.setLocation(280,10);

		JTextField happyCount = new JTextField("Happiness: " + format(happiness)
				+ "  per tick: " + format(perSecond[1]));
		happyCount.setEditable(false);
		happyCount.setSize(200, 20);
		happyCount.setLocation(280, 30);

		JTextField scienceCount = new JTextField("Science: " + format(science)
				+ "  per tick: " + format(perSecond[2]));
		scienceCount.setEditable(false);
		scienceCount.setSize(150, 20);
		scienceCount.setLocation(490, 10);

		JTextField magicCount = new JTextField("Magic: " + format(magic)
				+ "  per tick: " + format(perSecond[3]));
		magicCount.setEditable(false);
		magicCount.setSize(150, 20);
		magicCount.setLocation(490, 30);


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

		JButton techButton = new JButton("Tech Tree");
		techButton.setSize(150, 30);
		techButton.setLocation(30, 160);


		catPanel.add(buyCat);
		catPanel.add(buyCatDescription);
		catPanel.add(techButton);

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

		techButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				techScreen();
			}
		});

		return catPanel;
	}

	private JPanel ArchitectFirm(boolean activated) {
		JPanel architectPanel = new JPanel(null);
		architectPanel.setSize(200, 200);
		architectPanel.setLocation(20, 320);
		architectPanel.setBackground(Color.MAGENTA);

		if (!activated) {
			//show purchase screen
			Building temp = new Building("Architecture Firm");
			JButton buyFirm = new JButton("Buy Architecture Firm");
			buyFirm.setLocation(20, 60);
			buyFirm.setSize(170, 30);

			JTextField firmCost = new JTextField("  " + temp.getCost() + " Food");
			firmCost.setEditable(false);
			firmCost.setLocation(75, 100);
			firmCost.setSize(75, 30);

			architectPanel.add(buyFirm);
			architectPanel.add(firmCost);

			buyFirm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (food >= temp.getCost()) {
						architect = new Building("Architecture Firm");
						food -= architect.getCost();
						openScreen();
					}
				}
			});
		}
		else {
			//show Science Details
			JTextField firmText = new JTextField(architect.getName());
			firmText.setSize(150,20);
			firmText.setLocation(10,10);
			firmText.setEditable(false);

			JTextField drafterText = new JTextField("Architects: " + architect.getCount());
			drafterText.setEditable(false);
			drafterText.setSize(150, 20);
			drafterText.setLocation(10, 30);

			double[] array = architect.getOutput();
			JTextArea outputText;
			if (array == null)
				outputText = new JTextArea("Resources per tick: 0" );
			else
				outputText = new JTextArea("Resources per tick:\n" + arrayToString(array) );

			outputText.setEditable(false);
			outputText.setSize(150, 100);
			outputText.setLocation(10, 50);


			architectPanel.add(drafterText);
			architectPanel.add(outputText);


			JButton addArchitectCat = new JButton("Add Cat");
			addArchitectCat.setLocation(10, 160);
			addArchitectCat.setSize(80, 30);

			JButton removeArchitectCat = new JButton("Remove");
			removeArchitectCat.setLocation(100, 160);
			removeArchitectCat.setSize(80,30);



			architectPanel.add(firmText);
			architectPanel.add(addArchitectCat);
			architectPanel.add(removeArchitectCat);

			addArchitectCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (unemployed != null && unemployed.getCount() > 0)
						architect.addCat(unemployed.removeCat());
					openScreen();
				}
			});

			removeArchitectCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (architect.hasCats()) {
						if (unemployed == null)
							unemployed = new CatList(architect.removeCat());
						else
							unemployed.addCat(architect.removeCat());
					}
				}
			});

		}


		return architectPanel;
	}

	private JPanel ScienceLab(boolean activated) {
		JPanel sciencePanel = new JPanel(null);
		sciencePanel.setSize(200,200);
		sciencePanel.setLocation(460,100);
		sciencePanel.setBackground(Color.blue);

		if (!activated) {
			//show purchase screen
			Building temp = new Building("Science Lab");
			JButton buyLab = new JButton("Buy Science Lab");
			buyLab.setLocation(30, 60);
			buyLab.setSize(150, 30);

			JTextField labCost = new JTextField("  " + temp.getCost() + " Food");
			labCost.setEditable(false);
			labCost.setLocation(75, 100);
			labCost.setSize(75, 30);

			sciencePanel.add(buyLab);
			sciencePanel.add(labCost);

			buyLab.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (food >= temp.getCost()) {
						scienceLab = new Building("Science Lab");
						food -= scienceLab.getCost();
						openScreen();
					}
				}
			});
		}
		else {
			//show Science Details
			JTextField labText = new JTextField(scienceLab.getName());
			labText.setSize(150,20);
			labText.setLocation(10,10);
			labText.setEditable(false);

			JTextField researcherText = new JTextField("Scientists: " + scienceLab.getCount());
			researcherText.setEditable(false);
			researcherText.setSize(150, 20);
			researcherText.setLocation(10, 30);

			double[] array = scienceLab.getOutput();
			JTextArea outputText;
			if (array == null)
				outputText = new JTextArea("Resources per tick: 0" );
			else
				outputText = new JTextArea("Resources per tick:\n" + arrayToString(array) );

			outputText.setEditable(false);
			outputText.setSize(150, 100);
			outputText.setLocation(10, 50);


			sciencePanel.add(researcherText);
			sciencePanel.add(outputText);


			JButton addLabCat = new JButton("Add Cat");
			addLabCat.setLocation(10, 160);
			addLabCat.setSize(80, 30);

			JButton removeLabCat = new JButton("Remove");
			removeLabCat.setLocation(100, 160);
			removeLabCat.setSize(80,30);



			sciencePanel.add(labText);
			sciencePanel.add(addLabCat);
			sciencePanel.add(removeLabCat);

			addLabCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (unemployed != null && unemployed.getCount() > 0)
						scienceLab.addCat(unemployed.removeCat());
					openScreen();
				}
			});

			removeLabCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (scienceLab.hasCats()) {
						if (unemployed == null)
							unemployed = new CatList(scienceLab.removeCat());
						else
							unemployed.addCat(scienceLab.removeCat());
					}
				}
			});

		}


		return sciencePanel;
	}

	private JPanel Farm(boolean activated) {
		JPanel farmPanel = new JPanel(null);
		farmPanel.setSize(200,200);
		farmPanel.setLocation(240,100);
		farmPanel.setBackground(Color.cyan);

		if (!activated) {
			Building temp = new Building("Catnip Farm");
			JButton buyFarm = new JButton("Buy Catnip Farm");
			buyFarm.setLocation(30, 60);
			buyFarm.setSize(150, 30);

			JTextField farmCost = new JTextField("   " +temp.getCost() + " Food");
			farmCost.setEditable(false);
			farmCost.setLocation(75, 100);
			farmCost.setSize(75, 30);


			farmPanel.add(buyFarm);
			farmPanel.add(farmCost);

			buyFarm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (food >= temp.getCost()) {
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

			JButton removeFarmCat = new JButton("Remove");
			removeFarmCat.setLocation(100, 160);
			removeFarmCat.setSize(80,30);



			farmPanel.add(farmText);
			farmPanel.add(addCat);
			farmPanel.add(removeFarmCat);

			addCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (unemployed != null && unemployed.getCount() > 0)
						nipFarm.addCat(unemployed.removeCat());
					openScreen();
				}
			});

			removeFarmCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (nipFarm.hasCats()) {
						if (unemployed == null)
							unemployed = new CatList(nipFarm.removeCat());
						else
							unemployed.addCat(nipFarm.removeCat());
					}
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

		JButton removeHuntCat = new JButton("Remove");
		removeHuntCat.setLocation(100, 160);
		removeHuntCat.setSize(80,30);



		huntPanel.add(huntText);
		huntPanel.add(addCat);
		huntPanel.add(removeHuntCat);

		addCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (unemployed != null && unemployed.getCount() > 0)
					hunt.addCat(unemployed.removeCat());
				openScreen();
			}
		});

		removeHuntCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (hunt.hasCats()) {
					if (unemployed == null)
						unemployed = new CatList(hunt.removeCat());
					else
						unemployed.addCat(hunt.removeCat());
				}
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
		String toReturn = "   Food: " + format(array[0]) + "\n" +
				"   Happiness: " + format(array[1]) + "\n" +
				"   Science: " + format(array[2]) + "\n" +
				"   Magic: " + format(array[3]) + "\n" +
				"   Population: " + format(array[4]);

		return toReturn;
	}

	private double format(double in) {
		DecimalFormat twoDecimals = new DecimalFormat("#.##");
		return Double.valueOf(twoDecimals.format(in));
		/*
		int temp = (int) (100 * in);
		in = (double) (temp / 100);
		return in;
		*/
	}
	
	

}
