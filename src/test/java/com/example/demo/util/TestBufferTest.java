package com.example.demo.util;




import org.junit.jupiter.api.Test;
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
 * mark: 标记，表示记录当前position的位置。可以通过reset（）恢复到mark的位置
 *
 * position <= limit <= capacity
 *
 * 4.直接缓冲区与非直接缓冲区：
 * 非直接缓冲区：通过allocate（）方法分配缓冲区，将缓冲区建立在JVM的内存中
 * 直接缓冲区：通过allocateDirect（）方法分配直接缓冲区，将缓冲区建立在物理内存中。可以提高效率
 *
 */


class TestBufferTest {
    @Test
    void test1() {
        String str = "abcde";

        //1.分配一个指定大小的缓冲区
        ByteBuffer buf = ByteBuffer.allocate(1024);
        System.out.println("---------------allocate()------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //2.利用put（）存入数据到缓冲区中
        buf.put(str.getBytes());

        System.out.println("---------------put()------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //3.切换读取数据模式
        buf.flip();
        System.out.println("---------------flip()------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //4.利用get（）方法读取缓冲区中的数据
        byte[] bytes = new byte[buf.limit()];
        buf.get(bytes);
        System.out.println(new String(bytes,0,bytes.length));

        System.out.println("---------------get()------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //5.rewind（）方法可重复读
        buf.rewind();
        System.out.println("---------------rewind()------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());

        //6.clear（）：清空缓冲区,但是缓冲区中的数据依然存在，但是处于“被遗忘”状态
        buf.clear();
        System.out.println("---------------clear()------------");
        System.out.println(buf.position());
        System.out.println(buf.limit());
        System.out.println(buf.capacity());
        System.out.println((char) buf.get(0));

    }

    @Test
    void test2(){
        String str = "abcde";
        ByteBuffer buf = ByteBuffer.allocate(1024);

        buf.get(str.getBytes());

        buf.flip();

        byte[] bytes = new byte[buf.limit()];
        buf.get(bytes,0,2);
        System.out.println(new String(bytes,0,2));
        System.out.println(buf.position());

        //mark() :标记
        buf.mark();

        buf.get(bytes,2,2);
        System.out.println(new String(bytes,0,2));
        System.out.println(buf.position());

        //reset():恢复到mark的位置
        buf.reset();
        System.out.println(buf.position());

    }
    @Test
    void test3(){
        //分配直接缓冲区
        ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println(buf.isDirect());


    }
}