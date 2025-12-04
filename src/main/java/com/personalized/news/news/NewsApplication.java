package com.personalized.news.news;

import io.github.cdimascio.dotenv.Dotenv;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaAuditing
public class NewsApplication {

	public static void main(String[] args) {

		// 1) .env 파일 로딩
		Dotenv dotenv = Dotenv.configure()
				.ignoreIfMissing()
				.load();

		// 2) .env 값들을 시스템 프로퍼티로 등록
		dotenv.entries().forEach(entry -> {
			System.setProperty(entry.getKey(), entry.getValue());
		});

		// 3) Spring Boot 실행
		SpringApplication.run(NewsApplication.class, args);
	}

}
