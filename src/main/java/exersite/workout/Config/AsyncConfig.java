package exersite.workout.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableAsync
public class AsyncConfig implements AsyncConfigurer {

    @Override
    public Executor getAsyncExecutor() { // 스레드 풀을 정의
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        int processors = Runtime.getRuntime().availableProcessors(); // 현재 시스템의 프로세스 개수
        executor.setCorePoolSize(processors); // pool 사이즈를 현재 프로세스 개수만큼 설정
        executor.setMaxPoolSize(processors * 2); // pool 사이즈의 전체 개수를 현재 프로세스의 두배만큼 설정
        executor.setQueueCapacity(50);
        executor.setKeepAliveSeconds(60); // 60초 만큼 스레드를 살려두기
        executor.setThreadNamePrefix("CommentNotificationAsyncExecutor - ");
        executor.initialize();

        return executor;
    }
}
