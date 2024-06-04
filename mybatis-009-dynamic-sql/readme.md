批量删除：
url?id=1&id=2&id=3 表单的提交方式
String[] ids = request.getParameterValues("id")
String ids = {1,2,3}
delete from t_car where id in(1,2,3)

多条件查询
不提供查询：0 条件 select * from t_product;
当选择一个条件: select * from t_product where brand=#{brand}
当用户选了多个条件：select * from t_product where 条件1=...and 条件2=..and条件3 =...



批量插入:
insert into t_user(id,name,age) value(1,'zs',20),(2,'zs',20)
