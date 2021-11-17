package zingplay;

import zingplay.data.*;
import zingplay.log.Logger;
import zingplay.offer.OfferListener;
import zingplay.offer.SystemOfferGetDetail;

class SystemOfferController {

    private String version ="3";

    private SystemOfferListener listener;
    private final SystemOfferGetDetail systemOfferGetDetail;

    public SystemOfferController() {
        systemOfferGetDetail = new SystemOfferGetDetail();
    }

    void setListener(SystemOfferListener listener) {
        this.listener = listener;
    }

    public void init(String pathConfig) {
        SOKafkaConfig.getInstance().loadConfig(pathConfig);
        SOKafkaProducer.getInstance().startUp();
        systemOfferGetDetail.start();
    }

    public void dispatchOffer(UserOffers systemOfferUser) {
        this.listener.onReceiveOffer(systemOfferUser);
    }

    public void dispatchOfferDetail(Offer offer) {
        this.systemOfferGetDetail.onReceived(offer);
    }

    public void dispatchDataCustom(UserDataCustom userDataCustom) {
        this.listener.onReceiveDataCustom(userDataCustom);
    }

    public void dispatchConnected(String msg) {
        this.listener.onConnected(msg);
    }

    public void sendTest(String msg) {
        SOKafkaProducer.getInstance().sendTest(msg);
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void send(Sender sender) {
        String name = sender.getClass().getName();
        String action = sender.getAction();
        String msg = version + SystemOfferConst.SEPARATOR_CHAR + sender.getMessage();
        Logger.getInstance().info("SystemOffer|send|" + name +"|"+ action +"|" + msg);
        SOKafkaProducer.getInstance().send(action, msg);
    }

    public void sendWith(RequestOfferDetail sender, OfferListener listener) {
        this.send(sender);
        this.systemOfferGetDetail.add(sender.getIdOffer(), listener);
    }
}
