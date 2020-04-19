package flashcards;

import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import java.util.stream.Collectors;

public class Main {
    private Logger reader;
    private Cards cards;

    public Main() {
        this.reader =new Logger(new Scanner(System.in));
        this.cards=new Cards();
    }

    public void menu() throws IOException {
        String action="", term="",definition="",filename="";
        while(!action.equals("exit"))
        {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card," +
                    " reset stats):\n");
            switch ((action=reader.nextLine())){
                case "add":

                    System.out.println("The card: ");
                    do {
                        term = reader.nextLine();
                    }while(term.isEmpty());
                    if (cards.termExist(term)){
                        System.out.printf("The card \"%s\" already exists.\n",term);
                    }
                    else {
                        System.out.println("The definition of the card: ");
                        do {
                            definition = reader.nextLine();
                        }while(definition.isEmpty());
                        if (cards.definitionExist(definition)){
                            System.out.printf("The definition \"%s\" already exists.\n",definition);
                        }
                    }
                    if(cards.add(term,definition)){
                        System.out.printf("The pair (\"%s\":\"%s\") has been added.\n",term,definition);
                    }
                    break;
                case "remove":
                    System.out.println("The card: ");
                    term = reader.nextLine();
                    if (cards.remove(term)){
                        System.out.println("The card has been removed.");
                    }
                    else
                    {
                        System.out.printf("Can't remove \"%s\": there is no such card.\n",term);
                    }
                    break;
                case "import":
                    System.out.println("File name");
                    filename = reader.nextLine();
                    try {
                        System.out.printf("%d cards have been loaded.\n", cards.load(filename));
                    }
                    catch(Exception e)
                    {
                        System.out.println("File not found");
                    }
                    break;
                case "export":
                    System.out.println("File name: ");
                    filename = reader.nextLine();
                    System.out.printf("%d cards have been saved.\n", cards.export(filename));
                    break;
                case "log":
                    System.out.println("File name:");
                    filename = reader.nextLine();
                    if (reader.saveToFile(filename))
                    {
                        System.out.println("The log has been saved.");
                    }
                    else
                    {
                        System.out.println("error file");
                    }
                    break;
                case "hardest card":

                    if (cards.maxError() == 0)
                    {
                        System.out.println("There are no cards with errors.");
                    }
                    else
                    {
                        List<Card> hardest = cards.findHardest();
                        String answer = "The hardest card" + (hardest.size() > 1 ? "s are " : " is ");
                      answer += hardest.stream()
                                .map(card -> "\""+card.getTerm()+"\"")
                                .collect(Collectors.joining(","));
                        System.out.printf("%s. You have %d errors answering them.\n",answer,cards.maxError());
                    }

                    break;
                case "reset stats":
                    cards.resetStats();
                    System.out.println("Card statistics has been reset.");
                    break;
                case "ask":
                    System.out.println("How many times to ask?");

                    int countCards = reader.nextInt();
                    for (int i = 0;i <countCards;i++){
                        Card card = cards.getRandomCard();
                        System.out.printf("Print the definition of \"%s\"\n",card.getTerm());

                        String answer;
                        do {
                            answer=reader.nextLine();
                        } while(answer.isEmpty());

                        if (card.isValidAnswer(answer)){
                            System.out.println("Correct answer");
                        }
                        else{
                            System.out.printf("Wrong answer. The correct one is \"%s\"",card.getDefinition());
                            String  validTerm=cards.findTerm(answer);
                            if (!validTerm.isEmpty()){
                                System.out.printf(", you've just written the definition of \"%s\"",validTerm);
                            }
                            System.out.println(".");
                        }

                    }
                    break;


            }
            System.out.println();

        }
        System.out.println("Bye bye!");
    }


    public static void main(String[] args) throws IOException{
     Main app =new Main();
     app.menu();

    }

}
