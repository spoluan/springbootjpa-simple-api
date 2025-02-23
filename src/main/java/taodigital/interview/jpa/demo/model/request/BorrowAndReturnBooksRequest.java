package taodigital.interview.jpa.demo.model.request;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BorrowAndReturnBooksRequest {
    @NotNull
    private UUID userId;

    @NotNull
    private UUID inventoryId;
}
