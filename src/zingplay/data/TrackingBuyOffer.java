package zingplay.data;

import zingplay.SystemOfferConst;

public class TrackingBuyOffer implements Sender{
    private int userId;
    private String idOffer;

    private String country;
    private float price;
    private String currency;

    private long timeCurrent;

    public TrackingBuyOffer() {
    }


    public TrackingBuyOffer(int userId, String idOffer, String country,float price, String currency, long timeCurrent) {
        this.userId = userId;
        this.idOffer = idOffer;

        this.country = country;
        this.price = price;

        this.timeCurrent = timeCurrent;
    }
    public TrackingBuyOffer(int userId, String idOffer, String country,int price, String currency, long timeCurrent) {
        this.userId = userId;
        this.idOffer = idOffer;

        this.country = country;
        this.price = price;

        this.timeCurrent = timeCurrent;
    }
    public TrackingBuyOffer(int userId, String idOffer, String country,long price, String currency, long timeCurrent) {
        this.userId = userId;
        this.idOffer = idOffer;

        this.country = country;
        this.price = price;

        this.timeCurrent = timeCurrent;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setIdOffer(String idOffer) {
        this.idOffer = idOffer;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setTimeCurrent(long timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    public void setPrice(float price) { this.price = price; }
    public void setPrice(int price) { this.price = price; }
    public void setPrice(long price) { this.price = price; }

    @Override
    public String getAction() {
        return SystemOfferConst.ACTION_USER_BUY_OFFER;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        String separatorChar = SystemOfferConst.SEPARATOR_CHAR;
        stringBuilder.append(userId).append(separatorChar);
        stringBuilder.append(idOffer).append(separatorChar);
        stringBuilder.append(country).append(separatorChar);
        stringBuilder.append(price).append(separatorChar);
        stringBuilder.append(currency).append(separatorChar);
        stringBuilder.append(timeCurrent);
        return stringBuilder.toString();
    }
}
