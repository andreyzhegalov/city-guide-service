package cityguide.geocoder.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import cityguide.geocoder.config.RestServerConfig;
import cityguide.geocoder.dto.AddressDto;

@ExtendWith(MockitoExtension.class)
class DataStorageRestControllerTest {
    @Mock
    private RestTemplate restTemplate;

    private static RestServerConfig restServerConfig;

    @BeforeAll
    static void setUp() {
        restServerConfig = new RestServerConfig();
        restServerConfig.setUrl("http://test.com/");
        restServerConfig.setShowplacesUri("showplaces");
    }

    @Test
    void whenGetAddressesReturnAddressesList() {
        final var result = new AddressDto[] { new AddressDto(), new AddressDto() };
        ResponseEntity<AddressDto[]> response = new ResponseEntity<AddressDto[]>(result, HttpStatus.OK);

        Mockito.when(
                restTemplate.exchange(anyString(), Mockito.eq(HttpMethod.GET), any(), Mockito.eq(AddressDto[].class)))
                .thenReturn(response);
        final var restController = new DataStorageRestController(restTemplate, restServerConfig);
        final var addressList = restController.getAddresses();
        assertThat(addressList.toArray()).isEqualTo(result);
    }

    @Test
    void whenGetAddressesReturnNull() {
        final AddressDto[] result = null;
        ResponseEntity<AddressDto[]> response = new ResponseEntity<AddressDto[]>(result, HttpStatus.OK);

        Mockito.when(
                restTemplate.exchange(anyString(), Mockito.eq(HttpMethod.GET), any(), Mockito.eq(AddressDto[].class)))
                .thenReturn(response);
        final var restController = new DataStorageRestController(restTemplate, restServerConfig);
        final var addressList = restController.getAddresses();
        assertThat(addressList.toArray()).isEmpty();
    }

    @Test
    void testSendAddresses() {
        final var restController = new DataStorageRestController(restTemplate, restServerConfig);
        final var addressDto = new AddressDto();

        restController.sendAddress(addressDto);

        HttpEntity<?> entity = new HttpEntity<>(addressDto);
        Mockito.verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), eq(entity), eq(String.class));
    }
}
