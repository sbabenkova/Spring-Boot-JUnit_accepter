package ru.inex.accepter.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;
import ru.inex.accepter.entity.TestType;
import ru.inex.accepter.service.TestListBuilderAshotClient;
import ru.inex.accepter.service.TestListBuilderClient;
import ru.inex.accepter.service.TestListBuilderCypressClient;
import ru.inex.accepter.service.TestListBuilderKarateClient;

import java.util.function.Function;

@Configuration
public class AppConfig {
    /**
     * Используется в реализациях TestListBuilderClient для обращения в соответствующее приложение
     */

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    /**
     * "Фабрика" для получения соответствующего клиента в сервисе
     * @return конкретный клиент
     */
    @Bean
    public Function<TestType, TestListBuilderClient> testListBuilderClientSupplier(TestListBuilderCypressClient cypressClient,
                                                                                   TestListBuilderKarateClient karateClient,
                                                                                   TestListBuilderAshotClient ashotClient) {
        return testType -> {
            switch (testType) {
                case cypress:
                    return cypressClient;
                case karate:
                    return karateClient;
                case ashot:
                    return ashotClient;
                default:
                    throw new IllegalArgumentException("TestType " + testType + " is unknown");
            }
        };
    }
}
