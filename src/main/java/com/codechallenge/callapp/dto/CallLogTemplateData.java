package com.codechallenge.callapp.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class CallLogTemplateData extends CallLogTemplateLocationData {
    private String transferTypeName;
    private String createdDateTime;
}
