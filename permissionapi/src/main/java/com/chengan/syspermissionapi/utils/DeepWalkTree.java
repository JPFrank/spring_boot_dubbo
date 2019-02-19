package com.chengan.syspermissionapi.utils;

import com.chengan.syspermissionapi.domain.BaseNode;

import java.util.List;
import java.util.Stack;
import java.util.ArrayList;

public class DeepWalkTree<T extends BaseNode> extends Tree<T> {

  public DeepWalkTree(List<T> nodes){
    super(nodes);
  }

  @Override
  public List<T> findAllChildren(T node) {
    Stack <T> treeStack = new Stack<>();
    List<T> childrenNodes = new ArrayList<>();
    treeStack.add(node);
    while (!treeStack.isEmpty()){
      T tmpNode = treeStack.pop();
      List<T> snodes = findChildren(tmpNode);
      if (!snodes.isEmpty()){
        childrenNodes.addAll(snodes);
      }
      for (T snode : snodes){
        treeStack.add(snode);
      }
    }
    return childrenNodes;
  }
}