package com.ucr.reco.service;


import com.ucr.reco.model.Reservation;
import com.ucr.reco.model.Space;
import com.ucr.reco.model.Status;
import com.ucr.reco.model.User;
import com.ucr.reco.model.dto.ReservationDTO;
import com.ucr.reco.repository.ReservationJpaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ReservationService {

    @Autowired
    private ReservationJpaRepository repository;
    @Autowired
    private SpaceService spaceService;
    @Autowired
    private UserService userService;


    public ReservationService() {
    }


    public List<Reservation> findAll() {
        return repository.findAll();
    }

    public Reservation add(ReservationDTO reservation) {
        Space space = spaceService.getById(reservation.getSpaceId());
        User user = userService.getByEmail(reservation.getEmail());
        Reservation reservationTemp = new Reservation();
        reservationTemp.setSpace(space);
        reservationTemp.setUser(user);
        reservationTemp.setStartDate(reservation.getStarDate());
        reservationTemp.setFinalDate(reservation.getFinalDate());
        reservationTemp.setStatus(Status.PENDING);
        return repository.save(reservationTemp);
    }


}
