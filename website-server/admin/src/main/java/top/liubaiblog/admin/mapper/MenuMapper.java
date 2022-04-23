package top.liubaiblog.admin.mapper;

import java.util.List;
import top.liubaiblog.admin.pojo.Menu;

public interface MenuMapper {
    int deleteByPrimaryKey(Long menuId);

    int insert(Menu record);

    Menu selectByPrimaryKey(Long menuId);

    List<Menu> selectAll();

    int updateByPrimaryKey(Menu record);
}