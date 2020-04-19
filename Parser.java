package flashcards;

import java.util.Optional;

public class Parser {
    public static  Optional<String> getImport(String[] args){
        return getItem("import",args);
    }

    public static Optional<String> getExport(String[] args){
        return getItem("export",args);
    }

    public static Optional<String> getItem(String label, String[] args){
        for(int i = 0 ; i < args.length-1 ; i++){
            if (args[i].equals("-"+label)){
                return Optional.of(args[i+1]);
            }
        }
        return Optional.empty();
    }

}
