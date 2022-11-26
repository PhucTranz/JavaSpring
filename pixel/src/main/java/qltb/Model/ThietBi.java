package qltb.Model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class ThietBi {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String maTB;
	private String ten;
	private int soluong;
}
