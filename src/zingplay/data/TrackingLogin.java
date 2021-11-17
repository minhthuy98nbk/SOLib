package zingplay.data;

import zingplay.SystemOfferConst;

public class TrackingLogin implements Sender{
    private int userId;
    private long timeCreate;
    private long timeLastOnline;
    private String channelIdx;
    private long totalGame;
    private long timeCurrent;
    private String region = "";

    public TrackingLogin() {
    }

    public TrackingLogin(int userId, long timeCreate, long timeLastOnline, int channelIdx, long totalGame, long timeCurrent) {
        this.userId = userId;
        this.timeCreate = timeCreate;
        this.timeLastOnline = timeLastOnline;
        this.channelIdx = String.valueOf(channelIdx);
        this.totalGame = totalGame;
        this.timeCurrent = timeCurrent;
    }
    public TrackingLogin(int userId, long timeCreate, long timeLastOnline, String channelIdx, long totalGame, long timeCurrent) {
        this.userId = userId;
        this.timeCreate = timeCreate;
        this.timeLastOnline = timeLastOnline;
        this.channelIdx = channelIdx;
        this.totalGame = totalGame;
        this.timeCurrent = timeCurrent;
    }

    public void setRegion(String region) {
        this.region = region;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public void setTimeCreate(long timeCreate) {
        this.timeCreate = timeCreate;
    }

    public void setTimeLastOnline(long timeLastOnline) {
        this.timeLastOnline = timeLastOnline;
    }

    public void setChannelIdx(int channelIdx) {
        this.channelIdx = String.valueOf(channelIdx);
    }

    public void setChannelIdx(String channelIdx) {
        this.channelIdx = channelIdx;
    }

    public void setTotalGame(long totalGame) {
        this.totalGame = totalGame;
    }

    public void setTimeCurrent(long timeCurrent) {
        this.timeCurrent = timeCurrent;
    }

    @Override
    public String getAction() {
        return SystemOfferConst.ACTION_LOGIN_V2;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        String separatorChar = SystemOfferConst.SEPARATOR_CHAR;
        stringBuilder.append(userId).append(separatorChar);
        stringBuilder.append(timeCreate).append(separatorChar);
        stringBuilder.append(timeLastOnline).append(separatorChar);
        stringBuilder.append(channelIdx).append(separatorChar);
        stringBuilder.append(totalGame).append(separatorChar);
        stringBuilder.append(region).append(separatorChar);
        stringBuilder.append(timeCurrent);
        return stringBuilder.toString();
    }
}
