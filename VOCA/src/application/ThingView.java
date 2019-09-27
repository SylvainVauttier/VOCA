package application;


import javafx.event.EventHandler;
import javafx.geometry.Bounds;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.effect.DropShadow;
import javafx.scene.input.ClipboardContent;
import javafx.scene.input.Dragboard;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.scene.transform.Translate;
import model.IoTClassNames;
import model.Thing;

public class ThingView extends Rectangle {
	
	String name;
	Thing thing = null;
	Text label = null;
	double labelOffset;
	//ImagePattern icone;
	protected Thing model;
	boolean active=false;
	
	public ThingView(double x, double y) {
		super(x,y,30,30);
		setEffect(new DropShadow());
		
		label = new Text(x,y+40,IoTClassNames.names[VocaView.selectedIoTtool]);
		Bounds lb = label.getBoundsInLocal();
		labelOffset=15-lb.getWidth()/2;
		label.setX(x+labelOffset);
		
		this.setOnMousePressed(mouseEvent->{
			VocaView.pressedThing=this;
		});
		
		this.setOnMouseReleased(new EventHandler<MouseEvent>() {
		
		public void handle(MouseEvent event) {
			//Rectangle r = (Rectangle) event.getSource();
			//r.setFill(Color.ALICEBLUE);
			if (VocaView.draggedThing!=null) 
			{
				VocaView.centerPane.getChildren().remove(VocaView.dragRectangle);
				VocaView.dragRectangle=null;
				
				double x = event.getX();
				double y = event.getY();
				
				x -=15;
				y -=15;
				
				if (x<0) x=0;
				if (y<0) y=0;
				
				VocaView.draggedThing.move(x, y);
				VocaView.draggedThing=null;
				
			}
			VocaView.pressedThing=null;
			event.consume();
		}
		
		});
		
//		this.setOnDragDetected(mouseEvent->{
//			System.out.println("Dragged");
//			//Dragboard db = this.startDragAndDrop(TransferMode.NONE);
//			//ClipboardContent content = new ClipboardContent();
//			VocaView.draggedThing=this;
//			//mouseEvent.consume();
//		});
		
//		this.setOnDragDone(dragEvent->{
//			System.out.println("Dragged done");
//			double dx = dragEvent.getX();
//			double dy = dragEvent.getY();
//			
//			dx -=15;
//			dy -=15;
//			
//			if (dx<0) dx=0;
//			if (dy<0) dy=0;
//			
//			Translate translate = new Translate();
//			translate.setX(dx);
//			translate.setY(dy);
//			
//			this.getTransforms().add(translate);
//			
//			dragEvent.consume();
//		});
		
		this.setOnContextMenuRequested(contextMenuEvent->{
			//System.out.append("menu");
			VocaView.thingContextMenu.show(this,contextMenuEvent.getScreenX(),contextMenuEvent.getScreenY());
		});
	}
	

	public void paint(Pane p)
	{
		ColorAdjust ca = new ColorAdjust();
		if (active) ca.setBrightness(1);
		else ca.setBrightness(0.5);
		ca.setInput(new DropShadow());
		this.setEffect(ca);
		p.getChildren().addAll(this,this.label);
	}
	
	public void move(double x,double y)
	{
		this.setX(x);
		this.setY(y);
		this.label.setX(x+labelOffset);
		this.label.setY(y+40);
	}
	
	public void erase(Pane p)
	{
	  p.getChildren().removeAll(this,this.label);
	}


	public void toggle() {
		// TODO Auto-generated method stub
		ColorAdjust ca = new ColorAdjust();
		
		if (active)
		{
			active=false;
			ca.setBrightness(0.5);
			ca.setInput(new DropShadow());
			this.setEffect(ca);
		}
		else
		{
			active=true;
			ca.setBrightness(0);
			ca.setInput(new DropShadow());
			this.setEffect(ca);
		}
		
	}

}
