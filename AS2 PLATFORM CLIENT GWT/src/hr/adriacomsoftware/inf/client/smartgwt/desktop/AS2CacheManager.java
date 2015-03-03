package hr.adriacomsoftware.inf.client.smartgwt.desktop;

import java.util.LinkedHashMap;
import java.util.Map;

import com.smartgwt.client.data.RecordList;

public class AS2CacheManager{

    private static AS2CacheManager instance;
    private static Object monitor = new Object();

    private Map<String, Object> cache = new LinkedHashMap<String, Object>();

    public AS2CacheManager() {
    }

    public void put(String cacheKey, Object value) {
        cache.put(cacheKey, value);
    }

    public Object get(String cacheKey) {
        return cache.get(cacheKey);
    }

    public boolean isEmpty() {
        return cache.isEmpty();
    }

    public int size() {
        return cache.size();
    }

    public void clear(String cacheKey) {
        cache.put(cacheKey, null);
    }

    public void clear() {
        cache.clear();
    }

    public static AS2CacheManager getInstance() {
        if (instance == null) {
            synchronized (monitor) {
                if (instance == null) {
                    instance = new AS2CacheManager();
                }
            }
        }
        return instance;
    }

	public void fillCacheWithData(RecordList records) {
		LinkedHashMap<String, Object> valueMap = new LinkedHashMap<String, Object>();
		String temp = null;
		for (int i = 0; i < records.getLength(); i++) {
			if (records.get(i) != null) {
				if (i == 0)
					temp = records.get(i).getAttribute("vrsta");

				if (!temp.equals(records.get(i).getAttributeAsString("vrsta"))) {
					AS2CacheManager.getInstance().put(temp,valueMap);
					temp = records.get(i).getAttributeAsString("vrsta");
					valueMap = new LinkedHashMap<String, Object>();
				}
				if (temp.equals(records.get(i).getAttributeAsString("vrsta"))) {
					valueMap.put(records.get(i).getAttributeAsString("id_sifre"),records.get(i).getAttributeAsString("naziv_sifre"));
				}
				if (i == (records.getLength() - 1))
					AS2CacheManager.getInstance().put(temp,valueMap);
			}
		}
	}

}
