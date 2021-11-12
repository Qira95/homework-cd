package home.work.homework.api;

import home.work.homework.security.AuthoritySession;
import home.work.homework.service.DeviceService;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/device")
public class DeviceController {

    private final DeviceService deviceService;
    private final AuthoritySession authoritySession;

    public DeviceController(DeviceService deviceService, AuthoritySession authoritySession) {
        this.deviceService = deviceService;
        this.authoritySession = authoritySession;
    }

    @PostMapping
    public DeviceDto saveDevice(@RequestBody @Valid DeviceDto dto) {
        return new DeviceDto(deviceService.saveDevice(dto, authoritySession.getLoggedUser().getId()));
    }

    @GetMapping("/{deviceId}")
    public DeviceDto getDeviceDetails(@PathVariable("deviceId") String deviceId) {
        return new DeviceDto(deviceService.getDevice(deviceId, authoritySession.getLoggedUser().getId()));
    }

    @GetMapping
    public List<DeviceDto> getDeviceDetails() {
        return deviceService.getUserDevices(authoritySession.getLoggedUser().getId()).stream()
                .map(DeviceDto::new)
                .collect(Collectors.toList());
    }

    @PutMapping("/{deviceId}")
    public DeviceDto updateDevice(@PathVariable("deviceId") String deviceId, @RequestBody @Valid UpdateDeviceDto dto) {
        return new DeviceDto(deviceService.updateDevice(deviceId, authoritySession.getLoggedUser().getId(), dto));
    }

    @DeleteMapping("/{deviceId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteDevice(@PathVariable("deviceId") String deviceId) {
        deviceService.deleteDevice(deviceId, authoritySession.getLoggedUser().getId());
    }
}
