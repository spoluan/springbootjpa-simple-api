package taodigital.interview.jpa.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookResponse {
    private UUID id;
    private String title;
    private String author;
    private String image;
    private List<InventoryBookResponse> inventories;
}
