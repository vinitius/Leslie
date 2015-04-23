package br.org.mantra.android.leslie.ui;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import br.org.mantra.android.leslie.Leslie;

public abstract class LeslieFragment  extends Fragment{

    public abstract int getLayoutResource();

    private View mPostBindView;



    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        super.onCreateView(inflater, container, savedInstanceState);

        setPostBindView(Leslie.with(getActivity()).bind(getLayoutResource()).to(this));

        return getPostBindView();
    }

    public View getPostBindView() {
        return mPostBindView;
    }

    public void setPostBindView(View postBindView) {
        this.mPostBindView = postBindView;
    }
}
