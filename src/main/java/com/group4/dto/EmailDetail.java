package com.group4.dto;
import lombok.*;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailDetail {
    // Class data members
    private String recipient;
    private String msgBody;
    private String subject;
    private String attachment;
}
