package com.dotsandboxes.backend;

public class Game {
	
	private Player player1;
	private Player player2;
	
	private int player1Color;
	private int player2Color;
	
	private Board board;
	
	public Game(int numRows, int numCols) {
		board = new Board(numRows, numCols);
		
		player1 = new HumanPlayer();
		player2 = new AIPlayer();
		
		player1Color = Box.BLACK;
		player2Color = Box.WHITE;
	}
	
	public int gameLoop() {
		
		// randomize later
		int currentPlayer = player1Color;
		
		while (!board.isGameOver()) {
			
			System.out.println();
			System.out.println(board);
			System.out.println();
			
			Dot[] play;
			
			if (currentPlayer == player1Color) {
				
				play = player1.play(board);
				
			} else {
				
				play = player2.play(board);
				
			}
			
			Dot dot1 = play[0];
			Dot dot2 = play[1];
			
			try {
			
				boolean extraTurn = board.placeLine(currentPlayer, dot1, dot2);
				
				if (!extraTurn) {
					if (currentPlayer == player1Color) {
						currentPlayer = player2Color;
					} else {
						currentPlayer = player1Color;
					}
				}
			
			} catch (Exception e) {
				System.out.println("Invalid play");
			}
			
		}
		
		// calculate score
		int player1Score = board.calculateScore(player1Color);
		int player2Score = board.calculateScore(player2Color);
		
		System.out.println("Player 1: " + player1Score);
		System.out.println("Player 2: " + player2Score);
		
		if (player1Score > player2Score) {
			return player1Color;
		} else if (player2Score > player1Score) {
			return player2Color;
		} else {
			return -1;
		}
		
	}

}
