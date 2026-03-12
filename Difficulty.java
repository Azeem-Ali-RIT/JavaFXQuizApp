package MCQGame;

public enum Difficulty {
    
    EASY(5, 240),
    MEDIUM(10, 240),
    HARD(15, 120);

    private final int numofQuestions;
    private final int timeChose;

    Difficulty(int numOfQuestions, int timeChose){
        this.numofQuestions = numOfQuestions;
        this.timeChose = timeChose;
    }

    int getTime(){return this.timeChose;}
    int getNumOfQuestions(){return this.numofQuestions;}
}
