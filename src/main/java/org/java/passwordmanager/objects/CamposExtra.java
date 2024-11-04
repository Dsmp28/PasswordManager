package org.java.passwordmanager.objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import java.util.ArrayList;
import java.util.List;

public class CamposExtra {
    //Campos extra
    @JsonIgnore
    private List<String> extras;

    private String extra1;
    private String extra2;
    private String extra3;
    private String extra4;
    private String extra5;

    public CamposExtra(){}

    public CamposExtra(String extra1, String extra2, String extra3, String extra4, String extra5) {
        this.extra1 = (extra1 != null) ? extra1 : "";
        this.extra2 = (extra2 != null) ? extra2 : "";
        this.extra3 = (extra3 != null) ? extra3 : "";
        this.extra4 = (extra4 != null) ? extra4 : "";
        this.extra5 = (extra5 != null) ? extra5 : "";
        extras = new ArrayList<>(List.of(extra1, extra2, extra3, extra4, extra5));
    }

    public String getExtra1() {return extra1;}
    public String getExtra2() {return extra2;}
    public String getExtra3() {return extra3;}
    public String getExtra4() {return extra4;}
    public String getExtra5() {return extra5;}
    public List<String> getExtras() {return List.of(extra1, extra2, extra3, extra4, extra5);}

    public void setExtra1(String extra1) {this.extra1 = extra1;}
    public void setExtra2(String extra2) {this.extra2 = extra2;}
    public void setExtra3(String extra3) {this.extra3 = extra3;}
    public void setExtra4(String extra4) {this.extra4 = extra4;}
    public void setExtra5(String extra5) {this.extra5 = extra5;}
    public void setExtras(List<String> extras) {
        this.extras = new ArrayList<>(extras); // Almacenar como ArrayList mutable
    }

    @Override
    public String toString() {
        return extra1 + "," + extra2 + "," + extra3 + "," + extra4 + "," + extra5;
    }

}
