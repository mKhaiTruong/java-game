package application;

import java.util.Random;

import javafx.animation.TranslateTransition;
import javafx.scene.effect.DropShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

public class Flake {
	private TranslateTransition transtrans;
	private double width;
	private double height;
	private double opacity;
	private double x;
	private double x1;
	private double y;
	private double z;
	private double o;
//	private final double TAU = Math.PI * 2;
	Random rand = new Random();
	
	public Flake(double w, double h) {
		this.width = w;
		this.height = h;
		this.reset();
	}
	
	private void reset() {
		x = 0;
		x1 = Math.random() * width;
		y = -5;
		z = Math.random() * 0.8 + 0.2;
		o = Math.random() * Math.PI;
	}
	
	public void update() {
		x = Math.cos(this.o + this.y * (1 - this.z) * 0.05) * this.z * 20 + x1;
		this.y += this.z * 8;
		if(this.y > height + 5) {
			this.reset();
		}
		
		return;
	}
	
	public void draw(Pane pane) {
		double r = this.z * 5.5 + 1.5;
		opacity = rand.nextDouble(r * 1);
		
		Circle circle = new Circle();
		circle.setCenterX(this.x);
		circle.setCenterY(this.y);
		circle.setRadius(r);
		circle.setOpacity(opacity);
		circle.setFill(Color.WHITE);

		// Adding a light yellow drop shadow
		DropShadow dropShadow = new DropShadow();
		dropShadow.setColor(Color.WHITE);
		circle.setEffect(dropShadow);
		pane.getChildren().add(circle);
		
		transtrans = new TranslateTransition();
		transtrans.setNode(circle);
		transtrans.setDuration(Duration.seconds(5));
		transtrans.setToY(height + 50); 
		transtrans.setOnFinished(e ->{
			pane.getChildren().remove(circle);
        });
		
        transtrans.setToX(this.x + r + (height - this.y) * Math.cos(o));
		transtrans.play();
	}
}
