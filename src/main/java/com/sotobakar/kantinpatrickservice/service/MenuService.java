package com.sotobakar.kantinpatrickservice.service;

import com.sotobakar.kantinpatrickservice.dto.request.CreateMenuRequest;
import com.sotobakar.kantinpatrickservice.dto.request.UpdateMenuRequest;
import com.sotobakar.kantinpatrickservice.exception.NotFoundException;
import com.sotobakar.kantinpatrickservice.model.Menu;
import com.sotobakar.kantinpatrickservice.repository.MenuRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class MenuService {

    private final MenuRepository menuRepository;
    private final FileUploadService fileUploadService;

    @Autowired
    public MenuService(MenuRepository menuRepository, FileUploadService fileUploadService) {
        this.menuRepository = menuRepository;
        this.fileUploadService = fileUploadService;
    }

    public List<Menu> getListOfMenus() {
        return this.menuRepository.findAll();
    }

    public Menu create(CreateMenuRequest menuData) {
        // Upload photo
        String photoName = this.fileUploadService.uploadFile(menuData.getPhoto(), "menu");

        // Set Photo
        menuData.setPhotoName(photoName);

        Menu menu = new Menu();
        menu.setName(menuData.getName());
        menu.setPhoto(menuData.getPhotoName());
        menu.setPrice(menuData.getPrice());

        return this.menuRepository.save(menu);
    }

    public Menu update(Long id, UpdateMenuRequest menuData) {
        Menu menu = this.menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu"));

        // Check if there is photo in request, if there is then update
        if (menuData.getPhoto() != null) {
            // Check if photo is not null
            if (menu.getPhoto() != null) {
                this.fileUploadService.deleteFile(menu.getPhoto(), "menu");
            }
            // Get file name of photo and set to photo name
            String photoName = this.fileUploadService.uploadFile(menuData.getPhoto(), "menu");

            menu.setPhoto(photoName);
        }

        menu.setName(menuData.getName());
        menu.setPrice(menuData.getPrice());
        return this.menuRepository.save(menu);
    }

    public void delete(Long id) {
        Menu menu = this.menuRepository.findById(id).orElseThrow(() -> new NotFoundException("Menu"));

        // Check if photo is not null
        if (menu.getPhoto() != null) {
            this.fileUploadService.deleteFile(menu.getPhoto(), "menu");
        }

        this.menuRepository.delete(menu);
    }
}
