package application;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.GridPane;

public class TableOfDices {
	private Map<Integer, MemoryDice> tableOfDices;
	private Random random;
	private Button clickedButton;
	private int size;

	public TableOfDices() {
		this.tableOfDices = new HashMap<>();
		size = 8;
	}

	public Button getClickedButton() {
		return clickedButton;
	}

	public void initialTableOfDices() {
		for (int i = 0; i < size; i++)
			tableOfDices.put(i, new MemoryDice("1", i));

	}

	public void generateDiceRandomly() {
		random = new Random();
		for (int i = 0; i < tableOfDices.size(); i++) {
			int r = random.nextInt(6) + 1;

			if (!tableOfDices.get(i).isDisallowedDice()) {
				MemoryDice memoryDice = new MemoryDice(Integer.toString(r), i);
				memoryDice.fillImageView();
				tableOfDices.put(i, memoryDice);
			}
		}
	}

	public Map<Integer, MemoryDice> getTableOfDices() {
		return tableOfDices;
	}

	public void clearDices() {
		this.tableOfDices.clear();
	}

	public int getSize() {
		return this.size;
	}

	public MemoryDice getMemoryDice(int index) {
		return this.tableOfDices.get(index);
	}

	public int getMemoryDiceIndex(int index) {
		return getMemoryDice(index).getDiceIndex();
	}

	public void rollDices(GridPane gridPane) {
		for (int i = 0; i < getSize(); i++) {
			Button button = (Button) gridPane.getChildren().get(i);
//			int value = tableOfDices.get(i).getDiceValue();

			MemoryDice currentDice = tableOfDices.get(i);
			currentDice.fillImageView();

			ImageView imageView = tableOfDices.get(i).getImageView();
			imageView.setFitHeight(50);
			imageView.setFitWidth(50);
			imageView.setPreserveRatio(true);

			button.setGraphic(imageView);

//			if (!tableOfDices.get(i).isDisallowedDice()) {
//				button.setUserData((MemoryDice) tableOfDices.get(i));
//				setMouseEvents(i, gridPane, value, button, buttons, diceController);
//			} else {
//				tableOfDices.get(i).setChosenImageView(imageView);
//			}
		}
	}

	public void paintDices(ArrayList<Integer> notAllowedValue, GridPane gridPane, ButtonSet buttons,
			DiceController diceController) {
		for (int i = 0; i < getSize(); i++) {
			boolean flag = false;
			Button button = (Button) gridPane.getChildren().get(i);
			int value = tableOfDices.get(i).getDiceValue();

			for (int j = 0; j < notAllowedValue.size(); j++) {
				if (value == notAllowedValue.get(j)) {
					flag = true;
					break;
				}
			}

			if (!flag) {
				button.setUserData((MemoryDice) tableOfDices.get(i));
				setMouseEvents(i, gridPane, value, button, buttons, diceController);
			} else {
				button.setDisable(true);
			}
		}
	}

	private void setMouseEvents(int i, GridPane gridPane, int value, Button button, ButtonSet buttons,
			DiceController diceController) {
		ImageView imageView = (ImageView) button.getGraphic();

		button.setOnMouseClicked(event -> {
			clickedButton = button;
			if (!tableOfDices.get(i).isDisallowedDice()) {
				buttons.enableButton("delete", diceController, "delete");
				buttons.enableButton("choose", diceController, "handleChooseButton");
			} else {
				buttons.disableButton("choose");
			}

			tableOfDices.get(i).setDisallowedDice();

		});

		button.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
			if (!tableOfDices.get(i).isDisallowedDice())
				tableOfDices.get(i).setImageViewSelectedColor(imageView);
			else
				tableOfDices.get(i).setChosenImageView(imageView);
			event.consume();
		});

		button.addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
			if (!tableOfDices.get(i).isDisallowedDice())
				tableOfDices.get(i).resetImageViewColor(imageView);
			else
				tableOfDices.get(i).setChosenImageView(imageView);
			event.consume();
		});
	}
}
