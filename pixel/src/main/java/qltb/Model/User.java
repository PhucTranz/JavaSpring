package qltb.Model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

@Data
@Entity
@Table(name = "users")
public class User {

   @Id
   @Column(name = "id")
   @GeneratedValue(strategy = GenerationType.IDENTITY)
   private int id;

   private String username;
   @Column(name = "PASSWORD")
   private String password;
   private String name;
   private String chucvu;
   private String role;
   @Column(name = "ENABLE")
   private boolean enabled;

   // getters and setters are not shown for brevity

}
