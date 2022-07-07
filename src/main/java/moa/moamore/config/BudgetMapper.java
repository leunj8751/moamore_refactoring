package moa.moamore.config;

import moa.moamore.domain.Budget;
import moa.moamore.dto.BudgetDTO;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.modelmapper.convention.MatchingStrategies;


public class BudgetMapper {
    //This is a utility class
    private static ModelMapper mapper = new ModelMapper();
    static {
        try {
            mapper.getConfiguration().setMatchingStrategy(
                    MatchingStrategies.STRICT);

//            final Converter<Budget_period, Integer> converter = new AbstractConverter<Budget_period, Integer>() {
//                @Override
//                protected Integer convert(Budget_period s) {
//                    System.out.println("intValue :"+s.intValue());
//                    return s.intValue();
//                }
//            };



            mapper.addMappings(new PropertyMap<Budget, BudgetDTO>() {
                @Override
                protected void configure() {
                    // using(converter).map().setStatus(source.getStatus().name());
//                    map().setPeriod(source.getPeriod().intValue());
//                    System.out.println("source.getPeriod() :"+source.getPeriod());
//                    map(source.getPeriod().intValue()).setPeriod(7);
//                    using(enumConverter).map().setStatus(this.<Budget_period>source("getIntValue"));
                }
            });

        } catch (Throwable t) {
            t.printStackTrace();
        }
    }

    public static ModelMapper getMapper() {
        return mapper;
    }

    public static <T> T translate(Object source, Class<T> target) {
        return mapper.map(source, target);
    }

//    public static Converter<Budget_period, Integer> enumConverter = new Converter<Budget_period, Integer>()
//    {
//        @Override
//        public Integer convert(MappingContext<Budget_period, Integer> mappingContext) {
//            if(mappingContext.getSource() != null) {
//                return Budget_period.valueOf((Budget_period) mappingContext.getSource());
//            }
//            return null;
//        }
//    };

}