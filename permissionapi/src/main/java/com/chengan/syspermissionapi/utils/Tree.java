package com.chengan.syspermissionapi.utils;

import com.chengan.syspermissionapi.domain.BaseNode;

import java.util.List;
import java.util.ArrayList;

public abstract class Tree<T extends BaseNode> {
  private List<T> nodes;

  public Tree(List<T> nodes){
    this.nodes = nodes;
  }

  public List<T> findChildren(T node){
    List<T> childsList=new ArrayList<T>();
    for (T snode : nodes){
      if (snode.getPid() == node.getId()){
        childsList.add(snode);
      }
    }
    return childsList;
  }

  public List<T> findAllFather(T node){
    List<T> fatherList = new ArrayList<T>();
    T tempnode = node;
    while (tempnode.getPid() == 0){
    for (T snode : nodes){
      if (snode.getId() == tempnode.getPid()){
        tempnode = snode;
        fatherList.add(tempnode);
      }
    }
  }
  return fatherList;
}

  public abstract List<T> findAllChildren(T node);

}