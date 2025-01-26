package application;

import java.util.ArrayList;
import java.io.IOException;
import java.net.URL;
import java.util.Random;
import java.util.ResourceBundle;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

import javafx.animation.PauseTransition;
import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class DiceController implements Initializable {
	Random random = new Random();

	private boolean vsBots = false;
	private boolean botTurn = false;
	private int botChoice;
	private int timeToLive = 3;
	private int sum = 0;
	private int numberOfPlayers;
	private int playerId = 0;
	private Table table;
	private FrozenDices frozenDice;
	private ButtonSet buttons;
	private TableOfDices tableOfDices;
	private Button clickedButton;
	private Bot bot;
	private Thread t1;
	private Thread t2;
	private BackgroundSound bgs;

	private ArrayList<Integer> notAllowedValue;
	private ArrayList<Player> players;

	@FXML
	private AnchorPane anchorPane;
	@FXML
	private GridPane tableOfBricks;
	@FXML
	private GridPane tableOfDice;
	@FXML
	private Label score;

	@FXML
	private FlowPane fp1;
	@FXML
	private Label player1;
	@FXML
	private Label score1;
	@FXML
	private FlowPane fpOnTop1;
	@FXML
	private Label onTopValue1;
	@FXML
	private ImageView worm1;
	@FXML
	public ImageView player1Img;

	@FXML
	private Label player2;
	@FXML
	private Label score2;
	@FXML
	private FlowPane fp2;
	@FXML
	public ImageView player2Img;
	@FXML
	private FlowPane fpOnTop2;
	@FXML
	private Label onTopValue2;
	@FXML
	private ImageView worm2;

	@FXML
	private Label player3;
	@FXML
	private Label score3;
	@FXML
	private FlowPane fp3;
	@FXML
	public ImageView player3Img;
	@FXML
	private FlowPane fpOnTop3;
	@FXML
	private Label onTopValue3;
	@FXML
	private ImageView worm3;

	@FXML
	private Button rollButton;
	@FXML
	private Button chooseButton;
	@FXML
	private Button deleteButton;
	@FXML
	private Button stopButton;
	@FXML
	private Button nextButton;
	@FXML
	private Button endGameButton;
	@FXML
	public Label playerName;

	@FXML
	public void endGame(ActionEvent e) throws IOException {
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
		FXMLLoader loaderRank = new FXMLLoader();

		loaderRank.setLocation(getClass().getResource("rankMenu.fxml"));
		Parent rankMenuParent = loaderRank.load();
		Scene rankMenuScene = new Scene(rankMenuParent);

		rankMenuController rankController = loaderRank.getController();

		rankController.setPlayers(players);
		rankController.rankPlayer();
		window.setMaxWidth(1000);
		window.setMaxHeight(700);
		window.setScene(rankMenuScene);
	}

	@FXML
	public void nextTurn(ActionEvent e) {
		for (int i = 0; i < tableOfDices.getSize(); i++) {
			Button btn = (Button) tableOfDice.getChildren().get(i);
			tableOfDices.getMemoryDice(i).resetImageViewColor((ImageView) btn.getGraphic());
		}

		buttons.disableButton("stop");
		tableOfDices.clearDices();
		notAllowedValue.clear();
		frozenDice.clearDices();
		tableOfDices.initialTableOfDices();
		t1.interrupt();
		t2.interrupt();
		
		sum = 0;
		timeToLive = 3;
		updateScore();
		botTurn = false;
		roll(e);

		if (playerId < numberOfPlayers - 1)
			playerId++;
		else
			playerId = 0;

		if (vsBots && playerId == numberOfPlayers - 1)
			botTurn = true;

		playerName.setText(players.get(playerId).getName());

		buttons.disableButton("next");
	}

	private void takeTheNearestValueBrick() {
		buttons.disableButton("stop");
		buttons.disableButton("choose");
		buttons.disableButton("delete");
		buttons.disableButton("roll");

		int tableSize = table.getTableSize();
		if (sum < table.getTableBrick(0).getBrickValue())
			return;

		for (int i = 0; i < table.getTableSize(); i++) {
			int currentValue = table.getTableBrick(i).getBrickValue();
			FlowPane fp = (FlowPane) table.getBrickFlowPane(i);
			if (currentValue == sum && fp.isVisible()) {
				changeBrickOnTableAppearance(i);
				return;
			}
		}

		for (int i = 0; i < players.size(); i++) {
			if (playerId == i)
				continue;
			Player player = players.get(playerId);
			Player stolenPlayer = players.get(i);
			if (stolenPlayer.getOnTopBrickValueInt() == sum) {
				// if detect the brick on top of other player, steal it
				stealBrick(player, stolenPlayer);
				changeBrickOnPlayerHandAppearance(player);

				// then change the stolen player appearance
				switch (i) {
				case 0:
					if (stolenPlayer.getOnTopBrick() != null) {
						score1.setText(Integer.toString(stolenPlayer.getPoint()));
						onTopValue1.setText(stolenPlayer.getOnTopBrickValueStr());
						worm1.setImage(stolenPlayer.getOnTopBrickImage());
					} else {
						score1.setText(Integer.toString(stolenPlayer.getPoint()));
						onTopValue1.setText(Integer.toString(0));
						worm1.setImage(null);
						fpOnTop1.setVisible(false);
					}
					break;
				case 1:
					if (stolenPlayer.getOnTopBrick() != null) {
						score2.setText(Integer.toString(stolenPlayer.getPoint()));
						onTopValue2.setText(stolenPlayer.getOnTopBrickValueStr());
						worm2.setImage(stolenPlayer.getOnTopBrickImage());
					} else {
						score2.setText(Integer.toString(stolenPlayer.getPoint()));
						onTopValue2.setText(Integer.toString(0));
						worm2.setImage(null);
						fpOnTop2.setVisible(false);
					}
					break;
				case 2:
					if (stolenPlayer.getOnTopBrick() != null) {
						score3.setText(Integer.toString(stolenPlayer.getPoint()));
						onTopValue3.setText(stolenPlayer.getOnTopBrickValueStr());
						worm3.setImage(stolenPlayer.getOnTopBrickImage());
					} else {
						score3.setText(Integer.toString(stolenPlayer.getPoint()));
						onTopValue3.setText(Integer.toString(0));
						worm3.setImage(null);
						fpOnTop3.setVisible(false);
					}
					break;
				}

				return;
			}
		}

		if (sum >= table.getTableBrick(tableSize - 1).getBrickValue())
			for (int i = 0; i < 16; i++)
				if (table.getBrickFlowPane(tableSize - 1 - i).isVisible()) {
					changeBrickOnTableAppearance(tableSize - 1 - i);
					return;
				}

		int findYou = 0;
		for (int i = 0; i < table.getTableSize(); i++)
			if (table.getTableBrick(i).getBrickValue() > sum && table.getBrickFlowPane(i).isVisible()) {
				findYou = i - 1;
				break;
			}

		for (int i = findYou - 1; i >= 0; i--) {
			if (table.getBrickFlowPane(i).isVisible()) {
				changeBrickOnTableAppearance(i);
				return;
			}
		}

	}

	@FXML
	public void handleStopButton(ActionEvent e) {
		takeTheNearestValueBrick();
	
		Player player = players.get(playerId);
	
		switch (playerId) {
		case 0:
			score1.setText(Integer.toString(player.getPoint()));
			break;
		case 1:
			if (!vsBots)
				score2.setText(Integer.toString(player.getPoint()));
			else if (botTurn)
				score2.setText(Integer.toString(bot.getPoint()));
	
			break;
		case 2:
			if (!vsBots)
				score3.setText(Integer.toString(player.getPoint()));
			else
				score3.setText(Integer.toString(bot.getPoint()));
	
			break;
		}
	
		buttons.disableButton("roll");
		buttons.enableButton("next", this, "nextTurn");
		System.out.println("The Brick on top is " + players.get(playerId).getOnTopBrickValueInt() + " with "
				+ players.get(playerId).getOnTopBrick().getWormCount() + " worms");
		System.out.println("+++++++++++++++++++++++++++++++");
	
	}

	private void changeBrickOnTableAppearance(int i) {
		table.setFlowPaneInvisible(i);

		if (!botTurn) {
			Player player = players.get(playerId);
			Brick brick = table.getTableBrick(i);
			player.addBrickToPlayerHand(brick);
			changeBrickOnPlayerHandAppearance(player);
		} else {
			switch (playerId) {
			case 1:
				fp2.setPrefHeight(125);
				fp2.setPrefWidth(200);
				onTopValue2.setText(bot.getOnTopBrickValueStr());
				worm2.setImage(bot.getOnTopBrickImage());
				fpOnTop2.setVisible(true);
				break;
			case 2:
				fp3.setPrefHeight(125);
				fp3.setPrefWidth(200);
				onTopValue3.setText(bot.getOnTopBrickValueStr());
				worm3.setImage(bot.getOnTopBrickImage());
				fpOnTop3.setVisible(true);
				break;
			}
		}
	}

	private void changeBrickOnPlayerHandAppearance(Player player) {
		switch (playerId) {
		case 0:
			fp1.setPrefHeight(125);
			fp1.setPrefWidth(200);
			onTopValue1.setText(player.getOnTopBrickValueStr());
			worm1.setImage(player.getOnTopBrickImage());
			fpOnTop1.setVisible(true);
			break;
		case 1:
			fp2.setPrefHeight(125);
			fp2.setPrefWidth(200);
			onTopValue2.setText(player.getOnTopBrickValueStr());
			worm2.setImage(player.getOnTopBrickImage());
			fpOnTop2.setVisible(true);
			break;
		case 2:
			fp3.setPrefHeight(125);
			fp3.setPrefWidth(200);
			onTopValue3.setText(player.getOnTopBrickValueStr());
			worm3.setImage(player.getOnTopBrickImage());
			fpOnTop3.setVisible(true);
			break;
		}
	}

	private void stealBrick(Player player, Player stolenPlayer) {
		Brick brick = stolenPlayer.getOnTopBrick();
		stolenPlayer.removeOnTopBrick();
		stolenPlayer.minusPoint(brick.getWormCount());
		player.addBrickToPlayerHand(brick);
		return;
	}

	@FXML
	public void roll(ActionEvent e) {
		buttons.enableButton("roll", this, "roll");
		buttons.disableButton("choose");
		buttons.disableButton("delete");

		for (int i = 0; i < tableOfDices.getSize(); i++) {
			Button button = (Button) tableOfDice.getChildren().get(i);
			button.setDisable(false);
		}
		
		Task<Void> rollTask = new Task<Void>() {
			@Override
			protected Void call() throws Exception {
				for (int i = 0; i < 10; i++) {
					tableOfDices.generateDiceRandomly();
					updateUI(() -> tableOfDices.rollDices(tableOfDice));
					Thread.sleep(50);
				}
				updateUI(() -> tableOfDices.paintDices(notAllowedValue, tableOfDice, buttons, DiceController.this));
				updateUI(() -> checkRemain());
				return null;
			}

			private void checkRemain() {
				if (!checkIfTheRemainDicesAreAbleToClick()) {
					for (int i = 0; i < tableOfDices.getSize(); i++) {
						Button btn = (Button) tableOfDice.getChildren().get(i);
						btn.setDisable(true);
					}
					
					buttons.disableButton("roll");
					buttons.disableButton("choose");
					buttons.disableButton("stop");
					buttons.disableButton("delete");
				}

//				if (botTurn && timeToLive > 0) {
//				updateUI(() -> {
//					botPlay();
//				}
//				);
//					timeToLive--;
//				}
			}
			private void updateUI(Runnable update) {
				Platform.runLater(update);
			}
		};
		
		
		
		Task<Void> botTask = new Task<Void>() {
			private void botPlay() {
				ArrayList<Integer> otherPlayers = new ArrayList<>();

//				if (!vsBots) {
					players.stream().map(Player::getOnTopBrickValueInt).forEach(otherPlayers::add);
//				} else {
					for(Player p: players)
						System.out.println(p);
					
					players.stream().limit(players.size() - 1) // Exclude the last player (bot)
							.map(Player::getOnTopBrickValueInt).forEach(otherPlayers::add);
//				}

				updateUI(() -> {
					bot.updateOtherPlayerOnTopBrickValue(otherPlayers);
					bot.updateBotDiceValue(tableOfDices, notAllowedValue);
					botChoice = bot.chooseDice();
				});

				updateUI(() -> {
					botChoices();
				});
			}

			private void botChoices() {
				System.out.println("Bot's choice = " + botChoice);
				for (int i = 0; i < tableOfDices.getSize(); i++) {
					MemoryDice currentDice = tableOfDices.getMemoryDice(i);
	//
////					if (botChoice == -1)
////						updateUI(() -> nextButton.fire());
	//
					if (currentDice.getDiceValue() == botChoice) {
						Button btn = (Button) tableOfDice.getChildren().get(i);
						updateUI(() -> btn.fire());
						currentDice.setChosenImageView((ImageView) btn.getGraphic());
					}

					updateUI(() -> chooseButton.fire());
	//
//					if (botMoves == 0) {
//						updateUI(() -> stopButton.fire());
//						updateUI(() -> nextButton.fire());
//						return;
//					}
	//
//					if (bot.getSteal()) {
//						updateUI(() -> stopButton.fire());
//						updateUI(() -> nextButton.fire());
//					}

//					updateUI(() -> {
//						try {
//							Thread.sleep(1500);
//						} catch (InterruptedException e) {
//							e.printStackTrace();
//						}
//						rollButton.fire();
//					});
				}
			}
			
			private void updateUI(Runnable update) {
				Platform.runLater(update);
			}

			@Override
			protected Void call() throws Exception {
				botPlay();
				return null;
			}
		};
		
		t2 = new Thread(botTask);
		PauseTransition pause = new PauseTransition(Duration.seconds(1)); // Adjust the duration as needed
		pause.setOnFinished(event -> t2.start());
//		pause.play();
		
		t1 = new Thread(rollTask);
		t1.setDaemon(true);	
		t1.start();
		buttons.disableButton("roll");
	}

	@FXML
	public void delete(ActionEvent e) {
		clickedButton = tableOfDices.getClickedButton();

		// after deleting the previous chosen die, make the images clickable
		for (int i = 0; i < tableOfDices.getSize(); i++) {
			Button btn = (Button) tableOfDice.getChildren().get(i);
			btn.setDisable(false);
		}

		for (int i = 0; i < tableOfDices.getSize(); i++) {
			if (tableOfDices.getMemoryDice(i).getDiceValue() == ((MemoryDice) clickedButton.getUserData())
					.getDiceValue()) {
				changeAllDisallowedDice(i);
			}
		}

		if (notAllowedValue.size() > 0)
			notAllowedValue.remove(notAllowedValue.size() - 1);

		buttons.disableButton("delete");

		if (!checkIfTheRemainDicesAreAbleToClick()) {
			buttons.disableButton("roll");
			disableDiceClicking();
			Platform.runLater(() -> buttons.enableButton("stop", this, "handleStopButton"));
		} else {
			calculateSum();
			updateScore();
			buttons.enableButton("roll", this, "roll");
		}
	}

	@FXML
	public void handleChooseButton(ActionEvent event) {
		boolean flag = false;
		clickedButton = tableOfDices.getClickedButton();
		for (int i : notAllowedValue) {
			if (i == ((MemoryDice) clickedButton.getUserData()).getDiceValue())
				flag = true;
		}

		if (!flag) {
			for (int i = 0; i < tableOfDices.getSize(); i++) {
				if (tableOfDices.getMemoryDice(i).getDiceValue() == ((MemoryDice) clickedButton.getUserData())
						.getDiceValue()) {
					changeAllAllowedDice(i);
				}
			}

			notAllowedValue.add(((MemoryDice) clickedButton.getUserData()).getDiceValue());
			frozenDice.addFrozenDie((MemoryDice) clickedButton.getUserData());

			calculateSum();
			buttons.disableButton("choose");
			buttons.enableButton("roll", this, "roll");

			// after choosing a die, disable mouse events on all dice.
			for (int i = 0; i < tableOfDices.getSize(); i++) {
				Button btn = (Button) tableOfDice.getChildren().get(i);
				btn.setDisable(true);
			}
		} else {
			System.out.println("not allowed value?");
			buttons.disableButton("choose");
			buttons.disableButton("roll");
			buttons.disableButton("delete");
			((MemoryDice) clickedButton.getUserData()).setAllowedDice();
			((MemoryDice) clickedButton.getUserData()).resetImageViewColor((ImageView) clickedButton.getGraphic());
			changeAllDisallowedDice(((MemoryDice) clickedButton.getUserData()).getDiceIndex());
			buttons.enableButton("next", this, "nextTurn");
		}
	}

	private void changeAllAllowedDice(int index) {
		tableOfDices.getMemoryDice(index).setDisallowedDice();
		Button btn = (Button) tableOfDice.getChildren().get(index);
		tableOfDices.getMemoryDice(index).setChosenImageView((ImageView) btn.getGraphic());
	}

	private void changeAllDisallowedDice(int index) {
		tableOfDices.getMemoryDice(index).setAllowedDice();
		Button btn = (Button) tableOfDice.getChildren().get(index);
		tableOfDices.getMemoryDice(index).resetImageViewColor((ImageView) btn.getGraphic());
	}

	public void handleNoRemainDices() {
		buttons.disableButton("roll");
	}

	public void calculateSum() {
		sum = 0;
		for (int i = 0; i < tableOfDices.getSize(); i++) {
			MemoryDice currentDice = tableOfDices.getMemoryDice(i);

			if (currentDice.isDisallowedDice()) {
				sum += currentDice.getDiceValue();
				if (currentDice.getDiceValue() == 6) {
					sum -= 1;
				}
			}
		}

		updateScore();
	}

	public boolean checkIfTheRemainDicesAreAbleToClick() {

		boolean areAbleToClick = false;
		for (int i = 0; i < tableOfDices.getSize(); i++) {
			MemoryDice cd = (MemoryDice) tableOfDices.getMemoryDice(i);
			if (!frozenDice.checkSameValue(cd)) {
				areAbleToClick = true;
				break;
			}
		}

		if (areAbleToClick)
			return true;
		else {
			// if all remain dice are prohibited, then set image view disable
			for (int i = 0; i < tableOfDices.getSize(); i++) {
				Button btn = (Button) tableOfDice.getChildren().get(i);
				ImageView image = (ImageView) btn.getGraphic();
				image.setDisable(true);
			}

			System.out.println("OH...");
			buttons.disableButton("roll");
			resetSum();
			disableDiceClicking();
			return false;
		}
	}

	private void resetSum() {
		sum = 0;
	}

//	private boolean isTheGameOver() {
//		boolean allDicesDisallowed = tableOfDices.getTableOfDices().values().stream()
//				.allMatch(MemoryDice::isDisallowedDice);
//
//		int remember = 0;
//		for (int i = 0; i < tableOfDices.getSize(); i++) {
//			MemoryDice diceLeft = tableOfDices.getMemoryDice(i);
//
//			if (!diceLeft.isDisallowedDice()) {
//				if (remember == 0)
//					remember = diceLeft.getDiceValue();
//				else if (remember == diceLeft.getDiceValue()) {
//					allDicesDisallowed = true;
//				} else {
//					allDicesDisallowed = false;
//					break;
//				}
//			}
//		}
//		return allDicesDisallowed;
//	}

	private void printVal() {
		for (int i = 0; i < tableOfDices.getSize(); i++)
			System.out.println("Dice at " + tableOfDices.getMemoryDiceIndex(i) + " is "
					+ tableOfDices.getMemoryDice(i).getDiceValue());

	}

	private void disableDiceClicking() {
		Platform.runLater(() -> updateScore());
	}

	public void updateScore() {
		score.setText(Integer.toString(sum));
		int ultimateMin = 0;
		int minscore = 21;
		int minScoreOfPlayers = 50;

		for (int i = 0; i < players.size(); i++) {
			if (playerId == i)
				continue;
			if (players.get(i).getOnTopBrick() != null) {
				if (players.get(i).getOnTopBrickValueInt() <= minScoreOfPlayers) {
					minScoreOfPlayers = players.get(i).getOnTopBrickValueInt();
				}
			}
		}

		for (int i = 0; i < table.getTableSize(); i++)
			if (table.getBrickFlowPane(i).isVisible()) {
				minscore = table.getTableBrick(i).getBrickValue();
				break;
			}

		if (minscore > minScoreOfPlayers)
			ultimateMin = minScoreOfPlayers;
		else
			ultimateMin = minscore;

		if (Integer.parseInt(score.getText()) >= ultimateMin)
			buttons.enableButton("stop", this, "handleStopButton");
	}

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		table = new Table(tableOfBricks);
		tableOfDices = new TableOfDices();
		notAllowedValue = new ArrayList<>();
		frozenDice = new FrozenDices();
		players = new ArrayList<>();
		bot = new Bot();
		tableOfDices.initialTableOfDices();

		buttons = new ButtonSet();
		buttons.addButton("roll", rollButton);
		buttons.addButton("choose", chooseButton);
		buttons.addButton("stop", stopButton);
		buttons.addButton("next", nextButton);
		buttons.addButton("endGame", endGameButton);
		buttons.addButton("delete", deleteButton);

		buttons.disableButton("choose");
		buttons.disableButton("delete");
		buttons.disableButton("stop");
		buttons.disableButton("next");

		// initialize dice
		for (int i = 0; i < tableOfDice.getChildren().size(); i++) {
			Button button = (Button) tableOfDice.getChildren().get(i);
			ImageView img = new ImageView(new Image(getClass().getResource("/images/dice-1.png").toExternalForm()));

			img.setFitHeight(button.getPrefWidth());
			img.setFitWidth(button.getPrefWidth());
			img.setPreserveRatio(true);

			button.setGraphic(img);
			button.setUserData(1);

			button.setDisable(true);
			button.setOpacity(1);
		}

		table.createTable();

//		bgs = new BackgroundSound();
//		bgs.playSound();
	}

	public void addPlayer(Player player) {
		players.add(player);
	}

	public void initializePlayer() {
		playerName.setText(players.get(playerId).getName());
		player1.setText("Player 1");
		player2.setText("Player 2");
		player3.setText("Player 3");

		if (numberOfPlayers == 1) {
			player1Img.setImage(players.get(0).getImage());

			if (vsBots) {
				setCharacterAppear(fp2);
				player2.setText("Bot");
				player2Img.setImage(players.get(1).getImage());
				numberOfPlayers++;
			} else
				setCharacterDisappear(fp2);

			setCharacterDisappear(fp3);

		} else if (numberOfPlayers == 2) {
			player1Img.setImage(players.get(0).getImage());
			player2Img.setImage(players.get(1).getImage());

			if (vsBots) {
				setCharacterAppear(fp3);
				player3.setText("Bot");
				player3Img.setImage(players.get(2).getImage());
				numberOfPlayers++;
			} else
				setCharacterDisappear(fp3);

		} else if (numberOfPlayers == 3) {
			player1Img.setImage(players.get(0).getImage());
			player2Img.setImage(players.get(1).getImage());
			player3Img.setImage(players.get(2).getImage());
		}
	}

	private void setCharacterDisappear(FlowPane fp) {
		fp.setDisable(true);
		fp.setVisible(false);
	}

	private void setCharacterAppear(FlowPane fp) {
		fp.setDisable(false);
		fp.setVisible(true);
	}

	public void setNumberOfPlayer(int number) {
		this.numberOfPlayers = number;
	}

	public void setVsBots() {
		vsBots = true;
	}
}
