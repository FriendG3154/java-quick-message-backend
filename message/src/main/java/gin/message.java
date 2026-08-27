package gin;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

@SpringBootApplication
@EnableDiscoveryClient //开启nacos的注解
@EnableFeignClients
@MapperScan("gin.mapper")
public class message {
    public static void main(String[] args){
        SpringApplication.run(message.class, args);
    }
}
