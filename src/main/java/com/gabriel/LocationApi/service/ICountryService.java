package com.gabriel.LocationApi.service;

import com.gabriel.LocationApi.model.Country;
import java.util.List;

public interface ICountryService {
    List<Country> findAll();
    Country addCountry(Country country);
}