//package application;
	
import javafx.geometry.Insets;

import javafx.application.Application;
import javafx.scene.image.Image;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.ImageView;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.layout.HBox;
import javafx.scene.control.ProgressBar;


public class PokeBattle extends Application {
	private Pokemon user;
	private Pokemon opponent;
	private ProgressBar pb1 = new ProgressBar();
	private ProgressBar pb2 = new ProgressBar();
	private Move[] moves = new Move[4];
	@Override
	public void start(Stage primaryStage) {
		BorderPane pane = new BorderPane();
		user = Pikachu();
		opponent = BlueOne();
		HBox top = getPokemon(opponent,"file:pokemon1.jpg", false);
		HBox center = getPokemon(user, "file:pokemon2.jpg", true);
		pane.setTop(top);
		pane.setCenter(center);
		while(!user.isFainted()) {
			HBox bottom = new HBox(15);
			bottom.getChildren().add(new Label("What should " + user.getName() + "do?"));
			setButtons(bottom, true);
			pane.setBottom(bottom);
			if(user.isFainted()) {
				System.exit(1);
			}
		}
		Scene scene = new Scene(pane, 500, 500);
		primaryStage.setTitle("PokeBattle");
		primaryStage.setScene(scene);
		primaryStage.show();
	}
	private Pokemon Pikachu() {
		Move move1 = new Move("Bite", 5, "GRASS");
		Move move2 = new Move("Electric", 10, "FIRE");
		Move move3 = new Move("Bubble", 3, "WATER");
		Move move4 = new Move("Scream", 2, "FLYING");
		Move[] moves = {move1, move2, move3, move4};
		Pokemon pikachu = new Pokemon("Pikachu", 15, 25, 5, "FIRE", moves);
		return pikachu;
	}
	private Pokemon BlueOne() {
		Move move1 = new Move("Bites", 2, "GRASS");
		Move move2 = new Move("Anything", 3, "FIRE");
		Move move3 = new Move("Bubble", 10, "WATER");
		Move move4 = new Move("Swim", 5, "FLYING");
		Move[] moves = {move1, move2, move3, move4};
		Pokemon blueone = new Pokemon("BlueOne", 15, 25, 5, "WATER", moves);
		return blueone;
	}
	private HBox getPokemon(Pokemon p, String s, boolean side) {
		HBox hBox = new HBox(15);
		hBox.setPadding(new Insets(15, 15, 15, 15));
		ImageView iv1 = new ImageView(new Image(s));// file:pokemon1.jpg", "file:pokemon2.jpg"
		iv1.setFitWidth(100);
		iv1.setFitHeight(100);
		hBox.getChildren().add(pokemonInfo(side));
		hBox.getChildren().add(iv1);
		return hBox;
	}
	private VBox pokemonInfo(boolean side) {
		VBox info = new VBox(15);
		info.setPadding(new Insets(15,5,5,5));
		Pokemon p = null;
		if(side) {
			p = user;
		} else {
			p = opponent;
		}
		info.getChildren().add(new Label(p.getName()+p.getLevel()));
		if(p == user) {
			info.getChildren().add(pb1);
		} else {
			info.getChildren().add(pb2);
		}
		return info;
	}
	private void setButtons(HBox pane, boolean side) {
		VBox pane1 = new VBox(15);
		Button fight = new Button("FIGHT");
		fight.setOnAction(e -> {
			pane.getChildren().clear();
			pane.getChildren().add(new Label("Choose a move."));
			setMoveButtons(pane,side);
		});
		pane1.getChildren().add(new Button("FIGHT"));
		pane1.getChildren().add(new Button("POKeMON"));
		VBox pane2 = new VBox(15);
		pane2.getChildren().add(new Button("BAG"));
		pane2.getChildren().add(new Button("RUN"));
		pane.getChildren().add(pane1);
		pane.getChildren().add(pane2);
	}
	private void controlHP() {
		
	}
	private void setMoveButtons(HBox pane, boolean side) {
		VBox pane1 = new VBox();
		VBox pane2 = new VBox();
		ProgressBar pb;
		if(side) {
			moves = user.getMoves();
		} else {
			moves = user.getMoves();
		}
		if(side) {
			pb = pb2;
		} else {
			pb = pb1;
		}
		Button move1 = new Button(moves[1].getName());
		move1.setOnAction(e -> {
			//moves[1].setCurrentHP(moves[1].getPower() - moves[1].getPower() *  user.compareType(moves[1]))
			pb.setProgress(moves[1].getPower() * user.compareType(moves[1]));
			pane.getChildren().clear();
			pane.getChildren().add(new Label(user.getName()+" used " + moves[1].getName() + "!"));
		});
		Button move2 = new Button(moves[2].getName());
		move2.setOnAction(e -> {
			pane.getChildren().clear();
			pane.getChildren().add(new Label(user.getName()+" used " + moves[2].getName() + "!"));
		});
		Button move3 = new Button(moves[3].getName());
		move3.setOnAction(e -> {
			pane.getChildren().clear();
			pane.getChildren().add(new Label(user.getName()+" used " + moves[3].getName() + "!"));
		});
		Button move4 = new Button(moves[4].getName());
		move4.setOnAction(e -> {
			pane.getChildren().clear();
			pane.getChildren().add(new Label(user.getName()+" used " + moves[4].getName() + "!"));
		});
		pane1.getChildren().addAll(move1, move2);
		pane2.getChildren().addAll(move3, move4);
		pane.getChildren().addAll(pane1, pane2);
	}
	public static void main(String[] args) {
		launch(args);
	}
}
