package com.vytrack.API_tests;


import java.util.List;

public class AllSpartans {
    private List<spartan> spartanList;

    public AllSpartans(List<spartan> spartanList) {
        this.spartanList = spartanList;
    }
    public AllSpartans(){

    }
    @Override
    public String toString() {
        return "AllSpartans{" +
                "spartanList=" + spartanList +
                '}';
    }



    public List<spartan> getSpartanList() {
        return spartanList;
    }

    public void setSpartanList(List<spartan> spartanList) {
        this.spartanList = spartanList;
    }

}
