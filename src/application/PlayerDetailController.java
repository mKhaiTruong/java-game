package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.effect.BlurType;
import javafx.scene.effect.BoxBlur;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.effect.Effect;
import javafx.scene.effect.InnerShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.paint.CycleMethod;
import javafx.scene.paint.LinearGradient;
import javafx.scene.paint.Stop;
import javafx.stage.Stage;
import javafx.util.Duration;

public class PlayerDetailController implements Initializable{

    private FrostEffect frostEffect = new FrostEffect(20);
	private DropShadow dropShadow = new DropShadow();
	private Color darkBlue = Color.web("#141E46");
	private Color beige = Color.web("#FFF5E0");
	private FXMLLoader loader = new FXMLLoader();
	private CharacterSet cs = new CharacterSet();;
	private int number;
	private int playerId;
	private Player player;
	private Bot bot;
	private ArrayList<Player> players;
	private String[] playerLabels = {"Player 1", "Player 2", "Player 3"};
	private String showLabel;
	private boolean vsBots = false;
	
	@FXML
	private Label playerLabel;
	@FXML
	private ImageView player1;
	@FXML
	private Label label1;
	@FXML
	private ImageView player2;
	@FXML
	private Label label2;
	@FXML
	private ImageView player3;
	@FXML
	private Label label3;
	@FXML
	private ImageView yanfei;
	@FXML
	private Label labeyanfei;
	@FXML
	private AnchorPane anchorPane;
	
	@FXML
	private StackPane root;
	@FXML
	private Button play;
	@FXML
	private GridPane gp;
	@FXML
	public MediaView mv;
	
	@FXML
    private void handlePlayGameButton(MouseEvent e) throws IOException{
		Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		loader.setLocation(getClass().getResource("dice.fxml"));
		Parent gameViewParent = loader.load();
		Scene scene = new Scene(gameViewParent);
		
		DiceController diceController = loader.getController();
		for(Player player: players)
			diceController.addPlayer(player);
		
		if(vsBots) {
			bot = new Bot();
			bot.setName("bot");
			players.add(bot);
			diceController.addPlayer(bot);
			diceController.setVsBots();
		}
		
		setNumberOfPlayer();
		diceController.initializePlayer();
		window.setScene(scene);
		window.setHeight(725);
		window.setWidth(1000);
		window.setX(200);
		window.setY(40);
	}
	
//	@FXML
//    private void handleReturnButton(MouseEvent e) throws IOException{
////		Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
////		
////		loader.setLocation(getClass().getResource("mainMenu.fxml"));
////		Parent gameViewParent = loader.load();
////		Scene scene = new Scene(gameViewParent);
////		
////		window.setScene(scene);
//		Platform.runLater(() -> {
//	        String[] args = {};
//	        PickominoGame.launch(PickominoGame.class, args);
//	    });
//
//		Stage window = (Stage) ((Node) e.getSource()).getScene().getWindow();
//	    window.close();
//	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		mv.fitWidthProperty().bind(root.widthProperty());
//		mv.fitHeightProperty().bind(root.heightProperty());
//	    MediaPlayer mp= new MediaPlayer(new Media(getClass().getResource("/videos/scene3.mp4").toExternalForm()));
//	    mv.setMediaPlayer(mp);
//	    mv.setPreserveRatio(true);
//	    mv.toBack();
//	    
//	    mp.setOnEndOfMedia(() -> {
//            mp.seek(Duration.ZERO);
//            mp.play();
//        });
//	    
//	    mp.play();
//	    mv.setEffect(frostEffect);
		
		ImageView img = new ImageView(new Image(getClass().getResource("/images/summer.png").toExternalForm()));
		img.fitWidthProperty().bind(root.widthProperty());
		img.fitHeightProperty().bind(root.heightProperty());
		img.setEffect(frostEffect.getEffect());

