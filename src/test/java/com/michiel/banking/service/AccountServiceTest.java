package com.michiel.banking.service;

import com.michiel.banking.repository.AccountRepository;
import com.michiel.banking.service.impl.AccountServiceImpl;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

@RunWith(MockitoJUnitRunner.class)
public class AccountServiceTest {

  @Mock
  private AccountRepository accountRepository;

  @InjectMocks
  private AccountServiceImpl accountService;

  @Test
  public void testAccountShouldBeAdded() {

  }
}
