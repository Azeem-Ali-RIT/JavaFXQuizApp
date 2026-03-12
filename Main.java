//import java.applet.Applet;
package MCQGame;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.Scanner;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBase;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;

public class Main extends Application{
    /*Starts the GUI */


    private TextField textEdit;
    @Override
    public void start(Stage stage) throws Exception{

        Label MainLabel = new Label("\t                 Rules:-\n -No using outside sources to answer \n -Make sure you read every answer before choosing\n -Manage Your Time Wisely - You have 4 mins\n ");
        //MainLabel.setBackground(new Background(new BackgroundFill(Color.WHITE, CornerRadii.EMPTY, Insets.EMPTY)));
        MainLabel.setFont(new Font("Times New Roman",15));
        MainLabel.setTextFill(Color.WHITE);
        MainLabel.setAlignment(Pos.CENTER);

        Label username = new Label("Enter your username: ");
        username.setFont(new Font("Times New Roman",20));
        username.setTextFill(Color.WHITE);
        

        Label title = new Label("QUIZ");
        title.setFont(new Font("Times New Roman",30));
        title.setTextFill(Color.WHITE);

        Label spacer = new Label("\n ");
        spacer.setFont(new Font("Times New Roman",15));

        Label space = new Label("\n ");
        space.setFont(new Font("Times New Roman",15));

        Label spacey = new Label("\n ");
        spacey.setFont(new Font("Times New Roman",15));

        textEdit = new TextField();
        textEdit.setFont(new Font("Times New Roman",15));
        textEdit.setMaxWidth(300);
        textEdit.setText("");

        Button Start = new Button("Start");
        Start.setFont(new Font("Times New Roman",15));
        Start.setTextFill(Color.BLACK);

        Button ShowLeaderBoard = new Button("LeaderBoard");
        ShowLeaderBoard.setFont(new Font("Times New Roman", 15));
        ShowLeaderBoard.setTextFill(Color.BLACK);
        //ShowLeaderBoard.setAlignment(Pos.CENTER);

        Button[] DiffButton = {new Button("Easy"), new Button("Medium"), new Button("Hard")};

        HBox horiz = new HBox();
        horiz.setSpacing(20);
        horiz.setAlignment(Pos.CENTER);

        loadButtons(DiffButton, horiz, textEdit);

        VBox ButtonBox = new VBox(horiz, ShowLeaderBoard);
        ButtonBox.setSpacing(20);
        ButtonBox.setAlignment(Pos.CENTER);

        Image backgroundImage = new Image("file:\\D:\\Java\\MCQGame\\bgpic.jpg"); // Correct file path usage
        ImageView backgroundView = new ImageView(backgroundImage);
        backgroundView.setPreserveRatio(false); // Allow full stretch
        backgroundView.setFitWidth(600); // Set width to match scene width
        backgroundView.setFitHeight(400); // Set height to match scene height

        backgroundView.fitWidthProperty().bind(stage.widthProperty());
        backgroundView.fitHeightProperty().bind(stage.heightProperty());
        

        /*
        Start.setOnAction(event -> {try {
            StartNewWindow(textEdit.getText());
        } catch (Exception e) {
            System.out.println("Error class not found");
            e.printStackTrace();
        }});
        */


        ShowLeaderBoard.setOnAction(event -> {try {
            ShowLeader();
        } catch (Exception e) {
            System.out.println("Error class not found");
            e.printStackTrace();
        }});

        System.out.println("check001");
        Label leaderBaord = new Label("");
        leaderBaord.setFont(new Font("Times New Roman",20));
        leaderBaord.setTextFill(Color.WHITE);
        leaderBaord.setAlignment(Pos.CENTER);
        VBox V1 = new VBox(title,spacer,username,textEdit,space,ButtonBox,spacey,MainLabel); //leaderBaord not included shown at end
        V1.setAlignment(Pos.TOP_CENTER);
        //V1.setSpacing(0);

        StackPane bg = new StackPane(backgroundView, V1);

        Scene scene = new Scene(bg, 600, 400);
        stage.setScene(scene);
        stage.setTitle("Quiz");
        stage.setHeight(370); stage.setWidth(750);
        stage.show();

        scene.getStylesheets().add(getClass().getResource("Button-style.css").toExternalForm());

    }


    private void loadButtons(Button[] diffButton, HBox horiz, TextField textUser) {
        
        for(Button button : diffButton){
            button.setOnAction(event -> {try {
                StartNewWindow(button.getText());
            } catch (Exception e) {
                System.out.println("Error class not found");
                e.printStackTrace();
            }});

            //button.setFont(new Font("Times New Roman",20));
            button.setTextFill(Color.BLACK);
            horiz.getChildren().add(button);
        }
    }


    void StartNewWindow(String BuDifficulty) throws Exception{
        /* Opens another window for all the quiz questions*/

        String UserName = this.textEdit.getText();
        if(UserName.equals("")){
            System.out.println("error test");
            Alert ErrorA = new Alert(AlertType.ERROR);

            ErrorA.setTitle("Error");
            ErrorA.setHeaderText("Error");
            ErrorA.setContentText("Please enter your name");

            ErrorA.show();
        }
        else{
            Difficulty difficulty = null;

            switch(BuDifficulty){

                case "Easy" : difficulty = Difficulty.EASY; break;
                case "Medium" : difficulty = Difficulty.MEDIUM; break;
                case "Hard" : difficulty = Difficulty.HARD; break;
            }

            QuizStart startIns = new QuizStart(UserName, difficulty);
            startIns.start(new Stage());
        }
    }

    void ShowLeader() throws Exception{
        /* Opens another window for all the quiz questions*/
        LeaderBoard startIns = new LeaderBoard();
        startIns.start(new Stage());
    }


    public static void main(String[] args){
        launch(args);
    }
}




