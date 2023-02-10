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

package com.tlcsdm.core.cglib.asm;

import com.tlcsdm.core.cglib.ProxyClassWriter;
import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassWriter;
import jdk.internal.org.objectweb.asm.Opcodes;
import jdk.internal.org.objectweb.asm.Type;

import java.io.IOException;

public class AsmProxyClassWriter extends ProxyClassWriter implements Opcodes {
    public AsmProxyClassWriter(Class superClass, String proxyClassName) {
        super(superClass);
        this.superName = Type.getInternalName(superClass);
        this.proxyClassName = proxyClassName.replace('.', '/');
    }

    @Override
    public byte[] getProxyClassByteArray() {
        ClassReader cr = null;
        try {
            cr = new ClassReader(superclass.getName());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);

        ProxyClassVisitor proxyClassVisitor = new ProxyClassVisitor(ASM5, cw, proxyClassName);

        int parsingOptions = ClassReader.SKIP_DEBUG | ClassReader.SKIP_FRAMES;

        cr.accept(proxyClassVisitor, parsingOptions);
        return cw.toByteArray();
    }

}
