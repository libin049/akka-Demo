package org.akka.essentials.wc.mapreduce.example.myWordCount;
import akka.actor.ActorRef;
import akka.actor.UntypedActor;

import java.io.*;
import java.util.*;

class Result implements Serializable{
    /**
     *
     */
    private static final long serialVersionUID = 5727560172917790458L;
    private String word;
    private int no_of_instances;

    public Result(String word, int no_of_instances){
        this.setWord(word);
        this.setNoOfInstances(no_of_instances);
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getWord() {
        return word;
    }

    public void setNoOfInstances(int no_of_instances) {
        this.no_of_instances = no_of_instances;
    }

    public int getNoOfInstances() {
        return no_of_instances;
    }

    public String toString(){
        return word + ":" + no_of_instances;
    }

}

public class MapActor extends UntypedActor {

    private ActorRef reduceActor = null;

    public MapActor(ActorRef reduceActor) {
        this.reduceActor = reduceActor;
    }
    @Override
    public void onReceive(Object msg) throws Exception {
        String fileName = (String) msg;
        String fileContext = readFile(fileName);
        reduceActor.tell(wordCount(fileContext));
    }




    String readFile(String name){
        return null;
    }
    private HashMap<String, Integer> wordCount(String context) {
        return null;
    }

    private void process(String fileName) {
        try {
            File file = new File(fileName);
            FileInputStream in = new FileInputStream(file);
            int fileLength = (int) file.length();
            byte[] fileContext = new byte[fileLength];
            in.read(fileContext);
            reduceActor.tell(reduce(evaluateExpression(new String(fileContext))));

        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    private List<Result> evaluateExpression(String context) {
        List<Result> list = new ArrayList<Result>();
        StringTokenizer parser = new StringTokenizer(context);
        while (parser.hasMoreTokens()) {
            String word = parser.nextToken().toLowerCase();
            list.add(new Result(word, 1));
        }
        return list;
    }
    private HashMap<String, Integer> reduce(List<Result> list) {
        HashMap<String, Integer> reducedMap = new HashMap<String, Integer>();
        Iterator<Result> iter = list.iterator();
        while (iter.hasNext()) {
            Result result = iter.next();
            if (reducedMap.containsKey(result.getWord())) {
                Integer value = (Integer) reducedMap.get(result.getWord());
                value++;
                reducedMap.put(result.getWord(), value);
            } else {
                reducedMap.put(result.getWord(), Integer.valueOf(1));
            }
        }
        return reducedMap;
    }
}

