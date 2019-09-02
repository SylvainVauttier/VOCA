package application;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import model.IoTClassNames;

public class ThingViewFactory {
	
	static ThingViewFactory factory = null;
	
	ImagePattern[] icones;
	String[] classNames;
	//int[] count;
	
	public ThingViewFactory()
	{
		int IoTClassListSize = IoTClassNames.names.length;
		
		icones =  new ImagePattern[IoTClassListSize];
		classNames = new String[IoTClassListSize];
		//count = new int[IoTSize];
		
		for (int i=0;i<IoTClassListSize;i++)
		{
			Image ic = new Image(getClass().getResourceAsStream("../img/"+IoTClassNames.names[i]+".png"),30,30,true,true);
			icones[i]=new ImagePattern(ic);
			classNames[i]=IoTClassNames.names[i];
		}
	}
	
	static ThingView buildView(int index, double x, double y)
	{
		return getThingViewFactory().buidView(index,x,y);
	}

	private ThingView buidView(int index, double x, double y) {
		// TODO Auto-generated method stub
		ThingView view= new ThingView(x,y);
		view.setFill(icones[index]);
		//view.icone=icones[index];
		return view;
	}

	private static ThingViewFactory getThingViewFactory() {
		// TODO Auto-generated method stub
		if (factory==null) factory = new ThingViewFactory();
		return factory;
	}

}
