package com.sxd.lddp.web;

import com.sxd.lddp.core.model.ActInfoBO;
import com.sxd.lddp.core.service.impl.DesignerService;
import com.sxd.lddp.core.utils.CommUtil;
import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;

@Log4j
@RestController
@RequestMapping("/metadata")
public class DesignerController{

    @Autowired
    private DesignerService designerService;

    @RequestMapping(value = "/queryMetaData" ,method = RequestMethod.POST, produces = "application/json;charset=UTF-8")
    public String queryMetaData(String actCode)
    {
        String content = "";
        String filePath = "C:/"+actCode+".md";
        try
        {

            content = CommUtil.readMetaData(filePath);
        }
        catch (Exception e)
        {
            if(!(new File(filePath)).exists())
            {
                log.error("【"+actCode+"】元数据文件不存在");
            }
            else
            {
                log.error(e);
            }
        }

        return content;
    }

    @RequestMapping(value = "/saveMetaData",method = RequestMethod.POST,  produces = "application/json;charset=UTF-8")
    public String saveMetaData(String content,String actCode)
    {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日");
        String filePath = "C:/"+actCode+".md";
        try
        {
            String contentstr = java.net.URLDecoder.decode(content,"UTF-8");
            Map<String,Object> jsonObj = CommUtil.formatJson(contentstr);
            Map<String,Object> actInf = (Map<String, Object>) jsonObj.get("actInf");
            String startDT = (String) actInf.get("actStartDT");
            String endDT = (String) actInf.get("actEndDT");
            actInf.put("actStartDT",sdf.parse(startDT));
            actInf.put("actEndDT",sdf.parse(endDT));

            log.warn("actInfo:"+actInf);
            designerService.saveActInf(actInf);

            content = CommUtil.writeMetaData(filePath,contentstr);
        }
        catch (Exception e)
        {
            log.error(e);
        }

        return "保存成功！";
    }

}
