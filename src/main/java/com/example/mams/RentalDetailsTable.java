package com.example.mams;

import javafx.collections.ObservableList;

public class RentalDetailsTable {
    int RentalID, QuantityAlbum;
    double TotalCost;
    String RentalDate, RentalStatus, AlbumName;

    public RentalDetailsTable(int rentalID, String rentalDate, String rentalStatus,
                                 int quantityAlbum, String albumName, double totalCost)
    {
        this.RentalID = rentalID;
        this.RentalDate = rentalDate;
        this.RentalStatus = rentalStatus;
        this.QuantityAlbum = quantityAlbum;
        this.AlbumName = albumName;
        this.TotalCost = totalCost;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    public String getRentalStatus() {
        return RentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        RentalStatus = rentalStatus;
    }

    public String getRentalDate() {
        return RentalDate;
    }

    public void setRentalDate(String rentalDate) {
        RentalDate = rentalDate;
    }

    public double getTotalCost() {
        return TotalCost;
    }

    public void setTotalCost(double totalCost) {
        TotalCost = totalCost;
    }

    public int getQuantityAlbum() {
        return QuantityAlbum;
    }

    public void setQuantityAlbum(int quantityAlbum) {
        QuantityAlbum = quantityAlbum;
    }

    public int getRentalID() {
        return RentalID;
    }

    public void setRentalID(int rentalID) {
        RentalID = rentalID;
    }
}


