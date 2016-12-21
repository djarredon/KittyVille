public class CatNode extends Cat{
	private CatNode next, previous;
	
	public CatNode() {
		super();
		next = previous = null;
	}
	
	public CatNode(Cat toCopy) {
		super(toCopy);
		next = previous = null;
	}
	
	public void setNext(CatNode next) {
		this.next = next;
	}
	
	public void setPrevious(CatNode previous) {
		this.previous = previous;
	}
	
	public CatNode getNext() {
		return next;
	}
	
	public CatNode getPrevious() {
		return previous;
	}

}
