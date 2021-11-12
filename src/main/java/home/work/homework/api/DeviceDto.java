package home.work.homework.api;

import home.work.homework.api.validation.ValidEnum;
import home.work.homework.domain.Device;
import home.work.homework.domain.OperatingSystem;

public class DeviceDto {

    @ValidEnum(targetedEnum = OperatingSystem.class)
    private String operatingSystem;
    private String deviceId;
    private String createdOn;
    private String updatedOn;

    public DeviceDto(String operatingSystem, String deviceId) {
        this.operatingSystem = operatingSystem;
        this.deviceId = deviceId;
    }

    public DeviceDto(Device device) {
        this.operatingSystem = device.getOperatingSystem().toString();
        this.deviceId = device.getDeviceId();
        this.createdOn = device.getCreatedOn().toString();
        this.updatedOn = device.getUpdatedOn() != null ? device.getUpdatedOn().toString() : null;
    }

    public Device toDevice(String userId) {
        return Device.newDevice()
                .operatingSystem(OperatingSystem.valueOf(operatingSystem))
                .deviceId(deviceId)
                .userId(userId)
                .build();
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }

    public String getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(String createdOn) {
        this.createdOn = createdOn;
    }

    public String getUpdatedOn() {
        return updatedOn;
    }

    public void setUpdatedOn(String updatedOn) {
        this.updatedOn = updatedOn;
    }
}
