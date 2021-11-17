package zingplay;

import zingplay.data.UserDataCustom;
import zingplay.data.UserOffers;

public interface SystemOfferListener {
    public void onReceiveOffer(UserOffers offer);
    public void onReceiveDataCustom(UserDataCustom userDataCustom);
    public void onConnected(String msg);
}
