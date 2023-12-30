package com.saifurtech.locale.controller;

import com.saifurtech.locale.dao.entity.Location;
import com.saifurtech.locale.dao.entity.ZipCodesByCountryCityAndState;
import com.saifurtech.locale.dto.PostalCodes;
import com.saifurtech.locale.service.LocationService;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
@Slf4j
public class LocationController {

    @Autowired
    private LocationService locationService;

    @QueryMapping("locationByZipCode")
    public List<Location> locationByZipCode(@NotNull @Argument(name = "zipCode") String zipCode) {
        List<Location> entity = locationService.locationByZipCode(zipCode);
        log.info("query called.");
        return entity;
    }

    @SchemaMapping("allPostalCodes")
    public ZipCodesByCountryCityAndState allPostalCodes(@NotNull Location location) {
        return locationService.getPostalCodes(location.getCountryCode(), location.getState(), location.getCity());
    }
}
