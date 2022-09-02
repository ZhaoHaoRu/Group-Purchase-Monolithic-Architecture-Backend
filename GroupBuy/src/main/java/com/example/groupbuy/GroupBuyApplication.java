package com.example.groupbuy;

import lombok.extern.slf4j.Slf4j;
import org.apache.catalina.connector.Connector;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ServletWebServerFactory;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.ConfigurableEnvironment;

import java.net.InetAddress;
import java.net.UnknownHostException;

@SpringBootApplication
@Slf4j
public class GroupBuyApplication {

    public static void main(String[] args) throws UnknownHostException {
        ConfigurableApplicationContext run =SpringApplication.run(GroupBuyApplication.class, args);
        ServerProperties bean = run.getBean(ServerProperties.class);
        ConfigurableEnvironment environment = run.getEnvironment();
        log.info("\n{----------------------------------------------------------}\n\t" +
                        "启动成功！点击进入swagger接口管理页面:\thttp://{}:{}" +
                        "\n{----------------------------------------------------------}",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port") + bean.getServlet().getContextPath() + "/doc.html");

        log.info("\n{----------------------------------------------------------}\n\t" +
                        "启动成功！点击进入swagger接口管理页面:\thttp://{}:{}" +
                        "\n{----------------------------------------------------------}",
                InetAddress.getLocalHost().getHostAddress(),
                environment.getProperty("server.port") + bean.getServlet().getContextPath() + "/swagger-ui.html");
    }

    // 同时支持对于http的8080端口进行访问
    @Bean
    public ServletWebServerFactory servletContainer() {
        TomcatServletWebServerFactory tomcat = new TomcatServletWebServerFactory();
        Connector connector = new Connector("org.apache.coyote.http11.Http11NioProtocol");
        connector.setPort(8080);
        tomcat.addAdditionalTomcatConnectors(connector);
        return tomcat;
    }


}
