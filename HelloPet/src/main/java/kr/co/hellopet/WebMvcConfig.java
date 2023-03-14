package kr.co.hellopet;

import java.nio.file.Paths;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import lombok.RequiredArgsConstructor;

@Configuration
@RequiredArgsConstructor
public class WebMvcConfig implements WebMvcConfigurer {
	// 로컬이미지 출력하기 위한 방법
	
	private final StorageProperties storage;
	
	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		// TODO Auto-generated method stub
		registry.addResourceHandler(storage.getWebLocation() + "/**" )
        	.addResourceLocations("file:" + Paths.get("file").toAbsolutePath() + "/");
		WebMvcConfigurer.super.addResourceHandlers(registry);
	}
}
