package zingplay.offer;

import zingplay.data.Offer;
import zingplay.data.SOError;

public interface OfferListener {
    public void onReceived(Offer offer);
    public void onFail(SOError error);
}
