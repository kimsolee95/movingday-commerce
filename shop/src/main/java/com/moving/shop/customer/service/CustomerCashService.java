package com.moving.shop.customer.service;

import com.moving.shop.customer.domain.dto.ChangeCashForm;
import com.moving.shop.customer.domain.entity.CashBalanceHistory;
import com.moving.shop.customer.domain.entity.Customer;

public interface CustomerCashService {

  ChangeCashForm changeCashBalance(String refinedToken, ChangeCashForm form);

  CashBalanceHistory changeCashBalance(Customer customer, ChangeCashForm form);

}
