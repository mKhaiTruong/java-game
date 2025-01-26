package application;

import java.io.IOException;
import java.util.Random;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.animation.TranslateTransition;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

public class PickominoGame extends Application {
	private boolean vsBot = false;
	private int width = 300;
	private MenuBox menu;
	private MenuItem three;
	private MenuBox menuNumber;
	private Timeline timeline;
	private TranslateTransition transtrans;
	Random rand = new Random();

    private FXMLLoader loader = new FXMLLoader();
    private Parent gameViewParent;
    private Scene scene;
    
	static Color lightYellow = Color.rgb(255, 243, 207);
	static Color yellow = Color.rgb(232, 200, 114);
	static Color lightBlue = Color.rgb(201, 215, 221);
	static Color blue = Color.rgb(99, 122, 159);

//	@Override
//		public void start(Stage primaryStage) {
//			try {
//				FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("dice.fxml"));
//				Scene scene = new Scene(fxmlLoader.load(), 600, 400);
//				scene.getStylesheets().add(getClass().getResource("application.css").toExternalForm());
//				primaryStage.setScene(scene);
//				primaryStage.show();
//			} catch (Exception e) {
//				e.printStackTrace();
//			}
//		}
	
		private Parent createContent() {
			Pane root = new Pane();
			root.setPrefSize(1000, 725);
			loader.setLocation(getClass().getResource("playerDetailController.fxml"));
			try {
				gameViewParent = loader.load();
			} catch (IOException e3) {
				// TODO Auto-generated catch block
				e3.printStackTrace();
			}
			scene = new Scene(gameViewParent);
			
	
			ImageView img = new ImageView(new Image(getClass().getResource("/images/house.jpg").toExternalForm()));
			createParticles(root);
	
			// Bind image size to scene size
			img.fitWidthProperty().bind(root.widthProperty());
			img.fitHeightProperty().bind(root.heightProperty());
	
			root.getChildren().add(img);
	
			MenuItem newGame = new MenuItem("NEW GAME      ", "", width);
			newGame.setOnMouseClicked(e -> {
				try {
					handleNewGameButton(e);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			});
	
			MenuItem vsAi = new MenuItem("VS BOTS      ", "", width);
			vsAi.setOnMouseClicked(e -> {
				try {
					handleAiButton(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
	
			newGame.setOnMouseClicked(e -> {
				try {
					handleNewGameButton(e);
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			});
	
			MenuItem exit = new MenuItem("QUIT      ", "", width);
			exit.setOnMouseClicked(e -> System.exit(0));
			
			menu = new MenuBox("Pickomino", width, 400, newGame, vsAi, new MenuItem("RANK      ", "", width), exit);
	
			MenuItem one = new MenuItem("One Player      ", "", width);
			one.setOnMouseClicked(e -> {
				try {
					handleOneButton(e);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			});
			MenuItem two = new MenuItem("Two Players      ", "", width);
			two.setOnMouseClicked(ev -> {
				try {
					handleTwoButton(ev);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			});
	
			three = new MenuItem("Three Players      ", "", width);
			three.setOnMouseClicked(e -> {
				try {
					handleThreeButton(e);
				} catch (IOException e2) {
					// TODO Auto-generated catch block
					e2.printStackTrace();
				}
			});
	
			MenuItem back = new MenuItem("Return      ", "", width);
			back.setOnMouseClicked(e -> handleBackButton(e));
	
			menuNumber = new MenuBox("Pickomino", width, 400, one, two, three, back);
	
			menu.setTranslateX(0);
			menuNumber.setTranslateX(-300);
			root.getChildren().addAll(menu, menuNumber);
			
			return root;
		}

	private void createParticles(Pane pane) {
		timeline = new Timeline(new KeyFrame(Duration.seconds(0.2), e -> {
			resetCycles(pane);
			transtrans.play();
		}));

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}

	private void resetCycles(Pane pane) {
		transtrans = new TranslateTransition();
		int r = 4 + rand.nextInt(4);
		double xCoordinate = pane.getScene().getWindow().getWidth() + 10;
		double yCoordinate = rand.nextDouble(pane.getPrefHeight());
		double opacity = rand.nextDouble(r * 0.1);

		Circle circle = new Circle();
		circle.setCenterX(xCoordinate);
		circle.setCenterY(yCoordinate);
		circle.setRadius(r);
		circle.setOpacity(opacity);
		circle.setFill(lightYellow);

		// Adding a light yellow drop shadow
		DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(yellow);
		circle.setEffect(dropShadow);

		pane.getChildren().add(circle);

		transtrans = new TranslateTransition();
		transtrans.setNode(circle);
		transtrans.setDuration(Duration.seconds(20));
		transtrans.setToX(-2000);
		transtrans.setToY(rand.nextDouble(pane.getPrefHeight())); 
		transtrans.setOnFinished(e ->{
            pane.getChildren().remove(circle);
        });
		
		transtrans.play();
	}

	private void handleOneButton(MouseEvent e) throws IOException {
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
	    
	    PlayerDetailController playerDetailController = loader.getController();
	    playerDetailController.setNumber(1);

	    if (vsBot)
	        playerDetailController.setVsBots();

	    timeline.stop();
	    
	    window.setHeight(725);
		window.setWidth(1000);
		window.setScene(scene);
	    window.show();
	}

	private void handleTwoButton(MouseEvent ev) throws IOException {
		Stage window = (Stage) ((Node) ev.getSource()).getScene().getWindow();

		PlayerDetailController playerDetailController = loader.getController();
		playerDetailController.setNumber(2);

		if (vsBot)
			playerDetailController.setVsBots();

		timeline.stop();
		window.setHeight(725);
		window.setWidth(1000);
		window.setScene(scene);

	}

	private void handleThreeButton(MouseEvent e) throws IOException {
		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();

		PlayerDetailController playerDetailController = loader.getController();
		playerDetailController.setNumber(3);

		if (vsBot)
			playerDetailController.setVsBots();

		timeline.stop();
		window.setHeight(725);
		window.setWidth(1000);
		window.setScene(scene);
	}

	private void handleAiButton(MouseEvent e) throws IOException {
		menu.hide(-300);

		PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
		pause.setOnFinished(event -> {
			menuNumber.show(0);
		});

		pause.play();

		vsBot = true;
		three.setDisable(true);
		three.setVisible(false);
	}

	private void handleNewGameButton(MouseEvent e) throws IOException {
		menu.hide(-300);

		PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
		pause.setOnFinished(event -> {
			menuNumber.show(0);
		});

		pause.play();
	}

	private void handleBackButton(MouseEvent e) {
		menuNumber.hide(-300);
		PauseTransition pause = new PauseTransition(Duration.seconds(0.5));
		pause.setOnFinished(event -> {
			menu.show(0);
			three.setDisable(false);
			three.setVisible(true);
		});
		pause.play();

		vsBot = false;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		Scene scene = new Scene(createContent());
		scene.setOnKeyPressed(e -> {

			if (e.getCode() == KeyCode.SPACE) {
				if (menu.isVisible())
					menu.hide(-300);
				else
					menu.show(0);
			}

		});
		primaryStage.setTitle("Test");
//		primaryStage.initStyle(StageStyle.TRANSPARENT);
		primaryStage.setScene(scene);
		primaryStage.show();
	}


	public static void main(String[] args) {
		launch(args);
	}
}
