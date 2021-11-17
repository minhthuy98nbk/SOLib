package zingplay.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;
import java.util.Set;

public class UserOffers implements Serializable {
    @SerializedName("v")
    private String version;
    @SerializedName("u")
    private String userId;
    @SerializedName("o")
    private List<Offer> offers;

    public String getUserId() {
        return userId;
    }

    public List<Offer> getOffers() {
        return offers;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public void setOffers(List<Offer> offers) {
        this.offers = offers;
    }

    @Override
    public String toString() {
        return "UserOffers{" +
                "version='" + version + '\'' +
                ", userId='" + userId + '\'' +
                ", offers=" + offers +
                '}';
    }
}
