package com.sid.cinema.entities;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.rest.core.config.Projection;

// on utilise les projections pour recuperer un ensemble de données spécifique qu'on a besoin pour les traiter ou les afficher en
// envoyant une seule requête au lieu de plusieurs

@Projection(name="p1",types={com.sid.cinema.entities.Projection.class})
public interface ProjectionProj {
	public Long getId();
	public double getPrix();
	public Date getDateProjection();
	public Salle getSalle();
	public Film getFilm();
	public Seance getSeance();
	public Collection<Ticket> getTickets();
}
