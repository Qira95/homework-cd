package home.work.homework.api;

import com.fasterxml.jackson.annotation.JsonCreator;
import home.work.homework.api.validation.ValidEnum;
import home.work.homework.domain.OperatingSystem;

public class UpdateDeviceDto {

    @ValidEnum(targetedEnum = OperatingSystem.class)
    private String operatingSystem;

    @JsonCreator
    public UpdateDeviceDto(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public void setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
    }
}
