package com.guanguan.map.map.config;

import com.guanguan.map.map.controller.MapServerHandler;
import org.apache.mina.core.service.IoAcceptor;
import org.apache.mina.core.service.IoHandler;
import org.apache.mina.core.session.IdleStatus;
import org.apache.mina.filter.logging.LoggingFilter;
import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.net.InetSocketAddress;

/**
 * @program: map
 * @description: 一句话说明该类
 * @author: 杨永平
 * @create: 2018年01月23日 17:05
 **/
@Configuration
public class MinaConfig {
    @Autowired
    MapServerHandler handler;
    @Bean
    public LoggingFilter loggingFilter() {
        return new LoggingFilter();
    }

    @Bean
    public InetSocketAddress inetSocketAddress() {
        return new InetSocketAddress(9123);
    }

    @Bean
    public IoAcceptor ioAcceptor() throws Exception {
        IoAcceptor acceptor=new NioSocketAcceptor();
        acceptor.getFilterChain().addLast( "logger", loggingFilter() );
        //acceptor.getFilterChain().addLast( "codec", new ProtocolCodecFilter( new TextLineCodecFactory(Charset.forName("UTF-8"))));因为传byte[]所以去掉
        acceptor.setHandler(handler);


        acceptor.getSessionConfig().setReadBufferSize( 2048 );
        acceptor.getSessionConfig().setIdleTime( IdleStatus.BOTH_IDLE, 10 );


        acceptor.bind(inetSocketAddress());
        System.out.println("服务器在端口："+"已经启动");
        return acceptor;
    }

}
