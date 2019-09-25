package com.michiel.banking.service;

import com.michiel.banking.entity.DeviceEntity;
import com.michiel.banking.repository.DeviceRepository;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DeviceService {

  @Autowired
  private DeviceRepository deviceRepository;

  public DeviceEntity saveDevice(DeviceEntity deviceEntity) {
    return deviceRepository.save(deviceEntity);
  }

  public Iterable<DeviceEntity> getDevices() {
    return deviceRepository.findAll();
  }

  public DeviceEntity getDeviceById(long Id) throws NoSuchElementException {
    Optional<DeviceEntity> deviceEntity = deviceRepository.findById(Id);
    if (deviceEntity.isPresent()) {
      return deviceEntity.get();
    } else {
      throw new NoSuchElementException();
    }
  }
}
