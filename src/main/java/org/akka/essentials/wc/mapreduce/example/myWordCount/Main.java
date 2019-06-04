package org.akka.essentials.wc.mapreduce.example.myWordCount;
import akka.actor.*;
import java.util.ArrayList;
import java.util.List;
import akka.actor.ActorSystem;
public class Main {
    static void fileReads() {

    }
    public static void main(String[] args) throws InterruptedException {
        List<String> fileNames = new ArrayList<String>();
        fileNames.add("/home/lb/Akka-Essentials/WordCountMapReduce/src/main/resources/Othello.txt");
        fileNames.add("/home/lb/Akka-Essentials/WordCountMapReduce/src/main/resources/Othello1.txt");

        ActorSystem system = ActorSystem.create("sys");
        // create the aggregate Actor
        final ActorRef aggregateActor = system.actorOf(new Props(ReduceActor.class));
        for (int i = 0; i < fileNames.size(); i++) {
            ActorRef countActor = system.actorOf(new Props(new UntypedActorFactory() {
                public UntypedActor create() {
                    return new MapActor(aggregateActor);
                }
            }), "countActor"+i);
            String fileName = fileNames.get(i);
            countActor.tell(fileName);
        }
        Thread.sleep(5000);
        aggregateActor.tell("DISPLAY_LIST");
    }
}

ActorRef creatActor(Object obj, ActorSystem system){
    system.actorOf(new Props(ReduceActor.class));
    return null;
}