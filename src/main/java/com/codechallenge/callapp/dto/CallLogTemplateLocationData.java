package com.codechallenge.callapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CallLogTemplateLocationData {
    private String locationName;
    private String locationNumber;
    private String locationEnabled;
    private String locationWeight;
}
