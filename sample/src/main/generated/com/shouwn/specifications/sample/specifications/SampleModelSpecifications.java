package com.shouwn.specifications.sample.specifications;

import java.lang.Long;
import java.lang.String;
import java.util.List;
import lombok.Data;

@Data
public class SampleModelSpecifications {
  private String test;

  private Long box;

  private List<Long> boxes;
}
