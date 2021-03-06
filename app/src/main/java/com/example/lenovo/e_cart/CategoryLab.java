package com.example.lenovo.e_cart;

import android.content.Context;
import android.util.Log;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class CategoryLab {

    private static CategoryLab sCategoryLab = null;

    public static CategoryLab getInstance(Context context) {
        if (sCategoryLab == null) {
            sCategoryLab = new CategoryLab(context);
        }
        return sCategoryLab;
    }
    private HashMap<UUID, ArrayList<Product>> mProductMap;
    private static ArrayList<Category> mCategories;


    private Context mAppContext;

    private CategoryLab(Context context) {
        mAppContext = context.getApplicationContext();
        mProductMap = new HashMap<>();

        mCategories = new ArrayList<>();

        Category electronics = new Category("Electronics");
        Category shoes = new Category("Shoes");
        Category clothes = new Category("Clothes");

        mCategories.add(electronics);
        mCategories.add(shoes);
        mCategories.add(clothes);

        mProductMap.put(electronics.getId(),new ArrayList<Product>());
        mProductMap.put(shoes.getId(),new ArrayList<Product>());
        mProductMap.put(clothes.getId(),new ArrayList<Product>());

        /*listproduct = new ArrayList<>();
        for (int i = 0; i <= 25; i++) {
            Product p = new Product();
            p.setProductName("Product" + i);
            p.setProductAmount(100);
            p.setProductDesc("xyz");
            listproduct.add(p);
        }

        mProductMap.put(electronics.getId(), listproduct);
        mProductMap.put(shoes.getId(), listproduct);
        mProductMap.put(clothes.getId(), listproduct);*/


    }

    public List<Category> getCategories() {
        return mCategories;
    }

    public void addProduct(Product product, Category category) {
        ArrayList<Product> products = mProductMap.get(category.getId());

        if (products == null) {
            products = new ArrayList<>();
        }
        products.add(product);
        mProductMap.put(category.getId(), products);

    }

    public void updateProduct(Product product, UUID categoryId){

        ArrayList<Product> products = mProductMap.get(categoryId);

        int foundIndex = -1;
        for(int i=0;i<products.size();i++){
            Product p = products.get(i);
            if(p.getProductID() == product.getProductID()){
                foundIndex = i;
                break;
            }
        }
        products.set(foundIndex,product);

        mProductMap.put(categoryId,products);
    }

    public List<Product> getProducts(Category category) {
        return getProducts(category.getId());
    }

    public List<Product> getProducts(UUID categoryId) {
        return mProductMap.get(categoryId);
    }

    public Product getProduct(UUID categoryId, UUID productId) {
        ArrayList<Product> listproduct = mProductMap.get(categoryId);
        for (Product p : listproduct) {
            if (p.getProductID().equals(productId)) {
                return p;
            }
        }
        return null;
    }

}
