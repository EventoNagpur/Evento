package evento.example.com.evento;

/**
 * Created by sony on 6/8/2017.
 */

public class User {
    String name,email,uid,url;
    int gender;
    Taareekh dob;
    User(){

    }

    public User(String name, String email, String uid, String url, int gender, Taareekh dob) {
        this.name = name;
        this.email = email;
        this.uid = uid;
        this.url = url;
        this.gender = gender;
        this.dob = dob;
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

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public Taareekh getDob() {
        return dob;
    }

    public void setDob(Taareekh dob) {
        this.dob = dob;
    }
}
