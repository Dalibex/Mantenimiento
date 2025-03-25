package org.mps.board;


import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class AdvertisementBoardTest {

    AdvertisementBoard adBoard;
    @BeforeEach
    void setup() {
        adBoard= new AdvertisementBoard();
    }

    @Test
    @DisplayName("Patata")
    public void AdvertisementBoard_CreateNewAdvertisementBoard_CreatesCorrectly() {
        // Arrange
        AdvertisementBoard ad = new AdvertisementBoard();

        // Act
        int ads = ad.numberOfPublishedAdvertisements();

        // Assert
        assertEquals(1,ads);
    }

    @Test
    @DisplayName("Patata2")
    public void  AdvertisementBoard_CreateCompanyAdvertisement_IncreasesAdvertisementsOnBoard() {
        //Arrange
        Advertisement ad = new Advertisement("Nuevo anuncio", "El diavlo loko", "THE Company");
        //Act
        adBoard.publish(ad, null, null);
        //Assert
        assertEquals(2, adBoard.numberOfPublishedAdvertisements());
    }
}