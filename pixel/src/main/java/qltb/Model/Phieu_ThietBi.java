package qltb.Model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name="phieu_thietbi")
public class Phieu_ThietBi {
	@EmbeddedId
	private P_TB_id id;
	private int soluong;
}
