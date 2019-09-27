package application;
	
import dao.DAO;
import dao.DaoObjet;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.stage.Stage;
import model.Thing;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Orientation;
import javafx.scene.input.MouseEvent;


public class Main extends Application {
	@Override
	public void start(Stage primaryStage) {
		
		//préinitialisation de la BD
		//évite un délai à la création du premier objet
		//inutile par la suite quand l'utilisateur sera inséré dans la base
		// après l'écran de présentation
		DAO.getDAO();
		
		VocaController ctr = new VocaController();
		
		VocaView view = new VocaView(primaryStage, ctr);
		ctr.setView(view);
	}
	
	
	
	@Override
	public void stop() throws Exception {
		// TODO Auto-generated method stub
		super.stop();
		dao.DAO.getDAO().stopServer();
	}



	public static void main(String[] args) {
		launch(args);
	}
}
