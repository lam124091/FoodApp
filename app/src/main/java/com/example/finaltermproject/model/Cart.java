package com.example.finaltermproject.model;

import java.util.ArrayList;
import java.util.List;

public class Cart
{
    private int cartid;
    private Customer customer;
    private ArrayList<LineItem> LINEITEM;

    public Cart(int cartid, Customer customer, ArrayList<LineItem> LINEITEM) {
        this.cartid = cartid;
        this.customer = customer;
        this.LINEITEM = LINEITEM;
    }

    public int getCartid() {
        return cartid;
    }

    public void setCartid(int cartid) {
        this.cartid = cartid;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public ArrayList<LineItem> getLINEITEM() {
        return LINEITEM;
    }

    public void setLINEITEM(ArrayList<LineItem> LINEITEM) {
        this.LINEITEM = LINEITEM;
    }

    public int getCount()
    {
        return LINEITEM.size();
    }

    public void addItem(LineItem item)
    {
        int foodid = item.getFood().getFoodid();
        int quantity = item.getQuantity();
        for (LineItem cartItem : LINEITEM)
            if (cartItem.getFood().getFoodid() == foodid)
            {
                if (item.getQuantity() == 1)
                    cartItem.setQuantity(cartItem.getQuantity() + 1);
                else
                    cartItem.setQuantity(quantity);
                return;
            }

        LINEITEM.add(item);
    }

    public void removeItem(LineItem item)
    {
        int foodid = item.getFood().getFoodid();
        for (int i = 0; i < LINEITEM.size(); i++)
        {
            LineItem lineItem = LINEITEM.get(i);
            if (lineItem.getFood().getFoodid() == foodid)
            {
                LINEITEM.remove(i);
                return;
            }
        }
    }

    public void updateItem(LineItem item)
    {
        int foodid = item.getFood().getFoodid();
        LineItem lineitem = getItemByCode(foodid);
        if (lineitem != null)
            lineitem.setQuantity(item.getQuantity());
    }

    public LineItem getItemByCode(int idTour)
    {
        for (LineItem cartItem : LINEITEM)
            if (cartItem.getFood().getFoodid() == idTour)
                return cartItem;

        return null;
    }

    public double calculateTotalAmount() {
        double total = 0;
        for (LineItem item : LINEITEM) {
            total += item.getTotal();
        }
        return total;
    }

    public LineItem findItemById(int foodId) {
        for (LineItem item : LINEITEM) {
            if (item.getFood().getFoodid() == foodId) {
                return item;
            }
        }
        return null;
    }

    public List<Integer> getAllSizeIds()
    {
        List<Integer> allSizeIds = new ArrayList<>();

        for (LineItem lineItem : LINEITEM)
        {
            Food food = lineItem.getFood();
            if (food != null)
            {
                List<Size> sizes = food.getFoodsize();
                if (sizes != null && !sizes.isEmpty()) {
                    Size firstSize = sizes.get(0); // Chỉ lấy phần tử đầu tiên
                    allSizeIds.add(firstSize.getSizeid());
                }
            }
        }

        return allSizeIds;
    }
}
