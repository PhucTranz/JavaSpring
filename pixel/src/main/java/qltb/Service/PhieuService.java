package qltb.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import qltb.Model.Phieu;
import qltb.Repository.PhieuRepository;

@Service
@Transactional
public class PhieuService {

	@Autowired
	private PhieuRepository repo;
	
	public List<Phieu> listAll() {
		return repo.findAll();
	}
	
	public void save(Phieu e) {
		repo.save(e);
	}
	
	public Phieu get(int id) {
		return repo.findById(id).get();
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
	
	public List<Phieu> getByUserID(int id){
		return repo.findByUserId(id);
	}
	
	public List<Phieu> getNotFinish(){
		return repo.findNotFinish();
	}
}
