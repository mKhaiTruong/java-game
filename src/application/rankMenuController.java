package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;
import java.util.ResourceBundle;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;

public class rankMenuController implements Initializable{
	
	private FrostEffect frostEffect = new FrostEffect(10);
	private ArrayList<Player> players;
	private ArrayList<Player> ps;
	private ArrayList<ImageView> imgs;
	private Timeline timeline;
	Random rand = new Random();
	
	private ImageView bg;
	@FXML
	private StackPane sp;
	@FXML
	private AnchorPane anhPane;

	@FXML
	private ImageView top1;
	@FXML
	private ImageView top2;
	@FXML
	private ImageView top3;
	@FXML
	private Button next;
	
	@FXML
    private void handleNextButton(ActionEvent e) throws IOException{
		Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("score.fxml"));
		Parent gameViewParent = loader.load();
		Scene scene = new Scene(gameViewParent);
		ScoreController scoreController = loader.getController();
		
		for (int i = 0; i < ps.size(); i++)
			ps.get(i).addInfoToFinalTable(ps.get(i), scoreController);
		
		timeline.stop();
		window.setMaxWidth(600);
	    window.setMaxHeight(500);
	    window.setX(350);
	    window.setY(75);
		window.setScene(scene);
	}
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
//		Image spritesheet = new Image(getClass().getResource("/images/f32.png").toExternalForm());
//		
//		for (double x = spriteSize; x < spritesheet.getWidth() - spriteSize; x += spriteSize) {
//            for (double y = spriteSize; y < spritesheet.getHeight() - spriteSize; y += spriteSize) {
//                Image texture = new Image("/f32.png", x, y,  false, false);
//                ImageView imageView = new ImageView(texture);
//                flakes.add(imageView);
//            }
//        }
		
		timeline = new Timeline(new KeyFrame(Duration.seconds(0.1), e -> {
			resetCycles();
		}));

		timeline.setCycleCount(Timeline.INDEFINITE);
		timeline.play();
	}
	
	private void resetCycles() {
		Flake flake = new Flake(anhPane.getScene().getWidth(), anhPane.getScene().getHeight());
		flake.update();
		flake.draw(anhPane);
	}

	public void setPlayers(ArrayList<Player> players) {
		this.players = players;
		this.ps = players;
		this.imgs = new ArrayList<>();
		imgs.add(top1);imgs.add(top2);imgs.add(top3);
		
		bg = new ImageView(new Image(getClass().getResource("/images/snowmoutain.png").toExternalForm()));
		bg.fitWidthProperty().bind(sp.widthProperty());
		bg.fitHeightProperty().bind(sp.heightProperty());
		bg.setEffect(frostEffect.getEffect());
		sp.getChildren().add(bg);
		bg.toBack();
     }
	
	public void rankPlayer(){
		// Sort the players based on points
        Collections.sort(players, Collections.reverseOrder());
        for(int i = 0; i < players.size(); i++) {
        	Image playerImage = players.get(i).getImage();
        	imgs.get(i).setImage(playerImage);
        }
	}
}
