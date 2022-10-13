package com.zeus.generate.core;

import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.converts.MySqlTypeConvert;
import com.baomidou.mybatisplus.generator.config.querys.MySqlQuery;
import com.baomidou.mybatisplus.generator.keywords.MySqlKeyWordsHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 代码生成器
 */
@Slf4j
@Component
public class MyBatisPlusGenerator implements InitializingBean {
    @Value("${url}")
    private String url;
    @Value("${username}")
    private String username;
    @Value("${pswd}")
    private String pswd;
    @Value("${author:}")
    private String author;
    @Value("${outputDir}")
    private String outputDir;
    @Value("${packageParent}")
    private String packageParent;
    @Value("${include}")
    private String include;
    @Value("${tablePrefix:}")
    private String tablePrefix;

    @Override
    public void afterPropertiesSet() throws Exception {
//        String url = System.getProperty("url");
//        String username = System.getProperty("username");
//        String pswd = System.getProperty("pswd");
//        String author = System.getProperty("author");
//        String outputDir = System.getProperty("outputDir");
//        String packageParent = System.getProperty("packageParent");
//        String include = System.getProperty("include");
//        String tablePrefix = System.getProperty("tablePrefix");
        // 数据源配置
//        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder("jdbc:mysql://10.20.12.20:3306/product_ams?characterEncoding=utf8&useSSL=false", "product_ams", "Cmp@v587").dbQuery(new MySqlQuery()).typeConvert(new MySqlTypeConvert()).keyWordsHandler(new MySqlKeyWordsHandler());
        DataSourceConfig.Builder dataSourceConfig = new DataSourceConfig.Builder(url, username, pswd).dbQuery(new MySqlQuery()).typeConvert(new MySqlTypeConvert()).keyWordsHandler(new MySqlKeyWordsHandler());

        FastAutoGenerator.create(dataSourceConfig).globalConfig(builder -> {
            builder.author(author) //设置作者
                    .commentDate("YYYY-MM-DD HH:mm:ss")//注释日期
                    .outputDir(outputDir); //指定输出目录
        }).packageConfig(builder -> {
            builder.parent(packageParent); // 设置父包名
            builder.controller("controller");
            builder.service("service");
            builder.entity("entity");
            builder.mapper("repository");
        }).strategyConfig(builder -> {
            builder.addInclude(include) // 设置需要生成的表名
                    .addTablePrefix(tablePrefix); // 设置过滤表前缀
            builder.entityBuilder().enableLombok();//开启 lombok 模型
            builder.entityBuilder().enableTableFieldAnnotation();//开启生成实体时生成字段注解
            builder.entityBuilder().disableSerialVersionUID().enableFileOverride();
            builder.controllerBuilder().enableFileOverride();
            builder.serviceBuilder().enableFileOverride();
            builder.mapperBuilder().enableFileOverride();
            builder.controllerBuilder().enableRestStyle();//开启生成@RestController 控制器
            builder.serviceBuilder().formatServiceFileName("%sService");
            builder.mapperBuilder().formatMapperFileName("%sRepository");
        }).templateConfig(builder -> {
            builder.controller("/vm/controller.java.vm");
            builder.service("/vm/service.java.vm");
            builder.serviceImpl("/vm/serviceImpl.java.vm");
            builder.entity("/vm/entity.java.vm");
            builder.mapper("/vm/repository.java.vm");
        }).execute();
        log.info("**********************************************");
        log.info("代码生成完成,请手动关闭当前服务<_||_>!!");
        log.info("**********************************************");
    }
}
