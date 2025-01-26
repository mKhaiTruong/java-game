package application;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;

public class ScoreMenuBox extends MenuBox{

	public ScoreMenuBox(String title, int w, int h, MenuItem[] items) {
		super(title, w, h, items);
		setAlignment(Pos.CENTER);
		Text text = changeTitle(title + "  ", Color.WHITE, font);
		VBox vbox = changeVBox(Pos.TOP_RIGHT, new Insets(60, 0, 0, 0), text);
		vbox.getChildren().addAll(items);
		setAlignment(Pos.TOP_RIGHT);
		getChildren().addAll(bg, vbox);
	}

}
