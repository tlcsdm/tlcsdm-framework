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

package com.tlcsdm.core.cglib;

import com.tlcsdm.core.cglib.asm.AsmProxyClassWriter;
import com.tlcsdm.core.cglib.asm.AsmUtil;

import java.net.URL;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

import static com.tlcsdm.core.cglib.ProxyClass.getProxyClassName;

public final class ProxyClassLoader extends ClassLoader {
    private final Map<String, Class> proxyClassCache = new ConcurrentHashMap<>();

    ProxyClassLoader(ClassLoader parent) {
        super(parent);
    }

    Class getProxyClass(Class superclass, boolean isWriteFile) {
        String cacheKey = getProxyClassName(superclass);
        if (proxyClassCache.containsKey(cacheKey)) {
            return proxyClassCache.get(cacheKey);
        }
        ProxyClassWriter proxyClassWriter = new AsmProxyClassWriter(superclass, cacheKey);
        byte[] cacheBytes = proxyClassWriter.getProxyClassByteArray();

        if (isWriteFile) {
            int index = cacheKey.lastIndexOf('.');
            if (index == -1) {
                throw new IllegalArgumentException("");
            }
            String superClassFile = cacheKey.substring(0, index).replace(".", "/");

            String proxyClassFile = cacheKey.substring(index + 1) + ".class";
            URL resource = this.getResource(superClassFile);
            Objects.requireNonNull(resource);
            String filePath = resource.getFile();
            AsmUtil.writeBytes(filePath + "/" + proxyClassFile, cacheBytes);
        }

        Class<?> proxyClass = defineClass(cacheKey, cacheBytes, 0, cacheBytes.length);
        proxyClassCache.put(cacheKey, proxyClass);
        return proxyClass;
    }

}