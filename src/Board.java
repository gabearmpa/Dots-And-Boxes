import java.util.ArrayList;
import java.util.List;

public class Board {
	
	private Position[][] board;
	private List<Position> playablePositions;
	private int size;
	
	public Board(int size) {
		board = new Position[size][size];
		this.size = size;
		
		//set up empty board
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				board[r][c] = new Position(r, c, Game.EMPTY);
			}
		}
		
		// fill middle
		board[3][3] = new Position(3, 3, Game.BLACK);
		board[4][4] = new Position(4, 4, Game.BLACK);
		board[3][4] = new Position(3, 4, Game.WHITE);
		board[4][3] = new Position(4, 3, Game.WHITE);
	}
	
	/**
	 * generates a list of playable positions for the color
	 * updates local copy of playablePositions
	 * @param color
	 * @return
	 */
	public List<Position> playablePositions(int color) {
		
		// if position is empty
		// and adjacent to at least one of other color in any of 8 directions
		// and if past other color is one of your color
		
		List<Position> playable = new ArrayList<>();
		
		for (int r = 0; r < size; r++) {
			for (int c = 0; c < size; c++) {
				Position p = board[r][c];
				
				if (isPositionPlayable(p, color)) {
					playable.add(p);
				}
			}
		}
		
		return playable;
		
	}
	
	/**
	 * see if a specific position is playable on the current board for color
	 * @param p
	 * @param color
	 * @return
	 */
	public boolean isPositionPlayable(Position p, int color) {
		
		int r = p.getX();
		int c = p.getY();
		
		// if empty
		if (p.getValue() == Game.EMPTY) {
			
			// check neighbors, keep going until you reach edge or empty or other color
			
			int[][] directions = new int[][]{
				{-1, 0}, {-1, 1}, {0, 1}, {1, 1}, {1, 0}, {1, -1}, {0, -1}, {-1, -1}
			};
			
			int currentColor = p.getValue();
			
			// test each line coming out of where we are
			for (int[] direction : directions) {
				
				int currentR = r + direction[0];
				int currentC = c + direction[1];
				
				boolean inOtherColor = false;
				
				// while inside the board
				while (0 <= currentR && currentR < size && 
						0 <= currentC && currentC < size) {
					
					Position nextPosition = board[currentR][currentC];

					// found an empty spot - break
					if (nextPosition.getValue() == Game.EMPTY) {
						break;
						
					// found other color - keep looking
					} else if (nextPosition.getValue() != currentColor) {
						inOtherColor = true;
						
					// found our color after other color - it works!
					} else if (inOtherColor && nextPosition.getValue() == currentColor) {
						return true;
						
					// found our color next - break
					} else if (nextPosition.getValue() == currentColor) {
						break;
					}
					
					// keep going in same direction
					currentR += direction[0];
					currentC += direction[1];							
				}
			}
		}
		
		return false;
	}
	
	/**
	 * Places a token at position p of color, calls updateBoard
	 * @param p
	 * @param color
	 * @throws Exception if that position is not playable with that color
	 */
	public void placeToken(Position p, int color) throws Exception {
		
	}
	
	/**
	 * updates the board based on the last play
	 * @param p
	 */
	public void updateBoard(Position p) {
		
	}
	
	/**
	 * @return a copy of the position[][] board
	 */
	public Position[][] getBoard() {
		
	}
	
	/**
	 * @return true iff every spot is filled
	 */
	public boolean isGameOver() {
		
	}

}
