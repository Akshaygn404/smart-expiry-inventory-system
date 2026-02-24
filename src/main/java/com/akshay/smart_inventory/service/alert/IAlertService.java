package com.akshay.smart_inventory.service.alert;

import com.akshay.smart_inventory.model.Alert;

import java.util.List;

public interface IAlertService {

    void scanAndGenerateAlerts();

    List<Alert> getActiveAlerts();

    void resolveAlert(Long alertId);
}