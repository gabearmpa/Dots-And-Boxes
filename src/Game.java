
public class Game {
	
	public static final int NUM_PLAYERS = 2;
	
	private Player[] players;
	
	private Board board;
	
	/**
	 * @param numRows
	 * @param numCols
	 * @param plies a ply value for each player (0 means human player)
	 */
	public Game(int numRows, int numCols, int[] plies) {
		board = new Board(new Board(numRows, numCols));
		
		players = new Player[NUM_PLAYERS];
		int[] colors = new int[] { Box.BLACK, Box.WHITE };
		
		// create players
		for (int i = 0; i < players.length; i++) {
			
			int ply = plies[i];
			
			if (ply == 0) {
				players[i] = new HumanPlayer(colors[i]);
			} else if (ply > 0) {
				players[i] = new MiniMaxAI(colors[i], ply);
			}
			
		}
	}
	
	/**
	 * main game loop
	 * @return winning player
	 */
	public int gameLoop() {
		
		Player player1 = players[0];
		Player player2 = players[1];
		
		// player 1 goes first
		int currentPlayer = player1.color;
		
		// main game loop
		while (!board.isGameOver()) {
			
			boolean withLetters = false;
			
			// if current player is human player, show letters in the board output
			if (player1.color == currentPlayer && player1 instanceof HumanPlayer ||
				player2.color == currentPlayer && player2 instanceof HumanPlayer) {
				
				withLetters = true;
			}
			
			// output the current board, say whose turn it is
			System.out.println();
			System.out.println(board.toString(withLetters));
			System.out.println();
			System.out.println("Player " + currentPlayer + "'s turn:");
			System.out.println();
			
			// get the next player from the current player
			Move play;
			
			if (currentPlayer == player1.color) {
				
				play = player1.play(board);
				
			} else {
				
				play = player2.play(board);
				
			}
			
			// get the dots from the play
			Dot dot1 = play.first();
			Dot dot2 = play.second();
			
			try {
			
				// execute the play, see if that player gets to go again
				boolean extraTurn = board.placeLine(currentPlayer, dot1, dot2);
				
				// swap players if not another turn
				if (!extraTurn) {
					if (currentPlayer == player1.color) {
						currentPlayer = player2.color;
					} else {
						currentPlayer = player1.color;
					}
				}
			
			} catch (Exception e) {
				System.out.println("Invalid play");
			}
			
		}
		
		// calculate score
		int player1Score = board.calculateScore(player1.color);
		int player2Score = board.calculateScore(player2.color);
		
		System.out.println();
		System.out.println(board);
		System.out.println();
		
		System.out.println("Player 1: " + player1Score);
		System.out.println("Player 2: " + player2Score);
		
		if (player1Score > player2Score) {
			return player1.color;
		} else if (player2Score > player1Score) {
			return player2.color;
		} else {
			return -1;
		}
		
	}

}
