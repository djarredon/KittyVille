public class Building {
	protected String name;	//farm, mage's tower, etc.
	protected float cost;
	
	/* 
	 * output [0] = food
	 * 		  [1] = happiness
	 * 		  [2] = science
	 * 		  [3] = magic
	 * 		  [4] = population cap
	 */
	protected int resourceNum;
	protected float[] baseOutput;
	protected float[] output;
	
	protected CatList catlist;	//list of cats	
	protected Building next, previous;

	public Building() {
		catlist = new CatList();
		name = null;
		cost = 0;
		resourceNum = 5;
		baseOutput = new float[resourceNum];
		output = new float[resourceNum];
		//next = previous = null;
	}
	
	public Building(String name) {
		resourceNum = 5;
		baseOutput = new float[resourceNum];
		output = new float[resourceNum];
		setName(name);
		catlist = new CatList();
		//next = previous = null;
	}
	//hunt, nipFarm, electrician, mageTower,
	//toymaker, architect, scienceLab
	private void setName(String name) {
		if (name.equalsIgnoreCase("Hunt")) {
			cost = 0;
			baseOutput[0] = 1;	//food
			baseOutput[1] = 0;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 0;	//population cap
		}
		else if (name.equalsIgnoreCase("Catnip Farm")) {
			cost = 100;
			baseOutput[0] = 1;	//food
			baseOutput[1] = 1;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 0;	//population cap
		}
		/*
		 //this will be implemented when I can decide what
		 //electricity is for
		  
		else if (name.equalsIgnoreCase("Electrician")) {
			cost = 600;
			baseOutput = 8;
			baseOutput[0] = 1;	//food
			baseOutput[1] = 0;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 0;	//population cap
		}
		*/
		
		else if (name.equalsIgnoreCase("Mage Tower")) {
			cost = 4000;
			baseOutput[0] = 0;	//food
			baseOutput[1] = 0;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 1;	//magic
			baseOutput[4] = 0;	//population cap
		}
		
		else if (name.equalsIgnoreCase("Toymaker")) {
			cost = 600;
			baseOutput[0] = 0;	//food
			baseOutput[1] = 1;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 0;	//population cap
		}
		
		else if (name.equalsIgnoreCase("Architecture Firm")) {
			cost = 800;
			baseOutput[0] = 0;	//food
			baseOutput[1] = 1;	//happiness
			baseOutput[2] = 0;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 1;	//population cap
		}
		
		else if (name.equalsIgnoreCase("Science Lab")) {
			cost = 3000;
			baseOutput[0] = 0;	//food
			baseOutput[1] = 0;	//happiness
			baseOutput[2] = 1;	//science
			baseOutput[3] = 0;	//magic
			baseOutput[4] = 0;	//population cap
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
		return (int) cost;
	}
	
	public float[] getOutput() {
		calculateOutput();
		return output;
	}
	
	public void addCat(Cat toAdd) {
		if (catlist == null)
			catlist = new CatList();
		catlist.addCat(toAdd);
	}
	
	public void addCatList(CatList toAdd) {
		if (catlist == null)
			catlist = new CatList();
		
		catlist.addCatList(toAdd);
	}
	
	protected void calculateOutput() {
		float[] temp = new float[resourceNum];
		if (catlist == null) {
			for (int i = 0; i < resourceNum; ++i)
				temp[i] = 0;
		}
		else {
			//later, this section will include various multipliers
			//from tech, magic, or other upgrades
			for (int i = 0; i < resourceNum; ++i) {
				temp[i] = this.baseOutput[i] * catlist.getCount();
			}
		}
		
		output = temp;
	}
	
	/*
	public void setNext(Building next) {
		this.next = next;
	}
	
	public void setPrevious(Building previous) {
		this.previous = previous;
	}
	
	public Building getNext() {
		return next;
	}
	
	public Building getPrevious() {
		return previous;
	}
	*/
	
}
