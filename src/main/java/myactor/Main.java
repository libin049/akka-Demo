package myactor;

public class Main {
    public static void main(String[] args) throws Exception {
        Counter counter = new Counter();
        counter.onReceiveHandle();
    }
}
