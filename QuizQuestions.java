package MCQGame;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class QuizQuestions {
    private ArrayList<Question> allQuestions;
    private ArrayList<Question> selectedQuestions;

    public QuizQuestions(){
        /* Creates a new array list of all questions and selected questions*/
        allQuestions = new ArrayList<Question>();
        selectedQuestions = new ArrayList<Question>();
    }

    void load(String filename) throws IOException{
        /* Loads the question file and creates classes of chosen questions - MCQ and T/F
         * Checks if the question is T/F or MCQ by checking correct answer
        */
        
        try {
            FileReader file = new FileReader(filename);
            //BufferedReader readER = new BufferedReader(file);

            Scanner readER = new Scanner(file);

            while(readER.hasNextLine()){

                String Questions = readER.nextLine();
                String[] Answers = unpack(readER.nextLine());
                String CorrectAnswer = readER.nextLine();

                //System.out.println(Arrays.toString(Answers));

                if(CorrectAnswer == "True" || CorrectAnswer == "False"){
                    allQuestions.add(new TFQQuestion(Questions, Answers, CorrectAnswer));
                }
                else{
                    allQuestions.add(new MCQQuestion(Questions, Answers, CorrectAnswer));;
                }

                readER.nextLine();
            }

            readER.close();
            file.close();

            

            //System.out.println(allQuestions.get(0).getQuestion());


        } catch (FileNotFoundException e) {
            System.out.println("Error");
            e.printStackTrace();
        }
    }
    String[] unpack(String answer){
        /* Splits the answer into string*/

        return answer.split(", ");

    }

    void select(int numberofQuestions){
        /* Selects the questions to remove so that the questions are never duplicated*/

        int numIndexToRemove;
        selectedQuestions = new ArrayList<Question>(allQuestions);
        int numToRemove = selectedQuestions.size() - numberofQuestions;

        for(int i = 0; i < numToRemove; i++){
            numIndexToRemove = (int)(Math.random() * selectedQuestions.size());
            selectedQuestions.remove(numIndexToRemove);
        }

    }

    public ArrayList<Question> getQuestions(){return allQuestions;}
    public ArrayList<Question> getSelectedArray(){return selectedQuestions;}

    public static void main(String[] args) throws IOException {
        /* for debuging*/
        QuizQuestions q = new QuizQuestions();
        q.load("D:\\Java\\MCQGame\\questionsBase.txt");
        q.select(10);
        
        for(int i = 0; i < 10; i++){
            System.out.println(q.getSelectedArray().get(i));
        }
    }
    
}
