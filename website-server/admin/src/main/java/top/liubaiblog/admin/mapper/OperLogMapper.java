package top.liubaiblog.admin.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import top.liubaiblog.admin.pojo.OperLog;

@Mapper
public interface OperLogMapper {
    int deleteByPrimaryKey(Integer operId);

    int insert(OperLog record);

    OperLog selectByPrimaryKey(Integer operId);

    List<OperLog> selectAll();

    int updateByPrimaryKey(OperLog record);
}