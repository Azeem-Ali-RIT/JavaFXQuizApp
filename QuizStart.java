package MCQGame;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

import com.google.gson.Gson;

import javafx.application.Platform;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

public class QuizStart extends Application{
    private int score;
    private int index;
    private int numberofQuestions;
    private Button[] ButtonArray;
    private Label Quest;
    private int timetook;
    private int timeSeconds;
    private Label timerLabel;
    private Stage newstage;
    private Timeline timeline;

    private ArrayList<Question> SelQuestions;
    private Alert alert;
    VBox ButtonBox = new VBox();
    private String user;
    private final int START_TIME;

    //private List<QuizQuestions> x;

    public interface QuizzSelector {
        /* Interface for lambda functions*/
    
        ArrayList<Question> returnSelected();
    }
    


    public QuizStart(String user, Difficulty difficulty) throws IOException{
        /* Constructor - checks how many questions the user gets right and stores the question and positions the buttons*/
        this.score = 0;
        this.index = -1;
        this.numberofQuestions = difficulty.getNumOfQuestions();
        this.Quest = new Label();

        Quest.setTextFill(Color.web("#fd0076"));
        Quest.setFont(new Font(30));
        Quest.setStyle("-fx-font-weight: bold");
        Quest.setTextAlignment(TextAlignment.CENTER);
        //Quest.setMaxWidth(1300);
        timeSeconds = difficulty.getTime();
        timerLabel = new Label();
        START_TIME = difficulty.getTime();

        this.ButtonArray = new Button[4];

        for(int i = 0; i < 4; i++){
            int index0 = i;
            ButtonArray[i] = new Button();
            ButtonArray[i].setAlignment(Pos.CENTER);
            ButtonArray[i].setScaleX(1.3);
            ButtonArray[i].setScaleY(1.3);
            //ButtonArray[i].setStyle("-fx-background-color:#b71bdf; -fx-background-radius: 5em;");
            ButtonArray[i].setOnAction(event -> {try {
                checkSystem(index0, this.ButtonBox);
            } catch (IOException e) {
                e.printStackTrace();
            }});

            ButtonBox.setAlignment(Pos.CENTER);
        }

        QuizzSelector selQuestions = () -> {
            QuizQuestions q = new QuizQuestions();
            try {
                q.load("D:\\Java\\MCQGame\\questionsBase.txt");
            } catch (IOException e) {
                e.printStackTrace();
            }
            q.select(numberofQuestions); // Replace with actual number of questions
            return q.getSelectedArray(); // Ensure this method returns an array
        };

        SelQuestions = selQuestions.returnSelected();
        alert = null;
        this.user = user;
    }

