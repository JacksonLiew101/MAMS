package com.example.mams;

public class MusicAlbum {
    int AlbumID, YearOfRelease, QuantityOnHand, AlbumUnitPrice;
    String AlbumName, Artist, Genre;
    public MusicAlbum(int albumID, String albumName, String artist, String genre,
                      int yearOfRelease, int quantityOnHand, int albumUnitPrice)
    {
        this.AlbumID = albumID;
        this.AlbumName = albumName;
        this.Artist = artist;
        this.Genre = genre;
        this.YearOfRelease = yearOfRelease;
        this.QuantityOnHand = quantityOnHand;
        this.AlbumUnitPrice = albumUnitPrice;
    }
    public String getGenre() {
        return Genre;
    }

    public void setGenre(String genre) {
        Genre = genre;
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

    public int getAlbumUnitPrice() {
        return AlbumUnitPrice;
    }

    public void setAlbumUnitPrice(int albumUnitPrice) {
        AlbumUnitPrice = albumUnitPrice;
    }

    public int getQuantityOnHand() {
        return QuantityOnHand;
    }

    public void setQuantityOnHand(int quantityOnHand) {
        QuantityOnHand = quantityOnHand;
    }

    public int getYearOfRelease() {
        return YearOfRelease;
    }

    public void setYearOfRelease(int yearOfRelease) {
        YearOfRelease = yearOfRelease;
    }

    public int getAlbumID() {
        return AlbumID;
    }

    public void setAlbumID(int albumID) {
        AlbumID = albumID;
    }

}
