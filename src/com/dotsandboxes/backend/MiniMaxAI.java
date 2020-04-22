package com.dotsandboxes.backend;

public class MiniMaxAI extends Player {
	
	private int ply;

	public MiniMaxAI(int color, int ply) {
		super(color);
		
		this.ply = ply;
	}
	
	public static long totalTimeNanos = 0;

	@Override
	public Move play(Board b) {
		
		// copy the board
		Board board = new Board(b);
		
		MoveValue max;
		
		long startTime = System.nanoTime();
		
		try {
			 max = maxValue(board, Integer.MIN_VALUE, Integer.MAX_VALUE, 0);
		} catch (Exception e) {
			max = new MoveValue(0);
			System.out.println("NO");
			System.out.println(e.getMessage());
		}
		
		long endTime = System.nanoTime();
		
		totalTimeNanos += (endTime - startTime);
		
		return max.move;
	}
	
	public MoveValue maxValue(Board board, int alpha, int beta, int depth) throws Exception {
		if (depth >= ply || board.isGameOver()) {
			return new MoveValue(utility(board));
		}
		
		MoveValue moveValue = new MoveValue(Integer.MIN_VALUE);
		
		for (Move m : board.getMoves()) {
			// copy the board, make the play
			//Board newBoard = new Board(board);
			
			boolean extraTurn = board.placeLine(color, m.first(), m.second());
			
			// see if continuing branch would increase value
			MoveValue branchValue;
			if (extraTurn) {
				branchValue = maxValue(board, alpha, beta, depth + 1);
			} else {
				branchValue = minValue(board, alpha, beta, depth + 1);
			}
			
			board.undoMove(m.first(), m.second());
			
			if (branchValue.value > moveValue.value) {
				moveValue.value = branchValue.value;
				moveValue.move = m;
			} else {
				if (moveValue.move == null) {
					moveValue.move = m;
				}
			}
			
			if (moveValue.value >= beta) {
				return moveValue;
			}
			
			alpha = Math.max(alpha, moveValue.value);
		}
		
		return moveValue;
	}
	
	public MoveValue minValue(Board board, int alpha, int beta, int depth) throws Exception {
		if (depth >= ply || board.isGameOver()) {
			return new MoveValue(utility(board));
		}
		
		MoveValue moveValue = new MoveValue(Integer.MAX_VALUE);
		
		for (Move m : board.getMoves()) {
			// copy the board, make the play
			//Board newBoard = new Board(board);
			
			boolean extraTurn = board.placeLine(otherColor(), m.first(), m.second());
			
			// see if continuing branch would decrease value
			MoveValue branchValue;
			if (extraTurn) {
				branchValue = minValue(board, alpha, beta, depth + 1);
			} else {
				branchValue = maxValue(board, alpha, beta, depth + 1);
			}
			
			board.undoMove(m.first(), m.second());
			
			if (branchValue.value < moveValue.value) {
				moveValue.value = branchValue.value;
				moveValue.move = m;
			} else {
				if (moveValue.move == null) {
					moveValue.move = m;
				}
			}
			
			if (moveValue.value <= alpha) {
				return moveValue;
			}
			
			beta = Math.min(beta, moveValue.value);
		}
		
		return moveValue;
	}
	
	public int utility(Board b) {
		return b.calculateScore(this.color) - b.calculateScore(otherColor());
	}

}
