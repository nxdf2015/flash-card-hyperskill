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
    private App app;
    public Main(String[] args) {

        this.app=new App(args);

    }




    public static void main(String[] args) throws IOException{
        Main main = new Main(args);
     main.app.start();

    }

}
