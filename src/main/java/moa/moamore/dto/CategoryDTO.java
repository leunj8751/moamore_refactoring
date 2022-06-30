package moa.moamore.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CategoryDTO {

    private Long category_id;
    private String category_name;
    private int amount;

    public CategoryDTO(Long category_id, String category_name) {
        this.category_id = category_id;
        this.category_name = category_name;
    }

    public CategoryDTO(Long category_id, String category_name, int amount) {
        this.category_id = category_id;
        this.category_name = category_name;
        this.amount = amount;
    }

    public CategoryDTO() {

    }
}
