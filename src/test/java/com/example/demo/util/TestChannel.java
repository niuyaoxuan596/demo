package com.example.demo.util;

import org.junit.jupiter.api.Test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.CharBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;

/**
 * 1.通道（Channel）。用于源节点与目标节点的连接。在Java NIO中负责缓冲区数据的传输。Channel本身不存储数据，因此需要配合缓冲区进行传输。
 *
 * 2.通道的主要实现类
 * java.nio.channels.Channel接口
 *   --FileChannel
 *   --SocketChannel
 *   --ServerSocketChannel
 *   --DatagramChannel
 *
 * 3.获取通道
 *   （1）Java针对支持通道的类提供了getChannel（）方法
 *        本地IO:
 *        FileInputStream/FileOutputStream
 *        RandomAccessFile
 *        网络IO:
 *        Socket
 *        ServerSocket
 *        DatagramSocket
 *
 *    (2)在JDK1.7 中的NIO2 针对各个通道提供了静态方法open（）
 *   （3）在JDK1.7中的NIO2 的Files工具类的newByteChannel（）也可以获取通道
 *
 * 4.通道之间的数据传输
 *   transferFrom()
 *   transforTo()
 *   
 * 5.分散（Scatter）与聚集（Gather）
 * 分散读取（Scattering Reads）:将通道中的数据分散到多个缓冲区中
 * 聚集写入（Gathering Writes）:将多个缓冲区中的数据聚集到通道中
 *
 * 6.字符集：Charset
 *   编码：字符串-》字节数组
 *   解码：字节数组-》字符串
 */
public class TestChannel {

    //1.利用通道完成文件的复制
    //非直接缓冲区
    @Test
    void test1(){
        long start = System.currentTimeMillis();
        FileInputStream fis = null;
        FileOutputStream fos = null;
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            fis = new FileInputStream("E:\\project\\demo\\src\\main\\resources\\1.jpg");
            fos = new FileOutputStream("E:\\project\\demo\\src\\main\\resources\\2.jpg");

            //1.获取通道
            inChannel = fis.getChannel();
            outChannel = fos.getChannel();

            //2.分配指定大小的缓冲区
            ByteBuffer buf = ByteBuffer.allocate(1024);

            //3.将通道中的数据存入缓冲区中
            while(inChannel.read(buf) != -1){
                buf.flip();//切换读取数据的模式
                //4.将缓冲区中的数据写入通道
                outChannel.write(buf);
                buf.clear();//清空缓冲区
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fos != null){
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null){
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间为："+(end - start));
    }

    //2.利用直接缓冲区完成文件的复制（内存映射文件）
    //直接缓冲区
    @Test
    void test2(){
        long start = System.currentTimeMillis();
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("E:\\project\\demo\\src\\main\\resources\\1.jpg"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("E:\\project\\demo\\src\\main\\resources\\3.jpg"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);

            //内存映射文件
            MappedByteBuffer inMappedBuf = inChannel.map(FileChannel.MapMode.READ_ONLY, 0, inChannel.size());
            MappedByteBuffer outMappedBuf = outChannel.map(FileChannel.MapMode.READ_WRITE, 0, inChannel.size());

            //直接对缓冲区进行数据的读写操作
            byte[] bytes = new byte[inMappedBuf.limit()];
            inMappedBuf.get(bytes);
            outMappedBuf.put(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间为："+(end - start));
    }
    //3.通道之间的数据传输
    //直接缓冲区
    @Test
    void test3(){
        long start = System.currentTimeMillis();
        FileChannel inChannel = null;
        FileChannel outChannel = null;
        try {
            inChannel = FileChannel.open(Paths.get("E:\\project\\demo\\src\\main\\resources\\1.jpg"), StandardOpenOption.READ);
            outChannel = FileChannel.open(Paths.get("E:\\project\\demo\\src\\main\\resources\\4.jpg"), StandardOpenOption.WRITE,StandardOpenOption.READ,StandardOpenOption.CREATE_NEW);

            //inChannel.transferTo(0,inChannel.size(),outChannel);
            outChannel.transferFrom(inChannel,0,inChannel.size());
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (outChannel != null){
                try {
                    outChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (inChannel != null){
                try {
                    inChannel.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        long end = System.currentTimeMillis();
        System.out.println("耗费时间为："+(end - start));
    }
    //4.分散和聚集
    @Test
    void test4() throws IOException {
        RandomAccessFile raf1 = new RandomAccessFile("E:\\test.txt", "rw");

        //1.获取通道
        FileChannel channel1 = raf1.getChannel();

        //2.分配指定大小的缓冲区
        ByteBuffer buf1 = ByteBuffer.allocate(100);
        ByteBuffer buf2 = ByteBuffer.allocate(1024);

        //3.分散读取
        ByteBuffer[] bufs = {buf1,buf2};
        channel1.read(bufs);
        for (ByteBuffer byteBuffer:bufs) {
            byteBuffer.flip();
        }

        System.out.println(new String(bufs[0].array(),0,bufs[0].limit()));
        System.out.println("-----------------");
        System.out.println(new String(bufs[1].array(),0,bufs[1].limit()));

        //4.聚集写入
        RandomAccessFile raf2 = new RandomAccessFile("E:\\test2.txt", "rw");
        FileChannel channel2 = raf2.getChannel();
        channel2.write(bufs);

        channel2.close();
        channel1.close();
        raf2.close();
        raf1.close();
    }
    //5.字符集
    @Test
    void test5() throws CharacterCodingException {
        Charset cs1 = Charset.forName("GBK");
        CharsetEncoder encoder = cs1.newEncoder();
        CharsetDecoder decoder = cs1.newDecoder();
        CharBuffer cbuf = CharBuffer.allocate(1024);
        cbuf.put("牛耀轩牛逼！");
        cbuf.flip();
        ByteBuffer bbuf = encoder.encode(cbuf);
        for (int i = 0; i < 12; i++) {
            System.out.println(bbuf.get());
        }
        bbuf.flip();
        CharBuffer cbuf2 = decoder.decode(bbuf);
        System.out.println(cbuf2.toString());
        System.out.println("--------------------");
        Charset cs2 = Charset.forName("UTF-8");
        bbuf.flip();
        CharBuffer cbuf3 = cs2.decode(bbuf);
        System.out.println(cbuf3.toString());
    }
}
