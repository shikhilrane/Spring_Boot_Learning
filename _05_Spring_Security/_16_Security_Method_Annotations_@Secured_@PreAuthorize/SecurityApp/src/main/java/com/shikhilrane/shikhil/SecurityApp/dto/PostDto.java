package com.shikhilrane.shikhil.SecurityApp.dto;

import com.shikhilrane.shikhil.SecurityApp.entities.User;
import jakarta.persistence.ManyToOne;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class PostDto {
    private Long id;
    private String title;
    private String description;
    private UserDto author;
}
