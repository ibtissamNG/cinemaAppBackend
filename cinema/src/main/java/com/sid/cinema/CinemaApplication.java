package com.sid.cinema;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.rest.core.config.RepositoryRestConfiguration;

import com.sid.cinema.entities.Film;
import com.sid.cinema.entities.Salle;
import com.sid.cinema.entities.Ticket;
import com.sid.cinema.service.ICinemaInitService;

@SpringBootApplication
public class CinemaApplication implements CommandLineRunner {
	
	@Autowired
	private ICinemaInitService cinemaInitService;
	
	@Autowired
	private RepositoryRestConfiguration restConfiguration;

	public static void main(String[] args) {
		SpringApplication.run(CinemaApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		restConfiguration.exposeIdsFor(Film.class, Salle.class,Ticket.class);
		
		  // spring.jpa.hibernate.ddl-auto = create, run this for once and turn it to update
		  cinemaInitService.initVilles(); cinemaInitService.initCinemas();
		  cinemaInitService.initSalles(); cinemaInitService.initPlaces();
		  cinemaInitService.initSeances(); cinemaInitService.initCategories();
		  cinemaInitService.initfilms(); cinemaInitService.initProjections();
		  cinemaInitService.initTickets();
		 
		 
	}

}
