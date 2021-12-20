package com.server.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;

import com.pokemon.Pokemon;
import com.pokemon.Trainer;

@RestController
public class HelloRest {

	private static final String template = "Hello, %s!";
	static private EntityManager em;

	public HelloRest() {}

	public HelloRest(EntityManager entityManager) {
		em = entityManager;
	}

	@GetMapping("/greeting")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return new String(String.format(template, name));
	}

	@GetMapping("/pokemon")
	public String pokemons(@RequestParam(value = "pokemon") int id) {
		Pokemon pokemon = em.find(Pokemon.class, id);
		if (pokemon == null) {
			return new String(String.format("Pokemon with id %s not found", id));
		}
		return pokemon.name;
	}

	@GetMapping("/trainer")
	public String trainers(@RequestParam(value = "trainer") int id) {
		Trainer trainer = em.find(Trainer.class, id);
		if (trainer == null) {
			return new String(String.format("Trainer with id %s not found", id));
		}
		return trainer.name;
	}

	@GetMapping("/attacks")
	public String attacks(@RequestParam(value = "pokemon") int id) {
		Pokemon pokemon = em.find(Pokemon.class, id);
		if (pokemon == null) {
			return new String(String.format("Pokemon with id %s not found", id));
		}
		String[] result = new String[4];
		for (int i = 0; i < result.length; i++) {
			result[i] = pokemon.attacks[i].name;
		}
		return String.join(",", result);
	}
}
