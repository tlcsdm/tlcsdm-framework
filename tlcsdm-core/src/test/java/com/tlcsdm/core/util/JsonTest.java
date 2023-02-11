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

import com.tlcsdm.core.pojo.json.Human;
import com.tlcsdm.core.pojo.json.Student;

import java.util.ArrayList;
import java.util.List;

public class JsonTest {
    public static void main(String[] args) {
        ArrayList<Human> human = new ArrayList<>();
        human.add(new Human().setAge(32).setName("zs"));
        List<List<Integer>> newStudents = new ArrayList<>();
        List<String> newStudent = new ArrayList<>();
        newStudent.add("1");
        newStudent.add("2");
        newStudent.add("3");

        Student student1 = new Student().setStudent(true).setStuId("123123").setHumans(human).setNewStudents(newStudent.toArray(new String[0]));
        student1.setName("haha").setAge(12);
//        String json ="{ stuId : 123123 , isStudent : true   , humans : [ { name: zs, age:32 } ] , newStudents:[  \"1\" ,  \"2\" ,\"3\" ] , name : haha , age:12 }";
//        System.out.println(json);
//        List<Student> list = new ArrayList<>();
//        list.add(student1);
//        list.add(student1);
//        list.add(student1);
//        String json = JsonHelper.object2JSON(student1);
//        System.out.println(json);
//        Student student = JsonHelper.json2Object(json, Student.class);
//        System.out.println(student);
        String json = JsonParser.object2JSON(student1);
        System.out.println(json);
//        String[] student = JsonHelper.json2Object(json, String[].class);
//
//        System.out.println(Arrays.toString(student));
        Student student = JsonParser.json2Object(json, Student.class);
        System.out.println(student);
//        System.out.println(student);
//        {name=123, cinfoDao={arg={name=123, cinfoDao={arg=123, id=123}}, id=123}}
//        {name=123, cinfoDao={arg={name=123, cinfoDao={arg=123, id=123}}, id=123}}
//        {name=123, cinfoDao={arg={name=123, cinfoDao={arg=123, id=123}}, id=123}}
//        Student{stuId='123123', isStudent=true, humans=[Human{name='zs', age=32}], newStudents=[1, 2, 3], name='haha', age=12}
//        Student{stuId='123123', isStudent=true, humans=[Human{name='zs', age=32}], newStudents=[1, 2, 3], name='haha', age=12}
//        Student{stuId='123123', isStudent=true, humans=[Human{name='zs', age=32}], newStudents=[1, 2, 3], name='haha', age=12}
//        {"stuId":"123123","isStudent":true,"humans":   [{"name":"zs","age":32  }],   " newStudents":[1,2,3]   ,  "name":"haha","age":12}
    }
}
