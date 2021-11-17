package zingplay.data;

import java.io.Serializable;
import java.util.List;
import java.util.Map;

public class UserDataCustom implements Serializable {
    private String version;
    private String userId;
    private List<DataCustom> dataCustoms;

    public String getVersion() {
        return version;
    }

    public String getUserId() {
        return userId;
    }

    public List<DataCustom> getDataCustoms() {
        return dataCustoms;
    }

    @Override
    public String toString() {
        return "UserOffers{" +
                "version='" + version + '\'' +
                ", userId='" + userId + '\'' +
                ", dataCustoms=" + dataCustoms +
                '}';
    }
}
