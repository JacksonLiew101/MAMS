package com.example.mams;

import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class ConfirmOrderClass implements Initializable {
    String AlbumName;
    int QuantityAlbumRented;
    double AlbumUnitPriceWhenRented, TotalAlbumCost;
    String RentalStatus;
    public ConfirmOrderClass(String albumName, int quantityAlbumRented,
                             double albumUnitPriceWhenRented, double totalAlbumCost,
                             String rentalStatus)
    {
        AlbumName = albumName;
        QuantityAlbumRented = quantityAlbumRented;
        AlbumUnitPriceWhenRented = albumUnitPriceWhenRented;
        TotalAlbumCost = totalAlbumCost;
        RentalStatus = rentalStatus;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    public int getQuantityAlbumRented() {
        return QuantityAlbumRented;
    }

    public void setQuantityAlbumRented(int quantityAlbumRented) {
        QuantityAlbumRented = quantityAlbumRented;
    }

    public double getAlbumUnitPriceWhenRented() {
        return AlbumUnitPriceWhenRented;
    }

    public void setAlbumUnitPriceWhenRented(double albumUnitPriceWhenRented) {
        AlbumUnitPriceWhenRented = albumUnitPriceWhenRented;
    }

    public double getTotalAlbumCost() {
        return TotalAlbumCost;
    }

    public void setTotalAlbumCost(double totalAlbumCost) {
        TotalAlbumCost = totalAlbumCost;
    }

    public String getRentalStatus() {
        return RentalStatus;
    }

    public void setRentalStatus(String rentalStatus) {
        RentalStatus = rentalStatus;
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {

    }
}
