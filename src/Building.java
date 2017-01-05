public class Building {
	protected String name;	//farm, mage's tower, etc.
	
	/*
	 * Building types
	 * 	Hunting Grounds 	(food) 		[0]		(implemented)
	 * 	Catnip Farm			(happiness)	[1] 	(implemented)
	 * 	Mage Tower			(magic)		[2] 	(implemented)
	 * 	Toymaker			(happiness)	[3]
	 * 	Architecture Firm	(happiness)	[4] 	(implemented)
	 * 	Science Lab			(science)	[5] 	(implemented)
	 *
	 *
	 * resources
	 * output [0] = food
	 * 		  [1] = happiness
	 * 		  [2] = science
	 * 		  [3] = magic
	 * 		  [4] = population cap
	 */
	public final int resourceNum;	//number of unique resources
	protected double[] baseOutput;	//output before modifiers
	protected double[] output;		//output after modifiers
	protected int buildingType;		//cost of upgrade tree
	protected int upgradeCost;		//cost of next upgrade
	protected int upgradeLevel;		//current upgrade level
	protected int maxUpgrade;		//highest level of upgrades
	//this variables is used to show if something has changed
	//and the output needs to be recalculated
	//(i.e. cat added/removed, upgrade purchased)
	protected boolean recalculate;

	protected int cost;		//cost of building
	
	protected CatList catlist;	//list of cats

	public Building() {
		name = null;
		cost = 0;
		resourceNum = 5;
		initialize();
	}
	
	public Building(String name) {
		resourceNum = 5;
		initialize();

		setName(name);
	}

	private void initialize() {
		baseOutput = new double[resourceNum];
		output = new double[resourceNum];
		catlist = new CatList();
		recalculate = true;
	}

	public int getCount() {
		if (catlist != null)
			return catlist.getCount();
		else
			return 0;
	}

	//hunt, nipFarm, electrician, mageTower,
	//toymaker, architect, scienceLab
	private void setName(String name) {
		if (name.equalsIgnoreCase("Hunting Grounds")) {
			cost = 0;
			baseOutput[0] = 1;	//food
			baseOutput[1] = 0;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 0;	//population cap
			buildingType = 0;
			maxUpgrade = 2;
		}
		else if (name.equalsIgnoreCase("Catnip Farm")) {
			cost = 100;
			baseOutput[0] = 0.05;	//food
			baseOutput[1] = 0.1;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 0;	//population cap
			buildingType = 1;
			maxUpgrade = 2;
		}
		
		else if (name.equalsIgnoreCase("Mage Tower")) {
			cost = 600;
			baseOutput[0] = 0;	//food
			baseOutput[1] = 0;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = .2;	//magic
			baseOutput[4] = 0;	//population cap
			buildingType = 2;
			maxUpgrade = 2;
		}
		
		else if (name.equalsIgnoreCase("Toymaker")) {
			cost = 600;
			baseOutput[0] = 0;	//food
			baseOutput[1] = 1;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 0;	//population cap
			buildingType = 3;
		}
		
		else if (name.equalsIgnoreCase("Architecture Firm")) {
			cost = 700;
			baseOutput[0] = 0;	//food
			baseOutput[1] = .01;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = .01;	//population cap
			buildingType = 4;
		}
		
		else if (name.equalsIgnoreCase("Science Lab")) {
			cost = 600;
			baseOutput[0] = 0;	//food
			baseOutput[1] = 0;	//happiness
			baseOutput[2] = .1;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 0;	//population cap
			buildingType = 5;
			maxUpgrade = 2;
		}
		
		else {
			//default to hunt
			setName("Hunt");
		}
		
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public int getCost() {
		return cost;
	}

	public boolean hasCats() {
		if (catlist == null)
			return false;
		if (catlist.getCount() <= 0)
			return false;
		return true;
	}
	
	public double[] getOutput() {
		//if the output needs to be recalculated
		if (recalculate)
			calculateOutput();
		//else, return last known value
		return output;
	}
	
	public void addCat(Cat toAdd) {
		if (catlist == null)
			catlist = new CatList();
		catlist.addCat(toAdd);
		recalculate = true;
	}
	
	public void addCatList(CatList toAdd) {
		if (catlist == null)
			catlist = new CatList();
		
		catlist.addCatList(toAdd);
		recalculate = true;
	}

	public Cat removeCat() {
		if (catlist == null)
			return null;
		recalculate = true;
		return catlist.removeCat();
	}

	public Cat removeCat(String name) {
		if (catlist == null)
			return null;
		recalculate = true;
		return catlist.removeCat(name);
	}

	public boolean upgrade() {
		if (upgradeLevel < maxUpgrade) {
			++upgradeLevel;
			recalculate = true;
			return true;	//upgrade succeeded
		}
		return false;		//upgrade failed
	}

	public int getUpgradeCost() {
		switch (buildingType) {
			case 0: //hunting grounds
				switch (upgradeLevel) {
					case 0:
						upgradeCost = 100;
						break;
					case 1:
						upgradeCost = 400;
						break;
				}
				break;
			case 1:	//catnip farm
				switch (upgradeLevel) {
					case 0:
						upgradeCost = 100;
						break;
					case 1:
						upgradeCost = 500;
						break;
				}
				break;
			case 2:	//mage tower
				switch (upgradeLevel) {
					case 0:
						upgradeCost = 200;
						break;
					case 1:
						upgradeCost = 1000;
						break;
				}
				break;
			case 5:	//science lab
				switch (upgradeLevel) {
					case 0:
						upgradeCost = 500;
						break;
					case 1:
						upgradeCost = 2000;
						break;
				}
				break;
			default:
				return 0;
		}
		return upgradeCost;
	}

	public int getUpgradeLevel() {
		return upgradeLevel;
	}
	
	protected void calculateOutput() {
		double[] temp = new double[resourceNum];
		if (catlist == null) {
			for (int i = 0; i < resourceNum; ++i)
				temp[i] = 0;
			recalculate = false;
		}
		else {
			//initialize temp array for base value times number
			//of workers
			for (int i = 0; i < resourceNum; ++i) {
				temp[i] = this.baseOutput[i] * catlist.getCount();
			}
			//apply upgrades
			switch (buildingType) {
				case 0:		//hunting grounds
					switch (upgradeLevel) {
						case 2:
							temp[0] += .2 * getCount();
						case 1:
							temp[0] += .1 * getCount();
					}
					break;
				case 1:		//catnip farm
					switch (upgradeLevel) {
						case 2:
							temp[0] += .01 * getCount();
							temp[1] += .05 * getCount();
						case 1:
							temp[1] += .05 * getCount();
					}
					break;
				case 2: 	//mage tower
					switch (upgradeLevel) {
						case 2:
							temp[3] += .05 * getCount();
						case 1:
							temp[3] += .05 * getCount();
					}
					break;
				case 5:		//science lab
					switch (upgradeLevel) {
						case 2:
							temp[2] += .05 * getCount();
						case 1:
							temp[2] += .05 * getCount();
					}
					break;
			}
		}
		recalculate = false;
		output = temp;
	}

	public void display() {
		System.out.println(name);
		catlist.displayAll();
	}
}
