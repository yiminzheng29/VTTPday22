package sg.edu.nus.iss.app.workshop22.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


import jakarta.json.Json;
import jakarta.json.JsonArray;
import jakarta.json.JsonArrayBuilder;
import jakarta.json.JsonObject;
import sg.edu.nus.iss.app.workshop22.models.Rsvp;
import sg.edu.nus.iss.app.workshop22.services.RsvpService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;


@RestController
@RequestMapping(path = "/api/rsvp", produces = MediaType.APPLICATION_JSON_VALUE)
public class RsvpRESTController {

    @Autowired
    private RsvpService rsvpSvc;

    // http://localhost:8080/api/rsvp
    
    @GetMapping()
    public ResponseEntity<String> getAllCustomer(@RequestParam(required = false) String q) {
        // Query the database for rsvps
        List<Rsvp> rsvps = rsvpSvc.getAllRsvp(q);

        // Build the result
        JsonArrayBuilder arrBuilder = Json.createArrayBuilder();
        for (Rsvp c : rsvps)
            arrBuilder.add(c.toJson());
        JsonArray result = arrBuilder.build();
        System.out.println("" + result.toString());
        if (rsvps.isEmpty()) {
            return ResponseEntity
                .status(HttpStatus.NOT_FOUND)
                .contentType(MediaType.APPLICATION_JSON)
                .body("{'error_code': " + HttpStatus.NOT_FOUND + "'}");
        }
        return ResponseEntity  
            .status(HttpStatus.OK)
            .contentType(MediaType.APPLICATION_JSON)
            .body(result.toString());
    }

    @GetMapping(path = "/count") 
    public ResponseEntity<String> countRSVP() {
        JsonObject resp;
        // Query the database for the rsvps
        Integer totalCntRsvps = rsvpSvc.getTotalRSVP();

        resp = Json.createObjectBuilder().add("total_cnt", totalCntRsvps).build();

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(resp.toString());
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE) 
    public ResponseEntity<String> postRSVP(@RequestBody String json) {
        Rsvp rsvp = null;
        Rsvp rsvpResult = null;
        JsonObject resp;
        try {
            rsvp = Rsvp.create(json);
        } catch (Exception ex) {
            ex.printStackTrace();
            resp = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return ResponseEntity.badRequest().body(resp.toString());
        }
        rsvpResult = rsvpSvc.insertRsvp(rsvp);
        resp = Json.createObjectBuilder().add("rsvpId", rsvpResult.getId()).build();

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(resp.toString());
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<String> putRSVP(@RequestBody String json) {
        Rsvp rsvp = null;
        boolean rsvpResult = false;
        JsonObject resp;
        try {
            rsvp = Rsvp.create(json);
        } catch (Exception ex) {
            ex.printStackTrace();
            resp = Json.createObjectBuilder().add("error", ex.getMessage()).build();
            return ResponseEntity.badRequest().body(resp.toString());
        }

        rsvpResult = rsvpSvc.updateRSVP(rsvp);
        resp = Json.createObjectBuilder().add("updated", rsvpResult).build();

        return ResponseEntity.status(HttpStatus.CREATED).contentType(MediaType.APPLICATION_JSON).body(resp.toString());
    }
}
