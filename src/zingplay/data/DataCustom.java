package zingplay.data;

import java.util.Set;

public class DataCustom {
    String typeCustom;
    String dataId;
    String dataName;
    private long timeStart;
    private long timeEnd;
    Set<Item> items;

    public String getDataName() {
        return dataName;
    }

    public String getTypeCustom() {
        return typeCustom;
    }

    public Set<Item> getItems() {
        return items;
    }

    public long getTimeStart() {
        return timeStart;
    }

    public long getTimeEnd() {
        return timeEnd;
    }

    public String getDataId() {
        return dataId;
    }

    @Override
    public String toString() {
        return "DataCustom{" +
                "typeCustom=" + typeCustom +
                ", dataName='" + dataName + '\'' +
                ", timeStart=" + timeStart +
                ", timeEnd=" + timeEnd +
                ", items=" + items +
                '}';
    }
}
