package zingplay.data;

import zingplay.SystemOfferConst;

public class RequestOfferDetail implements Sender {
    private String idOffer;
    private String country;

    public RequestOfferDetail(String idOffer,String country) {
        this.idOffer = idOffer;
        this.country = country;
    }

    @Override
    public String getAction() {
        return SystemOfferConst.ACTION_GET_OFFER;
    }

    @Override
    public String getMessage() {
        return idOffer + SystemOfferConst.SEPARATOR_CHAR + country;
    }

    public String getIdOffer() {
        return idOffer;
    }

    public String getCountry() {
        return country;
    }
}
