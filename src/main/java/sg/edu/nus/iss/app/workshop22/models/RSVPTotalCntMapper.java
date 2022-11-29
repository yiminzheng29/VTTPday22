package sg.edu.nus.iss.app.workshop22.models;

import java.sql.ResultSet;
import java.sql.SQLException;

import org.springframework.jdbc.core.RowMapper;

public class RSVPTotalCntMapper implements RowMapper<Rsvp> {
    @Override
    public Rsvp mapRow(ResultSet rs, int rowNum) throws SQLException {
        Rsvp r = new Rsvp();
        r.setTotalCnt(rs.getInt("total"));
        return r;
    }
    
}
