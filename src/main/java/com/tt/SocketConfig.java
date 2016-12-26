//package com.tt;
//
//import com.tt.device.DataHandler;
//import com.tt.device.DataParser;
//import com.tt.device.ValidateRule;
//import com.tt.device.Validator;
//import com.tt.device.handler.SocketDataHandler;
//import com.tt.device.impl.*;
//import org.apache.mina.core.service.IoAcceptor;
//import org.apache.mina.core.service.IoHandler;
//import org.apache.mina.core.session.IdleStatus;
//import org.apache.mina.filter.codec.ProtocolCodecFactory;
//import org.apache.mina.filter.codec.ProtocolCodecFilter;
//import org.apache.mina.filter.codec.textline.TextLineCodecFactory;
//import org.apache.mina.filter.logging.LoggingFilter;
//import org.apache.mina.transport.socket.nio.NioSocketAcceptor;
//import org.springframework.beans.factory.annotation.Qualifier;
//import org.springframework.beans.factory.annotation.Value;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//
//import java.io.IOException;
//import java.net.InetSocketAddress;
//import java.nio.charset.Charset;
//import java.util.ArrayList;
//import java.util.List;
//
///**
// * Created by tt on 2016/11/25.
// */
//@Configuration
//public class SocketConfig {
//    @Bean(destroyMethod = "unbind")
//    public IoAcceptor ioAcceptor(LoggingFilter loggingFilter,
//                                 ProtocolCodecFilter protocolCodecFilter,
//                                 IoHandler socketDataHandler,
//                                 @Value("${socket.port}") int port) throws IOException {
//        IoAcceptor acceptor = new NioSocketAcceptor();
//        acceptor.getFilterChain().addLast("logger", loggingFilter);
//        acceptor.getFilterChain().addLast("codec", protocolCodecFilter);
//        acceptor.setHandler(socketDataHandler);
//        acceptor.getSessionConfig().setReadBufferSize(2048);
//        acceptor.getSessionConfig().setIdleTime(IdleStatus.BOTH_IDLE, 10);
//        System.out.println("*********************************");
////        acceptor.setDefaultLocalAddress(new InetSocketAddress(PORT));
//        acceptor.bind(new InetSocketAddress(port));
//        System.out.println(String.format("socket listening on port %s", port));
//        return acceptor;
//    }
//
//    @Bean
//    public LoggingFilter loggingFilter() {
//        return new LoggingFilter();
//    }
//
//    @Bean
//    public ProtocolCodecFilter protocolCodecFilter() {
//        return new ProtocolCodecFilter(protocolCodecFactory());
//    }
//
//    @Bean
//    public ProtocolCodecFactory protocolCodecFactory() {
//        return new TextLineCodecFactory(Charset.forName("GBK"));
//    }
//
//    @Bean
//    public SocketDataHandler socketDataHandler() {
//        return new SocketDataHandler();
//    }
//
//    @Bean
//    @Qualifier("dataValidator")
//    public Validator dataValidator() {
//        DataValidator validator = new DataValidator();
//        List<ValidateRule> rules = new ArrayList<>();
//        rules.add(new PrgNeededRule());
//        rules.add(new StzhNeededRule());
//        rules.add(new DevNBNeededRule());
//        validator.setRules(rules);
//        return validator;
//    }
//
//    @Bean
//    public ValidateRule equipAvailRule(){
//        return new EquipAvailRule();
//    }
//    @Bean
//    @Qualifier("equipValidator")
//    public Validator equipValidator() {
//        EquipValidator validator = new EquipValidator();
//        validator.setRule(equipAvailRule());
//        return validator;
//    }
//
//    @Bean
//    public DataParser dataParser(){
//        return new JsonDataParser();
//    }
//
//    @Bean
//    public DataHandler dataHandler(){
//        return new DbDataHandler();
//    }
//
//}
