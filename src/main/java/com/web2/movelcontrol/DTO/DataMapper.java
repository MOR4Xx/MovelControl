package com.web2.movelcontrol.DTO;

import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;

public class DataMapper {
    // Podemos usar qualquer Mapper
    //private static Mapper mapper = DozerBeanMapperBuilder.buildDefault();
    private static ModelMapper mapper = new ModelMapper();
    // mapeamento genérica
    public static <O, D> D parseObject(O origin, Class<D> destination) {
        return mapper.map(origin, destination);
    }
    // mapeamento genérica
    public static <O, D> List<D> parseListObjects(List<O> origin, Class<D> destination) {
        List<D> destinationObjects = new ArrayList<D>();
        for (O o : origin) {
            destinationObjects.add(mapper.map(o, destination));
        }
        return destinationObjects;
    }
}