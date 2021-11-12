package home.work.homework.service;

import home.work.homework.api.DeviceDto;
import home.work.homework.api.UpdateDeviceDto;
import home.work.homework.config.ITConfig;
import home.work.homework.config.RequestContextTestExecutionListener;
import home.work.homework.config.WithLoggedUser;
import home.work.homework.domain.Device;
import home.work.homework.domain.DeviceRepository;
import org.flywaydb.test.FlywayTestExecutionListener;
import org.flywaydb.test.annotation.FlywayTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@ExtendWith(SpringExtension.class)
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
@FlywayTest
@TestPropertySource(locations = "classpath:it-application.properties")
@DataJpaTest
@TestExecutionListeners(
        listeners = {
                FlywayTestExecutionListener.class,
                DependencyInjectionTestExecutionListener.class,
                RequestContextTestExecutionListener.class
        },
        mergeMode = TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS
)
@Import(ITConfig.class)
class DeviceServiceIT {

    @Autowired
    private DeviceService deviceService;

    @Autowired
    private DeviceRepository deviceRepository;

    @Test
    void saveDeviceWillSaveNewEntity() {
        String operatingSystem = "IOS";
        String deviceId = "1984357";
        String userId = "24";
        DeviceDto dto = new DeviceDto(operatingSystem, deviceId);

        deviceService.saveDevice(dto, userId);

        Device dbDevice = deviceRepository.findByDeviceIdAndUserId(deviceId, userId).orElse(null);
        assertNotNull(dbDevice);
        assertEquals(dbDevice.getOperatingSystem().toString(), operatingSystem);
        assertEquals(dbDevice.getDeviceId(), deviceId);
        assertEquals(dbDevice.getUserId(), userId);
    }

    @Test
    void saveDeviceWillSaveNewEntityThrowConflictException() {
        String operatingSystem = "IOS";
        String deviceId = "PRESENT";
        String userId = "42";
        DeviceDto dto = new DeviceDto(operatingSystem, deviceId);

        assertThrows(ConflictException.class, () -> deviceService.saveDevice(dto, userId));
    }

    @Test
    void getUserDevices() {
        List<Device> userDevices = deviceService.getUserDevices("84");

        assertEquals(userDevices.size(), 2);
    }

    @Test
    void updateDeviceWillUpdateEntity() {
        String operatingSystem = "ANDROID";
        String deviceId = "TO_BE_UPDATED";
        String userId = "42";
        UpdateDeviceDto dto = new UpdateDeviceDto(operatingSystem);

        deviceService.updateDevice(deviceId, userId, dto);

        Device dbDevice = deviceRepository.findByDeviceIdAndUserId(deviceId, userId).orElse(null);
        assertNotNull(dbDevice);
        assertEquals(dbDevice.getOperatingSystem().toString(), operatingSystem);
        assertNotNull(dbDevice.getUpdatedOn());
    }

    @Test
    @WithLoggedUser(role = "CLIENT")
    void loggedUserWithRoleClientCannotAccessGetAllDevicesMethod() {
        assertThrows(AccessDeniedException.class, () -> deviceService.getAllDevices());
    }
}