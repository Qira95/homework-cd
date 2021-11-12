package home.work.homework.service;

import home.work.homework.api.DeviceDto;
import home.work.homework.api.UpdateDeviceDto;
import home.work.homework.domain.Device;
import home.work.homework.domain.DeviceRepository;
import home.work.homework.security.PermissionKey;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
public class DeviceService {

    private final DeviceRepository deviceRepository;

    public DeviceService(DeviceRepository deviceRepository) {
        this.deviceRepository = deviceRepository;
    }

    /**
     * Save new device
     *
     * @param dto Data of the new device
     * @param userId Id of the user performing operation
     * @throws ConflictException if requested device is already present in db for user
     */
    @Transactional
    public Device saveDevice(DeviceDto dto, String userId) {
        Device newDevice = dto.toDevice(userId);
        String deviceId = newDevice.getDeviceId();
        if (deviceRepository.existsByDeviceIdAndUserId(deviceId, userId)) {
            throw new ConflictException(
                    String.format("Requested device with device ID: %s for user: %s already exists.", deviceId, userId)
            );
        }

        return deviceRepository.save(newDevice);
    }

    /**
     * Return single device detail
     * @param deviceId Id of the device
     * @param userId Id of the user requesting detail
     * @return Found device
     * @throws NotFoundException if requested device is not found
     */
    @Transactional(readOnly = true)
    public Device getDevice(String deviceId, String userId) {
        return deviceRepository.findByDeviceIdAndUserId(deviceId, userId)
                .orElseThrow(() -> new NotFoundException(
                        String.format("Requested device with device ID: %s for user: %s does not exist.", deviceId, userId))
                );
    }

    /**
     * Return all devices with said user Id
     * @param userId Id of the user performing request
     * @return List of devices belonging to user
     */
    @Transactional(readOnly = true)
    public List<Device> getUserDevices(String userId) {
        return deviceRepository.findAllByUserId(userId);
    }

    /**
     * Update device
     * @param deviceId Id of the device
     * @param userId Id of the user performing request
     * @param dto Dto with data to be updated in found entity
     * @return Updated entity
     */
    @Transactional
    public Device updateDevice(String deviceId, String userId, UpdateDeviceDto dto) {
        Device dbDevice = getDevice(deviceId, userId);

        dbDevice.updateFromDto(dto);

        return deviceRepository.save(dbDevice);
    }

    /**
     * Try to delete device
     * @param deviceId Id of the device
     * @param userId Id of the user performing request
     * @throws NotFoundException if requested device is not found
     */
    @Transactional
    public void deleteDevice(String deviceId, String userId) {
        if (!deviceRepository.existsByDeviceIdAndUserId(deviceId, userId)) {
            throw new NotFoundException(
                    String.format("Requested device with device ID: %s for user: %s does not exist.", deviceId, userId)
            );
        }

        deviceRepository.deleteByDeviceIdAndUserId(deviceId, userId);
    }

    /**
     * Fetch all devices from database
     *
     * @return List of all devices
     */
    @PreAuthorize(PermissionKey.BACKOFFICE)
    @Transactional(readOnly = true)
    public List<Device> getAllDevices() {
        return deviceRepository.findAll();
    }
}
