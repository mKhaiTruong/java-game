package application;

import javafx.geometry.Pos;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class MenuItem extends StackPane {
	Color light = Color.rgb(255, 243, 207);
	Color white = Color.rgb(232, 200, 114);
	Color dark = Color.rgb(201, 215, 221);
	Color black = Color.rgb(99, 122, 159);
	
	public MenuItem(String txt1, String txt2, int w) {
		Rectangle bg = new Rectangle(w, 30);
		LinearGradient gradient = new LinearGradient(0, // startX
				0, // startY
				1, // endX
				0, // endY
				true, // proportional
				CycleMethod.NO_CYCLE, // cycleMethod
				new Stop(0, dark.deriveColor(0, 1, 1, 0.2)),
				new Stop(0.6, dark.deriveColor(0, 1, 1, 0.35)));
		bg.setFill(gradient);
		bg.setVisible(false);
		bg.setEffect(new DropShadow(5, 0, 5, Color.BLACK));

		StackPane sp = new StackPane();
		StackPane rightStackPane = createTextStackPane(txt1, Pos.CENTER_RIGHT);
		StackPane leftStackPane = createTextStackPane(txt2, Pos.CENTER_LEFT);
		sp.getChildren().addAll(rightStackPane, leftStackPane);
		getChildren().addAll(bg, sp);

		Text rightText = (Text) rightStackPane.getChildren().get(0);
		Text leftText = (Text) leftStackPane.getChildren().get(0);
		setOnMouseEntered(e -> {
			bg.setVisible(true);
			rightText.setFill(light);
			leftText.setFill(light);
		});

		setOnMouseExited(e -> {
			bg.setVisible(false);
			rightText.setFill(Color.LIGHTGREY);
			leftText.setFill(Color.LIGHTGREY);
		});

		setOnMousePressed(e -> {
			bg.setFill(new LinearGradient(0, // startX
					0, // startY
					1, // endX
					0, // endY
					true, // proportional
					CycleMethod.NO_CYCLE, // cycleMethod
					new Stop(0, black.deriveColor(0, 1, 1, 0.4)), new Stop(1, black.deriveColor(0, 1, 1, 0.4))));
			rightText.setFill(white);
			leftText.setFill(white);
		});

		setOnMouseReleased(e -> {
			bg.setFill(gradient);
			rightText.setFill(Color.WHITE);
			leftText.setFill(Color.WHITE);
		});
	}

	private StackPane createTextStackPane(String text, Pos alignment) {
        Text textNode = new Text(text);
        textNode.setFill(Color.LIGHTGREY);
        textNode.setFont(Font.font(20));

        StackPane stackPane = new StackPane(textNode);
        stackPane.setAlignment(alignment);

        return stackPane;
    }
	
	public void changeLight(Color light) {
		this.light = light;
	}
	public void changeWhite(Color white) {
		this.white = white;
	}
	public void changeDark(Color dark) {
		this.dark = dark;
	}
	public void changeBlack(Color black) {
		this.black = black;
	}
}
