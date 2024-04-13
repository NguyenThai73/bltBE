package com.be.controller;

import com.be.common_api.Decor;
import com.be.dto.DecorDto;
import com.be.mapper.DecorMapper;
import com.be.service.DecorService;
import com.llq.springfilter.boot.Filter;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;

@RequestMapping("/api/decor")
@RestController
@Slf4j
@Api("decor")
public class DecorController {
    private final DecorService decorService;

    public DecorController(DecorService decorService) {
        this.decorService = decorService;
    }

    @PostMapping("/post")
    public ResponseEntity<DecorDto> save(@RequestBody @Validated DecorDto decorDto) {
        DecorDto decorDto1 = decorService.save(decorDto);
        return ResponseEntity.ok(decorDto1);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<DecorDto> findById(@PathVariable("id") Long id) {
        DecorDto decor = decorService.findById(id);
        return ResponseEntity.ok(decor);
    }

    @DeleteMapping("/del/{id}")
    public ResponseEntity<Void> delete(@PathVariable("id") Long id) {
        DecorDto decor = decorService.findById(id);
        decor.setDeleted(true);
        decorService.update(decor, id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/get/page")
    public ResponseEntity<Page<DecorDto>> pageQuery(@Filter Specification<Decor> spec, @PageableDefault(sort = "id", direction = Sort.Direction.DESC, size = 1000000) Pageable pageable) {
        Page<DecorDto> decorPage = decorService.findByCondition(spec, pageable);
        return ResponseEntity.ok(decorPage);
    }

    @PutMapping("/put/{id}")
    public ResponseEntity<DecorDto> update(@RequestBody @Validated DecorDto decorDto, @PathVariable("id") Long id) {
        DecorDto decor = decorService.findById(id);
        if(decorDto.getIdDecorType() != null){
            decor.setIdDecorType(decorDto.getIdDecorType());
        }
        if(decorDto.getName() != null && decorDto.getName() != ""){
            decor.setName(decorDto.getName());
        }
        if(decorDto.getImage() != null && decorDto.getImage() != ""){
            decor.setImage(decorDto.getImage());
        }
        if(decorDto.getDescriptionDecor() != null && decorDto.getDescriptionDecor() != ""){
            decor.setDescriptionDecor(decorDto.getDescriptionDecor());
        }
        if(decorDto.getPrice() != null){
            decor.setPrice(decorDto.getPrice());
        }
        if(decorDto.getQuantity() != null){
            decor.setQuantity(decorDto.getQuantity());
        }
        if(decorDto.getStatus() != null){
            decor.setStatus(decorDto.getStatus());
        }
        DecorDto decorDto1 = decorService.update(decor, id);
        return ResponseEntity.ok(decorDto1);
    }
}