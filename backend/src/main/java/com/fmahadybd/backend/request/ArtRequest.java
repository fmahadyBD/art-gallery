package com.fmahadybd.backend.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class ArtRequest {

    @NotBlank(message = "Name is required")
    private String name;
    @NotBlank(message = "Description is required")
    private String description;
    private String image;
    @NotBlank(message = "Price is required")
    private String price;
    @NotBlank(message = "Status is required")
    private String status;
    @NotBlank(message = "Artist is required")
    private String artist;
    @NotNull(message = "Category ID is required")
    private Integer categoryId;
}
