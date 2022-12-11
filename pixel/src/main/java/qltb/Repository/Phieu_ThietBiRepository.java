package qltb.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import qltb.Model.P_TB_id;
import qltb.Model.Phieu_ThietBi;


public interface Phieu_ThietBiRepository extends JpaRepository<Phieu_ThietBi, P_TB_id> {
	@Query(value = "SELECT * FROM phieu_thietbi i where i.ma_phieu = :ma_phieu", nativeQuery = true)
	List<Phieu_ThietBi> findByMaPhieu(@Param("ma_phieu") int maPhieu);
}
