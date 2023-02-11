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

import com.tlcsdm.core.exception.BuilderException;

import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class Builder<T> {
    private final Supplier<T> constructor;

    private Predicate<T> verifyPredicate = (i) -> Boolean.TRUE;
    private Consumer<T> opsConsumer = (i) -> {
    };

    private Consumer<Throwable> catchConsumer = (e) -> {
        throw new BuilderException(e);
    };

    private Builder(Supplier<T> constructor) {
        this.constructor = constructor;
    }

    public <P> Builder<T> and(BiConsumer<T, P> ops, P param) {
        Consumer<T> consumer = instance -> ops.accept(instance, param);
        opsConsumer = opsConsumer.andThen(consumer);
        return this;
    }

    public Builder<T> and(Consumer<T> ops) {
        opsConsumer = opsConsumer.andThen(ops);
        return this;
    }

    public <P> Builder<T> verify(Predicate<T> vp) {
        verifyPredicate = vp.and(verifyPredicate);
        return this;
    }

    public Builder<T> catchThrow(Consumer<Throwable> catchOps) {
        catchConsumer = catchOps;
        return this;
    }

    public T build() {
        T instance = null;
        try {
            instance = constructor.get();
            opsConsumer.accept(instance);
            if (!verifyPredicate.test(instance)) {
                throw new BuilderException(instance.toString());
            }
        } catch (Throwable e) {
            catchConsumer.accept(e);
        }
        return instance;
    }

    public static <B> Builder<B> create(Supplier<B> constructor) {
        return new Builder<>(constructor);
    }

}
