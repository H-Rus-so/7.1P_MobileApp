package com.example.lostandfoundapp;

public class Advert {
    // These are the details for each advert. I'm keeping them private and using getters to access them later.
    private int    id;
    private String type;
    private String name;
    private String phone;
    private String description;
    private String date;
    private String location;

    /** This is the full constructor – I use it when I want to make a new advert with all the details */
    public Advert(int id,
                  String type,
                  String name,
                  String phone,
                  String description,
                  String date,
                  String location) {
        this.id          = id;
        this.type        = type;
        this.name        = name;
        this.phone       = phone;
        this.description = description;
        this.date        = date;
        this.location    = location;
    }

    // ——— These are getter methods so I can access each value later ———

    /** This gets whether the advert is for a lost or found item */
    public String getType() {
        return type;
    }

    /** This returns the name from the advert (either item name or person’s name) */
    public String getName() {
        return name;
    }

    /** This gets the date the item was lost/found */
    public String getDate() {
        return date;
    }

    /** This returns the location the item was last seen or found */
    public String getLocation() {
        return location;
    }

    /** This returns the unique ID for the advert */
    public int getId() {
        return id;
    }

    /** This gets the phone number that was entered in the advert */
    public String getPhone() {
        return phone;
    }

    /** This gives back the description that was written for the item */
    public String getDescription() {
        return description;
    }

    /** This is used to show a simple summary in the list (like: Found • Wallet (20/04/2025)) */
    @Override
    public String toString() {
        // This controls how each advert appears in the preview list
        return type + " \u2022 " + name + " (" + date + ")";
    }
}
