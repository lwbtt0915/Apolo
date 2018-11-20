package fabos.framework.flow.service.impl;

import org.springframework.stereotype.Service;

import fabos.framework.core.orm.service.impl.BaseServiceImpl;
import fabos.framework.flow.model.ProcessAction;
import fabos.framework.flow.model.ProcessActionPK;
import fabos.framework.flow.service.ProcessActionService;

@Service("processActionService")
public class ProcessActionServiceImpl extends BaseServiceImpl<ProcessAction, ProcessActionPK> implements ProcessActionService {

}
