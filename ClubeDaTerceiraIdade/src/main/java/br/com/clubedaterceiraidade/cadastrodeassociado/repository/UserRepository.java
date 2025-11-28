package br.com.clubedaterceiraidade.cadastrodeassociado.repository;

import br.com.clubedaterceiraidade.cadastrodeassociado.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);
}