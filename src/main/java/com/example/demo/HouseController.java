package com.example.demo;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
public class HouseController {

    private final HouseRepository houseRepository;

    public HouseController(HouseRepository houseRepository) {
        this.houseRepository = houseRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("lat", 27.6612);
        model.addAttribute("lng", 83.5469);
        model.addAttribute("houseName", "Tilottama Ward-9");
        return "index";
    }

    @PostMapping("/search")
    @ResponseBody
    public HouseResponse searchHouse(@RequestParam("name") String name) {
        Optional<House> houseOpt = houseRepository.findByNameIgnoreCase(name);

        if (houseOpt.isPresent()) {
            House house = houseOpt.get();
            return new HouseResponse(house.getName(), house.getLatitude(), house.getLongitude(), null);
        } else {
            return new HouseResponse(null, 27.6612, 83.5469, "House not found in Tilottama Ward-9!");
        }
    }

    // DTO for JSON response
    public static class HouseResponse {
        private String name;
        private double lat;
        private double lng;
        private String error;

        public HouseResponse(String name, double lat, double lng, String error) {
            this.name = name;
            this.lat = lat;
            this.lng = lng;
            this.error = error;
        }

        // getters
        public String getName() { return name; }
        public double getLat() { return lat; }
        public double getLng() { return lng; }
        public String getError() { return error; }
    }
}
