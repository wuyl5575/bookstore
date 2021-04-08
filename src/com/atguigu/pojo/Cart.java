package com.atguigu.pojo;

import java.math.BigDecimal;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * 购物车对象
 */
public class Cart {

    //private Integer totalCount;
    //private BigDecimal totalPrice;

    /**
     * key:是商品编号 value是商品信息
     */
    private Map<Integer, CartItem> items = new LinkedHashMap<Integer, CartItem>();



    /**
     * 添加商品项
     * @param cartItem
     */
    public void addItem(CartItem cartItem) {
        /**
         * 先查看购物车中是否已经添加过此商品，如果添加，则数量累加，总金额更新，如果
         *  没有添加过这直接放入集合中
         */
        CartItem item = items.get(cartItem.getId());
        if (item == null) {
            //之前没有添加过此商品
            items.put(cartItem.getId(), cartItem);
        } else {
            //已经添加过了
            //数量累加
            item.setCount(item.getCount()+1);
            //更新总金额
            item.setTotalPrice(item.getPrice().multiply(new BigDecimal(item.getCount())));
        }
    }


    /**
     * 删除商品项
     * @param id
     */
    public void deleteItem(Integer id) {
        items.remove(id);
    }


    /**
     *
     * 清空购物车
     */
    public void clear() {
        items.clear();
    }


    /**
     * 修改商品数量
     * @param id
     * @param count
     */
    public void updateCount(Integer id, Integer count) {
        /**
         * 先查看集合中是否有此商品，如果有修改商品数量，更新总金额
         */
        CartItem cartItem = items.get(id);
        if(cartItem != null) {
            //修改商品数量
            cartItem.setCount(count);
            cartItem.setTotalPrice(cartItem.getPrice().multiply(new BigDecimal(cartItem.getCount())));
        }

    }









    public Integer getTotalCount() {
        //购物车里商品的数量
        Integer totalCount = 0;
        for (Map.Entry<Integer, CartItem>entry : items.entrySet()) {
            totalCount += entry.getValue().getCount();
        }
        //也可以这样遍历
        //for (CartItem value : items.values()) {
        //
        //}
        return totalCount;
    }


    public BigDecimal getTotalPrice() {
        //购物车总价
        BigDecimal totalPrice = new BigDecimal(0);
        for (Map.Entry<Integer, CartItem>entry : items.entrySet()) {
            totalPrice = totalPrice.add(entry.getValue().getTotalPrice());
        }
        return totalPrice;
    }



    public Map<Integer, CartItem> getItems() {
        return items;
    }

    public void setItems(Map<Integer, CartItem> items) {
        this.items = items;
    }

    @Override
    public String toString() {
        return "Cart{" +
                "totalCount=" + getTotalCount() +
                ", totalPrice=" + getTotalPrice() +
                ", items=" + items +
                '}';
    }

}
