package twolevelсache;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class MEMORYCache implements Cache {
    private Map<String, String> cache;
    private Map<String, Class> classMap;
    private int capacity;
    Gson gson = new Gson();

    private final Logger logger = LoggerFactory.getLogger(TwoLevelCacheImpl.class);

    public MEMORYCache(int capacity) {
        this.capacity = capacity;
        cache = new HashMap<>();
        classMap = new HashMap<>();
    }

    @Override
    public void cacheObject(String key, Object obj) {
        logger.info("начинается кэширование объекта в Memory");
        String objectInString = gson.toJson(obj);
        logger.info(objectInString);
        String pathToObject;
        pathToObject = "tempCache\\" + UUID.randomUUID().toString() + ".temp";
        cache.put(key, pathToObject);
        classMap.put(key, obj.getClass());
        try (FileWriter writer = new FileWriter(pathToObject, false)) {
            writer.write(objectInString);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Object getObject(String key) {
        logger.info("получение элемента из Мемори");
        String pathToObject = cache.get(key);
        try (FileReader reader = new FileReader(pathToObject)) {
            StringBuilder objectInString = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                objectInString.append((char) c);
            }
            logger.info(objectInString.toString());
            return gson.fromJson(objectInString.toString(), classMap.get(key));
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public boolean containsObject(String key) {
        return cache.containsKey(key);
    }

    @Override
    public Object removeObject(String key) {
        logger.info("вытаскивается элемент из Мемори");
        String pathToObject = cache.get(key);
        try (FileReader reader = new FileReader(pathToObject)) {
            StringBuilder objectInString = new StringBuilder();
            int c;
            while ((c = reader.read()) != -1) {
                objectInString.append((char) c);
            }
            logger.info(objectInString.toString());
            Object obj = gson.fromJson(objectInString.toString(), classMap.get(key));
            return obj;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        } finally {
            deleteObject(key);
        }
    }

    @Override
    public boolean deleteObject(String key) {
        logger.info("удаляется элемент из Мемори");
        String pathToObject = cache.remove(key);
        classMap.remove(key);
        File deletingFile = new File(pathToObject);
        return deletingFile.delete();
    }

    @Override
    public boolean isNotFull() {
        return cache.size() < capacity;
    }

    @Override
    public void clear() {
        for (Map.Entry<String, String> entry : cache.entrySet()) {
            File deletingFile = new File(entry.getValue());
            System.out.println(deletingFile.delete());
        }
        cache.clear();
        classMap.clear();
    }

    @Override
    public Set<String> keySet() {
        return cache.keySet();
    }

    @Override
    public int getSize() {
        return cache.size();
    }

}
