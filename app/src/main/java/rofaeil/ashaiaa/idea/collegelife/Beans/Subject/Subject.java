package rofaeil.ashaiaa.idea.collegelife.Beans.Subject;

/**
 * Created by emad on 1/4/2017.
 */

public class Subject {
    private String ID;
    private String OldID;
    private String Name;
    private String Type;
    private int BackgroundId;

    public int getBackgroundId() {
        return BackgroundId;
    }

    public void setBackgroundId(int backgroundId) {
        this.BackgroundId = backgroundId;
    }

    public String getType() {
        return Type;
    }

    public void setType(String type) {
        Type = type;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getOldID() {
        return OldID;
    }

    public void setOldID(String oldID) {
        this.OldID = oldID;
    }

    public String getName() {
        return Name;
    }

    public void setName(String name) {
        this.Name = name;
    }

}
