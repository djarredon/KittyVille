public class CatList {
	//maintains list of cats
	
	private CatNode head;
	private int count;
	
	public CatList() {
		head = null;
		count = 0;
	}
	
	public void displayAll() {
		if (count == 1)
			System.out.println("1 cat!");
		else
			System.out.println(count + " cats!");
		CatNode current = head;
		while (current != null) {
			current.display();
			current = current.getNext();
		}
	}
	
	public int getCount() {
		return count;
	}
	
	public void addCat(Cat toAdd) {
		addCat(new CatNode(toAdd));
	}
	
	public void addCat(CatNode toAdd) {
		if (head == null) {
			head = toAdd;
			++count;
		}
		else {
			toAdd.setNext(head);
			head.setPrevious(toAdd);
			
			head = toAdd;
			++count;
		}
	}
	
	public void addCatList(CatList toAdd) {
		if (toAdd == null || toAdd.head == null)
			return;
		else {
			if (head == null)
				head = toAdd.head;
			else {
				CatNode tail = head;
				while (tail.getNext() != null) {
					tail = tail.getNext();
				}
				tail.setNext(toAdd.head);
				this.count += toAdd.count;
			}
		}
	}
	
	public void removeCat() {
		if (head == null)
			return;
		
		head = head.getNext();
		head.setPrevious(null);
		--count;
	}
	
	public boolean removeCat(String toRemove) {
		if (head == null)
			return false;
		
		CatNode current = head;
		while (current != null) {
			if (current.getName().equalsIgnoreCase(toRemove)) {
				if (current.getNext() != null)
					current.getNext().setPrevious(current.getPrevious());
				if (current.getPrevious() != null)
					current.getPrevious().setNext(current.getNext());
				--count;
				return true;
			}
			current = current.getNext();
		}
		return false;
	}

}
