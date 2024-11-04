package com.ecommerce.Order.dto;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@RequiredArgsConstructor
public class GetCartResponse {
    private int id;
    private int userid;
    private int totalCartPrice;
    private List<GetCartReponseCartItem> cartitems=new ArrayList<GetCartReponseCartItem>();
}
