package cityguide.geocoder.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
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
    void shouldThrowExceptionThenResponseNotOkForGetAllAddresses() {
        ResponseEntity<AddressDto[]> response = new ResponseEntity<AddressDto[]>((AddressDto[]) null, HttpStatus.BAD_REQUEST);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(AddressDto[].class)))
                .thenReturn(response);
        final var restController = new DataStorageRestControllerImpl(restTemplate, restServerConfig);

        assertThatThrownBy(restController::getAddresses).isInstanceOf(GeoCoderRestControllerException.class);
    }

    @Test
    void whenGetAddressesShouldReturnAddressesList() {
        final var result = new AddressDto[] { new AddressDto(), new AddressDto() };
        final var response = new ResponseEntity<>(result, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(AddressDto[].class)))
                .thenReturn(response);
        final var restController = new DataStorageRestControllerImpl(restTemplate, restServerConfig);

        final var addressList = restController.getAddresses();

        assertThat(addressList.toArray()).isEqualTo(result);
    }

    @Test
    void whenGetAddressesShouldReturnEmptySize() {
        final var response = new ResponseEntity<>((AddressDto[]) null, HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.GET), any(), eq(AddressDto[].class)))
                .thenReturn(response);
        final var restController = new DataStorageRestControllerImpl(restTemplate, restServerConfig);

        final var addressList = restController.getAddresses();

        assertThat(addressList.toArray()).isEmpty();
    }

    @Test
    void testSendAddresses() {
        final ResponseEntity<String> response = new ResponseEntity<>("", HttpStatus.OK);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(response);

        final var restController = new DataStorageRestControllerImpl(restTemplate, restServerConfig);
        final var addressDto = new AddressDto();

        restController.sendAddress(addressDto);

        HttpEntity<?> entity = new HttpEntity<>(addressDto);
        Mockito.verify(restTemplate).exchange(anyString(), eq(HttpMethod.POST), eq(entity), eq(String.class));
    }

    @Test
    void shouldThrowExceptionThenResponseNotOkForSendData() {
        final ResponseEntity<String> response = new ResponseEntity<>("", HttpStatus.BAD_REQUEST);
        Mockito.when(restTemplate.exchange(anyString(), eq(HttpMethod.POST), any(), eq(String.class)))
                .thenReturn(response);
        final var restController = new DataStorageRestControllerImpl(restTemplate, restServerConfig);

        assertThatThrownBy(() -> restController.sendAddress(new AddressDto()))
                .isInstanceOf(GeoCoderRestControllerException.class);
    }
}
