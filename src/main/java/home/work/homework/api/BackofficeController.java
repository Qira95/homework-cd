package home.work.homework.api;

import home.work.homework.service.DeviceService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/backoffice")
public class BackofficeController {

    private final DeviceService deviceService;

    public BackofficeController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping
    public List<DeviceDto> getDeviceDetails() {
        return deviceService.getAllDevices().stream()
                .map(DeviceDto::new)
                .collect(Collectors.toList());
    }
}
