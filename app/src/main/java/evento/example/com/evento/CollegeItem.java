package evento.example.com.evento;

import android.support.annotation.NonNull;

import java.io.Serializable;

/**
 * Created by sony on 6/16/2017.
 */
public class CollegeItem{
    String key,name,college,dept,event,short_desc,long_desc,contactP;
    int players,fees,upvotes;
    long contactNo;
    Taareekh eventDate;


    CollegeItem()
    {}

    public CollegeItem(String key) {
        this.key = key;
    }

    @Override
    public String toString() {
        return
                 key + '\n' +
                 name + '\n' +
                 college + '\n' +
                 dept + '\n' +
                 event + '\n' +
                  short_desc + '\n' +
                long_desc + '\n' +
                 contactP + '\n' +
                 players + '\n'+
                 fees + '\n'+
                 upvotes +'\n'+
                 contactNo +'\n'+
                 eventDate +'\n'
                ;
    }

    @Override
    public boolean equals(Object obj) {
        CollegeItem ci=(CollegeItem) obj;

        if(ci.getKey().equals(this.key))
            return true;

        return false;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCollege() {
        return college;
    }

    public void setCollege(String college) {
        this.college = college;
    }

    public String getDept() {
        return dept;
    }

    public void setDept(String dept) {
        this.dept = dept;
    }

    public String getEvent() {
        return event;
    }

    public void setEvent(String event) {
        this.event = event;
    }

    public String getShort_desc() {
        return short_desc;
    }

    public void setShort_desc(String short_desc) {
        this.short_desc = short_desc;
    }

    public String getLong_desc() {
        return long_desc;
    }

    public void setLong_desc(String long_desc) {
        this.long_desc = long_desc;
    }

    public String getContactP() {
        return contactP;
    }

    public void setContactP(String contactP) {
        this.contactP = contactP;
    }

    public long getContactNo() {
        return contactNo;
    }

    public void setContactNo(long contactNo) {
        this.contactNo = contactNo;
    }

    public int getPlayers() {
        return players;
    }

    public void setPlayers(int players) {
        this.players = players;
    }

    public int getFees() {
        return fees;
    }

    public void setFees(int fees) {
        this.fees = fees;
    }

    public Taareekh getEventDate() {
        return eventDate;
    }

    public void setEventDate(Taareekh eventDate) {
        this.eventDate = eventDate;
    }

    public int getUpvotes() {
        return upvotes;
    }

    public void setUpvotes(int upvotes) {
        this.upvotes = upvotes;
    }



    public String shortDescription() {
        return name + "\n" +
                college  + "\n" +
                dept  + "\n" +
                event + "\n" +
                short_desc  + "\n" +
                contactP + ":" + contactNo  + "\n" +
                players  + "\n" +
                fees  + "\n" +
                eventDate  + "\n"+
                upvotes  + "\n"

                ;
    }


}
