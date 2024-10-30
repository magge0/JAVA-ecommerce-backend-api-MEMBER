package com.myshop.modules.product.entity.dto;

import com.myshop.modules.product.entity.dos.Wholesale;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.springframework.beans.BeanUtils;

/**
 * Quy tắc bán sỉ DTO
 */
@Data
@EqualsAndHashCode(callSuper = true)
@NoArgsConstructor
public class WholesaleDTO extends Wholesale {

    private static final long serialVersionUID = 853287561151783335L;

    public WholesaleDTO(Wholesale wholesale) {
        BeanUtils.copyProperties(wholesale, this);
    }
}