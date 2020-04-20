package com.dotsandboxes.backend;

public class MiniMaxAI extends Player {

	public MiniMaxAI(int color) {
		super(color);
	}

	@Override
	public Move play(Board b) {
		
		// copy the board
		Board board = new Board(b);
		
		MoveValue max;
		
		try {
			 max = maxValue(board, Integer.MIN_VALUE, Integer.MAX_VALUE);
		} catch (Exception e) {
			max = new MoveValue(0);
			System.out.println("NO");
		}
		
		return max.move;
	}
	
	public MoveValue maxValue(Board board, int alpha, int beta) throws Exception {
		if (board.isGameOver()) {
			return new MoveValue(utility(board));
		}
		
		MoveValue moveValue = new MoveValue(Integer.MIN_VALUE);
		
		for (Move m : board.getMoves()) {
			// copy the board, make the play
			Board newBoard = new Board(board);
			boolean extraTurn = newBoard.placeLine(color, m.first(), m.second());
			
			// see if continuing branch would increase value
			MoveValue branchValue;
			if (extraTurn) {
				branchValue = maxValue(newBoard, alpha, beta);
			} else {
				branchValue = minValue(newBoard, alpha, beta);
			}
			
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
	
	public MoveValue minValue(Board board, int alpha, int beta) throws Exception {
		if (board.isGameOver()) {
			return new MoveValue(utility(board));
		}
		
		MoveValue moveValue = new MoveValue(Integer.MAX_VALUE);
		
		for (Move m : board.getMoves()) {
			// copy the board, make the play
			Board newBoard = new Board(board);
			boolean extraTurn = newBoard.placeLine(color, m.first(), m.second());
			
			// see if continuing branch would decrease value
			MoveValue branchValue;
			if (extraTurn) {
				branchValue = minValue(newBoard, alpha, beta);
			} else {
				branchValue = maxValue(newBoard, alpha, beta);
			}
			
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
