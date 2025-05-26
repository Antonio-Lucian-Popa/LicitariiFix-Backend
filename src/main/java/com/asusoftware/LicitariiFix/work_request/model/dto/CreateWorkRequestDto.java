package com.asusoftware.LicitariiFix.work_request.model.dto;

import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateWorkRequestDto {
    private String title;
    private String description;
    private String location;
    private String[] photos;
}
