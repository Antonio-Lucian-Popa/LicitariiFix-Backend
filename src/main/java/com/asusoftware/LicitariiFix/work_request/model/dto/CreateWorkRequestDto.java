package com.asusoftware.LicitariiFix.work_request.model.dto;

import lombok.*;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkRequestDto {
    private String title;
    private String description;
    private String location;
    private List<String> photos;
}
