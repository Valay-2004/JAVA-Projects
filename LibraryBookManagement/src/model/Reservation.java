package model;

import java.time.LocalDate;

public class Reservation {
    private String id;
    private String bookId;
    private String patronId;
    private LocalDate reservationDate;

    enum ReservationStatus{
        PENDING,
        READY_FOR_PICKUP,
        EXPIRED,
        CANCELLED
    }

}
