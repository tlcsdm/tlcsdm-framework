package com.tlcsdm.framework.cloud.register;

import com.tlcsdm.framework.cloud.URL;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class RemoteMapRegister {

    private static Map<String, List<URL>> REGISTER = new HashMap<>();

    private static String FILE_PATH = "/temp1.txt";

    public static void register(URL url) {
        String interfaceKey = getInterfaceKey(url.getInterfaceName(), url.getVersion());
        List<URL> list = REGISTER.get(interfaceKey);
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(url);
        REGISTER.put(interfaceKey, list);
        saveFile();
    }

    public static List<URL> get(String interfaceName, String version) {
        REGISTER = getFile();
        List<URL> list = REGISTER.get(getInterfaceKey(interfaceName, version));
        return list;
    }

    private static String getInterfaceKey(String interfaceName, String version) {
        return interfaceName + version;
    }

    private static void saveFile() {
        try (ObjectOutputStream objectOutputStream = new ObjectOutputStream(new FileOutputStream(FILE_PATH))) {
//            REGISTER.putAll(getFile());
            objectOutputStream.writeObject(REGISTER);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static Map<String, List<URL>> getFile() {
        try (ObjectInputStream objectInputStream = new ObjectInputStream(new FileInputStream(FILE_PATH));) {
            return (Map<String, List<URL>>) objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }
}
