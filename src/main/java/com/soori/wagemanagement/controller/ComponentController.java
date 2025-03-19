package com.soori.wagemanagement.controller;

import com.soori.wagemanagement.dto.ComponentDto;
import com.soori.wagemanagement.service.ComponentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/component")
public class ComponentController {

    @Autowired
    private ComponentService componentService;

    @PostMapping("/addComponent")
    public ResponseEntity<ComponentDto> createComponent(@RequestBody ComponentDto componentDto) {
        return ResponseEntity.status(HttpStatus.CREATED).body(componentService.addComponent(componentDto));
    }

    @GetMapping("/allComponent")
    public ResponseEntity<List<ComponentDto>> getAllComponents() {
        return ResponseEntity.status(HttpStatus.OK).body(componentService.getAllAvailableComponent());
    }

    @GetMapping("/{componentName}")
    public ResponseEntity<ComponentDto> getComponentByName(@PathVariable("componentName") String name) {
        return ResponseEntity.status(HttpStatus.OK).body(componentService.getComponentByName(name));
    }

    @PutMapping("/edit")
    public ResponseEntity<ComponentDto> updateComponent(@RequestBody ComponentDto componentDto) {
        return ResponseEntity.status(HttpStatus.OK).body(componentService.updateComponent(componentDto));
    }

}
