package com.ucr.reco.model.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.ucr.reco.model.Space;

import java.time.LocalDateTime;

public class ReservationDTO {

    private Integer spaceId;
    private String email;
    @JsonFormat(pattern = "dd-mm-yyyy hh:mm")
    private LocalDateTime starDate;
    @JsonFormat(pattern = "dd-mm-yyyy hh:mm")
    private LocalDateTime finalDate;



    public ReservationDTO() {
    }


    public ReservationDTO(Integer spaceId, String email,LocalDateTime starDate, LocalDateTime finalDate) {
        this.spaceId = spaceId;
        this.email = email;
        this.starDate = starDate;
        this.finalDate = finalDate;
    }


    public Integer getSpaceId() {
        return spaceId;
    }

    public void setSpaceId(Integer spaceId) {
        this.spaceId = spaceId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getStarDate() {
        return starDate;
    }

    public void setStarDate(LocalDateTime starDate) {
        this.starDate = starDate;
    }

    public LocalDateTime getFinalDate() {
        return finalDate;
    }

    public void setFinalDate(LocalDateTime finalDate) {
        this.finalDate = finalDate;
    }



}
