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
	protected double food, happiness, science, magic, popCap;
	protected double[] perTick;

	//
	protected int screenNum;
	protected Timer timer;
	protected int tick;
	protected int xMax, yMax;
	protected Dimension textFieldSize = new Dimension(150, 20);
	protected Dimension textAreaSize = new Dimension(150, 100);
	private Container c;
	
	public CatTown(String name) {
		super(name);
		//screen size and name
		this.name = name;
		this.xMax = 1000;
		this.yMax = 900;
		setSize(this.xMax, this.yMax);

		//starting values
		population = 3;
		popCap = 12;
		unemployed = new CatList();
		happiness = 12;
		food = 120;

		//initialize perTick values
		perTick = new double[5];
		for (int i = 0; i < 5; ++i)
			perTick[i] = 0;

		//initialize buildings
		hunt = new Building("Hunting Grounds");
		hunt.addCatList(new CatList(population));
		nipFarm = electrician = mageTower = toymaker
				= architect = scienceLab 
				= null;

		//initialize screen
		c = null;
		setLayout(null);

		//refresh rate
		tick = 0;
		timer = new Timer(100, new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				++tick;
				choosePage();
			}
		});

		//start program
		timer.start();
	}

	private void choosePage() {
		/*
		screenNum
			0 = main screen
			1 = tech screen
			2 = magic screen
		 */
		switch (screenNum) {
			case 0:
				mainScreen();
				break;
			case 1:
				techScreen();
				break;
			case 2:
				magicScreen();
				break;
			default:
				mainScreen();
		}
	}
	
	private void mainScreen() {
		repaint();
		getContentPane().removeAll();


		c = getContentPane();
		c.add(resources());
		c.add(Hunt(true));
		c.add(Farm(nipFarm != null));
		c.add(ScienceLab(scienceLab != null));
		c.add(ArchitectFirm(architect != null));
		c.add(MagicTower(mageTower != null));

		c.add(catArea());
	}

	private void techScreen() {
		//On this screen the player can buy tech upgrades.
		repaint();
		getContentPane().removeAll();
		c = getContentPane();

		Dimension buttonSize = new Dimension(300, 30);

		//back button
		JButton back = new JButton("Back");
		back.setLocation(xMax/2 - buttonSize.width/2, yMax - buttonSize.height - 50);
		back.setSize(buttonSize);

		//buttons for upgrades
		JButton upgradeHunt = new JButton("Upgrade Hunt (Level "
				+ hunt.getUpgradeLevel() + ")  Cost: " + hunt.getUpgradeCost() );
		if (science < hunt.getUpgradeCost())
			upgradeHunt.setForeground(Color.red);
		upgradeHunt.setLocation(20, 100);
		upgradeHunt.setSize(buttonSize);



		c.add(resources());
		c.add(back);
		//upgrade buttons
		c.add(upgradeHunt);

		//upgrade farm output
		if (nipFarm != null) {
			JButton upgradeFarm = new JButton("Upgrade Farm (Level "
					+ nipFarm.getUpgradeLevel() + ") Cost: " + nipFarm.getUpgradeCost());
			if (science < nipFarm.getUpgradeCost())
				upgradeFarm.setForeground(Color.red);
			upgradeFarm.setLocation(20, 150);
			upgradeFarm.setSize(buttonSize);

			c.add(upgradeFarm);

			upgradeFarm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int temp = nipFarm.getUpgradeCost();
					if (science >= temp) {
						if (nipFarm.upgrade())
							science -= temp;
					}
				}
			});
		}
		//upgrade science output
		if (scienceLab != null) {
			JButton upgradeLab = new JButton("Upgrade Lab (Level "
					+ scienceLab.getUpgradeLevel() + ") Cost: " + scienceLab.getUpgradeCost());
			if (science < scienceLab.getUpgradeCost())
				upgradeLab.setForeground(Color.red);
			upgradeLab.setLocation(20, 200);
			upgradeLab.setSize(buttonSize);

			c.add(upgradeLab);

			upgradeLab.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int temp = scienceLab.getUpgradeCost();
					if (science >= temp) {
						if (scienceLab.upgrade())
							science -= temp;
					}
				}
			});
		}
		//upgrade Mage Tower
		if (mageTower != null) {
			JButton upgradeTower = new JButton("Upgrade Tower (Level "
					+ mageTower.getUpgradeLevel() + ") Cost: " + mageTower.getUpgradeCost());
			if (science < mageTower.getUpgradeCost())
				upgradeTower.setForeground(Color.red);
			upgradeTower.setLocation(20, 250);
			upgradeTower.setSize(buttonSize);

			c.add(upgradeTower);

			upgradeTower.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					int temp = mageTower.getUpgradeCost();
					if (science >= temp) {
						if (mageTower.upgrade())
							science -= temp;
					}
				}
			});
		}

		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				screenNum = 0;
			}
		});

		upgradeHunt.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int temp = hunt.getUpgradeCost();
				if (science >= temp) {
					if (hunt.upgrade())
						science -= temp;
				}
			}
		});

	}

	/*
		Spells
			* Summon megacat
			* Summon food
			* Create Happiness
			*
	 */
	private void magicScreen() {
		//cast spells
		repaint();
		getContentPane().removeAll();

		Dimension buttonSize = new Dimension(300, 30);

		//back button
		JButton back = new JButton("Back");
		back.setLocation(xMax/2 - buttonSize.width/2, yMax - buttonSize.height - 50);
		back.setSize(buttonSize);

		//buttons for spells



		c = getContentPane();

		c.add(back);
		c.add(resources());


		back.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				screenNum = 0;
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
		if (tick % 3 == 0)
			calculateResources();

		//GUI
		JPanel resourcePanel = new JPanel(null);
		resourcePanel.setSize(xMax - 50, 60);
		resourcePanel.setLocation(10,10);
		resourcePanel.setBackground(Color.LIGHT_GRAY);

		JTextField popCount = new JTextField("Population: " + population);
		popCount.setEditable(false);
		popCount.setSize(120, 20);
		popCount.setLocation(10,10);

		JTextField unemployedCount = new JTextField("Unemployed: " + unemployed.getCount());
		unemployedCount.setEditable(false);
		unemployedCount.setSize(120, 20);
		unemployedCount.setLocation(10, 30);

		JTextField popCapCount = new JTextField("Population Max: " + format(popCap));
		popCapCount.setEditable(false);
		popCapCount.setSize(170, 20);
		popCapCount.setLocation(140, 10);

		//food count and per tick
		JTextField foodCount = new JTextField("Food: " + format(food));
		foodCount.setEditable(false);
		foodCount.setSize(130, 20);
		foodCount.setLocation(320,10);
		JTextField foodTick = new JTextField("per tick: " + format(perTick[0]));
		foodTick.setEditable(false);
		foodTick.setSize(90, 20);
		foodTick.setLocation(450, 10);

		//happy count and per tick
		JTextField happyCount = new JTextField("Happiness: " + format(happiness));
		happyCount.setEditable(false);
		happyCount.setSize(130, 20);
		happyCount.setLocation(320, 30);
		JTextField happyTick = new JTextField("per tick: " + format(perTick[1]));
		happyTick.setEditable(false);
		happyTick.setSize(90,20);
		happyTick.setLocation(450, 30);

		//science count and per tick
		JTextField scienceCount = new JTextField("Science: " + format(science));
		scienceCount.setEditable(false);
		scienceCount.setSize(130, 20);
		scienceCount.setLocation(550, 10);
		JTextField scienceTick = new JTextField("per tick: " + format(perTick[2]));
		scienceTick.setEditable(false);
		scienceTick.setSize(90,20);
		scienceTick.setLocation(680, 10);

		//magic count and per tick
		JTextField magicCount = new JTextField("Magic: " + format(magic));
		magicCount.setEditable(false);
		magicCount.setSize(130, 20);
		magicCount.setLocation(550, 30);
		JTextField magicTick = new JTextField("per tick: " + format(perTick[3]));
		magicTick.setEditable(false);
		magicTick.setSize(90,20);
		magicTick.setLocation(680, 30);


		resourcePanel.add(popCount);
		resourcePanel.add(unemployedCount);
		resourcePanel.add(popCapCount);
		resourcePanel.add(foodCount);
		resourcePanel.add(foodTick);
		resourcePanel.add(happyCount);
		resourcePanel.add(happyTick);
		resourcePanel.add(scienceCount);
		resourcePanel.add(scienceTick);
		resourcePanel.add(magicCount);
		resourcePanel.add(magicTick);

		return resourcePanel;
	}

	private JPanel catArea() {
		JPanel catPanel = new JPanel(null);
		catPanel.setSize(300, 200);
		catPanel.setLocation(340, 320);
		catPanel.setBackground(Color.LIGHT_GRAY);

		JButton buyCat = new JButton("Recruit Cat (100 Food)");
		buyCat.setLocation(25,10);
		buyCat.setSize(175, 30);

		JButton buyTenCats = new JButton("x10");
		if (food < 1000 || popCap - population <= 10)
			buyTenCats.setForeground(Color.red);
		buyTenCats.setLocation(200, 10);
		buyTenCats.setSize(75, 30);

		catPanel.add(buyCat);
		catPanel.add(buyTenCats);

		if (scienceLab != null) {
			JButton techButton = new JButton("Tech Tree");
			techButton.setSize(250, 30);
			techButton.setLocation(25, 50);

			catPanel.add(techButton);

			techButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					screenNum = 1;
				}
			});
		}

		if (mageTower != null) {
			JButton magicButton = new JButton("Spells");
			magicButton.setSize(250, 30);
			magicButton.setLocation(25, 90);

			catPanel.add(magicButton);

			magicButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					screenNum = 2;
				}
			});
		}

		buyCat.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (food >= 100 && population + 1 <= popCap) {
					food -= 100;
					unemployed.addCat(1);
					++population;
				}
			}
		});

		buyTenCats.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				if (food >= 1000 && population + 10 < popCap) {
					food -= 1000;
					unemployed.addCat(10);
					population += 10;
				}
			}
		});

		return catPanel;
	}

	private JPanel MagicTower(boolean activated) {
		JPanel magePanel = new JPanel(null);
		magePanel.setSize(300, 200);
		magePanel.setLocation(660, 320);
		magePanel.setBackground(Color.cyan);

		if (!activated) {
			//purchase screen
			Building temp = new Building("Mage Tower");
			JButton buyMage = new JButton("Buy Mage Tower ("
					+ temp.getCost() + " Food)");
			buyMage.setLocation(25, 60);
			buyMage.setSize(250, 30);

			magePanel.add(buyMage);

			buyMage.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (food >= temp.getCost()) {
						mageTower = new Building("Mage Tower");
						food -= mageTower.getCost();
					}
				}
			});
		}
		else {
			//show Science Details
			JTextField towerText = new JTextField(mageTower.getName() + " Level "
					+ mageTower.getUpgradeLevel());
			towerText.setSize(textFieldSize);
			towerText.setLocation(10,10);
			towerText.setEditable(false);

			JTextField mageText = new JTextField("Mages: " + mageTower.getCount());
			mageText.setEditable(false);
			mageText.setSize(textFieldSize);
			mageText.setLocation(10, 30);

			double[] array = mageTower.getOutput();
			JTextArea outputText;
			if (array == null)
				outputText = new JTextArea("Resources per tick: 0" );
			else
				outputText = new JTextArea("Resources per tick:\n" + arrayToString(array) );

			outputText.setEditable(false);
			outputText.setSize(textAreaSize);
			outputText.setLocation(10, 50);


			magePanel.add(mageText);
			magePanel.add(outputText);


			JButton addMageCat = new JButton("Add Cat");
			addMageCat.setLocation(10, 160);
			addMageCat.setSize(80, 30);

			JButton removeMageCat = new JButton("Remove");
			removeMageCat.setLocation(100, 160);
			removeMageCat.setSize(80,30);

			magePanel.add(towerText);
			magePanel.add(addMageCat);
			magePanel.add(removeMageCat);

			addMageCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (unemployed != null && unemployed.getCount() > 0)
						mageTower.addCat(unemployed.removeCat());
				}
			});

			removeMageCat.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (mageTower.hasCats()) {
						if (unemployed == null)
							unemployed = new CatList(mageTower.removeCat());
						else
							unemployed.addCat(mageTower.removeCat());
					}
				}
			});

		}


		return magePanel;
	}

	private JPanel ArchitectFirm(boolean activated) {
		JPanel architectPanel = new JPanel(null);
		architectPanel.setSize(300, 200);
		architectPanel.setLocation(20, 320);
		architectPanel.setBackground(Color.MAGENTA);

		if (!activated) {
			//show purchase screen
			Building temp = new Building("Architecture Firm");
			JButton buyFirm = new JButton("Buy Architecture Firm ("
					+ temp.getCost() + " Food)");
			buyFirm.setLocation(25, 60);
			buyFirm.setSize(250, 30);

			architectPanel.add(buyFirm);

			buyFirm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (food >= temp.getCost()) {
						architect = new Building("Architecture Firm");
						food -= architect.getCost();
					}
				}
			});
		}
		else {
			//show Science Details
			JTextField firmText = new JTextField(architect.getName()
					+ " Level " + architect.getUpgradeLevel());
			firmText.setSize(textFieldSize);
			firmText.setLocation(10,10);
			firmText.setEditable(false);

			JTextField drafterText = new JTextField("Architects: " + architect.getCount());
			drafterText.setEditable(false);
			drafterText.setSize(textFieldSize);
			drafterText.setLocation(10, 30);

			double[] array = architect.getOutput();
			JTextArea outputText;
			if (array == null)
				outputText = new JTextArea("Resources per tick: 0" );
			else
				outputText = new JTextArea("Resources per tick:\n" + arrayToString(array) );

			outputText.setEditable(false);
			outputText.setSize(textAreaSize);
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
					//mainScreen();
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
		sciencePanel.setSize(300,200);
		sciencePanel.setLocation(660,100);
		sciencePanel.setBackground(Color.blue);

		if (!activated) {
			//show purchase screen
			Building temp = new Building("Science Lab");
			JButton buyLab = new JButton("Buy Science Lab ("
					+ temp.getCost() + " Food)");
			buyLab.setLocation(25, 60);
			buyLab.setSize(250, 30);

			sciencePanel.add(buyLab);

			buyLab.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (food >= temp.getCost()) {
						scienceLab = new Building("Science Lab");
						food -= scienceLab.getCost();
					}
				}
			});
		}
		else {
			//show Science Details
			JTextField labText = new JTextField(scienceLab.getName()
					+ " Level " + scienceLab.getUpgradeLevel());
			labText.setSize(textFieldSize);
			labText.setLocation(10,10);
			labText.setEditable(false);

			JTextField researcherText = new JTextField("Scientists: " + scienceLab.getCount());
			researcherText.setEditable(false);
			researcherText.setSize(textFieldSize);
			researcherText.setLocation(10, 30);

			double[] array = scienceLab.getOutput();
			JTextArea outputText;
			if (array == null)
				outputText = new JTextArea("Resources per tick: 0" );
			else
				outputText = new JTextArea("Resources per tick:\n" + arrayToString(array) );

			outputText.setEditable(false);
			outputText.setSize(textAreaSize);
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
					//mainScreen();
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
		farmPanel.setSize(300,200);
		farmPanel.setLocation(340,100);
		farmPanel.setBackground(Color.cyan);

		if (!activated) {
			Building temp = new Building("Catnip Farm");
			JButton buyFarm = new JButton("Buy Catnip Farm ("
					+ temp.getCost() + " Food)");
			buyFarm.setLocation(25, 60);
			buyFarm.setSize(250, 30);

			farmPanel.add(buyFarm);

			buyFarm.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					if (food >= temp.getCost()) {
						nipFarm = new Building("Catnip Farm");
						food -= nipFarm.getCost();
					}
				}
			});
		}
		else {
			JTextField farmText = new JTextField(nipFarm.getName()
					+ " Level " + nipFarm.getUpgradeLevel());
			farmText.setSize(textFieldSize);
			farmText.setLocation(10,10);
			farmText.setEditable(false);

			JTextField workerText = new JTextField("Farmers: " + nipFarm.getCount());
			workerText.setEditable(false);
			workerText.setSize(textFieldSize);
			workerText.setLocation(10, 30);

			double[] array = nipFarm.getOutput();
			JTextArea outputText;
			if (array == null)
				outputText = new JTextArea("Resources per tick: 0" );
			else
				outputText = new JTextArea("Resources per tick:\n" + arrayToString(array) );

			outputText.setEditable(false);
			outputText.setSize(textAreaSize);
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
					//mainScreen();
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
		huntPanel.setSize(300,200);
		huntPanel.setLocation(20,100);
		huntPanel.setBackground(Color.GREEN);


		JTextField huntText = new JTextField(hunt.getName()
				+ " Level " + hunt.getUpgradeLevel());
		huntText.setSize(textFieldSize);
		huntText.setLocation(10,10);
		huntText.setEditable(false);

		if (hunt != null) {
			JTextField workerText = new JTextField("Hunters: " + hunt.getCount());
			workerText.setEditable(false);
			workerText.setSize(textFieldSize);
			workerText.setLocation(10, 30);

			double[] array = hunt.getOutput();
			JTextArea outputText;
			if (array == null)
				outputText = new JTextArea("Resources per tick: 0" );
			else
				outputText = new JTextArea("Resources per tick:\n" + arrayToString(array) );

			outputText.setEditable(false);
			outputText.setSize(textAreaSize);
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
				//mainScreen();
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

	private void calculateResources() {
		//get base resources per tick
		for (int i = 0; i < 5; ++i) {
			if (hunt != null)
				perTick[i] = hunt.getOutput()[i];
			if (nipFarm != null)
				perTick[i] += nipFarm.getOutput()[i];
			if (electrician != null)
				perTick[i] += electrician.getOutput()[i];
			if (mageTower != null)
				perTick[i] += mageTower.getOutput()[i];
			if (toymaker != null)
				perTick[i] += toymaker.getOutput()[i];
			if (architect != null)
				perTick[i] += architect.getOutput()[i];
			if (scienceLab != null)
				perTick[i] += scienceLab.getOutput()[i];
		}
		//apply penalties
		perTick[0] -= .5 * population;	//food per cat
		perTick[1] -= .02 * population;	//happiness per cat
		//apply resources
		food += perTick[0];
		happiness += perTick[1];
		if (food < 0)
			happiness += perTick[1];
		science += perTick[2];
		magic += perTick[3];
		popCap += perTick[4];
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
