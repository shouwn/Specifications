package com.shouwn.specifications.sample;

import com.shouwn.specifications.annotation.Detail;
import com.shouwn.specifications.annotation.Specifications;

import java.util.List;

@Specifications
public class SampleModel {

    @Detail
    private String test;

    private String test2;

    @Detail(type = Detail.ID)
    private Box box;

    @Detail(type = Detail.IDS)
    private List<Box> boxes;
}

class Box {
    private Integer id;
}