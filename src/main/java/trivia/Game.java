package trivia;

import java.util.ArrayList;
import java.util.LinkedList;

// REFACTOR ME
public class Game implements IGame {
    ArrayList<String> players = new ArrayList<>();
    ArrayList<Integer> places = new ArrayList<>();
    ArrayList<Integer> purses = new ArrayList<>();
    ArrayList<Boolean> inPenaltyBox = new ArrayList<>();

    LinkedList<String> popQuestions = new LinkedList<>();
    LinkedList<String> scienceQuestions = new LinkedList<>();
    LinkedList<String> sportsQuestions = new LinkedList<>();
    LinkedList<String> rockQuestions = new LinkedList<>();

    int currentPlayer = 0;
    boolean isGettingOutOfPenaltyBox;

    public static final int NUMBER_OF_QUESTIONS = 50;
    public static final int NUMBER_OF_SQUARES = 12;

    public Game() {
        for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast(("Rock Question " + i));
        }
    }

    public boolean add(String playerName) {
        places.add(1);
        purses.add(0);
        inPenaltyBox.add(false);
        players.add(playerName);

        System.out.println(playerName + " was added");
        System.out.println("They are player number " + players.size());
        return true;
    }

    public void roll(int roll) {
        final boolean rollIsOdd = roll % 2 != 0;

        System.out.println(players.get(currentPlayer) + " is the current player");
        System.out.println("They have rolled a " + roll);

        if (!inPenaltyBox.get(currentPlayer)) {
            movePlayer(places, currentPlayer, roll);

            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + places.get(currentPlayer));
            System.out.println("The category is " + currentCategory());
            askQuestion();
            return;
        }

        if (rollIsOdd) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            movePlayer(places, currentPlayer, roll);

            System.out.println(players.get(currentPlayer)
                    + "'s new location is "
                    + places.get(currentPlayer));
            System.out.println("The category is " + currentCategory());
            askQuestion();
            return;
        }

        System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
        isGettingOutOfPenaltyBox = false;
    }

    private void askQuestion() {
        switch (currentCategory()) {
            case "Pop"     -> System.out.println(popQuestions.removeFirst());
            case "Science" -> System.out.println(scienceQuestions.removeFirst());
            case "Sports"  -> System.out.println(sportsQuestions.removeFirst());
            case "Rock"    -> System.out.println(rockQuestions.removeFirst());
        }
    }

    private String currentCategory() {
        return switch (places.get(currentPlayer)) {
            case 1, 5, 9  -> "Pop";
            case 2, 6, 10 -> "Science";
            case 3, 7, 11 -> "Sports";
            default       -> "Rock";
        };
    }

    public boolean handleCorrectAnswer() {
        if (inPenaltyBox.get(currentPlayer) && !isGettingOutOfPenaltyBox) {
            setNextPlayer();
            return true;
        }

        // Print differs depending on whether they're escaping the penalty box
        if (inPenaltyBox.get(currentPlayer)) {
            System.out.println("Answer was correct!!!!");
        } else {
            System.out.println("Answer was corrent!!!!"); // TYPO: "corrent" should be -> "correct"
        }
        purses.set(currentPlayer, purses.get(currentPlayer) + 1);
        System.out.println(players.get(currentPlayer)
                + " now has "
                + purses.get(currentPlayer)
                + " Gold Coins.");

        boolean winner = didPlayerWin();
        setNextPlayer();

        return winner;
    }

    public boolean wrongAnswer() {
        System.out.println("Question was incorrectly answered");
        System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
        inPenaltyBox.set(currentPlayer, true);

        setNextPlayer();
        return true;
    }

    public void setNextPlayer() {
        currentPlayer++;
        if (currentPlayer == players.size()) currentPlayer = 0;
    }

    public void movePlayer(ArrayList<Integer> places, int currentPlayer, int amount) {
        int position = places.get(currentPlayer);
        position += amount;
        if (position > NUMBER_OF_SQUARES) {
            position -= NUMBER_OF_SQUARES;
        }
        places.set(currentPlayer, position);
    }

    private boolean didPlayerWin() {
        return !(purses.get(currentPlayer) == 6);
    }
}
