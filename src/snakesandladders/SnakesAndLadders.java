/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package snakesandladders;

import java.util.ArrayList;
import java.util.Random;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Border;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;

/**
 * This is the class which brings the game together and renders it on the screen.
 * This class inherits from {@link javafx.application.Application}
 * All attributes in this class do not have any inherent use as such in Object-Oriented terms.
 * The are all Nodes which have to be accessed by the event-handlers inside {@link #start(javafx.stage.Stage) start} method of the class.
 * 
 * @author Shantanu
 */
public class SnakesAndLadders extends Application {
    
    /**Object of {@link java.util.Random} which is used to roll the dice and for other functionalities.*/
    static Random rnd = new Random();
    
    /**Points to the Player who is to play.*/
    Player currentPlayer;
    /**An {@link java.util.ArrayList} of {@link snakesandladders.Player players} on the board.*/
    ArrayList players;
    
    /**Points to the index of the current player.*/
    Integer currentPlayerIndex=0;
    
    /**A {@link javafx.scene.control.Label label} which displays prompts to the players.*/
    static Label prompt = new Label();
    
    /**A {@link javafx.scene.image.ImageView} which displays {@link javafx.scene.image.Image} objects of the dice.*/
    static ImageView imageView = new ImageView();
        
    /**{@link javafx.scene.control.Spinner} which is used to set the number of snakes.*/
    static Spinner snakecounter= new Spinner(1, 15, 1,1);
    
    /**{@link javafx.scene.control.Spinner} which is used to set the number of ladders.*/
    static Spinner laddercounter= new Spinner(1, 15, 1,1);
    
    /**An array consisting of the {@link javafx.scene.shape.Circle#getFill() colours} of the added players.*/
    static Paint[] fills = new Paint[4];
    
    /**{@link javafx.scene.shape.Circle} which is used to display the colour of the {@link #currentPlayer}.*/
    static Circle playerCircle = new Circle(15.0);
    
    /**{@link javafx.scene.control.Label} used to display the winner.*/
    static Label winnerLbl = new Label();

    public SnakesAndLadders() {
        this.players = new ArrayList<Player>();
    }
    
