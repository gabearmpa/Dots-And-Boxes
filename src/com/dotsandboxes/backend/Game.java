package com.dotsandboxes.backend;

import java.util.Scanner;

public class Game {
	
	private final int NUM_PLAYERS = 2;
	
	private Player[] players;
	
	private Board board;
	
	Scanner keyboard;
	
	public Game(int numRows, int numCols) {
		board = new Board(new Board(numRows, numCols));
		
		players = new Player[NUM_PLAYERS];
		int[] colors = new int[] { Box.BLACK, Box.WHITE };
		
		keyboard = new Scanner(System.in);
		
		// validation later
		
		for (int i = 0; i < players.length; i++) {
			System.out.print("Enter player 1's ply (if 0, human player): ");
			
			int ply = keyboard.nextInt();
			
			if (ply == 0) {
				players[i] = new HumanPlayer(colors[i]);
			} else if (ply > 0) {
				players[i] = new MiniMaxAI(colors[i], ply);
			}
			
		}
	}
	
	public int gameLoop() {
		
		Player player1 = players[0];
		Player player2 = players[1];
		
		// randomize later
		int currentPlayer = player1.color;
		
		while (!board.isGameOver()) {
			
			boolean withLetters = false;
			
			if (player1.color == currentPlayer && player1 instanceof HumanPlayer ||
				player2.color == currentPlayer && player2 instanceof HumanPlayer) {
				
				withLetters = true;
			}
			
			System.out.println();
			System.out.println(board.toString(withLetters));
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
		
		keyboard.close();
		
		if (player1Score > player2Score) {
			return player1.color;
		} else if (player2Score > player1Score) {
			return player2.color;
		} else {
			return -1;
		}
		
	}

}
