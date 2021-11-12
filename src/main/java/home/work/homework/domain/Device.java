package home.work.homework.domain;

import home.work.homework.api.UpdateDeviceDto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.Instant;
import java.util.UUID;

@Entity
@Table(name = "t_device")
public class Device {

    @Id
    @Column(name = "id", unique = true)
    private UUID id;

    @Column(name = "operating_system", nullable = false)
    @Enumerated(EnumType.STRING)
    private OperatingSystem operatingSystem;

    @Column(name = "user_id", nullable = false)
    private String userId;

    @Column(name = "device_id", nullable = false)
    private String deviceId;

    @Column(name = "created_on", nullable = false)
    private Instant createdOn;

    @Column(name = "updated_on")
    private Instant updatedOn;

    protected Device() {
    }

    private Device(OperatingSystem operatingSystem, String userId, String deviceId) {
        this.id = UUID.randomUUID();
        this.operatingSystem = operatingSystem;
        this.userId = userId;
        this.deviceId = deviceId;
        this.createdOn = Instant.now();
    }

    public static DeviceBuilder newDevice() {
        return new DeviceBuilder();
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(OperatingSystem operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public Instant getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Instant createdOn) {
        this.createdOn = createdOn;
    }

    public Instant getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(Instant updatedOn) {
        this.updatedOn = updatedOn;
    }

    public void updateFromDto(UpdateDeviceDto dto) {
        this.operatingSystem = OperatingSystem.valueOf(dto.getOperatingSystem());
        this.updatedOn = Instant.now();
    }

    public static final class DeviceBuilder {
        private OperatingSystem operatingSystem;
        private String userId;
        private String deviceId;

        private DeviceBuilder() {
        }

        public static DeviceBuilder aDevice() {
            return new DeviceBuilder();
        }

        public DeviceBuilder operatingSystem(OperatingSystem operatingSystem) {
            this.operatingSystem = operatingSystem;
            return this;
        }

        public DeviceBuilder userId(String userId) {
            this.userId = userId;
            return this;
        }

        public DeviceBuilder deviceId(String deviceId) {
            this.deviceId = deviceId;
            return this;
        }

        public Device build() {
            return new Device(operatingSystem, userId, deviceId);
        }
    }
}
