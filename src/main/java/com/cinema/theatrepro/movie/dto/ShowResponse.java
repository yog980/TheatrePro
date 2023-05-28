package com.cinema.theatrepro.movie.dto;

import com.cinema.theatrepro.shared.enums.ShowShift;
import com.cinema.theatrepro.shared.enums.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ShowResponse {
    private Long id;
    private ShowShift shift;
    private Date startDate;
    private double discountPercentage;
    private Long movie;
    private Long theatre;
    private Status status;
}
