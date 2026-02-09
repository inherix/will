package com.deft.will.controllers;
import com.deft.will.dtos.WillFormRequest;
import com.deft.will.dtos.WillFormResponse;
import com.deft.will.models.PdfRequest;
import com.deft.will.services.PdfService;
import com.deft.will.services.WillService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


import java.util.List;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = {"http://localhost:5000"})
public class Beneficiary {

    private final WillService willService;
    private final PdfService pdfService;

    public Beneficiary(WillService willService, PdfService pdfService) {
        this.willService = willService;
        this.pdfService=pdfService;
    }

    @GetMapping("/get/beneficiary/{id}")
    public Mono<WillFormResponse> getBeneficiary(@PathVariable String id) {

        return willService.getWill(id);
    }
        @GetMapping("/get_all/beneficiary")
        public Flux<WillFormResponse> getAllBeneficiary () {
            return willService.getAllWill();
        }

        @PostMapping("/add/beneficiary")
        public Mono<WillFormResponse> addBeneficiary (@RequestBody WillFormRequest willFormRequest){
            System.out.println("test");
            return willService.createWill(willFormRequest);

        }

        @DeleteMapping("/delete/beneficiary/{id}")
        public Mono<Void> delBeneficiary (@PathVariable String id){
            return willService.delBeneficiary(id);
        }
        @PutMapping("/update/beneficiary/{id}")
        public Mono<WillFormResponse> updateBeneficiary (@PathVariable String id, @RequestBody WillFormRequest
        updateRequest){
            return willService.updateBeneficiary(id, updateRequest);
        }

        @PostMapping("/generate/pdf")
        public Mono<String> processPdf( @Valid @RequestBody PdfRequest data){
        List<String> keys=List.of("templates/header.html", "templates/body.html", "templates/footer.html");
        return pdfService.processWillPdf(keys, data);
        }
    }

