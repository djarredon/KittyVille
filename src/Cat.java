public class Cat{
	//What does a cat need?
	//What is a cat?
	//Just a photo of the cat?
	//Yeah, probably
	
	public String name;
	
	public Cat() {
		name = null;
	}
	
	public Cat(Cat toCopy) {
		copyCat(toCopy);
	}
	
	public void display() {
		if (name != null)
			System.out.println(name);
		else
			System.out.println("Kitty!");
	}
	
	public void copyCat(Cat toCopy) {
		this.name = toCopy.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		if (name == null)
			return "";
		else
			return name;
	}

}
