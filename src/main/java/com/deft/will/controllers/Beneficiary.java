package com.deft.will.controllers;
import com.deft.will.dtos.WillFormRequest;
import com.deft.will.dtos.WillFormResponse;
import com.deft.will.services.WillService;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5000"})
public class Beneficiary {

    private WillService willService;

    public Beneficiary(WillService willService) {
        this.willService = willService;
    }

    @GetMapping("/get/beneficiary/{id}")
    public Mono<WillFormResponse> getBeneficiary(@PathVariable String id) throws InterruptedException {
        Thread.sleep(1000);
        return willService.getWill(id);
    }
        @GetMapping("/get_all/beneficiary")
        public Flux<WillFormResponse> getAllBeneficiary () {
            return willService.getAllWill();
        }

        @PostMapping("/add/beneficiary")
        public Mono<WillFormResponse> addBeneficiary (@RequestBody WillFormRequest willFormRequest){
            return willService.createWill(willFormRequest);
        }

        @DeleteMapping("/delete/beneficiary/{id}")
        public Mono<Void> delbeneficiary (@PathVariable String id){
            return willService.delBeneficiary(id);
        }
        @PutMapping("/update/beneficiary/{id}")
        public Mono<WillFormResponse> updateBeneficiary (@PathVariable String id, @RequestBody WillFormRequest
        updateRequest){
            return willService.updateBeneficiary(id, updateRequest);
        }

    }

