package com.example.lenovo.e_cart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class CategoryFragment extends Fragment {


    private ProductAdapter mAdapter;
    private RecyclerView mRecyclerView;
    private static final String ALERT = "Alert";
    Category mCategory;
    Boolean OkOrCancel;
    public static final int REQUEST_Boolean = 0;

    public static CategoryFragment newInstance(UUID ID) {
        Bundle args = new Bundle();

        args.putSerializable("Category_ID", ID);
        CategoryFragment fragment = new CategoryFragment();
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.categoryfragment, container, false);

        mRecyclerView = (RecyclerView) v.findViewById(R.id.recyclerview);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));


        updateUI();
        return v;
    }

    @Override
    public void onResume() {
        super.onResume();
        updateUI();
    }

    private void updateUI() {
        CategoryLab categoryLab = CategoryLab.getInstance(getActivity());
        UUID ID = (UUID) getArguments().getSerializable("Category_ID");

        List<Product> products = categoryLab.getProducts(ID);

        if (mAdapter == null) {
            mAdapter = new ProductAdapter(products);
            mRecyclerView.setAdapter(mAdapter);
        } else {
            mAdapter.notifyDataSetChanged();
        }

    }


    public class ProductHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        Product mProduct;
        TextView txtProductName, txtProductAmount;
        ImageButton imgToggle;

        public ProductHolder(View itemView) {
            super(itemView);

            txtProductName = (TextView) itemView.findViewById(R.id.txtName);
            txtProductAmount = (TextView) itemView.findViewById(R.id.txtAmount);
            imgToggle = (ImageButton) itemView.findViewById(R.id.imgtoggle);
            imgToggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {


                    FragmentManager manager = getFragmentManager();
                    AlertDialogFragment dialog = new AlertDialogFragment();
                    dialog.setTargetFragment(CategoryFragment.this, REQUEST_Boolean);
                    dialog.show(manager, ALERT);

                }
            });

        }


        public void bindProduct(Product product) {
            if(product.isOutOfStock()){
                itemView.setBackgroundColor(ContextCompat.getColor(getActivity(),R.color.bg_out_of_stock));
            }else{
                itemView.setBackgroundColor(ContextCompat.getColor(getActivity(), android.R.color.transparent));
            }
            mProduct = product;
            txtProductName.setText(mProduct.getProductName());
            txtProductAmount.setText(String.valueOf(mProduct.getProductAmount()));
        }


        @Override
        public void onClick(View view) {

        }
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode != Activity.RESULT_OK) {
            return;
        }
        if (requestCode == REQUEST_Boolean) {

            OkOrCancel = data.getBooleanExtra(AlertDialogFragment.OkOrCancel, true);
            if (OkOrCancel == true) {


            } else {



            }


        }
    }

    public class ProductAdapter extends RecyclerView.Adapter<ProductHolder> {

        private List<Product> mProducts;

        public ProductAdapter(List<Product> products) {
            mProducts = products;
        }

        @Override
        public ProductHolder onCreateViewHolder(ViewGroup parent, int viewType) {

            LayoutInflater layoutInflater = LayoutInflater.from(getActivity());
            View view = layoutInflater
                    .inflate(R.layout.product_list_layout, parent, false);
            return new ProductHolder(view);
        }

        @Override
        public void onBindViewHolder(ProductHolder holder, int position) {
            Product product = mProducts.get(position);
            holder.bindProduct(product);
        }

        @Override
        public int getItemCount() {

            return mProducts == null ? 0 : mProducts.size();
        }
    }
}

