package application;

import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import model.Thing;

public class ThingView extends Rectangle {
	
	Thing thing = null;
	//ImagePattern icone;
	
	public ThingView(double x, double y) {
		super(x,y,30,30);
		
	}

}
