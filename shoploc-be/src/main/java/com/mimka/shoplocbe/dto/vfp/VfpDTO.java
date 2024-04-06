package com.mimka.shoplocbe.dto.vfp;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
public class VfpDTO {

    private LocalDate grantedDate;

    private LocalDate expirationDate;

    private boolean isVfpMember;
}
