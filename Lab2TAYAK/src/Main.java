import graph.Graph;
import graph.TuringMachine;
import graph.Utils;
import graph.command.errors.IllegalCommandFormatException;
import graph.parts.Entity;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;

public class Main {
    public static void main(String[] args) {
        File file = new File("var1.txt");
        try {
            BufferedReader br = new BufferedReader(new FileReader("var1.txt"));
            Graph gp = new Graph();
            br.lines().forEach(s -> {
                try {
                    gp.addNewEdgeFromCommand(Utils.parseFromString(s));
                } catch (IllegalCommandFormatException e) {
                    e.printStackTrace();
                }
            });
            System.out.println(gp.toString());

            Entity issueEntity;
            while((issueEntity=Utils.getIssueEntity(gp))!=null){
                Utils.normalizeForOneEntity(gp,issueEntity);
            }
            while(Utils.haveUselessEntities(gp)) {
                Utils.removeUselessEntity(gp);
            }

            TuringMachine tm = new TuringMachine(gp);
            System.out.println(gp.toString());
            System.out.println(tm.isCorrectString("aekc"));
//            Utils.normalizeForOneEntity(gp,new Entity("q0"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
