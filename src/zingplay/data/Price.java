package zingplay.data;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.Set;

public class Price implements Serializable {
    @SerializedName("c")
    String country;
    @SerializedName("o")
    long basePrice;
    @SerializedName("p")
    float price;
    @SerializedName("$")
    String currency;
    @SerializedName("b")
    int bonus;
    @SerializedName("s")
    Set<String> channels;


    public String getCountry() {
        return country;
    }

    public long getBasePrice() {
        return basePrice;
    }

    public float getPrice() {
        return price;
    }

    public int getBonus() {
        return bonus;
    }

    public Set<String> getChannels() {
        return channels;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setBasePrice(long basePrice) {
        this.basePrice = basePrice;
    }

    public void setPrice(float price) {
        this.price = price;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public void setBonus(int bonus) {
        this.bonus = bonus;
    }

    public void setChannels(Set<String> channels) {
        this.channels = channels;
    }

    @Override
    public String toString() {
        return "Price{" +
                "country='" + country + '\'' +
                ", basePrice=" + basePrice +
                ", price=" + price +
                ", currency='" + currency + '\'' +
                ", bonus=" + bonus +
                ", channels=" + channels +
                '}';
    }
}
