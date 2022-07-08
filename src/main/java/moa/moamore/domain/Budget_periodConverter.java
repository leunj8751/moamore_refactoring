package moa.moamore.domain;

import javax.persistence.AttributeConverter;
import javax.persistence.Converter;
import java.util.Arrays;

@Converter(autoApply = true)
public class Budget_periodConverter implements AttributeConverter<Budget_period, Integer> {

    @Override
    public Integer convertToDatabaseColumn(Budget_period attribute) {
        return attribute.getValue();
    }

    @Override
    public Budget_period convertToEntityAttribute(Integer dbData) {
        return Arrays.stream(getValueList())
                .filter(e -> e.getValue().equals(dbData))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException(String.format("Unsupported type for %s.", dbData)));
    }

    public Budget_period[] getValueList() {
        return Budget_period.values();
    }


}
