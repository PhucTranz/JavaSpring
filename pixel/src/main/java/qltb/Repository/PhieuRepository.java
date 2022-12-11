package qltb.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import qltb.Model.Phieu;


public interface PhieuRepository extends JpaRepository<Phieu, Integer> {
	@Query(value = "SELECT * FROM phieu i where i.id = :id", nativeQuery = true)
	List<Phieu> findByUserId(@Param("id") int id);
	
	//Lay cac phieu trang thai CHO DUYET hoac CHO TRA
	@Query(value = "SELECT * FROM phieu i where i.trangthai ='Chờ duyệt' or i.trangthai ='Chờ trả'", nativeQuery = true)
	List<Phieu> findNotFinish();
}
