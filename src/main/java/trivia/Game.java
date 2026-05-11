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

   public Game() {
      for (int i = 0; i < NUMBER_OF_QUESTIONS; i++) {
         popQuestions.addLast("Pop Question " + i);
         scienceQuestions.addLast(("Science Question " + i));
         sportsQuestions.addLast(("Sports Question " + i));
         rockQuestions.addLast(("Rock Question " + i));
      }
   }

   public boolean isPlayable() {
      return (howManyPlayers() >= 2);
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

   public int howManyPlayers() {
      return players.size();
   }

   public void roll(int roll) {
      System.out.println(players.get(currentPlayer) + " is the current player");
      System.out.println("They have rolled a " + roll);

      if (inPenaltyBox.get(currentPlayer)) {
         if (roll % 2 != 0) {
            isGettingOutOfPenaltyBox = true;

            System.out.println(players.get(currentPlayer) + " is getting out of the penalty box");
            places.set(currentPlayer, places.get(currentPlayer) + roll);
            if (places.get(currentPlayer) > 12) places.set(currentPlayer, places.get(currentPlayer) - 12);

            System.out.println(players.get(currentPlayer)
                               + "'s new location is "
                               + places.get(currentPlayer));
            System.out.println("The category is " + currentCategory());
            askQuestion();
         } else {
            System.out.println(players.get(currentPlayer) + " is not getting out of the penalty box");
            isGettingOutOfPenaltyBox = false;
         }

      } else {

         places.set(currentPlayer, places.get(currentPlayer) + roll);
         if (places.get(currentPlayer) > 12) places.set(currentPlayer, places.get(currentPlayer) - 12);

         System.out.println(players.get(currentPlayer)
                            + "'s new location is "
                            + places.get(currentPlayer));
         System.out.println("The category is " + currentCategory());
         askQuestion();
      }

   }

   private void askQuestion() {
      if (currentCategory().equals("Pop"))
         System.out.println(popQuestions.removeFirst());
      else if (currentCategory().equals("Science"))
         System.out.println(scienceQuestions.removeFirst());
      else if (currentCategory().equals("Sports"))
         System.out.println(sportsQuestions.removeFirst());
      else if (currentCategory().equals("Rock"))
         System.out.println(rockQuestions.removeFirst());
   }


   private String currentCategory() {
      if (places.get(currentPlayer) - 1 == 0) return "Pop";
      if (places.get(currentPlayer) - 1 == 4) return "Pop";
      if (places.get(currentPlayer) - 1 == 8) return "Pop";
      if (places.get(currentPlayer) - 1 == 1) return "Science";
      if (places.get(currentPlayer) - 1 == 5) return "Science";
      if (places.get(currentPlayer) - 1 == 9) return "Science";
      if (places.get(currentPlayer) - 1 == 2) return "Sports";
      if (places.get(currentPlayer) - 1 == 6) return "Sports";
      if (places.get(currentPlayer) - 1 == 10) return "Sports";
      return "Rock";
   }

   public boolean handleCorrectAnswer() {
      if (inPenaltyBox.get(currentPlayer)) {
         if (isGettingOutOfPenaltyBox) {
            System.out.println("Answer was correct!!!!");
            purses.set(currentPlayer, purses.get(currentPlayer) + 1);
            System.out.println(players.get(currentPlayer)
                               + " now has "
                               + purses.get(currentPlayer)
                               + " Gold Coins.");

            boolean winner = didPlayerWin();
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;

            return winner;
         } else {
            currentPlayer++;
            if (currentPlayer == players.size()) currentPlayer = 0;
            return true;
         }


      } else {

         System.out.println("Answer was corrent!!!!");
         purses.set(currentPlayer, purses.get(currentPlayer) + 1);
         System.out.println(players.get(currentPlayer)
                            + " now has "
                            + purses.get(currentPlayer)
                            + " Gold Coins.");

         boolean winner = didPlayerWin();
         currentPlayer++;
         if (currentPlayer == players.size()) currentPlayer = 0;

         return winner;
      }
   }

   public boolean wrongAnswer() {
      System.out.println("Question was incorrectly answered");
      System.out.println(players.get(currentPlayer) + " was sent to the penalty box");
      inPenaltyBox.set(currentPlayer, true);

      currentPlayer++;
      if (currentPlayer == players.size()) currentPlayer = 0;
      return true;
   }


   private boolean didPlayerWin() {
      return !(purses.get(currentPlayer) == 6);
   }
}
