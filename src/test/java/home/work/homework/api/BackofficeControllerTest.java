package home.work.homework.api;

import home.work.homework.domain.Device;
import home.work.homework.domain.OperatingSystem;
import home.work.homework.service.DeviceService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class BackofficeControllerTest extends BaseControllerTest {

    private DeviceService deviceService;

    @BeforeEach
    void setUp() {
        deviceService = mock(DeviceService.class);

        setUp(new BackofficeController(deviceService));
    }

    @Test
    void getAllRecordsFromDatabase() throws Exception {
        when(deviceService.getAllDevices())
                .thenReturn(
                        List.of(
                                Device.newDevice()
                                        .operatingSystem(OperatingSystem.ANDROID)
                                        .deviceId("5ae08662-4337-11ec-81d3-0242ac130003")
                                        .userId("42")
                                        .build(),
                                Device.newDevice()
                                        .operatingSystem(OperatingSystem.IOS)
                                        .deviceId("5bc7a10f-c5f8-4032-9a03-1f21635fb47f")
                                        .userId("24")
                                        .build(),
                                Device.newDevice()
                                        .operatingSystem(OperatingSystem.IOS)
                                        .deviceId("46f12b8f-bdc5-43b3-b872-8ee13b4acd4a")
                                        .userId("42")
                                        .build()
                        )
                );

        mockMvc.perform(get("/backoffice"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray());
    }

}