package cityguide.datastorage.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Collections;
import java.util.Optional;

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
import cityguide.datastorage.core.dao.ShowPlaceDao;
import cityguide.datastorage.dto.AddressDto;
import cityguide.datastorage.model.ShowPlace;

@ExtendWith(MockitoExtension.class)
public class AddressesRestControllerTest {

    private MockMvc mvc;

    @Mock
    private ShowPlaceDao showPlaceDao;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new AddressesRestController(showPlaceDao)).build();
    }

    @Test
    void getAddressTest() throws Exception {
        Mockito.when(showPlaceDao.getAllShowPlace(false)).thenReturn(Collections.emptyList());
        mvc.perform(get("/api/addresses").param("hascoord", "false")).andDo(print()).andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }

    @Test
    void postAddressTest() throws Exception {
        final var address = "street1";
        final var addressDto = new AddressDto();
        addressDto.setAddress(address);

        ObjectMapper objectMapper = new ObjectMapper();
        final var jsonAddressDto = objectMapper.writeValueAsString(addressDto);

        final var showPlace = new ShowPlace();
        Mockito.when(showPlaceDao.getShowPlace(address)).thenReturn(Optional.of(showPlace));

        mvc.perform(post("/api/addresses").contentType(MediaType.APPLICATION_JSON).content(jsonAddressDto))
                .andDo(print()).andExpect(status().isOk());

        Mockito.verify(showPlaceDao).insertUpdateShowplace(showPlace);
    }

    @Test
    void postAddressTestForNotFoundAddress() throws Exception {
        final var addressDto = new AddressDto();
        addressDto.setAddress("street1");

        ObjectMapper objectMapper = new ObjectMapper();
        final var jsonAddressDto = objectMapper.writeValueAsString(addressDto);

        final var showPlace = new ShowPlace();
        Mockito.when(showPlaceDao.getShowPlace(any())).thenReturn(Optional.empty());

        mvc.perform(post("/api/addresses").contentType(MediaType.APPLICATION_JSON).content(jsonAddressDto))
                .andDo(print()).andExpect(status().isOk());

        Mockito.verify(showPlaceDao, never()).insertUpdateShowplace(showPlace);
    }
}
