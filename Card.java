package flashcards;

public class Card {


    private final Cards ctx;
    String  definition;
    String term;

    private Card(String definition, String term ,Cards ctx) {
        this.definition = definition;
        this.term = term;
        this.ctx = ctx;
    }

    public static Card of(String term,String definition,Cards ctx ){
        return new Card(definition,term ,ctx);
    }

    public boolean isValidAnswer(String answer){
        if(definition.equals(answer)){
            return true;
        }
        else
        {
            ctx.updateError(term);
            return false;
        }
    }

    public String getTerm() {
        return term;
    }

    public String getDefinition() {
        return definition;
    }


}
