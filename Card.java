package flashcards;

public class Card {

    String  definition;
    String term;

    private Card(String definition, String term) {
        this.definition = definition;
        this.term = term;
    }

    public static Card of(String term,String definition){
        return new Card(definition,term);
    }

    public boolean isValidAnswer(String answer){
        return definition.equals(answer);
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }
}
