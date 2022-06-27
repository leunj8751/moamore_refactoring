package moa.moamore.domain;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Budget_category_id implements Serializable {

    private Category category;
    private Budget budget;
    private Member member;

}
