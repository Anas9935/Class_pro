package com.example.myapplication.objects;

public class restraunts {
    private int resid;
    private String name;
    private String Address;
    private String Address2;
    private String offer;
    private String offer2;
    private float rating;
    private int imgId;

public restraunts(int i,String n,String address,String address2,String o,String o2,float rate,int id){
    resid=i;
    name=n;
    Address=address;
    Address2=address2;
    offer=o;
    offer2=o2;
    rating=rate;
    imgId=id;
}
    public restraunts(int i,String n,String address,String o,String o2,float rate,int id){
        resid=i;
        name=n;
        Address=address;
        Address2="";
        offer=o;
        offer2=o2;
        rating=rate;
        imgId=id;
    }

    public int getResid() {
        return resid;
    }

    public int getImgId() {
        return imgId;
    }

    public float getRating() {
        return rating;
    }

    public String getAddress() {
        return Address;
    }

    public String getAddress2() {
        return Address2;
    }

    public String getName() {
        return name;
    }

    public String getOffer() {
        return offer;
    }

    public String getOffer2() {
        return offer2;
    }
}
