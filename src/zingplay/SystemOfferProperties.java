package zingplay;

import zingplay.log.Logger;

class SystemOfferProperties extends PropertiesLoader {
    private static SystemOfferProperties instance;
    private SystemOfferProperties(String fileDir) {
        super(fileDir);
    }
    public static SystemOfferProperties getInstance() {
        return getInstance("");
    }

    public static SystemOfferProperties getInstance(String path) {
        if(path.isEmpty()){
            path = "res/system.offer.properties";
        }
        Logger.getInstance().debug("load config at " + path);
        if(instance == null){
            instance = new SystemOfferProperties(path);
        }
        return instance;
    }


}
