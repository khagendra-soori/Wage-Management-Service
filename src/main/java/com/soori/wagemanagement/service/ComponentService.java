package com.soori.wagemanagement.service;
//showing available component/item is one thing
//adding them to the cart and calculating price and reducing their quantity is another thing
//can delete all at once...
//but we have to do 'minus how much do they added to the cart'

//Not keeping delete operation on component because we can make the count 0
// and history of component could be needed later

import com.soori.wagemanagement.dto.ComponentDto;

import java.util.List;

public interface ComponentService {
    List<ComponentDto> getAllAvailableComponent();
    ComponentDto getComponentByName(String name);
    ComponentDto addComponent(ComponentDto componentDto);
    ComponentDto updateComponent(ComponentDto componentDto);
}
