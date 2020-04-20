package com.dotsandboxes.backend;

public class AIPlayer extends Player {

	public AIPlayer(int color) {
		super(color);
	}

	@Override
	public Move play(Board b) {
		Box[][] boxes = b.getBoxes();
		
		for (int r = 0; r < b.numRows; r++) {
			for (int c = 0; c < b.numCols; c++) {
				
				Box box = boxes[r][c];
				
				if (box.getValue() == Box.EMPTY) {
					for (int i = 0; i < 4; i++) {
						Line l = box.getLine(i);
						
						if (l.getValue() == Line.EMPTY) {
							
							if (i == Line.LEFT_LINE) {
								
								Dot dot1 = new Dot(r, c);
								Dot dot2 = new Dot(r + 1, c);
								return new Move(dot1, dot2);
								
							} else if (i == Line.TOP_LINE) {
								
								Dot dot1 = new Dot(r, c);
								Dot dot2 = new Dot(r, c + 1);
								return new Move(dot1, dot2);
								
							} else if (i == Line.RIGHT_LINE) {
								
								Dot dot1 = new Dot(r, c + 1);
								Dot dot2 = new Dot(r + 1, c + 1);
								return new Move(dot1, dot2);
								
							} else {
								// bottom line
								
								Dot dot1 = new Dot(r + 1, c);
								Dot dot2 = new Dot(r + 1, c + 1);
								return new Move(dot1, dot2);
								
							}
						}
					}
				}
				
			}
		}
		
		return null;
	}

}
