package kr.co.hellopet;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@SpringBootApplication
public class HelloPetApplication {

	public static void main(String[] args) {
		SpringApplication.run(HelloPetApplication.class, args);
	}
	
}
