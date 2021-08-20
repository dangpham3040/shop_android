package com.example.shop_android.fragment;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridView;

import com.example.shop_android.R;
import com.example.shop_android.adapter.productAdapter;
import com.example.shop_android.data.StaticConfig;
import com.example.shop_android.model.product;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link #newInstance} factory method to
 * create an instance of this fragment.
 */
public class fragment_shop extends Fragment {

    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    View view;
    ArrayList<product> list_product = new ArrayList<>();
    productAdapter adapter;
    GridView gridView;

    public fragment_shop() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment fragment_shop.
     */
    // TODO: Rename and change types and number of parameters
    public static fragment_shop newInstance(String param1, String param2) {
        fragment_shop fragment = new fragment_shop();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_shop, container, false);
        // Inflate the layout for this fragment

        setControl();
        //setEnvet();
        return view;
    }

    private void setControl() {
        gridView = view.findViewById(R.id.GridView);
        khoitao();
        adapter = new productAdapter(getContext(), R.layout.list_shop, list_product);
        gridView.setAdapter(adapter);
    }

    private void khoitao() {
        product sp1 = new product("san pham 1", StaticConfig.Default_avatar);
        product sp2 = new product("san pham 1", StaticConfig.Default_avatar);
        product sp3 = new product("san pham 1", StaticConfig.Default_avatar);
        product sp4 = new product("san pham 1", StaticConfig.Default_avatar);
        product sp5 = new product("san pham 1", StaticConfig.Default_avatar);
        product sp6 = new product("san pham 1", StaticConfig.Default_avatar);

        list_product.add(sp1);
        list_product.add(sp2);
        list_product.add(sp3);
        list_product.add(sp4);
        list_product.add(sp5);

    }
}