package com.moving.shop.customer.service;

import com.moving.shop.customer.domain.dto.ChangeCashForm;
import com.moving.shop.customer.domain.entity.CashBalanceHistory;

public interface CustomerCashService {

  ChangeCashForm changeCashBalance(String refinedToken, ChangeCashForm form);

}
