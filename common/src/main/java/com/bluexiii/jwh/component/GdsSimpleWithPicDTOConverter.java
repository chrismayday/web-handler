package com.bluexiii.jwh.component;

import com.bluexiii.jwh.domain.Gds;
import com.bluexiii.jwh.dto.GdsSimpleWithPicDTO;
import org.modelmapper.ModelMapper;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

@Component
public class GdsSimpleWithPicDTOConverter implements Converter<Gds, GdsSimpleWithPicDTO> {
    private final ModelMapper modelMapper = new ModelMapper();

    @Override
    public GdsSimpleWithPicDTO convert(Gds source) {
        GdsSimpleWithPicDTO dist = new GdsSimpleWithPicDTO();
        modelMapper.map(source, dist);
        return dist;
    }
}
