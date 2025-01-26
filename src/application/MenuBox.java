package application;

import javafx.animation.TranslateTransition;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.effect.Bloom;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class MenuBox extends StackPane {
	protected static Font font;
	protected static Rectangle bg;
	
	public MenuBox(String title, int w, int h, MenuItem... items) {
		font = Font.loadFont((getClass().getResource("/fonts/fabrik.otf").toExternalForm()), 36);
		
		bg = new Rectangle(w, h);

		// Define the colors and opacities for the gradient
		Stop[] stops = new Stop[] { new Stop(0, Color.BLACK.deriveColor(0, 1, 1, 0.15)), // Top with 0.1 opacity
				new Stop(0.8, Color.BLACK.deriveColor(0, 1, 1, 0)) // Bottom with full opacity
		};

		LinearGradient gradient = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);

		// Set the gradient as the fill of the rectangle
		bg.setFill(gradient);

		DropShadow shadow = new DropShadow(7, 5, 0, Color.BLACK);
		shadow.setSpread(0.2);
		bg.setEffect(shadow);

		Text text = changeTitle(title + "  ", Color.WHITE, font);
		VBox vbox = changeVBox(Pos.TOP_RIGHT, new Insets(60, 0, 0, 0), text);
		vbox.getChildren().addAll(items);
		getChildren().addAll(bg, vbox);
	}

	protected VBox changeVBox(Pos topRight, Insets insets, Text text) {
		VBox vbox = new VBox();
		vbox.setAlignment(topRight);
		vbox.setPadding(insets);
		vbox.getChildren().addAll(text);
		return vbox;
	}

	protected Text changeTitle(String string, Color white, Font font2) {
		Text text = new Text(string);
		Bloom bloom = new Bloom();
		text.setFill(Color.WHITE);
		text.setFont(font);
		text.setEffect(bloom);
		text.setStyle("-fx-font-family: \"Fabrik Personal Use\"; -fx-font-size: 50");
		return text;
	}

	protected void show(double x) {
		setVisible(true);
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
		tt.setToX(x);
		tt.play();
	}

	protected void hide(double x) {
		TranslateTransition tt = new TranslateTransition(Duration.seconds(0.5), this);
		tt.setToX(x);
		tt.setOnFinished(e -> setVisible(false));
		tt.play();
	}
}
