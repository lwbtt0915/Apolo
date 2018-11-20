package fabos.framework.core.orm.dao;

import tk.mybatis.mapper.additional.idlist.IdListMapper;
import tk.mybatis.mapper.additional.insert.InsertListMapper;

public interface BatchMapper<T, PK> extends BaseMapper<T>, IdListMapper<T, PK>, InsertListMapper<T> {


}
