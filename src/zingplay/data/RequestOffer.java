package zingplay.data;

import zingplay.SystemOfferConst;

public class RequestOffer implements Sender {
    private int userId;
    private long timeCurrent;
    private String country;

    public RequestOffer() {
    }

    public RequestOffer(int userId, long timeCurrent, String country) {
        this.userId = userId;
        this.timeCurrent = timeCurrent;
        this.country = country;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTimeCurrent(long timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getAction() {
        return SystemOfferConst.ACTION_USER_REQUEST_OFFERS;
    }

    @Override
    public String getMessage() {
        return userId + SystemOfferConst.SEPARATOR_CHAR + timeCurrent + SystemOfferConst.SEPARATOR_CHAR + country;
    }
}
