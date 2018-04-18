package com.example.lenovo.e_cart;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.Toast;

public class DetailProductFragment extends Fragment {
String Amount;
    String Name,Desc;
EditText mProductName,mProductAmount,mProductDesc;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {

        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_product, menu);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.menu_item_save_product:
Product product = new Product();
                /*CategoryLab.getInstance(getActivity()).addProduct(product,category);
                Intent intent = ECartActivity
                        .newIntent(getActivity(), crime.getId());
                startActivity(intent);*/
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detailproductfragment, container, false);

    mProductName = (EditText) v.findViewById(R.id.DetailProductName);
    mProductAmount = (EditText) v.findViewById(R.id.DetailProductAmount);
    mProductDesc = (EditText) v.findViewById(R.id.DetailProductDesc);
    Name = mProductName.getText().toString();
    Amount = String.valueOf(mProductAmount.getText().toString());
    Desc = mProductDesc.getText().toString();
try {
    Product product = new Product();
    product.setProductName(Name);
    product.setProductDesc(Desc);
    product.setProductAmount(Integer.parseInt(Amount));
}
catch(Exception e)
{
    Product product = new Product();
    product.setProductAmount(0);

}







        return v;
    }

}
