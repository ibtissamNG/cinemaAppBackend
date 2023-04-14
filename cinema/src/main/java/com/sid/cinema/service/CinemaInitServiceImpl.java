package com.sid.cinema.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.stream.Stream;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.sid.cinema.dao.CategorieRepository;
import com.sid.cinema.dao.CinemaRepository;
import com.sid.cinema.dao.FilmRepository;
import com.sid.cinema.dao.PlaceRepository;
import com.sid.cinema.dao.ProjectionRepository;
import com.sid.cinema.dao.SalleRepository;
import com.sid.cinema.dao.SeanceRepository;
import com.sid.cinema.dao.TicketRepository;
import com.sid.cinema.dao.VilleRepository;
import com.sid.cinema.entities.Categorie;
import com.sid.cinema.entities.Cinema;
import com.sid.cinema.entities.Film;
import com.sid.cinema.entities.Place;
import com.sid.cinema.entities.Projection;
import com.sid.cinema.entities.Salle;
import com.sid.cinema.entities.Seance;
import com.sid.cinema.entities.Ticket;
import com.sid.cinema.entities.Ville;

import jakarta.transaction.Transactional;

@Service
@Transactional
public class CinemaInitServiceImpl implements ICinemaInitService {

	@Autowired
	private VilleRepository villeRepository;
	@Autowired
	private CinemaRepository cinemaRepository;
	@Autowired
	private SalleRepository salleRepository;
	@Autowired
	private PlaceRepository placeRepository;
	@Autowired
	private SeanceRepository seanceRepository;
	@Autowired
	private FilmRepository filmRepository;
	@Autowired
	private ProjectionRepository projectionRepository;
	@Autowired
	private CategorieRepository categorieRepository;
	@Autowired
	private TicketRepository ticketRepository;

	
	@Override
	public void initVilles() {
		Stream.of("Rabat","Fes","Casablanca","Oujda","Tanger").forEach(nameVille -> {
			Ville v = new Ville();
			v.setName(nameVille);
			villeRepository.save(v);
		});
		
	}

	@Override
	public void initCinemas() {
		villeRepository.findAll().forEach(v -> {
			Stream.of("Cinéma 01","Cinéma 02","Cinéma 03","Cinéma 04","Cinéma 05")
			.forEach(nameCinema -> {
				Cinema c =new Cinema();
				c.setName(nameCinema);
				c.setNombreSalles(3+(int)(Math.random()*10));
				c.setVille(v);
				cinemaRepository.save(c);
			});
			
		}
				);
	}

	@Override
	public void initSalles() {
		cinemaRepository.findAll().forEach(c -> {
			for(int i=1;i<c.getNombreSalles();i++) {
				Salle salle = new Salle();
				salle.setName("Salle "+i);
				salle.setCinema(c);
				salle.setNombrePlace(10+(int)(Math.random()*20));
				salleRepository.save(salle);
			};
		});
		
	}

	@Override
	public void initPlaces() {
		salleRepository.findAll().forEach(salle -> {
			for(int i=0;i<salle.getNombrePlace();i++) {
				Place place =new Place();
				place.setNumero(i+1);
				place.setSalle(salle);
				placeRepository.save(place);
			}
		});
		
	}

	@Override
	public void initSeances() {
		DateFormat dateFormat = new SimpleDateFormat("HH:mm");
		Stream.of("12:00","15:00","17:00","20:00").forEach(s-> {
			Seance seance = new Seance();
			try {
				seance.setHeureDebut(dateFormat.parse(s));
				seanceRepository.save(seance);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
				);
		
	}

	@Override
	public void initCategories() {
		Stream.of("Action","Science fiction","Drame","Horreur","Comédie","Histoire","Policier").forEach(c->{
			Categorie categorie = new Categorie();
			categorie.setName(c);
			categorieRepository.save(categorie);
		});
		
	}

	@Override
	public void initfilms() {  
		List<Categorie> categories = categorieRepository.findAll();
		Stream.of("Avengers Endgame","Les Minions","Star Wars : Le Réveil de la Force","SpiderMan","Emily in Paris","Fast&Furious","Shadow Hunters","Cukur","Avatar : La Voie de l'eau","HarryPotter et les Reliques de la Mort : Partie 2").forEach(filmTitre->{
			Film film = new Film();
			film.setTitre(filmTitre);
			film.setDuree(1+Math.random()*3);
			film.setPhoto(filmTitre.replaceAll("[\\s:']","")+".jpg");
			film.setCategorie(categories.get(new Random().nextInt(categories.size())));
			filmRepository.save(film);	
		});
		
		
	}

	@Override
	public void initProjections() {
		double[] prices = new double[] {35,45,50,65,90,100};
		List<Film> films = filmRepository.findAll();
		villeRepository.findAll().forEach(ville->{
			ville.getCinemas().forEach(cinema->{
				cinema.getSalles().forEach(salle->{
				int index = new Random().nextInt(films.size());
				Film film = films.get(index);
				seanceRepository.findAll().forEach(seance->{
					Projection proj = new Projection();
					proj.setDateProjection(new Date());
					proj.setFilm(film);
					proj.setPrix(prices[new Random().nextInt(prices.length)]);
					proj.setSalle(salle);
					proj.setSeance(seance);
					projectionRepository.save(proj);
				});
				
				});
			});
		});
		
	}

	@Override
	public void initTickets() {
		
		  projectionRepository.findAll().forEach(p->{
		  p.getSalle().getPlaces().forEach(place->{ Ticket ticket = new Ticket();
		  ticket.setPlace(place); ticket.setPrix(p.getPrix()); ticket.setProjection(p);
		  ticket.setReserve(false); ticketRepository.save(ticket);
		  
		  }); });
		 
		
	}

}
