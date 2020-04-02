
public class Game {
	
	public static final int EMPTY = 0;
	public static final int BLACK = 1;
	public static final int WHITE = 2;
	
	private Board board;
	private Player ai;
	
	public Game(Board board) {
		this.board = board;
		this.ai = new Player(Game.WHITE);
	}
	
	public void playGame() {
		
		int turn = Game.BLACK;
		
		// human player is black
		
		while (!board.isGameOver()) {
			
			// boolean black can play
			// boolean white can play
			
			// if both false - end
			
			// add to lower if statements && "color can play"
			
			// human turn
			if (turn == Game.BLACK) {
				
				// tell UI that clicks are ready
				
				// generate list of playable positions
				
				// get clicks from the UI until one of them is playable
				
				// play it
				
				turn = Game.WHITE;
				
			// ai turn
			} else {
				
				// generate list of playable positions
				
				// call the AI's function
				
				// play it
				
				turn = Game.BLACK;
			}
			
		}
		
		// game over
		
		// count color of non-empty positions on board
		
		// winner is person with more
		
		// tell UI the winner
		
	}

}
