package com.deft.will.services;


import com.deft.will.dtos.WillFormRequest;
import com.deft.will.dtos.WillFormResponse;
import com.deft.will.models.WillFormModel;
import com.deft.will.repo.WillRepo;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class WillService {

    private final WillRepo repo;
    public WillService(WillRepo repo){
        this.repo=repo;
    }
    public Mono<WillFormResponse> createWill (WillFormRequest willFormRequest){
        WillFormModel will=new WillFormModel();
        will.setEmail(willFormRequest.getEmail());
        will.setFirstName(willFormRequest.getFirstName());
        will.setLastName(willFormRequest.getLastName());
        will.setMobile(willFormRequest.getMobile());
        will.setRelationShip(willFormRequest.getRelationShip());
        will.setDob(willFormRequest.getDob());

        return repo.save(will).map(
                save-> {
                    WillFormResponse res = new WillFormResponse();
                    res.setId(save.getId());
                    res.setFirstName(save.getFirstName());
                    res.setLastName(save.getLastName());
                    res.setEmail(save.getEmail());
                    res.setMobile(save.getMobile());
                    res.setDoB(save.getDob());
                    res.setRelation(save.getRelationShip());
                    return res;
                }


        );
    }

    public Flux<WillFormResponse> getAllWill() {
        return repo.findAll().map(
                save -> {
                    WillFormResponse res = new WillFormResponse();
                    res.setId(save.getId());
                    res.setFirstName(save.getFirstName());
                    res.setLastName(save.getLastName());
                    res.setEmail(save.getEmail());
                    res.setMobile(save.getMobile());
                    res.setDoB(save.getDob());
                    res.setRelation(save.getRelationShip());
                    return res;
                }
        );
    }

    public Mono<Void> delBeneficiary(String id) {
        return repo.deleteById(id);
    }

    public Mono<WillFormResponse> updateBeneficiary(String id, WillFormRequest request) {
        return repo.findById(id).flatMap(
                saved-> {
                    saved.setEmail(request.getEmail());
                    saved.setFirstName(request.getFirstName());
                    saved.setLastName(request.getLastName());
                    saved.setDob(request.getDob());
                    saved.setRelationShip(request.getRelationShip());
                    saved.setMobile(request.getMobile());
                    return repo.save(saved);
                }
        ).map(
                save-> {
                    WillFormResponse res = new WillFormResponse();
                    res.setId(save.getId());
                    res.setRelation(save.getRelationShip());
                    res.setEmail(save.getEmail());
                    res.setDoB(save.getDob());
                    res.setFirstName(save.getFirstName());
                    res.setLastName(save.getLastName());
                    res.setMobile(save.getMobile());
                    return res;
                }
        );

    }

    public Mono<WillFormResponse> getWill(String id) {
        return repo.findById(id).map(
                save->{
                    WillFormResponse res = new WillFormResponse();
                    res.setId(save.getId());
                    res.setRelation(save.getRelationShip());
                    res.setEmail(save.getEmail());
                    res.setDoB(save.getDob());
                    res.setFirstName(save.getFirstName());
                    res.setLastName(save.getLastName());
                    res.setMobile(save.getMobile());
                    return res;
                }
        );
    }
}
