package com.mouady.service;


import com.mouady.payload.request.SeatRequest;
import com.mouady.payload.response.SeatResponse;

import java.util.List;

public interface SeatService {


    void generateSeats(Long seatMapId) throws Exception;
    SeatResponse getSeatById(Long id);
    List<SeatResponse> getAll();
    SeatResponse updateSeat(Long id, SeatRequest request);

}
