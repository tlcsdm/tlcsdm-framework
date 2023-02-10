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

public abstract class BeanUtils {

    public static String toBeanName(String columnName) {
        columnName = columnName.toLowerCase();
        if (columnName.indexOf("_") != -1) {
            columnName = columnName.toLowerCase();
            String[] strings = columnName.split("_");
            String beanName = strings[0];
            for (int i = 1; i < strings.length; i++) {
                char[] chars = strings[i].toCharArray();
                chars[0] = Character.toUpperCase(chars[0]);
                beanName += new String(chars);
            }
            return beanName;
        }
        return columnName;
    }

    public static String toTableName(String name) {
        char[] chars = name.toCharArray();
        StringBuffer buffer = new StringBuffer();
        buffer.append(chars[0]);
        for (int i = 1; i < chars.length; i++) {
            if (Character.isUpperCase(chars[i])) {
                buffer.append("_");
            }
            buffer.append(chars[i]);
        }
        return buffer.toString().toUpperCase();
    }

//    public static Map<String, Object> getColumnMap(List<String>columnName,Object voBean)  {
//        return getColumnMap(columnName.toArray(new String[0]),voBean);
//    }
//    public static Map<String, Object> getColumnMap(String columnName,Object voBean)  {
//        return getColumnMap(columnName.split(","),voBean);
//    }
//    public static Object getColumn(String columnName,Object voBean)  {
//
//        columnName = columnName.trim();
//        try {
//            return getColumnMapVal(voBean.getClass().getDeclaredFields(),columnName,voBean);
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }
//        return null;
//    }
//    public static Map<String, Object> getColumnMap(String[] columnName,Object voBean) {
//        Map<String, Object> columnMap = new HashMap<>();
//        Class vosClass = voBean.getClass();
//        Field[] voFields = vosClass.getDeclaredFields();
//        Object val;
//       try {
//           for (String name : columnName){
//               name = name.trim();
//               if (name.equals("")){
//                   continue;
//               }
//               val = getColumnMapVal(voFields,name,voBean);
//               columnMap.put(name,val);
//           }
//       } catch (IllegalAccessException e) {
//           e.printStackTrace();
//       }
//        return columnMap;
//    }
//    private static Object getColumnMapVal(Field[] fields,String name,Object voBean) throws IllegalAccessException {
//        Object val = null;
//        for (Field voField : fields) {
//            Class beanClass = voField.getType();
//            voField.setAccessible(true);
//            if (voField.getName().equals(name)){
//                return voField.get(voBean);
//            } else if (isExtends(beanClass, BaseBeanAbs.class)) {
//                try {
//                    Field columnField = beanClass.getDeclaredField(name);
//                    columnField.setAccessible(true);
//                    Object fieldVal = voField.get(voBean);
//                    val = columnField.get(fieldVal);
//                    break;
//                }catch (NoSuchFieldException e) {
//                }
//            }else {
//                val =  getColumnMapVal(beanClass.getDeclaredFields(),name,voField.get(voBean));
//                if (val!=null){
//                    return val;
//                }
//            }
//        }
//        return val;
//    }

}
