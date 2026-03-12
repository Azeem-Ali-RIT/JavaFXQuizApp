package MCQGame;

public class TFQQuestion implements Question {

    private String question;
    private String[] answers;
    private String correctAnswer;
    
    //Constructors, initializes value of answer, question, correctAnswer
    TFQQuestion(String question, String[] answers, String correctAnswer){
        
        this.answers = answers;
        this.question = question;
        this.correctAnswer = correctAnswer;
    }

    //Getters that returns the following attributes
    @Override
    public String getQuestion(){return this.question;}
    @Override
    public String[] getAllAnswers(){return this.answers;}
    @Override
    public String getCorrectAnswer(){return this.correctAnswer;}

    //Setters to re-initialize the question, set of answers and correct answer
    void setQuestion(String question){this.question = question;}
    void setAnswers(String[] answer){this.answers = answer;}
    void setCorrectAnswer(String correctAnswer){this.correctAnswer = correctAnswer;}

    @Override
    /*Can check if two of the answers are equal/same */
    public boolean equals(Object obj) {
        if (obj instanceof MCQQuestion)
        {
            MCQQuestion other = (MCQQuestion)obj;
            return this.answers.equals(other.getAllAnswers());
        }
        else
        return false;
    }

    /*
     * The overriden toString follows the format:
     * Question:
     * 1: Answer 1
     * 2: Answer 2
     * ...
     * Index of answer array + 1: Answer Index+1
     * 
     * The reason we start of with 1 and end with Index+1 is because we dont want to start with 0 and end with a number less than number of questions
     */
    @Override
    public String toString() {
        String str = this.question + ":\n"; 
        for(int i = 0; i < this.answers.length; i++){
            str += " " + (i + 1) + ": " + this.answers[i] + '\n'; 
        }
        return str;
    }

    /*
     * at least one of the answer is equal to the correct answer, and if it is, the user is correct
     * if the answer is not equal to the correct answer, he is wrong.
     * the checkAnswer function comes after the person has inputed the answer.
     */

    @Override
    public Boolean checkAnswer(int option){
        /*Checks 2 answers if their equal or not */

        if(this.answers[option].equals(correctAnswer)){
            return true;
        }
        else{
            return false;
        }
    }
    
}
