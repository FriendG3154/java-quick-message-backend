package gin.filter;

import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

@Component
@Order(-2) // 必须高于默认的 DefaultErrorWebExceptionHandler(-1)
public class GlobalErrorWebExceptionHandler implements ErrorWebExceptionHandler {
    @Override
    public Mono<Void> handle(org.springframework.web.server.ServerWebExchange exchange, Throwable ex) {
        // 自定义异常处理逻辑
        var response = exchange.getResponse();
        if (response.isCommitted()) {
            return Mono.error(ex);
        }
        HttpStatus status = response.getStatusCode() != null && (response.getStatusCode().isError())
                ? HttpStatus.INTERNAL_SERVER_ERROR : HttpStatus.NOT_FOUND;
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        String json = "{\"code\":" + status.value() + ",\"message\":\"接口不存在或服务异常\",\"data\":\"\"}";
        DataBufferFactory bufferFactory = response.bufferFactory();
        return response.writeWith(Mono.just(bufferFactory.wrap(json.getBytes(StandardCharsets.UTF_8))));


    }

}

