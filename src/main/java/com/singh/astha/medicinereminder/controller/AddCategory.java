package com.singh.astha.medicinereminder.controller;

import com.singh.astha.medicinereminder.dtos.CategoryRequest;
import com.singh.astha.medicinereminder.models.Category;
import com.singh.astha.medicinereminder.services.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.neo4j.Neo4jProperties;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
@RestController
public class AddCategory {

    @Autowired
    private CategoryService categoryService;

    @PostMapping(value="/addcategory")
    public ResponseEntity<Long> addCategory(Authentication authentication,
                                            @RequestBody CategoryRequest categoryRequest){

        Long userId = Long.valueOf(authentication.getName());
        return ResponseEntity.status(HttpStatus.CREATED).body(categoryService.addCategory(categoryRequest,userId));
    }

}
