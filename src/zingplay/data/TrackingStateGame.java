package zingplay.data;

import zingplay.SystemOfferConst;

public class TrackingStateGame implements Sender{
    private int userId;
    private String channelIdx;
    private long totalGame;
    private long timeCurrent;

    public TrackingStateGame() {
    }

    public TrackingStateGame(int userId, int channelIdx, long totalGame, long timeCurrent) {
        this.userId = userId;
        this.channelIdx = String.valueOf(channelIdx);
        this.totalGame = totalGame;
        this.timeCurrent = timeCurrent;
    }

    public void setUserId(int userId) {
        this.userId = userId;
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
        return SystemOfferConst.ACTION_STATS_GAME;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        String separatorChar = SystemOfferConst.SEPARATOR_CHAR;
        stringBuilder.append(userId).append(separatorChar);
        stringBuilder.append(channelIdx).append(separatorChar);
        stringBuilder.append(totalGame).append(separatorChar);
        stringBuilder.append(timeCurrent);
        return stringBuilder.toString();
    }
}
