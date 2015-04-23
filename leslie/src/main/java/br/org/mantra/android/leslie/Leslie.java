package br.org.mantra.android.leslie;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Leslie {

    private Context mContext;
    private HashMap<String,Integer> mViewHash;
    private View mViewToInflate;



    public static Leslie with(Context context){

        Leslie instance = new Leslie();
        instance.setContext(context);

        return instance;
    }

    public Leslie bind(int resource){


        setViewToInflate(LayoutInflater.from(getContext()).inflate(resource,null));
        setViewHash(new HashMap<String, Integer>());


        List<View> visited = new ArrayList<View>(); //visited views in the view hierarchy
        List<View> unvisited = new ArrayList<View>(); // yet not visited views. All should be visited by the end
        unvisited.add(getViewToInflate()); // starting with the view root
        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            visited.add(child);
            if (child.getTag() != null)
                getViewHash().put(child.getTag().toString(), child.getId());

            if (!(child instanceof ViewGroup)) continue;

            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i=0; i<childCount; i++)
                unvisited.add(group.getChildAt(i));
        }


        return this;

    }

    public Leslie bind(View view){


        setViewToInflate(view);
        setViewHash(new HashMap<String, Integer>());


        List<View> visited = new ArrayList<View>(); //visited views in the view hierarchy
        List<View> unvisited = new ArrayList<View>(); // yet not visited views. All should be visited by the end
        unvisited.add(getViewToInflate()); // starting with the view root
        while (!unvisited.isEmpty()) {
            View child = unvisited.remove(0);
            visited.add(child);
            if (child.getTag() != null)
                getViewHash().put(child.getTag().toString(), child.getId());

            if (!(child instanceof ViewGroup)) continue;

            ViewGroup group = (ViewGroup) child;
            final int childCount = group.getChildCount();
            for (int i=0; i<childCount; i++)
                unvisited.add(group.getChildAt(i));
        }


        return this;

    }

    public View hold(View viewToBeHold,Object target){
        HashMap<String,Object> viewHolder = new HashMap<String, Object>();
        Field[] fields = target.getClass().getDeclaredFields();
        for(Field field : fields){
            field.setAccessible(true);
            Class<?> fieldClass = field.getType();
            Integer viewId = getViewHash().get(field.getName());
            if (viewId != null){
                try {
                    field.set(target,fieldClass.cast(viewToBeHold.findViewById(viewId)));
                    viewHolder.put(field.getName(), field.get(target));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

        }

        viewToBeHold.setTag(viewHolder);

        return viewToBeHold;

    }

    public void bindFromViewHolder(HashMap<String,Object> viewHolder,Object target){
        for(Field field: target.getClass().getDeclaredFields()){
            if (getViewHash().containsKey(field.getName())){
                field.setAccessible(true);
                try {
                    field.set(target, viewHolder.get(field.getName()));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                }
            }

        }

    }

    public View to(Object target){

        if (target instanceof Activity)
            ((Activity)target).setContentView(getViewToInflate());


        for(Field field:target.getClass().getDeclaredFields()){

            field.setAccessible(true);
            Class<?> fieldType = field.getType();
            Integer viewId = getViewHash().get(field.getName());
            if (viewId != null){
                try {
                    field.set(target,fieldType.cast(getViewToInflate().findViewById(viewId)));
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }


        }



         return getViewToInflate();



    }





    public Context getContext() {
        return mContext;
    }

    public void setContext(Context context) {
        this.mContext = context;
    }

    public HashMap<String, Integer> getViewHash() {
        return mViewHash;
    }

    public void setViewHash(HashMap<String, Integer> viewHash) {
        this.mViewHash = viewHash;
    }


    public View getViewToInflate() {
        return mViewToInflate;
    }

    public void setViewToInflate(View viewToInflate) {
        this.mViewToInflate = viewToInflate;
    }


}
