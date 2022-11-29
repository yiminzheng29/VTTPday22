package sg.edu.nus.iss.app.workshop22.models;

import java.io.ByteArrayInputStream;

import org.joda.time.DateTime;
import org.joda.time.Instant;
import org.springframework.jdbc.support.rowset.SqlRowSet;

import jakarta.json.Json;
import jakarta.json.JsonObject;
import jakarta.json.JsonReader;


public class Rsvp {
    private Integer id;
    private String name;
    private String email;
    private String phone;
    private DateTime confirmationDate;
    private String comments;
    private Integer totalCnt;

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getPhone() {
        return phone;
    }
    public void setPhone(String phone) {
        this.phone = phone;
    }
    public DateTime getConfirmationDate() {
        return confirmationDate;
    }
    public void setConfirmationDate(DateTime confirmationDate) {
        this.confirmationDate = confirmationDate;
    }
    public String getComments() {
        return comments;
    }
    public void setComments(String comments) {
        this.comments = comments;
    }

    public Integer getTotalCnt() {
        return totalCnt;
    }

    public void setTotalCnt(Integer totalCnt) {
        this.totalCnt = totalCnt;
    }

    public static Rsvp create(String jsonStr) throws Exception {
        JsonReader reader = Json.createReader(
                new ByteArrayInputStream(jsonStr.getBytes()));
        return create(reader.readObject());
    }

    public static Rsvp create(JsonObject readObject) {
        final Rsvp r = new Rsvp();
        r.setName(readObject.getString("name"));
        r.setEmail(readObject.getString("email"));
        r.setPhone(readObject.getString("phone"));
        r.setConfirmationDate(new DateTime(Instant.parse(readObject.getString("confirmation_date"))));
        r.setComments(readObject.getString("comments"));
        return r;
    }

    public static Rsvp create(SqlRowSet sqlObject) {
        final Rsvp r = new Rsvp();
        r.setName(sqlObject.getString("name"));
        r.setEmail(sqlObject.getString("email"));
        r.setPhone(sqlObject.getString("phone"));
        r.setConfirmationDate(new DateTime(Instant.parse(sqlObject.getString("confirmation_date"))));
        r.setComments(sqlObject.getString("comments"));
        return r;
    }

    public JsonObject toJson() {
        return Json.createObjectBuilder()
            .add("id", getId())
            .add("name", getName())
            .add("email", getEmail())
            .add("phone", getPhone())
            .add("confirmation_date", getConfirmationDate() != null ? getConfirmationDate().toString() : "")
            .add("comments", getComments())
            .build();
    }

}
