 不用ORM的原因
 
 认为二维数据（matrix,table）是核心的数据结构，人类对复杂结构化的数据的阅读远不如一张表来的直观，所以报表是非常常见的终端视图方式。
 而这种数据和传统的RDBMS是吻合的，和基于json或object的DBMS是不吻合的。
 如果数据库要用RDBMS,而表示层又要呈现大量的复杂报表，那么中间的ORM显然有点多余。
 所以SQL是不可能绕过的开发技术。原生的SQL在性能上，单元测试便利性上比更高级更抽象的一些DataAcess技术（如面向对象的JPA，Hibernate)等表现更好。而目前唯一明显的不足是不能跨SQL方言。
 建立以db table和SQL为核心的开发体系。

 
 技术架构：
 
 Java 8:
   Groovy 2.4+
   Spring Framework(webmvc,jdbc) 4+
   Spring Security 4+

显然现在使用这套框架有点过时，不过Spring已经非常成熟，还是有必要的。

考虑更新的技术：

 spring boot
 spring cloud
 spring 4 -> spring 5 
 groovy -> kotlin
 maven -> gradle
 

   


 

 
 
 