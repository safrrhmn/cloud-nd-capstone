package com.saifurtech.locale;

import com.saifurtech.locale.dao.entity.Location;
import com.saifurtech.locale.repo.LocaleRepo;
import com.saifurtech.locale.service.LocationService;
import com.saifurtech.locale.util.ZipcodeBaseRestClient;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class LocaleApplicationTests {

    @Mock
    private LocaleRepo localeRepo;
    @Mock
    private ZipcodeBaseRestClient zipcodeBaseRestClient;
    @InjectMocks
    private LocationService locationService;

    @Test
    void queryDbFetchSuccess() {
        List<Location> data = new ArrayList<>();
        Location location = new Location();
        location.setPostalCode("22193");
        data.add(location);

        when(localeRepo.findAllByZipCode(any())).thenReturn(data);
        List<Location> locations = locationService.locationByZipCode("22193");
        Assertions.assertFalse(locations.isEmpty());
    }

    @Test
    void callZipCodeBaseSuccess() {
        String responseBody = "{\n" +
                "  \"results\": {\n" +
                "    \"22193\": [\n" +
                "      {\n" +
                "        \"postal_code\": \"22193\",\n" +
                "        \"country_code\": \"ES\",\n" +
                "        \"latitude\": \"42.26210000\",\n" +
                "        \"longitude\": \"-0.40660000\",\n" +
                "        \"city\": \"Santa Eulalia De La Peña\",\n" +
                "        \"state\": \"Aragon\",\n" +
                "        \"city_en\": \"Santa Eulalia De La Peña\",\n" +
                "        \"state_en\": \"Aragon\",\n" +
                "        \"state_code\": \"AR\",\n" +
                "        \"province\": \"Huesca\",\n" +
                "        \"province_code\": \"HU\"\n" +
                "      }\n" +
                "    ]\n" +
                "  }\n" +
                "}\n";
        ResponseEntity<Object> locationResponseEntity = new ResponseEntity<>(responseBody, HttpStatusCode.valueOf(200));
        when(zipcodeBaseRestClient.get(any(), any(), any())).thenReturn(locationResponseEntity);
        List<Location> locations = locationService.locationByZipCode("22193");
        Assertions.assertFalse(locations.isEmpty());
    }

}
