package fabos.framework.core.orm.model;

/**
 * 乐观锁实现中,要求一个实体类中只能有一个乐观锁字段.
 * 
 * 想要使用乐观锁，只需要在实体中，给乐观锁字段增加 @tk.mybatis.mapper.annotation.Version 注解.
 * 
 * @author Bruno
 */
public interface Versionable {

	Integer getVersion();

	void setVersion(Integer version);
}
