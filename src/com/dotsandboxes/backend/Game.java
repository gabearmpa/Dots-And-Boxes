package com.dotsandboxes.backend;

public class Game {
	
	private Player player1;
	private Player player2;
	
	private Board board;
	
	public Game(int numRows, int numCols) {
		board = new Board(new Board(numRows, numCols));
		
		// currently can play human vs. human, human vs. AI, or AI vs. AI
		
		player1 = new MiniMaxAI(Box.BLACK, 10);
		player2 = new MiniMaxAI(Box.WHITE, 10);
	}
	
	public int gameLoop() {
		
		// randomize later
		int currentPlayer = player1.color;
		
		while (!board.isGameOver()) {
			
			System.out.println();
			System.out.println(board);
			System.out.println();
			System.out.println("Player " + currentPlayer + "'s turn:");
			System.out.println();
			
			Move play;
			
			if (currentPlayer == player1.color) {
				
				play = player1.play(board);
				
			} else {
				
				play = player2.play(board);
				
			}
			
			Dot dot1 = play.first();
			Dot dot2 = play.second();
			
			try {
			
				boolean extraTurn = board.placeLine(currentPlayer, dot1, dot2);
				
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
