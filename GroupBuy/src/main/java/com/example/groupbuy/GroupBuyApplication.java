package com.example.groupbuy;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.ServerProperties;
import org.springframework.context.ConfigurableApplicationContext;
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

}
