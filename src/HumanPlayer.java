
import java.util.Scanner;

public class HumanPlayer extends Player {
	
	private Scanner keyboard;
	
	public HumanPlayer(int color) {
		super(color);
		this.keyboard = new Scanner(System.in); // System.in will be closed by main
	}

	@Override
	public Move play(Board b) {

		System.out.println("Enter two neighboring space-separated symbols (case sensitive) for dots");
		System.out.print("  > ");
		
		// get dots as chars, convert them to Dot objects
		Dot dot1 = b.getDot(keyboard.next().charAt(0));
		Dot dot2 = b.getDot(keyboard.next().charAt(0));
		
		return new Move(dot1, dot2);
		
	}

}
