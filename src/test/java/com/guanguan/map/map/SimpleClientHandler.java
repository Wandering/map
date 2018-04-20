package com.guanguan.map.map;

import org.apache.mina.core.buffer.IoBuffer;
import org.apache.mina.core.service.IoHandlerAdapter;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.core.session.IoSession;
import org.slf4j.LoggerFactory;

import java.nio.charset.CharacterCodingException;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.CharsetEncoder;
import java.util.logging.Logger;

/**
 * @program: map
 * @description: 一句话说明该类
 * @author: 杨永平
 * @create: 2018年01月23日 17:19
 **/
public class SimpleClientHandler extends IoHandlerAdapter {
    private  static final CharsetEncoder charsetEncoder= Charset.forName("UTF-8").newEncoder();
    private static final CharsetDecoder charsetDecoder= Charset.forName("UTF-8").newDecoder();
    @Override
    public void sessionCreated(IoSession session) throws Exception {
        System.out.println("mina连接已经创建");
    }

    @Override
    public void sessionOpened(IoSession session) throws Exception {
        System.out.println("客户端会话打开");
    }

    @Override
    public void sessionClosed(IoSession session) throws Exception {

    }

    @Override
    public void sessionIdle(IoSession session, IdleStatus status) throws Exception {
        System.out.println("客户端会话休眠");
    }

    @Override
    public void exceptionCaught(IoSession session, Throwable cause) throws Exception {
        System.out.println("客户端异常");
    }

    @Override
    public void messageReceived(IoSession session, Object message) throws Exception {
        IoBuffer bbuf = (IoBuffer) message;
        byte[] byten = new byte[bbuf.limit()];
        bbuf.get(byten, bbuf.position(), bbuf.limit());
        System.out.println("客户端收到消息" + new String(byten,"UTF-8"));
    }

    @Override
    public void messageSent(IoSession session, Object message) throws Exception {
        System.out.println("客户端消息发送");
    }

    @Override
    public void inputClosed(IoSession session) throws Exception {
        System.out.println("客户端会话关闭");

    }
}