package zingplay;


import zingplay.log.Logger;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;


class PropertiesLoader {

    private Random r = new Random(System.currentTimeMillis());

    private Properties props;
    private ConcurrentHashMap<String, Long> longPropsCaching;
    private ConcurrentHashMap<String, String[]> listPropsCaching;

    public PropertiesLoader(String fileDir) {
        try {
            longPropsCaching = new ConcurrentHashMap<String, Long>();
            listPropsCaching = new ConcurrentHashMap<String, String[]>();
            props = new Properties();
            props.load(new InputStreamReader(new FileInputStream(System.getProperty("user.dir") +
                    File.separator + fileDir), "UTF8"));
        } catch (Exception e) {
            Logger.getInstance().error(e);
        }
    }

    public String get(String name) {
        if (props != null)
            return props.getProperty(name);
        else
            return null;
    }

    public Long getLong(String name) {
        Long result = longPropsCaching.get(name);
        if (result == null) {
            result = new Long(0);
            try {
                result = Long.parseLong(props.getProperty(name));
            } catch (Exception e) {
                Logger.getInstance().error(e);
            }
            longPropsCaching.put(name, result);
        }
        return result;
    }

    public Double getDouble(String name) {
        Double result = new Double(0);
        try {
            result = Double.parseDouble(props.getProperty(name));
        } catch (Exception e) {
            Logger.getInstance().error(e);
        }

        return result;
    }

    public Integer getInt(String name) {
        return getLong(name).intValue();
    }

    public Boolean getBoolean(String name) {
        return (getLong(name) == 1);
    }

    public String getRandom(String name) {
        String[] data = listPropsCaching.get(name);
        if (data == null) {
            data = new String[0];
            try {
                data = props.getProperty(name).split(";");
            } catch (Exception e) {
                Logger.getInstance().error(e);
            }
            listPropsCaching.put(name, data);
        }
        if (data.length > 0) {
            return data[r.nextInt(data.length)];
        } else
            return null;
    }
}
