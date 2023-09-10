package com.sotobakar.kantinpatrickservice.controller;

import com.sotobakar.kantinpatrickservice.dto.request.CreateMenuRequest;
import com.sotobakar.kantinpatrickservice.dto.request.UpdateMenuRequest;
import com.sotobakar.kantinpatrickservice.dto.response.BasicResponse;
import com.sotobakar.kantinpatrickservice.dto.response.DefaultResponse;
import com.sotobakar.kantinpatrickservice.dto.response.MenuResponse;
import com.sotobakar.kantinpatrickservice.model.Menu;
import com.sotobakar.kantinpatrickservice.service.MenuService;
import jakarta.validation.Valid;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/menus")
public class MenuController {

    private final ModelMapper modelMapper;
    private final MenuService menuService;
    @Value("${minio.url}" + "/" + "${minio.bucket.name}")
    private String photoBucketPath;

    @Autowired
    public MenuController(ModelMapper modelMapper, MenuService menuService) {
        this.modelMapper = modelMapper;
        this.menuService = menuService;

        this.modelMapper.typeMap(Menu.class, MenuResponse.class).addMappings(mapper ->
                mapper
                        .using(ctx -> ctx.getSource() != null ? this.photoBucketPath + "/menu/" + (ctx.getSource()) : null)
                        .map(Menu::getPhoto, MenuResponse::setPhoto)
        );
    }

    @GetMapping
    public ResponseEntity<DefaultResponse<List<MenuResponse>>> getAllMenus() {
        // Get List
        List<MenuResponse> menus = this.menuService.getListOfMenus()
                .stream()
                .map(menu -> this.modelMapper.map(menu, MenuResponse.class))
                .collect(Collectors.toList());

        DefaultResponse<List<MenuResponse>> response = new DefaultResponse<>(
                String.valueOf(HttpStatus.OK.value()),
                "List of menus",
                menus
        );

        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    // TODO: Validate photo must be jpeg,jpg,png format
    @PostMapping(consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<? extends BasicResponse> storeMenu(@Valid @ModelAttribute CreateMenuRequest menuData) {
        // Create Menu Service
        MenuResponse menu = this.modelMapper.map(this.menuService.create(menuData), MenuResponse.class);

        DefaultResponse<MenuResponse> response = new DefaultResponse<>(String.valueOf(HttpStatus.OK.value()), "Menu created", menu);
        return ResponseEntity.status(HttpStatus.OK.value()).body(response);
    }

    @PutMapping(value = "/{id}", consumes = {MediaType.MULTIPART_FORM_DATA_VALUE})
    public ResponseEntity<DefaultResponse<MenuResponse>> updateMenu(@PathVariable("id") Long id, @Valid @ModelAttribute UpdateMenuRequest menuData) {
        MenuResponse menu = this.modelMapper.map(this.menuService.update(id, menuData), MenuResponse.class);

        DefaultResponse<MenuResponse> response = new DefaultResponse<>(String.valueOf(HttpStatus.OK.value()), "Menu updated", menu);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<DefaultResponse<Object>> deleteMenu(@PathVariable("id") Long id) {
        this.menuService.delete(id);

        DefaultResponse<Object> response = new DefaultResponse<>(String.valueOf(HttpStatus.OK.value()), "Menu deleted", null);
        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
