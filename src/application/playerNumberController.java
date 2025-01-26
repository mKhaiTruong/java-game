package application;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.effect.DropShadow;
import javafx.stage.Stage;

public class playerNumberController implements Initializable{
	private boolean vsBots = false;

	@FXML 
	private Button onePlayer;
	@FXML 
	private Button twoPlayers;
	@FXML 
	private Button threePlayers;
	@FXML
	private Button toMenu;
	
	private DropShadow dropShadow = new DropShadow();
	
	@FXML
    private void handleToMenuButton(ActionEvent e) throws IOException{
		Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("mainMenu.fxml"));
		Parent gameViewParent = loader.load();
		Scene scene = new Scene(gameViewParent );

		window.setScene(scene);
	}
	
	@FXML
    private void handleOneButton(ActionEvent e) throws IOException{
		Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("playerDetailController.fxml"));
		Parent gameViewParent = loader.load();
		Scene scene = new Scene(gameViewParent );
		
		PlayerDetailController playerDetailController = loader.getController();
		playerDetailController.setNumber(1);

		if(vsBots)
			playerDetailController.setVsBots();
		
		window.setScene(scene);
	}

	@FXML
    private void handleTwoButton(ActionEvent e) throws IOException{
		Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("playerDetailController.fxml"));
		Parent gameViewParent = loader.load();
		Scene scene = new Scene(gameViewParent );
		
		PlayerDetailController playerDetailController = loader.getController();
		playerDetailController.setNumber(2);

		if(vsBots)
			playerDetailController.setVsBots();
		
		window.setScene(scene);
	}
	
	@FXML
    private void handleThreeButton(ActionEvent e) throws IOException{
		Stage window = (Stage)((Node) e.getSource()).getScene().getWindow();
		
		FXMLLoader loader = new FXMLLoader();
		loader.setLocation(getClass().getResource("playerDetailController.fxml"));
		Parent gameViewParent = loader.load();
		Scene scene = new Scene(gameViewParent );
		
		PlayerDetailController playerDetailController = loader.getController();
		playerDetailController.setNumber(3);
		
		if(vsBots)
			playerDetailController.setVsBots();
		
		window.setScene(scene);
	}
	
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		addHoverEffect(onePlayer);
        addHoverEffect(twoPlayers);
        addHoverEffect(threePlayers);
	}

	private void addHoverEffect(Button button) {
		button.setOnMouseEntered(event -> button.setEffect(dropShadow));
        button.setOnMouseExited(event -> button.setEffect(null));
	}
	
	public void setVsBots() {
		vsBots = true;
		threePlayers.setDisable(true);
		threePlayers.setOpacity(0.5);
	}
	
}
