package flashcards;

import java.io.*;
import java.nio.file.Path;
import java.util.*;
import java.util.stream.Collectors;

public class Cards {
    private final Random random;
    private final LinkedHashMap<String,Integer> errors;
    private final LinkedHashMap<String,String> cards;

    public Cards()
    {
        this.cards = new LinkedHashMap<>();
        this.errors = new LinkedHashMap<>();
        this.random= new Random();
    }


    public boolean add(String term,String definition){
        if ( !(termExist(term) || definitionExist(definition))){
            cards.put(term,definition);
            return true;
        }
        else
        {
            return false;
        }
    }

    public boolean termExist(String term) {
        return cards.containsKey(term);
    }

    public boolean definitionExist(String definition) {
        return cards.containsValue(definition);
    }

    public boolean remove(String term) {
        errors.remove(term);
        return cards.remove(term)  != null ;
    }

    public Card getRandomCard() {
        int id = random.nextInt(cards.size());
        int i=0;

        for(Map.Entry<String,String> card : cards.entrySet()){
            if (id == i){

                return Card.of(card.getKey(),card.getValue(),this);
            }
            i++;
        }

       return  null;
    }

    public String findTerm(String answer) {
        for(Map.Entry<String,String> card : cards.entrySet()){
            if (card.getValue().equals(answer)){
                return card.getKey();
            }
        }
        return "";
    }


    public int load(String filename) throws   IOException {

        File file = new File(  "./"+filename);


        Scanner in =new Scanner(file);
        int count=0;

        while (in.hasNext()){

            String line = in.nextLine();
            String[]  card =line.split(":");
            cards.put(card[0],card[1]);
            errors.put(card[0],Integer.parseInt(card[2]));
            count++;
        }

        return count;
    }


    public int  export(String filename) throws IOException {

        File file = new File(  "./"+filename);

        FileWriter writer = new FileWriter(filename,false);
        for(Map.Entry<String,String> card : cards.entrySet())  {
           String term= card.getKey();
           String definition=card.getValue();
           int error = errors.get(term);
            try {
                writer.write(term+":"+definition+":"+ error+"\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        writer.close();
        return cards.size();

    }

    public void updateError(String term) {
        errors.put(term, errors.getOrDefault(term,0) + 1);
    }

    public void resetStats() {
        for(String term : errors.keySet()){
            errors.put(term,0);
        }
    }

    public int maxError() {
        int max=0;

        for(int error : errors.values()){
            max  = max < error ? error : max;
        }

        return max;
    }

    public List<Card> findHardest() {
        int maxError = maxError();
      return   cards.keySet()
                .stream()
                .filter(term -> errors.getOrDefault(term,0) == maxError)
                .map(term -> Card.of(term, cards.get(term),null))
                .collect(Collectors.toList());
    }
}
