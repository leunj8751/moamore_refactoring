package moa.moamore.domain;

import javax.persistence.Converter;

@Converter(autoApply = true)
public class Budget_periodConverter extends AbstractBaseEnumConverter<Budget_period, Integer> {

    @Override
    protected Budget_period[] getValueList() {
        return Budget_period.values();
    }
}
