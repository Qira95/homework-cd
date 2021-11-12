package home.work.homework.api;

import home.work.homework.domain.Device;
import home.work.homework.domain.OperatingSystem;
import home.work.homework.security.AuthoritySession;
import home.work.homework.security.LoggedUser;
import home.work.homework.service.ConflictException;
import home.work.homework.service.DeviceService;
import home.work.homework.service.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.http.MediaType;

import static org.hamcrest.Matchers.containsString;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class DeviceControllerTest extends BaseControllerTest {

    private DeviceService deviceService;
    private AuthoritySession authoritySession;

    @BeforeEach
    void setUp() {
        deviceService = mock(DeviceService.class);
        authoritySession = mock(AuthoritySession.class);

        setUp(new DeviceController(deviceService, authoritySession));
    }

    @Test
    void postWithValidEnumIsAccepted() throws Exception {
        String deviceId = "5ae08662-4337-11ec-81d3-0242ac130003";
        when(authoritySession.getLoggedUser()).thenReturn(new LoggedUser("42", "CLIENT"));
        when(deviceService.saveDevice(any(DeviceDto.class), anyString()))
                .thenReturn(Device.newDevice()
                        .operatingSystem(OperatingSystem.ANDROID)
                        .deviceId(deviceId)
                        .userId("42")
                        .build()
                );
        mockMvc.perform(post("/device")
                        .content("""
                                    {
                                        "operatingSystem": "ANDROID",
                                        "deviceId": "5ae08662-4337-11ec-81d3-0242ac130003"
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId").value(deviceId))
                .andExpect(jsonPath("$.operatingSystem").value("ANDROID"));

        verify(deviceService, times(1)).saveDevice(any(DeviceDto.class), anyString());
    }

    @Test
    void postWithInvalidEnumIsRejected() throws Exception {
        mockMvc.perform(post("/device")
                        .content("""
                                    {
                                        "operatingSystem": "Microsoft Mobile",
                                        "deviceId": "5ae08662-4337-11ec-81d3-0242ac130003"
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(deviceService, times(0)).saveDevice(any(DeviceDto.class), anyString());
    }

    @Test
    void postWithAlreadyPresentDeviceReturnsConflict() throws Exception {
        String errorMessage = "Requested device with device ID: 5ae08662-4337-11ec-81d3-0242ac130003 for user: 42 does not exist.";
        when(authoritySession.getLoggedUser()).thenReturn(new LoggedUser("42", "CLIENT"));
        doThrow(new ConflictException(errorMessage)).when(deviceService).saveDevice(any(DeviceDto.class), anyString());

        mockMvc.perform(post("/device")
                        .content("""
                                    {
                                        "operatingSystem": "ANDROID",
                                        "deviceId": "5ae08662-4337-11ec-81d3-0242ac130003"
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isConflict())
                .andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    void getOnSingleDeviceReturnsDetail() throws Exception {
        String deviceId = "5ae08662-4337-11ec-81d3-0242ac130003";
        when(authoritySession.getLoggedUser()).thenReturn(new LoggedUser("42", "CLIENT"));
        when(deviceService.getDevice(anyString(), anyString()))
                .thenReturn(Device.newDevice()
                        .operatingSystem(OperatingSystem.IOS)
                        .deviceId(deviceId)
                        .userId("42")
                        .build()
                );

        mockMvc.perform(get(String.format("/device/%s", deviceId)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId").value(deviceId))
                .andExpect(jsonPath("$.operatingSystem").value("IOS"));
    }

    @Test
    void getOnSingleDeviceReturnsNotFound() throws Exception {
        String deviceId = "5ae08662-4337-11ec-81d3-0242ac130003";
        String errorMessage = String.format("Requested device with device ID: %s for user: 42 does not exist.", deviceId);
        when(authoritySession.getLoggedUser()).thenReturn(new LoggedUser("42", "CLIENT"));
        when(deviceService.getDevice(anyString(), anyString()))
                .thenThrow(new NotFoundException(errorMessage));

        mockMvc.perform(get("/device/5ae08662-4337-11ec-81d3-0242ac130003"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString(errorMessage)));
    }

    @Test
    void putWithValidEnumIsAccepted() throws Exception {
        String deviceId = "5ae08662-4337-11ec-81d3-0242ac130003";
        when(authoritySession.getLoggedUser()).thenReturn(new LoggedUser("42", "CLIENT"));
        when(deviceService.updateDevice(anyString(), anyString(), any(UpdateDeviceDto.class)))
                .thenReturn(Device.newDevice()
                        .operatingSystem(OperatingSystem.IOS)
                        .deviceId(deviceId)
                        .userId("42")
                        .build()
                );
        mockMvc.perform(put(String.format("/device/%s", deviceId))
                        .content("""
                                    {
                                        "operatingSystem": "IOS"
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.deviceId").value(deviceId))
                .andExpect(jsonPath("$.operatingSystem").value("IOS"));

    }

    @Test
    void putWithInvalidEnumIsRejected() throws Exception {
        mockMvc.perform(put("/device/5ae08662-4337-11ec-81d3-0242ac130003")
                        .content("""
                                    {
                                        "operatingSystem": "Microsoft Mobile"
                                    }
                                """)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());

        verify(deviceService, times(0)).updateDevice(anyString(), anyString(), any(UpdateDeviceDto.class));
    }
}