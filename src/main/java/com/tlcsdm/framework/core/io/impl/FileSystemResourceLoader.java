package com.tlcsdm.framework.core.io.impl;

import com.tlcsdm.framework.core.io.ResourceHolder;
import com.tlcsdm.framework.core.io.ResourceLoader;
import com.tlcsdm.framework.core.util.StringUtils;

import java.util.Objects;

/**
 * 文件的资源加载器
 */
public class FileSystemResourceLoader implements ResourceLoader {
    private static final String FILE_URL_PREFIX = "file:";

    @Override
    public ResourceHolder getResourceHolder(String location) {
        Objects.requireNonNull(location, "Location must not be null");
        String fileLocation = StringUtils.subStringOnAfter(location, FILE_URL_PREFIX);
        return new FileSystemResourceHolder(fileLocation);
    }

    @Override
    public boolean isMatchLoader(String location) {
        return location.toLowerCase().startsWith(FILE_URL_PREFIX);
    }

}
