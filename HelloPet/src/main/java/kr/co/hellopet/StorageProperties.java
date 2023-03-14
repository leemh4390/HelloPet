package kr.co.hellopet;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import lombok.Data;

@Data
@Component
@ConfigurationProperties("storage")
public class StorageProperties {
	// 로컬이미지 출력하기 위한 방법
	String location;
    String webLocation;
}
