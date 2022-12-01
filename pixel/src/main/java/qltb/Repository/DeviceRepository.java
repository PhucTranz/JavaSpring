package qltb.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import qltb.Model.Device;

public interface DeviceRepository extends JpaRepository<Device, Integer> {

}
