package zingplay.data;

import zingplay.SystemOfferConst;

public class TrackingPayment implements Sender{
    private int userId;
    private float costPack;
    private String currency;
    private String channelPayment;
    private String country;
    private long timeCurrent;

    public TrackingPayment() {
    }

    public TrackingPayment(int userId, float costPack, String currency, String channelPayment, String country, long timeCurrent) {
        this.userId = userId;
        this.costPack = costPack;
        this.currency = currency;
        this.channelPayment = channelPayment;
        this.country = country;
        this.timeCurrent = timeCurrent;
    }
    public TrackingPayment(int userId, long costPack, String currency, String channelPayment, String country, long timeCurrent) {
        this.userId = userId;
        this.costPack = costPack;
        this.currency = currency;
        this.channelPayment = channelPayment;
        this.country = country;
        this.timeCurrent = timeCurrent;
    }
    public TrackingPayment(int userId, int costPack, String currency, String channelPayment, String country, long timeCurrent) {
        this.userId = userId;
        this.costPack = costPack;
        this.currency = currency;
        this.channelPayment = channelPayment;
        this.country = country;
        this.timeCurrent = timeCurrent;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setCostPack(float costPack) {
        this.costPack = costPack;
    }
    public void setCostPack(long costPack) {
        this.costPack = costPack;
    }
    public void setCostPack(int costPack) {
        this.costPack = costPack;
    }

    public void setChannelPayment(String channelPayment) {
        this.channelPayment = channelPayment;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTimeCurrent(long timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    @Override
    public String getAction() {
        return SystemOfferConst.ACTION_USER_PAYMENT;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        String separatorChar = SystemOfferConst.SEPARATOR_CHAR;
        stringBuilder.append(userId).append(separatorChar);
        stringBuilder.append(costPack).append(separatorChar);
        stringBuilder.append(currency).append(separatorChar);
        stringBuilder.append(channelPayment).append(separatorChar);
        stringBuilder.append(country).append(separatorChar);
        stringBuilder.append(timeCurrent);
        return stringBuilder.toString();
    }
}
