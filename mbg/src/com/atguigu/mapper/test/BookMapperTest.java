package com.atguigu.mapper.test;

import com.atguigu.mapper.BookMapper;
import com.atguigu.pojo.Book;
import com.atguigu.pojo.BookExample;
import org.apache.ibatis.io.Resources;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
import org.junit.Before;
import org.junit.Test;

import java.math.BigDecimal;


public class BookMapperTest {
    SqlSessionFactory sqlSessionFactory;
    @Before
    public void setUp() throws Exception {
        sqlSessionFactory=new SqlSessionFactoryBuilder()
                .build(Resources.getResourceAsStream("mybatis-config.xml"));

    }

    @Test
    public void countByExample() {
        SqlSession session = sqlSessionFactory.openSession();
        try {
            BookMapper mapper = session.getMapper(BookMapper.class);

            BookExample bookExample = new BookExample();
            BookExample.Criteria criteria = bookExample.createCriteria();
            criteria.andPriceGreaterThan(new BigDecimal("50"));

            criteria.andSalesGreaterThan(100);
        }finally {
            session.close();
        }
    }

    @Test
    public void deleteByExample() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);
            BookExample bookExample=new BookExample();
            bookExample.createCriteria().andAuthorEqualTo("国哥");
            bookExample.or().andNameLike("%数据%");
            int i = mapper.deleteByExample(bookExample);
            System.out.println(i);
            session.commit();


        }finally {
            session.close();
        }
    }

    @Test
    public void deleteByPrimaryKey() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);

           mapper.deleteByPrimaryKey(1);
           session.commit();
        }finally {
            session.close();
        }
    }

    @Test
    public void insert() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);

            mapper.insert(new Book(null,"作词",null,null,1245,123));
            session.commit();
        }finally {
            session.close();
        }
    }

    @Test
    public void insertSelective() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);
            /**
             * insertSelective()，只插入非空的列，而空的字段不出现在插入的sql语句中
             */
            mapper.insertSelective(new Book(null,"写诗",null,null,1245,123));
            session.commit();
        }finally {
            session.close();
        }
    }

    @Test
    public void selectByExample() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);

            BookExample bookExample=new BookExample();
            // 添加需要排序的列
            bookExample.setOrderByClause("price desc");
            /**
             * 书名是：怎样拐跑别人的媳妇 或 销量大于100
             */
            bookExample.createCriteria().andNameEqualTo("怎样拐跑别人的媳妇");
            bookExample.or().andSalesLessThan(100);
            /**
             * selectByExample()根据给定的条件查询图书信息
             */
           mapper.selectByExample(bookExample).forEach(System.out::println);
        }finally {
            session.close();
        }
    }

    @Test
    public void selectByPrimaryKey() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);
            System.out.println(mapper.selectByPrimaryKey(7));



        }finally {
            session.close();
        }
    }

    @Test
    public void updateByExampleSelective() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);
            mapper.updateByPrimaryKeySelective(new Book(4,"盖饭","0821",new BigDecimal("12"),null,null));

        }finally {
            session.close();
        }
    }

    @Test
    public void updateByExample() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);

            BookExample bookExample=new BookExample();
            bookExample.createCriteria().andPriceIsNull();
            /**
             * 根据条件，去更新表（很少用）<br/>
             * updateByExampleSelective() 这个使用比较多，根据条件更新，并且排空字段
             */
            mapper.updateByExample(new Book(null,"xxx","xxx",new BigDecimal("1234"),100,100),bookExample);
            session.commit();
        }finally {
            session.close();
        }
    }

    @Test
    public void updateByPrimaryKeySelective() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);
            /**
             * updateByPrimaryKeySelective() 根据id来更新，非空字段
             */
            mapper.updateByPrimaryKeySelective(new Book(9,"hhh","国哥",new BigDecimal("1234"), null,null));
        }finally {
            session.close();
        }
    }

    @Test
    public void updateByPrimaryKey() {
        SqlSession session = sqlSessionFactory.openSession();
        try{
            BookMapper mapper = session.getMapper(BookMapper.class);

            /**
             * updateByPrimaryKeySelective()根据id来更新，非空字段
             * updateByPrimaryKey()空字段也更新
             */
            mapper.updateByPrimaryKey(new Book(9,"AI","HW",new BigDecimal("999"),null,null));
            session.commit();
        }finally {
            session.close();
        }
    }
}