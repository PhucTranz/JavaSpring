package qltb.Model;


import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Phieu {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int maPhieu;
	private int id;
	private String giomuon;
	private String giotra;
	private String trangthai;
}
