package com.chengan.syspermissionapi.query;

import java.io.Serializable;
import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@Data
@ToString
public class Query<T> implements Serializable {
  protected T filter;
  protected List<Sorter> sorters;
  protected Integer offset;
  protected Integer size;
}