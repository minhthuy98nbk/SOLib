package zingplay.data;

import zingplay.SystemOfferConst;
import zingplay.helper.GsonHelper;

import java.util.Date;
import java.util.HashMap;

public class Tracking implements Sender{
    private int userId;
    private long timeCreate;
    private long timeCurrent;
    private HashMap<String,ValueCondition> trackingObject;
    private HashMap<String,String> trackingStr;
    private HashMap<String,Long> trackingLong;
    private HashMap<String,Float> trackingFloat;
    private HashMap<String,Long> trackingDuration;

    public Tracking(int userId, long timeCreate, long timeCurrent) {
        this.userId = userId;
        this.timeCreate = timeCreate;
        this.timeCurrent = timeCurrent;
    }

    public Tracking(int userId, long timeCreate, long timeLastOnline, int channelIdx, long totalGame, long timeCurrent) {
        this.userId = userId;
        this.setTracking(TrackingCommon.TimeCreate,timeCreate);
        this.setTracking(TrackingCommon.TimeOnline,timeLastOnline);
        this.setTracking(TrackingCommon.ChannelIdx,channelIdx);
        this.setTracking(TrackingCommon.TotalGame,totalGame);
        this.setTracking(TrackingCommon.TimeCurrent,timeCurrent);
    }
    public Tracking(int userId, long timeCreate, long timeLastOnline, String channelIdx, long totalGame, long timeCurrent) {
        this.userId = userId;
        this.setTracking(TrackingCommon.TimeCreate,timeCreate);
        this.setTracking(TrackingCommon.TimeOnline,timeLastOnline);
        this.setTracking(TrackingCommon.ChannelIdx,channelIdx);
        this.setTracking(TrackingCommon.TotalGame,totalGame);
        this.setTracking(TrackingCommon.TimeCurrent,timeCurrent);
    }
    public void setTracking(String key,String value){
        if(trackingStr == null) trackingStr = new HashMap<>();
        trackingStr.put(key,value);
    }
    public void setTracking(String key,Long value){
        if(trackingLong == null) trackingLong = new HashMap<>();
        trackingLong.put(key,value);
    }
    public void setTrackingDuration(String key,Long value){
        if(trackingDuration == null) trackingDuration = new HashMap<>();
        trackingDuration.put(key,value);
    }
    public void setTracking(String key,Integer value){
        if(trackingLong == null) trackingLong = new HashMap<>();
        trackingLong.put(key, Long.valueOf(value));
    }
    public void setTracking(String key,Float value){
        if(trackingFloat == null) trackingFloat = new HashMap<>();
        trackingFloat.put(key,value);
    }

    public void setTracking(String key,ValueCondition value) {
        if(trackingObject == null) trackingObject = new HashMap<>();
        trackingObject.put(key,value);
    }

    @Override
    public String getAction() {
        return SystemOfferConst.ACTION_TRACKING;
    }

    @Override
    public String getMessage() {
        StringBuilder stringBuilder = new StringBuilder();
        String separatorChar = SystemOfferConst.SEPARATOR_CHAR;
        stringBuilder.append(userId).append(separatorChar);
        stringBuilder.append(timeCreate).append(separatorChar);
        stringBuilder.append(timeCurrent).append(separatorChar);
        stringBuilder.append(trackingStr == null ? "" : GsonHelper.toJson(trackingStr)).append(separatorChar);
        stringBuilder.append(trackingLong == null ? "" : GsonHelper.toJson(trackingLong)).append(separatorChar);
        stringBuilder.append(trackingFloat == null ? "" : GsonHelper.toJson(trackingFloat)).append(separatorChar);
        stringBuilder.append(trackingObject == null ? "" : GsonHelper.toJson(trackingObject)).append(separatorChar);
        stringBuilder.append(trackingDuration == null ? "" : GsonHelper.toJson(trackingDuration)).append(separatorChar);
        return stringBuilder.toString();
    }
}
