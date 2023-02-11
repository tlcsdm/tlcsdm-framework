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

package com.tlcsdm.core.converter;

import com.tlcsdm.core.converter.support.ConverterAdapter;
import com.tlcsdm.core.converter.support.StringDateConverter;

import java.util.Collection;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

public class ConverterRegistry {
    private static final GenericConverter[] EMPTY_CONVERTER_ARRAY = new GenericConverter[0];
    protected Map<Class, Set<GenericConverter>> converterMap = new ConcurrentHashMap<>();

    public ConverterRegistry() {
        this(EMPTY_CONVERTER_ARRAY);
    }

    public ConverterRegistry(ConverterRegistry registry) {
        this(registry.getConverters());
    }

    public ConverterRegistry(Collection<GenericConverter> converters) {
        this(converters.toArray(EMPTY_CONVERTER_ARRAY));
    }

    public ConverterRegistry(GenericConverter... converters) {
        initDefaultConverter();
        for (GenericConverter converter : converters) {
            this.registerConverter(converter);
        }
    }

    private void initDefaultConverter() {
        registerConverter(ConverterAdapter.adapter(new StringDateConverter()));
    }

    public void registerConverter(GenericConverter converter) {
        Class targetType = converter.getTargetType();
        Set converterSet;
        if (converterMap.containsKey(targetType)) {
            converterSet = converterMap.get(targetType);
        } else {
            converterSet = new HashSet();
            converterMap.put(targetType, converterSet);
        }
        converterSet.add(converter);
    }

    public boolean isRegister(Class returnType, Class targetType) {
        return getConverter(returnType, targetType) != null;
    }

    public GenericConverter getConverter(Class returnType, Class targetType) {
        if (!converterMap.containsKey(targetType)) {
            return null;
        }
        Set<GenericConverter> converterSet = converterMap.get(targetType);
        for (GenericConverter converter : converterSet) {
            Class rType = converter.getReturnType();
            if (rType != null && rType.isAssignableFrom(returnType)) {
                return converter;
            }
        }
        return null;
    }

    public GenericConverter[] getConverters() {
        return converterMap.isEmpty() ?
                EMPTY_CONVERTER_ARRAY : this.converterMap.values().toArray(EMPTY_CONVERTER_ARRAY);
    }

}
