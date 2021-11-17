package zingplay.data;

import java.util.HashMap;

/**
 * Created by thuydtm on 9/25/2021.
 */
public class ValueCondition {
    private HashMap<String, String> mapStrParams;
    private HashMap<String, Long> mapLongParams;
    private HashMap<String, Float> mapFloatParams;

    public ValueCondition() {
    }

    public ValueCondition(ValueCondition other) {
        if (other.getMapStrParams() != null) {
            mapStrParams = new HashMap<>();
        }
        if (other.getMapLongParams() != null) {
            mapLongParams = new HashMap<>();
        }
        if (other.getMapFloatParams() != null) {
            mapFloatParams = new HashMap<>();
        }
    }

    public void setParamTracking(String key, String value){
        if(mapStrParams == null) mapStrParams = new HashMap<>();
        mapStrParams.put(key,value);
    }
    public void setParamTracking(String key, Long value){
        if(mapLongParams == null) mapLongParams = new HashMap<>();
        mapLongParams.put(key,value);
    }
    public void setParamTracking(String key, Integer value){
        if(mapLongParams == null) mapLongParams = new HashMap<>();
        mapLongParams.put(key, Long.valueOf(value));
    }
    public void setParamTracking(String key, Float value){
        if(mapFloatParams == null) mapFloatParams = new HashMap<>();
        mapFloatParams.put(key,value);
    }

    public ValueCondition(HashMap<String, String> inListStr, HashMap<String, Long> inListLong, HashMap<String, Float> inListFloat) {
        this.mapStrParams = inListStr;
        this.mapLongParams = inListLong;
        this.mapFloatParams = inListFloat;
    }

    public boolean isValid () {
        return (mapStrParams != null && mapStrParams.size() > 0) || (mapLongParams != null && mapLongParams.size() > 0) || (mapFloatParams != null && mapFloatParams.size() > 0);
    }

    public boolean equals(ValueCondition o) {
        if (this == o) return true;
        if (mapStrParams != null && mapStrParams.size() > 0) {
            for (String s : mapStrParams.keySet()) {
                if (!o.getMapStrParams().get(s).equals(mapStrParams.get(s))) {
                    return false;
                }
            }
        }
        if (mapLongParams != null && mapLongParams.size() > 0) {
            for (String s : mapLongParams.keySet()) {
                if (!o.getMapLongParams().get(s).equals(mapLongParams.get(s))) {
                    return false;
                }
            }
        }
        if (mapFloatParams != null && mapFloatParams.size() > 0) {
            for (String s : mapFloatParams.keySet()) {
                if (!o.getMapFloatParams().get(s).equals(mapFloatParams.get(s))) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "ValueCondition" +
                ((mapStrParams != null) ? "stringParams=" + mapStrParams : "") +
                ((mapLongParams != null) ? ", longParams=" + mapLongParams : "") +
                ((mapFloatParams != null) ? ", floatParams=" + mapFloatParams : "") +
                '}';
    }

    // region get set

    public HashMap<String, String> getMapStrParams() {
        return mapStrParams;
    }

    public void setMapStrParams(HashMap<String, String> mapStrParams) {
        this.mapStrParams = mapStrParams;
    }

    public HashMap<String, Long> getMapLongParams() {
        return mapLongParams;
    }

    public void setMapLongParams(HashMap<String, Long> mapLongParams) {
        this.mapLongParams = mapLongParams;
    }

    public HashMap<String, Float> getMapFloatParams() {
        return mapFloatParams;
    }

    public void setMapFloatParams(HashMap<String, Float> mapFloatParams) {
        this.mapFloatParams = mapFloatParams;
    }

    // endregion get set
}
