/*
 * Copyright (c) 2019, 2023 unknowIfGuestInDream
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *     * Redistributions of source code must retain the above copyright
 * notice, this list of conditions and the following disclaimer.
 *     * Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *     * Neither the name of unknowIfGuestInDream, any associated website, nor the
 * names of its contributors may be used to endorse or promote products
 * derived from this software without specific prior written permission.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 * ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 * WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 * DISCLAIMED. IN NO EVENT SHALL UNKNOWIFGUESTINDREAM BE LIABLE FOR ANY
 * DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 * (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 * LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 * ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 * (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 * SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
 */

package com.tlcsdm.core.util;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public final class FactoryLoader<T> {
    private List<String> classNameCache;
    private final Class<T> factoryInterfaces;
    private final String factoryInterfacesName;
    private final ClassLoader classLoader;
    private List<Class<? extends T>> classCache;
    private List<T> instanceCache;

    private static Map<String, FactoryLoader> factoryCache;

    private static final String FACTORY_PATH = "META-INF/kamo.factories";

    private FactoryLoader(List<String> classNameCache, String factoryInterfacesName, ClassLoader classLoader) {
        this.classNameCache = classNameCache;
        this.factoryInterfacesName = factoryInterfacesName;
        this.classLoader = classLoader;
        this.factoryInterfaces = ClassUtils.loadClass(classLoader, factoryInterfacesName);
    }

    public List<? extends T> getInstance() {
        if (instanceCache == null) {
            createInstance();
        }
        return new ArrayList<>(instanceCache);
    }

    public Class<T> getFactoryInterfaces() {
        return factoryInterfaces;
    }

    public String getFactoryInterfacesName() {
        return factoryInterfacesName;
    }

    private void createInstance() {
        instanceCache = new ArrayList<>();
        Class<? extends T>[] factoryClasses = getFactoryClasses();
        for (Class<? extends T> factoryClass : factoryClasses) {
            T instance = ReflectUtils.newInstance(factoryClass);
            instanceCache.add(instance);
        }
    }

    public Class<? extends T>[] getFactoryClasses() {
        if (classCache == null) {
            classCache = new ArrayList<>();
            for (String name : classNameCache) {
                classCache.add(ClassUtils.loadClass(classLoader, name));
            }
        }
        return classCache.toArray(new Class[0]);
    }

    public Class[] getFactoryClasses(ClassLoader classLoader) {
        List<Class> classList = new ArrayList<>();
        for (String name : classNameCache) {
            classList.add(ClassUtils.loadClass(classLoader, name));
        }
        return classList.toArray(new Class[0]);
    }

    public static <T> FactoryLoader<T> load(Class<T> factoryClass) {
        return load(factoryClass, ClassUtils.getDefaultClassLoader());
    }

    public static <T> FactoryLoader<T> load(Class<T> factoryClass, ClassLoader classLoader) {
        return load(factoryClass.getName(), classLoader);
    }

    public static <T> FactoryLoader<T> load(String factoryClassName) {
        return load(factoryClassName, ClassUtils.getDefaultClassLoader());
    }

    public static <T> FactoryLoader<T> load(String factoryClassName, ClassLoader classLoader) {

        if (factoryCache == null) {
            try {
                loadCache(classLoader);
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }
        if (!factoryCache.containsKey(factoryClassName)) {
            return null;
        }
        FactoryLoader<T> factoryLoader = factoryCache.get(factoryClassName);

        return factoryLoader;
    }

    private static void loadCache(ClassLoader classLoader) throws IOException {
        factoryCache = new ConcurrentHashMap<>();
        ResourceUtils.forEachResources(FACTORY_PATH, classLoader, url -> {
            doLoad(url, classLoader);
        });
        //去重
        for (String interfacesName : factoryCache.keySet()) {
            FactoryLoader factoryLoader = factoryCache.get(interfacesName);
            List<String> oldList = factoryLoader.classNameCache;
            factoryLoader.classNameCache = ListUtils.deduplication(oldList);
        }
    }

    private static void doLoad(URL factoryURL, ClassLoader classLoader) {
        Properties properties = new Properties();
        try (InputStream inputStream = factoryURL.openStream()) {
            properties.load(inputStream);
            Set<String> interfacesNames = properties.stringPropertyNames();
            for (String interfacesName : interfacesNames) {
                String[] factoryNameArray = properties.getProperty(interfacesName).split(",");
                List<String> factoryNames = ListUtils.array2List(factoryNameArray);
                if (factoryCache.containsKey(interfacesName)) {
                    factoryCache.get(interfacesName).classNameCache.addAll(factoryNames);
                } else {
                    FactoryLoader factoryLoader = new FactoryLoader<>(factoryNames, interfacesName, classLoader);
                    factoryCache.put(interfacesName, factoryLoader);
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
