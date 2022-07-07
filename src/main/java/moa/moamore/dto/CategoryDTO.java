package moa.moamore.dto;

import lombok.Getter;
import lombok.Setter;
import moa.moamore.domain.Money_type;

@Getter
@Setter
public class CategoryDTO {

    private Long id;
    private String category_name;
    private int amount;

    private Money_type category_type;

    public CategoryDTO(Long id, String category_name) {
        this.id = id;
        this.category_name = category_name;
    }

    public CategoryDTO(Long id, String category_name, int amount) {
        this.id = id;
        this.category_name = category_name;
        this.amount = amount;
    }

    public CategoryDTO() {

    }

}
