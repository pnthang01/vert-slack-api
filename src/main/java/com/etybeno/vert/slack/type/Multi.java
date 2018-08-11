package com.etybeno.vert.slack.type;

import io.vertx.core.json.JsonArray;
import io.vertx.core.json.JsonObject;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by thangpham on 11/08/2018.
 */
public class Multi<T> {

    private final JsonObject json;
    private final String dataKey;
    private final Class<T> clazz;
    private List<T> dataList;

    public Multi(JsonObject jsonObject, String dataKey, Class<T> clazz) {
        this.json = jsonObject;
        this.dataKey = dataKey;
        this.clazz = clazz;
    }

    public List<T> getDataList() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
        if (null == dataList) {
            dataList = new ArrayList<>();
            JsonArray dataListJson = json.getJsonArray(dataKey);
            if (null != dataListJson && !dataListJson.isEmpty()) {
                Constructor<T> constructor = clazz.getDeclaredConstructor(JsonObject.class);
                for (int i = 0; i < dataListJson.size(); i++) {
                    dataList.add(constructor.newInstance(dataListJson.getJsonObject(i)));
                }
            }
        }
        return dataList;
    }

    public String getNextCursor() {
        if (this.json.containsKey("response_metadata"))
            return this.json.getJsonObject("response_metadata").getString("next_cursor");
        else return null;
    }

    public int getCacheTs() {
        return this.json.getInteger("cache_ts");
    }

}
