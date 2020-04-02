import java.util.List;

import javafx.application.Application;
import javafx.stage.Stage;

public class UI extends Application {

	public static void main(String[] args) {
		Application.launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		Board board = new Board(8);
		Game game = new Game(board);
		
		List<Position> playable = board.playablePositions(Game.BLACK);
		
		System.out.println(playable.size());
	}

}
