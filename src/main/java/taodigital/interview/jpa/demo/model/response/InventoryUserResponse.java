package taodigital.interview.jpa.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryUserResponse {
    private UUID id;
    private LocalDateTime borrowDate;
    private UUID bookId;
    private String title;
    private String author;
    private String image;
}
