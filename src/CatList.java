public class CatList {
	//maintains list of cats
	
	private CatNode head;
	private int count;
	
	public CatList() {
		head = null;
		count = 0;
	}

	public CatList(int num) {
		head = null;
		count = 0;
		addCat(num);
	}

	public CatList(Cat first) {
		if (first != null) {
			head = new CatNode(first);
			count = 1;
		}
		else {
			head = null;
			count = 0;
		}
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

	public void copy(CatList toCopy) {
		if (toCopy.head == null) {
			head = null;
			count = 0;
		}
		else {
			head = new CatNode(toCopy.head);
			CatNode copyCurrent = toCopy.head;
			CatNode current = head;

			while (copyCurrent != null) {
				current.setNext(new CatNode(copyCurrent));
				current = current.getNext();
				copyCurrent = copyCurrent.getNext();
			}
		}
	}

	public CatList empty() {
		if (head == null)
			return null;
		CatList temp = new CatList();
		temp.copy(this);

		head = null;
		count = 0;
		return temp;
	}
	
	public int getCount() {
		return count;
	}

	public void addCat(int num) {
		for (int i = 0; i < num; ++i)
			addCat(new Cat());
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
			if (head == null) {
				head = toAdd.head;
				this.count = toAdd.count;
			}
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

	public Cat removeCat() {
		if (head == null)
			return null;
		Cat temp = head;
		head = head.getNext();
		if (head != null)
			head.setPrevious(null);
		--count;

		return temp;
	}
	
	public Cat removeCat(String toRemove) {
		if (head == null)
			return null;
		
		CatNode current = head;
		while (current != null) {
			if (current.getName().equalsIgnoreCase(toRemove)) {
				Cat temp = current;
				if (current.getNext() != null)
					current.getNext().setPrevious(current.getPrevious());
				if (current.getPrevious() != null)
					current.getPrevious().setNext(current.getNext());
				--count;

				return temp;
			}
			current = current.getNext();
		}
		return null;
	}

}
