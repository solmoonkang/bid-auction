package com.bid.auction;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class BidAuctionApplication {

	public static void main(String[] args) {
		SpringApplication.run(BidAuctionApplication.class, args);
	}
}
