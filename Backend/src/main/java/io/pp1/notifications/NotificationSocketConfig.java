package io.pp1.notifications;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

@Configuration
public class NotificationSocketConfig {

	@Bean
	public ServerEndpointExporter serverEndpointExporter() {
		
		return new ServerEndpointExporter();
	}
	
	@Bean
	public CustomConfigurator customConfigurator() {
		return new CustomConfigurator();
	}
	
}
