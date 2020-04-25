
// abstract parent player class

public abstract class Player {
	
	public final int color;
	
	public Player(int color) {
		this.color = color;
	}
	
	// returns the color of the other player
	public int otherColor() {
		if (color == Box.BLACK) {
			return Box.WHITE;
		}
		return Box.BLACK;
	}
	
	// make a move given a board
	public abstract Move play(Board b);

}
