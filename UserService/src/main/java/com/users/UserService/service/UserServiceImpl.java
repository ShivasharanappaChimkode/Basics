package com.users.UserService.service;

import com.users.UserService.entities.Hotel;
import com.users.UserService.entities.Rating;
import com.users.UserService.entities.User;
import com.users.UserService.exception.ResourceNotFoundException;
import com.users.UserService.external.services.HotelService;
import com.users.UserService.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;


@Service
public class UserServiceImpl implements UserService{

    @Autowired
    UserRepository userRepository;
    @Autowired
    RestTemplate restTemplate;

    //Using FeignClient way
    @Autowired
    HotelService hotelService;

    private static final Logger logger= LoggerFactory.getLogger(UserServiceImpl.class);

    @Override
    public User saveUser(User user) {
        String randomUserId = UUID.randomUUID().toString();
        user.setUserId(randomUserId);
        return userRepository.save(user);
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String userId) {
        //Get the data from database with the help of repository
        User user = userRepository.findById(userId).orElseThrow(() -> new ResourceNotFoundException("User with given id is not found in the server !!" + userId));
        // fetch the rating from mentioned server.
        //http://localhost:8083/ratings/users/3ecca99a-20b6-46c1-90f9-404fda98b653
        //using Service_Name
        Rating[] ratingOfUser = restTemplate.getForObject("http://RATING-SERVICE/ratings/users/"+user.getUserId(), Rating[].class);
        logger.info("{}",ratingOfUser);
        List<Rating> ratings= Arrays.stream(ratingOfUser).toList();

        //api call to get the hotel service
        List<Rating> ratingList = ratings.stream().map(rating -> {
            ResponseEntity<Hotel> forEntity = restTemplate.getForEntity("http://HOTEL-SERVICE/hotels/" + rating.getHotelId(), Hotel.class);
//            Hotel hotel = forEntity.getBody();
            //using feignclient way
            Hotel hotel = hotelService.getHotel(rating.getHotelId());
            logger.info("response status code: {}", forEntity.getStatusCode());

            //set the hotel to rating
            rating.setHotel(hotel);

            // return the rating
            return  rating;
        }).collect(Collectors.toList());

        user.setRatings(ratingList);
        return user;

    }
}
