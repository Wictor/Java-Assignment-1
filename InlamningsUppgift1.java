import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class InlamningsUppgift1 {
    private static Scanner scan = new Scanner(System.in);

    public static void main(String[] args)
    {
        int[] highScore = {0, 0, 0, 0, 0};
        int playerAmount, diceAmount, playAgain;
        ArrayList<String> playerName = new ArrayList<>();
        String answerToQuestion;

        //Loop för att tvinga spelaren välja minst 1 spelare.
        do {
            playerAmount = decidePlayers();
        } while (playerAmount < 1);

        //Loop för att tvinga spelaren att välja Ja eller Nej
        do {
            System.out.print("Vill du namnge spelarna?: ");
            answerToQuestion = scan.next();

            playerName = choiceToNamePlayer(playerName, playerAmount, answerToQuestion);
        } while (!answerToQuestion.equals("Ja") && !answerToQuestion.equals("Nej"));

        //Här så uppdaterar jag antalet spelare och ArrayListen för att få med datorn.
        do {
            System.out.print("Vill du ha med Datorn?: ");
            answerToQuestion = scan.next();

            if(answerToQuestion.equals("Ja"))
            {
                playerAmount = playerAmount+1;
                playerName = computerPlayer(playerName, playerAmount);
            }

        } while (!answerToQuestion.equals("Ja") && !answerToQuestion.equals("Nej"));


        //Loop för att tvinga spelaren välja minst 1 tärning(kast).
        do {
            diceAmount = decideDices();
        } while (diceAmount < 1);

        do {

            //rullar jag tärningarna och hämtar ut informationen
            int [][] sumDiceRoller = diceRoller(playerAmount, diceAmount);
            //Den informationen skickar jag sedan vidare för att jämföra med arrayList index och skriva ut vad varje spelare rullade samtidigt som jag hämtar ut summan för varje spelare.
            int [] sumPlayer = sumEachPlayers(diceAmount, playerName, sumDiceRoller);

            //Den summan skickar jag vidare och kollar vem som vann och skriver ut det
            whoWon(sumPlayer, playerName);
            //Hämtar data från highscore och uppdaterar den med ny data
            highScoreList(sumPlayer, highScore, playerName);

            //Loop för att tvinga spelaren att välja spela igen eller avsluta
            do{
                System.out.println("1. Spela igen | 2. Avsluta");
                playAgain = scan.nextInt();

                if (playAgain == 1) {}
                else if (playAgain == 2) {
                    System.exit(0);
                }
                else {
                    System.out.println("Felaktig inmattning");
                }
            }while(playAgain != 1 && playAgain != 2);

        } while (playAgain == 1);
    }

    private static int decidePlayers()
    {
        System.out.print("Hur många spelare?: ");
        int players = scan.nextInt();

        if(players < 1)
        {
            System.out.println("Du måste välja minst 1 spelare!");
        }
        return players;
    }

    private static ArrayList<String> choiceToNamePlayer(ArrayList players, int playerAmount, String answer)
    {
        if (answer.equals("Ja")) {
            players = playerInputsNames(playerAmount);
        }
        else if (answer.equals("Nej")) {
            players = generatedPlayerNames(playerAmount);
        }
        else {
            System.out.println("Felaktig inmattning");
        }
        return players;
    }

    private static ArrayList<String> playerInputsNames(int playerAmount)
    {
        ArrayList<String> players = new ArrayList<>(playerAmount);
        for(int i = 0; i < playerAmount; i++)
        {
            System.out.print("Namnge spelare " + (i+1) + " : ");
            players.add(scan.next());
        }
        return players;
    }

    private static ArrayList<String> generatedPlayerNames(int playerAmount)
    {
        ArrayList<String> players = new ArrayList<>(playerAmount);
        for(int i = 0; i < playerAmount; i++)
        {
            players.add("Spelare " + (i+1));
        }
        return players;
    }

    private static ArrayList<String> computerPlayer(ArrayList players, int playerAmount)
    {
        players.ensureCapacity(playerAmount);
        players.add("Datorn");

        return players;
    }

    private static int decideDices()
    {
        System.out.print("Hur många tärningar vill du kasta?: ");
        int dices = scan.nextInt();

        if(dices < 1)
        {
            System.out.println("Du måste välja minst 1 tärning!");
        }

        return dices;
    }

    private static int[][] diceRoller ( int playerAmount, int diceAmount)
    {
        int[][] resultPerPlayer = new int[playerAmount][diceAmount];
        int result[][] = new int[playerAmount][diceAmount];

        for (int i = 0; i < playerAmount; i++)
        {
            for (int k = 0; k < diceAmount; k++)
            {
                resultPerPlayer[i][k] = (int) (1 + Math.random() * 6);
                result[i][k] += resultPerPlayer[i][k];
            }
        }
        return result;
    }

    private static int[] sumEachPlayers(int diceAmount, ArrayList playerName, int[][] sumDiceRoller)
    {
        int playerAmount = playerName.size();
        int[] sumEachPlayer = new int[playerAmount];

        for (int i = 0; i < playerAmount; i++)
        {
            for (int k = 0; k < diceAmount; k++)
            {
                System.out.println(playerName.get(i) + " rullade: " + sumDiceRoller[i][k]);
                sumEachPlayer[i] += sumDiceRoller[i][k];
            }
            System.out.println(playerName.get(i) + "s summa: " + sumEachPlayer[i] + "\n");
        }
        return sumEachPlayer;
    }

    private static void highScoreList (int[] sumEachPlayer, int[] highScore, ArrayList playerName)
    {
        String text = "";
        //Jämför vad alla rullade med vad som är highscore just nu och ersätter.
        for (int i = 0; i < sumEachPlayer.length; i++)
        {
            for(int k = 0; k < 5;k++)
            {
                if (sumEachPlayer[i] > highScore[k])
                {
                    text += playerName.get(i) + " slog ett tidigare rekord\n";
                    highScore[k] = sumEachPlayer[i];
                    break;
                }
            }
        //Array sort använder jag för att den ska göra så att den högsta alltid hamnar sist. Detta för att jag vill att den ska ersätta den lägsta först.
        Arrays.sort(highScore);
        }

        //if-sats för att göra att om det är text att skriva ut så ska den göra ett mellanrum, om det inte ligger i en if-sats så blir det dubbla mellanrum.
        if(!text.equals("")) {
            System.out.print(text+"\n");
        }
        System.out.println("Highscore:");

        //Här "vänder" jag på highscoren så att den skriver det högsta värdet överst även fast den har den sista index platsen.
        int k = 4;
        for(int i = 0; i < highScore.length ; i++)
        {
            System.out.println(i+1 + ". " + highScore[k]);
            k--;
        }
        System.out.print("\n");
    }

    private static void whoWon(int[] sumEachPlayer, ArrayList playerName)
    {
        ArrayList<Integer> compareWinner = new ArrayList<>(0);
        int winner = 0;

        //jämför det de olika tärningskasten mot varandra och sparar index av vinnaren.
        for (int i = 0; i < sumEachPlayer.length; i++)
        {
            if (sumEachPlayer[i] > sumEachPlayer[winner])
            {
                compareWinner.clear();
                compareWinner.add(i);
                winner = i;
            }
            else if (sumEachPlayer[i] == sumEachPlayer[winner])
            {
                compareWinner.add(i);
            }
        }
        //Om det är mer än 1 i compareWinner Arraylist så ska den loopa och skriva ut alla som finns i den.
        if(compareWinner.size() > 1)
        {
            System.out.println("Det blev lika mellan: ");
            for (int i = 0; i < compareWinner.size(); i++)
            {
                System.out.println(playerName.get(compareWinner.get(i)));
            }
            System.out.print("\n");
        }
        else
        {
            System.out.println(playerName.get(winner) + " är vinnaren\n");
        }
    }
}

