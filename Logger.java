package flashcards;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Logger {
    private Scanner reader;
    private int i;
    private List<String> logList;

    public Logger(Scanner reader) {
        this.i=0;
        this.reader = reader;
        this.logList = new ArrayList<>();
    }

    public int nextInt(){
        int value = reader.nextInt();
        addToList(value);
        return value;
    }

    public String nextLine(){
        String line = reader.nextLine();
        addToList(line);
        return line;
    }

    private void addToList(int value){
        logList.add(String.format("%d:%d\n",i,value));
        i++;
    }

    private void addToList(String value){
        logList.add(String.format("%d:%s\n",i,value));
        i++;
    }


    public boolean saveToFile(String filename) {
        File logFile = new File("./"+filename);
        try (FileWriter writer = new FileWriter(logFile, true)) {
           for(String line : logList){
               writer.write(line);
           }
        }
        catch (IOException e){
            return false;
        }
        return true;
    }
}
