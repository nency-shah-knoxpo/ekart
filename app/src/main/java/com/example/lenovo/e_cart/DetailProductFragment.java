package com.example.lenovo.e_cart;

import android.content.Context;
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

import java.util.UUID;

public class DetailProductFragment extends Fragment {

    private static final String TAG = DetailProductFragment.class.getSimpleName();
    private static final String ARGS_PRODUCT = TAG + ".ARGS_PRODUCT";
    private static final String ARGS_CATEGORY_ID = TAG + ".ARGS_CATEGORY_ID";


    private Product mProduct;
    private EditText mProductName, mProductAmount, mProductDesc;


    public static DetailProductFragment newInstance(Product product, UUID categoryId) {
        Bundle args = new Bundle();

        args.putParcelable(ARGS_PRODUCT, product);
        args.putSerializable(ARGS_CATEGORY_ID, categoryId);
        DetailProductFragment fragment = new DetailProductFragment();
        fragment.setArguments(args);
        return fragment;
    }

    public interface Callback{
        void onProductCreated(String name, String description, int amount);
        void onProductUpdated();
    }

    private Callback mCallback;

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        inflater.inflate(R.menu.add_product, menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_item_save_product:
                String name = mProductName.getText().toString();
                String description = mProductDesc.getText().toString();
                int amount;
                try{
                    amount = Integer.parseInt(mProductAmount.getText().toString());
                }catch(NumberFormatException e){
                    amount = 0;
                }
                if(mProduct != null){
                    //update product
                    mProduct.setProductName(name);
                    mProduct.setProductDesc(description);
                    mProduct.setProductAmount(amount);
                    UUID categoryId = (UUID) getArguments().getSerializable(ARGS_CATEGORY_ID);

                    CategoryLab.getInstance(getActivity())
                            .updateProduct(mProduct,categoryId);

                    mCallback.onProductUpdated();
                }else{
                    mCallback.onProductCreated(name, description, amount);
                }

                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        mCallback = (Callback) getActivity();
    }

    @Override
    public void onDetach() {
        mCallback = null;
        super.onDetach();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);

        if(getArguments()!=null && getArguments().getParcelable(ARGS_PRODUCT) != null){
            mProduct = getArguments().getParcelable(ARGS_PRODUCT);
        }
    }



    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.detailproductfragment, container, false);

        mProductName = (EditText) v.findViewById(R.id.DetailProductName);
        mProductAmount = (EditText) v.findViewById(R.id.DetailProductAmount);
        mProductDesc = (EditText) v.findViewById(R.id.DetailProductDesc);

        if(mProduct!= null){
            updateUI();
        }

        return v;
    }

    private void updateUI(){
        mProductName.setText(mProduct.getProductName());
        mProductAmount.setText(String.valueOf(mProduct.getProductAmount()));
        mProductDesc.setText(mProduct.getProductDesc());
    }

}
