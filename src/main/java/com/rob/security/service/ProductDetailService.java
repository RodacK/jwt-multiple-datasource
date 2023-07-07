package com.rob.security.service;

import com.rob.security.model.Product;

import javax.naming.NameNotFoundException;

public interface ProductDetailService {

    Product loadProductByName(String name) throws NameNotFoundException;
}
