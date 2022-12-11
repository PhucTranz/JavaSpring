package qltb.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import qltb.Model.P_TB_id;
import qltb.Model.Phieu;
import qltb.Model.Phieu_ThietBi;
import qltb.Repository.PhieuRepository;
import qltb.Repository.Phieu_ThietBiRepository;

@Service
@Transactional
public class Phieu_ThietBiService {

	@Autowired
	private Phieu_ThietBiRepository repo;
	
	public List<Phieu_ThietBi> listAll() {
		return repo.findAll();
	}
	
	public void save(Phieu_ThietBi e) {
		repo.save(e);
	}
	
	public Phieu_ThietBi get(P_TB_id id) {
		return repo.findById(id).get();
	}
	
	public void delete(P_TB_id id) {
		repo.deleteById(id);
	}
	
	public List<Phieu_ThietBi> getByMaPhieu(int maPhieu){
		return repo.findByMaPhieu(maPhieu);
	}
}
