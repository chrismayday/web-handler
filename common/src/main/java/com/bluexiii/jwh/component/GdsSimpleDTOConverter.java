package com.bluexiii.jwh.component;

import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.dto.GdsSimpleDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GdsSimpleDTOConverter implements Converter<Gds, GdsSimpleDTO> {
    private final ModelMapper modelMapper = new ModelMapper();
    @Override
    public GdsSimpleDTO convert(Gds source) {
        GdsSimpleDTO dist = new GdsSimpleDTO();
        modelMapper.map(source, dist);
        return dist;
    }
}
