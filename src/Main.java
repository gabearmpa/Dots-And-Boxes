
import java.util.Scanner;

public class Main {

	public static void main(String[] args) throws Exception {
		
		Scanner keyboard = new Scanner(System.in);
		
		System.out.println("Dots and Boxes");
		System.out.println("Gabe Armstrong, Ethan Greenly, Stephen Stoltzfus");
		System.out.println("Note: human player only supported up to 6x6 boards\n");
		
		// get number of rows
		boolean valid = false;
		int rows = 1;
		while (!valid) {
			
			// validate that number of rows is a positive integer
			try {
				System.out.print("Enter number of rows: ");
				String line = keyboard.nextLine();
				rows = Integer.parseInt(line);
				
				if (rows > 0) {
					valid = true;
				} else {
					System.out.println("Error - number of rows should be a positive integer\n");
				}
			} catch (NumberFormatException ex) {
				System.out.println("Error - number of rows should be a positive integer\n");
			}
		}

		
		// get number of columns
		valid = false;
		int cols = 1;
		while (!valid) {
			
			// validate that number of columns is a positive integer
			try {
				System.out.print("Enter number of columns: ");
				String line = keyboard.nextLine();
				cols = Integer.parseInt(line);
				
				if (cols > 0) {
					valid = true;
				} else {
					System.out.println("Error - number of columns should be a positive integer\n");
				}
			} catch (NumberFormatException ex) {
				System.out.println("Error - number of columns should be a positive integer\n");
			}
		}
		
		// alphabet only has 52 characters, so can only support so many boards for user input
		boolean humanSupported = ((rows + 1) * (cols + 1)) <= Board.alphabet.length();
		
		int plies[] = new int[Game.NUM_PLAYERS];
		
		// get ply for each player
		for (int i = 0; i < Game.NUM_PLAYERS; i++) {
			valid = false;
			int ply = 1;
			while (!valid) {
				
				try {
					System.out.print("Enter player " + (i + 1) + "'s ply (if 0, human player): ");
					String line = keyboard.nextLine();
					ply = Integer.parseInt(line);
					
					// valid if ply is a positive integer, or 
					if (ply > 0 || (ply == 0 && humanSupported)) {
						valid = true;
					} else if (ply == 0){
						System.out.println("Error - human player not supported for this size board\n");
					} else {
						System.out.println("Error - ply should be a non-negative integer\n");
					}
				} catch (NumberFormatException ex) {
					System.out.println("Error - ply should be a non-negative integer\n");
				}
			}
			
			plies[i] = ply;
		}
		
		// create the game object with the given parameters
		Game game = new Game(rows, cols, plies);
		
		// run the game
		game.gameLoop();
		
		// print the time MiniMax ran
		System.out.println("Duration of MiniMax (msec): " + (MiniMaxAI.totalTimeNanos / 1000000));
		
		keyboard.close();
	}

}
