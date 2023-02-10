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
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.util.Enumeration;
import java.util.Properties;
import java.util.function.Consumer;

public final class ResourceUtils {

    public static final String PROPERTIES_SUFFIX = ".properties";

    public static URL getResource(String resourcePath) {
        ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
        return defaultClassLoader.getResource(resourcePath);
    }

    public static InputStream getResourceAsStream(String resourcePath) {
        ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
        return defaultClassLoader.getResourceAsStream(resourcePath);
    }

    public static Reader getResourceAsReader(String resourcePath) {
        return new InputStreamReader(getResourceAsStream(resourcePath));
    }

    public static void forEachResources(String resourcePath, Consumer<URL> consumer) throws IOException {
        ClassLoader defaultClassLoader = ClassUtils.getDefaultClassLoader();
        forEachResources(resourcePath, defaultClassLoader, consumer);
    }

    public static void forEachResources(String resourcePath, ClassLoader classLoader, Consumer<URL> consumer) throws IOException {
        Enumeration<URL> resources = classLoader.getResources(resourcePath);
        while (resources.hasMoreElements()) {
            consumer.accept(resources.nextElement());
        }
    }

    public static void loadResource(Properties properties, InputStream inputStream) {
        loadResource(properties, new InputStreamReader(inputStream));
    }

    public static void loadResource(Properties properties, Reader reader) {
        ObjectUtils.requireNonNull(properties, reader);
        try {
            properties.load(reader);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            try {
                reader.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
