package com.yoninaldo.dealership.manager;

import com.yoninaldo.dealership.config.DatabaseConfig;
import com.yoninaldo.dealership.dao.AddOnDao;
import com.yoninaldo.dealership.model.AddOn;

import java.util.List;
import javax.sql.DataSource;

public class AddOnDataManager {
    private final AddOnDao addOnDao;

    public AddOnDataManager() {
        DataSource dataSource = DatabaseConfig.getDataSource();
        this.addOnDao = new AddOnDao(dataSource);
    }

    public List<AddOn> getAvailableAddOns() {
        return addOnDao.getAllAddOns();
    }

    public AddOn getAddOnByIndex(int index) {
        List<AddOn> addOns = getAvailableAddOns();
        if (index >= 0 && index < addOns.size()) {
            return addOns.get(index);
        }
        return null;
    }
}