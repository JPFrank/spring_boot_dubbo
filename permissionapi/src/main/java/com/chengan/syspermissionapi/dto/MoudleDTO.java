package com.chengan.syspermissionapi.dto;

import com.chengan.syspermissionapi.domain.Moudle;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
@ApiModel(value="MoudleDTO", description="模块信息")
public class MoudleDTO implements Serializable {
  private Long id;
  private Long pid;
  private Long level;
  private String path;
  private Boolean isLeaf;
  private String name;
  private Long seq;
  private String url;

  public Moudle toConvertEntity() {
    Moudle moudle = new Moudle();
    moudle.setId(id);
    moudle.setPid(pid);
    moudle.setLevel(level);
    moudle.setPath(path);
    moudle.setIsLeaf(isLeaf);
    moudle.setName(name);
    moudle.setSeq(seq);
    moudle.setUrl(url);
    return moudle;
  }
}