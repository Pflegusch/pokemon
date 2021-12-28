package com.server.rest;

import java.util.Map;
import java.util.Optional;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

import com.pokemon.User;

@RestController
@RequestMapping("/rest")
public class UserRest {
	static private EntityManager em;
    static private EntityTransaction tx;

	public UserRest() {}

	public UserRest(EntityManager entityManager, EntityTransaction entityTransaction) {
		em = entityManager;
        tx = entityTransaction;
	}

	@GetMapping("/signup")
	public ResponseEntity<String> signup(@RequestParam Map<String,String> params) {
        String username = params.get("username");
        String email = params.get("email");
        char[] password = params.get("password").toCharArray();
        Optional<String> byte_password = Passwords.hashPassword(password, Passwords.salt);

        String sql = String.format("INSERT INTO Users VALUES ('%s', '%s', '%s')", username, email, byte_password.get());

        try {
            em.getTransaction();
            tx.begin();
            em.createNativeQuery(sql).executeUpdate();
            tx.commit(); 
        } catch (javax.persistence.PersistenceException e) {
            tx.rollback();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.toString());
        }          
		return ResponseEntity.status(HttpStatus.OK).body("200");
	}

    @GetMapping("/login")
    @ResponseBody
    public ResponseEntity<String> login(@RequestParam Map<String,String> params) {
        String username = params.get("username");
        char[] password = params.get("password").toCharArray();

        Optional<String> byte_password = Passwords.hashPassword(password, Passwords.salt);

        User user = em.find(User.class, username);
        if (user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        if (user.password.equals(byte_password.get())) {
            return ResponseEntity.status(HttpStatus.OK).body("200");
        }
        throw new ResponseStatusException(HttpStatus.FORBIDDEN);
    }
}
