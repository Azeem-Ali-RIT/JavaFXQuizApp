package MCQGame;

public interface Question {
    
    abstract String getQuestion();
    abstract String[] getAllAnswers();
    abstract String getCorrectAnswer();
    abstract Boolean checkAnswer(int option);

}
