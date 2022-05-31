package com.kalanco.dictator.fragments;

import static com.kalanco.dictator.R.drawable.back;

import android.os.Bundle;

import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import com.kalanco.dictator.R;
import com.kalanco.dictator.models.FragmentHolder;
import com.kalanco.dictator.services.UserService;


public class FragmentEdit extends Fragment {
    FragmentHolder fragmentHolder;
    ImageButton a1, a2, a3, a4, a5, a6, a7, a8;
    ImageView ava;
    View view;
    Button save;
    EditText editText;
    int current;
    private Animation animation = null;

    public FragmentEdit() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_edit, container, false);

        linker();
        current = fragmentHolder.mDBHelper.getImg();
        editText.setText(fragmentHolder.mDBHelper.getName());
        setAva();
        view.findViewById(R.id.btn_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fragmentHolder.changeToProfile();
            }
        });
        View.OnClickListener listener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                animation = AnimationUtils.loadAnimation(
                        getActivity(), R.anim.scale);
                v.startAnimation(animation);

                switch (v.getId()) {
                    case R.id.a1:
                        ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar1));
                        current = 1;
                        break;
                    case R.id.a2:
                        ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar2));
                        current = 2;
                        break;
                    case R.id.a3:
                        ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar3));
                        current = 3;
                        break;
                    case R.id.a4:
                        ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar4));
                        current = 4;
                        break;
                    case R.id.a5:
                        ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar5));
                        current = 5;
                        break;
                    case R.id.a6:
                        ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar6));
                        current = 6;
                        break;

                }
//                v.animate()
//                        .scaleY(1.1f).scaleX(1.1f)
//                        .setInterpolator(new AccelerateDecelerateInterpolator())
//                        .setDuration(100);

                //Toast.makeText(getActivity(), "", Toast.LENGTH_SHORT).show();
            }
        };
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                UserService.setAva(current);
                fragmentHolder.mDBHelper.setAva(current);
                UserService.setName(editText.getText().toString());
                fragmentHolder.mDBHelper.setName(editText.getText().toString());
            }
        });
        a1.setOnClickListener(listener);
        a2.setOnClickListener(listener);
        a3.setOnClickListener(listener);
        a4.setOnClickListener(listener);
        a5.setOnClickListener(listener);
        a6.setOnClickListener(listener);

        return view;
    }

    private void setAva() {
        switch (current) {
            case 0:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar));
                break;
            case 1:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar1));
                break;
            case 2:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar2));
                break;
            case 3:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar3));
                break;
            case 4:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar4));
                break;
            case 5:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar5));
                break;
            case 6:
                ava.setImageDrawable(getActivity().getDrawable(R.drawable.avatar6));
                break;
        }
    }

    private void linker() {
        ava = view.findViewById(R.id.imageView2);
        a1 = view.findViewById(R.id.a1);
        a2 = view.findViewById(R.id.a2);
        a3 = view.findViewById(R.id.a3);
        a4 = view.findViewById(R.id.a4);
        a5 = view.findViewById(R.id.a5);
        a6 = view.findViewById(R.id.a6);
        save = view.findViewById(R.id.btn_save);
        editText = view.findViewById(R.id.fild_edit_name);
    }

    public void setFragmentHolder(FragmentHolder holder) {
        this.fragmentHolder = holder;
    }
}