package com.server.rest;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import com.pokemon.Pokemon;
import com.pokemon.User;
import com.pokemon.Attack;

@RestController
@RequestMapping("/rest")
public class HelloRest {

	private static final String template = "Hello, %s!";
	static private EntityManager em;

	public HelloRest() {}

	public HelloRest(EntityManager entityManager) {
		em = entityManager;
	}

	@GetMapping("/greeting")
	public String greeting(@RequestParam(value = "name", defaultValue = "World") String name) {
		return (String.format(template, name);
	}

	@GetMapping("/pokemon")
	public String pokemons(@RequestParam(value = "pokemon") int id) {
		Pokemon pokemon = em.find(Pokemon.class, id);
		if (pokemon == null) {
			return String.format("Pokemon with id %s not found", id);
		}
		return pokemon.name;
	}

	@GetMapping("/attacks")
	public String attacks(@RequestParam(value = "pokemon") int id) {
		Pokemon pokemon = em.find(Pokemon.class, id);
		if (pokemon == null) {
			return String.format("Pokemon with id %s not found", id);
		}
		String[] result = new String[4];
		for (int i = 0; i < result.length; i++) {
			result[i] = pokemon.attacks.get(i).name;
		}
		return String.join(",", result);
	}

	@GetMapping("/pokemons")
	public List<?> user_pokemons(@RequestParam(value = "user_id") String user_id) {
		User user = em.find(User.class, user_id);
		String sql = String.format("SELECT DISTINCT Pokemons.name from Pokemons, Users where owner = '%s'", user.username);
		
		Query query = em.createNativeQuery(sql);
		List<?> pokemons = query.getResultList();

		return pokemons;
	}

	@GetMapping("/attack")
	public String get_attack(@RequestParam(value = "attack") String name) {
		Attack attack = em.find(Attack.class, name);
		if (attack == null) {
			return String.format("Attack with name %s not found", name);
		}
		String[] result = new String[6];
		result[0] = attack.name;		
		result[1] = attack.type.toString();		
		result[2] = Integer.toString(attack.damage);		
		result[3] = Integer.toString(attack.max_ap);		
		result[4] = Integer.toString(attack.accuracy);
		result[5] = String.valueOf(attack.ko);

		return String.join(",", result);
	}
}
