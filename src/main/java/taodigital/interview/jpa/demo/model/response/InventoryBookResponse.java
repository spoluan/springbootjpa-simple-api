package taodigital.interview.jpa.demo.model.response;

import lombok.Builder;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class InventoryBookResponse {
    private UUID id;
    private LocalDateTime borrowDate;
    private UUID userId;
    private String username;
    private String role;
}
