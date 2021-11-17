package zingplay.offer;

import java.util.HashSet;
import java.util.Set;

public class ListListener {
    long currentTime;
    Set<OfferListener> listenerSet;

    public ListListener() {
        currentTime = System.currentTimeMillis();
        listenerSet = new HashSet<>();
    }
    public void add(OfferListener listener){
        listenerSet.add(listener);
    }
    public void remove(OfferListener listener){
        listenerSet.remove(listener);
    }
}
