package sg.edu.nus.iss.app.workshop22.repositories;

import java.util.LinkedList;
import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import sg.edu.nus.iss.app.workshop22.models.RSVPTotalCntMapper;
import sg.edu.nus.iss.app.workshop22.models.Rsvp;

import static sg.edu.nus.iss.app.workshop22.repositories.Queries.*;

@Repository
public class RsvpRepository {
    
    @Autowired
    private JdbcTemplate jdbcTemplate;

    public List<Rsvp> getAllRSVP(String x) {
        final List<Rsvp> rsvp = new LinkedList<>();
        SqlRowSet result = null;
        if (x==null) {
            result = jdbcTemplate.queryForRowSet(SQL_SELECT_ALL_RSVP);
        } else {
            result = jdbcTemplate.queryForRowSet(SQL_SEARCH_RSVP_BY_NAME, x);
        }
        while (result.next()) {
            rsvp.add(Rsvp.create(result));
        }
        return rsvp;
    }

    public Rsvp searchRSVPByEmail(String email) {
        // prevent inheritance
        final List<Rsvp> rsvps = new LinkedList<>();
        // perform the query
        final SqlRowSet rs = jdbcTemplate.queryForRowSet(SQL_SEARCH_RSVP_BY_EMAIL, email);

        while (rs.next()) {
            rsvps.add(Rsvp.create(rs));
        }
        return rsvps.get(0);
    }

    public Rsvp insertRsvp(final Rsvp rsvp) {
        KeyHolder keyholder = new GeneratedKeyHolder();
        try {
            jdbcTemplate.update(conn -> {
                PreparedStatement ps = conn.prepareStatement(SQL_INSERT_RSVP, Statement.RETURN_GENERATED_KEYS);
                ps.setString(1, rsvp.getName());
                ps.setString(2, rsvp.getEmail());
                ps.setString(3, rsvp.getPhone());
                System.out.println("Confirmation date > " + rsvp.getConfirmationDate());
                ps.setTimestamp(4, new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()));
                ps.setString(5, rsvp.getComments());
                return ps;
            }, keyholder);

            BigInteger primaryKeyVal = (BigInteger) keyholder.getKey();
            rsvp.setId(primaryKeyVal.intValue());
        
        } catch (DataIntegrityViolationException e) {
            Rsvp existingRSVP = searchRSVPByEmail(rsvp.getEmail());
            existingRSVP.setComments(rsvp.getComments());
            existingRSVP.setName(rsvp.getName());
            existingRSVP.setPhone(rsvp.getPhone());
            existingRSVP.setConfirmationDate(rsvp.getConfirmationDate());
            if (updateRSVP(existingRSVP)) {
                rsvp.setId(existingRSVP.getId());
            }
        }
        return rsvp;
    }

    public boolean updateRSVP(final Rsvp rsvp) {
        return jdbcTemplate.update(SQL_UPDATE_RSVP_BY_EMAIL, 
            rsvp.getName(),
            rsvp.getEmail(),
            rsvp.getPhone(),
            new Timestamp(rsvp.getConfirmationDate().toDateTime().getMillis()),
            rsvp.getComments(),
            rsvp.getEmail()) > 0;
    }

    public Integer getTotalRSVP() {
        // perform the query
        List<Rsvp> rsvps = jdbcTemplate.query(SQL_TOTAL_CNT_RSVP, new RSVPTotalCntMapper(), new Object[] {});
        return rsvps.get(0).getTotalCnt();
    }
}
