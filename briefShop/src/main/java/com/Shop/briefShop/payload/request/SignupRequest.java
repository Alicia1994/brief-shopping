package com.Shop.briefShop.payload.request;

import lombok.Data;

import java.util.Set;

import javax.validation.constraints.*;

@Data
public class SignupRequest {
   @NotBlank
   @Size(min = 3, max = 20)
   private String username;

   @NotBlank
   @Size(max = 50)
   @Email
   private String email;

   private Long roleId;
//   private String roleName;

    private String presentation;

   @NotBlank
   @Size(min = 6, max = 40)
   private String password;
}
