package zingplay.data;

import zingplay.SystemOfferConst;

public class TrackingDataCustom implements Sender {
    private int userId;
    private String typeCustom;
    private String dataId;
    private long timeCurrent;
    private String country;

    public TrackingDataCustom() {
    }

    public TrackingDataCustom(int userId, String typeCustom, String dataId, long timeCurrent, String country) {
        this.userId = userId;
        this.typeCustom = typeCustom;
        this.dataId = dataId;
        this.timeCurrent = timeCurrent;
        this.country = country;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getTypeCustom() {
        return typeCustom;
    }

    public void setTypeCustom(String typeCustom) {
        this.typeCustom = typeCustom;
    }

    public String getDataId() {
        return dataId;
    }

    public void setDataId(String dataId) {
        this.dataId = dataId;
    }

    public long getTimeCurrent() {
        return timeCurrent;
    }

    public void setTimeCurrent(long timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    @Override
    public String getAction() {
        return SystemOfferConst.ACTION_RECEIVE_DATA_CUSTOM;
    }

    @Override
    public String getMessage() {
        return userId + SystemOfferConst.SEPARATOR_CHAR +
                typeCustom + SystemOfferConst.SEPARATOR_CHAR
                + dataId + SystemOfferConst.SEPARATOR_CHAR
                + timeCurrent + SystemOfferConst.SEPARATOR_CHAR
                + country + SystemOfferConst.SEPARATOR_CHAR ;
    }
}
