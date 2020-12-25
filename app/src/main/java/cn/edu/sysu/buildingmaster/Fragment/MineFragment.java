package cn.edu.sysu.buildingmaster.Fragment;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import cn.edu.sysu.buildingmaster.Activity.LoginActivity;
import cn.edu.sysu.buildingmaster.R;
import cn.edu.sysu.buildingmaster.Util.Base64Helper;
import cn.edu.sysu.buildingmaster.Util.BitmapHelper;
import cn.edu.sysu.buildingmaster.Util.SharePreferenceUtil;


public class MineFragment extends Fragment {

    public MineFragment() {
        // Required empty public constructor
    }

    public static MineFragment newInstance() {
        return new MineFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_mine, container, false);
        ImageView head = view.findViewById(R.id.mine_head);
        TextView username =view.findViewById(R.id.mine_name);
        Button cancel = view.findViewById(R.id.cancel);
        if (SharePreferenceUtil.isLogined(getActivity().getApplicationContext())){
            String s = SharePreferenceUtil.getData(getActivity().getApplicationContext(),"head");
            Log.i("head",s);
            Bitmap map = Base64Helper.Base64ToBitMap(s);
            head.setImageBitmap(map);
            username.setText(SharePreferenceUtil.getData(getActivity().getApplicationContext(),"username"));
        }
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharePreferenceUtil.setData(getActivity().getApplicationContext(),"username","");
                startActivity(new Intent(getActivity(), LoginActivity.class));
                getActivity().finish();
            }
        });
        return view;
    }
}