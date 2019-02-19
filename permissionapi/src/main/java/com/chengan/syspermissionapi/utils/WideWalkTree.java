package com.chengan.syspermissionapi.utils;

import com.chengan.syspermissionapi.domain.BaseNode;

import java.util.List;
import java.util.ArrayList;

public class WideWalkTree<T extends BaseNode> extends Tree<T> {

  public WideWalkTree(List<T> nodes){
    super(nodes);
  }

  @Override
  public List<T> findAllChildren(T node) {
    List<T> childrenNodes = new ArrayList<>();
    List<T> anodes = new ArrayList<>();
    anodes = findChildren(node);
    childrenNodes.addAll(anodes);
    while (!anodes.isEmpty()){
      List<T> cnodes = new ArrayList<>();
      for (T anode : anodes){
        cnodes.addAll(findChildren(anode));
      }
      childrenNodes.addAll(cnodes);
      anodes = cnodes;
    }
    return childrenNodes;
  }


}