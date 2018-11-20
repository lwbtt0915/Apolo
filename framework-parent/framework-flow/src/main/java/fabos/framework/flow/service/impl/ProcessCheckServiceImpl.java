package fabos.framework.flow.service.impl;

import org.springframework.stereotype.Service;

import fabos.framework.core.orm.service.impl.BaseServiceImpl;
import fabos.framework.flow.model.ProcessCheck;
import fabos.framework.flow.model.ProcessCheckPK;
import fabos.framework.flow.service.ProcessCheckService;

@Service("processCheckService")
public class ProcessCheckServiceImpl extends BaseServiceImpl<ProcessCheck, ProcessCheckPK> implements ProcessCheckService {

}
