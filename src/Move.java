
public class Move {
	
	private int CAPTURES = 0;
	private int OTHER = 1;
	private int GIVESUP = 2;
	
	public final int moveType;
	
	private Dot a;
	private Dot b;
	
	public Move(Dot a, Dot b) {
		this.a = a;
		this.b = b;
		
		this.moveType = -1;
	}
	
	public Move(Dot a, Dot b, int numFills) {
		this.a = a;
		this.b = b;
		
		if (numFills == 4) {
			this.moveType = CAPTURES;
		} else if (numFills == 3) {
			this.moveType = GIVESUP;
		} else {
			this.moveType = OTHER;
		}
	}
	
	public Dot first() {
		return a;
	}
	
	public Dot second() {
		return b;
	}
}