package com.users.UserService.payload;

import lombok.*;
import org.springframework.http.HttpStatus;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder(toBuilder = true)
public class ApiResponse {

        private String message;
        private boolean success;
        private HttpStatus status;

}
