package com.dotsandboxes.backend;

public class AIPlayer extends Player {

	@Override
	public Dot[] play(Board b) {
		Box[][] boxes = b.getBoxes();
		
		for (int r = 0; r < b.numRows; r++) {
			for (int c = 0; c < b.numCols; c++) {
				
				Box box = boxes[r][c];
				
				if (box.getValue() == Box.EMPTY) {
					for (int i = 0; i < 4; i++) {
						Line l = box.getLine(i);
						
						if (l.getValue() == Line.EMPTY) {
							
							if (i == Line.LEFT_LINE) {
								
								Dot dot1 = new Dot(c, r);
								Dot dot2 = new Dot(c, r + 1);
								return new Dot[] {dot1, dot2};
								
							} else if (i == Line.TOP_LINE) {
								
								Dot dot1 = new Dot(c, r);
								Dot dot2 = new Dot(c + 1, r);
								return new Dot[] {dot1, dot2};
								
							} else if (i == Line.RIGHT_LINE) {
								
								Dot dot1 = new Dot(c + 1, r);
								Dot dot2 = new Dot(c + 1, r + 1);
								return new Dot[] {dot1, dot2};
								
							} else {
								// bottom line
								
								Dot dot1 = new Dot(c, r + 1);
								Dot dot2 = new Dot(c + 1, r + 1);
								return new Dot[] {dot1, dot2};
								
							}
						}
					}
				}
				
			}
		}
		
		return null;
	}

}
