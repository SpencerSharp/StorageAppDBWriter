package storageappdbwriter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

/**
 * Created by spencersharp on 6/18/18.
 */
public class User {
    long id;
    String type;
    String firstName;
    String lastName;
    String username;
    String password;
    boolean isActive;
    Date dateCreated;
    String dateCreatedString;
    Date dateUpdated;
    String dateUpdatedString;

    public User() {

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setIsActive(boolean isActive) {
        this.isActive = isActive;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateCreatedString = ft.format(dateCreated);
    }

    public String getDateCreatedString()
    {
        return dateCreatedString;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
        SimpleDateFormat ft = new SimpleDateFormat ("yyyy-MM-dd HH:mm");
        ft.setTimeZone(TimeZone.getTimeZone("UTC"));
        dateUpdatedString = ft.format(dateUpdated);

    }

    public String toString() {
        return firstName + " " + lastName + " " + username + " " + type;
    }
}
