package com.example.mams;

public class SalesReportTableClass {
    int AlbumID, TotalRentedAlbumQuantity;
    double AlbumUnitPrice, TotalSales;
    String AlbumName, Artist;

    public SalesReportTableClass(int albumID, String albumName, String artist,
                                 double albumUnitPrice, int totalRentedAlbumQuantity, double totalSales )
    {
        this.AlbumID = albumID;
        this.AlbumName = albumName;
        this.Artist = artist;
        this.AlbumUnitPrice = albumUnitPrice;
        this.TotalRentedAlbumQuantity = totalRentedAlbumQuantity;
        this.TotalSales = totalSales;
    }

    public String getArtist() {
        return Artist;
    }

    public void setArtist(String artist) {
        Artist = artist;
    }

    public String getAlbumName() {
        return AlbumName;
    }

    public void setAlbumName(String albumName) {
        AlbumName = albumName;
    }

    public double getTotalSales() {
        return TotalSales;
    }

    public void setTotalSales(double totalSales) {
        TotalSales = totalSales;
    }

    public double getAlbumUnitPrice() {
        return AlbumUnitPrice;
    }

    public void setAlbumUnitPrice(double albumUnitPrice) {
        AlbumUnitPrice = albumUnitPrice;
    }

    public int getTotalRentedAlbumQuantity() {
        return TotalRentedAlbumQuantity;
    }

    public void setTotalRentedAlbumQuantity(int totalRentedAlbumQuantity) {
        TotalRentedAlbumQuantity = totalRentedAlbumQuantity;
    }

    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int albumID) {
        AlbumID = albumID;
    }



}