		root.getChildren().add(img);
		img.toBack();
		initailInformation();
	}
	
	private void initailInformation() {
		playerId = 0;
		
		Stop[] stops = new Stop[] {new Stop(0, beige), new Stop(0.65, beige), new Stop(1, beige)};
		LinearGradient lg = new LinearGradient(0, 0, 0, 1, true, CycleMethod.NO_CYCLE, stops);
		BackgroundFill bgFill = new BackgroundFill(lg, CornerRadii.EMPTY, Insets.EMPTY);
		anchorPane.setBackground(new Background(bgFill));
		anchorPane.setStyle("-fx-background-color: rgba(0, 00, 0, 0.1)");
		cs.createCharacterSet();
		
		addHoverEffect(play);
        
        for(int i = 0; i < gp.getChildren().size(); i++) {
        	cs.addCharacter(new Character(Integer.toString(i)));
        	
        	VBox vbox = (VBox) gp.getChildren().get(i);
        	StackPane sp = (StackPane) vbox.getChildren().get(0);
        	ImageView imageView = (ImageView) sp.getChildren().get(0);
        	Label l = (Label) sp.getChildren().get(1);
        	
        	vbox.setUserData(i);
			setMouseEvents(imageView, vbox, l);
		}
	}

	private void setMouseEvents(ImageView imageView, VBox vb, Label label) {
		showLabel = playerLabels[playerId];
		
		vb.setOnMouseClicked(e -> {
			if((Integer) vb.getUserData() == 0) {
				player = new Player();
				player.setName("chick 1");
				player.setImage(cs.getCharacter(0).getImage());
				players.add(player);
				playerId++;
			}
			if((Integer) vb.getUserData() == 1) {
				player = new Player();
				player.setName("chick 2");
				player.setImage(cs.getCharacter(1).getImage());
				players.add(player);
				playerId++;
			}
			if((Integer) vb.getUserData() == 2) {
				player = new Player();
				player.setName("chick 3");
				player.setImage(cs.getCharacter(2).getImage());
				players.add(player);
				playerId++;
			}
			if((Integer) vb.getUserData() == 3) {
				player = new Player();
				player.setName("yanfei");
				player.setImage(cs.getCharacter(3).getImage());
				players.add(player);
				playerId++;
			}
			
			label.setText(showLabel);
			label.setVisible(true);
			label.setTextFill(darkBlue);
			imageView.setOpacity(0.5);
			imageView.setDisable(true);
			setButton();
		});
		
		vb.addEventFilter(MouseEvent.MOUSE_ENTERED, event -> {
			imageView.setEffect(new ColorAdjust(0, 0, 0, -0.2));
			imageView.setEffect(new DropShadow());
			
			vb.setEffect(new InnerShadow(BlurType.values()[0], 
                    Color.DARKBLUE, 0.5, 0.3f, 0.2f, 0.2f));
			event.consume();
		});

		vb.addEventFilter(MouseEvent.MOUSE_EXITED, event -> {
			imageView.setEffect(new ColorAdjust(0, 0, 0, 0));
			vb.setEffect(new ColorAdjust(0, 0, 0, 0));
			event.consume();
		});
	}

	private void addHoverEffect(Button button) {
		button.setOnMouseEntered(event -> button.setEffect(dropShadow));
        button.setOnMouseExited(event -> button.setEffect(null));
	}
	
	public void setNumber(int n) {
		number = n;
		
		players = new ArrayList<>();
		loader.setLocation(getClass().getResource("DiceController.fxml"));
		
		setButton();
	}
	
	public void setNumberOfPlayer() {
		//set number of players in diceController
		((DiceController) loader.getController()).setNumberOfPlayer(number);
	}
	
	private void setButton() {
		if(playerId < number) {
			showLabel = playerLabels[playerId];
        	play.setDisable(true);
        	play.setVisible(false);
        	playerLabel.setText("Player " + (playerId + 1) + "!" +
								" Choose your character!!!");
        } else {
        	play.setDisable(false);
        	play.setVisible(true);
        	playerLabel.setText("Players");
        	for(int i = 0; i < gp.getChildren().size(); i++) {
        		VBox vbox = (VBox) gp.getChildren().get(i);
        		vbox.setOpacity(0.75);
        		vbox.setDisable(true);
        		
            	StackPane sp = (StackPane) vbox.getChildren().get(0);
        		Label l = (Label) sp.getChildren().get(1);
        		l.setOpacity(1);
        	}
        }
	}
	
	public void setVsBots() {
		vsBots = true;
	}
}
