package twolevelсache;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class MEMORYCache implements Cache {
    private Map<String, String> cache;
    private Map<String, Class> classMap;
    private int capacity;
    private Path cacheTempFolder;
    private Gson gson = new Gson();

    private final Logger logger = LoggerFactory.getLogger(TwoLevelCacheImpl.class);

    MEMORYCache(int capacity) {
        this.capacity = capacity;
        cache = new ConcurrentHashMap<>();
        classMap = new ConcurrentHashMap<>();
        try {
            cacheTempFolder = Files.createTempDirectory("cacheTempFolder");
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (cacheTempFolder != null) {
            cacheTempFolder.toFile().deleteOnExit();
        }
    }

//    @Override
//    public synchronized void cacheObject(String key, Object obj) {
//        logger.info("начинается кэширование объекта в Memory");
//        String objectInString = gson.toJson(obj);
//        logger.info(objectInString);
//        String pathToObject;
//        pathToObject = cacheTempFolder.toString() + "\\" + UUID.randomUUID().toString() + ".temp";
//        cache.put(key, pathToObject);
//        classMap.put(key, obj.getClass());
//        try (FileWriter writer = new FileWriter(pathToObject)) {
//            writer.write(objectInString);
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//    }

    @Override
    public synchronized void cacheObject(String key, Object obj) {
        logger.info("начинается кэширование объекта в Memory");
        String objectInString = gson.toJson(obj);
        logger.info(objectInString);
        File tempFile = null;
        try {
            tempFile = File.createTempFile(UUID.randomUUID().toString(), ".javatemp", cacheTempFolder.toFile());
            tempFile.deleteOnExit();
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (tempFile != null) {
            cache.put(key, tempFile.getPath());
            classMap.put(key, obj.getClass());
            try (FileWriter writer = new FileWriter(tempFile)) {
                writer.write(objectInString);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public Object getObject(String key) {
        logger.info("получение элемента из Мемори");
        String pathToObject = cache.get(key);
        try (BufferedReader br = new BufferedReader(new InputStreamReader(new FileInputStream(pathToObject)))) {
            StringBuilder objectInJson = new StringBuilder();
            String strLine;
            while ((strLine = br.readLine()) != null) {
                objectInJson.append(strLine);
            }
            logger.info(objectInJson.toString());
            return gson.fromJson(objectInJson.toString(), classMap.get(key));
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
    public synchronized Object removeObject(String key) {
        logger.info("вытаскивается элемент из Мемори");
        try {
            return getObject(key);
        } finally {
            deleteObject(key);
        }
    }

    @Override
    public synchronized boolean deleteObject(String key) {
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
    public synchronized void clear() {
        for (String key : cache.keySet()) {
            deleteObject(key);
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
