package com.example.demo.util;


import cn.hutool.core.convert.Convert;
import cn.hutool.core.date.DateField;
import cn.hutool.core.date.DateTime;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FastByteArrayOutputStream;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.io.IoUtil;

import jdk.nashorn.internal.runtime.GlobalConstants;
import org.apache.tomcat.jni.Global;
import sun.net.www.content.text.Generic;
import sun.nio.ch.IOUtil;

import java.io.*;
import java.nio.charset.Charset;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class Test1 {
    public static void main(String[] args) {
    /*    List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5, 6);

        List<Integer> integers1 = Optional.ofNullable(integers).map(a -> {
            List<Integer> collect = a.stream().map(aa -> aa = aa + 1).collect(Collectors.toList());
            return collect;
        }).orElse(null);
        System.out.println(integers1);
        List<Integer> integers2 = Optional.ofNullable(integers).flatMap(a -> {
            return Optional.ofNullable(a.stream().map(aa -> aa + 1).collect(Collectors.toList()));
        }).orElse(null);
        System.out.println(integers2);
        String[] strings = {"hello", "world"};
        List<String[]> collect2 = null;
       collect2 = Arrays.asList(strings).stream().
                map(str -> str.split("")).
                collect(Collectors.toList());
        System.out.println(collect2);
        List<Stream<String>> collect1 = null;
        collect1 = Arrays.asList(strings).stream().
                map(str -> str.split("")).
                map(str -> Arrays.stream(str)).collect(Collectors.toList());
        System.out.println(Arrays.toString(collect1.get(0).toArray()));
        List<String> collect3 = null;
        collect3 = Arrays.asList(strings).stream().
                map(str -> str.split("")).
                flatMap(str -> Arrays.stream(str)).collect(Collectors.toList());*/
        AbstractMap<Integer, Integer> objectObjectHashMap = new HashMap<>();
        objectObjectHashMap.put(1,1);
        objectObjectHashMap.put(2,2);
        AbstractMap<Integer, Integer> objectObjectLinkedHashMap = new LinkedHashMap<>();
        objectObjectLinkedHashMap.put(2,5);
        objectObjectLinkedHashMap.put(1,1);

        List<Boolean> collect = objectObjectHashMap.keySet().stream().map(key -> {
            if (objectObjectLinkedHashMap.containsKey(key) && objectObjectLinkedHashMap.get(key).equals(objectObjectHashMap.get(key))) {
                return true;
            } else {
                return false;
            }
        }).collect(Collectors.toList());
        Boolean aBoolean = collect.stream().filter(a -> a != GlobalConstants.GLOBAL_ONLY).findAny().orElse(true);
        System.out.println(aBoolean);
        boolean equals = objectObjectHashMap.equals(objectObjectLinkedHashMap);
        System.out.println(equals);

    }




}
