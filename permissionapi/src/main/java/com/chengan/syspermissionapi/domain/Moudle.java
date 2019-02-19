package com.chengan.syspermissionapi.domain;

import com.chengan.syspermissionapi.dto.MoudleDTO;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;


@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class Moudle extends BaseNode{
  private Long level;
  private String path;
  private Long seq;
  private String url;

  public MoudleDTO toConvertDTO() {
    MoudleDTO moudleDTO = new MoudleDTO();
    moudleDTO.setId(id);
    moudleDTO.setPid(pid);
    moudleDTO.setLevel(level);
    moudleDTO.setPath(path);
    moudleDTO.setIsLeaf(isLeaf);
    moudleDTO.setName(name);
    moudleDTO.setSeq(seq);
    moudleDTO.setUrl(url);
    return moudleDTO;
  }
}