    @Override
    public void start(Stage stage) throws Exception{
        /* Alignment of options and countdown of timer*/
        String text = "LOL";
        VBox Options = new VBox();

        int MinuteInt;
        //GridPane ButtonPane = new GridPane();

        //HBox ButtonBox = new HBox();
        /* 
        for (int i = 0; i < ButtonArray.length; i++) {
            ButtonBox.getChildren().add(ButtonArray[i]);
        }
        */

        //ButtonBox.setSpacing(20);

        Image BI = new Image("file:\\D:\\Java\\MCQGame\\bgpic.jpg"); // Correct file path usage
        BackgroundImage BV = new BackgroundImage(
        BI,
        BackgroundRepeat.NO_REPEAT,
        BackgroundRepeat.NO_REPEAT,
        BackgroundPosition.DEFAULT,
        new BackgroundSize(100, 100, true, true, true, true));

        timerLabel.setTextFill(Color.web("#fd0076"));
        timerLabel.setFont(new Font(25));
        timerLabel.setText("Time left: " + String.valueOf(Math.floorDiv(timeSeconds, 60)) + " minutes and " +  String.valueOf(timeSeconds - Math.floorDiv(timeSeconds, 60) * 60 ) + " seconds");
        timeline = new Timeline();
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.getKeyFrames().add(
            new KeyFrame(Duration.seconds(1), event -> {
                timeSeconds--;
                timerLabel.setText("Time left: " + String.valueOf(Math.floorDiv(timeSeconds, 60)) + " minutes and " +  String.valueOf(timeSeconds - Math.floorDiv(timeSeconds, 60) * 60 ) + " seconds");
                if (timeSeconds <= 0) {
                    timeline.stop();
                    try {
                        EndScreen();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            })
        );
        timeline.playFromStart();
        
        Label spacing = new Label("\n ");
        spacing.setFont(new Font("Times New Roman",15));

        VBox LabelBox = new VBox(spacing,Quest, this.ButtonBox);
        LabelBox.setSpacing(20);
        LabelBox.getChildren().add(timerLabel);
        LabelBox.setAlignment(Pos.CENTER);
        LabelBox.setBackground(new Background(BV));

        //VBox vr = new VBox(LabelBox);
        //vr.setBackground(new Background(BV));
        //vr.setAlignment(Pos.CENTER);

        
        updateQuestion(this.ButtonBox);
        //VBox Questions = new VBox(Quest,Options);
        newstage = new Stage();
        newstage.setOnCloseRequest(event -> {
            try {
                EndScreen();
            } catch (IOException e) {
                e.printStackTrace();
            }
        });
        Scene scene = new Scene(LabelBox,1400,400);
        scene.getStylesheets().add(getClass().getResource("Button-style.css").toExternalForm());
        newstage.setScene(scene);
        newstage.setTitle("Quiz");
        newstage.show();
    }

    public void updateQuestion(VBox ButtonBox) throws IOException{
        
        //ButtonBox[1].getChildren().clear();
        ButtonBox.setSpacing(40);
        /* It changes and updates the question to the next one when the option is clicked*/
        this.index = this.index + 1;

        if(index >= numberofQuestions){
            EndScreen();
        }
        else{
            ButtonBox.getChildren().clear();
            Quest.setText(SelQuestions.get(index).getQuestion());
            
            String[] Answers = SelQuestions.get(index).getAllAnswers();
            for (int i = 0; i < 4; i++) {
                if(i <= Answers.length - 1){
                    ButtonArray[i].setText(Answers[i]);
                    ButtonBox.getChildren().add(ButtonArray[i]);
                    //ButtonBox[Math.floorDiv(i, 2)].setSpacing(60);
                }
                else{
                    ButtonArray[i].setText("  ");
                }
            }
        }
        
    }

    public int checkSystem(int index0, VBox ButtonBox) throws IOException{
        /* Checks if the user is right or not - for T/F*/

        if(ButtonArray[index0].getText() == "  "){
            return -1;
        }

        if(SelQuestions.get(this.index).checkAnswer(index0)){
            this.score = this.score + 1;
        }

        updateQuestion(ButtonBox);

        return score;
    }

    public Button getButton(int index){
        /* Gives user the function*/
        return this.ButtonArray[index];
    }

    public void sendData(String addInfo) throws UnknownHostException, IOException{

        try{
            //int numPos = 1;
            
            Socket serverSocket = new Socket("localhost", 12345);
            System.out.println("Server started. Waiting for client to connect...");

                //Socket ServerSocket = serverSocket.accept();
            System.out.println("Client connected.");

            PrintWriter out = new PrintWriter(serverSocket.getOutputStream());
            out.println(1);
            out.flush();

            //String strArr = new Gson().toJson(addInfo);

            out.println(addInfo);
            System.out.println(addInfo);
            out.flush();

            out.close();
            serverSocket.close();
        }
        catch(IOException e){
            System.out.println("LOL");
        }

    }

    public void EndScreen() throws IOException{
        /* Creates a popup with the score and then closes the prior screen - alert makes the function only executes once */

        String filename = "D:\\Java\\MCQGame\\leaderBoard.txt";
        int percentScore = (this.score * 100)/this.numberofQuestions;
        if(alert != null && alert.isShowing()){return;}

        //FileReader fileW = new FileReader(filename);
        //Scanner readEr = new Scanner(fileW);
        //Boolean elinecheck = readEr.hasNextLine();
        //fileW.close(); readEr.close();
        timeline.stop();


        //FileWriter file = new FileWriter("/home/azeemjiwa/Documents/Second-Semester/GCIS-124/gcisstuff/src/MCQGame/leaderBoard.txt", true);
        //BufferedWriter bUff = new BufferedWriter(file);
        this.timetook = this.START_TIME - timeSeconds;
        //if(elinecheck == true){bUff.newLine();}
        //bUff.write(percentScore + ", " + user + ", " + this.timetook);  
        //bUff.close();

        String toAdd = percentScore + ", " + user + ", " + this.timetook;
        sendData(toAdd);
        
        ///Platform.exit();
        
        Platform.runLater(() -> {;
            alert = new Alert(AlertType.INFORMATION);
            alert.setTitle("Information");
            alert.setHeaderText("Your score");
            alert.setContentText("Your score is: " + percentScore + "%\nTime it took is: " + this.timetook + " seconds");

            alert.showAndWait().ifPresent(response -> {this.newstage.close();});
        });

    }

    public static void main(String[] args) {
        launch(args);
    }
}

