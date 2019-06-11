package myactor;

public class Counter extends MyActor {
    private int a = 1;
    public int b =2;

    public Counter(String actorName) {
        super(actorName);
    }

    public void onReceive(Object message) throws Exception {
        System.out.println("onReceive:"+"a:"+a+", b:"+a);
    }
}
