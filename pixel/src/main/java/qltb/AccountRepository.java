package qltb;

import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<User, Integer> {

}
