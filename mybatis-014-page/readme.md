页码: pageNum(用户会发送请求，携带页码pageNum给服务器)
每页显示的记录条数，pageSize，例如，百度默认就是每页展示10条记录。
实际上每一次在进行分页请求发送的时候，都是要发送两个数据的
页码pageNum 要传送给服务器
每页显示的记录条数pageSize也要传送给服务器

前端提交表单的话，数据格式：
url?pageNum=1&pageSize=10

关于mysql当中的分页sql应该怎么写？limit 关键字
limit 语法格式：
limit 开始下标，显示的记录条数
limit startIndex, pageSize

select * from t_car limit 0,3;
select * from t_car limit 2; 和 select * from t_car limit 0,2; 是等效的
mysql当中起始的下标从0开始，第一条记录的下标是 0 

> 假设每页显示3条记录
> 第一页: limit 0,3 (0 1 2)
> 第二页: limit 3,3 (3 4 5)
> 第三页: limit 6,3 (6 7 8)
> 第四页: limit 9,3 (9 10 11)
> ...
> 假设每页显示pageSize 条记录
> 第pageNum 页；limit (pageNum - 1) * pageSize, pageSize
> //  每页显示的记录条数
>  int pageSize = 3
> 页码
> int pageNum = 10
> // 起始下标
> int startIndex =(pageNum - 1) * pageSize;