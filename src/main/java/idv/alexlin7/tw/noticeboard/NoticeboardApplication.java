package idv.alexlin7.tw.noticeboard;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@PropertySource("classpath:storage.properties")
@EnableScheduling
public class NoticeboardApplication {

	public static void main(String[] args) {
		SpringApplication.run(NoticeboardApplication.class, args);
	}

}
