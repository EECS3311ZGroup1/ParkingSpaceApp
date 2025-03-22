package yorku.eecs3311;

public interface Subject {
    static void addSubscriber(Subscriber sub) {}
    static void removeSubscriber(Subscriber sub) {}
    static void notifySubscribers() {}
}
