package nl.wehkamp.everest;

import javax.servlet.Servlet;

import nl.wehkamp.everest.web.MockingServlet;

import org.springframework.beans.BeansException;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = { "nl.wehkamp.everest.web", "nl.wehkamp.everest.dao", "nl.wehkamp.everest.service" })
public class WebServer implements ApplicationContextAware {
	public static WebServer instance;

	public WebServer() {
		instance = this;
	}

	private ApplicationContext applicationContext;

	public <T> T getBean(Class<T> claz) {
		return applicationContext.getBean(claz);
	}

	@Bean
	public ServletRegistrationBean servletRegistrationBean() {
		return new ServletRegistrationBean(mockingServlet(), "/*");
	}

	@Bean
	public Servlet mockingServlet() {
		return new MockingServlet();
	}

	public static void main(String[] args) {
		WebServer.start();
	}

	public static void start() {
		SpringApplication.run(WebServer.class);
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		this.applicationContext = applicationContext;
	}
}
