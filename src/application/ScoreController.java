package application;

import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

public class ScoreController implements Initializable{
	private ArrayList<Player> playerDetails = new ArrayList<>();
	
	@FXML
	private Label score;
	@FXML
	private TableView<Player> table;
	@FXML
	private TableColumn<Player, String> nameCol;
	@FXML
	private TableColumn<Player, Integer> triedCol;
	@FXML
	private TableColumn<Player, Integer> countCol;
	@FXML
	private TableColumn<Player, Integer> collectedCol;
	
	private ObservableList<Player> playerList;

	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		playerList = FXCollections.observableArrayList();
		
		triedCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("tried"));
		nameCol.setCellValueFactory(new PropertyValueFactory<Player, String>("name"));
		countCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("wormCount"));
		collectedCol.setCellValueFactory(new PropertyValueFactory<Player, Integer>("wormCollected"));
		table.setItems(playerList);
	}
	
	public void setData(int index, String name, int wormCount) {
		playerDetails.add(new Player(name, index, wormCount, 0)); 
	}
	
	public void update() {
		int wormCollected = 0;
		for(int i = 0; i < playerDetails.size(); i++) {
			wormCollected += playerDetails.get(i).getWormCount();
			playerDetails.get(i).setWormCollected(wormCollected);
			playerList.add(playerDetails.get(i));
		}
		
		score.setText(Integer.toString(wormCollected));
	}
}
