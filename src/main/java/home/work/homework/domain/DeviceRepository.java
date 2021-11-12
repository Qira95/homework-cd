package home.work.homework.domain;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface DeviceRepository extends JpaRepository<Device, UUID> {
    Optional<Device> findByDeviceIdAndUserId(String deviceId, String userId);

    List<Device> findAllByUserId(String userId);

    void deleteByDeviceIdAndUserId(String deviceId, String userId);

    boolean existsByDeviceIdAndUserId(String deviceId, String userId);
}
