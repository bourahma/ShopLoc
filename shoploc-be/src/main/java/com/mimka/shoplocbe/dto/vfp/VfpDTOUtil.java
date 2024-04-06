package com.mimka.shoplocbe.dto.vfp;

import com.mimka.shoplocbe.entities.Customer;
import com.mimka.shoplocbe.entities.VfpHistory;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class VfpDTOUtil {

    private ModelMapper modelMapper;

    @Autowired
    public VfpDTOUtil(ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    public VfpDTO toVfpDTO (VfpHistory vfpHistory, Customer customer) {
        VfpDTO vfpDTO = new VfpDTO();
        vfpDTO.setGrantedDate(vfpHistory.getGrantedDate());
        vfpDTO.setExpirationDate(vfpHistory.getExpirationDate());
        vfpDTO.setVfpMember(customer.isVfpMembership());

        return vfpDTO;
    }
}
