package sg.edu.nus.iss.app.workshop22.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sg.edu.nus.iss.app.workshop22.models.Rsvp;
import sg.edu.nus.iss.app.workshop22.repositories.RsvpRepository;

@Service
public class RsvpService {
    
    @Autowired
    private RsvpRepository rsvpRepo;

    public List<Rsvp> getAllRsvp(String q) {
        return rsvpRepo.getAllRSVP(q);
    }

    public Rsvp searchRSVPByName(String name) {
        return rsvpRepo.searchRSVPByName(name);
    }

    public Rsvp insertPurchaseOrder(final Rsvp rsvp) {
        return rsvpRepo.insertRsvp(rsvp);
    }

    public boolean updateRSVP(final Rsvp rsvp) {
        return rsvpRepo.updateRSVP(rsvp);
    }

    public Integer getTotalRSVP() {
        return rsvpRepo.getTotalRSVP();
    }
}
