package cityguide.datastorage.controller;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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

import cityguide.datastorage.contoroller.ShowPlaceRestController;
import cityguide.datastorage.core.service.ShowPlaceService;
import cityguide.datastorage.dto.ShowPlaceDto;
import cityguide.datastorage.model.Location;

@ExtendWith(MockitoExtension.class)
public class ShowPlaceRestControllerTest {
    private MockMvc mvc;

    @Mock
    private ShowPlaceService showPlaceService;

    @BeforeEach
    public void setUp() {
        mvc = MockMvcBuilders.standaloneSetup(new ShowPlaceRestController(showPlaceService)).build();
    }

    @Test
    void getShowplaceTest() throws Exception {
        final String description = "description";
        Mockito.when(showPlaceService.getDescription(new Location(0.0, 0.0), 100)).thenReturn(description);
        mvc.perform(get("/api/showplaces").param("lat", "0.0").param("lon", "0.0").param("radius", "100"))
                .andDo(print()).andExpect(status().isOk()).andExpect(content().string(description));
    }

    @Test
    void postShowPlace() throws Exception {
        final var showPlaceDto = new ShowPlaceDto();
        showPlaceDto.setAddress("street1");
        ObjectMapper objectMapper = new ObjectMapper();
        final var jsonShowPlace = objectMapper.writeValueAsString(showPlaceDto);

        mvc.perform(post("/api/showplaces").param("address", "street1").contentType(MediaType.APPLICATION_JSON)
                .content(jsonShowPlace)).andDo(print()).andExpect(status().isOk());

        Mockito.verify(showPlaceService).insertUpdateShowplace(any());
    }
}
