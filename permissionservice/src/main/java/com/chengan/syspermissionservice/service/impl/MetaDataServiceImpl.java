//package com.chengan.syspermissionservice.service.impl;
//
//import com.chengan.syspermissionapi.domain.MetaData;
//import com.chengan.syspermissionservice.service.MetaDataService;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.mongodb.core.MongoTemplate;
//import org.springframework.data.mongodb.core.query.Criteria;
//import org.springframework.data.mongodb.core.query.Query;
//import org.springframework.data.mongodb.core.query.Update;
//import org.springframework.stereotype.Service;
//
//import java.util.List;
//
//@Slf4j
//@Service("metaDataService")
//public class MetaDataServiceImpl implements MetaDataService{
//
//    @Autowired
//    private MongoTemplate mongoTemplate;
//
//    @Override
//    public void create(MetaData metaData){
//        mongoTemplate.save(metaData);
//    }
//
//    @Override
//    public void update(MetaData metaData){
//        Query query=new Query(Criteria.where("id").is(metaData.getId()));
//        Update update= new Update().set("sourceCode", metaData.getSourceCode()).set("sourceType", metaData.getSourceType());
//        mongoTemplate.updateFirst(query,update,MetaData.class);
//    }
//
//    @Override
//    public void delete(Long id){
//        Query query=new Query(Criteria.where("id").is(id));
//        mongoTemplate.remove(query,MetaData.class);
//    }
//
//    @Override
//    public List<MetaData> getListBySourceCode(Long sourceCode){
//        Query query=new Query(Criteria.where("sourceCode").is(sourceCode));
//        List<MetaData> metaData =  mongoTemplate.find(query , MetaData.class);
//        return metaData;
//    }
//}