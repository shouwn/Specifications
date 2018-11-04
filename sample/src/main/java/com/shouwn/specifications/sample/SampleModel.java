package com.shouwn.specifications.sample;

import com.shouwn.specifications.annotation.Detail;
import com.shouwn.specifications.annotation.Specifications;

@Specifications
public class SampleModel {

    @Detail
    private String test;

    private String test2;
}
