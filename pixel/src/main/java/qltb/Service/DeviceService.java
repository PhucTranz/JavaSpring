package qltb.Service;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import qltb.Model.Device;
import qltb.Repository.DeviceRepository;

@Service
@Transactional
public class DeviceService {

	@Autowired
	private DeviceRepository repo;
	
	public List<Device> listAll() {
		return repo.findAll();
	}
	
	public void save(Device e) {
		repo.save(e);
	}
	
	public Device get(int id) {
		return repo.findById(id).get();
	}
	
	public void delete(int id) {
		repo.deleteById(id);
	}
}
