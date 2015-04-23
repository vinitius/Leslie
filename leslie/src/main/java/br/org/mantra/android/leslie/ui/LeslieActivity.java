package br.org.mantra.android.leslie.ui;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import br.org.mantra.android.leslie.Leslie;

public abstract class LeslieActivity extends ActionBarActivity{

    public abstract int getLayoutResource();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Leslie.with(this).bind(getLayoutResource()).to(this);
    }
}
