package com.deft.will.services;

import com.deft.will.dtos.WillFormRequest;
import com.deft.will.dtos.WillFormResponse;
import com.deft.will.models.WillFormModel;
import com.deft.will.repo.WillRepo;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.test.StepVerifier;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static reactor.core.publisher.Mono.when;

@ExtendWith(MockitoExtension.class)
class WillServiceTest {
    @InjectMocks
    private WillService willService;
    @Mock
    private WillRepo willRepo;
    private WillFormRequest request;
    private WillFormModel savedEntity;
    @BeforeEach
    void setUp(){
        LocalDate localDate = LocalDate.parse("1999-11-12");
        request= new WillFormRequest( "John", "Doe", "john123@gmail.com","12345" , localDate, "friend");
         savedEntity =new WillFormModel();

        savedEntity.setId("1");
        savedEntity.setFirstName("John");
        savedEntity.setLastName("Doe");
        savedEntity.setEmail("john123@gmail.com");
        savedEntity.setMobile("12345");
        savedEntity.setDob(localDate);
        savedEntity.setRelationShip("friend");


    }
    @Test
    void createWill() {

        when(willRepo.save(any(WillFormModel.class))).thenReturn(Mono.just(savedEntity));

        Mono<WillFormResponse> response=willService.createWill(request);
        WillFormResponse result=response.block();
        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john123@gmail.com", result.getEmail());
        assertEquals("12345", result.getMobile());
        assertEquals(LocalDate.of(1999, 11, 12), result.getDoB());
        assertEquals("friend", result.getRelation());

        verify(willRepo, times(1)).save(any(WillFormModel.class));
    }

    @Test
    void getAllWill() {
        LocalDate localDate = LocalDate.parse("1999-11-12");

        WillFormModel e1 = new WillFormModel();
        e1.setId("1");
        e1.setFirstName("John");
        e1.setLastName("Doe");
        e1.setEmail("john1@gmail.com");
        e1.setMobile("11111");
        e1.setDob(localDate);
        e1.setRelationShip("friend");

        WillFormModel e2 = new WillFormModel();
        e2.setId("2");
        e2.setFirstName("Jane");
        e2.setLastName("Smith");
        e2.setEmail("jane2@gmail.com");
        e2.setMobile("22222");
        e2.setDob(localDate);
        e2.setRelationShip("sister");
        when(willRepo.findAll()).thenReturn(Flux.just(e1, e2));

        Flux<WillFormResponse> res= willService.getAllWill();
        List<WillFormResponse> result=res.collectList().block();
        assertNotNull(result);
        assertEquals(2, result.size());

        WillFormResponse r1 = result.get(0);
        assertEquals("1", r1.getId());
        assertEquals("John", r1.getFirstName());
        assertEquals("Doe", r1.getLastName());
        assertEquals("john1@gmail.com", r1.getEmail());
        assertEquals("11111", r1.getMobile());
        assertEquals(localDate, r1.getDoB());
        assertEquals("friend", r1.getRelation());

        WillFormResponse r2 = result.get(1);
        assertEquals("2", r2.getId());
        assertEquals("Jane", r2.getFirstName());
        assertEquals("Smith", r2.getLastName());
        assertEquals("jane2@gmail.com", r2.getEmail());
        assertEquals("22222", r2.getMobile());
        assertEquals(localDate, r2.getDoB());
        assertEquals("sister", r2.getRelation());

       verify(willRepo, times(1)).findAll();
    }

    @Test
    void delBeneficiary() {
        String id="1";
        when(willRepo.deleteById(id)).thenReturn(Mono.empty());
        Mono<Void> del= willService.delBeneficiary(id);
        del.block();
        verify(willRepo, times(1)).deleteById(id);
    }

    @Test
    void updateBeneficiary() {
        String id = "1";

        LocalDate oldDob = LocalDate.of(1995, 1, 1);
        LocalDate newDob = LocalDate.of(1999, 11, 12);
        WillFormModel existing = new WillFormModel();
        existing.setId(id);
        existing.setFirstName("Old");
        existing.setLastName("Name");
        existing.setEmail("old@mail.com");
        existing.setMobile("00000");
        existing.setDob(oldDob);
        existing.setRelationShip("old-relation");

        WillFormRequest request = new WillFormRequest(
                "John",
                "Doe",
                "john123@gmail.com",
                "12345",
                newDob,
                "friend"
        );
        WillFormModel updated = new WillFormModel();
        updated.setId(id);
        updated.setFirstName("John");
        updated.setLastName("Doe");
        updated.setEmail("john123@gmail.com");
        updated.setMobile("12345");
        updated.setDob(newDob);
        updated.setRelationShip("friend");

        when(willRepo.findById(id)).thenReturn(Mono.just(existing));
        when(willRepo.save(any(WillFormModel.class)))
                .thenReturn(Mono.just(updated));

        StepVerifier.create(willService.updateBeneficiary(id, request))
                .assertNext(res -> {
                    assertEquals("John", res.getFirstName());
                    assertEquals("Doe", res.getLastName());
                    assertEquals("john123@gmail.com", res.getEmail());
                    assertEquals("12345", res.getMobile());
                    assertEquals(newDob, res.getDoB());
                    assertEquals("friend", res.getRelation());
                })
                .verifyComplete();

        verify(willRepo).findById(id);
        verify(willRepo).save(any(WillFormModel.class));







    }

    @Test
    void getWill() {
        LocalDate localDate = LocalDate.parse("1999-11-12");
        when(willRepo.findById("1")).thenReturn(Mono.just(savedEntity));

        Mono<WillFormResponse> resp= willService.getWill("1");
        WillFormResponse result= resp.block();

        assertNotNull(result);
        assertEquals("1", result.getId());
        assertEquals("John", result.getFirstName());
        assertEquals("Doe", result.getLastName());
        assertEquals("john123@gmail.com", result.getEmail());
        assertEquals("12345", result.getMobile());
        assertEquals(localDate, result.getDoB());
        assertEquals("friend", result.getRelation());


    }
}