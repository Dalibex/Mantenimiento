package org.mps.board;


import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class AdvertisementBoardTest {

    AdvertisementBoard adBoard;
    AdvertiserDatabase adDataBase;
    PaymentGateway paymentGateway;
    @BeforeEach
    void setup() {
        adBoard= new AdvertisementBoard();
        adDataBase = mock(AdvertiserDatabase.class);
        paymentGateway = mock(PaymentGateway.class);
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

    @Test
    @DisplayName("Publicar un anuncio por parte del anunciante Pepe Gotera y Otilio y comprobar que, si está en la base de datos de anunciantes pero no tiene saldo, el anuncio no se inserta,lo que se determina observando que el número de anuncios no aumenta.")
    public void AdvertisementBoard_CreateNewAdvertisementWithoutFunds_AdvertisementIsNotPublished() {
        //Arrange
        Advertisement ad = new Advertisement("Nuevo anuncio", "El diavlo loko", "Pepe Gotera y Otilio");
        
        when(adDataBase.advertiserIsRegistered("Pepe Gotera y Otilio")).thenReturn(true);
        
        when(paymentGateway.advertiserHasFunds("Pepe Gotera y Otilio")).thenReturn(false);
        //Act
        adBoard.publish(ad, adDataBase, paymentGateway);
        //Assert
        assertEquals(1, adBoard.numberOfPublishedAdvertisements());
    }

    @Test
    @DisplayName("Publicar un anuncio por parte de un anunciante llamado Robin Robot, asumiendo que está en la base de datos de anunciantes, que tiene saldo y finalmente comprobando que se ha realizado el cargo.")
    public void AdvertisementBoard_CreateNewAdvertisementWithFunds_AdvertisementPublished(){
        //Arrange
        Advertisement ad = new Advertisement("Nuevo anuncio", "El diavlo", "Robin Robot");
        when(adDataBase.advertiserIsRegistered("Robin Robot")).thenReturn(true);
        when(paymentGateway.advertiserHasFunds("Robin Robot")).thenReturn(true);
        //Act
        adBoard.publish(ad, adDataBase, paymentGateway);
        //Assert
        assertEquals(2, adBoard.numberOfPublishedAdvertisements());
    }

    @Test
    @DisplayName("Publicar dos anuncios distintos por parte de THE Company, borrar el primero y comprobar que si se busca ya no está en el tablón.")
    public void AdvertisementBoard_CreateTwoAdvertisementsByTHECompanyRemoveFirst_VerifyTheFirst(){
        //Arrange
        String boardOwner = "THE Company";
        String Title = "Nuevo anuncio";
        Advertisement ad = new Advertisement(Title, "El diavlo", boardOwner);
        Advertisement ad2 = new Advertisement(Title+"2", "El diavlo 2", boardOwner);
        //Act
        adBoard.publish(ad, adDataBase, paymentGateway);
        adBoard.publish(ad2, adDataBase, paymentGateway);
        adBoard.deleteAdvertisement(Title, boardOwner);
        //Assert
        //adBoard.findByTitle(Title);
        Optional<Advertisement> none = Optional.empty();
        assertEquals(none, adBoard.findByTitle(Title));
        assertEquals(2, adBoard.numberOfPublishedAdvertisements());
    }

    @Test
    @DisplayName("Comprobar que si se intenta publicar un anuncio que ya existe (mismo título y mismo anunciante), no se realiza la publicación y no se realiza ningún cargo. El anunciante no debe ser THE Company. Para pasar esta prueba hay que modificar la clase AdvertisementBoard.")
    public void AdvertisementBoard_PublishTwoSameAdvertisements_AdvertisementNotPublished(){
        //Arrange
        Advertisement ad = new Advertisement("Nuevo anuncio", "El diavlo", "Robin Robot");
        when(adDataBase.advertiserIsRegistered("Robin Robot")).thenReturn(true);
        when(paymentGateway.advertiserHasFunds("Robin Robot")).thenReturn(true);
        //Act
        adBoard.publish(ad, adDataBase, paymentGateway);
        adBoard.publish(ad, adDataBase, paymentGateway);
        //Assert
        assertEquals(2, adBoard.numberOfPublishedAdvertisements());
    }
}