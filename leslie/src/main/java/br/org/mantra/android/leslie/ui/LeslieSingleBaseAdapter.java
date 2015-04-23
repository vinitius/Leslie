package br.org.mantra.android.leslie.ui;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import java.util.HashMap;
import java.util.List;

import br.org.mantra.android.leslie.Leslie;

public abstract class LeslieSingleBaseAdapter<T> extends BaseAdapter {

    private List<T> mAdapterCollection;
    private Context mContext;
    private Leslie mLeslieBindInstance;

    public abstract int getLayoutResource();
    public abstract void setValuesForViewHolderItem(T item, int position, View convertView,ViewGroup parent);

    public LeslieSingleBaseAdapter(Context context,List<T> collection){
        setContext(context);
        setAdapterCollection(collection);
        setLeslieBindInstance(Leslie.with(getContext()).bind(getLayoutResource()));
    }

    @Override
    public int getCount() {
        return getAdapterCollection().size();
    }

    @Override
    public T getItem(int position) {
        return getAdapterCollection().get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        if (convertView == null) {
            convertView = LayoutInflater.from(getContext()).inflate(getLayoutResource(), parent, false);
            getLeslieBindInstance().hold(convertView, this);
        }

        getLeslieBindInstance().bindFromViewHolder((HashMap<String,Object>)convertView.getTag(),this);
        setValuesForViewHolderItem(getAdapterCollection().get(position),position,convertView,parent);

        return convertView;
    }

    public List<T> getAdapterCollection() {
        return mAdapterCollection;
    }

    public void setAdapterCollection(List<T> adapterCollection) {
        this.mAdapterCollection = adapterCollection;
    }

    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public Leslie getLeslieBindInstance() {
        return mLeslieBindInstance;
    }

    public void setLeslieBindInstance(Leslie leslieBindInstance) {
        this.mLeslieBindInstance = leslieBindInstance;
    }
}
