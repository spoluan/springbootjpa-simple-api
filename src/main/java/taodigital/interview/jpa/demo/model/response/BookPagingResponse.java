package taodigital.interview.jpa.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BookPagingResponse {
    private List<BookResponse> books;
    private Paging paging;
}
