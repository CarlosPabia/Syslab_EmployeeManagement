package com.gabriel.LocationApi.controller;

import com.gabriel.LocationApi.model.Country;
import com.gabriel.LocationApi.service.ICountryService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller
public class MyController {

    private final ICountryService countryService;

    public MyController(ICountryService countryService) {
        this.countryService = countryService;
    }

    @GetMapping("/countries")
    public String findCountries(Model model) {
        List<Country> countries = countryService.findAll(); // ensure service returns List<Country>
        model.addAttribute("countries", countries);
        return "showCountries";
    }

    @GetMapping("/add-country")
    public String addCountry(Model model) {
        model.addAttribute("country", new Country()); // fixed key (no spaces)
        return "addCountry";
    }

    @PostMapping("/add-country")
    public String addCountrySubmit(@ModelAttribute("country") Country country) {
        countryService.addCountry(country);
        return "redirect:/countries"; // Post/Redirect/Get
    }
}
