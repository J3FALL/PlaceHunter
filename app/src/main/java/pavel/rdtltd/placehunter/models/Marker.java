package pavel.rdtltd.placehunter.models;

import com.activeandroid.Model;
import com.activeandroid.annotation.Column;
import com.activeandroid.annotation.Table;
import com.google.gson.annotations.Expose;

import java.sql.Timestamp;

/**
 * Created by PAVEL on 09.12.2015.
 */
@Table(name = "Marker")
public class Marker extends Model{

    @Expose
    @Column(name = "marker_id")
    private int markerId;

    @Expose
    @Column(name = "creator_id")
    private int creatorId;

    @Expose
    @Column(name = "latitude")
    private double latitude;

    @Expose
    @Column(name = "longitude")
    private double longitude;

    @Expose
    @Column(name = "title")
    private String title;

    @Expose
    @Column(name = "snippet")
    private String snippet;

    @Expose
    @Column(name = "type")
    private String type;

    @Expose
    @Column(name = "timestamp")
    private Timestamp timestamp;

    @Expose
    @Column(name = "rate")
    private int rate;

    @Expose
    @Column(name = "pic")
    private String pic;

    public Marker() {
        super();
        markerId = 0;
        creatorId = 0;
        latitude = 0;
        longitude = 0;
        title = "";
        snippet = "";
        type = "";
        rate = 0;
        pic = "";
    }
    public int getmarkerId() {
        return markerId;
    }

    public void setmarkerId(int id) {
        this.markerId = id;

    }

    public int getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(int creatorId) {
        this.creatorId = creatorId;
    }

    public double getLatitude() {
        return latitude;
    }

    public void setLatitude(double latitude) {
        this.latitude = latitude;
    }

    public double getLongitude() {
        return longitude;
    }

    public void setLongitude(double longitude) {
        this.longitude = longitude;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSnippet() {
        return snippet;
    }

    public void setSnippet(String snippet) {
        this.snippet = snippet;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Timestamp getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Timestamp timestamp) {
        this.timestamp = timestamp;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public String getPic() {
        return pic;
    }

    public void setPic(String pic) {
        this.pic = pic;
    }
}
