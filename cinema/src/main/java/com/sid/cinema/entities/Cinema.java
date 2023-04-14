package com.sid.cinema.entities;

import java.io.Serializable;
import java.util.Collection;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data @AllArgsConstructor @NoArgsConstructor
public class Cinema implements Serializable {
	
	private static final long serialVersionUID = 1L;
	@Id @GeneratedValue(strategy=GenerationType.IDENTITY)
	private Long id;
	@Column(length = 50)
	private String name;
	private double longitude, latitude, altitude;
	private int nombreSalles;
	// Une cinema peut contenir plusieurs salles
	@OneToMany(mappedBy="cinema")
	private Collection<Salle> salles;
	// plusieurs cinema peuvent apartenir a la meme ville
	@ManyToOne
	private Ville ville;
	
	
}
