package application;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;

public class ButtonSet {
	private Map<String, Button> buttons;

	public ButtonSet() {
		this.buttons = new HashMap<>();
	}

	public void addButton(String name, Button button) {
		buttons.put(name, button);
	}

	public void removeButton(String btn) {
		if (buttons.containsKey(btn))
			buttons.remove(btn);
		else
			System.out.println("No such button");
	}

	public void disableButton(String btn) {
		if (buttons.containsKey(btn)) {
			buttons.get(btn).setOnAction(null);
			buttons.get(btn).setEffect(new ColorAdjust(0, 0, 0, -0.45));
			buttons.get(btn).setStyle("-fx-border-color: #7fcdff; -fx-border-width: 2px; -fx-border-radius: 40px; -fx-background-radius: 40px;");
		} else
			System.out.println("No such button");
	}

	public void enableButton(String btn, DiceController diceController, String methodName) {
		if (buttons.containsKey(btn)) {

			buttons.get(btn).setOnAction(e -> {
				switch (methodName) {
				case "roll":
					diceController.roll(e);
					break;
				case "endGame":
					try {
						diceController.endGame(e);
					} catch (IOException e1) {
						e1.printStackTrace();
					}
					break;
				case "nextTurn":
					diceController.nextTurn(e);
					break;
				case "handleStopButton":
					diceController.handleStopButton(e);
					break;
				case "handleChooseButton":
					diceController.handleChooseButton(e);
					break;
				case "delete":
					diceController.delete(e);
					break;
				default:
					System.out.println("Unknown method");
					break;
				}
			});

			buttons.get(btn).setEffect(new ColorAdjust(0, 0, 0, 0));
			buttons.get(btn).setStyle("-fx-border-color: black; -fx-border-width: 0px;");
		} else
			System.out.println("No such button");
	}

}
