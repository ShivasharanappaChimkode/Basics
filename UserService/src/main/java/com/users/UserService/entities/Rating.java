package com.users.UserService.entities;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Rating {

    private String ratingId;
    private String userId;
    private String hotelId;
    private double rating;
    private String feedback;

    private Hotel hotel;


}
