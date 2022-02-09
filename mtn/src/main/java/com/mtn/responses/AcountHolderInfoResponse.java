package com.mtn.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AcountHolderInfoResponse {

     private String sub;
     private String name;
     private String given_name;
     private String family_name;
     private String birthdate;
     private String locale;
     private String gender;
     private int updated_at;

}
