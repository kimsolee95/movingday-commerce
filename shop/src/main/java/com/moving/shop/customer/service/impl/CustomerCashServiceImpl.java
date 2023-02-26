package com.moving.shop.customer.service.impl;

import static com.moving.shop.customer.domain.entity.CashBalanceHistory.initCashBalanceHistory;

import com.moving.shop.common.exception.customexception.CustomerException;
import com.moving.shop.common.exception.type.CustomerErrorCode;
import com.moving.shop.common.security.TokenProvider;
import com.moving.shop.customer.domain.dto.ChangeCashForm;
import com.moving.shop.customer.domain.entity.CashBalanceHistory;
import com.moving.shop.customer.domain.entity.Customer;
import com.moving.shop.customer.domain.repository.CashBalanceHistoryRepository;
import com.moving.shop.customer.domain.repository.CustomerRepository;
import com.moving.shop.customer.service.CustomerCashService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerCashServiceImpl implements CustomerCashService {

  private final CashBalanceHistoryRepository cashBalanceHistoryRepository;
  private final TokenProvider tokenProvider;
  private final CustomerRepository customerRepository;

  @Transactional
  @Override
  public ChangeCashForm changeCashBalance(String refinedToken, ChangeCashForm form) {

    String email = tokenProvider.getUsername(refinedToken);
    Customer customer = customerRepository.findByEmail(email)
        .orElseThrow(() -> new CustomerException(CustomerErrorCode.NOT_EXIST_MEMBER));

    CashBalanceHistory cashBalanceHistory = cashBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(customer.getId())
        .orElseGet(() -> CashBalanceHistory.initCashBalanceHistory(customer));

    if (cashBalanceHistory.getCurrentCash() + form.getCash() < 0) {
      throw new CustomerException(CustomerErrorCode.SHORT_OF_BALANCE);
    }

    cashBalanceHistory = CashBalanceHistory.builder()
        .changeCash(form.getCash())
        .currentCash(cashBalanceHistory.getCurrentCash() + form.getCash())
        .description(form.getDescription())
        .fromWhom(email)
        .customer(customer)
        .build();

    customer.changeCash(cashBalanceHistory.getCurrentCash());
    cashBalanceHistoryRepository.save(cashBalanceHistory);
    return form;
  }

  @Override
  public CashBalanceHistory changeCashBalance(Customer customer, ChangeCashForm form) {

    CashBalanceHistory cashBalanceHistory = cashBalanceHistoryRepository.findFirstByCustomer_IdOrderByIdDesc(customer.getId())
        .orElseGet(() -> CashBalanceHistory.initCashBalanceHistory(customer));

    if (cashBalanceHistory.getCurrentCash() + form.getCash() < 0) {
      throw new CustomerException(CustomerErrorCode.SHORT_OF_BALANCE);
    }

    cashBalanceHistory = CashBalanceHistory.builder()
        .changeCash(form.getCash())
        .currentCash(cashBalanceHistory.getCurrentCash() + form.getCash())
        .description(form.getDescription())
        .fromWhom(form.getFromWhom())
        .customer(customer)
        .build();

    customer.changeCash(cashBalanceHistory.getCurrentCash());
    return cashBalanceHistoryRepository.save(cashBalanceHistory);
  }

}
