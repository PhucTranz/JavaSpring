package qltb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import qltb.Model.User;

public interface AccountRepository extends JpaRepository<User, Integer> {

}
