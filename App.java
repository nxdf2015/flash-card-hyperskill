package flashcards;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;

/**
 * use java Main [-import nameFile][-export nameFile]
 * -import load cards from file
 * -export save cards to file when exit
 */
public class App {
    private final Cards cards;
    private final Logger reader;
    private final Optional<String> exportName;

    public App(String[] args) {
        Optional<String> importName = Parser.getImport(args);
         exportName = Parser.getExport(args);

        this.cards=new Cards();

        this.reader =new Logger(new Scanner(System.in));

        if (importName.isPresent()){
            importFile(importName.get());
        }
    }

    public void start() throws IOException {
        String action="", term="",definition="",filename="";
        while(!action.equals("exit"))
        {
            System.out.println("Input the action (add, remove, import, export, ask, exit, log, hardest card," +
                    " reset stats):\n");
            switch ((action=reader.nextLine())){

                case "add":
                    add();
                    break;
                case "remove":
                    remove();
                    break;
                case "import":
                    importFile();
                    break;
                case "export":
                    exportFile();
                     break;
                case "log":
                    savelog();
                    break;
                case "hardest card":
                    hardestCard();
                    break;
                case "reset stats":
                    cards.resetStats();
                    System.out.println("Card statistics has been reset.");
                    break;
                case "ask":
                     ask();
                    break;

            }
        }


        System.out.println("Bye bye!");
        if (exportName.isPresent()){
            exportFile(exportName.get());
        }
    }

    private void exportFile(String filename)  throws IOException{
        System.out.printf("%d cards have been saved.\n", cards.export(filename));
    }

    private void ask() {
        System.out.println("How many times to ask?");

        int countCards = reader.nextInt();
        reader.nextLine();
        for (int i = 0;i <countCards;i++){
            Card card = cards.getRandomCard();
            System.out.printf("Print the definition of \"%s\"\n",card.getTerm());

            String answer=reader.nextLine();

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

    }

    private void exportFile() throws IOException  {
        System.out.println("File name: ");
       String  filename = reader.nextLine();
        System.out.printf("%d cards have been saved.\n", cards.export(filename));

    }

    private void savelog() {
        System.out.println("File name:");
        String filename = reader.nextLine();
        if (reader.saveToFile(filename))
        {
            System.out.println("The log has been saved.");
        }
        else
        {
            System.out.println("error file");
        }
    }

    private void hardestCard() {
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
    }

    private void importFile(String filename){
        try {
            System.out.printf("%d cards have been loaded.\n", cards.load(filename));
        }
        catch(Exception e)
        {
            System.out.println("File not found");
        }
    }

    private void importFile() {
        System.out.println("File name");
        String filename = reader.nextLine();
        importFile(filename);
    }

    private void remove() {
        String term;
        System.out.println("The card: ");
        term = reader.nextLine();
        if (cards.remove(term)){
            System.out.println("The card has been removed.");
        }
        else
        {
            System.out.printf("Can't remove \"%s\": there is no such card.\n",term);
        }
    }


    private void add(){
        String term="",definition="";
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
    }
}
