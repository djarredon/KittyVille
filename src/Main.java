
import javax.swing.JFrame;

public class Main {

	public static void main (String [] args) {
		System.out.println("Hello World!");
		run();
		System.out.println("LOL");
	}
	
	public static void run() {
		CatTown test = new CatTown("Kittyton");
		test.setVisible(true);
		test.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public static void catTest() {
		CatNode test = new CatNode();
		
		test.setName("Charles");
		
		CatList catlist = new CatList();
		catlist.addCat(test);
		catlist.addCat(new Cat());
		
		catlist.displayAll();
		
		CatList list2 = new CatList();
		for (int i = 0; i < 10; ++i) {
			list2.addCat(new CatNode());
		}
		
		list2.displayAll();
		
		System.out.println("\n");
		
		catlist.addCatList(list2);
		
		catlist.displayAll();
	}
}
