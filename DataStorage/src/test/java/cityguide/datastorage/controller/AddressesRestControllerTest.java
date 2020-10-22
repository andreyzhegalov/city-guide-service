package cityguide.datastorage.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;

import com.fasterxml.jackson.databind.ObjectMapper;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import cityguide.datastorage.contoroller.AddressesRestController;
import cityguide.datastorage.core.service.ShowPlaceService;
import cityguide.datastorage.dto.AddressDto;
import cityguide.datastorage.model.Location;

@ExtendWith(MockitoExtension.class)
public class AddressesRestControllerTest {

    private MockMvc mvc;

    @Mock
    private ShowPlaceService showPlaceService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AddressesRestController(showPlaceService)).build();
    }

    @Test
    void getAddressTest() throws Exception {
        Mockito.when(showPlaceService.getShowPlaces(false)).thenReturn(Collections.emptyList());
        mvc.perform(get("/api/addresses").param("hascoord", "false")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void postAddressTest() throws Exception {
        final var address = "street1";
        final var addressDto = new AddressDto();
        final var location = new Location(0.0, 1.1);
        addressDto.setAddress(address);
        addressDto.setLatitude(location.getLatitude());
        addressDto.setLongitude(location.getLongitude());

        ObjectMapper objectMapper = new ObjectMapper();
        final var jsonAddressDto = objectMapper.writeValueAsString(addressDto);

        mvc.perform(post("/api/addresses").contentType(MediaType.APPLICATION_JSON).content(jsonAddressDto))
                .andDo(print()).andExpect(status().isOk());

        Mockito.verify(showPlaceService).updateLocation(address, location);
    }
}
