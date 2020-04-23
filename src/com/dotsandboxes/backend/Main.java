package com.dotsandboxes.backend;

public class Main {

	public static void main(String[] args) throws Exception {
		Game game = new Game(3, 3);
		
		long startTime = System.nanoTime();
		
		game.gameLoop();
		
		long endTime = System.nanoTime();
		
		long duration = endTime - startTime;
		
		System.out.println("Duration (msec): " + (duration / 1000000));

		
		System.out.println("Place Line (msec): " + (Board.totalPlaceLineNanos / 1000000));
		
		System.out.println("Undo Line (msec): " + (Board.totalUndoLineNanos / 1000000));
		
		System.out.println("Get Moves (msec): " + (Board.totalGetMovesNanos / 1000000));
		
		System.out.println("Sort Moves (msec): " + (Board.totalSortMovesNanos / 1000000));
	}

}
