package luminia.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.Page;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@Setter
@Getter
public class ListDto<T> {
    private final List<T> list;
    private final int totalPages;
    private final long size;


    public ListDto(Page<T> page) {
        this.totalPages = page.getTotalPages();
        this.size = page.getTotalElements();
        list = new ArrayList<>();
        for(var e : page) {
            list.add(e);
        }
    }
}
