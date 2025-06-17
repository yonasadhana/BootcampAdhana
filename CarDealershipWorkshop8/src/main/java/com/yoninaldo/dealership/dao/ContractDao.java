package com.yoninaldo.dealership.dao;

import com.yoninaldo.dealership.model.Contract;
import com.yoninaldo.dealership.model.LeaseContract;
import com.yoninaldo.dealership.model.SalesContract;

import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

public class ContractDao {
    private final SalesDao salesDao;
    private final LeaseDao leaseDao;

    public ContractDao(DataSource dataSource) {
        this.salesDao = new SalesDao(dataSource);
        this.leaseDao = new LeaseDao(dataSource);
    }

    public void saveContract(Contract contract) {
        if (contract instanceof SalesContract) {
            salesDao.saveSalesContract((SalesContract) contract);
        } else if (contract instanceof LeaseContract) {
            leaseDao.saveLeaseContract((LeaseContract) contract);
        }
    }

    public List<Contract> getAllContracts() {
        List<Contract> allContracts = new ArrayList<>();


        allContracts.addAll(salesDao.getAllSalesContracts());


        allContracts.addAll(leaseDao.getAllLeaseContracts());


        allContracts.sort((c1, c2) -> c2.getDate().compareTo(c1.getDate()));

        return allContracts;
    }
}