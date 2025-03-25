package com.soori.wagemanagement.service;

import com.soori.wagemanagement.dto.ComponentDto;
import com.soori.wagemanagement.entity.Component;
import com.soori.wagemanagement.repository.ComponentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ComponentServiceImpl implements ComponentService{

    @Autowired
    private ComponentRepository componentRepository;

    @Override
    public List<ComponentDto> getAllAvailableComponent() {
        List<Component> components = findAllComponent();
        List<ComponentDto> componentDtos = new ArrayList<ComponentDto>();
        for (Component component : components) {
           componentDtos.add(mapToComponentDto(component));
        }

        return componentDtos;
    }

    @Override
    public ComponentDto getComponentByName(String name) {
        Component component = componentByName(name);
        return mapToComponentDto(component);
    }

    @Override
    public ComponentDto addComponent(ComponentDto componentDto) {
        boolean componentName = findComponentByName(componentDto.getComponentName()).isPresent();
        if (componentName) {
            return componentDto;
        }

        Component component = mapToComponentEntity(componentDto);
        Component savedComponent = componentRepository.save(component);

        return mapToComponentDto(savedComponent);
    }

    @Override
    public ComponentDto updateComponent(ComponentDto componentDto) {
        Component existingComponent = findComponentByName(componentDto.getComponentName()).get();
        updateExistingComponent(existingComponent,componentDto);
        Component savedComponent = mapToComponentEntity(componentDto);

        return mapToComponentDto(savedComponent);
    }

    private ComponentDto mapToComponentDto(Component component) {
        return ComponentDto.builder()
                .componentName(component.getComponentName())
                .unit(component.getUnit())
                .build();
    }

    private Component mapToComponentEntity(ComponentDto componentDto ) {
        return Component.builder()
                .componentName(componentDto.getComponentName())
                .unit(componentDto.getUnit())
                .build();
    }

    private void updateExistingComponent(Component existingComponent,ComponentDto componentDto){
        existingComponent.setComponentName(componentDto.getComponentName());
        existingComponent.setUnit(componentDto.getUnit());
    }

    private List<Component> findAllComponent() {
        return componentRepository.findAll();
    }

    private Optional<Component> findComponentByName(String name) {
        return componentRepository.findByComponentName(name);
    }

    private Component componentByName(String name) {
        return componentRepository.findComponentByComponentName(name);
    }

}
