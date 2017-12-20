package com.sxd.lddp.core.service.impl;

import com.sxd.lddp.core.dao.IDesignerDao;
import com.sxd.lddp.core.model.ActInfoBO;
import com.sxd.lddp.core.service.IDesignerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DesignerService implements IDesignerService {

    @Autowired
    private IDesignerDao designerDao;

    @Override
    public void saveActInf(Map<String,Object> map)
    {
        designerDao.saveActInf(map);
    }
}
