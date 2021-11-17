package zingplay.offer;

import zingplay.data.Offer;
import zingplay.data.SOError;
import zingplay.scheduler.CoreTaskScheduler;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;

public class SystemOfferGetDetail {
    private final Map<String, ListListener> offerListenerMap = new ConcurrentHashMap<>();

    public void start() {
        //5' time out
        CoreTaskScheduler.getInstance().scheduleTask(new Worker(1), 0, 10, TimeUnit.MILLISECONDS);
    }

    class Worker implements Runnable {
        Worker(int index) {
            Thread.currentThread().setName("system-offers-loop-check-" + index);
        }
        @Override
        public void run() {
            try {
                long delay = 5 * 60 * 1000;//5'
                long l = System.currentTimeMillis() - delay;
                Iterator<Map.Entry<String, ListListener>> iter = offerListenerMap.entrySet().iterator();
                while (iter.hasNext()) {
                    Map.Entry<String, ListListener> entry = iter.next();
                    ListListener value = entry.getValue();
                    if(value == null){
                        iter.remove();
                    }else{
                        if(l > value.currentTime){
                            Set<OfferListener> listenerSet = value.listenerSet;
                            if(listenerSet != null){
                                for (OfferListener offerListener : listenerSet) {
                                    try {
                                        offerListener.onFail(SOError.TIMEOUT);
                                    } catch (Exception e){ e.printStackTrace();}
                                }
                            }
                            iter.remove();
                        }
                    }
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    public void add(String idOffer, OfferListener listener){
        ListListener listListener = offerListenerMap.get(idOffer);
        if(listListener == null){
            listListener = new ListListener();
            listListener.add(listener);
        }
        offerListenerMap.put(idOffer,listListener);
    }
    public void onReceived(Offer offer){
        if(offer == null) return;
        String id = offer.getId();
        boolean isFail = false;
        if(offer.getPrice() == null || offer.getItems() == null){
            isFail = true;
        }
        ListListener listListener = offerListenerMap.get(id);
        if(listListener != null){
            Set<OfferListener> listenerSet = listListener.listenerSet;
            for (OfferListener next : listenerSet) {
                try {
                    if(isFail){
                        next.onFail(SOError.NOT_FOUND);
                    }else{
                        next.onReceived(offer);
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
        }
        offerListenerMap.remove(id);
    }
}
