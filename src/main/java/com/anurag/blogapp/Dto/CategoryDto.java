package com.anurag.blogapp.Dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class CategoryDto {

    private int id;

    @NotBlank(message = "Title should not be blank")
    @Size(min = 4)
    private String title;

    @NotBlank(message = "Description should not be blank")
    @Size(min = 3, max = 30, message = "Size should be minimum 3 & max 30 characters")
    private String description;
}
