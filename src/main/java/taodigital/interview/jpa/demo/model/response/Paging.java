package taodigital.interview.jpa.demo.model.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Paging {
    private Long offset;
    private Integer limit;
    private Integer pageNumber;
    private Integer totalPages;
}
