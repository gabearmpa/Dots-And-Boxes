
public class Line {
	
	public static final int EMPTY = 0;
	public static final int FULL = 1;
	
	public static final int LEFT_LINE = 0;
	public static final int TOP_LINE = 1;
	public static final int RIGHT_LINE = 2;
	public static final int BOT_LINE = 3;
	
	private Box box1 = null;
	private Box box2 = null;
	
	private int value;
	
	public Line() {
		setValue(Line.EMPTY);
	}
	
	public Line(Line l) {
		this.value = l.value;
	}
	
	/**
	 * @return the most number of lines filled in an adjacent box after this line gets toggled on
	 */
	public int numLinesFilledAfter() {
		if (box1 == null) {
			return box2.numLinesFilled() + 1;
		} else if (box2 == null) {
			return box1.numLinesFilled() + 1;
		} else {
			// both not null
			
			return Math.max(box1.numLinesFilled() + 1, box2.numLinesFilled() + 1);
		}
	}
	
	public Box getBox1() {
		return box1;
	}
	
	public Box getBox2() {
		return box2;
	}
	
	public void setBox1(Box box) {
		this.box1 = box;
	}
	
	public void setBox2(Box box) {
		this.box2 = box;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

}
