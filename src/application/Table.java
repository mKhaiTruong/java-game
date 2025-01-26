package application;

import java.util.ArrayList;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.GridPane;

public class Table {
	private ArrayList<Brick> tableOfBricks;
	private int numberOfBrick;
	private GridPane gridPane;

	public Table(GridPane gridPane) {
		this.gridPane = gridPane;
		numberOfBrick = 16;
	}

	public void createTable() {
		tableOfBricks = new ArrayList<>();

		for (int i = 0; i < numberOfBrick; i++) {
			FlowPane flowPane = (FlowPane) this.gridPane.getChildren().get(i);
			
			String value = ((Label) flowPane.getChildren().get(0)).getText();
			tableOfBricks.add(new Brick(value, new Worm(value), Integer.toString(i)));
		}
	}

	public void addBrickToTable(Brick brick) {
		tableOfBricks.add(brick);
	}

	public Brick removeBrickFromTable(int index) {
		return tableOfBricks.remove(index);
	}

	public Brick getTableBrick(int index) {
		return tableOfBricks.get(index);
	}

	public int getTableSize() {
		return this.tableOfBricks.size();
	}

	public int getBrickIndex(int value) {
		for (Brick brick : tableOfBricks)
			if (brick.getBrickValue() == value)
				return brick.getBrickIndex();

		return -1;
	}

	public void setFlowPaneInvisible(int index) {
		getBrickFlowPane(index).setVisible(false);
	}
	
	public void setFlowPaneVisible(int index) {
		getBrickFlowPane(index).setVisible(true);
	}
	
	public FlowPane getBrickFlowPane(int index) {
		Brick brick = tableOfBricks.get(index);
		FlowPane flowPane = (FlowPane) this.gridPane.getChildren().get(brick.getBrickIndex());
		return flowPane;
	}
}
