package com.shop.online.service;

import com.shop.online.entity.ShopOrderSeq;
import com.shop.online.repository.ShopOrderSeqRepository;
import jakarta.persistence.LockModeType;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OrderSequenceService {

    private final ShopOrderSeqRepository shopOrderSeqRepository;

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Transactional
    public Integer getNextId() {
        ShopOrderSeq seq = shopOrderSeqRepository.save(new ShopOrderSeq());
        return seq.getId();
    }
}
