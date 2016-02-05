package com.gft.jms;

import com.gft.jms.sender.SenderThread;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
@EnableAsync
public class JmsFastTrackConsumerApplication implements CommandLineRunner {

    @Autowired
    SenderThread messageSender;

	public static void main(String[] args) {
		SpringApplication.run(JmsFastTrackConsumerApplication.class, args);
	}

	@Override
	public void run(String... strings) throws Exception {
        messageSender.run();
	}
}
