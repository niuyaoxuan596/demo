package com.example.demo.util;

import java.nio.ByteBuffer;

/**
 * 1.缓冲区（Buffer）,在java nio中负责数据的存取。缓冲区就是数组。用于存储不同数据类型的数据
 *
 * 根据数据类型不同（boolean除外），提供了相应类型的缓冲区
 *
 * 所有缓冲区的管理方式几乎一致，通过（allocate）获取缓冲区
 *
 * 2.缓冲区存取数据的两个核心方法
 * put():存入数据到缓冲区
 * get():获取缓冲区中的数据
 *
 * 3.缓冲区中的四个核心属性
 * capacity：容量，表示缓冲区中最大存储数据的容量。一旦生命不能改变
 * limit：界限，表示缓冲区中可以操作数据的大小。（limit后数据不能读写）
 * position：位置，表示缓冲区中正在操作数据的位置
 *
 * position <= limit <= capacity
 */

public class TestBuffer {

    public void test(){
        //1.分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("---------------allocate()------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
    }

}

