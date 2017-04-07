package rofaeil.ashaiaa.idea.collegelife.Utils;

/**
 * Created by emad on 4/7/2017.
 */

public class RequestHeaderData {
    private String VIEWSTATE;
    private String VIEWSTATEGENERATOR;
    private String EVENTVALIDATION;

    public String getVIEWSTATE() {
        return VIEWSTATE;
    }

    public void setVIEWSTATE(String VIEWSTATE) {
        this.VIEWSTATE = VIEWSTATE;
    }

    public String getVIEWSTATEGENERATOR() {
        return VIEWSTATEGENERATOR;
    }

    public void setVIEWSTATEGENERATOR(String VIEWSTATEGENERATOR) {
        this.VIEWSTATEGENERATOR = VIEWSTATEGENERATOR;
    }

    public String getEVENTVALIDATION() {
        return EVENTVALIDATION;
    }

    public void setEVENTVALIDATION(String EVENTVALIDATION) {
        this.EVENTVALIDATION = EVENTVALIDATION;
    }
}
