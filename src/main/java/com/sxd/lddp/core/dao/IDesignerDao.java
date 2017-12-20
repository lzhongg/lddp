package com.sxd.lddp.core.dao;

import com.sxd.lddp.core.model.ActInfoBO;
import org.springframework.stereotype.Repository;

import java.util.Map;

@Repository
public interface IDesignerDao {
    public void saveActInf(Map<String,Object> map);
}