    /**
     * Puts the game together.
     */
    @Override
    public void start(final Stage primaryStage) {
        final GridPane grid = new GridPane();
        new Alert(Alert.AlertType.INFORMATION, "Welkom bij het spel Slangen en Ladders!\n\n1. Kies zometeen, na op 'OK' te klikken, hoeveel Slangen (rood) en hoeveel ladders (groen) je wilt.\n\n2. Druk daarna op 'Start Spel'.\n\n3. Voeg daarna minimaal 1 en maximaal 4 spelers toe.\n\n4. Klik daarna om de beurt op 'Gooi Dobbelsteen'.\nJe pion zal automatisch verplaatsen naar de plek die je hebt gegooid.").showAndWait();
                new Alert(Alert.AlertType.WARNING, "I - Als je op een ladder terrecht komt, ga je met de ladder omhoog. WINST!!!\n\nII - Maar als je op een slang komt, ga je dus met de slang terug naar beneden. Dat moet je dus voorkomen!!\n\nIII - De speler die als eerst 100 bereikt, wint!!\n\nVeel speelplezier!").showAndWait();
                    new Alert(Alert.AlertType.INFORMATION, "OPDRACHT INFORMATICA PO JAVAFX IN NETBEANS IDE\n\nGemaakt door:\nToine Rademacher\nWouter Bos\nNiels Dijkstra\nJoost verkaik\nPelle Limburg\nKoen van Winden").showAndWait();

        grid.setAlignment(Pos.TOP_CENTER);
        
        Button rollDice = new Button("Gooi Dobbelsteen");
        HBox hbox = new HBox(5.0,rollDice);
        hbox.setAlignment(Pos.CENTER);
        HBox hbox2 = new HBox(5.0,prompt);
        
        VBox vbox = new VBox(5.0, grid,hbox,hbox2);
        final StackPane root = new StackPane();
        
        
        //create init window
        
        GridPane initG = new GridPane();
        
        initG.alignmentProperty().set(Pos.CENTER);
        initG.setHgap(10);
        initG.setVgap(10);
        initG.setPadding(new Insets(10,10,10,10));
        primaryStage.setAlwaysOnTop(true);
        //Width Length Options
        Scene initScene = new Scene(initG,450,200);
        Button initBtn = new Button("Start Spel");
        Label l1 = new Label("Aantal Slangen (1-15): ");
        Label l2 = new Label("Aantal Ladders (1-15): ");
      

        initG.add(l1,0,0);
        initG.add(snakecounter, 1, 0);
        initG.add(l2,0,1);
        initG.add(laddercounter,1,1);
        initG.add(initBtn,1,2);
        
        primaryStage.centerOnScreen();
        
        //end
        
        Canvas canvas = new Canvas(500, 650);
        final GraphicsContext gc = canvas.getGraphicsContext2D();
        
        gc.setLineWidth(5);
        
        vbox.setAlignment(Pos.CENTER);
        final Scene scene = new Scene(root, 500, 650);
        Board.initialiseSquares();
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        //root.getChildren().add(imageView);
        
        final Image one = new Image(SnakesAndLadders.class.getResource("1.png").toString());
        final Image two = new Image(SnakesAndLadders.class.getResource("2.png").toString());
        final Image three = new Image(SnakesAndLadders.class.getResource("3.png").toString());
        final Image four = new Image(SnakesAndLadders.class.getResource("4.png").toString());
        final Image five = new Image(SnakesAndLadders.class.getResource("5.png").toString());
        final Image six = new Image(SnakesAndLadders.class.getResource("6.png").toString());
        
        
        for(Integer i =1;i<101;i++){
            Square sq = Board.squares[i];
            grid.add(sq,sq.getGridX(),sq.getGridY());
            grid.add(new Rectangle(50, 50, new Color(rnd.nextDouble(), rnd.nextDouble(),rnd.nextDouble() , 0.3)),
                    sq.getGridX(),sq.getGridY());
        }
        
        
        
        playerCircle.setFill(new Color(0, 0, 0, 0));
        Button addPlayer = new Button("Voeg Speler Toe");
        addPlayer.setOnAction((ActionEvent event) -> {
            if(Player.NumberOfPlayers<4){
                Player pl = new Player();
                players.add(pl);
                grid.add(pl,0,9);
                fills[Player.NumberOfPlayers-1]=pl.getFill();
                playerCircle.setFill(fills[0]);
                prompt.setText("Speler 1 is aan de beurt");
            }
            else{
                prompt.setText("Je kan niet meer dan 4 spelers toevoegen.");
            }
        });
        grid.setGridLinesVisible(true);
        
        root.getChildren().add(canvas);
        root.getChildren().add(vbox);
        
        //winner scene
        final Rectangle winRect = new Rectangle(500, 650);
        final Rectangle winRect2 = new Rectangle(500, 650);
        
        //Scene winSc = new Scene(winnerLbl,500,500);
        //end
        hbox.getChildren().add(imageView);
        hbox.getChildren().add(addPlayer);
        hbox2.getChildren().add(playerCircle);
        hbox2.setAlignment(Pos.CENTER);
        rollDice.setOnAction((ActionEvent e) -> {
            if(Player.NumberOfPlayers==0){
                prompt.setText("Voeg eerst minimaal één speler toe!");
            }else{
                prompt.setText("");
                Integer num = rnd.nextInt(6)+1;
                switch(num){
                    case 1:
                        imageView.setImage(one);
                        break;
                    case 2:
                        imageView.setImage(two);
                        break;
                    case 3:
                        imageView.setImage(three);
                        break;
                    case 4:
                        imageView.setImage(four);
                        break;
                    case 5:
                        imageView.setImage(five);
                        break;
                    case 6:
                        imageView.setImage(six);
                        break;
                }
                currentPlayer=(Player)players.get(currentPlayerIndex);
                currentPlayer.move(num);
                currentPlayerIndex++;
                
                
                //repeat chance if die shows six
                if(num==6)
                    currentPlayerIndex--;
                
                
                if(currentPlayerIndex>=Player.NumberOfPlayers)
                    currentPlayerIndex-=Player.NumberOfPlayers;
                
                playerCircle.setFill(fills[currentPlayerIndex]);
                
                
                prompt.setText("Speler "+Integer.toString(currentPlayerIndex+1)+" is aan de beurt");
                
                if(Player.winnerNum>=0){
                    winnerLbl.setText("Speler "+Player.winnerNum+" heeft gewonnen!");
                    prompt.setText("Gefeliciteerd Speler "+Player.winnerNum+"!");
                    winRect.setFill(fills[Player.winnerNum-1]);
                    winRect2.setFill(fills[Player.winnerNum-1]);
                    winnerLbl.setBorder(Border.EMPTY);
                    boolean add = root.getChildren().add(winRect);
                    root.getChildren().add(winRect2);
                    root.getChildren().add(winnerLbl);
                }
            }
        });
        
        
        
        
        initBtn.setOnAction((ActionEvent event) -> {
            Board.snakes=(Integer)snakecounter.getValue();
            Board.ladders=(Integer)laddercounter.getValue();
            gc.clearRect(0, 0, 500, 650);
            Board.initialiseSnL();
            
            for(Integer i=1;i<101;i++){
                if(Board.squares[i].getDestSquare()!=null){
                    if(Board.squares[i].getDestSquare().getSqNumber()<Board.squares[i].getSqNumber())
                        gc.setStroke(Color.RED);
                    else if(Board.squares[i].getDestSquare().getSqNumber()>Board.squares[i].getSqNumber())
                        gc.setStroke(Color.GREEN);
                    gc.strokeLine((Board.squares[i].getGridX())*50+25, (Board.squares[i].getGridY())*50+25,
                            (Board.squares[i].getDestSquare().getGridX())*50+25,
                            (Board.squares[i].getDestSquare().getGridY())*50+25);
                    
                }
            }
            primaryStage.setScene(scene);
            primaryStage.centerOnScreen();
        });
        
        initScene.getStylesheets().add(SnakesAndLadders.class.getResource("SnakesAndLadders.css").toExternalForm());
        //winSc.getStylesheets().add(SnakesAndLadders.class.getResource("SnakesAndLadders.css").toExternalForm());
        
        scene.getStylesheets().add(SnakesAndLadders.class.getResource("SnakesAndLadders.css").toExternalForm());
        primaryStage.setTitle("Slangen en Ladders");
        primaryStage.setScene(initScene);
        primaryStage.show();
    }
    
    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        
        launch(args);
    }
    
    
}